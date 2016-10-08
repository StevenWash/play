// @Bind view.onReady
!function(dsExamples, stack, listExample, frameExample) {
	
	function processHashChange() {
		var hash = window.location.hash;
		if (hash) {
			hash = hash.substring(1);
		}
		
		if (hash) {
			var url = dsExamples.getData("examples[@.get('id')==" + hash + "](R).url");
			frameExample.set("path", url);
			stack.pushControl(frameExample);
		}
		else {
			frameExample.set("path", "about:blank");
			stack.popControl();
		}
	}
	
	jQuery(window).hashchange(function() {
		processHashChange();
	});
	
	if (window.location.hash) {
		setTimeout(processHashChange, 200);
	}
}

//@Bind #listExample.onCreate
!function(listExample) {
	listExample.set("renderer", new dorado.widget.list.TemplateRenderer({
		template: "<span class='item'>" +
			"<span class='text'>" +
			"<span class='label'><%-data.label%></span>" +
			"<span class='summary'><%-data.summary%></span>" +
			"</span>"
	}));
}

//@Bind #listExample.onItemTap
!function(arg) {
	if (!arg.leaf) return;
	window.location.hash = arg.item.get("id");
}

//@Bind #listExample.onItemTapHold
!function(arg) {
	if (!arg.leaf) return;
	window.open(arg.item.get("url"), "_blank");
}

//@Bind #dialogQrCode.onReady
!function(labelPath, qrcode) {
	var path = location.protocol + "//" + location.host + dorado.Setting["common.contextPath"];
	qrcode.set("data", {
		text: path
	}).generateQRCode();
}

//@Bind #buttonQrCode.onTap
!function(dialogQrCode) {
	dialogQrCode.show();
}