<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Dorado学习目录</title>
</head>
<body style="margin-left: 100px">
	<p>
	<a href="<%=path%>/test.sample.one.panel.d">panel</a><br>
	<a href="<%=path%>/test.sample.one1.panel.d">panel1</a><br>
	<a href="<%=path%>/test.sample.two.SimpleAjax.d">Ajax</a><br>
	<div>
		<ul>
			<li>test.sample.two.SimpleAjax.d</li><br>
			<li>Ajax的简单使用</li>
			<li>Dialog的简单使用</li>
			<li>Component和 DataProvider的注解的使用</li>
			<li>全局model的使用，DataGrid->DataSet->DataType</li>
		</ul>
	</div>
	<a href="<%=path%>/test.sample.sysinfo.SystemInfo.d">显示系统信息</a><br>
	<div>
		<ul>
			<li>test.sample.sysinfo.SystemInfo.d</li><br>
			<li>Container的使用</li>
		</ul>
	</div>
	<a href="<%=path%>/test.sample.contacts.CompanyInfo.d">公司联系方式</a><br>
	<div>
		<ul>
			<li>test.sample.contacts.CompanyInfo.d</li><br>
			<li>dbtool生成JavaBean的使用</li>
			<li>HibernateDao 的使用</li>
			<li><a href="http://wiki.bsdn.org/pages/viewpage.action?pageId=10355489">AutoForm 的使用</a></li>
		</ul>
	</div>
	<a href="<%=path%>/test.sample.contacts.AllContacts.d">员工信息</a><br>
	<ul>
		<li>test.sample.contacts.AllContacts.d</li><br>
		<li>DataPilot控件以及分页的使用</li>
		<li>DataGrid控件的Filter用法，有关Page和Criteria对象的封装</li>
	</ul>
</body>
</html>