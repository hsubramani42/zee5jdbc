package com.zee.zee5app.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.enums.ROLE;
import com.zee.zee5app.repository.LoginRepository;
import com.zee.zee5app.utils.DBUtils;

public class LoginRepositoryImpl implements LoginRepository {

	private DBUtils dbutils = null;
	private static LoginRepository loginRepository = null;

	private LoginRepositoryImpl() throws IOException {
		dbutils = DBUtils.getInstance();
	}

	public static LoginRepository getInstance() throws IOException {
		if (loginRepository == null)
			loginRepository = new LoginRepositoryImpl();
		return loginRepository;
	}

	@Override
	public String addCredentials(Login login) {
		Connection connection = dbutils.getConnection();
		String insertQuery = "insert into login (Username, password, regId, role)" + "values(?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(insertQuery);
			ps.setString(1, login.getUserName());
			ps.setString(2, login.getPassword());
			ps.setString(3, login.getRegID());
			ps.setString(4, login.getRole().toString());
			int result = ps.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
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
	public String deleteCredentials(String userName) {
		Connection connection = dbutils.getConnection();
		String delQuery = "DELETE FROM login where username=?";
		try {
			PreparedStatement ps = connection.prepareStatement(delQuery);
			ps.setString(1, userName);
			int result = ps.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
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
	public String changePassword(String userName, String password) {

		Connection connection = dbutils.getConnection();
		String updateQuery = "UPDATE login SET password=? WHERE username=?";
		try {
			PreparedStatement ps = connection.prepareStatement(updateQuery);
			ps.setString(1, password);
			ps.setString(2, userName);
			int result = ps.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
			}
			return "fail";

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
	public String changeRole(String userName, ROLE role) {

		Connection connection = dbutils.getConnection();

		String updateQuery = "UPDATE login SET role=? WHERE username=?";
		try {
			PreparedStatement ps = connection.prepareStatement(updateQuery);
			ps.setString(1, role.toString());
			ps.setString(2, userName);
			int result = ps.executeUpdate();
			if (result > 0) {
				connection.commit();
				return "success";
			}
			return "fail";

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
