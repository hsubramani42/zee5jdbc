package com.zee.zee5app;

import javax.naming.InvalidNameException;

import com.zee.zee5app.dto.Login;
import com.zee.zee5app.dto.Register;
import com.zee.zee5app.exception.IdNotFoundException;
import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;
import com.zee.zee5app.service.impl.UserServiceimpl;

public class Main {

	public static void main(String[] args) {
		Register register = null;
		try {
			register = new Register("000001", "Subramani", "Harisankar", "hsubramani42@gmail.com", "password");
			System.out.println("Register Object: " + register);
		} catch (InvalidIdLengthException e) {
			e.printStackTrace();
		} catch (InvalidNameException e) {
			e.printStackTrace();
		} catch (InvalidEmailFormatException e) {
			e.printStackTrace();
		} catch (InvalidPasswordException e) {
			e.printStackTrace();
		}

		Login login = new Login();

		login.setUserName("hsubramani42@gmail.com");
		login.setPassword("hsubramani42");

		System.out.println("Login Object: " + login);

		UserServiceimpl userService = UserServiceimpl.getInstance();
		int i = 1;
		for (; i <= 10; i++) {
			register = new Register();
			String result = null;
			try {
				register = new Register("00000" + i, "User", "__" + i, "user-" + i + "@gmail.com", "pass@123@" + i);
				// System.out.println("Register Object: " + register);
				result = userService.addUser(register);
			} catch (InvalidIdLengthException e) {
				e.printStackTrace();
			} catch (InvalidNameException e) {
				e.printStackTrace();
			} catch (InvalidEmailFormatException e) {
				e.printStackTrace();
			} catch (InvalidPasswordException e) {
				e.printStackTrace();
			}

			System.out.println("\nAdded User: " + i + "\nStatus: " + result);

		}
		String uid = "00000" + 10;

//		for (Register user : userService.getAllUsers()) {
//			System.out.println("\nUser Object: " + user);
//		}

		try {
			System.out.println("\nCheck User: " + uid + "\nStatus: " + userService.getUserById(uid).isPresent());
		} catch (IdNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			System.out.println("\nDelete User id: " + uid + "\nStatus: " + userService.deleteUserById(uid));
		} catch (IdNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("\nCheck User: " + uid);

		try {
			System.out.println("Status: " + userService.getUserById(uid + "100").isPresent());
		} catch (IdNotFoundException e1) {

			e1.printStackTrace();
		}

		i--;
		String mid = "00000" + 9;
		Register modify;
		try {
			i = 10;
			modify = new Register("00000" + i, "User", "__" + i, "user-" + i + "@gmail.com", "pass@123@" + i);
			System.out.println("\nModify User: " + mid + "\nStatus: " + (userService.updateUserById(mid, modify)));

		} catch (InvalidNameException | InvalidIdLengthException | InvalidEmailFormatException
				| InvalidPasswordException | IdNotFoundException e) {
			e.printStackTrace();
		}
		;

		System.out.println("All registrations:");
		for (Register user : userService.getAllUsers()) {
			System.out.println("\nUser Object: " + user);
		}
		System.out.println("All registrations list:");
		userService.getAllUsersList().forEach(e -> System.out.println(e));
	}

}