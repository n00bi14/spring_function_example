package de.lise.blog.functions.HelloWorld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.function.Function;

@SpringBootApplication
//@EnableKafka
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

	@Bean
	public Function<Request, Greeting> hello()
	{
		return user -> new Greeting( user.getName() );
	}

	@Bean
	public Function<Request, Void> a() {
		return request -> null;
	}

//	@Bean
//	Function<Request, Greeting> helloAllListener( KafkaTemplate<String, Greeting> kafkaTemplate){
//		return user -> {
//			Greeting greeting = new Greeting( user.getName() );
//			kafkaTemplate.send( "greeting-message", greeting );
//			return greeting;
//		};
//	}
}
