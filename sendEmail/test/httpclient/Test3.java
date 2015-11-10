package httpclient;

import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Test3 {
	public static void main(String[] args) {
		InputStream is = Test3.class.getResourceAsStream("/httpclient/my.keystore");
		HttpClient hc = new DefaultHttpClient();
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(is, "nopassword".toCharArray());
			
			SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore);
			Scheme sch = new Scheme("https", 443, socketFactory);
			hc.getConnectionManager().getSchemeRegistry().register(sch);
			
			HttpGet get = new HttpGet("https://passport.baidu.com/?login");
			
			System.out.println("request = " + get.getRequestLine());
			
			HttpResponse res = hc.execute(get);
			HttpEntity entity = res.getEntity();
			
			System.out.println("response content length = " + entity.getContentLength());
			
			EntityUtils.consume(entity);
			hc.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
