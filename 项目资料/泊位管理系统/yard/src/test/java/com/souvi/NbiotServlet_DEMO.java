package prj.nbiot;  

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;  
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  

import pub.Pub;

import base.db.Database;
import base.db.XSet;
import base.util.Data;
import base.util.XDate;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import tb.nb.NbSeat;
import net.sf.json.JSONObject;

public class NbiotServlet_DEMO extends HttpServlet{  
    
	@Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
    }  
  
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
    	try{
	    	System.out.println("post ......");
	        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	        String line = null;
	        StringBuilder sb = new StringBuilder();
	        while((line = br.readLine())!=null){
	            sb.append(line);
	        }
	        String reqBody = sb.toString();
	        System.out.println(reqBody);
	        //
	        JSONObject json = JSONObject.fromObject(reqBody); 
	        String notifyType = json.getString("notifyType");
	        if("deviceDataChanged".equals(notifyType)){//以下处理车辆进出的数据
//		        String deviceId = json.getString("deviceId");//考虑不使用？？？
		        JSONObject serviceJson = json.getJSONObject("service");
		        String eventTime = serviceJson.getString("eventTime");
		        eventTime = eventTime.replace("T","").replace("Z","");
		        Date iotTime = XDate.makeTime(eventTime, "yyyyMMddHHmmss");
		        JSONObject dataJson = serviceJson.getJSONObject("data");
		        int battery = dataJson.getInt("battery");
		        int signal = dataJson.getInt("signal");
		        int temperature = dataJson.getInt("temperature");//
		        int heart1 = dataJson.getInt("heart1");
		        int heart2 = dataJson.getInt("heart2");
		        int version = dataJson.getInt("version");
		        byte[] bBytes = decodeBase64(dataJson.getString("bytes"));
		        String sBytes = makeHex(bBytes);
		        dataJson.remove("bytes");//日志记录解析出来后的
		        dataJson.put("bytes", sBytes);
		        //
		        Date serverTime = new Date(System.currentTimeMillis()/1000*1000);
		        String imeiNo = bytesToStr(bBytes,2,16);
		        int parked = bBytes[106];
		        if(parked!=0 && parked!=1) {//针对状态2的情况，可能重启了
			        writeToFile(XDate.makeTime(serverTime,"yyyy-MM-dd HH:mm:ss")+" "+imeiNo+" "+json.toString(),"/log","parked2.txt");
		        	return;
		        }
		        String hasCar = parked>0 ? "Y" : "N";
		        Date sensorTime = new Date(Pub.makeTime(this.getTime(copyBytes(bBytes,79,84)),"yyMMddHHmmss"));
		        Date sensorInoutTime = new Date(Pub.makeTime(this.getTime(copyBytes(bBytes,100,105)),"yyMMddHHmmss"));
		        //
		        Database db = Database.getDbFromThread();
		        XSet seatSet = db.get("SELECT * FROM nb_seat WHERE imei_no=?",imeiNo);
		        if(seatSet.getRowCount()<1){//针对无车位映射的
		        	writeToFile(XDate.makeTime(serverTime,"yyyy-MM-dd HH:mm:ss")+" "+imeiNo+" "+json.toString(),"/log","noseat.txt");
		        	return;
		        }
		        int seatNo = seatSet.getInteger("seat_no",null);
		      	long freshTime0 = seatSet.getDatetime("fresh_time", serverTime).getTime();
		      	int iotTimeDiff = seatSet.getInteger("iot_time_diff",0);//8*3600*1000=28800000
		      	iotTime = new Date(iotTime.getTime()+iotTimeDiff);//纠正时差
		        //
		        XSet parkSet = db.get("SELECT TOP 1 * FROM nb_park WHERE seat_no=? ORDER BY park_no DESC",seatNo);
		        Date outTime = parkSet.getDatetime("out_time",null);
		        boolean isInOrOut = false;
		        long parkStamp = 0;
		        //记录车辆进出的流水
		        if(parkSet.getRowCount()>0 && null==outTime){//有车  
		        	if("N".equals(hasCar)){
		        		long parkNo = parkSet.getLong("park_no",null);
		        		long lParkTime0 = parkSet.getDatetime("park_time",null).getTime();
		        		int parkMiao = (int)(serverTime.getTime() - lParkTime0)/1000;	//按IOT时间为停车时间
		        		long outNo = Data.TIMEID();
		        		parkStamp = outNo;
		        		db.run("UPDATE nb_park SET out_no=?,out_time=?,park_miao=?,sensor_time2=?,iot_time2=?,server_time2=? WHERE park_no=?",outNo,serverTime,parkMiao,sensorInoutTime,iotTime,serverTime,parkNo);
		        		isInOrOut = true;
		        	}
		        }else{//无车
		        	if("Y".equals(hasCar)){
		        		long parkNo = Data.TIMEID();
		        		parkStamp = parkNo;
		        		db.run("INSERT INTO nb_park (seat_no,park_no,park_time,sensor_time1,iot_time1,server_time1) values (?,?,?,?,?,?)",seatNo,parkNo,serverTime,sensorInoutTime,iotTime,serverTime);
		        		isInOrOut = true;
		        	}
		        }
		        NbSeat nbSeat = new NbSeat();//修改车位状态
		        nbSeat.valFreshTime(serverTime);
		        nbSeat.valIsParked(hasCar);
		        if(isInOrOut){
		        	nbSeat.valParkTime(serverTime);
		        	nbSeat.valParkStamp(parkStamp);
		        	nbSeat.valSensorTime(sensorInoutTime); 
		        	nbSeat.valIotTime(iotTime);
		        	nbSeat.valServerTime(serverTime);
		        }
		        nbSeat.valBattery(battery);
		        nbSeat.valSignal(signal);
		        nbSeat.valTemperature(temperature);
		        nbSeat.valHeart1(heart1);
		        nbSeat.valHeart2(heart2);
		        nbSeat.valVer(version);
		        nbSeat.valBytes(sBytes);
		        nbSeat.keySeatNo(seatNo);
		        nbSeat.update();
		        //
		        long period = (serverTime.getTime() - freshTime0) / 1000;
		        long delay2 = (iotTime.getTime() - sensorTime.getTime() /*+ 8*3600*1000*/) / 1000;
		        long delay3 = (serverTime.getTime() - iotTime.getTime() /*- 8*3600*1000*/) / 1000;
		        writeToFile(XDate.makeTime(serverTime,"yyyy-MM-dd HH:mm:ss")+" "+period+" "+delay2+" "+delay3+" "+json.toString(),"/log",seatNo+"_"+XDate.getDate()+".txt");
	        }else if ("commandRsp".equals(notifyType)){
	        	JSONObject headerJson = json.getJSONObject("header");
	        	System.out.println("requestId:"+headerJson.getInt("requestId"));
	        	System.out.println("deviceId:"+headerJson.getInt("deviceId"));
	        	System.out.println("method:"+headerJson.getInt("method"));
	        	//
	        	JSONObject bodyJson = json.getJSONObject("body");
	        	System.out.println("errcode:"+headerJson.getInt("errcode"));
	        }
	        //应答
	//    	JSONObject json = new JSONObject();
	//    	json.put("ans", "xxx");
	//		PrintWriter out = response.getWriter();
	//		out.print(json.toString());
	//		out.flush();
	//		out.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }  
    
    public static byte[] decodeBase64(String s) throws Exception{
    	return new BASE64Decoder().decodeBuffer(s);  
    }

    public static String encodeBase64(byte[] b) throws Exception{
    	return new BASE64Encoder().encode(b);  
    }
    
	//16进制打印
	public static String makeHex(byte[] b){ 
		String str = "";
		for (int i=0; i<b.length; i++) { 
			String hex = Integer.toHexString(b[i] & 0xFF); 
			if(hex.length()==1) hex = '0'+hex; 
			str += hex.toUpperCase();
		} 
		return str;
	} 
    
	public static void printHex(byte[] b){ 
		for (int i=0; i<b.length; i++) { 
			String hex = Integer.toHexString(b[i] & 0xFF); 
			if(hex.length()==1) hex = '0'+hex; 
			System.out.print(hex.toUpperCase()); 
		} 
	} 
	
	public static void printHexLn(byte[] b) throws Exception{ 
		printHex(b);
		System.out.println();
	}
	
    //即时日志
    //filePath 形如 /log  相对于应用的目录
	public static void writeToFile(String logStr,String logPath,String logName){
		try{
			BufferedWriter idWriter = null;
			try{
				String fullPath = "d:/"+logPath;
				String fullPathName = fullPath + "/" + logName;
				idWriter = new BufferedWriter(new FileWriter(fullPathName,true));
				idWriter.write(logStr+"\n");
				idWriter.flush();
			}finally{
				if(idWriter!=null) idWriter.close();
			}
		}catch(Exception e){
			System.out.println("Logger.writeToFile "+e.getMessage());
		}
	}
	
	//含 from to
	public static byte[] copyBytes(byte[] srcBytes,int from,int to){
		int len = to-from+1;
		byte[] b = new byte[len];
		System.arraycopy(srcBytes, from, b, 0, len);
		return b;
	}

    public String getTime(byte[] bb){
    	return String.format("%02d%02d%02d%02d%02d%02d",bb[0],bb[1],bb[2],bb[3],bb[4],bb[5]);
    }

	public static String bytesToStr(byte[] bb,int from,int to){
		byte[] b = new byte[to-from+1];
		System.arraycopy(bb,from,b,0,to-from+1);
		return new String(b).trim();
	}

}
