var stage, layer;
var colors = [ 'red', 'orange', 'yellow', 'green', 'blue', 'purple' ];

function getRandomColor() {
	return colors[Math.round(Math.random() * 5)];
}

// @Bind view.onReady
!function(kinetic) {
	stage = kinetic.getStage();
	layer = new Kinetic.Layer();
	for ( var n = 0; n < 10; n++) {
		var shape = new Kinetic.RegularPolygon({
			x : Math.random() * stage.getWidth(),
			y : Math.random() * stage.getHeight(),
			sides : Math.ceil(Math.random() * 5 + 3),
			radius : Math.random() * 100 + 20,
			fill : getRandomColor(),
			stroke : 'black',
			opacity : Math.random(),
			strokeWidth : 4,
			draggable : true
		});
		layer.add(shape);
	}
	stage.add(layer);
}

// @Bind #buttonTango.onClick
!function() {
	var color = Kinetic.Util.getRGB(getRandomColor());

	for ( var n = 0; n < layer.getChildren().length; n++) {
		var shape = layer.getChildren()[n];
		var stage = shape.getStage();
		new Kinetic.Tween({
			node : shape,
			duration : 1,
			x : Math.random() * stage.getWidth(),
			y : Math.random() * stage.getHeight(),
			rotation : Math.random() * Math.PI * 2,
			radius : Math.random() * 100 + 20,
			opacity : Math.random(),
			easing : Kinetic.Easings.EaseInOut,
			fillR : color.r,
			fillG : color.g,
			fillB : color.b
		}).play();
	}
}