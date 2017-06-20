<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">公司证照近效期报表</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="CompanyNearInvalidApp" ng-controller="CompanyNearInvalidCtrl">
					<div class="validation-form">
						<form class="form-inline" >
							<div class="form-group">
								<label for="exampleInputName2">项目名称:</label> 
								<input type="text"  id="projectName" ng-model="filter.projectName" name="projectName" class="form-control" placeholder="项目名称">
							</div>
							   <div class="form-group">
								<label for="exampleInputName2">公司名称:</label> 
								<input type="text" id="companyName" name="companyName" ng-model="filter.companyName" class="form-control" placeholder="公司名称">
							</div>
							 <div class="form-group">
								<label for="exampleInputName2">证照名称:</label> 
								<input type="text"  id="licenseName" name="licenseName" ng-model="filter.licenseName" class="form-control" placeholder="证照名称">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">证照编号:</label> 
								<input type="text"  id="licenceNo" name="licenceNo" ng-model="filter.licenceNo" class="form-control" placeholder="证照编号">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">有效期至:</label> 						
								<input type="text" placeholder="有效期开始时间" class="form-control datepicker" ng-model="filter.expiredDate_Start" name="expiredDate_Start" id="expiredDate_Start" >
					            <span >to</span>					            
					            <input type="text" placeholder="有效期结束时间" class="form-control datepicker" ng-model="filter.expiredDate__End" name="expiredDate__End" id="expiredDate__End" >
							</div>
							<defined:isright rightCode="user_search">
								<button type="button" class="btn btn-primary" ng-click="queryUser();" id="sub">查询</button>
							</defined:isright>
						</form>
						<div class="w3l-table-info">
							<table id="table">
								<thead>
									<tr>
										<th>序号</th>
					                    <th>项目编号</th>
					                    <th>项目名称</th>
					                    <th>公司编号</th>
					                    <th>公司名称</th>
					                    <th>公司类型</th>
					                    <th>证照名称</th>
					                    <th>证照编号</th> 
					                    <th>有效期至</th> 
					                    <th>失效期</th> 
					                    <th>有效天数</th> 
					                    <th>控制期限</th> 
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="ul in cleList">
										<td>{{$index + 1}}</td>	
										<td>{{ul.projectNo}}</td>										
										<td>{{ul.ProjectName}}</td>
										<td>{{ul.CompanyNo}}</td>
										<td>{{ul.CompanyName}}</td>
										<td>{{ul.CompanyType}}</td>
										<td>{{ul.LicenseName}}</td>
										<td>{{ul.LicenceNo}}</td>
										<td>{{ul.ExpiredDate}}</td>
										<td>{{ul.Expirationdate}}</td>
										<td>{{ul.Effectivedays}}</td>
										<td>{{ul.ControlPeriod}}</td>										
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
	angular.module('CompanyNearInvalidApp', ['ui.bootstrap']).controller('CompanyNearInvalidCtrl', function($scope,$http,$modal) {
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
			
			url = '<%=path%>/companyNearInvalid/companyNearInvalidList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			};
			 $scope.filter.expiredDate_Start=$("#expiredDate_Start").val();
			 $scope.filter.expiredDate__End=$("#expiredDate__End").val();
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.cleList = data.list;
				})
				.error(function(data){
					alert("操作失败");
				});
		};
		
	});
		
</script>