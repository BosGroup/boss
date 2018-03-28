<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- 
		shiro权限控制方式三:通过标签方式
		页面必须是jsp页面
		引入shiro的标签库
		authenticated:通过认证的用户可以看到被此标签包裹的内容
		hasPermission:检查用户是否拥有对应的权限(name的值),如果有就可以看到内容,没有就看不到
		hasRole:检查用户是否拥有对应的角色,如果有就可以看到内容,没有就看不到
	 -->
	<shiro:authenticated>
		当前用户已经通过认证(登录成功)了
	</shiro:authenticated><hr>
	
	<shiro:hasPermission name="courierAction_pageQuery">
		你拥有权限名为courier_pageQuery的权限
	</shiro:hasPermission><hr>
	
	<shiro:hasRole name="admin">
		你拥有admin角色的权限
	</shiro:hasRole>
</body>
</html>