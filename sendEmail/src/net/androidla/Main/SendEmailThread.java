package net.androidla.Main;

import java.util.List;

import net.androidla.common.CommonHelper;
import net.androidla.sendemail.AbstractSendEmail;
import net.androidla.sendemail.SendEmailHelper;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sendemail.bean.SendMsgBean;
import net.androidla.sqlite.SqliteHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SendEmailThread extends CommonHelper implements Runnable {
	private static Log log = LogFactory.getLog(SendEmailThread.class);
	private String sqlite_no_send_email_sql = getParam("sqlite_no_send_email_sql");
	private int email_send_count = Integer.parseInt(getParam("email_send_count"));
	private String emailtype = getParam("email_type");
	private boolean flag = true;
	private int count = 0;
	private List<EmailBean> sendtolist = null;
	private SendMsgBean bean = null;
	private AbstractSendEmail sendMail = new SendEmailHelper();
	@Override
	public void run() {
		while (flag) {
			log.info(">>> get data from database now, please wait ... <<<");
			sendtolist = SqliteHelper.getEmailAddress(email_send_count, sqlite_no_send_email_sql);
			bean =  sendMail.getSendMsgBean();
			count = sendtolist.size();
			if (count > 0) {
				log.info(">>> start send to " + count + " email <<<");
				
				if (bean != null) {
					try {
						sendMail.mailSend(sendtolist, bean, emailtype);
					} catch (Exception e) {
						log.error(" 发送出现异常 " + e.getMessage());
						sendtolist.clear();
						flag = true;
						continue;
					}
				} else {
					sendMail.mailSend(sendtolist, emailtype);
				}
				
				sendtolist.clear();
				log.info(">>> end send to " + count + " email <<<");
			} else {
				flag = false;
				log.info(">>> no email selected <<<");
			}
		}
	}
}
