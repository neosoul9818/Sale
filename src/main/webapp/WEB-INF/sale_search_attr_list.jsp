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
	//shx_mch代表shx_zh 和shxm_zh
	function search_up(shxm_id,shxzh_id,shx_mch){
		//保存参数
		$("#search_param").append('<span id="search_param_id_'+shxm_id+'"><input type="text" name="array_av" value=\'{"shxm_id":'+shxm_id+',"shxzh_id":'+shxzh_id+'}\'/><a href="javascript:search_down('+shxm_id+');">'+shx_mch+'</a></span>'); 
		 /* $("#search_param").append("<div id='search_param_id_"+shxm_id+"'><input type='text' name='array_av' value='{\"shxm_id\":"+shxm_id+",\"shxzh_id\":"+shxzh_id+"}'>"+"<a href='javascript:search_down("+shxm_id+");'>"+shx_mch+"</a>"+"</div>"); */ 
		$("#attr_show_hide_"+shxm_id).hide();
		 //异步请求
		search_sku();
	}
	function search_sku(){
		//取得页面上的二级分类id,且默认是价格的升序
		var param_string="class_2_id="+${class_2_id}+"&order="+$("#search_order").val();
		//ajax将表单数据转换成json的对象即js对象，传入ajax中
		var array = new Array();
		$(":input[name='array_av']").each(function(i, json){
			json_string= json.value;
			var jsonObj = $.parseJSON(json_string);
			param_string = param_string 
			+"&list_av["+i+"].shxm_id="+jsonObj.shxm_id+"&list_av["+i+"].shxzh_id="+jsonObj.shxzh_id;
		});
		
		alert(param_string);
		
		 $.ajax({
			type:"POST",
			url:"search_sku_by_attr.do",
			data:param_string,
			success:function(data){
				$("#search_sku_list").html(data);
			}
		}); 
		/* $.get("search_sku_by_attr.do",param_string,function(data){
			$("#search_sku_list").html(data);
		}); */
	}
	function search_down(shxm_id){
		$("#search_param_id_"+shxm_id).remove();
		$("#attr_show_hide_"+shxm_id).show();
		//不管是是点上去还是点下来，都需要调用ajax函数去后台查询商品数据
		 //异步请求
		search_sku();
	}
	
	function search_order(new_order){
		
		var  old_order= $("#search_order").val();
		//，默认是升序，即价格，销量，价格时间，当重复点击时，变成降序。或者同一个选项重复点击时，会因为一个有desc而order不等
		if(old_order == new_order){
			$("#search_order").val(new_order+' desc ');
		}else{
			$("#search_order").val(new_order);
		}
		
		search_sku();
	}
</script>
<title>硅谷商城</title>
</head>
<body>
	检索属性列表${class_2_id}${class_2_name}<br>
	<div id="search_param"></div>
	<c:forEach items="${attr_list }" var="attr">
	 	<div  id="attr_show_hide_${attr.id }">
			 ${attr.shxm_mch}:
			<c:forEach items="${attr.list_value }" var="val">
				<a href="javascript:search_up(${attr.id },${val.id },'${val.shxzh }${val.shxzh_mch }');">${val.shxzh }${val.shxzh_mch}</a>
			</c:forEach>
		</div>
			<br>
	</c:forEach>
	<input type="text" id="search_order" value=" order by jg "/>
	&nbsp;&nbsp;<a href="javascript:search_order(' order by jg ');">价格</a>&nbsp;
	<a href="javascript:search_order(' order by sku_xl ');">销量</a>&nbsp;
	<a href="javascript:search_order(' order by t_mall_sku.chjshj ');">上架时间</a>&nbsp;评论数
</body>
</html>