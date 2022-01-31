package com.zee.zee5app.repository;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.enums.ROLE;

public interface LoginRepository {

	public String addCredentials(Login login);

	public String deleteCredentials(String userName);

	public String changePassword(String userName,String password);
	
	public String changeRole(String userName, ROLE role);

}
