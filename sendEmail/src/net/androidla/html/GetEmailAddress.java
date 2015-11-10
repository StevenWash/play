package net.androidla.html;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.androidla.common.CommonHelper;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sqlite.SqliteHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetEmailAddress extends CommonHelper {
	private static Log log = LogFactory.getLog(GetEmailAddress.class);
	
	public static List<EmailBean> getEmailUrl(String htmlcontent, String extractFrom) {
		if (htmlcontent == null) {
			return null;
		}
		log.info("EMAIL EXTRACT >>> start extract email address from htmlcontent <<<");
		List<EmailBean> list = new ArrayList<EmailBean>();
		List<String> temp = new ArrayList<String>();
		EmailBean bean = null;
        Pattern pattern = Pattern.compile(getParam("email"));
        Matcher matcher = pattern.matcher(htmlcontent);
        while (matcher.find()) {
        	String sendTo = matcher.group();
        	if (!SqliteHelper.isEmailAddressExists(sendTo) && !temp.contains(sendTo)) {
        		temp.add(sendTo);
            	bean = new EmailBean();
            	bean.setSendTo(sendTo);
            	bean.setExtractFrom(extractFrom);
            	list.add(bean);
        	}
        }
        temp = null;
        log.info("EMAIL EXTRACT <<< end extract email address from htmlcontent >>>");
        return list;
	}
	
}
