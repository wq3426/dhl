<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../common/CommonTaglib.jsp"%>

<%
	String path = request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>FLMS</title>
		<%@ include file="../common/CommonMeta.jsp"%>
		<%@ include file="../common/CommonCss.jsp"%>
		
		<script type=text/javascript><!--//--><![CDATA[//><!--
		function logout(){
			//window.top.location.href='<%=path%>/ssologin/logOut.do';
			window.top.location.href='<%=path%>/login/logOut.do';
		}
//--><!]]></script>
	</head>
	<body>
		