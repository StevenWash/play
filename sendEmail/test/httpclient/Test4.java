package httpclient;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.util.EntityUtils;

public class Test4 {
	public static void main(String[] args) {
		ThreadSafeClientConnManager conn = new ThreadSafeClientConnManager();
		conn.setMaxTotal(100);
		HttpClient hc = new DefaultHttpClient(conn);
		
		String[] uritoget = {
			"http://jakarta.apache.org/",
	        "http://jakarta.apache.org/commons/",
	        "http://jakarta.apache.org/commons/httpclient/",
	        "http://svn.apache.org/viewvc/jakarta/httpcomponents/"
		};
		IdleConnectionEvictor connEvictor = new IdleConnectionEvictor(conn);
		connEvictor.start();
		
		
		try {
		for (int i = 0; i < uritoget.length; i++) {
			String uri = uritoget[i];
			HttpGet get = new HttpGet(uri);
			
			System.out.println("executing = " + uri);

				HttpResponse res = hc.execute(get);
				HttpEntity entity = res.getEntity();
				
				System.out.println("status = " + res.getStatusLine());
				
				if (entity != null) {
					System.out.println("content length = " + entity.getContentLength());
					
					EntityUtils.consume(entity);
				}
	
		}
		Thread.sleep(20000);
		connEvictor.shutdown();
		connEvictor.join();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			hc.getConnectionManager().shutdown();
		}
	}
	
	private static class IdleConnectionEvictor extends Thread {
		private final ClientConnectionManager conn;
		private volatile boolean shutdown;
		public IdleConnectionEvictor (ClientConnectionManager conn) {
			this.conn = conn;
		}
		@Override
		public void run() {
			try {
				while(!shutdown) {
					synchronized (this) {
						wait(5000);
						conn.closeExpiredConnections();
						conn.closeIdleConnections(5, TimeUnit.SECONDS);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
		}
	}
}

