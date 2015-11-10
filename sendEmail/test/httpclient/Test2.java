package httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Test2 {
	public static void main(String[] args) {
		DefaultHttpClient hc = new DefaultHttpClient();
		hc.getCredentialsProvider().setCredentials(new AuthScope("http://hi.baidu.com/", 80), new UsernamePasswordCredentials("svsechen", "cc198789"));
		
		HttpPost post = new HttpPost("https://passport.baidu.com/?login");
		try {
			HttpResponse res = hc.execute(post);
			HttpEntity entity = res.getEntity();
			System.out.println(res.getStatusLine());
			
			
			EntityUtils.consume(entity);
			hc.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
