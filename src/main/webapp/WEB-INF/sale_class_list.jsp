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
/* 	$(function (){
		$.getJSON("js/json/class_1.js",function(data){
			// 用js循环json的分类集合
			$(data).each(function(i,json){
				// 将分类集合的内容生成<option>对象加载到下拉列表中
				$("#class_1_ul").append("<li  onmouseover='get_class_2(this.value)'  value="+json.id+">"+json.flmch1+"</li>");
			});
		});
	}); */
	
	$(function(){
		$.getJSON("js/json/class_1.js",function(data){
			$(data).each(function(i,json){
				$("#class_1_ul").append("<li value="+json.id+" onmouseover='get_class_2_by_class_1_id(this.value)'><a href='javascript:;'>"+json.flmch1+"</a></li>");
			});
		});
	});
	
	function get_class_2_by_class_1_id(class_1_id) {
		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			$("#class_2_ul").empty();
			$(data).each(function(i, json){
				$("#class_2_ul").append("<li value="+json.id+"><a href='goto_search_class.do?class_2_id="+json.id+"&class_2_name="+json.flmch2+"' target='_blank'>"+json.flmch2+"</a></li>");
			});
		 $("#class_2_ul").show();  
		});
	}
	
	
	function get_class_2(class_1_id){

		$.getJSON("js/json/class_2_"+class_1_id+".js",function(data){
			// 用js循环json的分类集合
			$("#class_2_ul").empty();
			$(data).each(function(i,json){
				// 将分类集合的内容生成<option>对象加载到下拉列表中
				$("#class_2_ul").append("<li value="+json.id+"><a href='goto_search_class.do?class_2_id="+json.id+"&class_2_name="+json.flmch2+"' target='_blank'>"+json.flmch2+"</li>");
			});
			$("#class_2_ul").show();
		});
	}
</script>
<title>硅谷商城</title>
</head>
<body>
<div class="menu">
		<div class="nav">
			<div class="navs">
				<div class="left_nav">
					全部商品分类
					<div class="nav_mini">
						<ul id="class_1_ul">
							<li>
								<!-- <a href="">家用电器</a> -->
								<div class="two_nav" id="class_2_ul">
									<!-- <a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a>
									<a href="">11111</a> -->
								</div>
							</li>
							<li><a href="">手机、数码、通信</a></li>
							<li><a href="">电脑、办公</a></li>
							<li><a href="">家具、家居、家装</a></li>
							<li><a href="">男装、女装、内衣</a></li>

						</ul>
					</div>
				</div>
				<!-- <ul>
					<li><a href="">服装城</a></li>
					<li><a href="">美妆馆</a></li>
					<li><a href="">超市</a></li>
					<li><a href="">全球购</a></li>
					<li><a href="">闪购</a></li>
					<li><a href="">团购</a></li>
					<li><a href="">拍卖</a></li>
					<li><a href="">金融</a></li>
					<li><a href="">智能</a></li>
				</ul> -->
			</div>
		</div>
	</div> 

	<!-- <ul id="class_1_ul" style="float:left;"></ul>
	<ul id="class_2_ul" style="float:left;"></ul>  -->
</body>
</html>