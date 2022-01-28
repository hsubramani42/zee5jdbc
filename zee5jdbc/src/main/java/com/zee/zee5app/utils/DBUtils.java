package com.zee.zee5app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
	public static DBUtils dbutils = null;
	static Properties properties = null;

	Connection connection = null;

	private DBUtils() throws IOException {
		properties = this.loadProperties();
	}

	public static DBUtils getInstance() throws IOException {
		if (dbutils == null)
			dbutils = new DBUtils();
		return dbutils;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(properties.getProperty("jdbc.url"),
						properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
				connection.setAutoCommit(false);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public Properties loadProperties() throws IOException {
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

}
