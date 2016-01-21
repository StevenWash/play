package net.androidla.html;

import net.androidla.common.CommonHelper;
import net.androidla.constant.Constant;
import net.androidla.html.bean.ProxyBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;

public abstract class AbStractHTMLContent extends CommonHelper {
	protected static Log log = LogFactory.getLog(AbStractHTMLContent.class);
	
	private String use_proxy = pro.getProperty("use_proxy");
	protected String encoding = getParam("html_content_encoding");
	protected DefaultHttpClient hc = getDefaultHttpClient();
	protected HttpGet get = null;
	protected HttpResponse resp = null;
	protected HttpEntity entity = null;
	
	public abstract String getHTMLContent(String target);
	
	private DefaultHttpClient getDefaultHttpClient() {
		if (use_proxy.equals(Constant.YES)) {
			DefaultHttpClient hc = new DefaultHttpClient();
			ProxyBean pb = initProxy();
			hc.getCredentialsProvider().setCredentials(new AuthScope(pb.getHost(), pb.getPort()), new UsernamePasswordCredentials(pb.getUsername(), pb.getPassword()));
			hc.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost(pb.getHost(), pb.getPort(), pb.getScheme()));
			return hc;
		} else if (use_proxy.equals(Constant.NO)) {
			return new DefaultHttpClient();
		}
		return null;
	}

}
