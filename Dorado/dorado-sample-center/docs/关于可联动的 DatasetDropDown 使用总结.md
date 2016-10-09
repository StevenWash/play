## 涉及的文件
- 视图文件 `com/bstek/dorado/sample/widget/form/LinkageDropDowns2.view.xml`
- Js文件 `com/bstek/dorado/sample/widget/form/LinkageDropDowns2.js`
- Java文件 `com/bstek/dorado/sample/widget/form/DataSetDropDown1.java`

## 基本说明

- 大类下拉框值的变化会导致小类下拉框的值异步更新
- 通过提交按钮将下拉框中选中的key,value值异步提交到后台

## 程序流程说明

- AutoForm(dsAutoForm)->DataSet(dsForm)->DataType(Form)，通过 **dsForm.onReady** 事件完成表单初始化
- AutoFormElement(categoryName) 通过 trigger 属性绑定 DataSetDropDown(ddCategories)，ddCategories通过 assignmentMap 属性完成在
用户选中下拉项后将key和value分别赋值给categoryName和categoryId，
- ddCategories 在用户选中下拉项后通过 **onValueSelect** 事件将  dsAutoForm 中的 productId和productName置空
- ddProducts 通过 **beforeExecute** 事件来根据分类值来异步更新产品列表
- Button(btnSubmit) 通过 **onClick** 事件绑定  AjaxAction(ajaxActionSubmit)，并将表单值异步提交给后台处理，处理完毕后将结果交给回调函数通知处理的结果
- ajaxActionSubmit 通过 service 属性绑定后台的服务，服务的方法必须标注`@Expose`

