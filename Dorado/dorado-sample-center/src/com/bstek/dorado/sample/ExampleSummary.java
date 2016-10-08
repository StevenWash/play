package com.bstek.dorado.sample;

import java.io.PrintWriter;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.context.Context;
import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.sample.dao.ExampleDao;
import com.bstek.dorado.sample.entity.Example;
import com.bstek.dorado.util.PathUtils;

@Component
public class ExampleSummary {

	@Resource
	private ExampleDao exampleDao;

	@DataProvider
	public Example getExample(long id) {
		return exampleDao.get(id);
	}

	public void buildCloudoLink(PrintWriter writer, Context context)
			throws Exception {
		Example example = (Example) context.get("example");
		String url = example.getUrl();
		if (PathUtils.match("*com.bstek.dorado.sample.*.d", url)) {
			String viewName = StringUtils.substringBeforeLast(url, ".d").replace(".", "/");
			context.put("cloudoLink", "dorado/cloudo/vidor?url=" + viewName+ ".view.xml");
		}

	}

	public void outputContent(PrintWriter writer, Context context)
			throws Exception {
		Example example = (Example) context.get("example");

		String summary = StringUtils.defaultIfEmpty(example.getSummary(),
				"''(no description)''");
		String html = BikiUtils.render(summary);
		writer.append(html);
	}
}
