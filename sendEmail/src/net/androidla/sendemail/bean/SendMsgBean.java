package net.androidla.sendemail.bean;

public class SendMsgBean {
	private Integer id;
	private String sendfrom;
	private String subject;
	private String mailcontent;
	private String sendname;
	private String sendpassword;
	private String useCid;
	private String sendDate;
	public String getSendpassword() {
		return sendpassword;
	}
	public void setSendpassword(String sendpassword) {
		this.sendpassword = sendpassword;
	}
	private String resetDate;
	
	public String getResetDate() {
		return resetDate;
	}
	public void setResetDate(String resetDate) {
		this.resetDate = resetDate;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getSendfrom() {
		return sendfrom;
	}
	public void setSendfrom(String sendfrom) {
		this.sendfrom = sendfrom;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMailcontent() {
		return mailcontent;
	}
	public void setMailcontent(String mailcontent) {
		this.mailcontent = mailcontent;
	}
	public String getSendname() {
		return sendname;
	}
	public void setSendname(String sendname) {
		this.sendname = sendname;
	}
	public String getUseCid() {
		return useCid;
	}
	public void setUseCid(String useCid) {
		this.useCid = useCid;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
