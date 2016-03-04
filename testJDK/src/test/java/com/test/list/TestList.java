package com.test.list;

import java.util.ArrayList;
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
}
