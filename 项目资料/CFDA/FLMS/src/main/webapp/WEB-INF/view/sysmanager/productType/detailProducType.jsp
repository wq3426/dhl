<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>产品类型查看</a></li>
			</ol>
			<!--grid-->
			<div class="validation-system">
				<div class="validation-form">
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<label class="control-label">编号:</label> 									
								</div>
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<span style="color: black;font-size: 15px;">${ptelist.productTypeNo }</span>
								</div>								
							</div>
							<div class="row">
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<label class="control-label">类型:</label> 									 
								</div>
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<span style="color: black;font-size: 15px;">${ptelist.productTypeLevel }</span>
								</div>
							</div>
							<div class="row" ">
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<label class="control-label">名称:</label>  
								</div>
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<span style="color: black;font-size: 15px;">${ptelist.productTypeName }</span>
								</div>
								
							</div>
							<div class="row">
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<label class="control-label">状态:</label> 									
								</div>
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<span style="color: black;font-size: 15px;">${ptelist.dataStatus == null ? null : ptelist.dataStatus == 'Y' ? '启用' : '禁用'}</span>
								</div>
							</div>
							<div class="row" style="margin-top: 10px;">
								<div class="col-md-7 col-sm-offset-4 form-group1">
									<label class="control-label">备注</label> 
								</div>
								<div class="col-md-3 col-sm-offset-4 form-group1">
									<span style="color: black;font-size: 15px;">${ptelist.remark == null ? '无' : ptelist.remark}</span>
								</div>
							</div>													
						</div>
				</div>
				<div class="col-md-7 col-sm-offset-2 form-group" style ="text-align: center;">
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
  			location.href="<%=path%>/productType/toProductTypeList.do";		
  		}
  </script>