package com.souvi.sysmanager.login.controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.souvi.common.Constant;
import com.souvi.common.MessageUtil;
import com.souvi.common.RSA;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.user.entity.User;
import com.souvi.sysmanager.user.service.userService;

@Controller
@RequestMapping("ssologin")
public class SSOLoginController {
	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Autowired 
	private userService userService;
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("toLogin")
	public String toLogin(HttpServletRequest request){
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION_NAME);
		if(null!=user)
			return "index";
		else
			return "sysmanager/login/ssoLogin";
	}
	/**
	 * 验证用户
	 * @throws IOException 
	 */
	@RequestMapping("checkingUser")
	@ResponseBody
	public String checkingUser(HttpServletRequest request,HttpServletResponse response,String userName,String password) throws IOException{
		String result="";
		try{
			password=new RSA().encode(password);
			User user = this.userService.getUserOnLogin(userName, password);
		
			if(null==user){
				result="Non-existent";
			}else if(Constant.STATUS_N.equalsIgnoreCase(user.getStatus())){
				result="Forbidden";
			}else{
				result=user.getUserId();
			}
		}catch(Exception e){
			result="N";
			log.error("checkingUser Error userName="+userName,e);
		}
		return result;
	}
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("userLoginSSO")
	public String userLogin(HttpServletRequest request,HttpServletResponse response){
		try{
			Assertion assertion = AssertionHolder.getAssertion();
		    AttributePrincipal ap = assertion.getPrincipal();
		    
		    String mappingId = (String) ap.getAttributes().get("userid");
			User user = this.userService.getUserByMppingID(mappingId);
			if(null!=user){
				List<Role> userAssRole = this.userService.getUserAssRole(user.getUserId());
				HttpSession session = request.getSession(true);
				session.setAttribute(Constant.USER_SESSION_NAME, user);
				session.setAttribute(Constant.USER_SESSION_ROLE, userAssRole);
			}
			log.info(MessageUtil.MessageUtil("登录","用户登录成功", user));
		}catch(Exception e){
			log.error("用户登录错误",e);
		}
		return "index";
	}
	/**
	 * 退出
	 * @throws Exception 
	 */
	@RequestMapping("logOut")
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
		session.invalidate();
		log.info(MessageUtil.MessageUtil("登录","用户退出", user));
		return "sysmanager/login/ssoLoginOut";
	}
}
