
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
	<div class="page-container">
		<!--/content-inner-->
		<div class="left-content">
			<div class="mother-grid-inner">
				<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
				<ol class="breadcrumb">
					<li class="breadcrumb-item"><a href="javascript:void(0);">EDI日志</a>
					</li>
				</ol>
				<!--grid-->
				<div class="validation-system" ng-app="ediLogApp" ng-controller="ediLogCtrl">
					<div class="validation-form">
						<form class="form-inline" >
						     <div class="form-group">
								<label for="exampleInputName2">处理状态：</label> 
								 <select class="form-control" ng-model="filter.processingState"  placeholder="处理状态" id="processingState" name="processingState">
								          <option value="">请选择</option>
								          <option value="Y">成功</option>
								          <option value="N">失败</option>
								  </select>   
							</div>
							   <div class="form-group">
								<label for="exampleInputName2">处理结果：</label> 
								    <input type="text"	class="form-control" ng-model="filter.processingResult" id="processingResult" name="processingResult" placeholder="处理结果">
								         
							</div>
							<div class="form-group">
								<label for="exampleInputName2">EDI类型：</label> 
										 <select class="form-control" ng-model="filter.edi_Type"  placeholder="EDI类型" id="edi_Type" name="edi_Type">
								          <option value="">请选择</option>
								          <option value="ASN">ASN</option>
                                          <option value="SO">SO</option>
						        </select>
							</div>
							<div class="form-group">
								<label for="exampleInputName2">处理时间:</label>				
								<input type="text" placeholder="处理时间结束时间" class="form-control datepicker" ng-model="filter.processingDate_Start" name="processingDate_Start" id="processingDate_Start">
					            <span >to</span>					            
					            <input type="text" placeholder="处理时间结束时间" class="form-control datepicker" ng-model="filter.processingDate_End" name="processingDate_End" id="processingDate_End" >
							</div>
							<div class="form-group">
								<label for="exampleInputName2">文件名:</label> <input type="text"
									class="form-control" ng-model="filter.fileName" id="fileName" name="fileName" placeholder="文件名">
							</div>			
							<defined:isright rightCode="user_search">
								<button type="button" class="btn btn-primary" ng-click="queryUser();" id="sub">查询</button>
							</defined:isright>
						</form>
						<div class="w3l-table-info">
							<table id="table" >
								<thead>
									<tr>
										<th>序号</th>
					                    <th>文件名</th>					                    
					                    <th>EDI类型</th>
					                    <th>处理时间</th>
					                    <th>处理状态</th> 
					                    <th>处理结果</th>
					                    <th>重发人</th>
					                    <th>重发时间</th>          
					                    <th>重发原因</th>
					                    <th>操作</th>   
									</tr>
								</thead>
								<tbody id="dataBody" style="display: none;" >
									<tr ng-repeat="ul in elgList">
										<td>{{$index + 1}}</td>			
										<td>{{ul.fileName}}</td>
										<td>{{ul.edi_Type}}</td>
										<td>{{ul.processingDate | limitTo:10}}</td>
										<td>{{ul.processingState == null ? null : ul.processingState == 'Y' ? '成功' : '失败'}}</td>
										<td width="20%">{{ul.processingResult}}</td>
										<td>{{ul.retransMission}}</td>
										<td>{{ul.retransMissionTime | limitTo:10}}</td>
										<td>{{ul.repeatedCause}}</td>																			
										<td>
										    <defined:isright rightCode="user_assignRole">
												<a href="#"  ng-click="deleteUser(ul)">下载</a>
													<!--  data-toggle="modal" data-target="#userModal" -->
													&nbsp;&nbsp;&nbsp;
											</defined:isright>
											<defined:isright rightCode="user_del">
												<a href="#"  data-toggle="modal" data-target="#houseModal"    ng-click="selectrewire(ul)" >重发</a>
												<!--   ng-click="deleteUser(ul)" -->
											</defined:isright>
										</td>
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
						<!-- 模态框 -->
						<form id="updatefrom">
    					<div class="modal fade" id="houseModal" tabindex="-1" role="dialog"
							aria-labelledby="partModalLabel">
							<div class="modal-dialog" role="document" style="width:750px;">
								<div class="modal-content">
									   <div class="modal-header">
							            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							            <h4 class="modal-title" id="partModalLabel">重发</h4>
							          </div>
									<div class="modal-body" style="margin-top:-30px;">
										<div class="row">													
											<div class="form-group">
											<div class="col-sm-12">
												<label class="control-label">重发原因</label>
												     <input type="hidden" class="form-control" ng-model="filePath" ></input>	
												    <input type="hidden" class="form-control" name="ediId" ng-model="ediId" ></input>
													<textarea rows="5" cols="30" name="repeatedCause" id="repeatedCause" class="form-control"></textarea>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
										<button type="button" class="btn btn-primary" ng-click="download(ediId,filePath)">确定</button>
									</div>
								</div>
							</div>
						</div>
                       </form>
					</div>

				</div>
				<div class="clearfix"></div>

			</div>
		</div>
		<!--//content-inner-->
		<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
	</div>
	
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
<script>
	angular.module('ediLogApp', ['ui.bootstrap']).controller('ediLogCtrl', function($scope,$http,$modal) {
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
			url = '<%=path%>/ediLog/ediLogList.do',
			postCfg = {
			    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
			    transformRequest: function (data) {
			        return $.param(data);
			    }
			   
			};
		    $scope.filter.processingDate_Start=$("#processingDate_Start").val();
		    $scope.filter.processingDate_End=$("#processingDate_End").val();
			$http.post(url,data,postCfg)
				.success(function(data){
					$scope.filter.total = data.total;
					$scope.elgList = data.list;
				})
				.error(function(data){
					alert("操作失败");
				});
		};
		//文件下载
		$scope.deleteUser = function(ul){
				 window.location.href='<%=path%>/ediLog/fileDownload.do?filePath='+ul.filePath;                  
				
		};
		//文件重发
		$scope.download = function(ediId,filePath){
		        var rse=$("#repeatedCause").val();	
				$.ajax({
					url:"<%=path%>/ediLog/retransmission.do",
					type:"post",
					data:{ediId:ediId,filePath:filePath,repeatedCause:rse},					
					success:function(result){
									if(result==2){									
										 alert("重发成功");
										 $("#houseModal").modal("hide");
										  $("#sub").click();									
									}else if(result==3){									
										alert("重发失败，原因：文件不存在");										
									}else if(result==0){
																		    
										alert("重发原因存储失败");										
									}		
					}
				})    
									
		       };
		       
		       
		    //查询   filePath
		$scope.selectrewire = function(ul){
			var data = $scope.filter;
			url = '<%=path%>/ediLog/selectEdiLogByID.do?ediId='+ul.ediId,
			$http.post(url)
			            .success(function(data){			           
						 $scope.fileName = data[0].fileName;
						 $scope.retransMission = data[0].retransMission;
						 $scope.retransMissionTime = data[0].retransMission;
						 $scope.ediId = data[0].ediId;
						 $scope.filePath = data[0].filePath;
					})		
		               .error(function(data){
		               alert("操作失败")
		               });
	};
	
	});
</script>