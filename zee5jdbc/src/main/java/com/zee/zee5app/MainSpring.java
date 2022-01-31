package com.zee.zee5app;

import javax.naming.InvalidNameException;
import javax.sql.DataSource;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.zee.zee5app.config.Config;
import com.zee.zee5app.dto.Register;
import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.repository.UserRepository;
import com.zee.zee5app.repository.impl.UserRepositoryImpl;

public class MainSpring {
	public static void main(String[] args) {
		AbstractApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

		UserRepository ur1 = applicationContext.getBean(UserRepositoryImpl.class);
		System.out.println("HashCode: " + ur1.hashCode());

		UserRepository ur2 = applicationContext.getBean(UserRepositoryImpl.class);
		System.out.println("HashCode: " + ur2.hashCode());

		 DataSource dataSource=applicationContext.getBean("dataSource", DataSource.class);
		 
		 
		 System.out.println(dataSource!=null);
		 
		 try {
			System.out.println(ur1.addUser(new Register("ZEE0000036", "Ramesh", "Mahendran", "rames1h@gmail.com", "9275674829",
						"ramesh@2000")));
		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 //dataSource.getConnection().close();
		 
		 
		
		applicationContext.close();
	}
}
