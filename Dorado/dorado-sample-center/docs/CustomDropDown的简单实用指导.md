## 给某个文本框绑定  CustomDropDown 后的一些操作

- 下面是 CustomDropDown 的 beforeExecute 事件，用于在点击下拉框前填充下拉框数据，这里填充的是一个数据表格
- onDataRowClick 是数据表格绑定的事件，用于在点击表格的某行数据后回填给下拉框绑定的文本框

```
//@Bind #ropCustomDropDown.beforeExecute
!function(self, arg, dataSetProjectState) {
	var currentEntity = dataSetProjectState.getData("#");
	var projectId = currentEntity.get("id");
	dataSetProjectState.set("parameter", {id : projectId}).flushAsync();
};

//@Bind #ropDataGrid.onDataRowClick
!function(self, arg, dataSetProjectState) {
	var currentEntity = dataSetProjectState.getData("#");
	var developRopNum = currentEntity.get("developRopNum");
	var ropNum = currentEntity.get("ropNum");

	// 关闭自定义下拉框，并向
	var dropDown = dorado.widget.DropDown.findDropDown(self);
	if (ropNum != null && ropNum != "") {
		dropDown.close(ropNum);
	} else if (developRopNum != null && developRopNum != "") {
		dropDown.close(developRopNum);
	}
};
```

- 其中 ropCustomDropDown 是 CustomDropDown 的id，ropDataGrid 是 CustomDropDown 绑定的数据表格，dataSetProjectState 是 ropDataGrid 绑定的 DataSet 对象
