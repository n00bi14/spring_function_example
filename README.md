## Spring Cloud Function
The aim of _Spring Cloud Function_ is the possibility to write business locig once. Additionaly with the automatic configuration of _Spring Boot_ or whose dependency injection or metrics. 

The implemented function is directly available as a HTTP-Endpoint, a stream processor or a running task. You can execute the function instant local or deploy it to AWS or Azure by Maven. But always you have to write the code once!

You have the ability to code reacitve during the Flux library of the Reactor project. By the Java streaming API you will be able to write normal imperative code too. So a hybrid of both is also possible.

In this code example you will see the possibility to send and receive messages to a broker like Apache Kafka.


Here you can see a runnable programm which expect a name and returns a greeting.
```java
@SpringBootApplication
public class HelloWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWorldApplication.class, args);
	}

	@Bean
	public Function<Request, Greeting> hello() {
		return user -> new Greeting(user.getName()) ;
	}
}
```

This bean can be injected by the spring context. But it also creates a HTTP-Endpoint. In a distributed software system, these constructs can be connected with a _Spring Cloud API Gateway_ instantly.

Call the local endpoint with _curl_.
```sh
curl --request POST \
  --url http://localhost:8080/hello \
  --header 'content-type: application/json' \
  --data '{
	"name": "World!"
}'
```

Inject the function bean like other components.
```java
@Service
public class TransformService {
	private final Function<A, B> transformToInquiryPosition;

	public InquiryService(Function<A, B> transformToInquiryPosition){
		this.transformToInquiryPosition = transformToInquiryPosition;
	}

	public List<B> createInquiryPositions(List<A> basketCatalogItems){
		return basketCatalogItems
				.stream()
				.map(transformToInquiryPosition)
				.collect(Collectors.toList());
	}
}
```

These functions are easy testable.
```java
@SpringBootTest
class HelloWorldApplicationTests {
	@Test
	void contextLoads() { // <1>
	}

	@Test
	public void test() { // <2>
		Greeting result = new HelloWorldApplication().hello().apply(new Request("foo"));
		assertThat(result.getMessage()).isEqualTo("Hello, foo");
	}
}
```
<1> Test whether the spring context starts correctly

<2> Executes the function without the spring context


## Cloud Adapter
The public cloud provider AWS and Azure provide handler libraries to deploy these functions as a serverless function. The handler wrap our code with a trigger mechanism. Like an HTTP or a queue triger.

A handler for a HTTP-Trigger could be implemented like this.
```java
public class HelloHandler extends AzureSpringBootRequestHandler<Request, Greeting> {
	@FunctionName("hello") // <1>
	public Greeting execute(
			@HttpTrigger(name = "request", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<Request>> request,
			ExecutionContext context) {

		context.getLogger().info("Greeting user name: " + request.getBody().get().getName());
		return handleRequest( request.getBody().get(), context); // <2>
	}
}
```
<1> The beans names are like the method names

<2> Calls the function, described in (1)

Dieser Wrapper kann durch ein Äquivalent von AWS ausgetauscht werden ohne die eigentliche Logik der Anwendung zu berühren.

## Manually deployment
You can deploy yout function with a configured maven plugin.

`mvn clean package azure-functions:deploy`

Important is to know that the namespace always exists!