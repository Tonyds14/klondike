package com.svi.process;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ReadInputTextFile {
	static String[] inputCardList =  new String[52];
	
	public static String[] ReadInputText() {
		
		ArrayList<String> tokenList = new ArrayList<>();
		
		String filePath = "input/input.txt"; 
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String line;
	        while ((line = br.readLine()) != null) {
	        	StringTokenizer tokenizer = new StringTokenizer(line, ",");
				while (tokenizer.hasMoreTokens()) {
					String token = tokenizer.nextToken();
					tokenList.add(token);
				}
			inputCardList = tokenList.toArray(new String[0]);
	        }
		 }catch (IOException e) {
	            e.printStackTrace();
		 }
		 
		System.out.println("ReadInputTextFile: inputCardList");
		String result = String.join(", ", inputCardList);
        System.out.println(result);
        
		return inputCardList;
		 
	}

}
	
