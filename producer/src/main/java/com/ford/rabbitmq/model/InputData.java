package com.ford.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InputData {

	@JsonProperty("no1")
	private int numberOne;

	@JsonProperty("no2")
	private int numberTwo;
	
	@JsonProperty("op")
	private String operator;

}
