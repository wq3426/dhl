<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!-- Top -->
<%@ include file="/WEB-INF/view/iframe/top.jsp"%>
<div class="page-container">
	<!--/content-inner-->
	<div class="left-content">
		<div class="mother-grid-inner">
			<%@ include file="/WEB-INF/view/iframe/title.jsp"%>
			<ol class="breadcrumb">
				<li class="breadcrumb-item"><a>权限管理</a></li>
			</ol>
			<!--grid-->
			<div id="rightTree" class="ztree validation-form" style="float: left;width: 20%;height: 500px;padding: 10px;margin-right:5px;overflow: auto;">
      		</div>
			<div class="validation-system" style="float: left;width: 79%;">
				<div class="validation-form" style="height: 500px;">
					<form action="<%=path%>/right/rightManage.do"" method="post" id="commentForm">
						<div class="vali-form ">
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>权限名称:</label> 
									<input type="hidden" id="rightId" name="rightId">
									<input type="text" placeholder="权限名称" class="form-control" name="rightName" id="rightName" required="required">
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>权限代码:</label> 
									<input type="text" placeholder="权限代码" class="form-control" name="rightCode" id="rightCode" required>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>菜单路径:</label> 
									<input type="text" placeholder="菜单路径" class="form-control" name="path" id="path" required>
								</div>
								<div class="col-md-6 form-group2">
										<label class="control-label"><span style="color: red;">*</span>菜单类型:</label>
										<select name="rightType" id="rightType" class="form-control" required>
											<option value="">请选择</option>
											<option value="1" <c:if test="${rightType=='1'}">selected</c:if> >菜单</option>
											<option value="0" <c:if test="${rightType=='0'}">selected</c:if> >按钮</option>
				    					</select>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>父菜单:</label> 
									<select name="parentid" id="parentid" class="form-control" required>
										<option value="">请选择</option>
				                    	 	<c:forEach items="${rightList}" var="rightList">  
				                    	 		<c:if test="${rightList.rightType == '1'}">
				                       				<option value="${rightList.rightId}">${rightList.rightName}</option> 
				                       			</c:if>  
				                    		</c:forEach> 
				    				</select>
								</div>
								<div class="col-md-6 form-group2">
									<label class="control-label"><span style="color: red;">*</span>顺序号:</label> 
									<input type="text" placeholder="顺序号" class="form-control" name="orderNum" id="orderNum" required>
								</div>
							</div>
							<div class="row">
								<div class="col-md-6 form-group1">
									<label class="control-label"><span style="color: red;">*</span>权限状态:</label> 
									<select id="rightStatus" name="rightStatus" class="form-control" required>
										<option value="">请选择</option>
				    					<option value="Y" <c:if test="${rightStatus=='Y'}">selected</c:if> >启用</option>
				    					<option value="N" <c:if test="${rightStatus=='N'}">selected</c:if> >禁用</option>
				    				</select>
								</div>
								<div class="col-md-6 form-group1">
									<label class="control-label">描述:</label> 
									<textarea name="remark" id="remark" class="form-control"></textarea>
								</div>
							</div>
						</div>
					</form>
					<defined:isright rightCode="right_update">
						<div class="row" id="toADD">
								<div class="col-md-6 col-sm-offset-3 form-group" style ="text-align: center;">
									<button type="button" onclick="rightManage()" class="btn btn-primary">添加</button>
								</div>
						</div>
						<div class="row" id="toINSERT" style="display: none;">
								<div class="col-md-6 col-sm-offset-3 form-group" style ="text-align: center;">
									<button type="button" onclick="rightManage()" class="btn btn-primary">更新</button>
									<button type="reset" onclick="toADD()" class="btn btn-primary">取消</button>
								</div>
						</div>
					</defined:isright>
				</div>
				
				
				<!--//content-inner-->
				<%@ include file="/WEB-INF/view/iframe/menu.jsp"%>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/view/iframe/bottom.jsp"%>
 <script type="text/javascript">
 var leftRightTree = ${rightTree};
	$(function(){
		function zTreeOnClick(event, treeId, treeNode) {
		 $('#commentForm')[0].reset();
		 if(treeNode.level==0){
			return;
		 }
		 $("#toINSERT").show();
		 $("#toADD").hide();
		 $.post("<%=path%>/right/rightInfo.do", {
					rightId : treeNode.id
				}, function(data) {
					if(!data){
						return;
					}
					  $("#commentForm input[name=rightId]").val(data.rightId);
					  $("#commentForm input[name=rightName]").val(data.rightName);
					  $("#commentForm input[name=rightCode]").val(data.rightCode);
					  $("#commentForm input[name=path]").val(data.path);
					  $("#commentForm select[name=rightType]").val(data.rightType);
					  $("#commentForm select[name=parentid]").val(data.parentid);
					  $("#commentForm input[name=orderNum]").val(data.orderNum);
					  $("#commentForm select[name=rightStatus]").val(data.rightStatus);
					  $("#commentForm textarea[name=remark]").val(data.remark);
					  $("#commentForm").valid()
				});
			};
			var setting = {
				callback : {
					onClick : zTreeOnClick
				},
				view: {
					selectedMulti: false,
					showIcon : true,
					showLine : true
				},
				data: {
					simpleData: {
						enable: true
					}
				}
			};
			var rightTree = $.fn.zTree.init($("#rightTree"), setting, leftRightTree);
	});
	$(document).ready(function(){
			$("#commentForm").validate({    
				rules:{
					//customerName:"required",
					rightName:"required",
					rightCode:"required",
					path:"required",
					rightType:"required",
					parentid:"required",
					orderNum:"digits",
					rightStatus:"required",
				},
				messages:{
					rightName:"权限名称不能为空",
					rightCode:"权限代码不能为空",
					path:"菜单路径不能为空",
					rightType:"请选择菜单类型",
					parentid:"请选择父菜单",
					orderNum:"请输入数字",
					rightStatus:"请选择状态",
				}
			});
			
		});
  function rightManage(){
 		//校验表单内容
		if($("#commentForm").valid()){
			var rightId = $("#rightId").val();
			var rightName = $("#rightName").val();
  			var rightCode = $("#rightCode").val();
			 $.ajax({
		        type:"post",
		        url:"<%=path%>/right/checkRightManage.do",
		        data:{ 
		        		rightId:rightId,
						rightName:rightName,
						rightCode:rightCode
					},
		        success:function(msg){
			         if(msg==true){
				           $.ajax({
						        type:"post",
						        url:"<%=path%>/right/rightManage.do",
						        data:$("form").serialize(),
						        dataType:"json",
						        success:function(msg){
							         if(msg==true){
							           alert("成功");
							           location.href="<%=path%>/right/rightList.do";
							         }else{
							         	alert("失败");
							         }
						        }
						  	 });
			         }else{
			         	alert("权限名称，权限代码重复");
			         }
		        }
		  	 });
		}
   }
   function toADD(){
   		location.href="<%=path%>/right/rightList.do";
   }
   function deleteRight(){
     var rightId = $("#rightId").val();
   	 location.href="<%=path%>/right/deleteRight.do?rightId="+rightId;
   }
   function toRightList(){
     location.href="<%=path%>/right/rightList.do";
   }

  </script>