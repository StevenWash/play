package com.toulezu.test.springboot;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.toulezu.test.service.DBService;

@ContextConfiguration(classes = {DBService.class, SpringPropertiesConfigure.class})
public class TestDBService extends BaseTest {

	@Autowired
	private DBService dBService;
	
	@Test
	public void testGetDBInfo() {
		String dbInfo = dBService.toString();
		Assert.assertNotNull(dbInfo);
		System.out.println(dbInfo);
	}
}
