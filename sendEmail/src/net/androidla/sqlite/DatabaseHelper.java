package net.androidla.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.androidla.common.CommonHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DatabaseHelper extends CommonHelper {
	protected static Log log = LogFactory.getLog(DatabaseHelper.class);
	protected static Connection conn = null;
	protected static PreparedStatement ps = null;
	protected static ResultSet rs = null;
	
	public static Connection getConn() {
		try {
			Class.forName(pro.getProperty("sqlite_class"));
			return DriverManager.getConnection(getParam("sqlite_url"), getParam("sqlite_name"), getParam("sqlite_pwd"));
		} catch (Exception e) {
			log.error("get Connection occurrence error", e);
			return null;
		}
	}
	
	public static Connection getConn(String sender) {
		try {
			Class.forName(pro.getProperty("sqlite_class"));
			return DriverManager.getConnection(getParam(sender), getParam("sqlite_name"), getParam("sqlite_pwd"));
		} catch (Exception e) {
			log.error("get sender Connection occurrence error", e);
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
	
}
