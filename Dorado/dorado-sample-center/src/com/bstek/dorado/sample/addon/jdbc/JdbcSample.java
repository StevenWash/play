package com.bstek.dorado.sample.addon.jdbc;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.data.variant.Record;
import com.bstek.dorado.jdbc.iapi.IJdbcDao;
import com.bstek.dorado.jdbc.iapi.annotation.JdbcDaor;


@Component
public class JdbcSample {

	@Autowired
	@JdbcDaor
	private IJdbcDao jdbcDao;

	@DataProvider
	public Collection<Record> loadCategories() {
		return jdbcDao.query("CATEGORIES", null);
	}
}
