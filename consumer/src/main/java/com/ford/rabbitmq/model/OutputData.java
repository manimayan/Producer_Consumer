package com.ford.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "OPERATION_RESULT")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutputData implements Serializable {
	
	private static final String ADDITION = "+";

	 @Id
	 @GeneratedValue 
	 private long id;
	
	@Column(name = "FIRSTNUM")
	private int numberOne;
	
	@Column(name = "SECONDNUM")
	private int numberTwo;

	@Column(name = "RESULT")
	private int result;

	@Column(name = "OPERATION")
	private String operator;


	public OutputData(InputData message) {
		this.numberOne=message.getNumberOne();
		this.numberTwo=message.getNumberTwo();
		this.operator=message.getOperator();
		this.result=(ADDITION.equals(this.operator) ? message.getNumberOne()+message.getNumberTwo() 
		: message.getNumberOne()*message.getNumberTwo()) ;
	}
}
