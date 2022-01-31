package com.zee.zee5app.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.zee.zee5app")
@PropertySource("classpath:application.properties")
public class Config {

	@Autowired
	Environment env;

	@Bean
	public DataSource dataSource() {
		System.out.println(env.getProperty("jdbc.url"));
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUsername(env.getProperty("jdbc.username"));
		basicDataSource.setPassword(env.getProperty("jdbc.password"));
		basicDataSource.setUrl(env.getProperty("jdbc.url"));
		basicDataSource.setAutoCommitOnReturn(false);
		basicDataSource.setDefaultAutoCommit(false);
		return basicDataSource;
	}

}
