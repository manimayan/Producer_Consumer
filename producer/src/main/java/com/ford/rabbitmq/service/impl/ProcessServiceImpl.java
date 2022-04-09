package com.ford.rabbitmq.service.impl;

import com.ford.rabbitmq.model.InputData;
import com.ford.rabbitmq.model.Response;
import com.ford.rabbitmq.service.ProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProcessServiceImpl implements ProcessService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	@Qualifier("addition")
	Binding bindingAdd;

	@Autowired
	@Qualifier("multiplication")
	Binding bindingMul;

	@Value("${rabbitmq.addqueue}")
	String addQueue;

	@Value("${rabbitmq.mulqueue}")
	String mulQueue;

	private static final String ADDITION = "+";

	private static final String MULTIPLICATION = "*";

	@Override
	public Response postToQueue(InputData input) {
		Response data = new Response();
		try {
			if(ADDITION.equals(input.getOperator())) {
				rabbitTemplate.convertAndSend(bindingAdd.getExchange(), bindingAdd.getRoutingKey(), input);
				data.setStatus(true);
				data.setMessage("Data posted sucessfully to Add queue");
				log.info("Data posted sucessfully");
			} else if(MULTIPLICATION.equals(input.getOperator())) {
				rabbitTemplate.convertAndSend(bindingMul.getExchange(), bindingMul.getRoutingKey(), input);
				data.setStatus(true);
				data.setMessage("Data posted successfully to multiplication queue");
				log.info("Data posted successfully");
			} else {
				data.setStatus(true);
				data.setMessage("Invalid identifier");
				log.info("Invalid identifier");
			}
		} catch( AmqpException ae) {
			data.setMessage("exception while executing AMQP operations");
			log.info("exception while executing AMQP operations");
		}
		return data;
	}

//	@RabbitListener(queues = "${rabbitmq.addqueue}" , containerFactory="listenerFactory")
//	public void receiveAdd(InputData message) {
//		System.out.println(message.getOperator());
//	}
//
//	@RabbitListener(queues = "${rabbitmq.mulqueue}", containerFactory="listenerFactory")
//	public void receiveMul(InputData message) {
//		System.out.println(message.getOperator());
//	}

}
