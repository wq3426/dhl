<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>产品类型添加</a>
				</li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form id="commentForm">
						<div class="vali-form ">
						     <div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>类型:</label> <select
										class="form-control" placeholder="类型" id="productTypeLevel"
										name="productTypeLevel">	
										<option value="">请选择</option>										
										<option value="I">I</option>
										<option value="II">II</option>
										<option value="III">III</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3  form-group1">
									<label class="control-label"><span style="color: red;">*</span>编号:</label>
									<input type="text" placeholder="编号" name="productTypeNo"
										id="productTypeNo" class="form-control" required>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6  col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>名称:</label> <input type="text"
										name="productTypeName" id="productTypeName"
										class="form-control" placeholder="名称">
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label"><span style="color: red;">*</span>状态:</label> <select
										name="dataStatus" id="dataStatus" class="form-control">
										<option value="">请选择</option>										
										<option value="Y">启用</option>
										<option value="N">禁用</option>
									</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 col-sm-offset-3 form-group1">
									<label class="control-label">备注:</label>
									<textarea name="remark" id="remark" class="form-control"></textarea>
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
					productTypeNo:"required",	
					ProductTypeLevel:"required",
					ProductTypeName:"required",
					DataStatus:"required",
									
				},
				messages:{
					productTypeNo:"编号不能为空",
					ProductTypeLevel:"类型不能为空",
					ProductTypeName:"名称不能为空",
					DataStatus:"数据状态不能为空",
				}
			});
			
		});
		
		
  		function submitClick(){
  			//校验表单内容
			if($("#commentForm").valid()){
			 	$.ajax({
					url:"<%=path%>/productType/insertProductType.do",
					type:"post",
					data:$("#commentForm").serialize(),
					success:function(result){
						if(result==2){
						alert("类型+编号必须唯一");
						}else if(result==0){
							alert("添加成功");
							$("#commentForm")[0].reset();
							location.href="<%=path%>/productType/toProductTypeList.do";
						}else{
						  alert("添加失败");
						}
					}
				})
			}
  		}
  		function goBack(){
  			location.href="<%=path%>/productType/toProductTypeList.do";	
	}
</script>