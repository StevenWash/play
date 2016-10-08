package com.bstek.dorado.sample.interceptor;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;
import com.bstek.dorado.data.provider.Page;
import com.bstek.dorado.sample.dao.CategoryDao;
import com.bstek.dorado.sample.entity.Category;
import com.bstek.dorado.sample.entity.Product;

@Component
public class CategoryInterceptor {

	@Resource
	private CategoryDao categoryDao;

	@Resource
	private ProductInterceptor productService;

	@DataProvider
	public Collection<Category> getAll() {
		return categoryDao.getAll();
	}

	@DataProvider
	public void getAll(Page<Category> page) {
		categoryDao.getAll(page);
	}

	@DataProvider
	public Category getCategory(long id) {
		return categoryDao.get(id);
	}

	@DataProvider
	public Collection<Category> getCategoriesByParent(long parentId) {
		return categoryDao.find("from Category where parent.id=" + parentId);
	}

	@DataResolver
	@Transactional
	public void saveAll(Collection<Category> categories) {
		for (Category category : categories) {
			EntityState state = categoryDao.persistEntity(category);
			if (EntityState.isVisible(state)) {
				Collection<Product> products = category.getProducts();
				if (products != null) {
					for (Product product : products) {
						if (EntityState.NEW.equals(EntityUtils
								.getState(product))) {
							product.setCategoryId(category.getId());
						}
					}
					productService.saveAll(products);
				}
			}
		}
	}
}
