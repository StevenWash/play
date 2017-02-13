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
        System.out.println("start main");
        System.out.println("class path = "+Start.class.getResource(""));
        
        // 测试从通过 assembly 插件打成的 tag.gz 中读取配置
        System.out.println("ChromeDriverPath = "+prop.getProperty("ChromeDriverPath"));
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
