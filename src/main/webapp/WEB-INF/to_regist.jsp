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
<style type="text/css">
body,dl,dt,dd,div,form {padding:0;margin:0;}

#header,#main{
    width:650px;
    margin:0 auto;
    }
.bg{
    background-image:url(../images/register_bg.gif);
    background-repeat:no-repeat;
    width:6px;
    height:6px;
    }
    .bg_top_left{
        background-position:0px 0px;
        }
    .bg_top_right{
        background-position:0px -6px;
        }
    .bg_end_left{
        background-position:0px -12px;
        }
    .bg_end_right{
        background-position:0px -18px;
        }
    .bg_top{
        border-top:solid 1px #666666;
        }
    .bg_end{
        border-bottom:solid 1px #666666;
        }
    .bg_left{
        border-left:solid 1px #666666;
        }
    .bg_right{
        border-right:solid 1px #666666;
        }


.content{
    padding:10px;
    }
    .inputs{
        border:solid 1px #a4c8e0;
        width:150px;
        height:15px;
    }
    
    .userWidth{
        width:110px;
        }
    .content div{
        float:left;
        font-size:12px;
        color:#000;
        }
    dl{
        clear:both;
        }
    dt,dd{
        float:left;
        }
    dt{
        width:130px;
        text-align:right;
        font-size:14px;
        height:30px;
        line-height:25px;
        }
    dd{
        font-size:12px;
        color:#666666;
        width:180px;
        }
/*当鼠标放到文本框时，提示文本的样式*/
.import_prompt{
    border:solid 1px #ffcd00;
    background-color:#ffffda;
    padding-left:5px;
    padding-right:5px;
    line-height:20px;
    }
/*当文本框内容不符合要求时，提示文本的样式*/
.error_prompt{
    border:solid 1px #ff3300;
    background-color:#fff2e5;
    background-image:url(../images/li_err.gif);
    background-repeat:no-repeat;
    background-position:5px 2px;
    padding:2px 5px 0px 25px;
    line-height:20px;
    }
/*当文本框内容输入正确时，提示文本的样式*/
.ok_prompt{
    border:solid 1px #01be00;
    background-color:#e6fee4;
    background-image:url(../images/li_ok.gif);
    background-repeat:no-repeat;
    background-position:5px 2px;
    padding:2px 5px 0px 25px;
    line-height:20px;
    }
</style>
<script type="text/javascript">
/*通过ID获取HTML对象的通用方法，使用$代替函数名称*/
function $(elementId){
    return document.getElementById(elementId);
    }  
    
/*当鼠标放在用户名文本框时，提示文本及样式*/    
function userNameFocus(){
    var userNameId=$("userNameId");
    userNameId.className="import_prompt";
    userNameId.innerHTML="1、由字母、数字、下划线、点、减号组成<br/>2、只能以数字、字母开头或结尾，且长度为4-18";
    }
    
/*当鼠标离开用户名文本框时，提示文本及样式*/    
function userNameBlur(){
    var userName=$("userName");
    var userNameId=$("userNameId");
    var reg=/^[0-9a-zA-Z][0-9a-zA-Z_.-]{2,16}[0-9a-zA-Z]$/;
    if(userName.value==""){
        userNameId.className="error_prompt";
        userNameId.innerHTML="用户名不能为空，请输入用户名";
        return false;
        }
    if(reg.test(userName.value)==false){
        userNameId.className="error_prompt";
        userNameId.innerHTML="1、由字母、数字、下划线、点、减号组成<br/>2、只能以数字、字母开头或结尾，且长度为4-18";
        return false;
        }
        userNameId.className="ok_prompt";
        userNameId.innerHTML="用户名输入正确";
        return true;
    }

/*当鼠标放在密码文本框时，提示文本及样式*/    
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
    
/*当鼠标放在昵称文本框时，提示文本及样式*/    
function nickNameFocus(){
    var nickNameId=$("nickNameId");
    nickNameId.className="import_prompt";
    nickNameId.innerHTML="1、包含汉字、字母、数字、下划线以及@!#$%&*特殊字符<br/>2、长度为4－20个字符<br/>3、一个汉字占两个字符";
    }
    
/*当鼠标离开昵称文本框时，提示文本及样式*/    
function nickNameBlur(){
    var nickName=$("nickName");
    var nickNameId=$("nickNameId");
    var k=0;
    var reg=/^([\u4e00-\u9fa5]|\w|[@!#$%&*])+$/;   // 匹配昵称
    var chinaReg=/[\u4e00-\u9fa5]/g;   //匹配中文字符
    if(nickName.value==""){
        nickNameId.className="error_prompt";
        nickNameId.innerHTML="昵称不能为空，请输入昵称";
        return false;
        }
    if(reg.test(nickName.value)==false){
        nickNameId.className="error_prompt";
        nickNameId.innerHTML="只能由汉字、字母、数字、下划线以及@!#$%&*特殊字符组成";
        return false;
        }
    
    var len=nickName.value.replace(chinaReg,"ab").length;  //把中文字符转换为两个字母，以计算字符长度
    if(len<4||len>20){
        nickNameId.className="error_prompt";
        nickNameId.innerHTML="1、长度为4－20个字符<br/>2、一个汉字占两个字符";
        return false;
        }
    
        nickNameId.className="ok_prompt";
        nickNameId.innerHTML="昵称输入正确";
        return true;
    }    

/*当鼠标放在关联手机号文本框时，提示文本及样式*/    
function telFocus(){
    var telId=$("telId");
    telId.className="import_prompt";
    telId.innerHTML="1、手机号码以13，15，18开头<br/>2、手机号码由11位数字组成";
    }
    
/*当鼠标离开关联手机号文本框时，提示文本及样式*/    
function telBlur(){
    var tel=$("tel");
    var telId=$("telId");
    var reg=/^(13|15|18|17|14)\d{9}$/;
    if(tel.value==""){
        telId.className="error_prompt";
        telId.innerHTML="关联手机号码不能为空，请输入关联手机号码";
        return false;
        }
    if(reg.test(tel.value)==false){
        telId.className="error_prompt";
        telId.innerHTML="关联手机号码输入不正确，请重新输入";
        return false;
        }
        telId.className="ok_prompt";
        telId.innerHTML="关联手机号码输入正确";
        return true;
    }    


/*当鼠标放在保密邮箱文本框时，提示文本及样式*/    
function emailFocus(){
    var emailId=$("emailId");
    emailId.className="import_prompt";
    emailId.innerHTML="请输入您常用的电子邮箱";
    }
    
/*当鼠标离开保密邮箱文本框时，提示文本及样式*/    
function emailBlur(){
    var email=$("email");
    var emailId=$("emailId");
    var reg=/^\w+@\w+(\.[a-zA-Z]{2,3}){1,2}$/;
    if(email.value==""){
        emailId.className="error_prompt";
        emailId.innerHTML="保密邮箱不能为空，请输入保密邮箱";
        return false;
        }
    if(reg.test(email.value)==false){
        emailId.className="error_prompt";
        emailId.innerHTML="保密邮箱格式不正确，请重新输入";
        return false;
        }
        emailId.className="ok_prompt";
        emailId.innerHTML="保密邮箱输入正确";
        return true;
    }    

/*表单提交时验证表单内容输入的有效性*/
function checkForm(){
      var flagUserName=userNameBlur();
      var flagPwd=pwdBlur();
      var flagRepwd=repwdBlur();
      var flagNickName=nickNameBlur();
      var flagTel=telBlur();
      var flagEmail=emailBlur();
      
      userNameBlur();
      pwdBlur();
      repwdBlur();
      nickNameBlur();
      telBlur();
      emailBlur();
      
      if(flagUserName==true &&flagPwd==true &&flagRepwd==true &&flagNickName==true&&flagTel==true&flagEmail==true){
          return true;
          }
        else{
            return false;
            }
    
    }
   
</script>
<title>硅谷商城</title>
</head>
<body>
<div id="main">
 <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="bg bg_top_left"></td>
    <td class="bg_top"></td>
    <td class="bg bg_top_right"></td>
  </tr>
  <tr>
    <td class="bg_left"></td>
    <td class="content">
      <form action="regist.do" method="post" enctype="multipart/form-data" name="myform" onsubmit="return checkForm()">
        <dl>
          <dt>用 户 头 像：</dt>
			<dd><input id="file" style="display:none" type="file" name="files" onchange="img_change()"/>
			<img id="img" src="images/upload_hover.png" style="cursor: pointer;" width="70px;" onclick="img_click()"/></dd>
        </dl>
        <dl>
          <dt>用户名：</dt>
          <dd><input type="text" id="userName" name="yh_mch" class="inputs" onfocus="userNameFocus()" onblur="userNameBlur()" /> </dd>
          <div id="userNameId"></div>
        </dl>
         <dl>
          <dt>登录密码：</dt>
          <dd><input type="password" id="pwd" name="yh_mm" class="inputs"  onfocus="pwdFocus()" onblur="pwdBlur()"/></dd>
          <div id="pwdId"></div>
        </dl>
         <dl>
          <dt>重复登录密码：</dt>
          <dd><input type="password" id="repwd" class="inputs"  onblur="repwdBlur()"/></dd>
          <div id="repwdId"></div>
        </dl>
        <dl>
          <dt>性别：</dt>
          <dd><input name="sex" type="radio" value="" checked="checked"/>男 <input name="sex" type="radio" value="" />女 </dd>
        </dl>
        <dl>
          <dt>昵称：</dt>
          <dd><input type="text" id="nickName" name="yh_nch" class="inputs"  onfocus="nickNameFocus()" onblur="nickNameBlur()"/></dd>
          <div id="nickNameId"></div>
        </dl>
        <dl>
          <dt>关联手机号：</dt>
          <dd><input type="text" id="tel" name="yh_shjh" class="inputs"  onfocus="telFocus()" onblur="telBlur()" /></dd>
          <div id="telId"></div>
        </dl>
        <dl>
          <dt>保密邮箱：</dt>
          <dd><input type="text" id="email" name="yh_yx" class="inputs" onfocus="emailFocus()" onblur="emailBlur()" /></dd>
          <div id="emailId"></div>
        </dl>
        <dl>
          <dt></dt>
          <dd><input type="checkbox" id="ck" />
    		 <span>我已阅读并同意<a href="##">《京东用户注册协议》</a></span>
    	 </dd>
        </dl>
        <dl>
          <dt></dt>
          <dd><input name=" " type="image" src="validate/img.png" onclick="regist()"/></dd>
        </dl>
      </form>
    </td>
    <td class="bg_right"></td>
  </tr>
  <tr>
     <td class="bg bg_end_left"></td>
    <td class="bg_end"></td>
    <td class="bg bg_end_right"></td>
  </tr>
</table>
</div>

<script type="text/javascript">
//表单提交
function regist(){
	$(":input[name='myform']").submit();
}
//点击图片，弹出文本框，请选择图片
function img_click() {
	$("#file").click();
	alert(1); 
	
}

function img_change() {
	
	//获取图片缓存
	var file = $("#file")[0].files[0];
	
	//转化为url对象
	var url = window.URL.createObjectURL(file);
	//alert(url);
	//显示图片
	$("#img").attr("src", url);
	
}
</script>
</body>

</html>