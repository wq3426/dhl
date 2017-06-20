<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container" ng-app="updateCompanyApp" ng-controller="updateCompanyCtrl">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>公司查看</a></li>
			</ol>
			<form id="updateCompany" method="post">
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
					<div class="vali-form">
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>编号</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>公司名称</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span class="ttd">${company.companyNo}</span>
							</div>
							<div class="col-md-6">
								<span class="ttd">${company.companyName}</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>项目名称</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>是否允许经营医疗器械</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span class="ttd">
									<c:forEach items="${projectList}" var="project">
  										<c:choose>
  											<c:when test="${project.projectId == company.projectId}">${project.projectNo}</c:when>
  										</c:choose>
	  								</c:forEach>
								</span>
							</div>
							<div class="col-md-6">
								<span class="ttd">
									<c:choose>
										<c:when test="${company.isMedicalinstrumentsRight == 'Y' }">是</c:when>
										<c:otherwise>否</c:otherwise>
									</c:choose>
								</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>公司类型</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>是否提供冷链服务</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span class="ttd">
									<c:forEach items="${sessionScope.GLOBAL_CompanyType}" var="companyType">
  										<c:choose>
  											<c:when test="${companyType.number == company.companyType}">${companyType.value}</c:when>
  										</c:choose>
	  								</c:forEach>
								</span>
							</div>
							<div class="col-md-6">
								<span class="ttd">
									<c:choose>
										<c:when test="${company.isColdChainService == 'Y' }">是</c:when>
										<c:otherwise>否</c:otherwise>
									</c:choose>
								</span>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>经营方式</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span class="ttd">
									<c:forEach items="${sessionScope.GLOBAL_OperationType}" var="operationType">
  										<c:choose>
  											<c:when test="${operationType.number == company.operationType}">${operationType.value}</c:when>
  										</c:choose>
	  								</c:forEach>
								</span>
							</div>
						</div>
						
						<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>证照校验</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label"><span style="color: red;">*</span>备注</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span class="ttd">
									<select multiple="multiple" name="licenseIds" id="licenseIds" class="form-control">
							    		<c:forEach items="${licenseList}" var="license">
							    			<option value="${license.licenseId}">${license.licenseType}</option>
							    		</c:forEach>
							    	</select>
								</span>
							</div>
							<div class="col-md-6">
								<span class="ttd">
									${company.remark1}
								</span>
							</div>
						</div>
					</div>
					<div><label class="control-label">相关证件</label></div><hr/>
					<div class="w3l-table-info">
						<table id="table">
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
					<div><label class="control-label">审批</label></div><hr/>
					<div class="w3l-table-info">
						<table id="table">
							<thead>
								<tr>
									<th>审批人</th>
									<th>审批人岗位</th>
									<th>审批时间</th>
									<th>审批意见</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${auditList}" var="audit">
									<tr>
										<td>${audit.auditPersion}</td>
										<td>${audit.auditPosition}</td>
										<td>
											<fmt:formatDate value="${audit.auditDate}" pattern="yyyy-MM-dd"/>
										</td>
										<td>${audit.auditOpinion }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<div class="col-md-12 form-group">
					<button type="button" class="btn btn-default" onclick="returnMain();">取消</button>
				</div>
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
			</form>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script type="text/javascript">
	function returnMain(){
		window.location.href="<%=path%>/company/toCompanyList.do";
	}
</script>