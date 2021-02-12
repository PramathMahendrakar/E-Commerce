package com.pra.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.exception.ServiceException;

@Service
public class PasswordResetServiceImpl implements PasswordResetService {

	@Autowired
	private MailSender mailsender;

	@Override
	public boolean sendMail(RegisterDTO DTO, String otp) throws ServiceException {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(DTO.getEmail());
		mailMessage.setSubject("One Time Password");
		mailMessage.setText("Hello " + DTO.getFname() + "\n" + "Please login with " + "\n" + "Email :" + DTO.getEmail()
				+ "\n" + "OTP " + otp);
		try {
			mailsender.send(mailMessage);
			return true;
		} catch (MailException e) {
			throw new ServiceException(e.getMessage());
		}
	}

}
