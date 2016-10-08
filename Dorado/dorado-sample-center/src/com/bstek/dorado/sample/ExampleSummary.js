// @Controller

// @Global
function showURL(id) {
	dorado.MessageBox
		.alert("http://bsdn.org/projects/dorado7/deploy/sample-center/com.bstek.dorado.sample.Main.d"
			+ "#" + id);
}

// @Global
function showQrCode(id) {
	view.get("#dialogQrCode").show();
}

// @Bind view.onReady
!function(dsExample, iframeExample, intro1) {
	var $iFrameHolder = $("#iFrameHolder");
	$iFrameHolder.css({
		width: dsExample.getData("embedWidth") || "100%",
		height: dsExample.getData("embedHeight") || 300
	}).slideDown(function() {
			$iFrameHolder.shadow({
				mode: "frame"
			});

			iframeExample.set({
				width: "100%",
				height: "100%",
				visible: true,
				path: dsExample.getData("url")
			});
		});

	var $cloudoLink = $("#cloudo-link");
	if ($cloudoLink.length) {
		var cookievalue = $.cookie("sampleCloudoOpen");
		if (!cookievalue) {
			var sevenDaysFromNow = new Date();
			sevenDaysFromNow.setDate(sevenDaysFromNow.getDate() + 31);
			$.cookie("sampleCloudoOpen", "known", {
				expires: sevenDaysFromNow
			});

			intro1.set("steps", [
				{
					element: $cloudoLink,
					name: 'cloudo',
					intro: '通过Cloudo编辑试试？'
				}
			]);
			intro1.start();
		}
	}
}

// @Bind #dsExample.onReady
!function(self) {
	var sources = self.getData().get("sources");
	if (sources.entityCount == 0) {
		sources.createChild({
			label: "(未定义源文件)"
		});
	}
}

// @Bind #dialogQrCode.onShow
!function(dsExample, qrcodeExample, qrcodeSummary) {
	qrcodeExample.set(
		"data",
		{
			text: "http://bsdn.org/projects/dorado7/deploy/sample-center/"
				+ dsExample.getData("url")
		}).generateQRCode();
	qrcodeSummary
		.set(
		"data",
		{
			text: "http://bsdn.org/projects/dorado7/deploy/sample-center/com.bstek.dorado.sample.Main.d#"
				+ dsExample.getData("id")
		}).generateQRCode();
}