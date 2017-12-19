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
<link rel="stylesheet" type="text/css" href="css/css.css">
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function a () {
	
	}
	
</script>
<title>硅谷商城</title>
</head>
<body>
<div>
收银台:<br>
数据库中生成订单！！但是没有yjsdshj，但是有chjshj，且jdh进度号是1，订单已提交！<br>
数据库生成物流flow，没有psshj配送时间，psmsh配送描述，yhid，mdd，ywy业务员，lxfsh业务员联系方式
<form action="pay_order.do" method="post">
	<input type="text" name="sum" value="${order.zje}"/>
	<input class="goprice" type="submit" value="付款" />
</form>
</div> 


</body>
</html>