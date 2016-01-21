package net.androidla.Main;

import net.androidla.common.CommonHelper;
import net.androidla.constant.Constant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainClass extends CommonHelper {
	private static Log log = LogFactory.getLog(MainClass.class);
	private static String enable_get_url_email = getParam("enable_get_url_email");
	private static String enable_send_email = getParam("enable_send_email");
	public static void main(String[] args) {
		// 创建相关表
		Thread t1 = new Thread(new InitThread(), "create table thread");
		t1.start();
		try {
			t1.join(); // 让当前线程block住，等该线程执行完之后，再继续执行其他线程
		} catch (InterruptedException e) {
			log.error(t1.getName(), e);
		}
		
		// 读取还没有解析过的 url
		if (Constant.YES.equals(enable_get_url_email)) {
			new Thread(new InsertAlinkEmailThread()).start();
		}
		
		// 发送邮件的任务
		if (Constant.YES.equals(enable_send_email)) {
			log.info("### 开始执行发送邮件任务  ###");
			new Thread(new SendEmailThread()).start();
		}
	}
}