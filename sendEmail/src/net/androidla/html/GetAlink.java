package net.androidla.html;

import java.util.ArrayList;
import java.util.List;

import net.androidla.common.CommonHelper;
import net.androidla.html.bean.AlinkBean;
import net.androidla.sqlite.SqliteHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.SimpleNodeIterator;

public class GetAlink extends CommonHelper {
	private static Log log = LogFactory.getLog(GetAlink.class);
	
	public static List<AlinkBean> getAlinkList(String htmlcontent, String extractFrom) {
		if (htmlcontent == null) {
			return null;
		}
		log.info("A LINK EXTRACT >>> start extract a link data from htmlcontent <<<");
		try {
			List<AlinkBean> list = new ArrayList<AlinkBean>();
			List<String> temp = new ArrayList<String>();
			AlinkBean abean = null;
			TagNode node = null;
			String href = null;
			for (SimpleNodeIterator it = createParser(htmlcontent).parse(new TagNameFilter("a")).elements(); it.hasMoreNodes();) {
				node = (TagNode) it.nextNode();
				href = node.getAttribute("href");
				if (href != null) {
					if (checkIsAlink(href) && !SqliteHelper.isAlinkExists(href) && !temp.contains(href)) {
						temp.add(href);
						abean = new AlinkBean();
						abean.setUrl(href);
						abean.setExtractFrom(extractFrom);
						abean.setScheme(href.startsWith("http") ? "http" : "https");
						abean.setTitle(node.getAttribute("title"));
						list.add(abean);
					}
				}
			}
			temp = null;
			log.info("A LINK EXTRACT <<< end extract a link data from htmlcontent >>>");
			return list;
		} catch (Exception e) {
			log.error("get a link href occurrence error", e);
			return null;
		}
	}
	
}
