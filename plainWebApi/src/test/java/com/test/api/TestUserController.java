package com.test.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;
import com.test.api.controller.UserController;
import com.test.api.util.StringUtil;

public class TestUserController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected UserController userController;

	protected MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	public void testFindPageUsers() throws Exception {
		logger.info("uid is {}", uid);

		ResultActions ra = mockMvc.perform(MockMvcRequestBuilders.get("/user/user_info")
				.accept(MediaType.APPLICATION_JSON).param("test", "1").param("uid", uid));
		MvcResult mr = ra.andReturn();
		MockHttpServletResponse resp = mr.getResponse();
		Assert.assertEquals(resp.getStatus(), 200);

		JSONObject obj = JSONObject.parseObject(resp.getContentAsString());
		JSONObject dataObj = obj.getJSONObject("data");
		Assert.assertEquals(StringUtil.getStr(dataObj.get("uid")), uid);
	}

}
