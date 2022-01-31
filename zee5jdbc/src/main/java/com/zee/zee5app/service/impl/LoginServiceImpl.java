package com.zee.zee5app.service.impl;

import java.io.IOException;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.enums.ROLE;
import com.zee.zee5app.repository.LoginRepository;
import com.zee.zee5app.repository.impl.LoginRepositoryImpl;
import com.zee.zee5app.service.LoginService;

public class LoginServiceImpl implements LoginService {

	private static LoginService loginService = null;

	private LoginRepository loginRepository = null;

	private LoginServiceImpl() throws IOException {
		loginRepository = LoginRepositoryImpl.getInstance();
	}

	public static LoginService getInstance() throws IOException {
		if (loginService == null)
			loginService = new LoginServiceImpl();
		return loginService;
	}

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
