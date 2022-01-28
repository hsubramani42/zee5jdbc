package com.zee.zee5app.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Register;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;

public interface UserRepository {

	public String addUser(Register register);

	public Optional<Register> getUserById(String id) throws IdNotFoundException, SQLException, InvalidIdLengthException,
			InvalidNameException, InvalidEmailFormatException, InvalidPasswordException;

	public Optional<List<Register>> getAllUsersList()
			throws IdNotFoundException, InvalidIdLengthException, InvalidNameException, InvalidEmailFormatException, InvalidPasswordException;
	
	public Register[] getAllUsers()
			throws InvalidNameException, IdNotFoundException, InvalidIdLengthException, InvalidEmailFormatException, InvalidPasswordException;

	public String deleteUserById(String id) throws IdNotFoundException;

	public String updateUserById(String id, Register register) throws IdNotFoundException;

}
