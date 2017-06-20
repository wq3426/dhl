package com.souvi.sysmanager.ediLog.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.ExportExcelUtil;
import com.souvi.common.GlobalProperties;
import com.souvi.common.MessageUtil;
import com.souvi.common.SessionUser;
import com.souvi.common.StringUtil;
import com.souvi.sysmanager.ediLog.entity.EdiLog;
import com.souvi.sysmanager.ediLog.service.EdiLogService;
@Controller
@RequestMapping("ediLog")
public class EdiLogController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EdiLogService ediLogService;

	@RequestMapping("toediLogList")
	public String toediLogList() {
		return "sysmanager/ediLog/ediLogList";
	}

	/*
	 * edi日志列表
	 */
	@RequestMapping("ediLogList")
	@ResponseBody
	public PageInfo<EdiLog> ediLogList(HttpServletRequest request, EdiLog elg,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count) {
		PageHelper.startPage(page, count);
		List<EdiLog> elgList = ediLogService.getediLogList(elg);
		PageInfo<EdiLog> pageInfo = new PageInfo<EdiLog>(elgList);
		return pageInfo;
	}

 
	// 文件下载
	@RequestMapping(value="/fileDownload", produces = "application/json; charset=utf-8")
	@ResponseBody
	public String fileDownload(HttpServletRequest request, String filePath,
			HttpServletResponse response) {
		//response.setContentType("text/html;charset=UTF-8");
		String  flag=null;
		try {
			String filePaths = GlobalProperties.EDILOG_IMPORT_SRCFILE_PATH + "\\"
					+ filePath;
			File file = new File(filePaths);
			if (file.exists()) {
				ExportExcelUtil export = new ExportExcelUtil();
				export.downLoadFile(request,response,filePaths);
			}else{
				flag="文件不存在";
			}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				flag="EdiLog日志信息下载时存在异常"+e.getMessage();
				logger.error("EdiLog日志信息下载时存在异常：", e);
			}
		

		return flag;
	}
	//重发
	@RequestMapping(value="/retransmission", produces = "application/json; charset=utf-8")
	@ResponseBody
	public int retransmission(HttpServletRequest request,String ediId,String repeatedCause,String filePath,
			HttpServletResponse response) throws UnsupportedEncodingException {
			request.setCharacterEncoding("utf-8") ;
			 int flag=0;
			String retransMission=SessionUser.getLoginUserName(request);
			Date retransMissionTime=new Date();
			String strsrcfile = GlobalProperties.EDILOG_IMPORT_SRCFILE_PATH + "\\"
					+ filePath;
			String strdestfile =GlobalProperties.EDILOG_IMPORT_DESTFILE_PATH ;
				
			StringUtil SUl=new StringUtil();
			try {
				File srcfile=new File(strsrcfile);
				if (srcfile.exists()) {
					SUl.copy(strsrcfile,strdestfile,filePath);
					ediLogService.updataEdiLog(ediId,repeatedCause,retransMission,retransMissionTime);
					flag=2;
				}else{
					flag=3;
				}
			logger.info(MessageUtil.MessageUtil("EdiLog日志查询", "重发", filePath));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("EdiLog日志信息重发时存在异常：", e);
			flag=3;
		}
		return flag;
		
	}
	public void copyFile(String oldPath, String newPath) { 
		try { 
		int bytesum = 0; 
		int byteread = 0; 
		File oldfile = new File(oldPath); 
		if (oldfile.exists()) { //文件存在时 
		InputStream inStream = new FileInputStream(oldPath); //读入原文件 
		FileOutputStream fs = new FileOutputStream(newPath); 
		byte[] buffer = new byte[1444]; 
		int length; 
		while ( (byteread = inStream.read(buffer)) != -1) { 
		bytesum += byteread; //字节数 文件大小 
		System.out.println(bytesum); 
		fs.write(buffer, 0, byteread); 
		} 
		inStream.close(); 
		} 
		} 
		catch (Exception e) { 
		System.out.println("复制单个文件操作出错"); 
		e.printStackTrace(); 

		} 

		} 
	
	
	@RequestMapping("selectEdiLogByID")
	@ResponseBody
	public List<EdiLog> selectEdiLogByID(HttpServletRequest request,String ediId,
			HttpServletResponse response) {
		List<EdiLog> edilist=ediLogService.selectEdiLogByID(ediId);
		
				return edilist;
		
	}
	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

}
