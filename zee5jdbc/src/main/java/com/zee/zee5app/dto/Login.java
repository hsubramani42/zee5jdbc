package com.zee.zee5app.dto;

import com.zee.zee5app.dto.enums.ROLE;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
	private String userName;
	private String password;
	private String regID;
	private ROLE role;
}
