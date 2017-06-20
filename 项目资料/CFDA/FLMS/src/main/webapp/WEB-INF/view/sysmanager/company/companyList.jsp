<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container" ng-app="companyManageApp" ng-controller="companyManageCtrl">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a href="javascript:void(0);">公司管理</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<form class="form-inline">
						<div class="form-group">
							<label for="number">项目名称</label> <input
								type="text" class="form-control" id="projectName" name="projectName" ng-model="filter.projectName" placeholder="项目名称">
						</div>
						<div class="form-group">
							<label for="type">公司名称</label> <input
								type="text" class="form-control" id="companyName" name="companyName" ng-model="filter.companyName" placeholder="公司名称">
						</div>
						<div class="form-group">
							<label for="serialNumber">公司类型</label>
							<select class="form-control" id="companyType" name="companyType" ng-model="filter.companyType" placeholder="公司类型">
								<option value="">请选择</option>
								<c:forEach items="${sessionScope.GLOBAL_CompanyType}" var="companyType">
									<option value="${companyType.number}">${companyType.value}</option>
								</c:forEach>
							</select>	
						</div>
				         <button type="button" class="btn btn-primary" ng-click="queryCompany();">查询</button>
				         <ul class="bt-list pull-right">
							<button type="button" class="btn btn-primary" onclick="ToADD()" >添加</button>
						 </ul>
					</form>
					<div class="w3l-table-info">
							<table id="table" >
								<thead>
									<tr>
										<th>序号</th>
										<th>编号</th>
										<th>项目名称</th>
										<th>公司名称</th>
										<th>公司类型</th>
										<th>审批状态</th>
										<th>数据状态</th>
										<th>数据来源</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="company in companyList">
											<td>{{$index + 1}}</td>
											<td>{{company.companyNo}}</td>
											<td>{{company.projectName}}</td>
											<td>{{company.companyName}}</td>
											<td>{{company.companyType}}</td>
											<td>{{company.auditStatus}}</td>
											<td>{{company.dataStatus}}</td>
											<td>{{company.dataSources}}</td>
											<td>
											<a href="<%=path%>/company/toCompanyDetail.do?id={{company.companyId}}" class="glyphicon glyphicon-list-alt onhand link" title="查看"></a>
												&nbsp;
											<a href="<%=path%>/company/toUpdateCompany.do?id={{company.companyId}}" class="glyphicon glyphicon-pencil onhand link" title="编辑"></a>
												&nbsp;
											<a href="#" ng-click="deleteCompany(company.companyId)" class="glyphicon glyphicon-trash onhand link" title="删除"></a>
												&nbsp;
											<a href="#" ng-click="updateCompanyStatus(company.companyId)" class="glyphicon glyphicon-cog" title="更改状态"></a>
										</td>
									</tr>
								</tbody>
							</table>
							<div pagination total-items="filter.total" items-per-page="filter.count" max-size="filter.maxsize" ng-if="companyList" ng-change="pageChanged()" 
								ng-model="filter.page" previous-text='上一页' next-text= '下一页' first-text="首页"
                                last-text="尾页" class="pull-right" boundary-links="true" force-ellipses="true" rotate="false">
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
				                <table class="table" id="table" style="white-space:nowrap;" >
					                 <thead>
					                     <tr>
					                         <th>序号</th>
					                         <th>状态</th>
					                     </tr>
					                 </thead>
					                 <tbody id="list">
					                 	 <tr>
					                 	 	<td><input type="radio" name="Status" value="1"></td>
					                 	 	<td>启用</td>
					                 	 </tr>
					                 	 <tr>
					                 	 	<td><input type="radio" name="Status" value="2"></td>
					                 	 	<td>禁用</td>
					                 	 </tr>
					                 </tbody>
					             </table>
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
<script type="text/javascript">

	angular.module('companyManageApp', ['ui.bootstrap']).controller('companyManageCtrl', function($scope,$http,$modal) {
		
		$scope.filter = {
			page : 1,
			count : 9,
			maxsize : 10
		};
		
		$scope.companyTypeList = eval('${companyTypeList}');// 公司类型
		$scope.auditTypeList = eval('${auditTypeList}');// 审批状态
		$scope.dataStatusList = eval('${dataStatusList}');// 数据状态
		$scope.dataSourceList = eval('${dataSourceList}');// 数据来源
		
		$scope.queryCompany = function(){
			$("#dataBody").show();
			$scope.filter.page = 1;
			$scope.pageChanged();
		};
		// 查询
		$scope.pageChanged = function(){
			$http({method:'post',
				   url:'<%=path%>/company/companyList.do',  
				   data:$scope.filter,
				   headers:{'Content-Type': 'application/x-www-form-urlencoded'},  
				   transformRequest: function(obj) {
						return $.param(obj);
				   }  
				}).success(function(data){
					
					$scope.filter.total = data.total;
					
					angular.forEach(data.list,function(company){
						angular.forEach($scope.companyTypeList,function(type){
							if(company.companyType == type.number){
								company.companyType = type.value;
							}
						})
						angular.forEach($scope.auditTypeList,function(type){
							if(company.auditStatus == type.number){
								company.auditStatus = type.value;
							}
						})
						angular.forEach($scope.dataStatusList,function(type){
							if(company.dataStatus == type.number){
								company.dataStatus = type.value;
							}
						})
						angular.forEach($scope.dataSourceList,function(type){
							if(company.dataSources == type.number){
								company.dataSources = type.value;
							}
						})
					});
					$scope.companyList = data.list;
				})  
		};
		//删除公司
		$scope.deleteCompany = function(id){
			if(confirm("确定删除该公司信息吗？")){
				$http.get('<%=path%>/company/deleteCompany.do?timestamp=' +  Math.random(),{params:{id : id}})
				.success(function(data){
					if(data){
						alert('删除成功');
						$scope.queryCompany();
					}
				}).error(function(data){
				});
			}
		}
		
		
		$scope.selectedCompanyId = '';
		$scope.updateCompanyStatus = function(companyId){
			$scope.selectedCompanyId = companyId;
			$("#updateStatusModal").modal("show");
		};
		$scope.updateDataStatus = function(){
			
			var dataStatus = $('input:radio[name="Status"]:checked').val();
			
			var Status = "";
			if(dataStatus == "1"){
				Status = "启用";
			}else{
				Status = "禁用";
			}
			if(confirm("确定"+ Status +"么？")){
				$http.get("<%=path%>/company/updateCompanyStatus.do?dataStatus="+dataStatus+"&companyId="+$scope.selectedCompanyId)
					.success(function(data){
						if(data.success){
							alert("成功");
							$("#updateStatusModal").modal("hide");
							$scope.pageChanged();
						}else{
							alert(data.message);
						}
					})
					.error(function(response){
						alert("更新失败");
					});
			}
		};
	});
	
	// 添加公司
	function ToADD(){
 		window.location.href="<%=path%>/company/toAddCompany.do";
 	}
</script>
