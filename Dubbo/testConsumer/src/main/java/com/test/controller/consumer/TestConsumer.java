package com.test.controller.consumer;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.service.TestRegisterService;

@Controller
public class TestConsumer {
	
	@Resource
	private TestRegisterService testRegisterService;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public void hello(HttpServletRequest request, HttpServletResponse response) {
		try {
			String msg = request.getParameter("msg");
			String str = testRegisterService.sayHello(msg);
			PrintWriter pw = response.getWriter();
			pw.write(str);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
