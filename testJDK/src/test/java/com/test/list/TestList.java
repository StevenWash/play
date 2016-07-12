package com.test.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestList {

	@Test
	public void testListAddALL() {
		List<Map<String,Object>> tempALL = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
		tempALL.addAll(temp);// ok
		tempALL.addAll(null);// wrong
	}
	
	@Test
	public void testArrays() {
		List<String> list = Arrays.asList("1,2,3,4,5,6,7,8,17,18,82,83,70,170".split(","));
		for (String string : list) {
			System.out.println(string);
		}
	}
}
