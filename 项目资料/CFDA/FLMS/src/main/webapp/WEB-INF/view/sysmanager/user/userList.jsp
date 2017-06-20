<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<%@ include file="/WEB-INF/view/common/CommonJS.jsp" %>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">用户查询</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="userApp" ng-controller="userCtrl">
					<div class="validation-form">
						<form class="form-inline">
							<div class="form-group">
								<label for="exampleInputName2">用户名</label> <input type="text"
									class="form-control" ng-model="filter.userName" id="userName" name="userName" placeholder="用户名">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">工号</label> <input type="text"
									class="form-control" ng-model="filter.idnum"  id="idnum" name="idnum" placeholder="工号">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">邮箱</label> <input type="text"
									class="form-control" ng-model="filter.email"  id="email" name="email" placeholder="邮箱">
							</div>
							<defined:isright rightCode="user_search">
								<button type="button" class="btn btn-primary" ng-click="queryUser();">查询</button>
							</defined:isright>
							<defined:isright rightCode="user_add">
								<ul class="bt-list pull-right">
									<button type="button" class="btn btn-primary" onclick="ToADD()" >添加</button>
								</ul>
							</defined:isright>
						</form>
						<div class="w3l-table-info">
							<table id="table">
								<thead>
									<tr>
										<th>序号</th>
										<th>用户名</th>
										<th>中文名</th>
										<th>工号</th>
										<th>邮箱</th>
										<th style="text-align: center">角色</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="ul in userList">
										<td>{{$index + 1}}</td>
										<td>{{ul.userName}}</td>
										<td>{{ul.cname}}</td>
										<td>{{ul.idnum}}</td>
										<td>{{ul.email}}</td>
										<td style="text-align: center">
											<span ng-repeat="role in ul.role">
												{{role.roleName}}
											</span>
										</td>
										<td>{{ul.status == null ? null : ul.status == 'Y' ? '启用' : '禁用'}}</td>
										<td>
											<defined:isright rightCode="user_update">
												<a href="<%=path%>/user/toUpdateUser.do?userId={{ul.userId}}"><i class="fa fa-pencil" title="编辑"></i></a>
												&nbsp;&nbsp;&nbsp;
											</defined:isright>
											<defined:isright rightCode="user_assignRole">
												<a href="#" ng-click="AssignRole(ul)"><i class="fa fa-cogs" title="分配角色"></i></a>
													<!--  data-toggle="modal" data-target="#userModal" -->
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
<!-- 显示用户列表 -->
 <script type="text/ng-template" id="myModalContent.html"  /> 
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true" ng-click="cancel()">&times;</button>
				<h4 class="modal-title" id="userModalLabel">用户列表</h4>
			</div>
				<table id="table">
					<thead>
						<tr>
							<th></th>
							<th>权限名称</th>
							<th>描述</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="role in roleList">
							<td><input type="checkbox" name="text" value={{role.roleId}} id={{role.roleId}} ng-checked={{role.selected}}></td>
							<td>{{role.roleName}}</td>
							<td>{{role.description}}</td>
						</tr>
					</tbody>
				</table>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="cancel()">关闭</button>
				<button type="button" class="btn btn-primary" ng-click="userAssign();">确定</button>
			</div>
</script>
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
	angular.module('userApp', ['ui.bootstrap']).controller('userCtrl', function($scope,$http,$modal) {
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
			url = '<%=path%>/user/userList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.userList = data.list;
				})
				.error(function(data){
					alert("失败");
				});
		};
		$scope.deleteUser = function(ul){
			if(confirm('确定删除    '+ul.userName+'    吗 ？')){
				$http.get('<%=path%>/user/deleteUser.do?userId='+ul.userId)
					.success(function(data){
						if(data){
							alert("删除成功");
							$scope.pageChanged();
						}else{
							alert("此用户名下有资产，所以不能删除");
						}
					})
					.error(function(response){
						alert("失败");
					});
			}
		};
		$scope.AssignRole = function(ul){
			$modal.open({
	 			templateUrl: 'myModalContent.html',
	 			controller: ['$scope','$modalInstance',function(scopeControll,$modalInstance){
	 			
					$http.get('<%=path%>/user/AssignRole.do?userId='+ul.userId)
	 				 	.success(function(data){
		 				 	scopeControll.roleList = data.list;
						})
						.error(function(response){
							alert("失败");
						});
						
					scopeControll.cancel = function () {
						$modalInstance.dismiss('cancel');
					};
					
					scopeControll.userAssign = function () {
						var userId = ul.userId;
						obj = document.getElementsByName("text");
					    roleIds = [];
					    for(k in obj){
					        if(obj[k].checked){
								roleIds.push(obj[k].value);
					        }
					    }
						$http.get('<%=path%>/user/userAssignRole.do?roleIds='+roleIds+'&userId=' + userId)
							.success(function(data){
								$modalInstance.dismiss('cancel');
							})
							.error(function(data){
								alert("失败");
							}); 
					};
				}]
			 })
		};	
	});
		
		function ToADD(){
	 		location.href="<%=path%>/user/toADD.do";
	 	}
</script>