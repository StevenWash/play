package com.bstek.dorado.sample.interceptor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.core.io.Resource;
import com.bstek.dorado.sample.entity.Shoes;
import com.bstek.dorado.util.PathUtils;
import com.bstek.dorado.web.DoradoContext;

@Component
public class ShoesInterceptor {

	@DataProvider
	public Collection<Shoes> getAll(DoradoContext context) throws Exception {
		Collection<Shoes> items = new ArrayList<Shoes>();

		Resource resource = context
				.getResource("classpath:com/bstek/dorado/sample/touch/shoes/shoes.js");
		InputStream in = resource.getInputStream();
		try {
			ObjectMapper mapper = new ObjectMapper();
			items = mapper.readValue(in, new TypeReference<List<Shoes>>() {
			});

			for (Shoes item : items) {
				item.setImage(PathUtils
						.concatPath(">dorado/res/com/bstek/dorado/sample/touch/shoes/"
								+ item.getId() + ".jpg"));
			}
		} finally {
			in.close();
		}
		return items;
	}
}
