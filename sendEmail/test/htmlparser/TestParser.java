package htmlparser;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.Properties;

import net.androidla.common.CommonHelper;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.SimpleNodeIterator;

public class TestParser {
	private static Properties pro = CommonHelper.getProperties();
	public static void main(String[] args) {
		try {
			URL url = new URL(pro.getProperty("mlink"));
			SocketAddress address = new InetSocketAddress(pro.getProperty("host"), Integer.parseInt(pro.getProperty("port")));
			Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
			URLConnection conn = url.openConnection(proxy);
			Authenticator.setDefault(new MyAuthenticator(pro.getProperty("username"), pro.getProperty("password")));
			
			conn.setConnectTimeout(Integer.parseInt(pro.getProperty("timeout")));
			Parser parser = new Parser(conn);
			
			NodeList nodeList = parser.parse(new TagNameFilter("A")); 
			System.out.println(nodeList.size());
			
			for (SimpleNodeIterator it = nodeList.elements(); it.hasMoreNodes(); ) {
				TagNode node = (TagNode) it.nextNode();
				String href = node.getAttribute("href");
				String dhref = URLDecoder.decode(href, "UTF-8");
				if (CommonHelper.checkIsAlink(dhref)) {
					System.out.println(dhref);	
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static class MyAuthenticator extends Authenticator {
		private String username;
	    private String password;
        public MyAuthenticator(String username, String password) {
            this.username = username;
            this.password = password;
        }
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password.toCharArray());
        }
	}
}
