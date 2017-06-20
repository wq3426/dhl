package com.souvi.task.ediTextTask.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.csvreader.CsvReader;
import com.souvi.common.GlobalProperties;
import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.task.ediTextTask.service.EdiTextTaskService;

public class ediTextTask {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EdiTextTaskService ediTextTaskService;
	/**  
     * 业务逻辑处理  
     */  
    public void execute() {  
        // 执行业务逻辑  
        // ........  
        System.out.println("定时任务......."); 
        logger.info("开始批量解析文本格式Order Event Inbound EDI文件! 目录:"+GlobalProperties.EDITEXTTASK_IMPORT_SRCFILE_PATH);
	    File[] eventInboundFile = new File(GlobalProperties.EDITEXTTASK_IMPORT_SRCFILE_PATH).listFiles();
	    ediTextTaskUtil.sortFiles(eventInboundFile);
	    parserOrderInboundTXT(eventInboundFile);
	    logger.info("结束批量解析文本格式Order Event Inbound EDI文件!");
	    
	    


    }  
    private void parserOrderInboundTXT(File[] files) {
    	
		if(null==files) return ;
		logger.info("文件数： "+files.length);
		for(int i=0;i<files.length;i++){
			logger.info("文件名称："+files[i].getName());
			//文件拓展名

			String _name = files[i].getName().length()>3 ? files[i].getName().substring(files[i].getName().length()-3, files[i].getName().length()) :"";
			if(!"txt".equalsIgnoreCase(_name)&& !"csv".equalsIgnoreCase(_name)){
				continue;
			}
			List<Product> Prolist = new ArrayList<Product>();
			List<ProductUpdate> ProlistUpdate = new ArrayList<ProductUpdate>();
			String s=null;
			String message="";
			String[] str;
			CsvReader readfile = null;
			try {
				readfile =new CsvReader(files[i].getAbsolutePath(),'|',Charset.forName("UTF-8"));
//				InputStreamReader read = new InputStreamReader(new FileInputStream(files[i].getAbsolutePath()) , "utf-8");
//				readfile=new  BufferedReader(read);
				readfile.readHeaders();
				 while (readfile.readRecord()) {
					 
				 }
				 readfile.close();
				if(null != readfile){
					str=message.split("ORDERHEADER|ORDERDETAIL");
				
					ediTextTaskUtil etl=new ediTextTaskUtil();
					Prolist=etl.ediTextSplit(str);
					//ediTextTaskService.setproductLicense(ple);					
					for(int j=0;j<=Prolist.size();j++){
						ProjectManage project= ediTextTaskService.getprojectname(Prolist.get(j).getProjectName());
						ProductType productType=ediTextTaskService.setproductname(Prolist.get(j).getProductType());
						System.out.println(productType);
						System.out.println(project.getProjectId());
						if(null!=productType&&null!=project){
							Product product=ediTextTaskService.getproductmaterial(Prolist.get(j).getMaterial());
							if(null!=product.getProductId()){
								ProlistUpdate=etl.ediTextSplitProductUpdate(str);
								ediTextTaskService.setproductUpdate(ProlistUpdate.get(j));
							}else{
								
								ediTextTaskService.setproductLicense(Prolist.get(j));
							}
							
							
						}
						
					}
				}
			} catch (Exception  e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			try {
//				readfile =new CsvReader(files[i].getAbsolutePath(),'|',Charset.forName("UTF-8"));
//				//判断文件是否为有效的xml文件
//				if(null != readfile){
//					logger.info("开始解析订单信息!");
//					List<EDITextDTO>  orderList = ediTextImpl.parserOrderXml(readfile);
//					
//					logger.info("订单信息解析完成!");
//					logger.info("验证订单信息!");
//					String messagestr = saveOrderInbound(orderList);
//					if(messagestr.indexOf("Success")>-1 || messagestr.indexOf("Update Carrier")>-1){
//						logger.info("插入数据库成功!文件名："+" "+files[i].getName());
//						//记录日志
//						this.ediTextImpl.saveOperateLog(EPodConstants.OPERATETYPE_IMPORT, "Order Inbound EDI 解析XML", 
//								"", ">文件："+files[i].getName()+" ; "+messagestr, 0, "");
//						remove(files[i],new File(EPodProperties.getProperties("PRODUCESHIPMENTTEXTSUCCESS")));
//					}else{
//						log.info("插入数据库失败!文件名："+messagestr+" "+files[i].getName());
//						//记录日志
//						this.ediTextImpl.saveOperateLog(EPodConstants.OPERATETYPE_IMPORT, "Order Inbound 解析XML", 
//								"", ">文件："+files[i].getName()+" ; "+messagestr, 1, "");
//						remove(files[i],new File(EPodProperties.getProperties("PRODUCESHIPMENTTEXTFAILED")));
//					}
//				}
//			}catch (FileNotFoundException e) {
//				message = "Error:解析文件失败，文件不存在！";
//				logger.error(message);
//				logger.error(e.getStackTrace(),e);
//			}catch (IOException e) {
//				message = "Error:解析文件失败，文件读取错误！";
//				logger.info(message);
//				logger.error(e.getStackTrace(),e);
//			}catch (ArrayIndexOutOfBoundsException e) {
//				message = "Error:解析文件失败，文件不符合规范！";
//				logger.info(message);
//				logger.error(e.getStackTrace(),e);
//			}catch(NumberFormatException e){
//				logger.info(message);
//				message = "Error:解析文件失败,数字转换失败！"+e.getLocalizedMessage();
//				logger.error(e.getStackTrace(),e);
//			}catch(EPodBusinessException e){
//				message = "Error:"+e.getMessage();
//				logger.info(message);
//				logger.error(e.getStackTrace(),e);
//			}catch (Exception e) {
//				message = "Error:解析文件失败！";
//				logger.info(message);
//				logger.error(e.getStackTrace(),e);
//			}finally {
//				if (readfile != null) {
//					readfile.close();
//				}
//				if(!"".equalsIgnoreCase(message)){
//					this.ediTextImpl.saveOperateLog(EPodConstants.OPERATETYPE_IMPORT, "Order Inbound 解析XML", 
//							"", ">文件："+files[i].getName()+"; "+message,1, "");
//					remove(files[i],new File(EPodProperties.getProperties("PRODUCESHIPMENTTEXTFAILED")));
				}
			}
		//}
	}
//}
