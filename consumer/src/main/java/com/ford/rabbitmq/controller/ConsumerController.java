package com.ford.rabbitmq.controller;

import com.ford.rabbitmq.model.OutputData;
import com.ford.rabbitmq.model.Response;
import com.ford.rabbitmq.service.ConsumerService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RefreshScope
@RestController
@RequestMapping("/consume")
@Slf4j
public class ConsumerController {

	@Autowired
	ConsumerService service;

	@GetMapping(value = "/cache-all-entries", produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod="recovery")
	public ResponseEntity<Response> getAllData() {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Response data = new Response();
		try {
			data = service.getAllData();
			if(data.isStatus()) {
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in processing",e);
		}
		return new ResponseEntity<>(data, status);
	}

	@GetMapping(value = "/db-all-entries", produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod="recovery")
	public ResponseEntity<Response> getAllDataDB() {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Response data = new Response();
		try {
			data = service.getAllDataDB();
			if(data.isStatus()) {
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in processing",e);
		}
		return new ResponseEntity<>(data, status);
	}

	@GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod="recoveryget")
	public ResponseEntity<Response> getDataByid(@PathVariable int id){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Response data = new Response();
		try {
			data = service.getDataByid(id);
			if(data.isStatus()) {
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in processing",e);
		}
		return new ResponseEntity<>(data, status);
	}

	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateData(@RequestBody OutputData input) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Response data = new Response();
		try {
			data = service.updateData(input);
			if(data.isStatus()) {
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in processing",e);
		}
		return new ResponseEntity<>(data, status);
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteData(@PathVariable int id) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Response data = new Response();
		try {
			data = service.deleteData(id);
			if(data.isStatus()) {
				status = HttpStatus.OK;
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in processing",e);
		}
		return new ResponseEntity<>(data, status);
	}

	public ResponseEntity<Response> recovery() {
		Response data = new Response();
		HttpStatus status = HttpStatus.ACCEPTED;
		data.setMessage("DB connection is halted");
		return new ResponseEntity<>(data, status);
	}

	public ResponseEntity<Response> recoveryget(int id) {
		Response data = new Response();
		HttpStatus status = HttpStatus.ACCEPTED;
		data.setMessage("DB connection is halted");
		return new ResponseEntity<>(data, status);
	}
}
