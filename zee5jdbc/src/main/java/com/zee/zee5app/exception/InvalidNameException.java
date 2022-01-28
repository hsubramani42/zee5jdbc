package com.zee.zee5app.exception;

import lombok.ToString;

@ToString(callSuper = true)
public class InvalidNameException extends Exception {
	public InvalidNameException(String message) {
		super(message);
	}
}
