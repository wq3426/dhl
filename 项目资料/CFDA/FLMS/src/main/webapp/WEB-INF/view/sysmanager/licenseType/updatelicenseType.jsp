<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>证照类型编辑</a>
				</li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form id="commentForm">
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-6 col-sm-offset-3  form-group1">
									<label class="control-label"><span style="color: red;">*</span>证照类型:</label>
									<input type="hidden" id="licenseId" name="licenseId"
										value="${lse.licenseId }"> <input type="text"
										placeholder="编号" name="licenseType" id="licenseType"
										value="${lse.licenseType }" class="form-control"
										required>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>状态:</label>
									<select name="dataStatus" id="dataStatus" class="form-control">
										<option value="">请选择</option>
										<option value="Y"
											<c:if test="${lse.dataStatus=='Y'}">selected</c:if>>启用</option>
										<option value="N"
											<c:if test="${lse.dataStatus=='N'}">selected</c:if>>禁用</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label">备注:</label>
									<textarea name="remark" id="remark" class="form-control">${lse.remark}</textarea>
								</div>
							</div>

						</div>
					</form>
				</div>

				<div class="col-md-6 col-sm-offset-3 form-group"
					style="text-align: center;">
					<button type="button" onclick="submitClick()"
						class="btn btn-primary">保存</button>
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
					licenseType:"required",	
					dataStatus:"required",
					
									
				},
				messages:{
					licenseType:"证照类型不能为空",
					dataStatus:"状态不能为空",
					
				}
			});
			
		});
		

		
  		function submitClick(){
  			//校验表单内容
			if($("#commentForm").valid()){
			 	 $.ajax({
					url:"<%=path%>/licenseType/updatelicenseType.do",
					type:"post",
					data:$("#commentForm").serialize(),
					success:function(result){
						if(result==2){
						alert("证照类型重复");
						}else if(result==0){
							alert("修改成功");
							$("#commentForm")[0].reset();
							location.href="<%=path%>/licenseType/tolicenseTypeList.do";
						}else{
						alert("修改失败");
						}
					}
				})
			}
  		}
  		function goBack(){
  			location.href="<%=path%>/licenseType/tolicenseTypeList.do";
	
	}
</script>