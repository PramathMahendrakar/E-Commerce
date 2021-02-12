package com.pra.spring.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pra.spring.dto.LoginDTO;
import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.dto.ResetPasswordDTO;
import com.pra.spring.email.MailSendingImpl;
import com.pra.spring.exception.ControllerException;
import com.pra.spring.exception.ServiceException;
import com.pra.spring.service.RegisterService;

@Controller
@RequestMapping("/")
public class RegisterController {

	private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private RegisterService service;

	public RegisterController() {
		logger.info("Created" + this.getClass().getSimpleName());
	}

	@RequestMapping(value = "/info.do", method = RequestMethod.POST)
	public String onSave(@ModelAttribute RegisterDTO dto, Model model) {

		try {
			logger.info("Invoked onsave " + dto);
			String success = service.validateSave(dto);

			if (success.equals("duplicate")) {
				model.addAttribute("message", "Email is duplicte");
				logger.info("Not saved");
			} else {
				logger.info("Saved" + success);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "LandingPage";
	}

	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute RegisterDTO dto, @ModelAttribute LoginDTO loginDTO, Model m)
			throws ControllerException {
		try {
			List<RegisterDTO> list=service.loginDetails(dto);
			String message = service.validateAndLogin(dto,loginDTO);
			
			for (RegisterDTO eCommerceDTO : list) {
				
				logger.debug(message);
				if(message.equals("matching")){
					m.addAttribute("message","Hi "+eCommerceDTO.getFname());
					return "Home";
				}
			}
			
			if(message.equals("notMatching")){
				m.addAttribute("message","Incorrect password you only have 3 attempts");
				return "Login";
			}
			if(message.equals("trialsExceeded")){
				m.addAttribute("message","Password attempts exceeded your account will be locked, please use forgot password");
				return "Login";
			}
			if(message.equals("locked")){
				m.addAttribute("message","Your account is locked, please use forgot password");
				return "Login";
			}if(message.equals("notRegistered")){
				m.addAttribute("message","This is not a registered email");
				return "Login";
			}

		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage());
		} catch (Exception e) {
			throw new ControllerException(e.getMessage());
		}
		return "Login";

	}

	@RequestMapping(value = "forget.do", method = RequestMethod.POST)
	public String forgot(@ModelAttribute RegisterDTO DTO, Model m) {

		try {
			boolean reset = service.resetPassword(DTO);
			if (!reset) {
				m.addAttribute("message", "This email is not registered");
				return "ForgetPassword";

			}
		} catch (ServiceException e) {
			System.out.println(e.getMessage());
		}
		return "Reset";

	}

	@RequestMapping(value = "reset.do", method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute ResetPasswordDTO redto, @ModelAttribute RegisterDTO dto, Model m)
			throws ControllerException {
		try {
			String vaildAndUpdated = service.validateAndUpdatePassword(redto);

			if (vaildAndUpdated.equals("valid")) {
				m.addAttribute("reset", "Password updated");
			} else if (vaildAndUpdated.equals("invalid")) {
				m.addAttribute("reset", "Invalid otp");
			} else if (vaildAndUpdated.equals("notMatching")) {
				m.addAttribute("reset", "Password and confirm password not matching");
			} else if (vaildAndUpdated.equals("NA")) {
				m.addAttribute("reset", "Please fill fields with valid data");
			}
		} catch (ServiceException e) {
			throw new ControllerException(e.getMessage());
		}
		return "Reset";
	}

}
