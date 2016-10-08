/**
 * Alex.tong
 */
// @Bind ^bookmark.onClick
!function(self, arg, iframe1) {
	iframe1.set('path', self.get('userData'));
}

// @Bind #container1.onContextMenu
!function(self, arg, menu1) {
	menu1.show({
		position : {
			left : arg.event.pageX,
			top : arg.event.pageY
		}
	});
}