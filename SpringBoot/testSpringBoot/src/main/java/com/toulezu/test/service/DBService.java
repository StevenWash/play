package com.toulezu.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = { "classpath:jdbc.properties" })
public class DBService {
	@Value("${jdbc_driverClassName}")
	private String driverClassName;
	@Value("${jdbc_url}")
	private String url;
	@Value("${jdbc_username}")
	private String username;
	@Value("${jdbc_password}")
	private String password;
	
	@Override
	public String toString() {
		return "DBService [driverClassName=" + driverClassName + ", url=" + url + ", username=" + username
				+ ", password=" + password + "]";
	}
	
}
