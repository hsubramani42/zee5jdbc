package com.zee.zee5app.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.enums.ROLE;
import com.zee.zee5app.repository.LoginRepository;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

	@Autowired
	DataSource dataSource;

	@Override
	public String addCredentials(Login login) {

		Connection connection = null;
		PreparedStatement ps = null;
		try {
			connection = dataSource.getConnection();
			String insertQuery = "insert into login (Username, password, regId, role)" + "values(?,?,?,?)";
			ps = connection.prepareStatement(insertQuery);
			ps.setString(1, login.getUserName());
			ps.setString(2, login.getPassword());
			ps.setString(3, login.getRegID());
			ps.setString(4, login.getRole().toString());
			System.out.println("reg hash: " + connection.hashCode());
			int result = ps.executeUpdate();
			if (result > 0) {
				connection.commit();
				ps.close();
				return "success";
			} else {
				connection.rollback();
				ps.close();
				return "fail";
			}

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String deleteCredentials(String userName) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
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
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return "fail";
	}

	@Override
	public String changePassword(String userName, String password) {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
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
		}
		return "fail";
	}

	@Override
	public String changeRole(String userName, ROLE role) {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

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
		}
		return "fail";
	}

}
