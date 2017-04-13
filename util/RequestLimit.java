package com.wemeow.web.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
//最高优先级
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
	
	String value() default ADMIN_PRIVATE;
	
	
	String ADMIN_PRIVATE = "admin_private";
	
	String ADMIN_PUBLIC = "admin_public";
	
	String USER_PRIVATE = "user_private";
	
	String USER_PUBLIC = "user_public";
	
}
