package com.pra.spring.service;

import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.exception.ServiceException;

public interface PasswordResetService {

	boolean sendMail(RegisterDTO dto,String otp) throws ServiceException;
}
