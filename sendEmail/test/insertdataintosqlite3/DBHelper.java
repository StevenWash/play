package insertdataintosqlite3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.androidla.util.CollectionsUtils;
import net.androidla.util.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DBHelper {
	private static Log log = LogFactory.getLog(DBHelper.class);
	private static Connection conn = null;
	private static PreparedStatement ps = null;
	
	public static Connection getConn() {
		try {
			Class.forName("org.sqlite.JDBC");
			return DriverManager.getConnection("jdbc:sqlite:databases/sqlite/tianya.db", "svsechen", "cc198789");
		} catch (Exception e) {
			log.error("get Connection occurrence error", e);
			return null;
		}
	}
	
	public static void closeResource(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				log.error("close Connection occurrence error", e);
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				log.error("close Statement occurrence error", e);
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("close ResultSet occurrence error", e);
			}
		}
	}
	
	public static void createTBEMAIL() {
		try {
			conn = getConn();
			ps = conn.prepareStatement("create table if not exists tb_email (id integer primary key, send_to varchar(300), extract_from varchar(300), input_date varchar(30), send_date varchar(30), send_from varchar(200), send_status varchar(1), email_type varchar(10), msg_id varchar(30))");
			ps.execute();
		} catch (Exception e) {
			log.error("create table tb_email occurrence error", e);
		} finally {
			closeResource(conn, ps, null);
		}
	}
	
	public static int insertData(List<DataBean> list, int dbcache) {
		if (CollectionsUtils.isEmpty(list)) {
			return 0;
		}
		try {
			int re = 0;
			int c = 0;
			conn = getConn();
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO TB_EMAIL (SEND_TO, EXTRACT_FROM, INPUT_DATE) VALUES (?, ?, ?)");
			for (DataBean bean : list) {
				c ++;
				ps.setString(1, bean.getSendto());
				ps.setString(2, bean.getExtractfrom());
				ps.setString(3, DateUtils.getCurrentTimeStamp());
				ps.addBatch();
				if (c % dbcache == 0) {
					ps.executeBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			re = list.size();
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
	
	public static void main(String[] args) {
		if (getConn() != null) {
			System.out.println("dddd");
		}
	}
}
