package com.valtech.test;


public class StringTest {
	
	public static void main(String[] args) {
		String messageBody = "CAS-S400";
		//String messageBody ="sasas";
		
		for(String str :messageBody.split("\\s+")){
			System.out.println(str+":");
		}
		
	}
	
	
}