<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<link rel="shortcut icon" type="image/icon" href="images/jd.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
$(function(){
	/* 一级	分类 */
	$('.nav_mini ul li').hover(function(){
		/* 找到this的子代元素two_nav，然后显示two_nav类选择器对应的元素 */
		$(this).find('.two_nav').show(100);
	},function(){
		$(this).find('.two_nav').hide(100);
	}) 
}) 
</script>
<title>硅谷商城</title>
</head>
<body>
	<jsp:include page="sale_header.jsp"></jsp:include>

	<div class="top_img">
		<img src="images/top_img.jpg" alt="">
	</div>
	
	<jsp:include page="sale_search_area.jsp"></jsp:include>
	<jsp:include page="sale_class_list.jsp"></jsp:include>
	<div class="banner">
		<div class="ban">
			<img src="images/banner.jpg" width="980" height="380" alt="">
		</div>
	</div>
</body>
</html>