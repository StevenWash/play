// @Bind #chinaMap.onRefreshPaper
function chinaMapRefreshPaper(self, arg, chinaMap) {
    var paper = chinaMap.getPaper();
    //声明位置信息（经纬度和名称）
    var position01 = {lat: 31.090574094954167, lon: 121.3330078125, name: "发货地"},
        position02 = {lat: 43.588074, lon: 121.390226, name: "中转站"},
        position03 = {lat: 33.365852, lon: 100.168002, name: "目的地"};
    
    function positionXY(position) {
        position.x = chinaMap.lonToX(position.lon);
        position.y = chinaMap.latToY(position.lat);
    }
    //构造线
    function buildLine(position1, position2,attr) {
        var overStyle = {stroke: "#813232"};
        paper.path().attr(overStyle).attr({path: "M" + position1.x + "," + position1.y + "L" + position2.x + "," + position2.y + "z"});
    }

    positionXY(position01);
    positionXY(position02);
    positionXY(position03);


    buildLine(position01, position02);
    buildLine(position02, position03);


    var labelStyle = {
        font: '10px Helvetica, Arial',
        fill: "#fff"
    }, popupStyle = {
        fill: "#813232",
        stroke: "#9E6060",
        "stroke-width": 2,
        "fill-opacity": 0.7
    };

    function buildPopup(position) {
        var popupLabel = paper.text(position.x, position.y, position.name).attr(labelStyle);
        paper.popup(position.x, position.y, popupLabel, "top").attr(popupStyle);
    }

    buildPopup(position01);
    buildPopup(position02);
    buildPopup(position03);


    // 点样式
    var circleStyle = {
        fill: "r#AC4D4D:0-#FFFFFF:50-#AC4D4D:100",
        stroke: "#9E6060",
        "stroke-width": 2,
        r: 8
    };
    //创建圆点
    var cu = paper.circle(position01.x, position01.y, 5)
        .attr(circleStyle);
    
    //点运动
    cu.animate({
        cx: position02.x,
        cy: position02.y
    }, 5000,"linear",function(){
        cu.animate({
            cx: position03.x,
            cy: position03.y
        }, 5000,"linear");
    });

}
