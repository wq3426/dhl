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
				<li class="breadcrumb-item"><a>用户添加</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system" ng-app="userApp" ng-controller="userCtrl">
				<div class="validation-form">
					<form action="<%=path%>/user/queryUser.do" method="post" id="commentForm" class="form-inline">
						<div class="form-group">
							<label for="exampleInputName2">用户名</label> <input type="text"
								class="form-control" ng-model="filter.userName" id="userName" name="userName" placeholder="用户名">
						</div>
						<div class="form-group">
							<label for="exampleInputName2">工号</label> <input type="text"
								class="form-control" ng-model="filter.idnum" id="idnum" name="idnum" placeholder="工号">
						</div>
						<div class="form-group">
							<label for="exampleInputEmail2">邮箱</label> <input type="text"
								class="form-control" ng-model="filter.email" id="email" name="email" placeholder="邮箱">
						</div>
						<button type="button" class="btn btn-primary" ng-click="queryUser();">查询</button>
						<button type="reset" class="btn btn-primary" onclick="goBack()">取消</button>
						<button style="float: right" type="button" ng-click="submitClick()" class="btn btn-primary">保存</button>
					</form>
				<div class="w3l-table-info">
						<table id="table">
							<thead>
								<tr>
									<th><input type="checkbox" id="checkAll" onclick="selectAll()"></th>
					    			<th>用户名</th>
									<th>中文名</th>
									<th>英文名</th>
									<th>工号</th>
									<th>邮箱</th>
								</tr>
							</thead>
							<tbody id="dataBody" style="display: none;">
								<tr ng-repeat="InUser in InUser">
									<td>
										<input type="checkbox" name="checkNode" value={{InUser.userId}} id={{InUser.userId}}>
									</td>
									<td>{{InUser.userName }}</td>
									<td>{{InUser.cname }}</td>
									<td>{{InUser.ename }}</td>
									<td>{{InUser.idnum }}</td>
									<td>{{InUser.email }}</td>
								</tr>
							</tbody>
						</table>
						<div pagination total-items="filter.total" page="filter.count" first-text="首页"
                                last-text="尾页" ng-model="filter.page" items-per-page="filter.count" previous-text="上一页"
                                next-text='下一页' max-size="filter.maxsize" ng-change="pageChanged()"  class="pull-right" 
                                boundary-links="true" force-ellipses="true" rotate="false">
                        </div>
					</div>
				</div>
				
				
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script type="text/javascript">
		angular.module('userApp', ['ui.bootstrap']).controller('userCtrl', function($scope,$http) {
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
			url = '<%=path%>/user/queryUser.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.InUser = data.list;
				})
				.error(function(data){
					alert("失败");
				});
		};
		$scope.submitClick = function () {
			obj = document.getElementsByName("checkNode");
		    userIds = [];
		    for(k in obj){
		        if(obj[k].checked)
		            userIds.push(obj[k].value);
		    }
		    if(userIds == ""){
		    	alert("请选择添加的用户");
		    	return ;
		    }
			$http.get('<%=path%>/user/checkUser.do?userIds='+userIds)
				.success(function(data){
					if(data.success) {
			  	 		location.href="<%=path%>/user/addUser.do?userIds="+userIds;
			  	 	}else{
			  	 		alert(data.msg+"用户已经存在");
			  	 	}
				})
				.error(function(data){
					alert(data)
					alert("失败");
				}); 
		};
		
	});

  		function goBack(){
  			location.href="<%=path%>/user/toUserList.do";
  		}
  		<%-- function submitClick(){
		    obj = document.getElementsByName("checkNode");
		    CheckedUser = [];
		    for(k in obj){
		        if(obj[k].checked){
		    	    CheckedUser.push(obj[k].value);
		        }
		    }
		    if(CheckedUser == null || CheckedUser == ""){
		    	alert("请选择添加的用户");
		    	return false;
		    }
		    alert(CheckedUser);
		    $.ajax({
			  	 type:"post",
			  	 url:"<%=path%>/user/checkUser.do",
			  	 data:"CheckedUser="+CheckedUser,
			  	 dataType:"text",
			  	 success:function(userName){
			  	 	if(userName != null && userName != ""){
			  	 		alert(userName+"用户已经存在");
			  	 	}else{
			  	 		location.href="<%=path%>/user/addUser.do?CheckedUser="+CheckedUser;
			  	 	}
			  	 }
			});  	 
		    
		} --%>
  </script>