package com.ckjava.thread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ck.common.DateUtil;
import net.ck.common.StringUtil;

public class DataCompare {
	
	private static final Logger logger = LoggerFactory.getLogger(DataCompare.class);
	
	public static final Integer CHECK_PAGESIZE = 10000;
	public static final String DB_SOURCE_MYSQL = "mysql";
	public static final String DB_SOURCE_SQL_SERVER = "sql server";
	
	public static Connection getConnection(String driverName, String url, String username, String password) throws Exception {
		Class.forName(driverName);
		return DriverManager.getConnection(url, username, password);
	}
	
	public static Connection getLeftConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gubei", "root", "root");
	}

	public static Connection getRightConnection() throws Exception {
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

	/**
	 * 启动两个线程进行数据对比，一个线程以左边的库为基准，另一个线程以右边的库为基准
	 * @param Connection leftConn 左边的数据库连接
	 * @param Connection rightConn 右边的数据库连接
	 * @param dbSource 数据库类型
	 * @param leftTitle 左边的对比名称
	 * @param rightTitle 右边的对比名称
	 * @param pageSize 分页比较大小
	 * @param table 对比的表
	 * @param businessKey 表的业务主键
	 * @param column 对比的列，如果有多列，以英文逗号分隔
	 * @return String 对比结果
	 */
	public String dbCompare(Connection leftConn, Connection rightConn, String dbSource, String leftTitle, String rightTitle, int pageSize, String table, String businessKey, String column) {
		StringBuilder result = new StringBuilder();
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		try {
			List<DbCompareThread> compareList = new ArrayList<DbCompareThread>();
			compareList.add(new DbCompareThread(pageSize, dbSource, leftTitle, rightTitle, leftConn, rightConn, table, businessKey, column));
			compareList.add(new DbCompareThread(pageSize, dbSource, rightTitle, leftTitle, rightConn, leftConn, table, businessKey, column));
			// 调用两个线程，并确保同时返回结果
			List<Future<Map<String, String>>> compareResultList = executorService.invokeAll(compareList);
			
			// 将对比结果存入StringBuilder中
			Map<String, String> resultMap = new HashMap<String, String>();
			for (Future<Map<String, String>> future : compareResultList) {
				resultMap.putAll(future.get());
			}
			for (Iterator<String> it = resultMap.values().iterator(); it.hasNext();) {
				result.append(it.next());
			}
			
			return result.toString();
		} catch (Exception e) {
			logger.error("对比表:"+table+",业务主键:"+businessKey+",字段:"+column+"出现异常", e);
			return null;
		} finally {
			executorService.shutdown();
		}
	}

	public static void main(String[] args) {
		DataCompare compare = new DataCompare();
		Connection leftConn = null;
		Connection rightConn = null;
		try {
			leftConn = getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/gubei", "root", "root");
			rightConn = getConnection("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1:3306/pbs", "root", "root");
			
			compare.dbCompare(leftConn, rightConn, DB_SOURCE_MYSQL, "被测库", "备份库", CHECK_PAGESIZE, "sys_user", "code", "code,name");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeResource(leftConn, null, null);
			closeResource(rightConn, null, null);
		}
		
	}

	private class DbCompareThread implements Callable<Map<String, String>> {
		private int pageSize;
		private String dbSource;
		private String table;
		private String column;
		private String businessKey;
		private String leftTitle;
		private String rightTitle;
		private Connection leftConn;
		private Connection rightConn;

		private DbCompareThread(int pageSize, String dbSource, String leftTitle, String rightTitle, Connection leftConn, Connection rightConn, String table, String businessKey, String column) {
			this.pageSize = pageSize;
			this.dbSource = dbSource;
			this.leftTitle = leftTitle;
			this.rightTitle = rightTitle;
			this.leftConn = leftConn;
			this.rightConn = rightConn;
			this.table = table;
			this.column = column;
			this.businessKey = businessKey;
		}
		
		@Override
		public Map<String, String> call() throws Exception {
			String[] columns = column.split(",");
			
			Map<String, String> resultMap = new HashMap<String, String>();
			int leftCount = getCount(leftConn, table);
			int rightCount = getCount(rightConn, table);
			// 首先以备份库为基准进行对比
			int leftBatch = leftCount / pageSize;
			for (int j = 0; j <= leftBatch; j++) { // 分页遍历备份库
				PreparedStatement leftPs = leftConn.prepareStatement(getSqlStatement(table, businessKey, column, j, pageSize, dbSource));
				ResultSet leftRs = leftPs.executeQuery();
				Map<String, String> leftCompareMap = getDataToMap(columns, leftRs);
				closeResource(null, leftPs, leftRs);
				
				int rightBatch = rightCount / pageSize;
				for (int k = 0; k <= rightBatch; k++) { // 分页遍历测试库
					PreparedStatement rightPs = rightConn.prepareStatement(getSqlStatement(table, businessKey, column, k, pageSize, dbSource));
					ResultSet rightRs = rightPs.executeQuery();
					Map<String, String> rightCompareMap = getDataToMap(columns, rightRs);
					closeResource(null, rightPs, rightRs);
					
					// 执行当前页比较
					List<String> sameEntryKey = doCompare(resultMap, columns, businessKey, leftTitle, rightTitle, leftCompareMap, rightCompareMap);
					// 当前页遍历一次被测库后，将一致的数据从备份库中删除，并继续对比下一页的测试库数据
					removeAll(leftCompareMap, sameEntryKey);
					// 如果在对测试库某次遍历中已经找到了所有的业务主键，那么退出对测试库的遍历，继续获取下一页备份库数据进行遍历比较
					if (leftCompareMap.isEmpty()) {
						break;
					}
				}
				// 最后当分页遍历完测试库后，将在测试库中没有找到的记录写入日志中
				for (Iterator<String> it = leftCompareMap.keySet().iterator(); it.hasNext();) {
					String keyValue = it.next();
					resultMap.put(keyValue, "业务主键:"+businessKey+"="+keyValue+"在"+leftTitle+"中存在，在"+rightTitle+"中不存在\n");
				}
			}
			return resultMap;
		}
	}
	
	private String getSqlStatement(String table, String businessKey, String column, int index, int pageSize, String dbSource) {
		if (dbSource.equals(DB_SOURCE_MYSQL)) {
			return "select "+businessKey+","+column+" from "+table+" order by "+businessKey+" limit "+index*pageSize+","+pageSize;
		} else if (dbSource.equals(DB_SOURCE_SQL_SERVER)) {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT TOP "+ pageSize + " " + column +" FROM "+ table);
			sql.append(" WHERE "+businessKey+" NOT IN ");
			sql.append(" (SELECT TOP "+ pageSize*(index == 0 ? 0 : index-1) +" "+businessKey+" FROM "+table+" order by "+businessKey+") "); // 以第一个字段排序
			sql.append(" order by "+businessKey);
			return sql.toString();
		}
		return null;
	}

	private List<String> doCompare(Map<String, String> resultMap, String[] columns, String businessKey, String leftTitle, String rightTitle, Map<String, String> leftCompareMap, Map<String, String> rightCompareMap) {
		List<String> sameEntryKey = new ArrayList<String>(); // 把两个map中一致的记录的key放到这个List中
		for (Iterator<Entry<String, String>> it = leftCompareMap.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> leftData = it.next();
			String leftKey = leftData.getKey();
			String leftValue = leftData.getValue();
			if (rightCompareMap.containsKey(leftKey)) { // 如果在当前批次的测试库中找到备份库中的记录，就放到sameEntryKey中
				sameEntryKey.add(leftKey);
				String rightValue = rightCompareMap.get(leftKey);
				if (!leftValue.equals(rightValue)) { // 如果备份库和测试库的key一致，value 不一致，找出具体是哪一列有问题并记录入日志中
					String[] leftColumnDatas = leftValue.split(",");
					String[] rightColumnDatas = rightValue.split(",");
					for (int z = 0, d = columns.length; z < d; z++) {
						if (!leftColumnDatas[z].equals(rightColumnDatas[z])) {
							resultMap.put(leftKey, "业务主键:"+businessKey+"="+leftKey+"一致，但是列:"+columns[z]+"不一致=>["+leftTitle+"值:"+leftColumnDatas[z]+"]vs["+rightTitle+"值:"+rightColumnDatas[z]+"]\n");
						}
					}
				}
			}
		}
		return sameEntryKey;
	}
	
	private int getCount(Connection conn, String tableName) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select count(1) from ".concat(tableName));
			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return 0;
		} catch (Exception e) {
			return 0;
		} finally {
			closeResource(null, ps, rs);
		}
	}

	private void removeAll(Map<String, String> dataMap, List<String> keyList) {
		for (String key : keyList) {
			dataMap.remove(key);
		}
	}

	private Map<String, String> getDataToMap(String[] columns, ResultSet backupRs) throws SQLException {
		Map<String, String> backupCompareMap = new HashMap<String, String>();
		while (backupRs.next()) {
			String businessKeyData = getStringData(backupRs, 1);
			String columnData = "";
			for (int k = 0, d = columns.length; k < d; k++) {
				String data = getStringData(backupRs, k+2);
				columnData += (k == d-1 ? data : data.concat(","));
			}
			backupCompareMap.put(businessKeyData, columnData);
		}
		return backupCompareMap;
	}

	private boolean isDate(Object str) {
		return DateUtil.parseDate(str) != null ? true : false;
	}
	
	private String getStringData(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		Object data = isDate(obj) ? obj : StringUtil.getCleanXmlString(obj);
		return StringUtil.getStr(data);
	}
}
