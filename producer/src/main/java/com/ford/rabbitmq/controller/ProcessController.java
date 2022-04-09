package com.ford.rabbitmq.controller;

import com.ford.rabbitmq.model.InputData;
import com.ford.rabbitmq.model.Response;
import com.ford.rabbitmq.service.ProcessService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RefreshScope
@RequestMapping("/produce")
@Slf4j
public class ProcessController {

	@Autowired
	ProcessService service;

	@Value("${rabbitmq.refresh}")
	String refresh;

	
	@ApiOperation(value = "post arithemetic data to queue", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "data posted successfully"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
	
	@PostMapping(value = "/post-data", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> postToQueue(@RequestBody InputData input) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Response data = new Response();
		try {
			data = service.postToQueue(input);
			if(data.isStatus()) {
				status = HttpStatus.OK;			
			}
		} catch (Exception e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			log.error("Error in processing",e);
		}
		return new ResponseEntity<>(data, status);
	}

	@ApiOperation(value = "Test cloud config refresh scope", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Property value updated"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })

	@GetMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> refresh() {
		HttpStatus status = HttpStatus.OK;
		Response data = new Response();
		data.setRefresh(refresh);
		return new ResponseEntity<>(data, status);
	}

}
