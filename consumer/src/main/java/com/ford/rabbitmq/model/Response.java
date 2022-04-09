package com.ford.rabbitmq.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include. NON_EMPTY)
public class Response  implements Serializable {

	private boolean status; 

	private String message;
	
	private List<OutputData> outputData = new ArrayList<>();
	
	private String refresh;

	public void setOuptut(OutputData output) {
		this.outputData.add(output);
	}
}
