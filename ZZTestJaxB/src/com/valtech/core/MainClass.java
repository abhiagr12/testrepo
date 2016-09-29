package com.valtech.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.valtech.core.client.AccountServiceClient;


public class MainClass {
	public static void main(String[]a){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
		AccountServiceClient client = (AccountServiceClient)context.getBean("accountServiceClient");
		client.callService("1236547890","Abhilash A");
		context.close();
	}
}
