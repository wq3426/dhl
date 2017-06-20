<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>项目查看</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
		        	<h3>基本信息</h3>
					<div class="vali-form">
	            		<div class="row">
							<div class="col-md-6 form-group1">
								<label class="control-label">编号</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label">项目名称</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<span class="ttd">${project.projectNo }</span>
							</div>
							<div class="col-md-6">
								<span class="ttd">${project.projectName }</span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-6 form-group1">
								<label class="control-label">合同开始时间</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label">合同结束时间</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<span class="ttd"><cfmt:formatDate value="${project.contractStartDate }" pattern="yyyy-MM-dd"/></span>
							</div>
							<div class="col-md-6 form-group1">
								<span class="ttd"><cfmt:formatDate value="${project.contractEndDate }" pattern="yyyy-MM-dd"/></span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-6 form-group1">
								<label class="control-label">委托期限(月)</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label">是否委托加贴中文标签</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<span class="ttd">${project.contractPeriod }</span>
							</div>
							<div class="col-md-6 form-group1">
								<span class="ttd">${project.isChineseLabels == null ? null : project.isChineseLabels == 'Y' ? '是' : '否'}</span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-6 form-group1">
								<label class="control-label">是否效验合同有效期</label> 
							</div>
							<div class="col-md-6 form-group1">
								<label class="control-label">是否效验经营方式</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-6 form-group1">
								<span class="ttd">${project.isValidateContract == null ? null : project.isValidateContract == 'Y' ? '是' : '否'}</span>
							</div>
							<div class="col-md-6 form-group1">
								<span class="ttd">${project.isValidateOperationtype == null ? null : project.isValidateContract == 'Y' ? '是' : '否'}</span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12 form-group1">
								<label class="control-label">委托客户邮箱</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group1">
								<span class="ttd">${project.customerMail}</span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12 form-group1">
								<label class="control-label">委托业务范围</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group1">
								<span class="ttd">${project.businessScope}</span>
							</div>
						</div>
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12 form-group1">
								<label class="control-label">备注</label> 
							</div>
						</div>
						<div class="row">
							<div class="col-md-12 form-group1">
								<span class="ttd">${project.remark == null ? '无' : project.remark}</span>
							</div>
						</div>
	                	<div class="clearfix"></div>
					</div>
					<div class="clearfix"></div>
				</div>
				
			<c:if test="${!empty updateLog}">
	            <div class="validation-form">
					<h3>合同历史</h3>
					<div class="vali-form">
						<div class="w3l-table-info">
							<table id="table">
							<thead>
								<tr>
									<th >修改人</th>
									<th >修改时间</th>
									<th >修改字段</th>
									<th >修改前</th>
									<th >修改后</th>
									<th >修改原因</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${updateLog}" var="updateLog">
									<tr>
										<td >${updateLog.updatePersion}</td>
										<td ><fmt:formatDate value="${updateLog.updateDate}" pattern="yyyy-MM-dd HH:mm"/></td>
										<td >${updateLog.fieldName}</td>
										<td ><cfmt:formatDate value="${updateLog.oldValue}" pattern="yyyy-MM-dd"/></td>
										<td >${updateLog.newValue}</td>
										<td >
											<div style="width:400px;WORD-WRAP: break-word;">
												${updateLog.updateReason}
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
            	 </div>
				<div class="clearfix"></div>
			</div>
		</c:if>	
				<div class="clearfix"></div>
			</div>
			
				<div class="col-md-12" style ="text-align: center;">
					<button type="reset" onclick="goBack()" class="btn btn-primary">取消</button>
				</div>
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
 <script type="text/javascript">
  		function goBack(){
  			location.href="<%=path%>/projectManage/toProjectList.do";
  		}
  </script>