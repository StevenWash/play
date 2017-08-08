## 需求 

打开一个 Dorado 视图后通过在 url 后面加上 `?param1=value1&param2=value2` 的方式传入参数,通过这些参数可以预先加载一个 DataSet 对象的数据.

## DataSet 的 loadMode 属性的 preload 模式

loadMode 属性一共有
 
1. preload 视图加载的时候装载数据
2. onReady 视图加载完毕后装载数据
3. lazy 需要加载的时候才装载数据
4. manual 手动控制何时装载数据

## 具体做法

这里将 `dataSetRApply` DataSet 对象设置为 preload 模式, dataProvider 为 `releaseRApply#findApplyByTask`

访问的 url 为 

`http://localhost:9085/BigScm/com.ctrip.scm.web.view.release.PhaRnApply.d?taskId=T335&tester=%25E9%2599%2588%25E5%25A5%258E`

其中 `%25E9%2599%2588%25E5%25A5%258E` 为中文`陈奎`,在 php 中经过如下操作后获得

```php
$ownername = urlencode(urlencode($ownerUser->getRealName()));
```

这里之所以要嵌套一次 urlencode 的原因在于浏览器会自动进行一次  urldecode 解码, 嵌套一次后浏览器便无法自动转码, 传到后端后再通过嵌套两次 `URLDecoder.decode` 方法
进行解码即可获取到传入的中文参数

后端的 findApplyByTask 方法如下

```java
	@DataProvider
	public List<RApply> findApplyByTask() {
		DoradoContext dc = DoradoContext.getCurrent();
		String taskId = (String) dc.getParameter("taskId");
		String tester = (String) dc.getParameter("tester");
		
		if (StringUtils.isBlank(taskId) || StringUtils.isBlank(tester)) {
			return null;
		}
		
		String currentCharset = Charset.defaultCharset().displayName();
		try {
			tester = URLDecoder.decode(URLDecoder.decode(tester, currentCharset), currentCharset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rApplyManager.newApplyByTask(taskId, tester);
	}
```

## 总结

通过如上方法可以在视图加载的同时通过传入相关参数来控制 DataSet 对象的数据加载

需要注意的是 urlencode('陈奎') 返回的内容是 `%E9%99%88%E5%A5%8E`
再次 urlencode('%E9%99%88%E5%A5%8E') 返回的内容是 `%25E9%2599%2588%25E5%25A5%258E`
区别在于将`%`变成了`%25`,通过对中文字符进行了简单的编码从而避免了传输过程中导致的乱码问题