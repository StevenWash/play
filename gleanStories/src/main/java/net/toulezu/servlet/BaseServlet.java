package net.toulezu.servlet;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ckjava.utils.DateUtils;
import com.ckjava.utils.FileUtils;
import com.ckjava.utils.StringUtils;

import net.toulezu.bean.GhostStoryBean;
import net.toulezu.service.GhostStoriesService;

@SuppressWarnings({"unchecked","rawtypes"})
public class BaseServlet extends HttpServlet implements ServletContextListener {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(BaseServlet.class);
	
	protected static final Map gleanStoriesMap = Collections.synchronizedMap(new HashMap());
	protected static final Map outStoriesMap = Collections.synchronizedMap(new HashMap());
	
	private GhostStoriesService ghostStoriesService;
	
	public void contextDestroyed(ServletContextEvent arg0) {
		gleanStoriesMap.clear();
		outStoriesMap.clear();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		ghostStoriesService = new GhostStoriesService();
		
		new Timer().schedule(ghostStoriesTimer, 0, 2*60*60*1000);
		new Timer().schedule(putStoriesToMapTimer, 0, 1*60*60*1000);
		log.info("后台定时更新故事线程已经启动");
	}
	
	public TimerTask ghostStoriesTimer = new TimerTask() {
		@Override
		public void run() {
			try {
				log.info("开始抓取当天的故事");
				
				final String todayTitle = DateUtils.getDateString(new Date(), DateUtils.DATE);
			//	final String todayTitle = "2015-04-29";
				final String today = todayTitle.substring(2);
				
				final List<GhostStoryBean> storiesList = new ArrayList<GhostStoryBean>();
				final List<GhostStoryBean> existsStoriesList = gleanStoriesMap.get("stories.json") != null ? (List<GhostStoryBean>) JSON.parse(gleanStoriesMap.get("stories.json").toString()) : new ArrayList<GhostStoryBean>();
				final List<String> existsTitlesList = gleanStoriesMap.get("storiestitles.json") != null ? (List<String>) JSON.parse(gleanStoriesMap.get("storiestitles.json").toString()) : new ArrayList<String>();
				for (Map.Entry entry: ghostStoriesService.getTopicMap().entrySet()) {
					Map paraMap = new HashMap<String, Object>();
					paraMap.put("topic", entry.getKey());
					paraMap.put("url", entry.getValue());
					ghostStoriesService.getGhostStoriesToList(paraMap, today, storiesList, existsTitlesList);
				}
				if (!storiesList.isEmpty()) {
					existsStoriesList.addAll(storiesList);
					FileUtils.writeStringToFile(new File("datas"+File.separator+todayTitle+File.separator+"stories.json"), JSON.toJSONString(existsStoriesList, SerializerFeature.WriteClassName), false, "utf-8");
					FileUtils.writeStringToFile(new File("datas"+File.separator+todayTitle+File.separator+"storiestitles.json"), JSON.toJSONString(existsTitlesList, SerializerFeature.WriteClassName), false, "utf-8");
				}
				
				log.info("结束抓取当天的故事");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("执行定时任务出现异常！");
			}
		}
	};
	
	public TimerTask putStoriesToMapTimer = new TimerTask() {
		@Override
		public void run() {
			try {
				log.info("开始将故事读取到Map中");
				
				 // 读取7天的数据
				int dayOffset = 0;
				outStoriesMap.clear();
				while (dayOffset > -7) {
					ghostStoriesService.readToMap(outStoriesMap, DateUtils.getAssignDay(dayOffset));
					dayOffset --;
				}
				
				// 把数据全部存放到 totoalStoriesList 中
				final List<GhostStoryBean> totoalStoriesList = new ArrayList<GhostStoryBean>();
				for (Iterator it = outStoriesMap.keySet().iterator(); it.hasNext();) {
					String keyString = StringUtils.getStr(it.next());
					if (keyString.contains("stories.json")) {
						totoalStoriesList.addAll((List<GhostStoryBean>) JSON.parse(outStoriesMap.get(keyString).toString()));
					}
				}
				
				// 找出数据中的所有分类
				final Map<String, String> categoryMap = new HashMap<String, String>();
				final List<String> tempCategoryCodeList = new ArrayList<String>();
				for (GhostStoryBean ghostStoryBean : totoalStoriesList) {
					String tempCategory = ghostStoryBean.getCategory();
					String tempCategoryCode = ghostStoryBean.getCategoryCode();
					if (!tempCategoryCodeList.contains(tempCategoryCode)) {
						categoryMap.put(tempCategory, tempCategoryCode);
						tempCategoryCodeList.add(tempCategoryCode);
					}
				}
				
				// 找出每个分类对应的所有故事
				final Map<String, ArrayList<GhostStoryBean>> categoryStoryMap = new HashMap<String, ArrayList<GhostStoryBean>>();
				for (String categoryCode : tempCategoryCodeList) {
					ArrayList<GhostStoryBean> categoryList = new ArrayList<GhostStoryBean>();
					categoryStoryMap.put(categoryCode, categoryList);
					for (GhostStoryBean ghostStoryBean : totoalStoriesList) {
						String tempCategoryCode = ghostStoryBean.getCategoryCode();
						if (tempCategoryCode.equals(categoryCode)) {
							categoryList.add(ghostStoryBean);
						}
					}
				}
				
				// 将 所有的分类 和 所有的分类对应的故事 写入到输出Map中
 				outStoriesMap.put("categoryMap", categoryMap);
 				outStoriesMap.put("categoryStoryMap", categoryStoryMap);
				
				log.info("结束将故事读取到Map中");
			} catch (Exception e) {
				e.printStackTrace();
				log.error("执行定时任务出现异常！");
			}
		}
	};


}
