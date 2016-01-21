package net.androidla.sqlite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.androidla.common.CommonHelper;
import net.androidla.constant.Constant;
import net.androidla.html.bean.AlinkBean;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sendemail.bean.SendMsgBean;
import net.androidla.util.CollectionsUtils;
import net.androidla.util.DateUtils;
import net.androidla.util.StringUtils;

public class SqliteHelper extends DatabaseHelper {
	public static void createTBALINK() {
		try {
			conn = getConn();
			ps = conn.prepareStatement(pro.getProperty("sqlite_tbalinksql"));
			ps.execute();
		} catch (Exception e) {
			log.error("create table tb_alink occurrence error", e);
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public static void createTBEMAIL() {
		try {
			conn = getConn();
			ps = conn.prepareStatement(pro.getProperty("sqlite_tbemailsql"));
			ps.execute();
		} catch (Exception e) {
			log.error("create table tb_email occurrence error", e);
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public synchronized static boolean isAlinkExists(String target) {
		try {
			conn = getConn();
			ps = conn.prepareStatement("SELECT URL FROM TB_ALINK WHERE URL = ?");
			ps.setString(1, target);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("check a link exists occurrence error", e);
			return false;
		} finally {
			closeResource(conn, ps, rs);
		}
	}
	
	public synchronized static boolean isEmailAddressExists(String emailAddress) {
		try {
			conn = getConn();
			ps = conn.prepareStatement("SELECT SEND_TO FROM TB_EMAIL TE WHERE SEND_TO = ?");
			ps.setString(1, emailAddress);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("check email address exists occurrence error", e);
			return false;
		} finally {
			closeResource(conn, ps, rs);
		}
	}
	
	public synchronized static int insertAlink(List<AlinkBean> list, int cache) {
		if (CollectionsUtils.isEmpty(list)) {
			return 0;
		}
		try {
			int re = 0;
			int c = 0;
			conn = getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO TB_ALINK (USE, URL, EXTRACT_FROM, TITLE, SCHEME, INPUT_DATE) VALUES (?, ?, ?, ?, ?, ?)");
			for (AlinkBean bean : list) {
				c ++;
				ps.setString(1, Constant.NO_USE);
				ps.setString(2, bean.getUrl());
				ps.setString(3, bean.getExtractFrom());
				ps.setString(4, bean.getTitle());
				ps.setString(5, bean.getScheme());
				ps.setString(6, DateUtils.getCurrentTimeStamp());
				ps.addBatch();
				if (c % cache == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			re = list.size();
			log.info("A LINK INSERT  >>>  $$$ " + re + " $$$ alink data has been inserted into the database");
			return re;
		} catch (Exception e) {
			log.error("insert data to table tb_alink occurrence error", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public synchronized static int insertEmail(List<EmailBean> list, int cache) {
		if (CollectionsUtils.isEmpty(list)) {
			return 0;
		}
		try {
			int re = 0;
			int c = 0;
			conn = getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO TB_Email (SEND_TO, EXTRACT_FROM, INPUT_DATE) VALUES (?, ?, ?)");
			for (EmailBean bean : list) {
				c ++;
				ps.setString(1, bean.getSendTo());
				ps.setString(2, bean.getExtractFrom());
				ps.setString(3, DateUtils.getCurrentTimeStamp());
				ps.addBatch();
				if (c % cache == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			re = list.size();
			log.info("EMAIL INSERT  >>>  $$$ " + re + " $$$ email address data has been inserted into the database");
			return re;
		} catch (Exception e) {
			log.error("insert data to table tb_email occurrence error", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public synchronized static int updateAlinkUse(List<AlinkBean> list, int cache) {
		if (CollectionsUtils.isEmpty(list)) {
			return 0;
		}
		try {
			int re = 0;
			int c = 0;
			conn = getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE TB_ALINK SET USE = ? WHERE ID = ?");
			for (AlinkBean bean : list) {
				c ++;
				ps.setString(1, Constant.YES_USE);
				ps.setInt(2, bean.getId());
				ps.addBatch();
				if (c % cache == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			re = list.size();
			log.info("UPDATE  >>>  update $$$ " + re + " $$$ alink data set field use value to 'Y'");
			return re;
		} catch (Exception e) {
			log.error("update tb_alink occurrence error", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public synchronized static int updateSendEmailSuccess(List<EmailBean> list, int cache) {
		if (CollectionsUtils.isEmpty(list)) {
			return 0;
		}
		try {
			int re = 0;
			int c = 0;
			conn = getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE TB_EMAIL SET SEND_DATE = ?, SEND_FROM = ?, SEND_STATUS = ?, EMAIL_TYPE = ?, MSG_ID = ? WHERE ID = ?");
			for (EmailBean bean : list) {
				c ++;
				ps.setString(1, DateUtils.getCurrentTimeStamp());
				ps.setString(2, bean.getSendFrom());
				ps.setString(3, Constant.YES);
				ps.setString(4, bean.getEmailType());
				ps.setString(5, bean.getMsgId());
				ps.setInt(6, bean.getId());
				ps.addBatch();
				if (c % cache == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			re = list.size();
			log.info("UPDATE  >>>  $$$ " + re + " $$$ email address send success");
			return re;
		} catch (Exception e) {
			log.error("update tb_email send success occurrence error", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public synchronized static int updateSendEmailFail(List<EmailBean> list, int cache) {
		if (CollectionsUtils.isEmpty(list)) {
			return 0;
		}
		try {
			int re = 0;
			int c = 0;
			conn = getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE TB_EMAIL SET SEND_DATE = ?, SEND_FROM = ?, SEND_STATUS = ?, EMAIL_TYPE = ? WHERE ID = ?");
			for (EmailBean bean : list) {
				c ++;
				ps.setString(1, DateUtils.getCurrentTimeStamp());
				ps.setString(2, bean.getSendFrom());
				ps.setString(3, Constant.NO);
				ps.setString(4, bean.getEmailType());
				ps.setInt(5, bean.getId());
				ps.addBatch();
				if (c % cache == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			re = list.size();
			log.info("UPDATE  >>>  $$$ " + re + " $$$ email address send fail");
			return re;
		} catch (Exception e) {
			log.error("update tb_email send fail occurrence error", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public synchronized static List<AlinkBean> getNoUseAlinkData(int noUseListSize, String sql) {
		if (!StringUtils.isNotEmpty(sql)) {
			sql = "SELECT ID, URL FROM TB_ALINK WHERE USE = 'N'";
		}
		try {
			List<AlinkBean> list = new ArrayList<AlinkBean>();
			List<String> urllist = new ArrayList<String>();
			AlinkBean bean = null;
			int c = 0;
			conn = getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				c = list.size();
				bean = new AlinkBean();
				bean.setId(rs.getInt(1));
				bean.setUrl(rs.getString(2));
				if (c < noUseListSize && !urllist.contains(bean.getUrl())) {
					urllist.add(bean.getUrl());
					list.add(bean);
				} else {
					break;
				}
			}
			urllist = null;
			return list;
		} catch (SQLException e) {
			log.error("list table TB_ALINK occurrence error", e);
			return null;
		} finally {
			closeResource(conn, ps, rs);
		}
	}
	
	/**
	 * 获取待发送的邮件地址 包含 id, 被发送人邮箱地址
	 * @param send_email_size 获取的数量
	 * @param sql 查询sql
	 * @return
	 */
	public synchronized static List<EmailBean> getEmailAddress(int send_email_size, String sql) {
		if (!StringUtils.isNotEmpty(sql)) {
			sql = "SELECT ID, SEND_TO FROM TB_EMAIL WHERE SEND_DATE IS NULL";
		}
		try {
			List<EmailBean> list = new ArrayList<EmailBean>();
			List<String> emaillist = new ArrayList<String>();
			EmailBean bean = null;
			int c = 0;
			conn = getConn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				c = list.size();
				bean = new EmailBean();
				bean.setId(rs.getInt(1));
				bean.setSendTo(rs.getString(2));
				if (c < send_email_size) {
					if (!emaillist.contains(bean.getSendTo()) && CommonHelper.isValidEmail(bean.getSendTo())) {
						emaillist.add(bean.getSendTo());
						list.add(bean);
					}
				} else {
					break;
				}
			}
			emaillist = null;
			return list;
		} catch (SQLException e) {
			log.error("get send email address occurrence error", e);
			return null;
		} finally {
			closeResource(conn, ps, rs);
		}
	}
	
	/**
	 * 获取发送人的实体信息
	 * @return
	 */
	public synchronized static SendMsgBean getSendMsgBean() {
		try {
			SendMsgBean bean = null;
			conn = getConn(Constant.SQLITE_SENDER_URL);
			ps = conn.prepareStatement("SELECT I.ID, I.MAILADDRESS, I.SENDCONTENT, I.SENDSUBJECT, I.SENDERNAME, I.PASSWORD FROM TB_SENDMAILINFO I WHERE I.STATUS = 'Y' AND I.SENDSTATUS = 'N' and i.MAILADDRESS like '%163.com' ORDER BY RANDOM() LIMIT 1");
			rs = ps.executeQuery();
			if (rs.next()) {
				bean = new SendMsgBean();
				bean.setId(rs.getInt(1));
				bean.setSendfrom(rs.getString(2));
				bean.setMailcontent(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setSendname(rs.getString(5));
				bean.setSendpassword(rs.getString(6));
				return bean;
			}
			return null;
		} catch (SQLException e) {
			log.error("getSendMsgBean occurrence error", e);
			return null;
		} finally {
			closeResource(conn, ps, rs);
		}
	}
	
	public synchronized static int updateSenderInfo(SendMsgBean bean) {
		try {
			int re = 0;
			conn = getConn(Constant.SQLITE_SENDER_URL);
			conn.setAutoCommit(false);
			if (bean != null) {
				ps = conn.prepareStatement("UPDATE TB_SENDMAILINFO SET SENDSTATUS = ?, SENDDATE = ? WHERE ID = ?");
				ps.setString(1, Constant.YES_USE);
				ps.setString(2, DateUtils.getCurrentDateTime());
				ps.setInt(3, bean.getId());
				
				re = ps.executeUpdate();
				conn.commit();
			} else {
				ps = conn.prepareStatement("UPDATE TB_SENDMAILINFO SET SENDDATE = NULL, SENDSTATUS = ?, RESETDATE = ?");
				ps.setString(1, Constant.NO_USE);
				ps.setString(2, DateUtils.getCurrentDateTime());
				
				re = ps.executeUpdate();
				conn.commit();
			}
			log.info("UPDATE  >>>  update $$$ " + re + " $$$ SenderInfo");
			return re;
		} catch (Exception e) {
			log.error("update updateSenderInfo occurrence error", e);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
}
