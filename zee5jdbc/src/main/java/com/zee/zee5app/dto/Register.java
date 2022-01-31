package com.zee.zee5app.dto;

import java.math.BigDecimal;

import javax.naming.InvalidNameException;

import com.zee.zee5app.exception.InvalidEmailFormatException;
import com.zee.zee5app.exception.InvalidIdLengthException;
import com.zee.zee5app.exception.InvalidPasswordException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Setter
@ToString
public class Register implements Comparable<Register> {
	public Register(String id, String firstName, String lastName, String email, String contactNumber, String password)
			throws InvalidNameException, InvalidIdLengthException, InvalidEmailFormatException,
			InvalidPasswordException {
		this.setId(id);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setContactNumber(contactNumber);
	}

	private String id;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private BigDecimal contactNumber;

	public void setId(String id) throws InvalidIdLengthException {
		if (id.length() < 6)
			throw new InvalidIdLengthException("Id length < 6.");

		this.id = id;
	}

	public void setFirstName(String firstName) throws InvalidNameException {
		if ((firstName == null) || (firstName == "") || (firstName.length() < 2))
			throw new InvalidNameException("Invalid First Name!");
		this.firstName = firstName;
	}

	public void setLastName(String lastName) throws InvalidNameException {
		this.lastName = lastName;
	}

	public void setEmail(String email) throws InvalidEmailFormatException {
		if ((email == null) || (!email.contains("@")) || (!email.contains(".") || (email.length() < 5)))
			throw new InvalidEmailFormatException("Invalid Email Format");
		this.email = email;
	}

	public void setPassword(String password) throws InvalidPasswordException {
		if ((password == null) || (password.length() < 8))
			throw new InvalidPasswordException("Invalid Password");
		this.password = password;
	}

	@Override
	public int compareTo(Register obj) {
		return this.id.compareTo(obj.id);
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = new BigDecimal(contactNumber);
	}
}
