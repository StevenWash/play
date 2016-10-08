/**
 * @author Alex Tong
 */

// @Bind #orderMap.onRefreshPaper
function orderMapRefreshPaper(self, arg) {
    var paper = self.getPaper(),
        circleDot,popupFrame,popupLabels;

    var x = 10, y = 10,
        circleStyle = {
            fill: "r#FE7727:50-#F57124:100",
            stroke: "#fff",
            "stroke-width": 2
        },
        labelStyle = {
            font: '10px Helvetica, Arial',
            fill: "#fff"
        },
        popupStyle = {
            fill: "#5C4D4D",
            stroke: "#666",
            "stroke-width": 2,
            "fill-opacity": .7
        };

    circleDot = paper.circle(x, y, 1).attr(circleStyle);
    popupLabels = paper.set();
    popupLabels.push(paper.text(60, 12, "下单量:000").attr(labelStyle));
    popupLabels.push(paper.text(60, 30, "金额:0000万").attr(labelStyle));
    popupLabels.hide();
    popupFrame = paper.popup(100, 100, popupLabels, "top").attr(popupStyle).hide();


    setTimeout(function () {
        (function () {
            var texts = self._data.texts,
                text = texts[Math.floor(Math.random() * 35)],
                amount = Math.floor(Math.random() * 99 + 1),
                money = amount * 10 + Math.floor(Math.random() * 100) / 100 + "万",
                easing = "bounce", ms = 800;
            var popupStyle = amount > 80 ? {
                fill: "#813232",
                stroke: "#9E6060"
            } : {
                fill: "#5C4D4D",
                stroke: "#666"
            };
            var ppp = self._paper.popup(text.x, text.y, popupLabels, 'top', 1),
                anim = Raphael.animation({
                    path: ppp.path,
                    transform: [ "t", ppp.dx, ppp.dy ]
                }, ms, easing);

            var matrix = popupLabels[0].matrix;
            var lx = matrix.e + ppp.dx,
                ly = matrix.f + ppp.dy;

            popupLabels[0].attr({text: "下单量:" + amount });
            popupLabels[1].attr({ text: "金额:" + money });
            popupLabels.show().stop().animateWith(
                popupFrame, anim,
                {transform: [ "t", lx, ly ]},
                ms, easing
            );
            popupFrame.show().stop().attr(popupStyle).animate(anim);
            circleDot.stop().attr({
                cy: text.y,
                cx: text.x,
                r: 2
            }).animate({r: 7}, ms * 3, easing);

            var data = view.get("#dsOrder.data");
            (data.toArray().length >= 10) && (data.remove(data.last()));
            data.insert({
                amount: amount, money: money,
                orderTime: new Date(),
                address: text.content
            }, "begin");
            setTimeout(arguments.callee, 2000);
        })();


    }, 1000);


}
