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
    // 定义Tab变量
    var tab = {};
    // self 代表事件所属的控件，此处指 Tree对象
    // self.get("currentNode")表示获取当前被点击的节点。
    with (self.get("currentNode")) {
        // 制定当前的tab为IFrameTab
        tab.$type = "IFrame";
        // 定义新Tab的标签
        tab.caption = get("label");
        // 定义Tab的Path
        // get("userData")表示获取当前节点的UserData属性，
        // 也就是刚才设定的 sample.chapter01.HelloWorld.d
        tab.path = get("userData");
        tab.name = get("label");
        tab.closeable = true;
    }
    // 如果当前节点有指定的Path则打开新的tab
    if (tab.path) {
        with (view.get("#tabControl")) {
            // 根据name查找是否已经打开过当前的Tab。
            // 如果没有打开过，则需要添加一个新的Tab
            var currentTab = getTab(tab.name);
            if (currentTab) {
                tab = currentTab;
            } else {
                // 获取ID为tabControl的对象，并添加一个新的Tab
                // 设定ID为tabControl的对象的当前Tab为新创建的Tab
                tab = addTab(tab);
            }
            // 设定当前的Tab为制定的tab
            set("currentTab", tab);
        }
    }
}