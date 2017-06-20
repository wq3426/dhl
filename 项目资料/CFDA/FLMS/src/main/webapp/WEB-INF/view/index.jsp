<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container" ng-app="assetApp" ng-controller="assetCtrl">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<!--grid-->
			<div >
				<div style="height:550px;width:100%;background-image:url(../resources/images/bg_1.png)">
					<center>
					<h1 style="margin-left:20px;padding-top:100px;color:#5B5B5B;">欢迎使用FLMS系统！</h1>
					</center>
				</div>
					
				</div>
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
		</div>
	</div>
	
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
