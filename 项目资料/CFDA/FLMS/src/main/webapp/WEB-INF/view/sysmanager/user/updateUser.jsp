<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>用户编辑</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form action="<%=path%>/user/updateUser.do" method="post" id="commentForm">
						<div class="vali-form">
							<div class="row">
								<div class="col-md-6 col-sm-offset-3  form-group1">
									<label class="control-label"><span style="color: red;">*</span>用户名:</label> 
									<input type="hidden" id="userId" name="userId" value="${user.userId }">
	    							<input type="text" id ="userName" name="userName" value="${user.userName }" class="form-control" placeholder="用户名" required>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6  col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>密码:</label> 
									<input type="password" name="password" id="password" value="${user.password }" class="form-control" placeholder="密码" required>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>中文名:</label> 
									<input type="text" name="cname" id="cname" value="${user.cname }" class="form-control" placeholder="中文名">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>英文名:</label> 
									<input type="text" name="ename" id="ename" value="${user.ename }" class="form-control" placeholder="英文名">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>工号:</label> 
									<input type="text" name="idnum" id="idnum" value="${user.idnum }" class="form-control" placeholder="工号">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>邮箱:</label> 
									<input type="text" name="email" id="email" value="${user.email }" class="form-control" placeholder="邮箱">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label">电话:</label> 
									<input type="text" name="telephone" id="telephone" value="${user.telephone }" class="form-control" placeholder="电话">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>状态:</label> 
									<select name="status" id="status" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y" <c:if test="${user.status=='Y'}">selected</c:if> >启用</option>
				    					<option value="N" <c:if test="${user.status=='N'}">selected</c:if> >禁用</option>
				    				</select>
								</div>
							</div>
						</div>
					</form>
				</div>
				
				<div class="col-md-6 col-sm-offset-3 form-group" style ="text-align: center;">
					<button type="button" onclick="submitClick()" class="btn btn-primary">保存</button>
					<button type="reset" class="btn btn-primary" onclick="goBack()">取消</button>
				</div>
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script type="text/javascript">
		$(document).ready(function(){
			$("#commentForm").validate({    
				rules:{
					//customerName:"required",
					userName:{
								required :true,
								minlength: 2,
							},
					password:{
								required :true,
								minlength: 5,
							},
					cname:"required",
					ename:"required",
					idnum:"required",
					status:"required",
					email	:{
						        required:true,
						        email	:true,
						     },
				},
				messages:{
					userName:{
						        required : "用户名不能为空",
						        minlength: "用户名必需由两个字母组成",
						     },
					
					password:{
						        required : "密码不能为空",
						        minlength: "密码长度不能小于 5 个字母",
						     },
					cname:"中文名不能为空",
					ename:"英文名不能为空",
					idnum:"工号不能为空",
					status:"状态不能为空",
					email	:{
						        required : "请输入邮箱",
						        email	 : "请输入正确的邮箱",
						     },
				}
			});
			
		});


  		function submitClick(){
  			var userId = $("#userId").val();
  			var userName = $("#userName").val();
  			var password = $("#password").val();
  			var idnum = $("#idnum").val();
  			var ename = $("#ename").val();
  			var email = $("#email").val();
  			var telephone = $("#telephone").val();
  			$.ajax({
					type:"Post",
					async:false,
					url:'<%=path%>/user/addUserClick.do',
					data: { 
								userId:userId,
								userName:userName,
								idnum:idnum
							},
					success:function(data){
						//校验表单内容
						if($("#commentForm").valid()){
							if(data == 1){
								alert("用户名必须唯一");
						    }else if(data == 2){
						    	alert("工号必须唯一");
						    }else{
							 	$("#commentForm").submit();
							}
					    }
					}
				});
  		}
  		function goBack(){
  			location.href="<%=path%>/user/toUserList.do";
  		}
  </script>