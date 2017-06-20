<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">项目管理</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="projectApp" ng-controller="projectCtrl">
					<div class="validation-form">
						<form class="form-inline">
							<div class="form-group">
								<label for="exampleInputName2">项目名称</label> <input type="text"
									class="form-control" ng-model="filter.projectName" id="projectName" name="projectName" placeholder="项目名称">
							</div>
							<button type="button" class="btn btn-primary" ng-click="queryProject();">查询</button>
							<ul class="bt-list pull-right">
								<button type="button" class="btn btn-primary" onclick="ToADD()" >添加</button>
							</ul>
						</form>
						<div class="w3l-table-info">
							<table id="table">
								<thead>
									<tr>
										<th>序号</th>
										<th>编号</th>
										<th>项目名称</th>
										<th>合同结束时间</th>
										<th>数据状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="pm in pmList">
										<td>{{$index + 1}}</td>
										<td>{{pm.projectNo}}</td>
										<td>{{pm.projectName}}</td>
										<td>
											{{pm.contractEndDate | limitTo:10 }}
										</td>
										<td>{{pm.dataStatus == null ? null : pm.dataStatus == 'Y' ? '启用' : '禁用'}}</td>
										<td>
											<a href="<%=path%>/projectManage/toDetailLog.do?projectId={{pm.projectId}}" class="glyphicon glyphicon-list-alt onhand link" title="查看"></a>
											&nbsp;
											
											<a href="<%=path%>/projectManage/toUpdateProject.do?projectId={{pm.projectId}}" class="glyphicon glyphicon-pencil onhand link" title="编辑"></a>
											&nbsp;
											<a href="#" ng-click="deleteProject(pm)" class="glyphicon glyphicon-trash onhand link" title="删除"></a>
											&nbsp;
											<a href="#" ng-click="updateStatus(pm)" class="glyphicon glyphicon-ok onhand link" title="启用" ng-if="pm.dataStatus=='N'"></a>
											<a href="#" ng-click="updateStatus(pm)" class="glyphicon glyphicon-off onhand link" title="禁用" ng-if="pm.dataStatus=='Y'"></a>
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
	angular.module('projectApp', ['ui.bootstrap']).controller('projectCtrl', function($scope,$http,$modal) {
		$scope.filter = {
			page : 1,
			count : 9,
			maxsize:10
		};
		$scope.queryProject = function(){
			$("#dataBody").show();
			$scope.filter.page = 1;
			$scope.pageChanged();
		};
		$scope.pageChanged = function(){
			var data = $scope.filter;
			url = '<%=path%>/projectManage/projectList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.pmList = data.list;
				})
				.error(function(data){
					alert("失败");
				});
		};
		$scope.deleteProject = function(pm){
			if(confirm('确定删除    '+pm.projectName+'    吗 ？')){
				$http.get('<%=path%>/projectManage/deleteProject.do?projectId='+pm.projectId)
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
		$scope.updateStatus = function(pm){
			var Status = pm.dataStatus;
			var dataStatus = "";
			if(Status == "Y"){
				dataStatus = "N";
				Status = "禁用";
			}else{
				dataStatus = "Y";
				Status = "启用";
			}
			if(confirm("确定"+ Status +"么？")){
				$http.get('<%=path%>/projectManage/updateStatus.do?dataStatus='+dataStatus+"&projectId="+pm.projectId)
					.success(function(data){
						if(data){
							alert("成功");
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
	 		location.href="<%=path%>/projectManage/toAddProject.do";
	 	}
</script>