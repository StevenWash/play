package httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * �� dict.cn ��վΪ�������
 * @author Winter Lau
 */
public class DictSpider {

	private final static HttpClient client = new DefaultHttpClient();
	
	public static void main(String[] args) throws IOException {
		login("<�û���>","<����>", false);
		get("http://www16.dict.cn/bdc/141");
	}
	
	/**
	 * ץȡ��ҳ
	 * @param url
	 * @throws IOException
	 */
	static void get(String url) throws IOException {
		HttpGet get = new HttpGet(url);
		HttpResponse response = client.execute(get);
		System.out.println(response.getStatusLine());
		HttpEntity entity = response.getEntity();
		dump(entity);
	}
	
	/**
	 * ִ�е�¼���
	 * @param user
	 * @param pwd
	 * @param debug
	 * @throws IOException
	 */
	static void login(String user, String pwd, boolean debug) throws IOException {
		HttpPost post = new HttpPost("http://dict.cn/login.php");
		post.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.3 (KHTML, like Gecko) Chrome/6.0.472.63 Safari/534.3");
		
		//��¼�?����Ϣ
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		qparams.add(new BasicNameValuePair("username", user));
		qparams.add(new BasicNameValuePair("password", pwd));
		qparams.add(new BasicNameValuePair("url", "http://www16.dict.cn/bdc/141"));
		qparams.add(new BasicNameValuePair("loginforever", "1"));
		
		UrlEncodedFormEntity params = new UrlEncodedFormEntity(qparams, "UTF-8");
		post.setEntity(params);

		// Execute the request
		HttpResponse response = client.execute(post);
		
		if(debug){
			// Examine the response status
			System.out.println(response.getStatusLine());
	
			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			
			dump(entity);
		}
	}

	/**
	 * ��ӡҳ��
	 * @param entity
	 * @throws IOException
	 */
	private static void dump(HttpEntity entity) throws IOException {
	}
	
}

