package com.toulezu.service.deploy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 启动类
 * @author Administrator
 *
 */
public class Start {
	
	public static Properties prop = getProperties();

    public static void main(String[] args) throws IOException {
    	com.alibaba.dubbo.container.Main.main(args);
    }
    
    public static Properties getProperties() {
		Properties prop = new Properties();
		InputStream is = null;
		try {
			is = Start.class.getClassLoader().getResourceAsStream("prop.properties");
			prop.load(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return prop;
	}
}
