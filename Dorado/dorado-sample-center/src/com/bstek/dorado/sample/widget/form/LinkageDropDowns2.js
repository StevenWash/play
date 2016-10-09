// @Bind #dsForm.onReady
!function(self, arg) {
	self.insert();
};

// @Bind #ddCategories.onValueSelect
!function(self, arg) {
	view.get("#dsAutoForm").get("entity").set("productId","");
	view.get("#dsAutoForm").get("entity").set("productName","");
};

// @Bind #ddProducts.beforeExecute
!function (self, arg, dsForm, dsProducts) {
	var categoryId = dsForm.get("data.categoryId");
	if (categoryId) {
		dsProducts.set("parameter", categoryId).flushAsync();
	}
};

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