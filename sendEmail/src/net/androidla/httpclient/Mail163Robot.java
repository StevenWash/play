package net.androidla.httpclient;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.androidla.constant.Constant;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sendemail.bean.SendMsgBean;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@SuppressWarnings("static-access")
public class Mail163Robot {
	private static Logger log = LoggerFactory.getLogger(Mail163Robot.class);
	
	public static final String SESSION_INIT = "http://mail.163.com";
	public static final String LOGIN_URL = "https://ssl.mail.163.com/entry/coremail/fcg/ntesdoor2?df=webmail163&from=web&funcid=loginone&iframe=1&language=-1&net=t&passtype=1&product=mail163&race=-2_-2_-2_db&style=-1&uid=";
	public static final String MAIL_LIST_URL = "http://twebmail.mail.163.com/js4/s?sid={0}&func=mbox:listMessages";
	public static final String MAIL_SEND_URL = "http://cwebmail.mail.163.com/js5/s?sid={0}&func=mbox:compose&cl_address=1&from=nav&action=goCompose&cl_send=1&l=compose&action=deliver";
	public static final String MAIL_SENDED_URL = "http://cwebmail.mail.163.com/js5/s?sid={0}&func=mbox:listMessages&name=font_work&value=true&from=nav&group=folder&id=3&action=click&mboxentry=3";
	public static final String MAIL_SENDED_EMAIL_URL = "http://cwebmail.mail.163.com/js5/s?sid={0}&func=user:listDeliveryHistory&from=nav&group=folder&id=3&action=click&mboxentry=3&read_fid=3&read_cached=false&read_read=true&read_page=0&read_index=0";
	
	public static void doSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, List<EmailBean> success, List<EmailBean> fail, int c, int t, long email_send_interval) throws Exception {
		HttpClientHelper hc = new HttpClientHelper(true);
		HttpResult result;
		try {
			result = hc.get(SESSION_INIT);
		} catch (Exception e) {
			throw e;
		}
		String adhtml = "&lt;div style='line-height:1.7;color:#000000;font-size:14px;font-family:arial'&gt;&amp;nbsp;你好 ！&lt;br&gt;&lt;br&gt;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&amp;nbsp;&lt;span style=\"font-size: 18px;\"&gt;&lt;b&gt; &lt;a href=\"http://u2l.info/1KAaMM\" target=\"_blank\"&gt;曝光！港星秘密  性  玩  具&lt;/a&gt;&lt;/b&gt;&lt;/span&gt;&lt;br&gt;&lt;span style=\"background-color: rgb(255, 0, 0);\"&gt;&lt;/span&gt;&lt;/div&gt;";
		String senderName = sendMsgBean.getSendname();
		String senderEmailAddress = sendMsgBean.getSendfrom();
		String senderEmailPassword = sendMsgBean.getSendpassword();
		
		String subject = sendMsgBean.getSubject();
		//String mailContent = HttpClientHelper.html(sendMsgBean.getMailcontent());
		String mailContent = adhtml;
		// 执行登录
		log.info("账号<" + senderEmailAddress + ">执行登录 ...");
		Map<String, String> data = getLoginInfo(senderEmailAddress, senderEmailPassword);
		try {
			result = hc.post(LOGIN_URL, data, setHeader());
		} catch (Exception e) {
			throw e;
		}
		Document doc = Jsoup.parse(result.getHtml());
		if (!doc.select("script").html().isEmpty()) {
			String sessionId = doc.select("script").html().split("=")[2];
			sessionId = sessionId.substring(0,sessionId.length()-2);
			log.info("账号<" + senderEmailAddress + ">登录成功  sessionId = " + sessionId);
			
			// 执行循环发送
			for (EmailBean emailBean : sendtolist) {
				String receiverName = emailBean.getSendTo();
				String receiverAddress = emailBean.getSendTo();
				try {
					data.clear();
					data.put("var", "<?xml version=\"1.0\"?><object><string name=\"id\">c:1386062612583</string><object name=\"attrs\"><string name=\"account\">\""+senderName+"\"&lt;"+senderEmailAddress+"&gt;</string><boolean name=\"showOneRcpt\">false</boolean><array name=\"to\"><string>\""+receiverName+"\"&lt;"+receiverAddress+"&gt;</string></array><array name=\"cc\"/><array name=\"bcc\"/><string name=\"subject\">"+subject+"</string><boolean name=\"isHtml\">true</boolean><string name=\"content\">"+mailContent+"</string><int name=\"priority\">3</int><boolean name=\"requestReadReceipt\">false</boolean><boolean name=\"saveSentCopy\">true</boolean><string name=\"charset\">GBK</string></object><boolean name=\"returnInfo\">false</boolean><string name=\"action\">deliver</string><int name=\"saveTextThreshold\">1048576</int></object>");
					result = hc.post(MessageFormat.format(MAIL_SEND_URL, sessionId), data, setQueryHeader(sessionId));
					JSONObject obj = JSON.parseObject(result.getHtml().replaceAll("new Date\\([\\s\\S]*\\)", "'date'"));
					if (obj == null || obj.get("code") == null) {
						throw new RuntimeException("获取不到邮件服务器响应");
					} else {
						if (obj.get("code").toString().equals("S_OK")) {
							success.add(emailBean);
							log.info("No." + t + Constant.SPLIT_4 + c + " : send robot email from <" + sendMsgBean.getSendfrom() + "> to <" + receiverAddress + "> success");
						} else if (obj.get("code").toString().equals("FA_NEED_VERIFY_CODE")) {
							throw new RuntimeException("{'code':'FA_NEED_VERIFY_CODE'} 邮件服务器提示发送需要验证码或者超过当日该账号的发送上限");
						} else {
							fail.add(emailBean);
							log.info("No." + t + Constant.SPLIT_4 + c + " : send robot email from <" + sendMsgBean.getSendfrom() + "> to <" + receiverAddress + "> fail");
							log.info(" result : " + result.getHtml());
						}
					}
					Thread.currentThread().sleep(email_send_interval);
				} catch (Exception e) {
					fail.add(emailBean);
					log.error("No." + t + Constant.SPLIT_4 + c + " : send robot email from <" + sendMsgBean.getSendfrom() + "> to <" + receiverAddress + "> fail", e);
					if (e instanceof InterruptedException) {
						log.error("No." + t + Constant.SPLIT_4 + c + " : send robot email from <" + sendMsgBean.getSendfrom() + "> to <" + receiverAddress + "> occurrence error when the thread sleep", e);
					}
					throw e;
				} finally {
					c ++;
					t ++;
				}
			}
		} else {
			log.info("获取网页信息失败！");
		}
	}
	
	private static Map<String, String> getLoginInfo(String senderEmailAddress, String senderPassword) {
		final Map<String, String> data = new HashMap<String, String>();
		data.put("url2", "http://mail.163.com/errorpage/err_163.htm");
		data.put("savelogin", "0");
		data.put("username", senderEmailAddress.substring(0, senderEmailAddress.indexOf("@")));
		data.put("password", senderPassword);
		return data;
	}
	
	public static Header[] setHeader() {
		Header[] result = { 
				new BasicHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"), 
				new BasicHeader("Accept-Encoding","gzip, deflate"),
				new BasicHeader("Accept-Language","zh-CN"),
				new BasicHeader("Cache-Control","no-cache"),
				new BasicHeader("Connection","Keep-Alive"),
				new BasicHeader("Content-Type","application/x-www-form-urlencoded"),
				new BasicHeader("Host","ssl.mail.163.com"),
				new BasicHeader("Referer","http://mail.163.com/"),
				new BasicHeader("Accept","text/html, application/xhtml+xml, */*")
				
		};
		return result;
	}
	public static Header[] setQueryHeader(String sessionId) {
		Header[] result = { 
				new BasicHeader("User-Agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)"), 
				new BasicHeader("Accept-Encoding","gzip, deflate"),
				new BasicHeader("Accept-Language","zh-CN"),
				new BasicHeader("Cache-Control","no-cache"),
				new BasicHeader("Connection","Keep-Alive"),
				new BasicHeader("Content-Type","application/x-www-form-urlencoded"),
				new BasicHeader("Host","twebmail.mail.163.com"),
				new BasicHeader("Referer","http://twebmail.mail.163.com/js4/index.jsp?sid="+sessionId),
				new BasicHeader("Accept","text/javascript")
		};
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//robotSender();
		//String str = "dsert45455445@yeah.net";
		//System.out.println(str.substring(0, str.indexOf("@")));
	}
}
