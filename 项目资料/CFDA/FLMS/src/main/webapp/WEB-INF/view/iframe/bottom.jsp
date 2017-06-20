<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/CommonJS.jsp"%>
<script type="text/javascript">

	function showadvquerybar(){
		if($("#advqueryDiv").is(":hidden")){
			$("#advSearch").removeClass().addClass("glyphicon glyphicon-chevron-up");
			$("#advqueryDiv").show();
		}else{
			$("#advSearch").removeClass().addClass("glyphicon glyphicon-chevron-down");
			$("#advqueryDiv").hide();
		}
		
	}
	function selectAll() {
		var checktoID = document.getElementById("checkAll");
		var toss = document.getElementsByName("checkNode");
		for(var i = 0 ; i < toss.length ; i ++){
			toss[i].checked = checktoID.checked;
		}
		checktoID.blur();
	}
	var toggle = true;
	$(".sidebar-icon").click(function() {
		if (toggle) {
			$(".page-container").addClass("sidebar-collapsed")
					.removeClass("sidebar-collapsed-back");
			$("#menu span").css({
				"position" : "absolute"
			});
		} else {
			$(".page-container").removeClass("sidebar-collapsed")
					.addClass("sidebar-collapsed-back");
			setTimeout(function() {
				$("#menu span").css({
					"position" : "relative"
				});
			}, 400);
		}

		toggle = !toggle;
	});
	
	$(function(){
	 $('[data-toggle="tooltip"]').tooltip();
		$(".datepicker").datepicker({
		    language:  'zh-CN',
		    format: 'yyyy-mm-dd',
		    todayBtn:  1,
		    autoclose: 1
		});
		$(".datetimepicker").datetimepicker({
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        forceParse: 0,
	        format: 'yyyy-mm-dd hh:ii:ss'
	    });
	});
</script>
<!-- Bottom -->
 </body>
</html>