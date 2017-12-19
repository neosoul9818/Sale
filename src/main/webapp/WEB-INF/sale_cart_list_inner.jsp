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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">


</script>
<style type="text/css">
.Cprice {
	width: 100%;
	position: fixed;
	bottom: 0px;
	z-index: 999;
}
</style>
<title>硅谷商城</title>
</head>
<body>
	<c:if test="${empty list_cart }">
		<h3 align="center" style="color: red">购物车空空如也，客官老爷赶紧赏点银子买东西咯</h3>
		<input id="hide_cart" type="hidden" value="${list_cart }" />
		<font id="countdown" color="red"></font>
	</c:if>
	<c:if test="${not empty list_cart }">
		<div>
			全选/全不选<input id="checkBoxs" onclick="checkAll(this)" type="checkbox">
		</div>
		<div onclick="all_to_check()" id="in_check" class="one"
			style="width: 100%">
			<c:forEach items="${list_cart }" var="cart">
				<div class="one" style="width: 100%">
					<input type="checkbox" ${cart.shfxz=="1" ? "checked" : ""}
						onclick='change_item("check",${cart.sku_id},${cart.tjshl },${cart.sku_jg },this.checked)' />
					<img width="50px" alt="图片不存在" src="upload/image/${cart.shp_tp }" />
					<span>${cart.sku_mch }</span> <span> <a
						href='javascript:reduce_item("check",${cart.sku_id},${cart.tjshl-1 },${cart.sku_jg });'><b>-</b></a>
						<input type="text" id="cart_tjshl" size="2" value="${cart.tjshl }"
						onchange="tjshl_change(${cart.shfxz},${cart.sku_id },${cart.sku_jg },this.value,${cart.tjshl })" />个
						<a
						href='javascript:plus_item("check",${cart.sku_id},${cart.tjshl+1 },${cart.sku_jg });'><b>+</b></a>
						单价： <b>￥${cart.sku_jg }</b> &nbsp;<a
						href="javascript:delete_cart('${cart.sku_id }',${cart.id });">删除</a>
					</span>
				</div>
			</c:forEach>
		</div>
		<div class="Cprice">
			共<span>${size}</span>件商品&nbsp;&nbsp;&nbsp;&nbsp; 总金额：￥<span>${sum }</span>
			<form action="goto_order.do" method="post">
				<input class="goprice" id="submit_btn" type="submit" onclick="check_user('${user}')"
					value="去结算" /> <input type="hidden" name="sum" value="${sum }" />
			</form>
		</div>

	</c:if>
</body>
</html>