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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function b(){}
</script>
<title>硅谷商城</title>
</head>
<body>
	检索结果列表<br>
	<c:forEach items="${sku_list }" var="sku">
	 	<div style="border:red 1px solid; float:left; width: 200px;height:250px ; margin-left:10px ; margin-top:10px ;">
			<img style="width: 50px;height:75px ; margin-left:10px ; margin-top:10px ;" src="upload/image/${sku.shp_tp }">
			<br>
			商品名称：<a href="goto_sku_detail.do?spu_id=${sku.shp_id}&sku_id=${sku.id }">${sku.sku_mch}</a><br>        
			商品价格：${sku.jg }<br>
			商品销量：${sku.sku_xl}<br>
			商品库存：${sku.kc}
		</div>
	</c:forEach>
</body>
</html>