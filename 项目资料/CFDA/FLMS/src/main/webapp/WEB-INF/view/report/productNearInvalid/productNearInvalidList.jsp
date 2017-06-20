<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">产品证照近效期报表</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="ProductNearInvalidApp" ng-controller="ProductNearInvalidCtrl">
					<div class="validation-form">
						<form class="form-inline" >
							<div class="form-group">
								<label for="exampleInputName2">项目名称:</label> 
								<input type="text"  ng-model="filter.projectName" id="projectName" name="projectName" class="form-control" placeholder="项目名称">
							</div>
							   <div class="form-group">
								<label for="exampleInputName2">物料号:</label> 
								<input type="text" ng-model="filter.material" id="material" name="material" class="form-control" placeholder="物料号">
							</div>
							 <div class="form-group">
								<label for="exampleInputName2">证照名称:</label> 
								<input type="text" ng-model="filter.licenseName" id="licenseName" name="licenseName" class="form-control" placeholder="证照名称">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">证照编号:</label> 
								<input type="text" ng-model="filter.productLicenceNo"  id="productLicenceNo" name="productLicenceNo" class="form-control" placeholder="证照编号">
							</div>
							<div class="form-group">
								<label for="exampleInputName2">有效期至:</label> 						
								<input type="text" placeholder="有效期开始时间" class="form-control datepicker" ng-model="filter.expiredDate_Start" name="expiredDate_Start" id="expiredDate_Start" value="">
					            <span >to</span>					            
					            <input type="text" placeholder="有效期结束时间" class="form-control datepicker" ng-model="filter.expiredDate__End"  name="expiredDate__End" id="expiredDate__End" value="">
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
					                    <th>物料号</th>
					                    <th>产品名称</th>
					                    <th>证照名称</th>
					                    <th>证照编号</th> 
					                    <th>有效期至</th> 
					                    <th>失效期</th> 
					                    <th>有效天数</th> 
					                    <th>控制期限</th> 
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;">
									<tr ng-repeat="ul in productList">
										<td>{{$index + 1}}</td>			
										<td>{{ul.ProjectNo}}</td>										
										<td>{{ul.ProjectName}}</td>
										<td>{{ul.Material}}</td>
										<td>{{ul.ProductName}}</td>
										<td>{{ul.LicenseName}}</td>
										<td>{{ul.ProductLicenceNo}}</td>
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
	angular.module('ProductNearInvalidApp', ['ui.bootstrap']).controller('ProductNearInvalidCtrl', function($scope,$http,$modal) {
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
			url = '<%=path%>/productNearInvalid/productNearInvalidList.do',
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
					$scope.productList = data.list;
				})
				.error(function(data){
					alert("操作失败");
				});
		};
		
	});
		
		
</script>