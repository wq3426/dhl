<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>产品添加</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form method="post" id="commentForm">
						<h3>基本信息</h3>
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>项目名称:</label> 
									<select name="projectId" id="projectId" class="form-control">
					                    <option value="">请选择</option>
										<c:forEach items="${projectList}" var="projectList">  
					                        <option value="${projectList.projectId}">${projectList.projectName}</option>  
					                    </c:forEach>
									</select>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>物料号:</label> 
									<input type="text" placeholder="物料号" class="form-control" name="material" id="material" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">物料描述:</label> 
									<input type="text" placeholder="物料描述" class="form-control" name="materialDescription" id="materialDescription" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">物料类型:</label> 
									<select name="materalType" id="materalType" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y">是</option>
				    					<option value="N">否</option>
				    				</select>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">JAN Code:</label> 
									<input type="text" placeholder="JAN Code" class="form-control" name="janCode" id="janCode" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">产品名称:</label> 
									<input type="text" placeholder="产品名称" class="form-control" name="productName" id="productName" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>规格/型号:</label> 
									<input type="text" placeholder="规格/型号" class="form-control" name="model" id="model" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">植入/非植入:</label> 
									<input type="text" placeholder="植入/非植入" class="form-control" name="inplantFlag" id="inplantFlag" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">运输条件:</label> 
									<input type="text" placeholder="运输条件" class="form-control" name="shippingCondition" id="shippingCondition" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">毛重:</label> 
									<input type="text" placeholder="毛重" class="form-control" name="weight" id="weight" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">尺寸:</label> 
									<input type="text" placeholder="尺寸" class="form-control" name="size" id="size" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">TraceType:</label> 
									<input type="text" placeholder="TraceType" class="form-control" name="traceType" id="traceType" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">CFDA状态代码:</label> 
									<input type="text" placeholder="CFDA状态代码" class="form-control" name="cfdaStatecode" id="cfdaStatecode" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">是否效期管理物料:</label> 
									<input type="text" placeholder="是否效期管理物料" class="form-control" name="validManage" id="validManage" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">近效期管理天数:</label> 
									<input type="text" placeholder="近效期管理天数" class="form-control" name="validManageDays" id="validManageDays" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>是否需要冷链存储:</label> 
									<select name="isitColdChainStorage" id="isitColdChainStorage" class="form-control">
										<option value="">请选择</option>
				    					<option value="Y">是</option>
				    					<option value="N">否</option>
				    				</select>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">储存条件:</label> 
									<input type="text" placeholder="储存条件" class="form-control" name="storageCondition" id="storageCondition" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">产品有效期（年）:</label> 
									<input type="text" placeholder="产品有效期（年）" class="form-control" name="validityPeriod" id="validityPeriod" >
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">生产企业许可证号:</label> 
									<input type="text" placeholder="生产企业许可证号" class="form-control" name="productionCompanyLicense" id="productionCompanyLicense" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">单位:</label> 
									<input type="text" placeholder="单位" class="form-control" name="uom" id="uom" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">产品许可证持证商:</label> 
									<input type="text" placeholder="产品许可证持证商" class="form-control" name="productionLicenseHolder" id="productionLicenseHolder" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label"><span style="color: red;">*</span>生产企业名称:</label> 
									<input type="text" placeholder="生产企业名称" class="form-control" name="productionCompany" id="productionCompany" >
								</div>
							</div>
							<div class="row">
								<div class="col-md-11 form-group1">
									<label class="control-label"><span style="color: red;">*</span>产品类型:</label> 
									<input type="hidden" placeholder="产品类型" class="form-control" name="productType" id="productType" >
									<input type="text" placeholder="产品类型" class="form-control" id="product" name="product" readonly>
								</div>
								<div class="col-md-1 form-group1">
									<button type="button" onclick="queryProductType();" style="color: black;margin-top: 22px;width: 80px;">选择</button>
								</div>	
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<label class="control-label">备注:</label> 
									<textarea rows="3" name="remark1" id="remark1" class="form-control"></textarea>
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
<!-- 产品类型 -->
<div class="modal fade" id="productTypeModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="productTypeModalLabel">产品类型</h4>
            </div>
            <div class="modal-body">
                <form class="form-inline" id="productType">
                	<div class="form-group">
						<label for="exampleInputName2">名称:</label> 
						<input type="text"class="form-control" id="productTypeName" name="productTypeName" placeholder="名称">
					</div>
					<div class="form-group">
						<label for="exampleInputName2">类型:</label> 
						<input type="text" class="form-control" id="productTypeLevel" name="productTypeLevel" placeholder="类型">
					</div>
					<button type="button" class="btn btn-primary" onclick="queryProductType();">查询</button>
                </form>
                <table class="table" id="table" style="white-space:nowrap;" >
	                 <thead>
	                     <tr>
	                         <th>序号</th>
	                         <th>类型</th>
	                         <th>编号</th>
	                         <th>名称</th>
	                     </tr>
	                 </thead>
	                 <tbody id="list">
	                 </tbody>
	             </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary"  onclick="addProductType()">确定</button>
            </div>
        </div>
    </div>
</div>
				
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
					projectId:"required",
					material:"required",
					model:"required",
					isitColdChainStorage:"required",
					productionCompany:"required",
					product:"required",
				},
				messages:{
					projectId:"项目名称不能为空",
					material:"物料号不能为空",
					model:"规格/型号不能为空",
					isitColdChainStorage:"请选择是否需要冷链存储",
					productionCompany:"生产企业名称不能为空",
					product:"请选择产品类型",
				}
			});
			
		});
  		function submitClick(){
  			var projectId = $("#projectId").val();
  			var material = $("#material").val();
  			//校验表单内容
			if($("#commentForm").valid()){
	  			$.ajax({
						type:"Post",
						async:false,
						url:'<%=path%>/product/toProductCheck.do',
						data: { 
								projectId:projectId,
								material:material,
							  },
						success:function(data){
							if(data != 0){
								alert("项目名称+物料号重复");
						    }else{
								addProduct();
						    }
						}
					});
			}
  		}
  		function addProduct(){
  			$.ajax({
					type:"Post",
					async:false,
					url:'<%=path%>/product/addProduct.do',
					data: $("#commentForm").serialize(),
					success:function(data){
						if(data){
							alert("添加成功");
							location.href="<%=path%>/product/toProductList.do";
					    }else{
					    	alert("添加失败");
					    }
					}
				});
  		}
  		function queryProductType(){
  			$("#list").html("");
  			$.ajax({
				type:"Post",
				async:false,
				url:'<%=path%>/product/queryProductType.do',
				data: $("#productType").serialize(),
				success:function(data){
					if(data!=null){
						for ( var i in data) {
							$("#table").append("<tr><td><input type='radio' id='productId' name='productId' value='"+data[i].productTypeId+","+data[i].productTypeLevel+","+data[i].productTypeNo+","+data[i].productTypeName+"'> </td><td>"+data[i].productTypeLevel+" </td></td> </td><td>"+data[i].productTypeNo+" </td> <td>"+data[i].productTypeName+" </td></tr>" );
						}
						$("#productTypeModal").modal("show");
					}else{
						alert("没有产品类型");
					}
				}
			});
  		}
  		function addProductType(){
  			document.getElementById("product").value="";
  			document.getElementById("productType").value="";
  			var allProduct = $('input:radio[name="productId"]:checked').val();
  			
		  	var str = allProduct.split(",");
		  	for(var i=0;i<str.length;i++){
			  	productId=str[0];
			  	productTypeLevel=str[1];
			  	productTypeNo=str[2];
			  	productTypeName=str[3];
		  	}
		  	
  			var product = productTypeLevel + "-" + productTypeNo + "-" + productTypeName;
  			
  			$("#product").val(product);
  			$("#productType").val(productId);
  			
  			$("#productTypeModal").modal("hide");
  		}
  		function goBack(){
  			location.href="<%=path%>/product/toProductList.do";
  		}
  </script>