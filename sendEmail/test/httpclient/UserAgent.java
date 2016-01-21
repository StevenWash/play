package httpclient;

import java.net.URLDecoder;

import net.androidla.common.CommonHelper;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class UserAgent {
	public static void main(String[] args) {
		HttpClient hc = new DefaultHttpClient();
		String link = CommonHelper.getParam("mlink");
		try {
			HttpGet get = new HttpGet(URLDecoder.decode(link, "UTF-8"));
			get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");
			
			System.out.println("url = " + get.getURI());
			
			HttpResponse resp = hc.execute(get);
			System.out.println(resp.getStatusLine());
			HttpEntity entity = resp.getEntity();
			Long l = entity.getContentLength();
			if (l > 0) {
				System.out.println("ok");
			} else {
				System.out.println("no content");
			}
			//System.out.println(HTMLUtil.getContent(entity));
			
			for (Header h : resp.getAllHeaders()) {
				System.out.println("header = " + h);
			}
			System.out.println(EntityUtils.getContentCharSet(entity));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
