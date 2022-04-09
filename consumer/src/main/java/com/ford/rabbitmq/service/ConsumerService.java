package com.ford.rabbitmq.service;

import com.ford.rabbitmq.model.OutputData;
import com.ford.rabbitmq.model.Response;

public interface ConsumerService {

	Response getAllData();

	Response getDataByid(int id);

	Response updateData(OutputData input);

	Response deleteData(int id);

	Response getAllDataDB();
}
