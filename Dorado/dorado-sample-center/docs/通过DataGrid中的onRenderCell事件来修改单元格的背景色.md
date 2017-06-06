# 通过DataGrid中的onRenderCell事件来修改单元格的背景色

## 具体要求

- 以第二列中值作为基准,其后的列中的值跟其对比,一致颜色不变,不一致则将单元格背景色修改成红色 

## 相关代码

- 视图文件如下,其中包含了 DataSet, ToolBar, DataPilot, DataGrid

![视图文件](http://7xt8a6.com1.z0.glb.clouddn.com/onrenderCell.PNG)

- 对应的 DataGrid 的 onRenderCell 事件如下, 用于在列表展示的时候进行数据渲染和颜色处理

```
// @Bind #dataGridAppVersion.onRenderCell
!function(self, arg) {
	debugger;
	var appID = arg.data.get("appID");
	var fws = arg.data.get("fws");
	var prod = arg.data.get("prod");
	var uat = arg.data.get("uat");
	var fat18 = arg.data.get("fat18");
	var fat103 = arg.data.get("fat103");
	
	var columnId = typeof arg.column != "undefined" ? arg.column.get("id") : "";
	if (columnId != "") {
		if (columnId == "appID") {
			arg.dom.innerText = appID;
		} else if (columnId == "PROD") {
			arg.dom.innerText = prod;
		} else if (columnId == "FWS") {
			arg.dom.innerText = fws;
			if (prod != "" && prod != fws) {
				arg.dom.style.background = "#FF0000";
			} else {
				arg.dom.style.background = "";
			}
		} else if (columnId == "UAT") {
			arg.dom.innerText = uat;
			if (prod != "" && prod != uat) {
				arg.dom.style.background = "#FF0000";
			} else {
				arg.dom.style.background = "";
			}
		} else if (columnId == "FAT18") {
			arg.dom.innerText = fat18;
			if (prod != "" && prod != fat18) {
				arg.dom.style.background = "#FF0000";
			} else {
				arg.dom.style.background = "";
			}
		} else if (columnId == "FAT103") {
			arg.dom.innerText = fat103;
			if (prod != "" && prod != fat103) {
				arg.dom.style.background = "#FF0000";
			} else {
				arg.dom.style.background = "";
			}
		}
	}
};
```

- 其中关键点在于通过 arg 的 dom 属性获取到单元格的 HTMLElement 对象,随后便是对HTML的 dom 对象进行处理

- dataSetAppVersion 绑定的 dataProvider 的值为 `libraryApp#findAllAppVersionInfo`, 相关代码如下

- LibraryApp.java (@Component)

```
@DataProvider
public void findAllAppVersionInfo(Page<ApplicationInfo> page,Criteria criteria) {
	applicationInfoManager.findAllAppVersionInfo(page,criteria);
}
```

- ApplicationInfoManager.java (@Component)

```
private ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

public void findAllAppVersionInfo(Page<ApplicationInfo> page, Criteria criteria) {
	try {
		DetachedCriteria detachedCriteria = applicationInfoDao.buildDetachedCriteria(criteria, ApplicationInfo.class);
		applicationInfoDao.pagingQuery(page, detachedCriteria);
		Collection<ApplicationInfo> list = page.getEntities();
		// 通过API 来自设置不同环境的最新版本
		// 全线程
		List<GetVersionNameWorker> workerList = new ArrayList<>();
		for (ApplicationInfo applicationInfo : list) {
			workerList.add(new GetVersionNameWorker(applicationInfo.getAppID()));
		}
		
		List<Future<Map<String, String>>> futureList = executorService.invokeAll(workerList);
		Map<String, String> dataMap = new HashMap<>();
		if (futureList != null) {
			for (Future<Map<String, String>> future : futureList) {
				dataMap.putAll(future.get());
			}
		}
		
		for (ApplicationInfo applicationInfo : list) {
			applicationInfo.setUat(dataMap.get(applicationInfo.getAppID().concat(ENV_UAT)));
			applicationInfo.setProd(dataMap.get(applicationInfo.getAppID().concat(ENV_PROD)));
			applicationInfo.setFws(dataMap.get(applicationInfo.getAppID().concat(ENV_FWS)));
			applicationInfo.setFat18(dataMap.get(applicationInfo.getAppID().concat(ENV_FAT18)));
			applicationInfo.setFat103(dataMap.get(applicationInfo.getAppID().concat(ENV_FAT103)));
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

/**
 * 异步获取版本号对象
 * 
 * @author chen_k
 *
 */
private class GetVersionNameWorker implements Callable<Map<String, String>> {

	private String appId;
	public GetVersionNameWorker(String appId) {
		super();
		this.appId = appId;
	}
	@Override
	public Map<String, String> call() throws Exception {
		Map<String, String> resultData = new HashMap<>();
		try {
			String result = HttpClientUtils.get("http://finance.tools.qa.nt.ctripcorp.com/api/AppidVersion/appid=".concat(appId), "UTF-8");
			com.alibaba.fastjson.JSONObject obj = com.alibaba.fastjson.JSONObject.parseObject(result);
			if (obj != null) {
				resultData.put(appId.concat(ENV_PROD), obj.getString("PROD"));
				resultData.put(appId.concat(ENV_UAT), obj.getString("UAT"));
				resultData.put(appId.concat(ENV_FWS), obj.getString("FWS"));
				resultData.put(appId.concat(ENV_FAT18), obj.getString("FAT18"));
				resultData.put(appId.concat(ENV_FAT103), obj.getString("FAT103"));
			}
			return resultData;
		} catch (Exception e) {
			return resultData;
		}
	}
}
```

## 参考

- [dorado7 jsdoc](http://dorado7.bsdn.org/jsdoc/)