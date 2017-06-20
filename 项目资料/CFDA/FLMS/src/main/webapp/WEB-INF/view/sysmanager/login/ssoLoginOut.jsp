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
	 <script type="text/javascript">
		window.location.href="http://10.210.16.4:8080/cas/logout?service=http://10.210.16.41:8097/asset";
    </script>
  </head>
  <body>
</body>
</html>
