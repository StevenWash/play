package com.test.list;

import java.util.ArrayList;
import java.util.List;

public class TestPlus {

	public static void main(String[] args) {
		List<String> temps = new ArrayList<>();
		temps.add("a");
		temps.add("b");
		temps.add("c");
		temps.add("d");
		
		int index = 0;
		//System.out.println(temps.get(index++)); // a
		System.out.println(temps.get(++index)); // b
	}

}
