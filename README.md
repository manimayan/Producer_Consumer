## About The Project

#### The repository is a working prototype of modern day web application which leverages various tools and technologies such as, 

➲ [SpringBoot](https://spring.io/projects/spring-boot)
➲ [JPA](https://spring.io/projects/spring-data-jpa)
➲ [RabbitMQ](https://www.rabbitmq.com/)
➲ [Swagger API](https://swagger.io)
➲ [Config Server](https://dzone.com/articles/using-spring-config-server)
➲ [Circuit Breaker](https://spring.io/projects/spring-cloud-circuitbreaker)
➲ [Service Registry](https://spring.io/guides/gs/service-registration-and-discovery/)
➲ [Redis Cache](https://www.journaldev.com/18141/spring-boot-redis-cache) 
➲ [Sonar Lint](https://www.sonarlint.org/)


#### The prototype comprises of four spring boot application which serves the purpose of following use cases,

➲ Producer - The application acts as a producer, which fetches input from the user via restApi and queue it in RabbitMQ.

➲ Consumer - The application acts as a consumer, which fetches  data from RabbitMQ(queued by producer) and persists in the Database.

➲ Service Registry - The application demonstrates a Netflix Eureka service registry which enables client-side load-balancing and decouples service providers from consumers.

➲ Config Server - The application is a central place to manage external properties for all the above applications across all environments.

Both Producer and Consumer Applications are enabled with SwaggerAPI, Circuit breaker to handle API fall backs, REDIS Cache which enables in-memory data store for faster data retrieval. 

The below are the URL's involved in running the prototype,
```sh
Swagger 	 			- http://localhost:8081/swagger-ui.html
```
```sh
eureka-service-registry - http://localhost:8761/
```
```sh
rabbitMQ 	 			- http://localhost:15672/
```
```sh
config-server 			- http://localhost:8888/appname/environment
```
```sh
circuit breaker 		- http://localhost:8081/hystrix/
```

