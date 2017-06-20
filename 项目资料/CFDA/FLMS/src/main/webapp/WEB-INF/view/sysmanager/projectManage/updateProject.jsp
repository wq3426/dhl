<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>项目编辑</a></li>
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
									<input type="hidden" placeholder="序号" class="form-control" name="projectId" id="projectId" value="${project.projectId }">
									<input type="text" placeholder="编号" class="form-control" name="projectNo" id="projectNo" value="${project.projectNo }">
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>项目名称:</label> 
									<input type="text" placeholder="项目名称" class="form-control" name="projectName" id="projectName" value="${project.projectName }">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>合同开始时间:</label> 
									<input type="text" placeholder="合同开始时间" class="form-control datepicker" name="contractStartDate" id="contractStartDate" value="<cfmt:formatDate value="${project.contractStartDate }" pattern="yyyy-MM-dd"/>">
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>合同结束时间:</label> 
									<input type="text" placeholder="合同结束时间" class="form-control datepicker" name="contractEndDate" id="contractEndDate" value="<cfmt:formatDate value="${project.contractEndDate }" pattern="yyyy-MM-dd"/>">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>委托期限(月):</label> 
									<input type="text" placeholder="合同开始时间" class="form-control" name="contractPeriod" id="contractPeriod" value="${project.contractPeriod }">
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否委托加贴中文标签:</label> 
									<select name="isChineseLabels" id="isChineseLabels" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y" <c:if test="${project.isChineseLabels=='Y'}">selected</c:if> >是</option>
				    					<option value="N" <c:if test="${project.isChineseLabels=='N'}">selected</c:if> >否</option>
				    				</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否效验合同有效期:</label> 
									<select name="isValidateContract" id="isValidateContract" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y" <c:if test="${project.isValidateContract=='Y'}">selected</c:if> >是</option>
				    					<option value="N" <c:if test="${project.isValidateContract=='N'}">selected</c:if> >否</option>
				    				</select>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否效验经营方式:</label> 
									<select name="isValidateOperationtype" id="isValidateOperationtype" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y" <c:if test="${project.isValidateOperationtype=='Y'}">selected</c:if> >是</option>
				    					<option value="N" <c:if test="${project.isValidateOperationtype=='N'}">selected</c:if> >否</option>
				    				</select>
								</div>
							</div>
							<div class="row" style="display: none;" id="start">
								<div class="col-md-12 form-group1" id="startReason">
									<label class="control-label">修改合同开始时间的原因:</label>
									<textarea rows="2" name="startUpdateReason" id="startUpdateReason" class="form-control"></textarea>
								</div>
							</div>
							<div class="row" style="display: none;" id="end">
								<div class="col-md-12 form-group1" id="endReason">
									<label class="control-label">修改合同结束时间的原因:</label> 
									<textarea rows="2" name="endUpdateReason" id="endUpdateReason" class="form-control"></textarea>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">委托客户邮箱:</label> 
									<input type="text" placeholder="委托客户邮箱" class="form-control" name="customerMail" id="customerMail" value="${project.customerMail }">
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label"><span style="color: red;">*</span>委托业务范围:</label> 
									<textarea rows="3" name="businessScope" id="businessScope" class="form-control">${project.businessScope }</textarea>
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">备注:</label> 
									<textarea rows="3" name="remark" id="remark" class="form-control">${project.remark }</textarea>
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
					startUpdateReason:"required",
					endUpdateReason:"required",
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
					startUpdateReason:"请输入修改合同开始时间的原因",
					endUpdateReason:"请输入修改合同结束时间的原因",
					contractPeriod:"只能输入数字",
					isChineseLabels:"请选择是否委托加贴中文标签",
					businessScope:"委托业务范围不能为空",
				}
			});
			
		});
  		function submitClick(){
  			//合同开始时间
  			var contractStartDate1 = "<cfmt:formatDate value="${project.contractStartDate }" pattern="yyyy-MM-dd"/>";
  			//修改后的开始时间
  			var contractStartDate2 =document.getElementById("contractStartDate").value;
  			//得到修改开始时间的原因
  			var startUpdateReason = document.getElementById("startUpdateReason").value;
  			if(startUpdateReason == ""){
  				if(contractStartDate1 != contractStartDate2){
					$("#start").show();
	  			}
  			}
  			//合同开始时间
  			var contractEndDate1 = "<cfmt:formatDate value="${project.contractEndDate }" pattern="yyyy-MM-dd"/>";
  			//修改后的开始时间
  			var contractEndDate2 =document.getElementById("contractEndDate").value;
  			//得到修改结束时间的原因
  			var endUpdateReason =document.getElementById("endUpdateReason").value;
  			if(endUpdateReason == ""){
	  			if(contractEndDate1 != contractEndDate2){
					$("#end").show();
	  			}
  			}
  		
  		
  			var projectId = $("#projectId").val();
  			var projectNo = $("#projectNo").val();
  			var projectName = $("#projectName").val();
  			var contractEndDate = $("#contractEndDate").val();
  			
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
  			//校验表单内容
			if($("#commentForm").valid()){
	  			$.ajax({
						type:"Post",
						async:false,
						url:'<%=path%>/projectManage/toProjectCheck.do',
						data: { 
								projectId:projectId,
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
								updateProject();
						    }
						}
					});
			}
  		}
  		function updateProject(){
  			$.ajax({
					type:"Post",
					async:false,
					url:'<%=path%>/projectManage/updateProject.do',
					data: $("#commentForm").serialize(),
					success:function(data){
						if(data){
							alert("修改成功");
							location.href="<%=path%>/projectManage/toProjectList.do";
					    }else{
					    	alert("修改失败");
					    }
					}
				});
  		}
  		function goBack(){
  			location.href="<%=path%>/projectManage/toProjectList.do";
  		}
  </script>