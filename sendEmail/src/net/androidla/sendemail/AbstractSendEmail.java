package net.androidla.sendemail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.androidla.common.CommonHelper;
import net.androidla.constant.Constant;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sendemail.bean.SendMsgBean;
import net.androidla.sqlite.SqliteHelper;
import net.androidla.util.FileUtils;
import net.androidla.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

public abstract class AbstractSendEmail extends CommonHelper {
	protected static Log log = LogFactory.getLog(AbstractSendEmail.class);
	
	protected String email_host = getParam("email_host");
	protected String email_username = getParam("email_username");
	protected String email_password = getParam("email_password");
	protected String email_charset = getParam("email_charset");
	protected long email_send_interval = Long.parseLong(getParam("email_send_interval"));
	protected String filepath = getParam("email_send_file_path");
	protected String imgpath = getParam("email_send_imgs_path");
	private int cache = Integer.parseInt(getParam("sqlite_cache"));
	private String useCid = getParam("email_send_use_cid");
	
	private List<EmailBean> success = new ArrayList<EmailBean>();
	private List<EmailBean> fail = new ArrayList<EmailBean>();
	private SendMsgBean sendMsgBean = initSendMailInfo();
	
	protected SimpleEmail smail = null;
	protected HtmlEmail hmail = null;
	protected String msgId = null;
	protected String sendto = null;
	
	protected int t = 1;
	protected int c = 1;
	
	/**
	 * 邮件发送方法
	 * @param sendtolist 被发送人列表
	 * @param mailtype 邮件类型
	 */
	public void mailSend(List<EmailBean> sendtolist, String mailtype) {
		c = 1;
		if (mailtype.equals(Constant.SIMPLE_EMAIL)) {
			simpleSend(sendtolist, sendMsgBean, smail, success, fail);
		} else if (mailtype.equals(Constant.HTML_EMAIL)) {
			htmlSend(sendtolist, sendMsgBean, hmail, success, fail);
		}
		updateSendResult(null);
	}
	
	/**
	 * 邮件发送方法
	 * @param sendtolist 被发送人列表
	 * @param sendMsgBean 发送人实体
	 * @param mailtype 邮件类型
	 * @throws Exception 
	 */
	public void mailSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, String mailtype) throws Exception {
		c = 1;
		sendMsgBean.setUseCid(useCid);
		try {
			if (mailtype.equals(Constant.SIMPLE_EMAIL)) {
				simpleSend(sendtolist, sendMsgBean, smail, success, fail);
			} else if (mailtype.equals(Constant.HTML_EMAIL)) {
				htmlSend(sendtolist, sendMsgBean, hmail, success, fail);
			} else if (mailtype.equals(Constant.ROBOT_EMAIL)) {
				robotSend(sendtolist, sendMsgBean, success, fail);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			updateSendResult(sendMsgBean);
		}
	}
	
    private void updateSendResult(SendMsgBean sendMsgBean) {
		SqliteHelper.updateSendEmailSuccess(success, cache);
		SqliteHelper.updateSendEmailFail(fail, cache);
		success.clear();
		fail.clear();
		if (sendMsgBean != null) {
			SqliteHelper.updateSenderInfo(sendMsgBean);
		}
    }
    
    private SendMsgBean initSendMailInfo() {
		String[] from_subject = null;
		String mailcontent = null;
		for (File file : FileUtils.listFiles(filepath)) {
			from_subject = file.getName().split(Constant.SPLIT_1);
			if (from_subject != null && from_subject.length == 5 && from_subject[3].equals(Constant.YES)) {
				try {
					mailcontent = FileUtils.readFileContent(file, email_charset);
				} catch (Exception e) {
					log.error("read " + file.getName() + " content occurence error", e);
				}
				break;
			}
		}
		if (StringUtils.isEmpty(mailcontent)) {
			throw new IllegalArgumentException("send email fail, because the sending mail content is null, please check!");
		}
		SendMsgBean sendMsgBean = new SendMsgBean();
		sendMsgBean.setSendfrom(from_subject[0]);
		sendMsgBean.setSubject(from_subject[1]);
		sendMsgBean.setSendname(from_subject[2]);
		sendMsgBean.setMailcontent(mailcontent);
		sendMsgBean.setUseCid(useCid);
		return sendMsgBean;
	}
    
    /**
     * 获取发送人信息
     * @return
     */
    public SendMsgBean getSendMsgBean() {
    	SendMsgBean bean = SqliteHelper.getSendMsgBean();
    	if (bean == null) {
    		SqliteHelper.updateSenderInfo(bean);
    		bean = SqliteHelper.getSendMsgBean();
    	}
    	return bean;
    }
    
    /**
     * 纯文本邮件类型
     * @param sendtolist 被发送列表
     * @param sendMsgBean 发送人实体
     * @param smail 纯文本邮件类
     * @param success 发送成功列表
     * @param fail 发送失败列表
     */
    public abstract void simpleSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, SimpleEmail smail, List<EmailBean> success, List<EmailBean> fail);
	
    /**
     * html 邮件类型
     * @param sendtolist 被发送列表
     * @param sendMsgBean 发送人实体
     * @param hmail 纯文本邮件类
     * @param success 发送成功列表
     * @param fail 发送失败列表
     */
	public abstract void htmlSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, HtmlEmail hmail, List<EmailBean> success, List<EmailBean> fail);

	/**
     * 通过机器人自动登录网络邮件模拟人工发送
     * @param sendtolist 被发送列表
     * @param sendMsgBean 发送人实体
     * @param hmail 纯文本邮件类
     * @param success 发送成功列表
     * @param fail 发送失败列表
	 * @throws Exception 
     */
	public abstract void robotSend(List<EmailBean> sendtolist, SendMsgBean sendMsgBean, List<EmailBean> success, List<EmailBean> fail) throws Exception;
}
