/**
 * @author ck
 */
// @Bind #ajaxActionGetSysInfo.onSuccess
!function(self,arg) {
	var info = self.get("returnValue");
	view.set("#lblProduct.text", "��Ʒ:"+info.product);
	view.set("#lblVendor.text", "������:"+info.vendor);
	view.set("#lblVersion.text", "�汾:"+info.version);
	view.set("#lblTime.text", "��ǰʱ��:"+info.time);
};

// @Bind #btnClear.onClick
!function(self,arg) {
	view.set("#lblProduct.text", "��Ʒ:...");
	view.set("#lblVendor.text", "������:...");
	view.set("#lblVersion.text", "�汾:...");
	view.set("#lblTime.text", "��ǰʱ��:...");
};