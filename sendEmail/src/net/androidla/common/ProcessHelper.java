package net.androidla.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import net.androidla.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ProcessHelper {
	private static Log log = LogFactory.getLog(ProcessHelper.class);
	
	public static void startMailServer() {
		try {
			Process run = Runtime.getRuntime().exec("cmd /c net.exe START hMailServer");
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
	
	public static void stopMailServer() {
		try {
			Process run = Runtime.getRuntime().exec("cmd /c net.exe STOP hMailServer");
	        InputStreamReader isr = new InputStreamReader(run.getInputStream(), "GBK");
	        BufferedReader br = new BufferedReader(isr);
	        String temp = null;
	        while ((temp = br.readLine()) != null) {
	        	if (StringUtils.isNotEmpty(temp)) {
	        		log.info(">>> " + temp + " <<<");
	        	}
	        }
		} catch (Exception e) {
			log.error("stop hMailServer service occurrence error", e);
		}
	}
	
}
