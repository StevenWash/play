/**
 * @author Alex Tong
 */
var GAT = "_diaoyudao_810000_820000_710000";
//@Bind view.onReady
!function(mapTip) {
	setTimeout(function() {
		var viewHeight = view.getDom().offsetHeight;
		mapTip.show({
			position : {
				left : 10,
				top : viewHeight-10
			}
		});
	}, 300);

}
// @Bind #chinaMap.onElementClick
!function(self, arg) {
	var subMap = self.get('subMap'), id = arg.element.data('id'), name = arg.element
			.data('name');

	if (GAT.indexOf(id) >= 0)
		return;

	function showSubMap() {
		subMap.set('data', dorado.widget.map.China[name]);
		subMap.show();
		return true;
	}
	dorado.widget.map.China[name] && showSubMap()
			|| $import("map-China-" + name, function() {
				showSubMap();
			});

}
//@Bind ^map.onRenderElement
!function(self, arg) {
	arg.element.color=Raphael.getColor();
}
// @Bind ^map.onElementMouseOver
!function(self, arg) {
	arg.element.attr({
		fill : arg.element.color
	});
}

// @Bind ^map.onElementMouseOut
!function(self, arg) {
	var element = arg.element;

	element.attr({
		fill :self.get('fill')
	});

}

