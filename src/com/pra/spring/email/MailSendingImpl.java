package com.pra.spring.email;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.pra.spring.dto.RegisterDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MailSendingImpl implements MailSending {

	private final static Logger logger = LoggerFactory.getLogger(MailSendingImpl.class);
	@Autowired
	JavaMailSender sender;

	@Autowired
	SimpleMailMessage mailMessage;

	public MailSendingImpl() {
		logger.info("Created" + this.getClass().getSimpleName());
	}

	@Override
	public boolean sendingMail(RegisterDTO dto) {

		try {

			mailMessage.setTo(dto.getEmail());
			mailMessage.setSubject("Xworkz common module registeration");
			mailMessage.setText("Hi " + dto.getFname() + "," + "\n" + "\n"
					+ "Your registeration is successfull, please login with email and password." + "\n" + "\n"
					+ "Thanks," + "\n" + "Xworkz");
			sender.send(mailMessage);

			logger.info("Mail sent for user");
			return true;
		} catch (Exception e) {
			logger.error("Exception occured : \t" + e);
		}

		return false;
	}

}
