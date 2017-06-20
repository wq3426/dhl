<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>项目添加</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form method="post" id="commentForm">
						<h3>基本信息</h3>
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-6  form-group1">
									<label class="control-label"><span style="color: red;">*</span>编号:</label> 
									<input type="text" placeholder="编号" class="form-control" name="projectNo" id="projectNo" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>项目名称:</label> 
									<input type="text" placeholder="项目名称" class="form-control" name="projectName" id="projectName" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>合同开始时间:</label> 
									<input type="text" placeholder="合同开始时间" class="form-control datepicker" name="contractStartDate" id="contractStartDate" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>合同结束时间:</label> 
									<input type="text" placeholder="合同结束时间" class="form-control datepicker" name="contractEndDate" id="contractEndDate" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否效验合同有效期:</label> 
									<select name="isValidateContract" id="isValidateContract" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y">是</option>
				    					<option value="N">否</option>
				    				</select>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否效验经营方式:</label> 
									<select name="isValidateOperationtype" id="isValidateOperationtype" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y">是</option>
				    					<option value="N">否</option>
				    				</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>委托期限(月):</label> 
									<input type="text" placeholder="合同开始时间" class="form-control" name="contractPeriod" id="contractPeriod" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否委托加贴中文标签:</label> 
									<select name="isChineseLabels" id="isChineseLabels" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y">是</option>
				    					<option value="N">否</option>
				    				</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">委托客户邮箱:</label> 
									<input type="email" placeholder="委托客户邮箱" class="form-control" name="customerMail" id="customerMail" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label"><span style="color: red;">*</span>委托业务范围:</label> 
									<textarea rows="3" name="businessScope" id="businessScope" class="form-control"></textarea>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">备注:</label> 
									<textarea rows="3" name="remark" id="remark" class="form-control"></textarea>
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
					projectNo:"required",
					projectName:"required",
					contractStartDate:"required",
					contractEndDate:"required",
					isValidateContract:"required",
					isValidateOperationtype:"required",
					contractPeriod:"digits",
					isChineseLabels:"required",
					businessScope:"required",
				},
				messages:{
					projectNo:"编号不能为空",
					projectName:"项目名称不能为空",
					contractStartDate:"合同开始时间不能为空",
					contractEndDate:"合同结束时间不能为空",
					isValidateContract:"请选择是否效验合同有效期",
					isValidateOperationtype:"请选择是否效验经营方式",
					contractPeriod:"只能输入数字",
					isChineseLabels:"请选择是否委托加贴中文标签",
					businessScope:"委托业务范围不能为空",
				}
			});
			
		});
  		function submitClick(){
  			var projectNo = $("#projectNo").val();
  			var projectName = $("#projectName").val();
  			var contractEndDate = $("#contractEndDate").val();
  			if(contractEndDate != ""){
  				var seperator1 = "-";
	  			//创建系统当前时间
	  			var date = new Date();
	  			var year = date.getFullYear();
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
	    		var currentDate = year + seperator1 + month + seperator1 + strDate;
	  			//验证合同结束时间
	  			if(contractEndDate <= currentDate){
	  				alert("合同结束时间必须晚于当前时间。");
	  				return;
	  			}
  			}
  			//校验表单内容
			if($("#commentForm").valid()){
	  			$.ajax({
						type:"Post",
						async:false,
						url:'<%=path%>/projectManage/toProjectCheck.do',
						data: { 
								projectNo:projectNo,
								projectName:projectName,
							  },
						success:function(data){
							if(data == 1){
								alert("编号和项目名称重复");
						    }else if(data == 2){
								alert("编号重复");
						    }else if(data == 3){
								alert("项目名称重复");
						    }else{
								addProject();
						    }
						}
					});
			}
  		}
  		function addProject(){
  			$.ajax({
					type:"Post",
					url:'<%=path%>/projectManage/addProject.do',
					data: $("#commentForm").serialize(),
					success:function(data){
						if(data){
							alert("添加成功");
							location.href="<%=path%>/projectManage/toProjectList.do";
					    }else{
					    	alert("添加失败");
					    }
					}
				});
  		}
  		function goBack(){
  			location.href="<%=path%>/projectManage/toProjectList.do";
  		}
  </script>