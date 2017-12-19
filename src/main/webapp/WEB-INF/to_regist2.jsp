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
<link rel="shortcut icon" type="image/icon" href="images/jd.ico">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/css.css">
<link rel="stylesheet" type="text/css" href="css/finishCart.css">
<link rel="stylesheet" type="text/css" href="css/list.css">
<link rel="stylesheet" type="text/css" href="css/logistics.css">
<link rel="stylesheet" type="text/css" href="css/pay.css">
<link rel="stylesheet" type="text/css" href="css/search.css">
<link rel="stylesheet" type="text/css" href="css/sign.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript">
	function a () {

	}
	function img_click(index) {
		$("#file_"+index).click();
		alert(1);
		
	}
	
	function img_change(index) {
		
		//获取图片缓存
		var file = $("#file_"+index)[0].files[0];
		
		//转化为url对象
		var url = window.URL.createObjectURL(file);
		//alert(url);
		//显示图片
		$("#img_"+index).attr("src", url);
		
		// 用户选择的空白图片，最佳新的图片
		var length = $(":file").length;
		if(length == (index+1)) {
			img_add(index);
		}
	}
	
	function img_add(index) {
		index = index +1;
		var a = '<div id="div_file_"'+index+'>';
		var b = '<img id="img_'+index+'" src="image/upload_hover.png" width="70px;" onclick="img_click('+index+')"/>';
		var c = '<input id="file_'+index+'" type="file" name="files" style="display:none" onchange="img_change('+index+')"/><br>';
		var d = '</div>';
		
		$("#div_file_"+(index-1)).after(a+b+c+d);
	}
	
    // 1、获得焦点，内容为空，tip默认提示
    // 2、失去焦点，内容为空，tip为隐藏
    // 3、其他情况（按键抬起，失去焦点且内容不为空，或最后表单总验证）
    //    按键抬起为空，报错，不能为空
    //    内容匹配，成功
    //    内容不匹配，失败
    // 4、密码要进行安全等级检测，含数字、字母、特殊字符为强，两种为中，一种为弱
    // 5、确认密码失去焦点的时候就要验证是否一致
    function pwdFocus(){
    var pwdId=$("pwdId");
    pwdId.className="import_prompt";
    pwdId.innerHTML="密码长度为6-16";
    }
    
/*当鼠标离开密码文本框时，提示文本及样式*/    
function pwdBlur(){
    var pwd=$("pwd");
    var pwdId=$("pwdId");
    if(pwd.value==""){
        pwdId.className="error_prompt";
        pwdId.innerHTML="密码不能为空，请输入密码";
        return false;
        }
    if(pwd.value.length<6 || pwd.value.length>16){
        pwdId.className="error_prompt";
        pwdId.innerHTML="密码长度为6-16";
        return false;
        }
        pwdId.className="ok_prompt";
        pwdId.innerHTML="密码输入正确";
        return true;
    }
    
/*当鼠标离开重复密码文本框时，提示文本及样式*/    
function repwdBlur(){
    var repwd=$("repwd");
    var pwd=$("pwd");
    var repwdId=$("repwdId");
    if(repwd.value==""){
        repwdId.className="error_prompt";
        repwdId.innerHTML="重复密码不能为空，请重复输入密码";
        return false;
        }
    if(repwd.value!=pwd.value){
        repwdId.className="error_prompt";
        repwdId.innerHTML="两次输入的密码不一致，请重新输入";
        return false;
        }
        repwdId.className="ok_prompt";
        repwdId.innerHTML="两次密码输入正确";
        return true;
    }

</script>
<title>硅谷商城</title>
</head>
<body>
    <!--头部-->
    <div class="header">
        <a class="logo" href="##"></a>
        <div class="desc">欢迎注册</div>
    </div>
    <!--版心-->
    <div class="container">
    <form action="regist.do" method="post" enctype="multipart/form-data">
    	<!--京东注册模块-->
    	<div class="register">
    		<!--用户名-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="userName">用&nbsp;户&nbsp;名</label>
    				<input type="text" id="userName" name="yh_mch" placeholder="您的账户名和登录名" />
    				<i></i>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!-- 用户昵称 -->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="userName">用 户 昵 称</label>
    				<input type="text" id="userNch" name="yh_nch" placeholder="您的昵称" />
    				<i></i>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!--用户头像-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="img">用 户 头 像</label>
					<input id="file_0" style="display:none" type="file" name="files" onchange="img_change(0)"/><br>
    				<i></i>
    			</div>
    				<img id="img_0" src="images/image.jpg" style="cursor: pointer;" width="70px;" onclick="img_click(0)"/>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!--用户级别-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="mobile">用 户 级 别</label>
    				<input type="text" name="yh_shjh" id="mobile" placeholder="请输入用 户 级 别" />
    				<i></i>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!--设置密码-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="pwd">设 置 密 码</label>
    				<input type="password" name="yh_mm" id="pwd" placeholder="建议至少两种字符组合" onfocus="pwdFocus()" onblur="pwdBlur()"/>
    				<i></i>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!--确认密码-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="pwd2">确 认 密 码</label>
    				<input type="password"  id="repwd" placeholder="请再次输入密码" onblur="repwdBlur()"/>
    				<i></i>
    			</div>
    			<div id="repwdId"></div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
			<!--设置密码-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="email">邮 箱 验 证</label>
    				<input type="text" name="yh_yx" id="email" placeholder="请输入邮箱" />
    				<i></i>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!--手机验证-->
    		<div class="register-box">
    			<!--表单项-->
    			<div class="box default">
    				<label for="mobile">手 机 验 证</label>
    				<input type="text" name="yh_shjh" id="mobile" placeholder="请输入手机号" />
    				<i></i>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		
    		 <!--注册协议-->
    		<div class="register-box xieyi">
    			<!--表单项-->
    			<div class="box default">
    				<input type="checkbox" id="ck" />
    				<span>我已阅读并同意<a href="##">《京东用户注册协议》</a></span>
    			</div>
    			<!--提示信息-->
    			<div class="tip">
    				<i></i>
    				<span></span>
    			</div>
    		</div>
    		<!--注册-->
    		<button id="btn">注册</button>
    	</div>
    </form>	
    </div>
</body>
</html>