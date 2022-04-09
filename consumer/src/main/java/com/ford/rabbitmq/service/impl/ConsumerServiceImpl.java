package com.ford.rabbitmq.service.impl;

import com.ford.rabbitmq.model.InputData;
import com.ford.rabbitmq.model.OutputData;
import com.ford.rabbitmq.model.Response;
import com.ford.rabbitmq.repository.ConsumerRepository;
import com.ford.rabbitmq.service.ConsumerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
public class ConsumerServiceImpl implements ConsumerService {

	@Value("${rabbitmq.addqueue}")
	String addQueue;

	@Value("${rabbitmq.mulqueue}")
	String mulQueue;

	@Autowired
	ConsumerRepository repository;

	@RabbitListener(queues = "${rabbitmq.addqueue}" , containerFactory="listenerFactory")
	public void receiveAdd(InputData message) {
		OutputData output = new OutputData(message);
		repository.saveOperation(output);
	}

	@RabbitListener(queues = "${rabbitmq.mulqueue}", containerFactory="listenerFactory")
	public void receiveMul(InputData message) {
		OutputData output = new OutputData(message);
		repository.saveOperation(output);
	}

	@Override
	public Response getAllData() {
		Response result = new Response();
		List<OutputData> output = repository.getAllData();
		if(!CollectionUtils.isEmpty(output)) {
			result.setOutputData(output);
			result.setStatus(true);
			result.setMessage("fetched successfully");
			log.info("fetched successfully");
		}else {
			result.setStatus(false);
			result.setMessage("fetching failed");
			log.info("fetching failed");
		}
		return result;
	}

	@Override
	public Response getAllDataDB() {
		Response result = new Response();
		List<OutputData> output = repository.getAllDataDB();
		if(!CollectionUtils.isEmpty(output)) {
			result.setOutputData(output);
			result.setStatus(true);
			result.setMessage("fetched successfully");
			log.info("fetched successfully");
		}else {
			result.setStatus(false);
			result.setMessage("fetching failed");
			log.info("fetching failed");
		}
		return result;
	}

	@Override
	public Response getDataByid(int id) {
		Response result = new Response();
		OutputData output = repository.getDataByid(id);
		if(null!=output) {
			result.setOuptut(output);
			result.setStatus(true);
			result.setMessage("fetched successfully");
			log.info("fetched data for Id: {} successfully",id);
		}else {
			result.setStatus(false);
			result.setMessage("fetching failed");
			log.info("fetching failed");
		}
		return result;
	}

	@Override
	public Response updateData(OutputData input) {
		Response result = new Response();
		OutputData queryOutput = repository.updateData(input);
		if(null!=queryOutput) {
			result.setStatus(true);
			result.setMessage("Id:"+input.getId()+" updated successfully");
			log.info("Updated Id: {} successfully",input.getId());
		}else {
			result.setStatus(false);
			result.setMessage("update failed");
			log.info("update failed");
		}
		return result;
	}

	@Override
	public Response deleteData(int id) {
		Response result = new Response();
		Boolean queryOutput = repository.deleteData(id);
		if(queryOutput) {
			result.setStatus(true);
			result.setMessage("Id:"+id+" deleted successfully");
			log.info("Deleted Id: {} successfully",id);
		}else {
			result.setStatus(false);
			result.setMessage("Deletion failed");
			log.info("Deletion failed");
		}
		return result;
	}
}
