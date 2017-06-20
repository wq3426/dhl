package com.souvi.sysmanager.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.souvi.common.Constant;
import com.souvi.common.StringUtil;
import com.souvi.sysmanager.user.entity.User;

public class LoginInterceptor implements HandlerInterceptor{
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
	
	private final static String SYSTEM = "System";
	private final static String SOURCEIP = "SourceIP";
	private final static String SESSION_USERID = "UserId";
	private final static String SESSION_USERNAME = "UserName";
	
	/** 
     * Handler执行完成之后调用这个方法 
     */  
    public void afterCompletion(HttpServletRequest request,  HttpServletResponse response, Object handler, Exception exc)  
            throws Exception {  
    	// 删除
    	MDC.remove(SYSTEM);
    	MDC.remove(SOURCEIP);
    	MDC.remove(SESSION_USERID);
    	MDC.remove(SESSION_USERNAME);
    }  
  
    /** 
     * Handler执行之后，ModelAndView返回之前调用这个方法 
     */  
    public void postHandle(HttpServletRequest request, HttpServletResponse response,  
            Object handler, ModelAndView modelAndView) throws Exception {  
    }  
  
    /** 
     * Handler执行之前调用这个方法 
     */  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
        //获取请求的URL  
        //String url = request.getRequestURI();  
        //URL:login.jsp是公开的;这个demo是除了login.jsp是可以公开访问的，其它的URL都进行拦截控制  
        /*if(url.indexOf("/asset/login/toLogin.do")>=0 || url.indexOf("/asset/login/checkingUser.do")>=0 || url.indexOf("/asset/login/userLogin.do")>=0){  
            return true;  
        } */ 
    	String user_name = request.getParameter("user_name");
    	
    	if(!StringUtil.isNull(user_name)){//用户名不为空，说明是app移动端发送请求,拦截器不过滤该请求，直接在业务类中作判断
    		return true;
    	}
    	
        //获取Session  
        HttpSession session = request.getSession();  
        User user =  (User) session.getAttribute(Constant.USER_SESSION_NAME); 
        if (null != user) {
        	MDC.put(SYSTEM, "YARD");
        	MDC.put(SOURCEIP, getRemortIP(request));
        	MDC.put(SESSION_USERID, user.getUser_id());
        	MDC.put(SESSION_USERNAME, user.getUser_name());
        	return true;  
    	}
        
//        Assertion assertion = AssertionHolder.getAssertion();
//        //不符合条件的，跳转到登录界面  
        request.getRequestDispatcher("/html/Login.html").forward(request, response); 
//        if(null!=assertion){
//            return true;
//        }
        return false;  
    }  
    
    
    //获得客户端IP地址的方法：
    public String getRemortIP(HttpServletRequest request) {  
        if (request.getHeader("x-forwarded-for") == null) {  
            return request.getRemoteAddr();  
        }  
        return request.getHeader("x-forwarded-for");  
    } 
    /*//获得客户端IP地址的方法：
    public String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
		} 
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		    ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
		} 
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }*/
}
