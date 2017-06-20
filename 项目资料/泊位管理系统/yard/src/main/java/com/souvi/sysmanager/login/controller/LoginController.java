package com.souvi.sysmanager.login.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.souvi.common.Constant;
import com.souvi.common.MessageUtil;
import com.souvi.common.RSA;
import com.souvi.sysmanager.basic.BasicController;
import com.souvi.sysmanager.user.entity.User;
import com.souvi.sysmanager.user.service.userService;

@Controller
@RequestMapping("/login")
public class LoginController extends BasicController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired 
	private userService userService;
	
	//用户登录
	@RequestMapping(value="/userLogin", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject userLogin(HttpServletRequest request, 
			@RequestParam(required=true) String user_name, @RequestParam(required=true) String password){
		
		try {
			User user = userService.getUserByUsernameAndPassword(user_name, password);
			if(user != null){
				HttpSession session = request.getSession(true);
				session.setAttribute(Constant.USER_SESSION_NAME, user);
				
				setResultJSON(1, "", net.sf.json.JSONObject.fromObject(user));
				
				logger.info(MessageUtil.MessageUtil("登录","登录成功", user));
			} else{
				setResultJSON(0, "用户名或密码错误，登录失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("用户登录错误",e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 跳转到登录界面
	 * @return
	 */
	@RequestMapping("toLogin")
	public String toLogin(HttpServletRequest request){
		HttpSession session=request.getSession();
		User user = (User)session.getAttribute(Constant.USER_SESSION_NAME);
		if(null!=user)
			return "BerthManagement";
		else
			return "Login";
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
		logger.info(MessageUtil.MessageUtil("登录","用户退出", user));
		
		return "Login";
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
			}else if(Constant.STATUS_N == user.getUser_status()){
				result="Forbidden";
			}else{
				result=user.getUser_id();
			}
		}catch(Exception e){
			result="N";
			logger.error("checkingUser Error userName="+userName,e);
		}
		return result;
	}
	/**
	 * 登录
	 * @return
	 */
//	@RequestMapping("userLogin")
//	public String userLogin(HttpServletRequest request,HttpServletResponse response,String userId){
//		try{
//			User user = this.userService.getUserByID(userId);
//			List<Role> userAssRole = this.userService.getUserAssRole(userId);
//			HttpSession session = request.getSession(true);
//			session.setAttribute(Constant.USER_SESSION_NAME, user);
//			session.setAttribute(Constant.USER_SESSION_ROLE, userAssRole);
//			logger.info(MessageUtil.MessageUtil("登录","登录成功", user));
//		}catch(Exception e){
//			logger.error("用户登录错误",e);
//		}
//		return "index";
//	}

}
