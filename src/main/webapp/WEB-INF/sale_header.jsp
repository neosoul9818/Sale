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
	$(function (){
		var yh_nch = getCookeValue2("yh_nch");
		$("#yh_nch").html('<a href="javascript:;">'+yh_nch+'</a>');
	});
	//我学的
	function getCookeValue2(key){
		var value="";
		//获得的是当前文档的所有的cookie
		var cookies= document.cookie;
		//正则表达式去除空格每个cookie对象之间的空格
		cookies= cookies.replace(/\s/,"");
		var cookie_array = cookies.split(";");
		for (i = 0; i < cookie_array.length; i++) {
			var cookie =cookie_array[i].split("=");
			if(cookie[0]==key){
				value=decodeURIComponent(cookie[1]); 
				 /* value=cookie[1];  */
			}
		}
		return value;
	}
	//老师写的
	function getCookeValue(key){
		var value = "";
		//得到当前cookie
		var cookies = document.cookie;
		//将因为cookie是键值对（之间有分号和空格），键=值； 键=值,返回一个新的cookies
		cookies = cookies.replace(/\s/,"");
		
		var cookie_array = cookies.split(";");
		
		for(i=0;i<cookie_array.length;i++){
			var cookie = cookie_array[i].split("=");
			if(cookie[0]=="test"){
				value = decodeURIComponent(cookie[1]);
				value=cookie[1];
			}
		}
		return value;
	}
</script>
<title>硅谷商城</title>
</head>
<body>

	<div class="top">
		<div class="top_text">

			<c:if test="${empty user}">
				<span id="yh_nch" style="color:red"><a>${yh_nch}</a></span>  <a href="goto_login.do">请登陆</a>  <a href="to_regist.do">注册</a>
			</c:if>
			
			<c:if test="${not empty user}">
				<a href="javascript:;">欢迎${user.yh_nch}</a>
				<a href="javascript:;"> 我的订单</a>  
				<a href="goto_out.do">退出</a>
			</c:if>
		</div>
	</div>


</body>
</html>