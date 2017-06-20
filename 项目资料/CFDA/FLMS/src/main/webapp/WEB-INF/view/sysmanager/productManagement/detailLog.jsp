<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>产品查看</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system"  ng-app="productApp" ng-controller="productCtrl">
				<c:if test="${!empty productUpdateList}">
					<div class="validation-form">
						<div class="row">
							<div class="col-md-12 form-group1">
								<h3>更新确认</h3>
								<div class="vali-form">
									<div class="w3l-table-info">
										<table id="table">
											<thead>
												<tr>
													<th>序号</th>
													<th>文件</th>
													<th>传入时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${productUpdateList }" var="productUpdateList" varStatus="status">
													<tr>
														<td>${ status.index + 1}</td>
														<td>${ productUpdateList.fileName}</td>
														<td><fmt:formatDate value="${ productUpdateList.createDate}" pattern="yyyy-MM-dd"/></td>
														<td>
															<a href="#" ng-click="details('${product.productId}','${productUpdateList.productId}')">详情</a>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
			            	 	</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</div>
				</c:if>
			
			
				<div class="validation-form">
							
						<h3>基本信息</h3>
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label">项目名称</label>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">物料号</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6">
									<input type="hidden" placeholder="主键" class="form-control" name="ProductId" id="updateProductId" >
									<span class="ttd">${product.projectName }</span>
								</div>
								<div class="col-md-6">
									<span class="ttd">${product.material }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-12 form-group1">
									<label class="control-label">物料描述</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<span class="ttd">${product.materialDescription }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">物料类型</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">规格/型号</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.materalType == null ? null : product.materalType == 'Y' ? '是' : '否'}</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.model }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">JAN Code</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">产品名称</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.janCode }</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.productName }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">植入/非植入</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">运输条件</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.inplantFlag }</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.shippingCondition }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">毛重</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">尺寸</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.weight }</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.size }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">TraceType</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">CFDA状态代码</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.traceType }</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.cfdaStatecode }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">是否效期管理物料</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">近效期管理天数</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.validManage == null ? null : product.validManage == 'Y' ? '是' : '否'}</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.validManageDays }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">是否需要冷链存储</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">储存条件</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.isitColdChainStorage == null ? null : product.isitColdChainStorage == 'Y' ? '是' : '否'}</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.storageCondition }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">产品有效期（年）</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">生产企业许可证号</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.validityPeriod }</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.productionCompanyLicense }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">产品许可证持证商</label> 
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">生产企业名称</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.productionLicenseHolder }</span>
								</div>
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.productionCompany }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-12 form-group1">
									<label class="control-label">产品类型</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<span class="ttd">
										<c:forEach items="${productTypeList}" var="productTypeList">
											<c:if test="${productTypeList.productTypeId eq product.productType}">
												${productTypeList.productTypeLevel }-${productTypeList.productTypeNo }-${productTypeList.productTypeName }
											</c:if>
										</c:forEach>
									</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-6 form-group1">
									<label class="control-label">单位</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<span class="ttd">${product.uom }</span>
								</div>
							</div>
							
							<div class="row" style="margin-top: 15px;">
								<div class="col-md-12 form-group1">
									<label class="control-label">备注</label> 
								</div>
							</div>
							<div class="row">
								<div class="col-md-12 form-group1">
									<span class="ttd">${product.remark1 == null ? '无' : product.remark1}</span>
								</div>
							</div>
						</div>	
				</div>
				
				
				<c:if test="${!empty ProductLicense}">		
					<div class="validation-form">
							<div class="col-md-12 form-group1">
								<h3>相关证照</h3>
								<div class="vali-form">
									<div class="w3l-table-info">
										<table id="table">
											<thead>
												<tr>
													<th>证照名称</th>
													<th>证照类型</th>
													<th>证照编号</th>
													<th>物料号</th>
													<th>有效期至</th>
													<th>检查确认状态</th>
													<th>数据状态</th>
												</tr>
											</thead>
											<tbody>
											</tbody>
										</table>
									</div>
			            	 	</div>
								<div class="clearfix"></div>
							</div>
						</div>
				</c:if>		
				<c:if test="${!empty auditLogList}">		
					<div class="validation-form">
						<h3>审批</h3>
						<div class="vali-form">
							<div class="w3l-table-info">
								<table id="table">
									<thead>
										<tr>
											<th>审批人</th>
											<th>审批岗位</th>
											<th>审批时间</th>
											<th>审批意见</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${auditLogList}" var="auditLogList">
											<tr>
												<td>${auditLogList.auditPersion}</td>  
												<td>${auditLogList.auditPosition}</td>  
												<td><fmt:formatDate value="${auditLogList.auditDate}" pattern="yyyy-MM-dd"/></td>  
												<td>${auditLogList.auditOpinion}</td>  
											</tr>
					                    </c:forEach>
									</tbody>
								</table>
							</div>
	            	 	</div>
						<div class="clearfix"></div>
					</div>
				</c:if>			
				
				<div class="col-md-6 col-sm-offset-2 form-group" style ="text-align: center;">
					<button type="reset" onclick="goBack()" class="btn btn-primary">取消</button>
				</div>
				
				
				
<!-- 更新产品的数据 -->
<div class="modal fade" id="productUpdateModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel">
    <div class="modal-dialog" role="document" style="width: 700px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="productUpdateModalLabel">产品更新</h4>
            </div>
            <div class="modal-body">
            	<form class="form-inline" id="updateDetails">
	            	<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label"><span style="color: red;">*</span>项目名称:</label> 
							<input type="hidden" placeholder="项目名称" class="form-control" ng-model="product.projectName">
							<input type="text" placeholder="项目名称" class="form-control" ng-model="productUpdate.projectName" readonly>
							<span ng-if="product.projectName!=productUpdate.projectName" class="remaidlabel">{{product.projectName}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label"><span style="color: red;">*</span>物料号:</label> 
							<input type="hidden" placeholder="物料号" class="form-control" ng-model="product.material">
							<input type="text" placeholder="物料号" class="form-control" name="material" ng-model="productUpdate.material" readonly>
							<span ng-if="product.material!=productUpdate.material" class="remaidlabel">{{product.material}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group1">
							<label class="control-label">物料描述:</label> 
							<input type="hidden" placeholder="物料描述" class="form-control" ng-model="product.materialDescription" >
							<input type="text" placeholder="物料描述" class="form-control" name="materialDescription" ng-model="productUpdate.materialDescription" readonly>
							<span ng-if="product.materialDescription!=productUpdate.materialDescription" class="remaidlabel">{{product.materialDescription}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">物料类型:</label> 
							<input type="hidden" placeholder="物料类型" class="form-control" ng-model="product.materalType">
							<input type="text" placeholder="物料类型" class="form-control" name="materalType" ng-model="productUpdate.materalType" readonly>
							<span ng-if="product.materalType!=productUpdate.materalType" class="remaidlabel">{{product.materalType}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">JAN Code:</label> 
							<input type="hidden" placeholder="JAN Code" class="form-control" ng-model="product.janCode">
							<input type="text" placeholder="JAN Code" class="form-control" name="janCode" ng-model="productUpdate.janCode" readonly>
							<span ng-if="product.janCode!=productUpdate.janCode" class="remaidlabel">{{product.janCode}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">产品名称:</label> 
							<input type="hidden" placeholder="产品名称" class="form-control" ng-model="product.productName">
							<input type="text" placeholder="产品名称" class="form-control" name="productName" ng-model="productUpdate.productName" readonly>
							<span ng-if="product.productName!=productUpdate.productName" class="remaidlabel">{{product.productName}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label"><span style="color: red;">*</span>规格/型号:</label> 
							<input type="hidden" placeholder="规格/型号" class="form-control" ng-model="product.model">
							<input type="text" placeholder="规格/型号" class="form-control" name="model" ng-model="productUpdate.model" readonly>
							<span ng-if="product.model!=productUpdate.model" class="remaidlabel">{{product.model}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">植入/非植入:</label> 
							<input type="hidden" placeholder="植入/非植入" class="form-control" ng-model="product.inplantFlag">
							<input type="text" placeholder="植入/非植入" class="form-control" name="inplantFlag" ng-model="productUpdate.inplantFlag" readonly>
							<span ng-if="product.inplantFlag!=productUpdate.inplantFlag" class="remaidlabel">{{product.inplantFlag}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">运输条件:</label> 
							<input type="hidden" placeholder="运输条件" class="form-control" ng-model="product.shippingCondition">
							<input type="text" placeholder="运输条件" class="form-control" name="shippingCondition" ng-model="productUpdate.shippingCondition" readonly>
							<span ng-if="product.shippingCondition!=productUpdate.shippingCondition" class="remaidlabel">{{product.shippingCondition}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">毛重:</label> 
							<input type="hidden" placeholder="毛重" class="form-control" ng-model="product.weight">
							<input type="text" placeholder="毛重" class="form-control" name="weight" ng-model="productUpdate.weight" readonly>
							<span ng-if="product.weight!=productUpdate.weight" class="remaidlabel">{{product.weight}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">尺寸:</label> 
							<input type="hidden" placeholder="尺寸" class="form-control" ng-model="product.size">
							<input type="text" placeholder="尺寸" class="form-control" name="size" ng-model="productUpdate.size" readonly>
							<span ng-if="product.size!=productUpdate.size" class="remaidlabel">{{product.size}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">TraceType:</label> 
							<input type="hidden" placeholder="TraceType" class="form-control" ng-model="product.traceType">
							<input type="text" placeholder="TraceType" class="form-control" name="traceType" ng-model="productUpdate.traceType" readonly>
							<span ng-if="product.traceType!=productUpdate.traceType" class="remaidlabel">{{product.traceType}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">CFDA状态代码:</label> 
							<input type="hidden" placeholder="CFDA状态代码" class="form-control" ng-model="product.cfdaStatecode">
							<input type="text" placeholder="CFDA状态代码" class="form-control" name="cfdaStatecode" ng-model="productUpdate.cfdaStatecode" readonly>
							<span ng-if="product.cfdaStatecode!=productUpdate.cfdaStatecode" class="remaidlabel">{{product.cfdaStatecode}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">是否效期管理物料:</label> 
							<input type="hidden" placeholder="是否效期管理物料" class="form-control" ng-model="product.validManage">
							<input type="text" placeholder="是否效期管理物料" class="form-control" name="validManage" ng-value="productUpdate.validManage == null ? null : productUpdate.validManage == 'Y' ? '是' : '否'" readonly>
							<span ng-if="product.validManage!=productUpdate.validManage" class="remaidlabel">{{product.validManage == null ? null : product.validManage == 'Y' ? '是' : '否'}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">近效期管理天数:</label> 
							<input type="hidden" placeholder="近效期管理天数" class="form-control" ng-model="product.validManageDays">
							<input type="text" placeholder="近效期管理天数" class="form-control" name="validManageDays" ng-model="productUpdate.validManageDays" readonly>
							<span ng-if="product.validManageDays!=productUpdate.validManageDays" class="remaidlabel">{{product.validManageDays}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label"><span style="color: red;">*</span>是否需要冷链存储:</label> 
		    				<input type="hidden" placeholder="是否需要冷链存储" class="form-control" ng-model="product.isitColdChainStorage">
							<input type="text" placeholder="是否需要冷链存储" class="form-control" name="isitColdChainStorage"  ng-value="productUpdate.isitColdChainStorage == null ? null : productUpdate.isitColdChainStorage == 'Y' ? '是' : '否'" readonly>
							<span ng-if="product.isitColdChainStorage!=productUpdate.isitColdChainStorage" class="remaidlabel">{{product.isitColdChainStorage == null ? null : product.isitColdChainStorage == 'Y' ? '是' : '否'}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">储存条件:</label> 
							<input type="hidden" placeholder="储存条件" class="form-control" ng-model="product.storageCondition">
							<input type="text" placeholder="储存条件" class="form-control" name="storageCondition" ng-model="productUpdate.storageCondition" readonly>
							<span ng-if="product.storageCondition!=productUpdate.storageCondition" class="remaidlabel">{{product.storageCondition}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6 form-group1">
							<label class="control-label">产品有效期（年）:</label> 
							<input type="hidden" placeholder="产品有效期（年）" class="form-control" ng-model="product.validityPeriod">
							<input type="text" placeholder="产品有效期（年）" class="form-control" name="validityPeriod" ng-model="productUpdate.validityPeriod" readonly>
							<span ng-if="product.validityPeriod!=productUpdate.validityPeriod" class="remaidlabel">{{product.validityPeriod}}</span>
						</div>
						<div class="col-md-6 form-group1">
							<label class="control-label">生产企业许可证号:</label> 
							<input type="hidden" placeholder="生产企业许可证号" class="form-control" ng-model="product.productionCompanyLicense">
							<input type="text" placeholder="生产企业许可证号" class="form-control" name="productionCompanyLicense" ng-model="productUpdate.productionCompanyLicense" readonly>
							<span ng-if="product.productionCompanyLicense!=productUpdate.productionCompanyLicense" class="remaidlabel">{{product.productionCompanyLicense}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group1">
							<label class="control-label">产品许可证持证商:</label> 
							<input type="hidden" placeholder="产品许可证持证商" class="form-control" ng-model="product.productionLicenseHolder">
							<input type="text" placeholder="产品许可证持证商" class="form-control" name="productionLicenseHolder" ng-model="productUpdate.productionLicenseHolder" readonly>
							<span ng-if="product.productionLicenseHolder!=productUpdate.productionLicenseHolder" class="remaidlabel">{{product.productionLicenseHolder}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group1">
							<label class="control-label"><span style="color: red;">*</span>生产企业名称:</label> 
							<input type="hidden" placeholder="生产企业名称" class="form-control" ng-model="product.productionCompany">
							<input type="text" placeholder="生产企业名称" class="form-control" name="productionCompany" ng-model="productUpdate.productionCompany" readonly>
							<span ng-if="product.productionCompany!=productUpdate.productionCompany" class="remaidlabel">{{product.productionCompany}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group1">
							<label class="control-label"><span style="color: red;">*</span>产品类型:</label> 
							<input type="hidden" placeholder="产品类型" class="form-control" ng-model="product.productType">
							<input type="text" placeholder="产品类型" class="form-control" name="productType" ng-model="productUpdate.productType" readonly>
							<span ng-if="product.productType!=productUpdate.productType" class="remaidlabel">{{product.productType}}</span>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12 form-group1">
							<label class="control-label">备注:</label> 
							<input type="hidden" placeholder="备注" class="form-control" ng-model="product.remark1">
							<input type="text" placeholder="备注" class="form-control" name="remark1" ng-model="productUpdate.remark1" readonly>
							<span ng-if="product.remark1!=productUpdate.remark1" class="remaidlabel">{{product.remark1}}</span>
						</div>
					</div>
				</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-default" ng-click="reject(product,productUpdate)">驳回</button>
                <button type="button" class="btn btn-primary" ng-click="updateDetails(product,productUpdate)">确定</button>
            </div>
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
 
angular.module('productApp', ['ui.bootstrap']).controller('productCtrl', function($scope,$http,$modal) {
		$scope.details = function(productId,productUpdateId){
			url = '<%=path%>/product/details.do?productId='+productId+"&productUpdateId="+productUpdateId,
			$http.post(url)
				.success(function(data){
					
					$scope.product = data[0];
					$scope.productUpdate = data[1];
					
					$('#productUpdateModal').modal('show')
				})
				.error(function(data){
					alert("失败");
				});
		};
		//驳回产品信息
		$scope.reject = function(product,productUpdate){
			var productId = product.productId;
			var productUpdateId = productUpdate.productId;
			url = '<%=path%>/product/reject.do?productUpdateId='+productUpdateId,
			$http.post(url)
				.success(function(data){
					if(data){
						alert("驳回成功");
						location.href="<%=path%>/product/toCheckLog.do?productId="+productId;
					}else{
						alert("驳回失败");
					}
				})
				.error(function(data){
					alert("驳回失败");
				});
		}
		//更新产品信息
		$scope.updateDetails = function(product,productUpdate){
			var productId = product.productId;
			var productUpdateId = productUpdate.productId;
			$.ajax({
				type:"Post",
				async:false,
				url:'<%=path%>/product/updateDetails.do?productId='+productId+"&productUpdateId="+productUpdateId,
				data: $("#updateDetails").serialize(),
				success:function(data){
					if(data){
						alert("更新成功");
						location.href="<%=path%>/product/toCheckLog.do?productId="+productId;
					}else{
						alert("更新失败");
					}
				}
			});
		}
});
		
  		function goBack(){
  			location.href="<%=path%>/product/toProductList.do";
  		}
  </script>