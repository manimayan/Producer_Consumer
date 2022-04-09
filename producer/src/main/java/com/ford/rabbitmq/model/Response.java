package com.ford.rabbitmq.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {

	private boolean status; 

	private String message;
	
	private String refresh;

}
