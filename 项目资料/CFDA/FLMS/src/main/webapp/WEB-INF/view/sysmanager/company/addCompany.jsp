<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container" ng-app="addCompanyApp" ng-controller="addCompanyCtrl">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>公司添加</a></li>
			</ol>
			<form id="addCompany" method="post">
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<div class="vali-form">
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>编号</label> 
								<input type="text" id="companyNo" name="companyNo" class="form-control"  placeholder="编号">
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>公司名称</label> 
								<input type="text" id="companyName" name="companyName" class="form-control"  placeholder="公司名称"/>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>项目名称</label>
								<select id="projectId" name="projectId" class="form-control">
									<option value="">请选择</option>
	  									<c:forEach items="${projectList}" var="project">
	  										<option value="${project.projectId}">${project.projectName}</option>
	  									</c:forEach>	
								</select>
							</div>
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>是否允许经营医疗器械</label>
								<select id="isMedicalinstrumentsRight" name="isMedicalinstrumentsRight" class="form-control">
									<option value="">请选择</option>
	  								<option value="Y">是</option>
	  								<option value="N">否</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>公司类型</label>
								<select id="companyType" name="companyType" class="form-control">
									<option value="">请选择</option>
									<c:forEach items="${sessionScope.GLOBAL_CompanyType}" var="companyType">
										<option value="${companyType.number}">${companyType.value}</option>
									</c:forEach>	
								</select>
							</div>
							<div class="col-md-6 form-group2">
								<label class="control-label"><span id="coldRequireId" style="color: red;display: none;">*</span>是否提供冷链服务</label>
								<select id="isColdChainService" name="isColdChainService" class="form-control">
									<option value="">请选择</option>
	  								<option value="Y">是</option>
	  								<option value="N">否</option>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>经营方式</label>
								<select id="operationType" name="operationType" class="form-control">
									<option value="">请选择</option>
									<c:forEach items="${sessionScope.GLOBAL_OperationType}" var="operationType">
	  										<option value="${operationType.number}">${operationType.value}</option>
	  								</c:forEach>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>证照校验
									<button type="button" ng-click="selectLicense()">选择</button>
								</label>
								<div class="input-group">
							    	<select multiple="multiple" name="licenseIds" id="licenseIds" class="form-control"></select>
							    </div>
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label">备注</label>
								<textarea rows="4" cols="5" id="remark1" name="remark1" class="form-control"></textarea>
							</div>
						</div>
					</div>	
				</div>
				<div class="col-md-6 col-sm-offset-3 form-group" style ="text-align: center;">
					<button type="button" class="btn btn-primary" onclick="saveCompany();">保存</button>
					<button type="button" class="btn btn-default" onclick="returnMain();">取消</button>
				</div>
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
			</form>
		</div>
	</div>
	<!-- 显示证照列表 -->
	<script type="text/ng-template" id="licenseModalContent.html"/>
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true" ng-click="cancel()">&times;</button>
			<h4 class="modal-title" id="licenseModalLabel">证照校验</h4>
			<form class="form-inline" style="margin-top: 10px;">
				<div class="form-group">
					<label for="number">证照类型</label> 
					<input type="text" class="form-control" ng-model="filter.licenseType" placeholder="证照类型">
				</div>
				<div class="btn-group">
					<button type="button" class="btn btn-primary" ng-click="queryLicense();">查询</button>
				</div>
			</form>
		</div>
		<div class="modal-body">
			<table id="table">
				<thead>
					<tr>
						<th>序号</th>
						<th>证照类型</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="license in licenseList">
						<td><input type="checkbox" name="licenseId" ng-checked="license.checked" value="{{license.licenseId}}&{{license.licenseType}}"/></td>
						<td>{{license.licenseType}}</td>
					</tr>
				</tbody>
			</table>
			<div pagination total-items="filter.total" items-per-page="filter.count" max-size="filter.maxsize" ng-if="licenseList" ng-change="pageChanged()" 
				ng-model="filter.page" previous-text='上一页' next-text= '下一页' first-text="首页"
            	last-text="尾页" class="pull-right" boundary-links="true" force-ellipses="true" rotate="false">
			</div>
		</div>
		<div class="modal-footer" style="margin-top: 50px;">
			<button type="button" class="btn btn-primary" ng-click="cancel();">关闭</button>
			<button type="button" class="btn btn-primary" ng-click="selectedLicense();">确定</button>
		</div>	
	</script>
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script type="text/javascript">

	angular.module('addCompanyApp', ['ui.bootstrap']).controller('addCompanyCtrl', function($scope,$http,$modal) {
		$scope.selectLicense = function(){
			$modal.open({
	 			templateUrl: 'licenseModalContent.html',
	 			controller: ['$scope','$modalInstance',function(scopeControll,$modalInstance){
	 			
					scopeControll.cancel = function () {
						$modalInstance.dismiss('cancel');
					};
					scopeControll.filter = {
						page : 1,
						count : 9,
						maxsize : 10
					};
					scopeControll.queryLicense = function(){
						scopeControll.filter.page = 1;
						scopeControll.pageChanged();
					};
					scopeControll.pageChanged = function(){
						var data = scopeControll.filter;
						url = '<%=path%>/licenseType/licenseTypeList.do',
						postCfg = {
						    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
						    transformRequest: function (data) {
						        return $.param(data);
						    }
						};
						$http.post(url,data,postCfg)
							.success(function(data){
								scopeControll.filter.total = data.total;
								scopeControll.licenseList = data.list;
								
								var selectedId = '';
								$("#licenseIds option").each(function(index,item){
									selectedId += $(item).val();
								});
								angular.forEach(scopeControll.licenseList,function(license){
									if(selectedId.indexOf(license.licenseId) >= 0){
										license.checked = true;
									}
			 				  	});
							})
							.error(function(data){
								alert("失败");
							});
					};
					scopeControll.queryLicense();
					// 选中
					scopeControll.selectedLicense = function(){
						
						var selectedLicense = [];
						
						$('#table').find('[name=licenseId]').each(function(index,item){
							if($(this).prop("checked")){
								selectedLicense.push($(item).val());
							}
						});
						
						if(selectedLicense.length > 0){
							$('#licenseIds').empty();
							$.each(selectedLicense,function(index,item){
								var value = item.split("&");
								$('#licenseIds').append("<option value='"+value[0]+"' selected>" + value[1] + "</option>");
							});
							scopeControll.cancel();
						}else{
							alert('请选择证照');
						}
					}
				}]
			 })
		};	
	});
	$(function(){
		$("#addCompany").validate({
			rules:{
				companyNo:"required",
				companyName : "required",
				projectId : "required",
				companyType : "required",
				isMedicalinstrumentsRight : "required",
				//isColdChainService : "required",
                operationType : "required",
                licenseIds : "required"
			},
			messages:{
				companyNo:"编号不能为空",
				companyName : "公司名称不能为空",
				projectId : "项目名称不能为空",
				isMedicalinstrumentsRight : "是否允许经营医疗器械不能为空",
				//isColdChainService : "是否提供冷链服务不能为空",
				operationType : "经营方式不能为空",
				licenseIds : "证照校验不能为空"
			}
		});
		$('#companyType').change(function(){
			var v = $(this).val();
			//第三方仓储物流服务商
			if('4' == v){
				$('#coldRequireId').show();
			}else{
				$('#coldRequireId').hide();
			}
		});
	});
	

	function returnMain(){
		window.location.href="<%=path%>/company/toCompanyList.do";
	}
	
	// 保存公司
	function saveCompany(){
		if($("#addCompany").valid()){
			if('4' == $('#companyType') && !$('#isColdChainService').val()){
				alert('公司类型为第三方仓储物流服务商，是否提供冷脸服务不能为空!');
				return;
			}
			checkCompany();
		}
	}
	
	// 校验公司
	function checkCompany(){
		$.ajax({
			type:"get",
			async:false,
			url:'<%=path%>/company/toCompanyCheck.do',
			data: {
				projectId : $('#projectId').val(),
				companyName : $('#companyName').val(),
				companyType : $('#companyType').val()
			},
			success:function(data){
				if(data.success){
			    	addCompany();
			    }else{
					alert(data.message);
			    }
			}
		});
	}
	
	function addCompany(){
		$.ajax({
			type:"post",
			async:false,
			url:'<%=path%>/company/addCompany.do',
			data: $("#addCompany").serialize(),
			success:function(data){
				if('true' == data){
					alert("添加成功");
					location.href="<%=path%>/company/toCompanyList.do";
			    }else{
			    	alert("添加失败");
			    }
			}
		});
	}
</script>