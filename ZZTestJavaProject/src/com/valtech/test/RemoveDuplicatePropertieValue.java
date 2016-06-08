package com.valtech.test;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RemoveDuplicatePropertieValue {
	public static void main(String[] arg){
		try {
			FileWriter writer = new FileWriter("C:\\Users\\abhilash.agrawal\\Desktop\\base.properties");
			Map<String, String> map = new HashMap<String, String>();
			Scanner scan = new Scanner(new File("C:\\Users\\abhilash.agrawal\\Desktop\\base_en.properties"));
			while(scan.hasNextLine()) {
				String line = scan.nextLine().trim();
				String split[] = line.split("=");
				if(split.length>1){
					String key = split[0].trim();
					String value = split[1].trim();
					if(!map.containsKey(key)){
						map.put(key, value);
						writer.append(key +"="+ value+"\n");
					}else if(map.get(key).equals(value)){
						//
					}
					else{
						System.out.println(line);
					}
				}else{
					writer.append(line+"\n");
				}
			}
			scan.close();
		    writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
