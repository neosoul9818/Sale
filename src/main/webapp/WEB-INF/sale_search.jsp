<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"  %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath %>">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<link rel="shortcut icon" type="image/icon" href="images/jd.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript">
	function b(){}
</script>
<title>硅谷商城</title>
</head>
<body>
	${class_2_id}${class_2_name}
	<jsp:include page="sale_header.jsp"></jsp:include>
	<hr>
	<jsp:include page="sale_search_area.jsp"></jsp:include>
	<hr>
	<jsp:include page="sale_search_attr_list.jsp"></jsp:include>
	<hr>
	<div id="search_sku_list">
		<jsp:include page="sale_search_sku_list.jsp"></jsp:include>
	</div>
</body>
</html>