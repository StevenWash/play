package test.sample.sysinfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.core.DoradoAbout;

@Component
public class SystemInfo {
	
	@Expose
	public Properties getSystemInfo() {
		Properties pro = new Properties();
		pro.put("product", DoradoAbout.getProductTitle());
		pro.put("vendor", DoradoAbout.getVendor());
		pro.put("version", DoradoAbout.getVersion());
		pro.put("time", new SimpleDateFormat("yyyyƒÍMM‘¬dd»’ hh:mm:ss").format(new Date()));
		return pro;
	}
}
