package com.ford.rabbitmq.repository;

import com.ford.rabbitmq.model.OutputData;

import java.util.List;


public interface ConsumerRepository {

	void saveOperation(OutputData output);

	List<OutputData> getAllData();

	OutputData getDataByid(int id);

	OutputData updateData(OutputData input);

	Boolean deleteData(int id);

	List<OutputData> getAllDataDB();
}
