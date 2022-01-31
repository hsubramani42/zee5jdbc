package com.zee.zee5app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.enums.ROLE;
import com.zee.zee5app.repository.LoginRepository;
import com.zee.zee5app.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepository loginRepository = null;

	
	@Override
	public String addCredentials(Login login) {
		return this.loginRepository.addCredentials(login);
	}

	@Override
	public String deleteCredentials(String userName) {
		return this.loginRepository.deleteCredentials(userName);
	}

	@Override
	public String changePassword(String userName, String password) {
		return this.loginRepository.changePassword(userName, password);
	}

	@Override
	public String changeRole(String userName, ROLE role) {
		return this.loginRepository.changeRole(userName, role);
	}

}
