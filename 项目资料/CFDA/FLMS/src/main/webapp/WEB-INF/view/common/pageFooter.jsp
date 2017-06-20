<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${!empty page && page.pageNum > 0}">

	<div><ul id='paginator' style="cursor: hand;"></ul></div>
	<script type="text/javascript">
		$(function() {
			var element = $('#paginator');//获得数据装配的位置
			//初始化所需数据
			var options = {
				bootstrapMajorVersion : 3,//版本号。3代表的是第三版本
				numberOfPages : 5, //显示页码数标个数
				currentPage : ${page.pageNum}, //当前页数
				totalPages : ${page.pages}, //总共的数据所需要的总页数
				itemTexts : function(type, page, current) {
					//图标的更改显示可以在这里修改。
					switch (type) {
						case "first":
							return "<<";
						case "prev":
							return "<";  
						case "next":  
							return ">";
						case "last":
							return ">>";
						case "page":
							return page;
					}
				},
				tooltipTitles : function(type, page, current) {
					//如果想要去掉页码数字上面的预览功能，则在此操作。例如：可以直接return。
					switch (type) {
						case "first":
							return "首页";
						case "prev":
							return "上一页";
						case "next":
							return "下一页";
						case "last":
							return "末页";
						case "page":
							return page;
					}
				},
				onPageClicked : function(e, originalEvent, type, page) {
					//单击当前页码触发的事件。若需要与后台发生交互事件可在此通过ajax操作。page为目标页数。
					$("#queryForm").attr("action",$("#queryForm").attr("action") + '?pageNum=' + page);
					$("#queryForm").submit();
				}
			};
			element.bootstrapPaginator(options); //进行初始化
		});
	</script>
</c:if>

