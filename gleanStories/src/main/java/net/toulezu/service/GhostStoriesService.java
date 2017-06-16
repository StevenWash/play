package net.toulezu.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ckjava.utils.DateUtils;
import com.ckjava.utils.FileUtils;
import com.ckjava.utils.HTMLUtils;
import com.ckjava.utils.StringUtils;

import net.toulezu.bean.GhostStoryBean;

public class GhostStoriesService {
	private static Map<String, String> topicCodeMap = new HashMap<String, String>();
	static {
		topicCodeMap.put("医院", "yiyuan");
		topicCodeMap.put("恐怖", "kongbu");
		topicCodeMap.put("校园", "xiaoyuan");
		topicCodeMap.put("灵异", "lingyi");
		topicCodeMap.put("真实", "zhenshi");
		topicCodeMap.put("短小", "duanxiao");
		topicCodeMap.put("网络", "wangluo");
		topicCodeMap.put("民间", "minjian");
		topicCodeMap.put("内涵", "neihan");
		topicCodeMap.put("乡村", "xiangcun");
		topicCodeMap.put("每夜一个", "meiye");
	}
	
	public Map<String, String> getTopicMap() {
		final Map<String, String> topicMap = new HashMap<String, String>();
		topicMap.put("医院", "http://www.guijj.com/yy-3-1/");
		topicMap.put("恐怖", "http://www.guijj.com/kb-4-1/");
		topicMap.put("校园", "http://www.guijj.com/xy-5-1/");
		topicMap.put("灵异", "http://www.guijj.com/ly-6-1/");
		topicMap.put("真实", "http://www.guijj.com/list-11-1/");
		topicMap.put("短小", "http://www.guijj.com/duanxiao/");
		topicMap.put("网络", "http://www.guijj.com/wangluo/");
		topicMap.put("民间", "http://www.guijj.com/minjian/");
		topicMap.put("内涵", "http://www.guijj.com/neihan/");
		topicMap.put("乡村", "http://www.guijj.com/xiangcun/");
		topicMap.put("每夜一个", "http://www.guijj.com/meiye/");
		return topicMap;
	}
	
	/**
	 * 从指定url中读取故事
	 * @param paraMap
	 * @throws Exception
	 */
	public void getGhostStoriesToList(final Map<String, Object> paraMap, final String today, final List<GhostStoryBean> newStoriesList, final List<String> existsTitlesList) throws Exception {
		final String url = StringUtils.getStr(paraMap.get("url"));
		final String topic = StringUtils.getStr(paraMap.get("topic"));
		
		Document doc = Jsoup.connect(url).timeout(5*1000).get();
		Elements trs = doc.select("dl#w dd ul li").select("li:not(.b)");
		for (int i = 0, c = trs.size(); i < c; i++) {
			GhostStoryBean bean = new GhostStoryBean();

			// 字数
			Element letterElement = trs.get(i).select("s").last();
			bean.setLetterCount(letterElement.text());
			
			// 分类
			Element catatoryElement = trs.get(i).select("u").last();
			if (!catatoryElement.text().contains(topic)) {
				continue;
			}
			bean.setCategory(topic);
			bean.setCategoryCode(topicCodeMap.get(topic));
			
			// 作者
			Element authorElement = trs.get(i).select("i").first();
			bean.setAuthor(authorElement.text());
			
			// 更新时间
			Element updateElement = trs.get(i).select("i").last();
			if (!updateElement.text().substring(0, 8).equals(today)) {
				continue;
			}
			bean.setDate(today);
			
			Element linkElement = trs.get(i).select("a").last();
			String titleString = linkElement.text();
			if (existsTitlesList.contains(titleString)) {
				continue;
			} else {
				existsTitlesList.add(titleString.trim());
			}
			String hrefString = linkElement.attr("abs:href");
			Document contentDoc = Jsoup.connect(hrefString).get();
			Element contentElement = contentDoc.select("dl#ar dd div#as").last();
			
			// 故事标题
			bean.setTitle(titleString.trim());
			// 故事的绝对地址
			bean.setUrl(hrefString);
			// 故事内容
			bean.setTextContent(HTMLUtils.htmlEscape(contentElement.html()));
			
			newStoriesList.add(bean);
		}
	}
	
	/**
	 * 读取目录下的json文件到Map中
	 * @param dataMap
	 * @throws Exception
	 */
	public void readToMap(final Map<String, String> dataMap, final String todayTitle) throws Exception {
		final List<File> fileList = new ArrayList<File>();
		FileUtils.getFileToList("datas"+File.separator+todayTitle+File.separator, fileList, "json");
		for (File file : fileList) {
			String key = file.getName().concat(todayTitle);
			if (!dataMap.containsKey(key)) {
				dataMap.put(key, FileUtils.readFileContent(file, "utf-8"));
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("http://www.guijj.com/xiangcun/").get();
			
			Elements trs = doc.select("dl#w dd ul li").select("li:not(.b)");
			GhostStoryBean bean = new GhostStoryBean();
			
			//final String todayString = DateUtils.getDateString(new Date(), "yy-MM-dd");
			final String todayString = "17-05-26";
			final String catString = "乡村";
			for (int i = 0; i < trs.size(); i++) {
				Elements ss = trs.get(i).select("s");
				//System.out.print("序号:" + ss.get(0).text());
				
				//System.out.print("  字数:" + ss.get(1).text());
				bean.setLetterCount(ss.get(1).text());
				Elements catatory = trs.get(i).select("u");
				//System.out.print("  分类:" + catatory.get(0).text());
				String category = catatory.get(0).text();
				if (!category.contains(catString)) {
					continue;
				}
				bean.setCategory(category);
				
				
				Elements date = trs.get(i).select("i");
				//System.out.print("  作者:" + date.get(0).text());
				bean.setAuthor(date.get(0).text());
				//System.out.print("  时间:" + date.get(1).text());
				String updateDate = date.get(1).text();
				if (!updateDate.substring(0, 8).equals(todayString)) {
					continue;
				}
				bean.setDate(updateDate);
				
				
				Elements links = trs.get(i).select("a");
				//System.out.print("  标题:" + links.text());
				String hrefsString = links.attr("abs:href");
				Document contentDoc = Jsoup.connect(hrefsString).get();
				Elements data = contentDoc.select("dl#ar dd div#as");
				
				//System.out.print("  链接:" + links.attr("abs:href"));
				
				//System.out.println("  内容:" + data.get(0).html());
				
				bean.setTitle(links.text());
				bean.setUrl(links.attr("abs:href"));
				try {
					bean.setTextContent(HTMLUtils.htmlEscape(data.get(0).html()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				//JSON.toJSONString(bean, SerializerFeature.WriteClassName);
				
			//	FileUtils.deleteDirectory("datas"+File.separator);
				
				FileUtils.writeStringToFile(new File("datas"+File.separator+DateUtils.getDateString(new Date(), DateUtils.DATE)+File.separator+bean.getTitle()+".json"), JSON.toJSONString(bean, SerializerFeature.WriteClassName), false, "utf-8");
				System.out.println("done.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
