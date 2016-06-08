package com.valtech.test;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;

public class ProductImage{
	
	public static void main(String[] abc){
		final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String dateSr="01-02-1985";
		try {
		Date date = formatter.parse(dateSr);
		System.out.println(date);
		}catch (final ParseException e){
			e.printStackTrace();
		}
		ProductImage.printImpex();
	}
	
	public static void printImpex(){
		Map<String, String> location = new HashMap<String, String>();
		location.put("Main", "http://content.etilize.com/images/");
		location.put("Front", "http://content.etilize.com/Front/");
		location.put("Left", "http://content.etilize.com/Left/");
		location.put("Rear", "http://content.etilize.com/Rear/");
		location.put("Bottom", "http://content.etilize.com/Bottom/");
		
		Map<String, String> format = new HashMap<String, String>();
		format.put("Thumbnails","50/50/");
		format.put("Main","285/230/");
		format.put("Zoom","300/300/");
		
		List<String> locationList = new ArrayList<String>();
		//locationList.add("http://content.etilize.com/images/");
		locationList.add("http://content.etilize.com/Front/");
		locationList.add("http://content.etilize.com/Left/");
		locationList.add("http://content.etilize.com/Rear/");
		locationList.add("http://content.etilize.com/Bottom/");
		
		List<String> formatList = new ArrayList<String>();
		formatList.add("50/50/");
		formatList.add("285/230/");
		formatList.add("");
		List<String> prodIdList = new ArrayList<String>();
		prodIdList.add("11389744");
		prodIdList.add("1020406189");
		for(String product : prodIdList){
			int i=0;
			for(String locationn:locationList){
				for(String formatt: formatList){
					if(formatt.isEmpty()){
						if(i==0){
							formatt = "FrontMaximum/";
						}else if(i==1){
							formatt = "LeftMaximum/";
						}else if(i==2){
							formatt = "BottomMaximum/";
						}else if(i==3){
							formatt = "RearMaximum/";
						}
					}
					System.out.println(locationn+formatt+product+".jpg");
				}
				i++;
			}
		}
	}
}