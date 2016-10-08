// @Bind #btnSubmit.onClick
!function(self, arg, ajaxActionSubmit) {
	
	ajaxActionSubmit.set("parameter", view.get("#dsAutoForm").get("entity")).execute(function(data) {
		var result = data.result;
		if (result == "success") {
			dorado.MessageBox.alert("保存成功");
		} else {
			dorado.MessageBox.alert("保存失败");
		}
	});
};