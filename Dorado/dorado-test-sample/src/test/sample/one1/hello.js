/**
 * @author ck
 */
// @Bind #menu1.#menuItem1.onClick
!function() {
    dorado.MessageBox.alert("Help");
}

// @Bind #menu1.#menuItem2.onClick
!function() {
    dorado.MessageBox.alert("About");
}

//@Bind #tree1.onDataRowClick
!function(self) {
    // ����Tab����
    var tab = {};
    // self �����¼������Ŀؼ����˴�ָ Tree����
    // self.get("currentNode")��ʾ��ȡ��ǰ������Ľڵ㡣
    with (self.get("currentNode")) {
        // �ƶ���ǰ��tabΪIFrameTab
        tab.$type = "IFrame";
        // ������Tab�ı�ǩ
        tab.caption = get("label");
        // ����Tab��Path
        // get("userData")��ʾ��ȡ��ǰ�ڵ��UserData���ԣ�
        // Ҳ���Ǹղ��趨�� sample.chapter01.HelloWorld.d
        tab.path = get("userData");
        tab.name = get("label");
        tab.closeable = true;
    }
    // �����ǰ�ڵ���ָ����Path����µ�tab
    if (tab.path) {
        with (view.get("#tabControl")) {
            // ����name�����Ƿ��Ѿ��򿪹���ǰ��Tab��
            // ���û�д򿪹�������Ҫ���һ���µ�Tab
            var currentTab = getTab(tab.name);
            if (currentTab) {
                tab = currentTab;
            } else {
                // ��ȡIDΪtabControl�Ķ��󣬲����һ���µ�Tab
                // �趨IDΪtabControl�Ķ���ĵ�ǰTabΪ�´�����Tab
                tab = addTab(tab);
            }
            // �趨��ǰ��TabΪ�ƶ���tab
            set("currentTab", tab);
        }
    }
}