package com.pra.spring.service;

import java.util.List;

import com.pra.spring.dto.LoginDTO;
import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.dto.ResetPasswordDTO;
import com.pra.spring.exception.RepositoryException;
import com.pra.spring.exception.ServiceException;

public interface RegisterService {

	public String validateSave(RegisterDTO dto);

	String validateAndLogin(RegisterDTO dto, LoginDTO dto2) throws ServiceException;

	public String validateAndUpdatePassword(ResetPasswordDTO resetDTO) throws ServiceException;

	boolean resetPassword(RegisterDTO dto) throws ServiceException;

	List<RegisterDTO> loginDetails(RegisterDTO dto) throws RepositoryException;
}
