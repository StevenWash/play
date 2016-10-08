package com.bstek.dorado.sample.touch;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.sample.dao.ProductDao;
import com.bstek.dorado.sample.entity.Product;

@Component
public class TouchSimpleCRUD {
	@Resource
	private ProductDao productDao;

	@DataProvider
	public Collection<Product> getAll() throws Exception {
		return productDao.getAll();
	}

	@DataProvider
	public void getAll(Page<Product> page) throws Exception {
		productDao.getAll(page);
	}

	@DataResolver
	@Transactional
	public void saveAll(Collection<Product> products) {
		productDao.persistEntities(products);
	}
}