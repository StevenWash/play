package com.test.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:spring/*-config.xml", "classpath:spring/*-servlet.xml" })
public class BaseController {
	
	protected static String uid;
	
	static {
		// 加载配置文件
		Properties config = new Properties();
		InputStream input = null;
		try {
			input = API.class.getClassLoader().getResourceAsStream("test/test.properties");
			Reader reader = new InputStreamReader(input, "UTF-8");
			config.load(reader);

			uid = config.getProperty("uid");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}

	// @Autowired
	// private WebApplicationContext wac;
	
}
