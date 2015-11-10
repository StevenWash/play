package dataparser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestReadDate {
	public static void main(String[] args) {
		String file = "D:\\工作积累\\CSDN-中文IT社区-600万\\www.csdn.net.sql";
		int cache = 10000;
		int dbcache = 1000;
		try {
			List<DataBean> list = new ArrayList<DataBean>();
			DataBean bean = null;
			String[] data = null;
			String temp = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((temp = br.readLine()) != null) {
				data = temp.split("#");
				bean = new DataBean();
				bean.setName(null);
				bean.setPassword(null);
				bean.setEmail(data[2].trim());
				if (list.size() < cache) {
					list.add(bean);	
				} else {
					DBHelper.insertData(list, dbcache);
					list.clear();
				}
			}
			if (list.size() > 0) {
				DBHelper.insertData(list, dbcache);
				list.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
