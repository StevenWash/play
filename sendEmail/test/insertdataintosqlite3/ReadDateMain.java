package insertdataintosqlite3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.androidla.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ReadDateMain {
	private static Log log = LogFactory.getLog(ReadDateMain.class);
	public static void main(String[] args) {
		DBHelper.createTBEMAIL();
		//fromWeibo();
		fromTianya();
	}

	public static void fromWeibo() {
		String file = "D:\\工作积累\\weibo.com_12160\\weibo.com_12160.dbh";
		String extractFrom = "weibo";
		int cache = 10000;
		int dbcache = 1000;
		try {
			List<DataBean> list = new ArrayList<DataBean>();
			DataBean bean = null;
			String temp = null;
			String email = null;
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			while ((temp = br.readLine()) != null) {
				email = getEmail(temp);
				if (StringUtils.isEmpty(email)) {
					continue;
				}
				if (list.size() < cache) {
					bean = new DataBean();
					bean.setSendto(email);
					bean.setExtractfrom(extractFrom);
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
	
	public static void fromTianya() {
		String filePath = "D:\\工作积累\\天涯_4000W\\tianya";
		Integer c = 0;
		for (File file : new File(filePath).listFiles()) {
			log.info("get data from file : " + file.getAbsolutePath());
			try {
				String extractFrom = "tianya";
				int cache = 10000;
				int dbcache = 1000;
				List<DataBean> list = new ArrayList<DataBean>();
				List<String> templist = new ArrayList<String>();
				DataBean bean = null;
				String temp = null;
				String email = null;
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
				while ((temp = br.readLine()) != null) {
					email = getEmail(temp);
					if (StringUtils.isEmpty(email)) {
						continue;
					}
					if (list.size() < cache) {
						if (!templist.contains(email)) {
							bean = new DataBean();
							bean.setSendto(email);
							bean.setExtractfrom(extractFrom);
							list.add(bean);
							templist.add(email);
						}
					} else {
						c = insertDataInfo(c, dbcache, list, templist);
					}
				}
				if (list.size() > 0) {
					c = insertDataInfo(c, dbcache, list, templist);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Integer insertDataInfo(Integer c, int dbcache, List<DataBean> list, List<String> templist) {
		c += DBHelper.insertData(list, dbcache);
		log.info(">>>  $$$ " + c + " $$$ data has been inserted into the database");
		list.clear();
		templist.clear();
		return c;
	}
	
	public static String getEmail(String temp) {
		if (StringUtils.isNotEmpty(temp)) {
			Pattern pattern = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
	        Matcher matcher = pattern.matcher(temp);
	        if (matcher.find()) {
	        	return matcher.group().trim();
	        }
		}
		return null;
	}
}
