package httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class TestProxy {
	public static void main(String[] args) {
		DefaultHttpClient hc = new DefaultHttpClient();
		hc.getCredentialsProvider().setCredentials(new AuthScope("10.192.18.148", 6600), new UsernamePasswordCredentials("c_dianxiaoxiangmuzu-001", "Cpic12345"));
		HttpHost proxy = new HttpHost("10.192.18.148", 6600, "http");
		
		hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		HttpHost target = new HttpHost("www.hao123.com");
		HttpGet get = new HttpGet("/");
		System.out.println("executing request to " + target + " via " + proxy);
		try {
			HttpResponse resp = hc.execute(target, get);
			System.out.println("status = " + resp.getStatusLine());
			HttpEntity entity = resp.getEntity();
			Header[] headers = resp.getAllHeaders();
			for (Header h : headers) {
				System.out.println(h);
			}
			System.out.println(EntityUtils.getContentCharSet(entity));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hc.getConnectionManager().shutdown();
		}
	}
}
