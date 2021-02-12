package com.pra.spring.service;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pra.spring.controller.RegisterController;
import com.pra.spring.dao.RegisterDAO;
import com.pra.spring.dto.LoginDTO;
import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.dto.ResetPasswordDTO;
import com.pra.spring.email.MailSending;
import com.pra.spring.exception.RepositoryException;
import com.pra.spring.exception.ServiceException;

@Service
public class RegisterServiceImpl implements RegisterService {

	private final static Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

	@Autowired
	private RegisterDAO dao;

	@Autowired
	private MailSending mailSending;

	@Autowired
	PasswordResetService passwordResetService;

	public RegisterServiceImpl() {

		logger.info("Created" + this.getClass().getSimpleName());
	}

	@Override
	public String validateSave(RegisterDTO dto) {

		boolean valid = false;
		String message = "NA";

		if (Objects.nonNull(dto)) {
			String name = dto.getFname();
			if (!StringUtils.isEmpty(name) && name.length() > 3) {
				logger.info("Name is valid");
				valid = true;
			} else {
				logger.info("name is invalid");
				valid = false;
			}

			if (valid) {
				String email = dto.getEmail();

				if (!StringUtils.isEmpty(email)) {
					System.out.println("Email is valid");
					valid = true;
				} else {
					System.out.println("Email is invalid");
					valid = false;
				}
			}

			if (valid) {
				String password = dto.getPassword();
				String confirmPassword = dto.getConfimPassword();

				if (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(confirmPassword)) {
					if (password.equals(confirmPassword)) {

						System.out.println("Password is valid");
						valid = true;
					} else {
						System.out.println("Password invalid");
						valid = false;
					}
				}

			}
			if (valid) {

				if (dao.fetchEmailCount(dto) > 0) {
					message = "duplicated";
				} else {

					dao.save(dto);
					logger.info("Inputs are valid and saving");
					message = "saved";
					mailSending.sendingMail(dto);
					logger.info("Sending mail to user");
					logger.error("");
				}
			} else {
				System.out.println("Invalid input...");
			}

		}
		return message;
	}

	@Override
	public List<RegisterDTO> loginDetails(RegisterDTO dto) throws RepositoryException {
		List<RegisterDTO> list = dao.fetch(dto);
		return list;
	}

	public String validateAndLogin(RegisterDTO dto, LoginDTO loginDTO) throws ServiceException {
		String message = null;
		boolean accountLocked = false;
		int failedLogin = 0;
		try {
			List<RegisterDTO> list = dao.fetch(dto);

			for (RegisterDTO registerDTO : list) {
				accountLocked = registerDTO.isAccountStatusLocked();

				logger.info("Account locked " + accountLocked);

				if (accountLocked == false) {
					if (list.size() == 1) {

						logger.info(registerDTO.getFname());
						System.out.println(list.size());

						if (registerDTO.getPassword().equals(loginDTO.getPassword())) {
							logger.info("password matching");
							dao.updateLoginFailCount(loginDTO.getEmail(), 0);
							logger.info("update login count to zero method");
							message = "matching";
						}

						else {
							failedLogin = registerDTO.getInvalidLoginCount();

							if (failedLogin < 3) {
								dao.updateLoginFailCount(loginDTO.getEmail(), ++failedLogin);
								message = "notMatching";
								logger.info("password not matching");
							}

							else {
								dao.updateAccountLocked(loginDTO.getEmail(), true);
								message = "trialsExceeded";
								logger.info("trials exceeded");
							}

							System.out.println(registerDTO);
						}

					} else {

						logger.info("User not registered");
						message = "notRegistered";
					}
				}
				if (accountLocked == true) {

					logger.info("Account Locked");
					message = "locked";
				}

			}

		} catch (RepositoryException e) {

			throw new ServiceException(e.getMessage());
		}
		return message;

	}

	@Override
	public boolean resetPassword(RegisterDTO dto) throws ServiceException {
		System.out.println("Invoked reset password method");
		try {
			long count = dao.fetchEmailCount(dto);
			System.out.println(count);
			if (count == 1) {
				String oTP = dao.updatePaswword(dto);
				List<RegisterDTO> list = dao.fetch(dto);
				for (RegisterDTO dtoRegisterDTO : list) {
					boolean sent = passwordResetService.sendMail(dtoRegisterDTO, oTP);
					dao.updateLoginFailCount(dto.getEmail(), 0);
					dao.updateAccountLocked(dto.getEmail(), false);
					logger.info("Email sent " + sent);
				}
				return true;
			}
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		return false;

	}

	@Override
	public String validateAndUpdatePassword(ResetPasswordDTO resetDTO) throws ServiceException {

		System.out.println("invoked validateAndUpdatePassword");
		String message = "NA";
		boolean valid = false;
		try {
			if (!resetDTO.getPassword().isEmpty() && !resetDTO.getNewPassword().isEmpty()
					&& !resetDTO.getcPassword().isEmpty()) {
				valid = true;
			}
			if (valid) {
				if (resetDTO.getNewPassword().equals(resetDTO.getcPassword())) {

					boolean validOtp = dao.isValidOtp(resetDTO);
					System.out.println(validOtp);

					if (validOtp) {
						dao.resetPassword(resetDTO);
						logger.info("otp is valid and password updated");
						return "valid";
					} else {
						logger.info("Invalid otp");

						return "invalid";
					}

				} else {
					logger.info("password and confirm password not matching...");
					return "notMatching";
				}
			} else {
				logger.info("fields can't be blank");
				return message;
			}

		} catch (RepositoryException e) {
			throw new ServiceException(e.getMessage());
		}

	}

}
