package httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Test5 {
	public static void main(String[] args) {
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpHost target = new HttpHost("www.baidu.com", 80, "http");
			HttpGet get = new HttpGet("/");
			System.out.println("execute request to = " + target);
			HttpResponse resp = hc.execute(target, get);
			HttpEntity entity = resp.getEntity();
			
			System.out.println("status = " + resp.getStatusLine());
			
			Header[] header = resp.getAllHeaders();
			for (int i = 0; i < header.length; i++) {
				System.out.println("----------------------------------------- begin");
				System.out.println(header[i]);
				System.out.println("----------------------------------------- end");
			}
			if (entity != null) {
				System.out.println(EntityUtils.toString(entity));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hc.getConnectionManager().shutdown();
		}
	}
}
