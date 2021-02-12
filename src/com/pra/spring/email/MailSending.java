package com.pra.spring.email;

import com.pra.spring.dto.RegisterDTO;

public interface MailSending {

	public boolean sendingMail(RegisterDTO dto);
	
}
