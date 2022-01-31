package com.zee.zee5app.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.Register;
import com.zee.zee5app.dto.enums.ROLE;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.repository.LoginRepository;
import com.zee.zee5app.repository.UserRepository;
import com.zee.zee5app.utils.DBUtils;
import com.zee.zee5app.utils.PasswordUtils;

public class UserRepositoryImpl implements UserRepository {
	static private UserRepository repo = null;
	static private DBUtils dbutils = null;
	static private LoginRepository loginRepository = null;

	private UserRepositoryImpl() throws IOException {
		loginRepository = LoginRepositoryImpl.getInstance();
		dbutils = DBUtils.getInstance();
	}

	public static UserRepository getInstance() throws IOException {

		if (repo == null)
			repo = new UserRepositoryImpl();
		return repo;

	}

	@Override
	public String addUser(Register register) {

		Connection connection = dbutils.getConnection();
		String insertQuery = "insert into register" + "(regId, firstname, lastname, email, contactnumber, password)"
				+ "values(?,?,?,?,?,?)";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(insertQuery);
			prepStatement.setString(1, register.getId());
			prepStatement.setString(2, register.getFirstName());
			prepStatement.setString(3, register.getLastName());
			prepStatement.setString(4, register.getEmail());
			prepStatement.setBigDecimal(5, register.getContactNumber());
			String encryptPassword = PasswordUtils.generateSecurePassword(register.getPassword(),
					PasswordUtils.getSalt(30));
			prepStatement.setString(6, encryptPassword);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				Login login = new Login(register.getEmail(), encryptPassword, register.getId(),
						ROLE.values()[new Random().nextInt(2)]);
				return loginRepository.addCredentials(login);

			} else {
				connection.rollback();
				return "fail";
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public Optional<Register> getUserById(String id) throws InvalidNameException, IdNotFoundException,
			InvalidIdLengthException, InvalidEmailFormatException, InvalidPasswordException {

		Connection connection = dbutils.getConnection();

		String getQuery = "SELECT * FROM register where regId=?";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			prepStatement.setString(1, id);
			ResultSet result = prepStatement.executeQuery();

			if (result.next()) {
				Register register = new Register();
				register.setId(result.getString("regId"));
				register.setFirstName(result.getString("firstname"));
				register.setLastName(result.getString("lastname"));
				register.setEmail(result.getString("email"));
				register.setPassword(result.getString("password"));
				register.setContactNumber(result.getString("contactnumber"));
				return Optional.of(register);
			} else {
				throw new IdNotFoundException("Invalid Id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbutils.closeConnection(connection);
		}

		return Optional.empty();
	}

	@Override
	public Optional<List<Register>> getAllUsersList() throws InvalidNameException, IdNotFoundException,
			InvalidIdLengthException, InvalidEmailFormatException, InvalidPasswordException

	{
		List<Register> registers = new ArrayList<>();
		Connection connection = dbutils.getConnection();

		String getQuery = "SELECT * FROM register";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(getQuery);
			ResultSet result = prepStatement.executeQuery();

			while (result.next()) {
				Register register = new Register();
				register.setId(result.getString("regId"));
				register.setFirstName(result.getString("firstname"));
				register.setLastName(result.getString("lastname"));
				register.setEmail(result.getString("email"));
				register.setPassword(result.getString("password"));
				register.setContactNumber(result.getString("contactnumber"));
				registers.add(register);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbutils.closeConnection(connection);
		}

		return Optional.ofNullable(registers);
	}

	@Override
	public Register[] getAllUsers() throws InvalidNameException, IdNotFoundException, InvalidIdLengthException,
			InvalidEmailFormatException, InvalidPasswordException {
		Optional<List<Register>> registers = this.getAllUsersList();
		if (registers.isPresent())
			return registers.get().toArray(new Register[registers.get().size()]);
		return null;
	}

	@Override
	public String deleteUserById(String id) throws IdNotFoundException {
		Connection connection = dbutils.getConnection();
		String delQuery = "DELETE FROM register where regId=?";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(delQuery);
			prepStatement.setString(1, id);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
			} else {
				connection.rollback();
				throw new IdNotFoundException("Invalid Id");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

	@Override
	public String updateUserById(String id, Register register) throws IdNotFoundException {
		Connection connection = dbutils.getConnection();
		String updateQuery = "UPDATE register SET firstname=?, lastname=?, email=?, contactnumber=?, password=? WHERE regid=?";
		try {
			PreparedStatement prepStatement = connection.prepareStatement(updateQuery);
			prepStatement.setString(6, register.getId());
			prepStatement.setString(1, register.getFirstName());
			prepStatement.setString(2, register.getLastName());
			prepStatement.setString(3, register.getEmail());
			prepStatement.setBigDecimal(4, register.getContactNumber());
			String encryptPassword = PasswordUtils.generateSecurePassword(register.getPassword(),
					PasswordUtils.getSalt(30));
			prepStatement.setString(5, encryptPassword);
			int result = prepStatement.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";

			} else {
				connection.rollback();
				throw new IdNotFoundException("Invalid Id");
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			dbutils.closeConnection(connection);
		}
		return "fail";
	}

}