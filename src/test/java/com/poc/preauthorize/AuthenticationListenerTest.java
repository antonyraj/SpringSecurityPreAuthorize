package com.poc.preauthorize;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


/**
 * @author anthony
 */
@Listeners(AuthenticationListener.class)
public class AuthenticationListenerTest
{

	@Test
	public void testNotAuthenticatedMethod()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.assertNull(authentication);
	}

	@Test
	@Authenticate(username = "pippo", roles = "ROLE_USER")
	public void testAuthenticatedMethod()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.assertNotNull(authentication);
		Assert.assertEquals(authentication.getAuthorities().size(), 1);
		Assert.assertEquals(authentication.getAuthorities().iterator().next().getAuthority(), "ROLE_USER");
	}

	@Test
	@Authenticate(username = "raj", roles = {"ROLE_USER", "ROLE_ADMIN" })
	public void testAuthenticatedMethodWithManyRoles()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.assertNotNull(authentication);
		Assert.assertEquals(authentication.getAuthorities().size(), 2);
		List<String> roles = new ArrayList<String>();
		for (GrantedAuthority auth : authentication.getAuthorities())
		{
			roles.add(auth.getAuthority());
		}
		Assert.assertTrue(roles.contains("ROLE_USER"));
		Assert.assertTrue(roles.contains("ROLE_ADMIN"));
	}

	@Test
	@Authenticate(username = "anto", roles = {"ROLE_USER", "test" })
	public void testAuthenticatedMethodWithDuplicateRoles()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.assertNotNull(authentication);
		Assert.assertEquals(authentication.getAuthorities().size(), 2);
		Assert.assertEquals(authentication.getAuthorities().iterator().next().getAuthority(), "ROLE_USER");
	}

	@Test
	@Authenticate(username = "pippo")
	public void testMissingRoles()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.assertNotNull(authentication);
		Assert.assertTrue(authentication.getAuthorities().isEmpty());
	}

	@Test
	@Authenticate(roles = {"ROLE_USER" })
	public void testMissingUsername()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Assert.assertNotNull(authentication);
		Assert.assertEquals(authentication.getPrincipal(), AuthenticationListener.DEFAULT_USERNAME);
	}

}
