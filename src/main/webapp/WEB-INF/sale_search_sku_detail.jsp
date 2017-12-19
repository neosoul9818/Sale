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
	商品详情页面
	<hr>
	${obj_sku.sku_mch}
	<hr>
	${obj_sku.jg}
	<hr>
	<!-- sku列表 -->
	<c:forEach items="${list_sku}" var="sku">
		<a href="goto_sku_detail.do?sku_id=${sku.id}&spu_id=${sku.shp_id}" >${sku.sku_mch}</a><br>
	</c:forEach>
	<hr>
	<!-- 商品描述 -->
	${obj_sku.spu.shp_msh}:<br>
	<hr>
	<form action="add_cart.do" method="post">
		<input type="hidden" name="sku_mch" value="${obj_sku.sku_mch}" />
		<input type="hidden" name="sku_jg" value="${obj_sku.jg}" />
		<input type="hidden" name="tjshl" value="1" />
		<input type="hidden" name="hj" value="${obj_sku.jg}" />
		<input type="hidden" name="shp_id" value="${obj_sku.shp_id}" />
		<input type="hidden" name="sku_id" value="${obj_sku.id}" />
		<input type="hidden" name="shp_tp" value="${obj_sku.spu.shp_tp}" />
		<input type="hidden" name="shfxz" value="1" />
		<input type="hidden" name="kcdz" value="${obj_sku.kcdz}" />
		<c:if test="${not empty user}">
			<input type="hidden" name="yh_id" value="${user.id}" />
		</c:if>
		<input type="submit" value="添加购物车"/>
	</form>
	<hr>
	<!-- 商品的多个图片样式 -->
	<c:forEach items="${obj_sku.list_image}" var="image">
		<img src="upload/image/${image.url}" height="200px"/>
	</c:forEach>
	<hr>
	<!-- 商品的规格（属性和属性值） -->
	<c:forEach items="${obj_sku.list_av_name}" var="av_name">
		${av_name.shxm_mch}:${av_name.shxzh_shxzhmch}<br>
	</c:forEach>
	
</body>
</html>