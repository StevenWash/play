package com.test.string;

import java.util.HashMap;
import java.util.Map;

public class TestSplitString {
	public static void main(String[] args) {
		String str = "mbox_msgcnt=5&mbox_newmsgcnt=2";
		String[] datas = str.split("&");
		Map<String, Object> map = new HashMap<String, Object>();
		for (String string : datas) {
			System.out.println(string);
			String[] kv = string.split("=");
			map.put(kv[0], kv[1]);
		}
		System.out.println(map.toString());
		
	}
}
