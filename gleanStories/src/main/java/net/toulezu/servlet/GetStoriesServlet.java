package net.toulezu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.toulezu.bean.GhostStoryBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ckjava.utils.StringUtils;


public class GetStoriesServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter pw = response.getWriter();
		final String method = StringUtils.getStr(request.getParameter("method"));
		if (method.equals("categoryList")) { // 获取所有的分类
			pw.write(JSON.toJSONString(outStoriesMap.get("categoryMap"), SerializerFeature.WriteClassName));
		} else if (method.equals("categoryStoryList")) { // 获取分类对应的故事
			String categoryCode = StringUtils.getStr(request.getParameter("categoryCode"));
			Map<String, ArrayList<GhostStoryBean>> categoryStoryMap = (Map<String, ArrayList<GhostStoryBean>>) outStoriesMap.get("categoryStoryMap");
			pw.write(JSON.toJSONString(categoryStoryMap.get(categoryCode), SerializerFeature.WriteClassName));
		}
	}

}
