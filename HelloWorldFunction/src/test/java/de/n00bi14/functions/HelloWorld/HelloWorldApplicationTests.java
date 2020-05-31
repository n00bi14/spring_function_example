package de.lise.blog.functions.HelloWorld;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HelloWorldApplicationTests {
	@Test
	void contextLoads() {
	}

	@Test
	public void test()
	{
		Greeting result = new HelloWorldApplication().hello().apply(new Request("foo"));
		assertThat(result.getMessage()).isEqualTo("Hello, foo");
	}

	@Test
	public void start() {
		AzureSpringBootRequestHandler<Request, Greeting> handler = new AzureSpringBootRequestHandler<>(HelloWorldApplication.class);
		Greeting result = handler.handleRequest(new Request("foo"), null);
		handler.close();
		assertThat(result.getMessage()).isEqualTo("Hello, foo");
	}
}
