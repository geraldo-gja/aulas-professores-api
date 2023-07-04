package com.unitech.entity;

import lombok.Data;


@Data
public abstract class Usuario {

	private Long id;
	
	private String login; //email
	
	private String password;
	
}
