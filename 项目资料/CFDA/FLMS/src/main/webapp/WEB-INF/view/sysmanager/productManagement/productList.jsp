<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">产品管理</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="productApp" ng-controller="productCtrl">
					<div class="validation-form">
						<form class="form-inline">
							<div class="form-group">
								<label for="exampleInputName2">物料号:</label> 
								<input type="text" class="form-control" ng-model="filter.material" id="material" name="material" placeholder="物料号">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">项目名称:</label> 
								<input type="text" class="form-control" ng-model="filter.projectName" id="projectName" name="projectName" placeholder="项目名称">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">规格/型号:</label> 
								<input type="text" class="form-control" ng-model="filter.model" id="model" name="model" placeholder="规格/型号">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">证照编号:</label> 
								<input type="text" class="form-control" placeholder="证照编号">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">生产企业名称:</label> 
								<input type="text" class="form-control" ng-model="filter.productionCompany" id="productionCompany" name="productionCompany" placeholder="生产企业名称">
							</div>
							
							<button type="button" class="btn btn-primary" ng-click="queryProduct();">查询</button>
							<ul class="bt-list pull-right">
								<button type="button" class="btn btn-primary" onclick="ToADD()" >添加</button>
							</ul>
						</form>
						<div class="w3l-table-info">
							<table id="table">
								<thead>
									<tr>
										<th>序号</th>
										<th>物料号</th>
										<th>产品名称</th>
										<th>项目名称</th>
										<th>生产企业名称</th>
										<th>储存条件</th>
										<th>产品有效期（年）</th>
										<th>审批状态</th>
										<th>数据状态</th>
										<th>数据来源</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="pList in productList">
										<td>{{$index + 1}}</td>
										<td>{{pList.material}}</td>
										<td>{{pList.productName}}</td>
										<td>{{pList.projectName}}</td>
										<td>{{pList.productionCompany}}</td>
										<td>{{pList.storageCondition}}</td>
										<td>{{pList.validityPeriod}}</td>
										<td>{{pList.auditStatus}}</td>
										<td>{{pList.dataStatus == null ? null : pList.dataStatus == 'Y' ? '启用' : '禁用'}}</td>
										<td>{{pList.dataSources ==  null ? null : pList.dataSources == 'DataSource1' ? '手动创建' : 'EDI'}}</td>
										<td>
											<a href="<%=path%>/product/toDetailLog.do?productId={{pList.productId}}" class="glyphicon glyphicon-list-alt onhand link" title="查看"></a>
											&nbsp;
											<a href="<%=path%>/product/toUpdateProduct.do?productId={{pList.productId}}" class="glyphicon glyphicon-pencil onhand link" title="编辑"></a>
											&nbsp;
											<a href="#" ng-click="deleteProduct(pList)" class="glyphicon glyphicon-trash onhand link" title="删除"></a>
											&nbsp;
											<a href="#" ng-click="updateStatus(pList)" class="glyphicon glyphicon-cog" title="更改状态"></a>
										</td>
									</tr>
								</tbody>
							</table>
							 <center>
								<form>
									<table id="tb" style="display: none; margin-top: 30px;">
									</table>
								</form>
							</center>
							<div pagination total-items="filter.total" page="filter.count" first-text="首页"
                                last-text="尾页" ng-model="filter.page" items-per-page="filter.count" previous-text="上一页"
                                next-text='下一页' max-size="filter.maxsize" ng-change="pageChanged()"  class="pull-right" 
                                boundary-links="true" force-ellipses="true" rotate="false">
                            </div>
						</div>
						
					</div>
		
					
<!-- 更改数据状态 -->
<div class="modal fade" id="updateStatusModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="updateStatusModalLabel">更改数据状态</h4>
            </div>
            <div class="modal-body">
                <!-- <table class="table" id="table" style="white-space:nowrap;" >
	                 <thead>
	                     <tr>
	                         <th>序号</th>
	                         <th>状态</th>
	                     </tr>
	                 </thead>
	                 <tbody id="list">
	                 	 <tr>
	                 	 	<td><input type="hidden" id="productId" ng-value="productId"><input type="radio" name="Status" id="Status" value="Y"></td>
	                 	 	<td>启用</td>
	                 	 </tr>
	                 	 <tr>
	                 	 	<td><input type="radio" name="Status" id="Status" value="N"></td>
	                 	 	<td>禁用</td>
	                 	 </tr>
	                 </tbody>
	             </table> -->
	             <label class="control-label">数据状态:</label> 
	             <input type="hidden" id="productId" ng-value="productId">
	             <select name="dataStatus" id="dataStatus" ng-model="dataStatus" class="form-control">
                    <option value="Y">启用</option>  
                    <option value="N">禁用</option>  
				 </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary"  ng-click="updateDataStatus()">确定</button>
            </div>
        </div>
    </div>
</div>

			</div>
				
				
				<!--//grid-->
				<!-- tables -->
				
				<div class="clearfix"></div>

			</div>
		</div>
		<!--//content-inner-->
		<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
	</div>
	
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script>
	angular.module('productApp', ['ui.bootstrap']).controller('productCtrl', function($scope,$http,$modal) {
		$scope.filter = {
			page : 1,
			count : 9,
			maxsize:10
		};
		$scope.queryProduct = function(){
			$("#dataBody").show();
			$scope.filter.page = 1;
			$scope.pageChanged();
		};
		$scope.pageChanged = function(){
			var data = $scope.filter;
			url = '<%=path%>/product/productList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.productList = data.list;
				})
				.error(function(data){
					alert("失败");
				});
		};
		$scope.deleteProduct = function(pList){
			if(confirm('确定删除    '+pList.productName+'    吗 ？')){
				$http.get('<%=path%>/product/deleteProduct.do?productId='+pList.productId)
					.success(function(data){
						if(data){
							alert("删除成功");
							$scope.pageChanged();
						}else{
							alert("失败");
						}
					})
					.error(function(response){
						alert("失败");
					});
			}
		};
		$scope.updateStatus = function(pList){
			$("#updateStatusModal").modal("show");
			var productId = pList.productId;
			var dataStatus = pList.dataStatus;
			$scope.productId = productId;
			$scope.dataStatus = dataStatus;
		};
		$scope.updateDataStatus = function(){
			
			var productId = document.getElementById("productId").value;
			var dataStatus = document.getElementById("dataStatus").value;
			
			var Status = "";
			if(dataStatus == "Y"){
				Status = "启用";
			}else{
				Status = "禁用";
			}
			if(confirm("确定"+ Status +"么？")){
				$http.get('<%=path%>/product/updateStatus.do?dataStatus='+dataStatus+"&productId="+productId)
					.success(function(data){
						if(data){
							alert("成功");
							$("#updateStatusModal").modal("hide");
							$scope.pageChanged();
						}else{
							alert("失败");
						}
					})
					.error(function(response){
						alert("失败");
					});
			}
		};
});
		function ToADD(){
	 		location.href="<%=path%>/product/toAddProduct.do";
	 	}
</script>