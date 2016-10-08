/**
 * @author Alex Tong
 */
var GAT = "_diaoyudao_810000_820000_710000", currentEl = null;
// @Bind view.onReady
!function(mapTip) {
	setTimeout(function() {
		var viewHeight = view.getDom().offsetHeight;
		mapTip.show({
			position : {
				left : 20,
				top : viewHeight - 28
			}
		});
	}, 500);

}
// @Bind #chinaMap.onRenderElement
!function(self, arg) {
	arg.element.attr({
		cursor : "pointer"
	});
}
// @Bind #chinaMap.onElementClick
!function(self, arg) {
	var subMap = self.get('subMap'), id = arg.element.data('id'), name = arg.element
			.data('name');
	var entity = view.get("#dsProvince.data:#"), item = China['_' + id];
	entity.set('name', item.name);
	entity.set('description', item.description);
	entity.set('id', id);
	arg.element.attr({
		fill : "#65aecf"
	});
	currentEl && !isHighlightElement(currentEl) && currentEl.attr({
		fill : self.get('fill')
	});
	currentEl = arg.element;
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

// @Bind ^map.onElementMouseOver
!function(self, arg, gridHighlight) {
	if (isHighlightElement(arg.element))
		return;
	(currentEl && currentEl.data('id') == arg.element.data('id'))
			|| arg.element.attr({
				fill : "#a8e5ff"
			});
}

// @Bind #chinaMap.onElementMouseOut
!function(self, arg, gridHighlight) {
	var element = arg.element;
	if (currentEl && currentEl.data('id') == element.data('id'))
		return;
	isHighlightElement(element) || element.attr({
		fill : self._fill
	});

}

// @Bind #dsProvince.onReady
!function(self, arg) {
	self.insert({});
}

// @Bind #btnZoomOut.onClick
!function(self, arg, chinaMap) {
	chinaMap.zoomOut(1);
}

// @Bind #btnZoomIn.onClick
!function(self, arg, chinaMap) {
	chinaMap.zoomIn(1);
}
// @Bind #dsHighlight.onReady
!function(self, arg) {
	var provinces = dorado.widget.map.China.paths, data;
	$(provinces).each(function(index, data) {
		self.insert({
			id : data.id,
			name : China["_" + data.id].name
		});
	});
	self.get('data').first();
}

// @Bind #provinceMap.onElementMouseOut
!function(self, arg, gridHighlight) {
	arg.element.attr({
		fill : self.get('fill')
	});
}
// @Bind #gridHighlight.onSelectionChange
!function(self, arg, chinaMap) {
	var elements = chinaMap.getElements(), addIDs = '', removeIDs = '';
	$(arg.added).each(function(index, el) {
		addIDs += el.get('id');
	});
	$(arg.removed).each(function(index, el) {
		removeIDs += el.get('id');
	});
	for ( var i = elements.length - 1; i >= 0; i--) {
		var element = elements[i], id = element.data('id');
		addIDs.indexOf(id) >= 0 && element.attr({
			fill : "#65aecf"
		});
		removeIDs.indexOf(id) >= 0 && element.attr({
			fill : chinaMap.get('fill')
		});
	}
}
function isHighlightElement(element) {
	var isHighlight = false;
	view.get('#gridHighlight').get("selection").each(function(data) {
		if (data.get('id') == element.data('id')) {
			isHighlight = true;
			return;
		}
	});
	return isHighlight;
}

China = {
	_710000 : {
		name : '台湾省（台）',
		description : '台湾一名源于西拉雅族的台窝湾支族。台湾在秦汉时称“东鳀”。三国时称“东夷”。元时称“瑠求”。明万历年间正式在公文上使用台湾一名。清光绪11年（1885年）设台湾省。取全称中的“台”字作为简称。'
	},
	_110000 : {
		name : '北京（京）',
		description : '北京有据可查的第一个名称为“蓟”，是春秋战国时燕国的都城。辽金是将北京作为陪都，称为燕京。金灭辽后，迁都于此，称中都。元代改称大都。明成祖朱棣从南京迁都于此，改称“北京”。名称一直沿用至今。1949年设为直辖市。取全称中的“京”字作为简称。'
	},
	_120000 : {
		name : '天津（津）',
		description : '唐宋以前，天津称为直沽。金代形成集市称“直沽寨”。元代设津海镇，这是天津建城的开始。明永乐2年（1404年）筑城设卫，始称天津卫，取“天子经过的渡口”之意。1949年设为直辖市。取全称中的“津”字作为简称。'
	},
	_310000 : {
		name : '上海（沪）',
		description : '上海之称始于宋代，当时上海已成为我国的一个新兴贸易港口，那时的上海地区有十八大浦，其中一条叫上海浦，它的西岸设有上海镇。1292年，上海改镇为县。这是上海这一名称的由来。1949年，上海设为直辖市。古时，上海地区的渔民发明了一种竹编的捕鱼工具“扈”，当时还没有上海这一地名，因此，这一带被称为“沪渎”，故上海简称“沪”。春秋战国时上海是楚春申君黄歇封邑的一部分，故上海别称“申”。'
	},
	_500000 : {
		name : '重庆（渝）',
		description : '重庆古称“巴”。秦时称江州。隋称渝州。北宋称恭州。重庆之名始于1190年，因南宋光宗赵敦先封恭王，后登帝位，遂将恭州升为重庆府，取“双重喜庆”之意。1997年，重庆设为直辖市。隋时，嘉陵江称渝水，重庆因位于嘉陵江畔而置渝州，故重庆简称“渝”。'
	},
	_150000 : {
		name : '内蒙古自治区（内蒙古）',
		description : '蒙古原为部落名，始见于唐代记载。1206年，成吉思汗统一蒙古各部，建立蒙古国。元灭后，蒙古族退居塞北。明清形成内、外蒙古之称。晚清以后，泛指大漠以南、长城以北、东起哲里木盟、西至套西厄鲁特所以盟旗为内蒙古。取全称中“内蒙古”三字作为简称。'
	},
	_650000 : {
		name : '维吾尔自治区（新）',
		description : '辖区古称西域。西汉设西域都护府。东汉魏晋改都护为长史。唐代设伊、西、庭三州和安西、北庭两个都护府。17世纪中叶以后，清朝平定了准噶尔部叛乱，在天山南北设伊犁将军。清光绪10年（1884年），改为新疆省，意为“故土新归”。1955年，设新疆维吾尔自治区。取全称中的“新”字作为简称。'
	},
	_540000 : {
		name : '西藏自治区（藏）',
		description : '元时称西藏地区为“乌思藏”。“乌思”是藏语“中央”的意思，“藏”是“圣洁”的意思。明代设立两个都指挥使司。清代称西藏东部为“康”（喀木），中部为“卫”，西部日喀则一带为“藏”（包括阿里），因其在中国西部，故称西藏。1965年设立西藏自治区。取全称中的“藏”字作为简称。另一说认为简称源于故称“乌思藏”。'
	},
	_640000 : {
		name : '宁夏回族自治区（宁）',
		description : '公元5世纪处，匈奴贵族赫连勃勃自以为是夏后氏后裔，故将建立的割据政权定国号为“夏”。宋代，党项族拓跋氏首领李元昊称帝，定都兴庆府（今银川），立国号“夏”，创立文字，建西夏王朝。13世纪，元灭西夏，取“平定西夏永远安宁”之意，在这里设宁夏行省，始有宁夏之名。1958年设宁夏回族自治区。取全称中的“宁”字作为简称。'
	},
	_450000 : {
		name : '广西壮族自治区（桂）',
		description : '宋设广南西路，简称广西路，“广西”一名产生。元设广西两江道。明设广西省。1958年设广西僮族自治区，1965年改为广西壮族自治区。因自宋至清，广西的行政中心在桂州（或桂林府），故广西简称“桂”。另一说认为广西秦时曾设桂林、象郡、南海三郡，而历史上广西2/3地域属桂林郡，故广西简称“桂”。'
	},
	_810000 : {
		name : '香港特别行政区（港）',
		description : '宋代以前，这里是海上渔民捕鱼歇息的地方。宋元以后，岛上有个小村，叫“香港村“，为转运南粤香料的集散港，香港因此得名。1997年成立香港特别行政区。取全称中的“港”字作为简称。'
	},
	_820000 : {
		name : '澳门特别行政区（澳）',
		description : '名字最早记录于明朝史书，叫做“蚝镜”（濠镜），意为海湾如明镜，盛产“蚝”。后又称做“澳”，即船只停航寄泊的地方，故称“蚝镜澳”，因隶属广东香山，亦称“香山澳”。“门”字的来历有多种说法，一说是本地内港的妈祖庙，隔海同湾仔的银坑相望，形成的海峡象门；另一说是本地南面的氹仔、小横琴、路环、大横琴四岛离立对峙，海水贯流其中呈十字门状；再一说是本地南台山（妈阁庙山）和北台山（莲峰山）相封成门。总之，既是澳，又是门，故曰澳门。1999年成立澳门特别行政区。取全称中的“澳”字作为简称。'
	},

	_230000 : {
		name : '黑龙江省（黑）',
		description : '1671年为抵御沙俄东侵，清政府在黑龙江沿岸修筑黑龙江城（黑河旧城），设置黑龙江将军，管辖黑龙江流域。1907年改为黑龙江省。取全称中的“黑”字作为简称。一说简称源于河流黑龙江。'
	},

	_220000 : {
		name : '吉林省（吉）',
		description : '吉林一名源于“吉林乌拉”，满语意为“沿松花江的城市”。1673年建城。1676年置吉林将军。1907年将其辖区改称吉林省。取全称中的“吉”字作为简称。'
	},

	_210000 : {
		name : '辽宁省（辽）',
		description : '秦汉魏晋时代，在辽河以东设辽东郡，以西设辽西郡。北宋时，在今河北、辽宁一带，契丹族建立辽国。辽金时代设置辽阳府。元设辽阳行省。明设辽东都司。清设辽东将军。后因辽河流域为清朝发源地，取“奉天承运”之意，改为奉天省。1929年，取“辽河流域永远安宁”之意，改称辽宁省。取全称中的“辽”字作为简称。一说因境内有辽河，故简称“辽”。'
	},

	_130000 : {
		name : '河北省（冀）',
		description : '战国时，黄河以北的齐国土地称为河北。汉设河北县。唐设河北道。辖区与今有出入。1928年设河北省。辖区相当于我国最早的地理著作《禹贡》中的冀州，故简称“冀”。'
	},

	_140000 : {
		name : '山西省（晋）',
		description : '战国至秦汉时，崤山、函谷关以西的地区称为山西。元时，称太行山以西为山西，设河东山西道宣慰司，这是山西作为政区名称的开始。明处设山西省。辖区为春秋时晋国地，故简称“晋”。'
	},

	_630000 : {
		name : '青海省（青）',
		description : '因境内有青海湖，故得名青海省。据《水经注》记载，早在公元前5世纪时，这里就称青海了，也有写作西海的。唐以后多以青海为正名。1928年设青海省。取全称中的“青”字作为简称。一说认为因境内有青海湖故简称“青”。'
	},

	_370000 : {
		name : '山东省（鲁）',
		description : '战国至秦汉时，崤山、函谷关以东的地区称为山东。金时，在开封以东地区设山东东路、山东西路，这是山东作为政区名称的开始。清初设山东省。辖区为春秋时鲁国地，故简称“鲁”。'
	},

	_410000 : {
		name : '河南省（豫）古称',
		description : '古称黄河以南地区为河南。汉设豫州部。唐置河南道。宋置河南路。元置河南江北行省。明初设河南省。辖区相当于《禹贡》中的豫州，故简称“豫”。'
	},

	_320000 : {
		name : '江苏省（苏）',
		description : '清康熙6年（1667年）设江苏省，取两江总督驻所江宁（今南京市）和巡抚驻所苏州（今苏州市）两府首字组成江苏省。取全称中的“苏”字作为简称。'
	},

	_340000 : {
		name : '安徽省（皖）',
		description : '1667年，取当时的政治中心安庆（今安庆市）和经济都会徽州（今歙县）二府首字组成安徽省。安庆府是春秋时皖国故地，别称为皖，故安徽简称“皖”。一说认为因境内最早的名山天柱山古称皖公山，故简称“皖”。'
	},

	_330000 : {
		name : '浙江省（浙）',
		description : '浙江即江流盘回曲折之意。战国时浙江指今天的富春江、钱塘江和新安江。东汉将浙江分为浙东、浙西两个地区。唐以后这两个地区转化为政区名称。明初设浙江省。取全称中的“浙”字作为简称。一说认为因富春江、钱塘江和新安江古称浙江，故简称“浙”。'
	},

	_350000 : {
		name : '福建省（闽）',
		description : '秦始皇统一中国后，在此设闽中郡。汉时称福建为闽越国。唐开元年间设福建节度使，管辖福、建、泉、漳、汀五州，福建是前两州的名字组成的。元设福建行省。明设福建省至今。辖区古为闽越族聚居地，故简称“闽”。一说因境内有闽江，故简称“闽”。'
	},

	_360000 : {
		name : '江西省（赣）',
		description : '唐代设江南西道，简称江西道，江西由此得名。宋设江南西路。元设江西行省。后皆设江西省。因赣江纵贯全省，故简称“赣”。'
	},

	_430000 : {
		name : '湖南省（湘）',
		description : '唐代在洞庭湖以南，包括湘姿二水流域设湖南节度使，始出现湖南一名。宋置荆湖南路，简称湖南路。元明两代设湖南道。清代设置湖南省。因湘江纵贯全省，故简称“湘”。'
	},

	_420000 : {
		name : '湖北省（鄂）',
		description : '宋代自洞庭湖以北至荆山，西包沅澧二水流域设荆湖北路，简称湖北路，湖北一名产生。元明两代设湖北道。清代设置湖北省。清代湖北的行政中心宜昌，为隋以后鄂州的治所，故湖北简称“鄂”。'
	},

	_440000 : {
		name : '广东省（粤）',
		description : '宋置广南东路，简称广东路，由此出现广东一名。元设广东道。明设广东省。辖区汉初为南粤之地，故简称“粤”。'
	},

	_460000 : {
		name : '海南省（琼）',
		description : '因其位于南中国海域，境内最大岛屿又称海南岛，故名海南省。1988年建省。因秦以后称这一带为琼台、琼州或琼崖，故简称“琼”。'
	},

	_620000 : {
		name : '甘肃省（甘或陇）',
		description : '甘肃一名始于11世纪西夏王朝设置的十二监军之一，治所在甘州（今张掖县），辖甘州、肃州（今酒泉）二州，取二州首字组成甘肃。元设甘肃行省，明代并入陕西省，清代恢复省治。取全称中的“甘”字作为简称。一说认为西夏时行政中心在甘州，故简称“甘”。由于甘、陕两省间有陇山，甘肃古时别称陇西，故又简称“陇”。'
	},

	_610000 : {
		name : '陕西省（陕或秦）',
		description : '陕西一名，源于周代周、召二公“分陕而治”，今陕县张汴塬一带古称陕塬，当时的陕西就是陕塬以西的泾渭平原。唐安史之乱后设陕西节度使，陕西始转化为政区名称。宋设陕西路。元设陕西行省。清设陕西省。取全称中的“陕”字作为简称。又因辖区春秋时为秦国地，故又简称“秦”。'
	},

	_510000 : {
		name : '四川省（川或蜀）',
		description : '辖区秦时设蜀郡。汉设益州部。唐设剑南道，又分剑南东川、剑南西川两节度使。宋设西川路和峡路，后将西川、峡二路分为益州、梓州、利州、夔州四路，合称“川峡四路”，简称“四川路”，四川一名由此产生。元时合并四路，设为四川行省。自此，四川省治沿用至今。取全称中的“川”字作为简称。又因辖区西部古为蜀国地，故又简称“蜀”。'
	},

	_520000 : {
		name : '贵州省（贵或黔）',
		description : '宋代以前设矩州，因当地语音“贵”“矩”难分，故也写做贵州，元初正式命名为贵州。明设贵州布政使司。清设贵州省。取全称中的“贵”字作为简称。一说认为因境内有贵山，故简称“贵”。辖区东北部秦时属黔中郡，唐属黔中道，故又简称“黔”。一说认为境内有黔灵山、黔灵河，故简称“黔”。'
	},

	_530000 : {
		name : '云南省（云或滇）',
		description : '因在云岭以南，故名云南。西汉时设云南县。三国蜀汉时设云南郡。元设云南行省。明设云南布政使司。清设云南省。取全称中的“云”字作为简称。又因行政中心昆明一带，属战国时滇国地，故又简称“滇”。一说因境内有滇池，故简称“滇”。'
	},
	_diaoyudao : {
		name : '钓鱼岛',
		description : '钓鱼岛是中国的！！！'

	}

}