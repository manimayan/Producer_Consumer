package com.ford.rabbitmq.service;

import com.ford.rabbitmq.model.InputData;
import com.ford.rabbitmq.model.Response;

public interface ProcessService {

	Response postToQueue(InputData input);

}
