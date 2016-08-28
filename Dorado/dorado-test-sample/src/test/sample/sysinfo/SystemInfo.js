/**
 * @author ck
 */
// @Bind #ajaxActionGetSysInfo.onSuccess
!function(self,arg) {
	var info = self.get("returnValue");
	view.set("#lblProduct.text", "产品:"+info.product);
	view.set("#lblVendor.text", "服务商:"+info.vendor);
	view.set("#lblVersion.text", "版本:"+info.version);
	view.set("#lblTime.text", "当前时间:"+info.time);
};

// @Bind #btnClear.onClick
!function(self,arg) {
	view.set("#lblProduct.text", "产品:...");
	view.set("#lblVendor.text", "服务商:...");
	view.set("#lblVersion.text", "版本:...");
	view.set("#lblTime.text", "当前时间:...");
};