package com.zee.zee5app.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Register;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.repository.impl.UserRepositoryImpl;
import com.zee.zee5app.service.UserService;

public class UserServiceimpl implements UserService {

	private static UserRepositoryImpl userRepository = null;
	private static UserServiceimpl service = null;

	private UserServiceimpl() throws IOException {
		userRepository = UserRepositoryImpl.getInstance();
	}

	public static UserServiceimpl getInstance() throws IOException {

		if (service == null)
			service = new UserServiceimpl();
		return service;

	}

	@Override
	public String addUser(Register register) {
		return this.userRepository.addUser(register);
	}

	@Override
	public Optional<Register> getUserById(String id) throws InvalidNameException, IdNotFoundException,
			InvalidIdLengthException, InvalidEmailFormatException, InvalidPasswordException

	{
		return this.userRepository.getUserById(id);
	}

	@Override
	public Optional<List<Register>> getAllUsersList() throws InvalidNameException, IdNotFoundException,
			InvalidIdLengthException, InvalidEmailFormatException, InvalidPasswordException

	{
		return this.userRepository.getAllUsersList();
	}

	@Override
	public Register[] getAllUsers() throws InvalidNameException, IdNotFoundException, InvalidIdLengthException,
			InvalidEmailFormatException, InvalidPasswordException {
		return this.userRepository.getAllUsers();
	}

	@Override
	public String deleteUserById(String id) throws IdNotFoundException {
		return this.userRepository.deleteUserById(id);
	}

	@Override
	public String updateUserById(String id, Register register) throws IdNotFoundException {
		return this.userRepository.updateUserById(id, register);
	}

}
