package com.poc.preauthorize;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation for secured method testing. Use with {@link AuthenticationListener}.
 * 
 * @author anthony
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Authenticate {

	String username() default "";

	String[] roles() default {};
}
