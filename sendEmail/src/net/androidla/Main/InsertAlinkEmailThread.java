package net.androidla.Main;

import java.util.List;

import net.androidla.common.CommonHelper;
import net.androidla.html.AbStractHTMLContent;
import net.androidla.html.GetAlink;
import net.androidla.html.GetEmailAddress;
import net.androidla.html.HTMLContentHelper;
import net.androidla.html.bean.AlinkBean;
import net.androidla.sendemail.bean.EmailBean;
import net.androidla.sqlite.SqliteHelper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InsertAlinkEmailThread extends CommonHelper implements Runnable {
	private static Log log = LogFactory.getLog(InsertAlinkEmailThread.class);
	private int cache = Integer.parseInt(getParam("sqlite_cache"));
	private int no_use_list_size = Integer.parseInt(getParam("no_use_list_size"));
	private String sqlite_no_use_url_sql = getParam("sqlite_no_use_url_sql");
	private int count = 0;
	private boolean flag = true;
	private String url = null;
	private String htmlcontext = null;
	private List<AlinkBean> alist = null;
	private List<EmailBean> elist = null;
	private List<AlinkBean> nouselist = null;
	private AbStractHTMLContent htmlHelper = new HTMLContentHelper();
	@Override
	public void run() {
		while (flag) {
			nouselist = SqliteHelper.getNoUseAlinkData(no_use_list_size, sqlite_no_use_url_sql);
			count = nouselist.size();
			if (count > 0) {
				log.info(">>> start analysis " + count + " url <<<");
				for (AlinkBean bean : nouselist) {
					url = bean.getUrl().trim();
					htmlcontext = htmlHelper.getHTMLContent(url);

					alist = GetAlink.getAlinkList(htmlcontext, url);
					SqliteHelper.insertAlink(alist, cache);
					alist.clear();

					elist = GetEmailAddress.getEmailUrl(htmlcontext, url);
					SqliteHelper.insertEmail(elist, cache);
					elist.clear();
				}
				SqliteHelper.updateAlinkUse(nouselist, cache);
				nouselist.clear();
				log.info("<<< end analysis " + count + " url >>>");
			} else {
				flag = false;
			}
		}
	}
}
