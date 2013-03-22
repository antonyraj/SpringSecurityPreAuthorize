package com.poc.preauthorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
/**
 * 
 * @author anthony
 *
 */

@ContextConfiguration("classpath:META-INF/spring/applicationContext*.xml")
@Listeners(AuthenticationListener.class)
public class MyServiceTest extends AbstractTestNGSpringContextTests
{

	@Autowired
	private MyService myService;

	@Test
	@Authenticate(username = "anto", roles = {"ROLE_USER" })
	public void testCallHelloAsAdmin()
	{
		myService.hello();
	}

	@Test
	@Authenticate(username = "anto", roles = {"ROLE_USER" })
	public void testCallHelloAsUser()
	{
		myService.hello();
	}

	@Test
	@Authenticate(username = "hexagon", roles = {"ROLE_ADMIN" })
	public void testCallByeAsAdmin()
	{
		myService.bye();
	}

	@Test(expectedExceptions = AccessDeniedException.class)
	@Authenticate(username = "anto", roles = {"ROLE_USER" })
	public void testCallByeAsUser()
	{
		myService.bye();
	}
}
