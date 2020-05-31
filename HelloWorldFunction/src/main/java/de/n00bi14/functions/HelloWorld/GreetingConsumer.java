package de.lise.blog.functions.HelloWorld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

//@Component
public class GreetingConsumer
{
	private final ObjectMapper objectMapper;


	public GreetingConsumer( ObjectMapper objectMapper )
	{
		this.objectMapper = objectMapper;
	}


	@KafkaListener(
			topics = {"greeting-message"},
			groupId = "simple-greeting-consumer")
	public void processGreeting( String object, MessageHeaders headers) throws JsonProcessingException
	{
		Greeting greeting = objectMapper.readValue( object, Greeting.class );
		System.out.println( greeting.getMessage() );
		headers.keySet().forEach( key ->  {
			System.out.println( "\t" + headers.get( key ) );
		});
	}
}
