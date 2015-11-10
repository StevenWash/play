package net.androidla.html;

import java.util.ArrayList;
import java.util.List;

import net.androidla.common.CommonHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.SimpleNodeIterator;

public class GetImg extends CommonHelper {
	private static Log log = LogFactory.getLog(GetEmailAddress.class);
	
	public static List<String> getImgSrc(String htmlcontent) {
		if (htmlcontent == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		List<String> temp = new ArrayList<String>();
		TagNode node = null;
		String src = null;
		try {
			for (SimpleNodeIterator it = createParser(htmlcontent).parse(new TagNameFilter("img")).elements(); it.hasMoreNodes();) {
				node = (TagNode) it.nextNode();
				src = node.getAttribute("src");
				if (src != null && !temp.contains(src)) {
					temp.add(src);
					list.add(src);
				}
			}
			temp = null;
			return list;
		} catch (Exception e) {
			log.error("get imgs from htmlcontent occurrence error", e);
		}
		return null;
	}
	
}
