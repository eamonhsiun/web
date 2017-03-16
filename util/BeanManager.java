package com.wemeow.web.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class BeanManager {
	private BeanManager() {}
	private static ApplicationContext ctx;
	
	public static void init(){
		ctx = new ClassPathXmlApplicationContext("spring-config.xml");
//		String[] s=ctx.getBeanDefinitionNames();
//		System.err.println(s.length);
//		for(String si:s){
//			System.err.println(si);
//		}
		
	}
	
	public static Object getBean(String name){
		return ctx.getBean(name);
	}
	

}
