<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>FLMS</title>
  </head>
 <script type="text/javascript">
 		window.location.href="<%=path%>/ssologin/userLoginSSO.do";
 </script>
  <body>
	

</body>
</html>
