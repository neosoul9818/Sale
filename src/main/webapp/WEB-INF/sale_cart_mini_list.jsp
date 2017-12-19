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
<script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="js/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function a() {

	}
</script>
<title>硅谷商城</title>
</head>
<body>
	<div class="cart_pro">
		<h6>最新加入的商品</h6>
		<div class="one">
			<c:forEach items="${list_cart }" var="cart">
				<div class="one">
					<img width="50px" alt="图片不存在" src="upload/image/${cart.shp_tp }" />
					<span>${cart.sku_mch }</span> <span> <b>￥${cart.sku_jg }</b>
						&nbsp;删除
					</span>
				</div>
			</c:forEach>
		</div>
		 <div class="gobottom">
			共<span>${size}</span>件商品&nbsp;&nbsp;&nbsp;&nbsp; 共计￥<span>${sum }</span>
			<button class="goprice">去购物车</button>
		</div> 
	</div>
</body>
</html>