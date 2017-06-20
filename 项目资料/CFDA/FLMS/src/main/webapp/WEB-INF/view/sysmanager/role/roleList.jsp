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
					<li class="breadcrumb-item"><a href="javascript:void(0);">角色管理界面</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="roleApp" ng-controller="roleCtrl">
					<div class="validation-form">
						<form class="form-inline" method="post">
							<div class="form-group">
								<label for="exampleInputName2">角色名：</label> 
								<input type="text" class="form-control" ng-model="filter.roleName" value="${role.roleName }" id="roleName" name="roleName" placeholder="角色名">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">状态：</label> 
								<select name="roleStatus" id="roleStatus" ng-model="filter.roleStatus" class="form-control" placeholder="状态">
				 					<option value="">全部</option>
				  					<option value="Y">启用</option>
				  					<option value="N">禁用</option>
				  				</select> 
							</div>
							<defined:isright rightCode="role_search">					
								<button type="button" ng-click="queryRole();" class="btn btn-primary">查询</button>
							</defined:isright>
							<defined:isright rightCode="role_add">
								<ul class="bt-list pull-right">
									<button type="button" onclick="ToADD()" class="btn btn-primary">添加</button>
								</ul>
							</defined:isright>	
						</form>
						<div class="w3l-table-info">
							<table id="table">
								<thead>
									<tr>
										<th>序号</th>
						    			<th>角色名</th>
						    			<th>状态</th>
						    			<th>描述</th>
						    			<th>操作</th>
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
										<tr ng-repeat="role in roleList">
											<td>{{ $index + 1 }}</td>
							    			<td>{{role.roleName }}</td>
							    			<td>{{role.roleStatus == 'Y' ? '启用' : '禁用'}}</td>
							    			<td>{{role.description }}</td>
											<td>
												<defined:isright rightCode="role_update">
													<a href="<%=path%>/role/toUpdateRole.do?roleId={{role.roleId}}" ng-if="role.isAllowEdit=='Y'"><i class="fa fa-pencil" title="编辑角色"></i></a>
													&nbsp;&nbsp;&nbsp;
												</defined:isright>	
												<defined:isright rightCode="role_del">	
													<a ng-click="deleteRole(role)" href="#" ng-if="role.isAllowEdit=='Y'"><i class="fa fa-trash-o" title="删除"></i></a>
													&nbsp;&nbsp;&nbsp;
												</defined:isright>
												<defined:isright rightCode="role_assignRight">
													<a  href="javascript:void(0);" ng-click="showzTree(role)" ><i class="fa fa-cogs" title="分配权限"></i></a>
													&nbsp;&nbsp;&nbsp;
												</defined:isright>	
												
											</td>
										</tr>
								</tbody>
							</table>
							 <center>
								<form>
									<table id="tb" style="display: none; margin-top: 50px;">
									</table>
								</form>
							</center>
						</div>
						<div pagination total-items="filter.total" page="filter.count" first-text="首页"
                                last-text="尾页" ng-model="filter.page" items-per-page="filter.count" previous-text="上一页"
                                next-text='下一页' max-size="filter.maxsize" ng-change="pageChanged()"  class="pull-right" 
                                boundary-links="true" force-ellipses="true" rotate="false">
                            </div>
					</div>
					<!-- 显示用户列表 -->
<div  id="userModal" class="modal fade" role="dialog" aria-labelledby="userModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:270px;">
		<div class="modal-content"> 
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				<h4 class="modal-title" id="userModalLabel">权限分配</h4>
			</div>
			
			<div id="rightTree" class="ztree" style="padding: 20px;">
			</div>
			
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" ng-click="selectUser();">确定</button>
			</div>
		</div>
	</div>
</div>
				</div>
				<!--//grid-->
				<!-- tables -->


				<div class="clearfix"></div>

<!-- script-for sticky-nav -->
<script type="text/javascript">
angular.module('roleApp', ['ui.bootstrap']).controller('roleCtrl', function($scope,$http,$modal) {
		$scope.filter = {
			page : 1,
			count : 9,
			maxsize:10
		};
		$scope.queryRole = function(){
			$("#dataBody").show();
			$scope.filter.page = 1;
			$scope.pageChanged();
		};
		$scope.pageChanged = function(){
			var data = $scope.filter;
			url = '<%=path%>/role/roleList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.roleList = data.list;
				})
				.error(function(data){
					alert("失败");
				});
		};
		$scope.deleteRole = function(role){
			if(confirm('确定删除    '+role.roleName+'    吗 ？')){
				$http.get('<%=path%>/role/deleteRole.do?roleId='+role.roleId)
					.success(function(data){
	 				 	if(data){
							alert("删除成功");
							$scope.pageChanged();
						}else{
							alert("删除失败");
						}
					})
					.error(function(response){
						alert("失败");
					});
				}
		};
		$scope.showzTree = function(role){
			$http.get('<%=path%>/role/toAssignRight.do?roleId='+role.roleId)
				.success(function(data){
					 /* zTree树 */
					var leftRightTree = data;
					$(function(){
						function zTreeOnClick(event, treeId, treeNode) {
						    if(treeNode.level==0){
								return;
						    }
						};
						var setting = {
							callback : {
								onClick : zTreeOnClick
							},
							check: {  
			                	enable: true //显示复选框  
			            	},
			            	data: {
						       simpleData: {
						         enable: true
						       }
						     }
						};
						roleTree = $.fn.zTree.init($("#rightTree"), setting, leftRightTree);
					});
					$('#userModal').modal('show')
				})
				.error(function(data){
					alert("失败");
				});
				$scope.selectUser = function(){
					var zTree = $.fn.zTree.getZTreeObj("rightTree");  
					var nodes=zTree.getCheckedNodes(true); 
					var result='';   
		          	for (var i = 0; i < nodes.length; i++) {  
	                  	if(result != null && result != ""){
	                  		result += ',';
	                  	}
	                     result += nodes[i].id;  
		            }
		            if(result==""){
		            	alert("请选择分配的权限");
		            	return;
		            }
					$.ajax({
				        type:"post",
				        url:"<%=path%>/role/roleAssignRight.do",
				        data:
				        	{
				        		rightIds:result,
				        		roleId:role.roleId
				        	},
				        dataType:"json",
				        success:function(msg){
					         if(msg==true){
					           alert("权限分配成功");
					           location.href="<%=path%>/role/toRoleList.do";
					         }else{
					         	alert("失败");
					         }
				        }
				  	 });
				}
				
		}
	});

	 	function ToADD(){
	 		location.href="<%=path%>/role/toADD.do";
	 	}
	 </script>
				<!-- /script-for sticky-nav -->


			</div>
		</div>
		<!--//content-inner-->
		<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
	</div>
	
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>