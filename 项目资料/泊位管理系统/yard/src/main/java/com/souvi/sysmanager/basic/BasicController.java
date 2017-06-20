package com.souvi.sysmanager.basic;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.souvi.common.StringUtil;
import com.souvi.sysmanager.basic.service.BasicService;
import com.souvi.sysmanager.user.entity.User;
/**
 * 基础类 放置公共代码
 * @author wq3426
 *
 */
public class BasicController {
	//返回结果对象，用于返回响应请求的JSON对象
	public JSONObject resultObj = new JSONObject();
	
	@Autowired
	private BasicService basicService;
	
	
	//响应请求返回的JSON对象
	public JSONObject setResultJSON(Integer result, String message, Object data){
		resultObj.put("result", result);//返回查询结果 1 请求成功 0 请求失败
		resultObj.put("msg", message);
		resultObj.put("data", data);
		
		return resultObj;
	}
	
	//响应请求返回的JSON对象
	public JSONObject setResultJSON(Integer result, String message){
		resultObj.put("result", result);//返回查询结果 1 请求成功 0 请求失败
		resultObj.put("msg", message);
		
		return resultObj;
	}
	
	/**
	 * 判断是否是系统用户，如果是，允许请求
	 * 
	 * @param request
	 * @return
	 */
	public boolean isSystemUser(HttpServletRequest request){
		boolean flag = false;
		
		try {
			String user_name = request.getParameter("user_name");
			
			if(!StringUtil.isNull(user_name)){
				User user = basicService.getUserWithUserName(user_name);
				if(user != null){
					flag = true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return flag;
	}
}
