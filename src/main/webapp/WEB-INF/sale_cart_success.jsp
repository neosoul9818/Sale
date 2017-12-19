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
var t=5;//设定倒计时的时间 
var interval;
function refer(){  
    $("#countdown").text(t+"秒后自动跳转到主页 "); // 显示倒计时 
    t--; // 计数器递减 
    if(t<=0){
    	clearInterval(interval);
    	window.location.href="index.do";
    }
} 

$(function() {
	
	 clearInterval(interval);
	 interval= setInterval("refer()",1000);//启动1秒定时
})
</script>
<title>硅谷商城</title>
</head>
<body>
	添加购物车成功<br>
	<font id="countdown" color="red"></font>
</body>
</html>