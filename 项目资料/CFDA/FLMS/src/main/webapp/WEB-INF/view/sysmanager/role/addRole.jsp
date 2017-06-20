<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>角色添加</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form action="<%=path%>/role/addRole.do" method="post" id="commentForm">
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-6 col-sm-offset-3  form-group1">
									<label class="control-label"><span style="color: red;">*</span>角色名:</label> 
									<input type="hidden" id="roleId" name="roleId">
									<input type="text" placeholder="角色名" class="form-control" name="roleName" id="roleName" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6  col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>状态:</label> 
									<select name="roleStatus" id="roleStatus" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y">启用</option>
				    					<option value="N">禁用</option>
				    				</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label">描述:</label> 
									<textarea name="description" id="description" class="form-control"></textarea>
								</div>
							</div>
						</div>
					</form>
				</div>
				
				<div class="col-md-6 col-sm-offset-3 form-group" style ="text-align: center;">
					<button type="button" onclick="submitClick()" class="btn btn-primary">保存</button>
					<button type="reset" onclick="goBack()" class="btn btn-default">取消</button>

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
					roleName:"required",
					roleStatus:"required",
				},
				messages:{
					roleName:"角色名不能为空",
					roleStatus:"请选择状态",
				}
			});
			
		});
  		function submitClick(){
  			var roleId = $("#roleId").val();
  			var roleName = $("#roleName").val();
  			var roleStatus = $("#roleStatus").val();
  			$.ajax({
					type:"Post",
					async:false,
					url:'<%=path%>/role/addRoleClick.do',
					data: { 
								roleId:roleId,
								roleName:roleName
							},
					success:function(data){
						if(data != true){
							alert("角色名必须唯一");
					    }else{
					  		//校验表单内容
							if($("#commentForm").valid()){
							 	$("#commentForm").submit();
							}
					    }
					}
				});
  		}
  		function goBack(){
  			location.href="<%=path%>/role/toRoleList.do";
  		}
  </script>