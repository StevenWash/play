/**
 * @author Alex Tong
 */
var circleDot, easing = "bounce", ms = 1000, mapPopupFrame, popupLabels;

// @Bind #chinaMap.onRefreshPaper
function chinaMapRefreshPaper(self, arg, chinaMap, labelLatLon) {
    var paper = chinaMap.getPaper(), circleStyle = {
        fill: "r#FE7727:50-#F57124:100",
        stroke: "#fff", "stroke-width": 2, r: 0
    }, style = {
        font: '10px Helvetica, Arial',
        fill: "#fff"
    };

    circleDot = paper.circle().attr(circleStyle);
    circleDot.hide();

    popupLabels = paper.set();
    popupLabels.push(paper.text(60, 12, "您当前位置").attr(style));
    popupLabels.push(paper.text(60, 30, "_____________").attr(style));
    popupLabels.hide();

    mapPopupFrame = paper.popup(100, 100, popupLabels, "top").attr({
        fill: "#000", stroke: "#666",
        "stroke-width": 2, "fill-opacity": .7
    });
    mapPopupFrame.hide();

    try {
        navigator.geolocation && navigator.geolocation.getCurrentPosition(function (pos) {

            var x = chinaMap.lonToX(pos.coords.longitude), y = chinaMap
                .latToY(pos.coords.latitude);
            circleStyle.r = 2;
            paper.circle(x, y, 1).stop().attr(circleStyle).animate({r: 5}, 2000, "elastic");

            var ppp = paper.popup(x, y, popupLabels, "top", 1),
                anim = Raphael.animation({
                    path: ppp.path,
                    transform: [ "t", ppp.dx, ppp.dy ]
                }, 200);


            var matrix = popupLabels[0].matrix;
            var lx = matrix.e + ppp.dx,
                ly = matrix.f + ppp.dy;

            popupLabels.show().stop().animateWith(mapPopupFrame, anim,
                {
                    transform: [ "t", lx, ly ]
                }, 200);
            mapPopupFrame.show().stop().animate(anim);
        });
    } catch (e) {
        labelLatLon.set('text', '  抱歉未能获得您所在位置，浏览器不支持或浏览器尚未允许程序获得位置信息！');
    }
}

// @Bind #groupCapitals.onCreate
!function (self, arg) {
    $(items).each(function (index, item) {
        item.title = "中国四大名都之一";
        self.addChild(dorado.Toolkits.createInstance("widget", {
            $type: "dorado.widget.Link",
            tags: "link1",
            style: "margin-right:20px",
            href: 'javascript:',
            text: item.name,
            userData: item
        }));
    });
}
// @Bind #groupMountains.onCreate
!function (self, arg) {
    $(mountains).each(function (index, item) {
        item.title = "中华十大名山之一";
        self.addChild(dorado.Toolkits.createInstance("widget", {
            $type: "dorado.widget.Link",
            tags: "link1",
            href: 'javascript:',
            text: item.name,
            userData: item
        }));
    });
}
// @Bind ^link1.onClick
function linkClick(self, arg, chinaMap, panelIntro, htmlIntro) {
    var userData = self.get('userData'), x = chinaMap.lonToX(userData.lon), y = chinaMap
        .latToY(userData.lat), paper = chinaMap.getPaper();
    goTo({ cx: x,
        cy: y  });

    var ppp = paper.popup(x, y, popupLabels, "top", 1),
        anim = Raphael.animation({
            path: ppp.path,
            transform: [ "t", ppp.dx, ppp.dy ]
        }, 200);

    var matrix = popupLabels[0].matrix;
    var lx = matrix.e + ppp.dx;
    var ly = matrix.f + ppp.dy;

    popupLabels[0].attr({ text: userData.name });
    popupLabels[1].attr({
        text: userData.title,
        fill: '#3993b3'
    });

    popupLabels.show().stop().animateWith(mapPopupFrame, anim, {
        transform: [ "t", lx, ly ]
    }, 200);

    mapPopupFrame.show().stop().animate(anim);

    panelIntro.set('caption', userData.name + "—简介");
    htmlIntro.set('content', '<span class="intro">' + userData.description
        + '</span');

}
// @Bind #btnFind1.onClick
!function (self, arg, textLat, textLon, chinaMap) {
    var x = chinaMap.lonToX(textLon.get('value')), y = chinaMap.latToY(textLat
        .get('value')), data = chinaMap.get('data');
    if (x < 0 || x > data.canvasWidth || y < 0 || y > data.canvasHeight) {
        dorado.widget.NotifyTipManager.notify("<div>位置超出画布范围了哦！调整调整再试试！</div>",
            {
                showDuration: 2,
                icon: "INFO"
            });
    }
    goTo({
        cx: x,
        cy: y
    });

}

// @Bind #btnFind2.onClick
!function (self, arg, textLatLon, chinaMap) {
    var txt = textLatLon.get('value'), xy;
    try {
        xy = chinaMap.parseLatLon(txt);
        goTo({
            cx: xy.x,
            cy: xy.y
        });
    } catch (e) {
        dorado.widget.NotifyTipManager
            .notify(
            "<div>输入的经纬度信息不合法！</div><div>请输入如下格式:31°11′29.97″N,121°30′11.87″E</div>",
            {
                showDuration: 2,
                icon: "INFO"
            });
    }

}
function goTo(attr) {
    attr.r = 2;
    circleDot.show().stop().attr(attr).animate({
        r: 8
    }, 2000, "elastic");
}

var items = [
    {
        name: '北京',
        lat: 39.80446907553041,
        lon: 115.79296875,
        description: "建都朝代：辽（938年，陪都）、金（1153年）、元、明（与南京交替）、清、中华民国（与南京交替）、中华人民共和国。"
    },
    {
        name: '南京',
        lat: 32.03369169193891,
        lon: 118.740234375,
        description: "建都朝代：吴（211年）、东晋（317年）、宋（420年）、齐（479年）、梁（502年）、陈（557年）、杨吴、南唐（937年）、宋（1127年）、南宋（1138年，陪都）、明（1368年）、南明（1644年）、太平天国（1853年）、中华民国（1912年）。"
    },
    {
        name: '洛阳',
        lat: 34.10947549180878,
        lon: 112.42721557617188,
        description: "依史书记载和建都时间，洛阳先后有夏、商、周、战国韩、汉、曹魏、晋、北魏、隋、唐、武周、后梁、后唐、后晋、中华民国十五个建都朝代，新、后赵、东魏、北周、后汉、后周、北宋、金八个陪都朝代，因此洛阳是中国历史上建都最早、朝代最多、时间最长的都城。"
    },
    {
        id: 'xian',
        name: '西安',
        lat: 34.25948651450623,
        lon: 109.40472412109375,
        description: "历史上又称“神都”、“雒阳”，建都朝代：西周(前363年)、秦、西汉(前202年)、新朝（前15年）、东汉、西晋、前赵(11年)、前秦(33年)、后秦(34年)、西魏(22年)、北周(25年)、隋(38年)、唐(273年)、武周(15年)等13个王朝在这里建都达1200余年之久。"
    }
], mountains = [
    {
        name: '长白山',
        lat: 41.75162,
        lon: 124.28738599999997,
        description: '长白山位于中国东北吉林省东南部与朝鲜接壤的边陲地带，绵延1000瀑布公里，主峰白云海拔2691米，2500米以上山峰16座，总面积为9万平方公里。长白山是个曾有过喷发历史的火山。顶峰天池是个由喷发形成的火山口湖。'
    },
    {
        name: '泰山',
        lat: 36.188438,
        lon: 117.10634000000005,
        description: '泰山位于山东省中部，泰安市之北，总面积250平方公里，古称岱山 泰山 ，春秋时改称泰山，被尊为我国五岳之首，有“中华国山”、“天下第一山”的美誉，又称东岳。'
    },
    {
        name: '黄山',
        lat: 29.714683,
        lon: 118.33747599999992,
        description: '黄山位于安徽省南部，属黄山市管辖。传说是中华祖先——轩辕黄帝修身炼丹而飘然成仙的地方。黄山千峰竞秀，万壑峥嵘。有名可指的就有72山峰，其中“莲花”、“光明顶”、“天都”三大主峰，均在海拔1800米以上，拔地极天，气势磅礴，雄姿灵秀。'
    },
    {
        name: '峨眉山',
        lat: 30.601199,
        lon: 103.48450400000002,
        description: '被称为“峨眉天下秀”的峨眉山位于峨眉山市境内，距乐山市30公里，峨眉山高出五岳，秀甲九州，以雄秀壮丽的自然风光和神奇迷人的佛教文化著称于世。主要景观有“双桥清音”等峨眉十景，主峰金顶海拔3079米，有云海、日出、佛光、圣灯“四大奇观”。'
    },
    {
        name: '庐山',
        lat: 29.108828,
        lon: 116.37977000000001,
        description: '中国江西省北部名山，位于九江以南，星子县以西。耸峙于长江中下庐山游平原与鄱阳湖畔。自东北向西南延伸约25公里，宽约15公里。东西两侧为大断裂，山体多峭壁悬崖，相对高度1,200～1,400公尺。主峰汉阳峰海拔1,474公尺(4,836呎)，山势雄伟。'
    },
    {
        name: '珠穆朗玛峰',
        lat: 29.9858181,
        lon: 87.92359579999993,
        description: '珠穆朗玛峰位于西藏定日县正南方，喜马拉雅中段的中尼边境处。珠穆朗玛，藏语意为“圣母”，它是世界最高大的山系喜马拉雅山的主峰，海拔8,844.43米，为2005年5月22日中华人民共和国重测珠峰高度测量登山队成功登顶精确测量的珠峰高度。'
    },
    {
        name: '华山',
        lat: 34.526593,
        lon: 110.082445,
        description: '西岳华山位于陕西省西安以东120公里历史文化故地的华阴市境内，北临坦荡的渭河华山景区导览图平原和咆哮的黄河，南依秦岭，是秦岭支脉分水脊的北侧的一座花岗岩山。凭藉大自然风云变换的装扮，华山的千姿万态被有声有色的勾画出来，华山是国家级风景名胜区，国家5A级旅游景区。'
    },
    {
        name: '武夷山',
        lat: 27.029687,
        lon: 118.83123600000004,
        description: '武夷山位于中国福建省北部，是全国唯一一处集国家级重点风景名胜 武夷山区、国家级旅游度假区、国家级重点自然保护区于一体的著名旅游胜地，是中国首批优秀旅游城市、世界第23处、中国第4处世界自然与文化双重遗产地，也是国家重点文物保护单位。'
    },
    {
        name: '玉山',
        lat: 23.945125865762933,
        lon: 122.40341796875,
        description: '玉山山脉的主峰玉山海拔为3997米，不仅是台湾岛上的最高峰，而且玉山也是我国东南沿海地区的最高峰。玉山虽然位于北回归线附近，但由于海拔高而山顶终年积雪，以其主峰为中心，分出十字形的东、西、南、北四峰环列，晶莹皎洁的冰雪形同堆玉一般覆盖山顶，景观壮丽无比。'
    },
    {
        name: '五台山',
        lat: 39.007616,
        lon: 113.196271,
        description: '五台山位于五台县内，由东台、西台、南台、北台和中台五座山峰环抱而镇海寺成。五座山峰，峰顶都象个平台,故称五台山。北台最高，主峰海拔3058米，号称“华北屋脊”山中气候特殊，每年四月解冻，九月积雪，盛夏气候凉爽，故又名“清凉山”，是避暑的好地方。'
    }
];
