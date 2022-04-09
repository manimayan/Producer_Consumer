package com.ford.rabbitmq.application.testcontroller;

import com.ford.rabbitmq.application.util.ConsumerTestData;
import com.ford.rabbitmq.controller.ConsumerController;
import com.ford.rabbitmq.service.ConsumerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@EnableWebMvc
public class TestConsumerController {

	@InjectMocks
	private ConsumerController consumerController;

	@Mock
	private ConsumerService consumerService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(consumerController).dispatchOptions(true).build();
	}

	@Test
	public void  testGetAllData() throws Exception {
      when(consumerService.getAllData()).thenReturn(ConsumerTestData.getResponse());
		mockMvc.perform(get("/consume/cache-all-entries")).andExpect(status().isOk());
		verify(consumerService, times(1)).getAllData();
	}

	@Test
	public void  testGetAllDataFail() throws Exception {
		doThrow(new RuntimeException()).when(consumerService).getAllData();
		mockMvc.perform(get("/consume/cache-all-entries")).andExpect(status().isInternalServerError());
		verify(consumerService, times(1)).getAllData();
	}

/*	@Test
	public void testGetDataById() throws Exception {
		int id=34;
		when(consumerService.getDataByid(id)).thenReturn(ConsumerTestData.getResponse());
		mockMvc.perform(get("/consume/id/id="+id)).andExpect(status().isOk());
		verify(consumerService, times(1)).getDataByid(id);
	}*/
}
