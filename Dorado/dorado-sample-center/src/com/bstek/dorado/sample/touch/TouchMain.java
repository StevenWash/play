package com.bstek.dorado.sample.touch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.core.io.Resource;
import com.bstek.dorado.web.DoradoContext;

@Component
public class TouchMain {

	@DataProvider
	public Collection<TouchExample> getExamples(DoradoContext context)
			throws Exception {
		Collection<TouchExample> examples = new ArrayList<TouchExample>();

		Resource resource = context
				.getResource("classpath:com/bstek/dorado/sample/touch/examples.js");
		InputStream in = resource.getInputStream();
		try {
			ObjectMapper mapper = new ObjectMapper();
			examples = mapper.readValue(in,
					new TypeReference<List<TouchExample>>() {
					});
		} finally {
			in.close();
		}
		return examples;
	}
}
