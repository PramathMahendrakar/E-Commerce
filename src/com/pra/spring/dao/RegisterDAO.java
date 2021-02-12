package com.pra.spring.dao;

import java.util.List;

import com.pra.spring.dto.LoginDTO;
import com.pra.spring.dto.RegisterDTO;
import com.pra.spring.dto.ResetPasswordDTO;
import com.pra.spring.exception.RepositoryException;

public interface RegisterDAO {

	public long save(RegisterDTO dto);

	public long fetchEmailCount(RegisterDTO dto);

	public List<RegisterDTO> fetch(RegisterDTO dto);

	public String fetchPassword(RegisterDTO dto);

	public String updatePaswword(RegisterDTO dto);

	public boolean isValidOtp(ResetPasswordDTO dto) throws RepositoryException;

	public boolean resetPassword(ResetPasswordDTO resetDTO);

	public Integer updateLoginFailCount(String email, int count) throws RepositoryException;

	public boolean updateAccountLocked(String email, boolean locked) throws RepositoryException;

}
