package com.poc.preauthorize;

import org.springframework.security.access.prepost.PreAuthorize;


/**
 * @author anthony
 */
public interface MyService
{

	@PreAuthorize("hasRole('ROLE_USER') OR hasRole('ROLE_ADMIN')")
	void hello();

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void bye();

}
