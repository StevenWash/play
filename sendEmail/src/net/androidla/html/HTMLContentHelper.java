package net.androidla.html;

import java.net.HttpURLConnection;

import net.androidla.util.IOUtils;

import org.apache.http.NoHttpResponseException;
import org.apache.http.client.methods.HttpGet;

public class HTMLContentHelper extends AbStractHTMLContent {
	
	public String getHTMLContent(String target) {
		log.info(">>> start get html context from url <" + target + "> <<<");
		get = new HttpGet(target);
		try {
			resp = hc.execute(get);
			if (resp == null) {
				return null;
			}
			if (resp.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK ) {
				entity = resp.getEntity();
				if (entity != null) {
					log.info(">>> end get html context from url <" + target + "> <<<");
					return IOUtils.getString(entity.getContent(), encoding);
				}
			}
			return null;
		} catch (Exception e) {
			if (e instanceof NoHttpResponseException) {
				log.warn("***********  The target server failed to respond  ***********");
				return null;
			}
			log.error("get target " + target + " html content occurrence error", e);
			return null;
		}
	}

	public static void main(String[] args) {
		AbStractHTMLContent helper = new HTMLContentHelper();
		System.out.println(helper.getHTMLContent("http://baike.baidu.com/view/1347241.htm"));
	}
}
