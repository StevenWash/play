package net.androidla.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.androidla.constant.Constant;
import net.androidla.html.bean.ProxyBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.Parser;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.lexer.Page;
import org.htmlparser.util.DefaultParserFeedback;
import org.htmlparser.util.ParserFeedback;

public class CommonHelper {
	private static Log log = LogFactory.getLog(CommonHelper.class);
	protected static Properties pro = null;
	
	static {
		InputStream is = CommonHelper.class.getClassLoader().getResourceAsStream(Constant.PROPERTIES_FILENAME);
		pro = new Properties();
		try {
			pro.load(is);
		} catch (IOException e) {
			log.error("get Properties occurrence error", e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getParam(String param) {
		return pro == null ? null : pro.getProperty(param, null);
	}
	
	public static Properties getProperties() {
		return pro == null ? null : pro;
	}
	
	public static Parser createParser(String inputHTML) {
        Lexer mLexer = new Lexer(new Page(inputHTML));
        return new Parser(mLexer, (ParserFeedback) new DefaultParserFeedback(DefaultParserFeedback.QUIET));
    }
	
	public static boolean checkIsAlink(String alink) {
		Pattern p = Pattern.compile(getParam("a_href"));
		Matcher m = p.matcher(alink);
		if (m.matches()) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是合法的 email 地址
	 * @param email 地址
	 * @return boolean true:是 false:不是 
	 */
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(getParam("email"));
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
        	return true;
        }
        return false;
	}
	
	public static ProxyBean initProxy() {
		ProxyBean pb = null;
		if (pro != null) {
			pb = new ProxyBean();
			pb.setHost(pro.getProperty("host"));
			pb.setPort(Integer.parseInt(pro.getProperty("port")));
			pb.setUsername(pro.getProperty("username"));
			pb.setPassword(pro.getProperty("password"));
			pb.setScheme(pro.getProperty("scheme"));
			pb.setTimeout(Integer.parseInt(pro.getProperty("timeout")));
			return pb;
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(isValidEmail("sdf@sdf@126.com"));
	}
	
}
