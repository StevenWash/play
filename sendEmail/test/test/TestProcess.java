package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.androidla.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestProcess {
	public static void main(String[] args) {
		new Thread(new StartMailServer()).start();
	}
}
class StartMailServer implements Runnable {
	private static Log log = LogFactory.getLog(StartMailServer.class);
	@Override
	public void run() {
		try {
			//, "cmd /k net.exe START hMailServer"
			String str = "cmd /c net.exe stop hMailServer"; //net.exe START hMailServer
			Process run = Runtime.getRuntime().exec(str);
	        InputStreamReader isr = new InputStreamReader(run.getInputStream(), "GBK");
	        BufferedReader br = new BufferedReader(isr);
	        String temp = null;
	        while ((temp = br.readLine()) != null) {
	        	if (StringUtils.isNotEmpty(temp)) {
	        		log.info(">>> " + temp + " <<<");
	        	}
	        }
		} catch (Exception e) {
			log.error("start hMailServer service occurrence error", e);
		}
	}
}