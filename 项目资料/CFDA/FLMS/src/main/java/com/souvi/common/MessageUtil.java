package com.souvi.common;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.souvi.sysmanager.user.entity.User;

public class MessageUtil {

	public  MessageUtil(){
		
	}
	public static String MessageUtil(List list){
		return JSON.toJSONString(list);
	}
	public static String MessageUtil(String title,String opcode,List list) throws Exception{
		JSONArray jsonArray= (JSONArray) JSON.toJSON(list);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject obj = (JSONObject) jsonArray.get(i);
			obj.remove("password");
		}
		
		JSONObject json = new JSONObject();
		json.put("op", title);
		json.put("opdata", jsonArray);
		return json.toString();
	}
	public static String MessageUtil(String title,String opcode,Object ob) throws Exception{
		JSONObject jsonObj= (JSONObject) JSON.toJSON(ob);
		jsonObj.remove("password");
		
		JSONObject json = new JSONObject();
		json.put("opcode",opcode);
		json.put("category", title);
		json.put("opdata", jsonObj);
		return json.toString();
	}
	public static String MessageUtil(String title,String opcode,String ob) throws Exception{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", ob);
		JSONObject json = new JSONObject();
		json.put("opcode",opcode);
		json.put("category", title);
		json.put("opdata", jsonObj);
		return json.toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setCname("s");
		user.setPassword("123");
		User user2 = new User();
		user2.setCname("s1111");
		user2.setPassword("123111");
		List list = new ArrayList();
		list.add(user);
		list.add(user2);
		try {
			System.out.println(MessageUtil("固定资产","添加","11111111111"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
