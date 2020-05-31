package de.lise.blog.functions.HelloWorld;

public class Greeting
{
	private String message;


	public Greeting()
	{
	}


	public Greeting( String message )
	{
		this.message = "Hello, " + message;
	}


	public String getMessage()
	{
		return message;
	}
}
