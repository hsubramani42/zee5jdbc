package com.zee.zee5app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
	private String userName;
	private String password;
	private String regID;
}
