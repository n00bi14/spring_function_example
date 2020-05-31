package de.lise.blog.functions.HelloWorld;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;

//@Configuration
public class KafkaProducerConfiguration
{

	@Bean
	public ProducerFactory<String, Greeting> producerFactory()
	{
		HashMap<String, Object> configProperties = new HashMap<>();
		configProperties.put( ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092" );
		configProperties.put( ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class );
		configProperties.put( ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class );

		return new DefaultKafkaProducerFactory(configProperties);
	}

	@Bean
	public KafkaTemplate<String, Greeting> kafkaTemplate(ProducerFactory<String, Greeting> producerFactory)
	{
		return new KafkaTemplate<>( producerFactory );
	}

	@Bean
	public NewTopic adviceTopic()
	{
		return new NewTopic( "simple-greeting-consumer", 3, (short)1 );
	}
}
