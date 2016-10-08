package com.bstek.dorado.sample.touch;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.bstek.dorado.common.event.DefaultClientEvent;
import com.bstek.dorado.core.io.Resource;
import com.bstek.dorado.touch.widget.desktop.Shortcut;
import com.bstek.dorado.web.DoradoContext;

@Component
public class TouchDesktop {

	public void onReady(com.bstek.dorado.touch.widget.desktop.Desktop desktop)
			throws Exception {
		DoradoContext context = DoradoContext.getCurrent();
		Resource[] resources = context
				.getResources("classpath:com/bstek/dorado/sample/touch/icons/*.png");
		for (Resource resource : resources) {
			String filename = resource.getFilename();
			Shortcut shortcut = new Shortcut();
			shortcut.setIcon(">dorado/res/com/bstek/dorado/sample/touch/icons/"
					+ filename);
			shortcut.setCaption(StringUtils.substringBeforeLast(filename, "."));
			shortcut.addClientEventListener("onTap", new DefaultClientEvent(
					"dorado.MessageBox.alert(\"您点击了'" + filename + "'。\");"));
			desktop.addItem(shortcut);
		}
	}
}
