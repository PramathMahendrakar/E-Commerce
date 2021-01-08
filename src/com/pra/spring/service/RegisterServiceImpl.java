package com.pra.spring.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.pra.spring.dao.RegisterDAO;
import com.pra.spring.dto.RegisterDTO;

@Service
public class RegisterServiceImpl implements RegisterService {

	@Autowired
	private RegisterDAO dao;

	public RegisterServiceImpl() {

		System.out.println("Created..." + this.getClass().getSimpleName());
	}

	@Override
	public boolean validateSave(RegisterDTO dto) {

		boolean valid = false;

		if (Objects.nonNull(dto)) {
			String name = dto.getFname();
			if (!StringUtils.isEmpty(name) && name.length() > 3) {
				System.out.println("Name is valid");
				valid = true;
			} else {
				System.out.println("name is invalid");
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
				long id = dao.save(dto);
				System.out.println(id + " saved");
			} else {
				System.out.println("Invalid input...");
			}

		}
		return valid;
	}

}
