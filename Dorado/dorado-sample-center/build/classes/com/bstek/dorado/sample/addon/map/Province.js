var currentProvinceItem, excludeItems = ["diaoyudao","810000","820000","710000" ];
function currentProvinceChange(id, map) {
	var province = _.findWhere(dorado.widget.map.China.paths, {
		id : id
	});
	var name = province.name;
	var data = dorado.widget.map.China[name];
	
	function setMapData(mapData){
		map.set("data", mapData);
		map.refreshPaper();
	}
	
	if (data) {
		setMapData(data);
	} else {
		$import("map-China-" + name, function() {
			setMapData(dorado.widget.map.China[name]);
		});
	}
}
// @Bind #provinceMenu.onCreate
!function(self, arg, provinceMap,provincePanel) {
	var texts = dorado.widget.map.China.texts, items = [];
	texts.each(function(province) {
		items.push({
			caption : province.content,
			userData : province.id,
			onClick : function(self, arg) {
				var id=self.get("userData");
				if(excludeItems.indexOf(id)>=0){
					dorado.widget.NotifyTipManager.notify({
						content:"暂无此省份地图资源！",
						showDuration:5,
						icon:"INFO"
					});
					return;
				}
				
				if (currentProvinceItem)
					currentProvinceItem.set("iconClass", "")
				self.set("iconClass", "fa fa-hand-o-right")
				currentProvinceChange(id, provinceMap);
				currentProvinceItem = self;
				provincePanel.set("caption",self.get("caption"));
			}
		});
	});
	self.set("items", items);
}
// @Bind #provincePanel.onReady
!function(self, arg) {
	var container = self.getContentContainer();
	$(container).css({
		"background-color" : "#ffffff"
	});
}