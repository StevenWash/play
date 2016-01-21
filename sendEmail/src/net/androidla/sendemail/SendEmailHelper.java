package net.androidla.sendemail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.androidla.constant.Constant;
import net.androidla.html.GetImg;
import net.androidla.httpclient.Mail163Robot;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sendemail.bean.SendMsgBean;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

@SuppressWarnings("static-access")
public class SendEmailHelper extends AbstractSendEmail {
	
	@Override
	public void simpleSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, SimpleEmail smail, List<EmailBean> success, List<EmailBean> fail) {
		for (EmailBean emailBean : sendtolist) {
			try {
				smail = new SimpleEmail();
				smail.setHostName(email_host);
				smail.setAuthentication(email_username, email_password);
				smail.setCharset(email_charset);
				smail.setSubject(sendMsgBean.getSubject());
				smail.setFrom(sendMsgBean.getSendfrom(), sendMsgBean.getSendname(), email_charset);
				smail.setMsg(sendMsgBean.getMailcontent());
				sendto = emailBean.getSendTo().toLowerCase();
				if (!isValidEmail(sendto)) {
					fail.add(emailBean);
					continue;
				}
				smail.addTo(sendto);
				msgId = smail.send();
				emailBean.setSendFrom(sendMsgBean.getSendfrom());
				emailBean.setMsgId(msgId);
				emailBean.setEmailType(Constant.SIMPLE_EMAIL);
				success.add(emailBean);
				log.info("No." + t + Constant.SPLIT_4 + c + " : send simple email from <" + sendMsgBean.getSendfrom() + "> to <" + sendto + "> success, message id = " + msgId);
				
				Thread.currentThread().sleep(email_send_interval);
			} catch (Exception e) {
				fail.add(emailBean);
				log.error("No." + t + Constant.SPLIT_4 + c + " : send simple email from <" + sendMsgBean.getSendfrom() + "> to <" + sendto + "> fail", e);
				if (e instanceof InterruptedException) {
					log.error("No." + t + Constant.SPLIT_4 + c + " : send simple email from <" + sendMsgBean.getSendfrom() + "> to <" + sendto + "> occurrence error when the thread sleep", e);
				}
			} finally {
				c ++;
				t ++;
			}
		}
	}

	@Override
	public void htmlSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, HtmlEmail hmail, List<EmailBean> success, List<EmailBean> fail) {
		for (EmailBean emailBean : sendtolist) {
			try {
				hmail = new HtmlEmail();
				hmail.setHostName(email_host);
				hmail.setAuthentication(email_username, email_password);
				hmail.setCharset(email_charset);
				hmail.setSubject(sendMsgBean.getSubject());
				hmail.setFrom(sendMsgBean.getSendfrom(), sendMsgBean.getSendname(), email_charset);
				if (Constant.YES.equals(sendMsgBean.getUseCid())) {
					hmail.setHtmlMsg(processImgCid(sendMsgBean.getMailcontent(), hmail));
				} else {
					hmail.setHtmlMsg(sendMsgBean.getMailcontent());
				}
				sendto = emailBean.getSendTo().toLowerCase();
				if (!isValidEmail(sendto)) {
					fail.add(emailBean);
					continue;
				}
				hmail.addTo(sendto);
				msgId = hmail.send();
				emailBean.setSendFrom(sendMsgBean.getSendfrom());
				emailBean.setMsgId(msgId);
				emailBean.setEmailType(Constant.HTML_EMAIL);
				success.add(emailBean);
				log.info("No." + t + Constant.SPLIT_4 + c + " : send html email from <" + sendMsgBean.getSendfrom() + "> to <" + sendto + "> success, message id = " + msgId);
				
				Thread.currentThread().sleep(email_send_interval);
			} catch (Exception e) {
				fail.add(emailBean);
				log.error("No." + t + Constant.SPLIT_4 + c + " : send html email from <" + sendMsgBean.getSendfrom() + "> to <" + sendto + "> fail", e);
				if (e instanceof InterruptedException) {
					log.error("No." + t + Constant.SPLIT_4 + c + " : send html email from <" + sendMsgBean.getSendfrom() + "> to <" + sendto + "> occurrence error when the thread sleep", e);
				}
			} finally {
				c ++;
				t ++;
			}
		}
	}
	
	private String processImgCid(String htmlcontent, HtmlEmail hmail) throws Exception {
		List<String> list = GetImg.getImgSrc(htmlcontent);
		String[] strs = null;
		for (String str : list) {
			strs = str.split(":");
			try {
				htmlcontent = htmlcontent.replaceAll(str, strs[0].concat(Constant.SPLIT_2).concat(hmail.embed(new File(imgpath.concat(strs[1])))));
			} catch (EmailException e) {
				throw new EmailException("add img cid to html email occurrence error", e);
			}
		}
		return htmlcontent;
	}
	
	@Override
	public void robotSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, List<EmailBean> success, List<EmailBean> fail) throws Exception {
		Mail163Robot.doSend(sendtolist, sendMsgBean, success, fail, c, t, email_send_interval);
	}
	
	public static void main(String[] args) {
		try {
			AbstractSendEmail helper = new SendEmailHelper();
			
			List<EmailBean> list = new ArrayList<EmailBean>();
			EmailBean b = new EmailBean();
			b.setSendTo("svsechen@739745891.cn");
			list.add(b);
			b = new EmailBean();
			b.setSendTo("svsechen@126.com");
			list.add(b);
			b = new EmailBean();
			b.setSendTo("svsechen@sina.com");
			list.add(b);
			
			SendMsgBean bean = new SendMsgBean();
			bean.setSendfrom("svsechen@163.com");
			bean.setSendname("老朋友了");
			
			helper.mailSend(list, bean, Constant.ROBOT_EMAIL);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
