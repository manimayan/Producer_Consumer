package com.ford.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Value("${rabbitmq.addqueue}")
	String addQueue;

	@Value("${rabbitmq.mulqueue}")
	String mulQueue;

	@Value("${rabbitmq.username}")
	String username;

	@Value("${rabbitmq.password}")
	private String password;

	@Value("${rabbitmq.exchange}")
	String exchangeName;

	@Value("${rabbitmq.addroutingkey}")
	private String addRoutingKey;

	@Value("${rabbitmq.mulroutingkey}")
	private String mulRoutingKey;

	@Bean(name = "addition")
	Binding bindingAdd() {
		return BindingBuilder.bind(new Queue(addQueue)).to(new DirectExchange(exchangeName)).with(addRoutingKey);
	}

	@Bean(name = "multiplication")
	Binding bindingMul() {
		return BindingBuilder.bind(new Queue(mulQueue)).to(new DirectExchange(exchangeName)).with(mulRoutingKey);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}

	@Bean
	public SimpleRabbitListenerContainerFactory listenerFactory(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setMessageConverter(jsonMessageConverter());
		return factory;
	}
}
