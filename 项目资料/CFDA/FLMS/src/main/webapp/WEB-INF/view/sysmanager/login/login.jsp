<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>FLMS</title>
	<link href="<%=path %>/resources/css/style.css" rel='stylesheet' type='text/css' />
	<link href="<%=path %>/resources/css/bootstrap.min.css" rel='stylesheet' type='text/css' />
	<script src="<%=path %>/resources/js/jquery-2.1.4.min.js"></script>
  </head>
  
  <body>
	<div class="main-wthree">
		<div class="container">
			<div class="logo-w3-agile">
				<h1>
					<img src="<%=path %>/resources/images/dhl_logo.gif">
				</h1>
			</div>
		</div>
		<div class="login_kuang">
			<div class="container">
				<div class="kuang">
					<form>
						<div class="form-group" style="overflow:hidden; height:30px;">
							<label for="userName">用户名</label> <input type="text"
								class="form-control" id="userName" name="userName" placeholder="用户名" >
						</div>
						<div class="clearfix"></div>
						<div class="form-group">
							<label for="password">密&nbsp;&nbsp;&nbsp;码</label> <input
								type="password" class="form-control" id="password" name="password"
								placeholder="密码">
						</div>
						<div class="login-w3">
							<input type="button" class="login" id="loginbnt" name="loginbnt" value="登录">
						</div>
						<div class="login-w4">
							<input type="reset" class="login" value="重置">
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>

</body>
<script type="text/javascript">
  	$(document).ready(function(){
  		//获取焦点
		$("#userName").focus();
		/*鼠标回车事件*/
		$(document).keypress(function(e){
	        if(e.keyCode==13)
	        {
	           $("#loginbnt").click();
	        }
	       });
		//导入
		$("#loginbnt").click(function(){
			  var userName=$("#userName").val();
			  var password=$("#password").val();
			  
			  if(userName==""){
		          alert("用户名不能为空！");
		      }else if(password==""){
		          alert("密码不能为空！");
		      }else{
				  $.ajax({
			          type : "POST",
			          url : "<%=path%>/login/checkingUser.do",
			          data: {
			          		userName:userName,
			          		password:password
			          },
			          success : function(data,status)
			          {
			             var str=$.trim(data);
			             if(str=="Non-existent")
			             	 alert("用户不存在！");
			             else if(str=="Forbidden")
			             	 alert("用户已经停用！");
			             else if(str=="N")
			             	  alert("登录失败！");
			             else{
			               	var _url = "<%=path%>/login/userLogin.do?userId="+str;  
			               	window.location.href = _url;
			             }
			          },
			          error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert("登录失败！");
                    }       
			      });
		    }
		});
	});
		
    </script>
</html>
