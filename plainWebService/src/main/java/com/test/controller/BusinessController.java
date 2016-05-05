package com.test.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.bean.CommodityBean;

@RestController
@RequestMapping(value = "/business")
public class BusinessController {

	@ResponseBody
	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	public String add2Cart(@RequestBody CommodityBean commodity) {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/cart/{id}", method = RequestMethod.GET)
	public CommodityBean getCart(@PathVariable Integer id) {
		CommodityBean bean = new CommodityBean(id, "test", new BigDecimal("9.99"));
		return bean;
	}

}
