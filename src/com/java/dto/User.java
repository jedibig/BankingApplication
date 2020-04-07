package com.java.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class User {
	private String username;
	private String password;
	private String name;
	private int accNum;
//	public void setUsername(String username) {
//		//TODO: validations on server side
//		//check if the username is invalid: unique && length>6.
//	}

}
