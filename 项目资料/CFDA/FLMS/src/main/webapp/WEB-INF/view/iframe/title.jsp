<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="javax.servlet.http.HttpSession" %>
<%@ page language="java" import="com.souvi.sysmanager.user.entity.User" %>
<%@ page language="java" import="com.souvi.common.Constant" %>
<%
	User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
	String loginName = "";
	if (null != user) {
		loginName = user.getUserName();
	}
%>
	<!--header start here-->
	<div class="header-main">
		<div class="logo-w3-agile">
			<h1>
				<a href="javascript:void(0);"><img src="<%=path %>/resources/images/dhl_logo.gif">FLMS</a>
			</h1>
		</div>
		<div class="user-name pull-right">
			<p>
				<i class="fa fa-user"></i><span>&nbsp;<%=loginName %></span>
				<a href="#" class="red" onclick="logout()"><i class="fa fa-power-off"></i>&nbsp;退出</a>
			</p>

		</div>
		<div class="clearfix"></div>
	</div>
	<!--heder end here-->