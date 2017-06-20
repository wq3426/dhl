package com.souvi;

import net.sf.json.JSONObject;

/**
 * 下面的示例按华为北向接口编写，其中parasJson是要设置的参数命令和值
	JSONObject parasJson = new JSONObject();
	parasJson.put("cmd", "101");
	parasJson.put("value", value);
	
	JSONObject commandJson = new JSONObject();
	commandJson.put("serviceId", "IsParked");
	commandJson.put("method", "SET_PARK_SENSOR_STRING");
	commandJson.put("paras", parasJson);
	
	JSONObject bodyJson = new JSONObject();
	bodyJson.put("requestId", requestId);
	bodyJson.put("command", commandJson);
	bodyJson.put("expireTime", 24*60*60);

	参数的内容主要有
	putCmd("LWCS", 124, 10, 60);//联网次数
	putCmd("CLFZ", 104, 0, 255);//测量阀值
	putCmd("SJSY", 112, 0, 255);//数据使用
	putCmd("CXFL", 113, 1, 3);//查询分类
	putCmd("JCZQ", 115, 1, 30);//检测周期
	putCmd("LXCS", 116, 1, 30);//连续次数
	putCmd("JSCS", 117, 1, 30);//接收次数
	putCmd("BFCS", 118, 0, 3);//补发次数
	putCmd("XTZQ", 119, 1, 255);//心跳周期
	putCmd("RFXT", 120, 5, 255);//射频心跳
	putCmd("XXHJ", 123, 0, 0);//学习环境
	putCmd("RSET", 199, 0, 0);//复位设备
 * 
 * 
 * @author wq3426
 *
 */
public class InterfaceDemo {

	private void doCommandRun() throws Exception{
		try{
			String imeiNo = (String)request.getAttribute("imeiNo");
			NbImei nbImei = NbImei.create(imeiNo);
			NbApp nbApp = NbApp.create(nbImei.getAppId());
	    	
			//112数据使用230/238   123初始化   117接收等待   119上报周期  113数据类    104测量阀   102计算阀
			String name = (String)request.getAttribute("cmd");
			String value = (String)request.getAttribute("value");
			String method = "SET_PARK_SENSOR_STRING";
			String requestId = Data.TIMEID()+"";
			
			//
			String url = nbApp.getHttps()+"://"+nbApp.getIotIp()+":"+nbApp.getIotPort()+"/iocm/app/cmd/v1.2.0/devices/"+nbImei.getDeviceId()+"/commands";//这个要用v1.2.0
			
			//
			List <NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new BasicNameValuePair("Content-Type","application/json"));
			headers.add(new BasicNameValuePair("app_key",nbApp.getAppId()));
			headers.add(new BasicNameValuePair("Authorization","bearer "+nbApp.getAccessToken()));
			
			//
			JSONObject parasJson = new JSONObject();
			if("CWBH".equals(name)){
				if(value.length()>9) throw new Exception("value length error");
				parasJson.put("cmd", "101");
				parasJson.put("value", value);
			}else{
				if(value==null) value="0";
				makeCmd(parasJson,name,Integer.parseInt(value));
			}
			
			System.out.println(parasJson.toString());
			
			JSONObject commandJson = new JSONObject();
		    commandJson.put("serviceId", "IsParked");
		    commandJson.put("method", method);
		    commandJson.put("paras", parasJson);
		    JSONObject bodyJson = new JSONObject();
		    bodyJson.put("requestId", requestId);
		    bodyJson.put("command", commandJson);
		    bodyJson.put("expireTime", 24*60*60);
		    
			//
			NbiotHttpClient httpClient = new NbiotHttpClient();
			httpClient.initSSLConfigForTwoWay();
			httpClient.doPost(url, headers, bodyJson, "200 OK");
			httpClient.close();
			
			//
			printSuccess();
			
		}catch(Exception e){
			e.printStackTrace();
			printString(e.getMessage());
		}
	}
	
	private void makeCmd(JSONObject cmdJson,String name,int value) throws Exception{
		JSONObject pJson = CMD.get(name);
		
		if(pJson==null) throw new Exception("no such cmd");
		
		if(value>pJson.getInt("max") || value<pJson.getInt("min")) throw new Exception("value limit error");
		
		cmdJson.put("cmd", pJson.getString("code"));
		cmdJson.put("value", value);
	}
	
}
