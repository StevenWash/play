package com.ckjava.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;

public class DataCompare {

	public static Connection getConnection1() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gubei", "root", "root");
	}

	public static Connection getConnection2() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/pbs", "root", "root");
	}

	public static void closeResource(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getCount(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select count(1) from sys_user");
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			closeResource(conn, ps, rs);
		}
	}

	public static void setData(Connection conn, Map<String, String> dataMap) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select code, name from sys_user order by code");
			rs = ps.executeQuery();
			while (rs.next()) {
				dataMap.put(rs.getString(1), rs.getString(1) + "," + rs.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(conn, ps, rs);
		}
	}

	public void doCompare() {
		Map<String, String> dataMap1 = Collections.synchronizedMap(new HashMap<String, String>());
		Map<String, String> dataMap2 = Collections.synchronizedMap(new HashMap<String, String>());
		Connection conn1 = null;
		Connection conn2 = null;
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {
			conn1 = getConnection1();
			conn2 = getConnection2();
			
			List<GetDataThread> taskList = new ArrayList<GetDataThread>();
			taskList.add(new GetDataThread(conn1, dataMap1));
			taskList.add(new GetDataThread(conn2, dataMap2));
			executorService.invokeAll(taskList); // 启动两个线程向Map中插入数据
			
			List<String> resultList = new ArrayList<String>();
			// 比较两个Map的数据是否一致，并写入结果list中
			// 循环DataMap1中每个Entry，先看看Entry中的key能否在DataMap2中找到，能找到，再比较value，不一致存入list中；不能找到存入list中
			for (Iterator<Entry<String, String>> it = dataMap1.entrySet().iterator(); it.hasNext();) { 
				Entry<String, String> entry1 = it.next();
				String key1 = entry1.getKey();
				String value1 = entry1.getValue();
				if (dataMap2.containsKey(key1)) { // 如果在第二个Map中发现有同样的key，就可以从两个Map中移除
					String value2 = dataMap2.get(key1);
					if (!value1.equals(value2)) {
						resultList.add(key1+"\t key一致，但是值不一致=>[value1:"+value1+"]vs[value2:"+value2+"]");
					} else {
						resultList.add(key1+"\t key一致，值也一致=>[value1:"+value1+"]vs[value2:"+value2+"]");
					}
				} else {
					resultList.add(key1+"\t 在DataMap1中存在，在DataMap2中不存在");
				}
			}
			System.out.println("DataMap1的记录为："+CollectionUtils.size(dataMap1)+",DataMap2的记录为："+CollectionUtils.size(dataMap2));
			System.out.println("比较结果List大小为:"+CollectionUtils.size(resultList));
			for (String string : resultList) {
				System.out.println(string);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(conn1, null, null);
			closeResource(conn2, null, null);
			executorService.shutdown();
		}
		
		
	}

	public static void main(String[] args) {
		DataCompare compare = new DataCompare();
		compare.doCompare();
	}

	public class GetDataThread implements Callable<String> {

		private Map<String, String> dataMap;
		private Connection conn;

		public GetDataThread(Connection conn, Map<String, String> dataMap) {
			this.dataMap = dataMap;
			this.conn = conn;
		}

		@Override
		public String call() throws Exception {
			setData(conn, dataMap);
			return "1";
		}

	}

	public class CompareThread implements Runnable {

		private Map<String, String> dataMap1;
		private Map<String, String> dataMap2;
		private List<String> resultList;

		public CompareThread(Map<String, String> dataMap1, Map<String, String> dataMap2, List<String> resultList) {
			this.dataMap1 = dataMap1;
			this.dataMap2 = dataMap2;
			this.resultList = resultList;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (dataMap1) {
					for (Iterator<Entry<String, String>> it = dataMap1.entrySet().iterator(); it.hasNext();) {
						Entry<String, String> entry1 = it.next();
						String key1 = entry1.getKey();
						String value1 = entry1.getValue();
						if (dataMap2.containsKey(key1)) { // 如果在第二个Map中发现有同样的key，就可以从两个Map中移除
							String value2 = dataMap2.get(key1);
							if (!value1.equals(value2)) {
								resultList.add(key1 + "一致，但是值不一致=>[value1:" + value1 + "]vs[value2:" + value2 + "]");
							}
							dataMap1.remove(key1);
							dataMap2.remove(key1);
						}
					}
				}
			}
		}
	}
}
