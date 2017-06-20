<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">证照类型</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="licenseTypeApp" ng-controller="licenseTypeCtrl">
					<div class="validation-form">
						<form class="form-inline" >
							   <div class="form-group">
								<label for="exampleInputName2">证照类型：</label> 
								<input type="text" ng-model="filter.licenseType" id="licenseType" name="licenseType" class="form-control" placeholder="证照类型">
							</div>
							<defined:isright rightCode="user_search">
								<button type="button" class="btn btn-primary" ng-click="queryUser();" id="sub">查询</button>
							</defined:isright>
							<defined:isright rightCode="user_add">
								<ul class="bt-list pull-right">
									<button type="button" class="btn btn-primary" onclick="ToADD()" >添加</button>
								</ul>
							</defined:isright>
							<!--<div class="col-sm-6 text-right">
							<button type="submit" class="btn btn-primary" ng-click="queryUser();">查询</button>
							<button type="button" class="btn btn-primary" onclick="ToADD()" >添加</button>
							</div>-->
						</form>
						<div class="w3l-table-info">
							<table id="table">
								<thead>
									<tr>
										<th>序号</th>
					                    <th>证照类型</th>
					                    <th>数据状态</th>
					                    <th>操作</th>   
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="ul in leList">
										<td>{{$index + 1}}</td>			
										<td>{{ul.licenseType}}</td>
										<td>{{ul.dataStatus == null ? null : ul.dataStatus == 'Y' ? '启用' : '禁用'}}</td>										
										<td>
										    <defined:isright rightCode="user_assignRole">
												<a href="<%=path%>/licenseType/tochecklicenseType.do?licenseId={{ul.licenseId}}"><i class="fa fa-list-alt" aria-hidden="true" title="查看"></i></a>
													<!--  data-toggle="modal" data-target="#userModal" -->
													&nbsp;&nbsp;&nbsp;
											</defined:isright>
											<defined:isright rightCode="user_update">
												<a href="<%=path%>/licenseType/toupdatelicenseType.do?licenseId={{ul.licenseId}}"><i class="fa fa-pencil" title="编辑"></i></a>
												&nbsp;&nbsp;&nbsp;
											</defined:isright>
											<defined:isright rightCode="user_del">
												<a href="#" ng-click="deleteUser(ul)"><i class="fa fa-trash-o" title="删除"></i></a>
											</defined:isright>
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
				<div class="clearfix"></div>

			</div>
		</div>
		<!--//content-inner-->
		<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
	</div>
	
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script>
	angular.module('licenseTypeApp', ['ui.bootstrap']).controller('licenseTypeCtrl', function($scope,$http,$modal) {
		$scope.filter = {
			page : 1,
			count : 9,
			maxsize:10
		};
		$scope.queryUser = function(){
			$("#dataBody").show();
			$scope.filter.page = 1;
			$scope.pageChanged();
		};
		$scope.pageChanged = function(){
			var data = $scope.filter;
			url = '<%=path%>/licenseType/licenseTypeList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.leList = data.list;
				})
				.error(function(data){
					alert("操作失败");
				});
		};
		$scope.deleteUser = function(ul){
			if(confirm('确定删除    '+ul.licenseType+'    吗 ？')){
				$http.get('<%=path%>/licenseType/deletelicenseType.do?licenseId='+ul.licenseId)
					.success(function(data){
						if(data){
							alert("删除成功");
							$scope.pageChanged();
						}else{
							alert("删除失败");
						}
					})
					.error(function(response){
						alert("操作失败");
					});
			}
		};

	});
		
		function ToADD(){
	 		location.href="<%=path%>/licenseType/toAddlicenseType.do";
	 	}
</script>