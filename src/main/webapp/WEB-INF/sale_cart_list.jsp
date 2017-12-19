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
<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/metro/easyui.css">
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css">
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	function change_item (change_type,sku_id,tjshl,sku_jg,shfxz) {
		
		all_to_check();
		
		if(shfxz){
			shfxz="1";
			//change_type选中check
			change_type="check";
		}else{
			shfxz="0";
			change_type="uncheck";
		}
		
		$.post("change_item.do",{change_type:change_type,sku_id:sku_id,tjshl:tjshl,sku_jg:sku_jg,shfxz:shfxz},function(data){
			
			$("#cart_list_inner").html(data);
		});
	}
	/*全选全不选*/
	function checkAll(all_check) {
		var flag = all_check.checked;
		$.each($("#in_check :checkbox"),function(i,n) {
			n.checked=flag;
			$(n).click();
		});
	}
	
	/*当子复选框选中状态一致时，全选按钮的选中状态也更新*/	
	function all_to_check(){
		var check_len =$("#in_check :checkbox").length;
		var select_len=0;
		var unselect_len=0;
		$.each($("#in_check :checkbox"),function(i,n) {
			
			if(n.checked==true){
				select_len++;
			}else{
				unselect_len++;
			}
		});
		if(select_len==check_len){
			//全选按钮被迫打钩
			$("#checkBoxs").attr("checked",true);
		}
		if(unselect_len==check_len){
			//全选按钮被迫去勾
			$("#checkBoxs").attr("checked",false);
		}
		
	}
	
	/* reduce_item("check",${cart.sku_id},${cart.tjshl-1 },${cart.sku_jg }) */
	function reduce_item(shfxz,sku_id,new_tjshl,sku_jg){
		if(new_tjshl<=0){
			alert("不能减少为0或负数");
			$("#cart_tjshl").val("1");
			return;
		}
		//购物减少后的数量正常
		$.post("change_item.do",{change_type:"check",sku_id:sku_id,tjshl:new_tjshl,sku_jg:sku_jg,shfxz:"1"},function(data){
			
			$("#cart_list_inner").html(data);
		});
		
	}
	
	/* plus_item("check",${cart.sku_id},${cart.tjshl+1 },${cart.sku_jg }) */
	function plus_item(shfxz,sku_id,new_tjshl,sku_jg){
		if(new_tjshl>200){
			alert("单个商品每次不能购物超过200个");
			$("#cart_tjshl").val("200");
			return;
		}
		//购物增加后的数量正常
		$.post("change_item.do",{change_type:"check",sku_id:sku_id,tjshl:new_tjshl,sku_jg:sku_jg,shfxz:"1"},function(data){
			
			$("#cart_list_inner").html(data);
		});
	}
	
	
	//当单个购物车中的个数 文本框数据更改时，触发后台，更新购物车车数据
	/* function tjshl_change(${cart.shfxz},${cart.sku_id },${cart.sku_jg },this.value,${cart.tjshl }) */
	function tjshl_change(shfxz,sku_id,sku_jg,new_tjshl,old_tjshl) {
		
		if(new_tjshl>200){
			alert("单次无法购买超过200个商品");
			
			$("#cart_tjshl").val(old_tjshl);
			return;
		}
		//正常购物，购物数量正常
		
		
		$.post("change_item.do",{change_type:"check",sku_id:sku_id,tjshl:new_tjshl,sku_jg:sku_jg,shfxz:"1"},function(data){
			
			$("#cart_list_inner").html(data);
		});
	}
	/*删除购物车*/
	function delete_cart (cart_sku_id,cart_id) {
		$.post("delete_cart.do",{sku_id:cart_sku_id,id:cart_id},function(data){
			$("#cart_list_inner").html(data);
		});
	}
	/*倒计时，步骤1*/	 
	 var t=10;//设定倒计时的时间 
	 var interval;
	 function refer(){  
	     $("#countdown").text(t+"秒后自动跳转到主页 "); // 显示倒计时 
	     t--; // 计数器递减 
	     if(t<=0){
	     	clearInterval(interval);
	     	window.location.href="index.do";
	     }
	 } 
	 /*倒计时，步骤2*/ 
	 $(function() {
		var $value=$("#hide_cart").val();
		if($value==""){
			//refer();
			//t=10;
			 clearInterval(interval);
			 interval= setInterval("refer()",1000);//启动1秒定时
		}
	})
	
	/*检查用户是否登录*/
	 function check_user(user) {
		if(user==""){
			alert("请先登录用户");
			$("#submit_btn")[0].disabled="disabled";
			window.location.href="goto_login.do";
			return false;
		}
	}

</script>
<title>硅谷商城</title>
</head>
<body>
	购物车详细列表<br>
	<div id="cart_list_inner">
		<jsp:include page="sale_cart_list_inner.jsp"></jsp:include>
	</div>
</body>
</html>