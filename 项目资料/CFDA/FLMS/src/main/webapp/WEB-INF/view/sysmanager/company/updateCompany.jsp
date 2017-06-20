<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container" ng-app="updateCompanyApp" ng-controller="updateCompanyCtrl">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>公司编辑</a></li>
			</ol>
			<form id="updateCompany" method="post">
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<div class="vali-form">
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>编号</label>
								<input type="hidden" name="companyId" id="companyId" value="${company.companyId}"/>
								<input type="hidden" name="auditStatus" value="${company.auditStatus}"/>
								<input type="hidden" name="dataSources" value="${company.dataSources}"/>
								<input type="hidden" name="dataStatus" value="${company.dataStatus}"/>
								<input type="text" id="companyNo" name="companyNo" class="form-control"  placeholder="编号" value="${company.companyNo}">
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>公司名称</label> 
								<input type="text" id="companyName" name="companyName" class="form-control"  placeholder="公司名称" value="${company.companyName}"/>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>项目名称</label>
								<select id="projectId" name="projectId" class="form-control">
									<option value="">请选择</option>
	  									<c:forEach items="${projectList}" var="project">
	  										<c:choose>
	  											<c:when test="${project.projectId == company.projectId}">
	  												<option value="${project.projectId}" selected="selected">${project.projectName}</option>
	  											</c:when>
	  											<c:otherwise>
	  												<option value="${project.projectId}">${project.projectName}</option>
	  											</c:otherwise>
	  										</c:choose>
	  									</c:forEach>	
								</select>
							</div>
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>是否允许经营医疗器械</label>
								<select id="isMedicalinstrumentsRight" name="isMedicalinstrumentsRight" class="form-control">
									<option value="">请选择</option>
									<c:choose>
										<c:when test="${company.isMedicalinstrumentsRight == 'Y' }">
											<option value="Y" selected="selected">是</option>
	  										<option value="N">否</option>
										</c:when>
										<c:otherwise>
											<option value="Y">是</option>
	  										<option value="N" selected="selected">否</option>
										</c:otherwise>
									</c:choose>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>公司类型</label>
								<select id="companyType" name="companyType" class="form-control">
									<option value="">请选择</option>
									<c:forEach items="${sessionScope.GLOBAL_CompanyType}" var="companyType">
										<c:choose>
											<c:when test="${companyType.number == company.companyType}">
												<option value="${companyType.number}" selected="selected">${companyType.value}</option>
											</c:when>
											<c:otherwise>
												<option value="${companyType.number}">${companyType.value}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>	
								</select>
							</div>
							<div class="col-md-6 form-group2">
								<label class="control-label"><span id="coldRequireId" style="color: red;display: none;">*</span>是否提供冷链服务</label>
								<select id="isColdChainService" name="isColdChainService" class="form-control">
									<option value="">请选择</option>
	  								<c:choose>
	  									<c:when test="${company.isColdChainService == 'Y' }">
	  										<option value="Y" selected="selected">是</option>
	  										<option value="N">否</option>
	  									</c:when>
	  									<c:otherwise>
	  										<option value="Y">是</option>
	  										<option value="N" selected="selected">否</option>
	  									</c:otherwise>
	  								</c:choose>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group2">
								<label class="control-label"><span style="color: red;">*</span>经营方式</label>
								<select id="operationType" name="operationType" class="form-control">
									<option value="">请选择</option>
									<c:forEach items="${sessionScope.GLOBAL_OperationType}" var="operationType">
	  										<c:choose>
	  											<c:when test="${operationType.number == company.operationType}">
	  												<option value="${operationType.number}" selected="selected">${operationType.value}</option>
	  											</c:when>
	  											<c:otherwise>
	  												<option value="${operationType.number}">${operationType.value}</option>
	  											</c:otherwise>
	  										</c:choose>
	  								</c:forEach>
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>证照校验
									<button>选择</button>
								</label>
								<div class="input-group">
							    	<select multiple="multiple" name="licenseIds" id="licenseIds" class="form-control">
							    		<c:forEach items="${licenseList}" var="license">
							    			<option value="${license.licenseId}" selected="selected">${license.licenseType}</option>
							    		</c:forEach>
							    	</select>
							    </div>
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label">备注</label>
								<textarea rows="4" cols="5" id="remark1" name="remark1" class="form-control">${company.remark1}</textarea>
							</div>
						</div>
					</div>
					<div><label class="control-label">相关证件</label></div><hr/>
					<div>
						<table style="width: 98%;font-size: 14px;">
							<thead>
								<tr>
									<th>证照名称</th>
									<th>证照类型</th>
									<th>证照编号</th>
									<th>有效期至</th>
									<th>检查确认状态</th>
									<th>数据状体</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>备案凭证</td>
									<td>医疗器械经营许可证</td>
									<td>20141027</td>
									<td>2018-6-10</td>
									<td>已确认</td>
									<td>启用</td>
									<td>查看</td>
								</tr>
								<tr>
									<td>营业执照</td>
									<td>营业执照</td>
									<td>310152102554</td>
									<td>2018-6-10</td>
									<td>未检查</td>
									<td>禁用</td>
									<td>查看</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
				<c:if test="${'1' == operateStage || '2' == operateStage}">
					<div class="col-md-12 form-group">
						<label class="control-label"><span style="color: red;">*</span>审批意见</label>
						<div class="input-group">
					    	<textarea rows="4" cols="5" id="auditOpinion" name="auditOpinion" class="form-control"></textarea>
					    </div>
					</div>
				</c:if>
				<div class="col-md-6 col-sm-offset-3 form-group" style ="text-align: center;">
					<c:choose>
						<%--提交阶段 --%>
						<c:when test="${'0' == operateStage}">
							<button type="button" class="btn btn-primary" onclick="submitCompany();">提交</button>
							<button type="button" class="btn btn-primary" onclick="beforeUpdateCompany();">保存</button>
						</c:when>
						<%-- 一级审批或二级审批 --%>
						<c:when test="${'1' == operateStage || '2' == operateStage}">
							<button type="button" class="btn btn-primary" onclick="auditCompany('3','${operateStage}');">审批通过</button>
							<button type="button" class="btn btn-primary" onclick="auditCompany('4','${operateStage}');">审批不通过</button>
						</c:when>
						<%-- 审批结束 --%>
						<c:when test="${'3' == operateStage}">
							<button type="button" class="btn btn-primary" onclick="beforeUpdateCompany();">保存</button>
						</c:when>
					</c:choose>
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
						<td><input type="checkbox" name="licenseId" ng-checked="license.checked" value="{{license.licenseId}}&{{license.licenseType}}"></td>
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

	angular.module('updateCompanyApp', ['ui.bootstrap']).controller('updateCompanyCtrl', function($scope,$http,$modal) {
		$scope.selectUser = function(){
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
								$("#table").find("[name=licenseId]").each(function(index,item){
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
						var license = [];
						$('#table').find('[name=licenseId]').each(function(index,item){
							if($(this).prop("checked")){
								license.push($(item).val());
							}
						});
						if(license.length > 0){
							$('#licenseIds').empty();
							$.each(license,function(index,item){
								var value = item.split("&");
								$('#licenseIds').append("<option value='"+value[0]+"' selected>" + value[1] + "</option>");
							});
						}else{
							alert('请选择证照');
						}
					}
				}]
			 })
		};	
	});
	$(function(){
		$("#upateCompany").validate({
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
	
	
	// 更新公司
	function beforeUpdateCompany(){
		if($("#updateCompany").valid()){
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
				companyId : $('#companyId').val(),
				projectId : $('#projectId').val(),
				companyName : $('#companyName').val(),
				companyType : $('#companyType').val()
			},
			success:function(data){
				if(data.success){
			    	updateCompany();
			    }else{
					alert(data.message);
			    }
			}
		});
	}
	
	function updateCompany(){
		$.ajax({
			type:"post",
			async:false,
			url:'<%=path%>/company/updateCompany.do',
			data: $("#updateCompany").serialize(),
			success:function(data){
				if('true' == data){
					alert("更新成功");
					location.href="<%=path%>/company/toCompanyList.do";
			    }else{
			    	alert("更新失败");
			    }
			}
		});
	}
	
	// 提交
	function submitCompany(){
		$.ajax({
			type:"post",
			async:false,
			url:'<%=path%>/company/submitCompany.do',
			data: {
				companyId : $('#companyId').val()
			},
			success:function(data){
				if(data.success){
					alert("提交成功");
					location.href="<%=path%>/company/toCompanyList.do";
			    }else{
			    	alert(data.message);
			    }
			}
		});
	}
	
	// 公司审批
	function auditCompany(auditStatus,auditStage){
		// 审批意见
		var auditOpinion = $('#auditOpinion').val();
		if(!auditOpinion){
			alert('请填写审批意见');
			return;
		}
		$.ajax({
			type:"post",
			async:false,
			url:'<%=path%>/company/auditCompany.do',
			data: {
				companyId : $('#companyId').val(),
				auditStatus : auditStatus,
				auditStage : auditStage,
				auditOpinion : auditOpinion
			},
			success:function(data){
				if(data.success){
					alert("审批成功");
					location.href="<%=path%>/company/toCompanyList.do";
			    }else{
			    	alert(data.message);
			    }
			}
		});
	}
</script>