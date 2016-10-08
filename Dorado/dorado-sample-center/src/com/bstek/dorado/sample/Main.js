var rootNodesLoaded = false;

// @Bind view.onReady
!function(treeExamples, tipSkin) {
	jQuery(window).hashchange(function() {
		if (rootNodesLoaded) {
			processHash();
		}
	});
	
	view.get("^skin-item").objects.each(function(menuItem) {
		if (menuItem.get("userData") == $setting["widget.skin"]) {
			menuItem.set("checked", true);
			return false;
		}
	});

	setTimeout(function() {
		var viewWidth = view.getDom().offsetWidth;
		tipSkin.show({
			position: {
				left: viewWidth - 92,
				top: 30
			}
		});
	}, 1500);
}

function processHash() {
	if (view.get("#accordion.currentIndex") != 0) return;

	var hash = window.location.hash;
	if (hash) {
		hash = hash.substring(1);
	}
	if (!hash) {
		return false;
	}

	var type = "example", id;
	if (hash.indexOf("category") == 0) {
		type = "category";
		id = parseInt(hash.substring(8));
	} else {
		id = parseInt(hash);
	}
	if (!(id > 0)) {
		return false;
	}

	if (view.get("#treeExamples.currentEntity.id") == id) {
		return;
	}

	view.get("#actionGetPath").set("parameter", {
		type : type,
		id : id
	}).execute(function(path) {
		var node = view.get("#treeExamples.root");
		path.each(function(categoryId) {
			node.expand();
			var child = findChild(node, categoryId);
			if (!child) {
				return false;
			}
			child.expand();
			node = child;
		});

		if (node) {
			var child = findChild(node, id);
			if (child) {
				view.set("#treeExamples.currentNode", child);
				openPageInFrame(id, type);
			}
		}
	});
}

function onRootNodesLoaded() {
	if (processHash() === false) {
		openPageInFrame(0);
	}
}

function findChild(node, id) {
	var result = null;
	node.get("nodes").each(function(child) {
		if (child.get("data.id") == id) {
			result = child;
			return false;
		}
	});
	return result;
}

var lastFrameUrl;

function openPageInFrame(id, type) {
	var url;
	if (id != 0) {
		var hash = "#";
		if (type == "category") {
			url = "com.bstek.dorado.sample.CategorySummary.d?categoryId=";
			hash += "category";
		} else {
			url = "com.bstek.dorado.sample.ExampleSummary.d?exampleId=";
		}
		url += id;
		window.location.hash = (hash + id);
	} else {
		url = "com.bstek.dorado.sample.Welcome.d";
		window.location.hash = "";
	}

	if (lastFrameUrl != url) {
		view.set("#frameExample.path", url);
		lastFrameUrl = url;
	}
}

// @Bind #treeExamples.onDataNodeCreate
!function(self, arg) {
	if (!rootNodesLoaded) {
		if (arg.node.get("parent") == self.get("root")) {
			rootNodesLoaded = true;
			setTimeout(onRootNodesLoaded, 0);
		}
	}
}

// @Bind #treeExamples.onDataRowClick
!function(self) {
	var currentNode = self.get("currentNode"), type = currentNode
			.get("bindingConfig.name");
    var id = currentNode.get("data.id");
    if (-100 != id){
        openPageInFrame(currentNode.get("data.id") || 0, type);
    }
}

// @Bind #treeExamples.onRenderNode
!function(arg) {
	var node = arg.node, data = node.get("data"), xCreateConfig = [];
	arg.dom.style.fontWeight = (data.get("hot") ? "bold" : "");

	if (node.get("bindingConfig.name") == "category") {
		xCreateConfig.push({
			tagName : "SPAN",
			contentText : node.get("label")
		});
		xCreateConfig.push({
			tagName : "SPAN",
			contentText : "(" + data.get("childCount") + ")",
			style : "margin-left: 2px; color: gray"
		});
		if (data.get("new")) {
			xCreateConfig.push({
				tagName : "IMG",
				src : $url(">images/new.gif"),
				style : "position: relative; left: 4px; top: 4px"
			});
		}
	} else {
		if (data.get("new")) {
			xCreateConfig.push({
				tagName : "SPAN",
				contentText : node.get("label")
			});
			xCreateConfig.push({
				tagName : "IMG",
				src : $url(">images/new.gif"),
				style : "position: relative; left: 4px; top: 4px"
			});
		}
	}

	if (xCreateConfig.length) {
		$(arg.dom).empty().xCreate(xCreateConfig);
	} else {
		arg.processDefault = true;
	}
}

// @Bind #treeExamples.onNodeAttached
!function(arg) {
	var node = arg.node, icon = node.get("data.icon");
	if (icon) {
		if (icon.charAt(0) == '#') {
			node.set("iconClass", icon.substring(1));
		}
		else {
			node.set("icon", icon);
		}
	}
}

function search() {
	var searchText = view.get("#inputSearch.text");
	if (!searchText) {
		dorado.MessageBox.alert("请输入一个有效的搜索条件。");
		return;
	}
	var dsSearchResult = view.get("#dsSearchResult");
	dsSearchResult.set("parameter", searchText).flushAsync();
}

// @Bind #buttonSearch.onClick
!function() {
	search();
}

// @Bind #inputSearch.onKeyDown
!function(arg) {
	if (arg.keyCode == 13) {
		search();
	}
}

// @Bind #buttonAdmin.onClick
!function() {
	open($url(">com.bstek.dorado.sample.admin.ExampleMaintain.d"));
}

// @Bind #buttonSkin.onMouseDown
!function(tipSkin) {
	tipSkin.hide();
}

// @Bind #listSearchResult.onDataRowClick
!function(dsSearchResult) {
	openPageInFrame(dsSearchResult.getData("#.id") || 0, "example");
}

// @Bind #listSearchResult.onRenderRow
!function(arg) {
	var data = arg.data;
	arg.dom.style.fontWeight = (data.get("hot") ? "bold" : "");
	
	var icon = $url(data.get("icon")), iconClass;
	if (icon) {
		if (icon.charAt(0) == '#') {
			iconClass = icon.substring(1);
			icon = undefined;
		}
	}
	else {
		iconClass = "fa fa-file-o";
	}
	
	var iconConfig = {
		tagName : "SPAN",
		style : {
			width : "16px",
			height : "16px",
			margin : "0 3px -3px 2px",
			display : "inline-block"
		}
	};
	if (iconClass) {
		iconConfig.className = iconClass;
	}
	else if (icon) {
		iconConfig.style.background = "url(" + icon + ")";
	}
	
	var config = [
		iconConfig, {
			tagName : "SPAN",
			contentText : data.get("label")
		}];
	if (data.get("new")) {
		config.push({
			tagName : "IMG",
			src : $url(">images/new.gif"),
			style : "position: relative; left: 4px; top: 4px"
		});
	}

	$(arg.dom).empty().css("whiteSpace", "nowrap").xCreate(config);
	arg.processDefault = false;
}

var hasSectionChangeFired = false;

// @Bind #accordion.onCurrentSectionChange
!function(self) {
	if (hasSectionChangeFired) {
		if (self.get("currentIndex") == 0) {
			processHash();
		}
	}
	hasSectionChangeFired = true;
}

// @Bind ^skin-item.onClick
!function(self, actionChangeSkin) {
	actionChangeSkin.set("parameter", self.get("userData")).execute(function() {
		window.location.reload();
	});
}

//@Bind #menuMoreSkin.onClick
!function(self, actionChangeSkin) {
	open("http://dorado7.bsdn.org/skin-builder", "_blank");
}