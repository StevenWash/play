/**
 * @author ck
 */

// @Bind #btnToUpperCase.onClick
!function() {
	view.get("#action1").set("parameter","ssd").execute(function(result){
        dorado.MessageBox.alert(result);
    });
}

// @Bind #btnShowDialog.onClick
!function() {
	view.get("#userDataSet").set("parameter","���ǲ���");
	view.get("#dialogUserData").show();
}