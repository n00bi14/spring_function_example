package de.lise.blog.functions.HelloWorld;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.cloud.function.adapter.azure.AzureSpringBootRequestHandler;

import java.util.Optional;

public class HelloHandler extends AzureSpringBootRequestHandler<Request, Greeting>
{

	@FunctionName("hello")
	public Greeting execute(
			@HttpTrigger(name = "request", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<Request>> request,
			ExecutionContext context) {

		context.getLogger().info("Greeting user name: " + request.getBody().get().getName());
		return handleRequest( request.getBody().get(), context);
	}
}
