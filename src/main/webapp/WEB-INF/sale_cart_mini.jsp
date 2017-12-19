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
	function show_mini_cart_list () {
		$.post("show_mini_cart.do",function(data){
			
			$("#mini_cart_list").html(data);
		});
		$("#mini_cart_list").show();
	}
	
	function hide_mini_cart_list() {
		$("#mini_cart_list").hide();
	}
</script>
<title>硅谷商城</title>
</head>
<body>
<div class="card">
			
	<a href="goto_cart_list.do" target="_blank" onmouseout="hide_mini_cart_list()" onmouseover="show_mini_cart_list()">购物车<div class="num">0</div></a>
	<div id="mini_cart_list">
	</div>
</div>

</body>
</html>