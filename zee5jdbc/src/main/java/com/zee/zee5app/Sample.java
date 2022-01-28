package com.zee.zee5app;

import java.util.ArrayList;
import java.util.Optional;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

public class Sample {

	public static void main(String[] args) {
		
		try {
			System.out.println(m());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static int m() throws Exception{
		try {
			int c = 1/0;
			return 0;
		}
		catch(Exception e)
		{
			return 1;
		}
		finally {
			System.out.println("DB Connection Close");
		}
	}

}

@AllArgsConstructor
@Getter
@ToString
class Node{
	int data;
	String name;
}
