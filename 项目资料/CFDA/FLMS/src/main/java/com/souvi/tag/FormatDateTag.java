package com.souvi.tag;


import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

/**
 * 日期时间格式化标签
 *
 */
public class FormatDateTag extends TagSupport {

	private Object value;
	private String pattern;
	
	//标签开始时调用的处理方法
	public int doStartTag(){
		try{
			if(null != value && "" != value){
				String timeValue = value.toString();
				if(timeValue.indexOf(".0") > -1){
					timeValue = timeValue.substring(0, timeValue.indexOf(".0"));
				}
				
				if(timeValue.indexOf("1900-01-01") > -1){
					pageContext.getOut().print("");
					return this.SKIP_BODY;
				}
				
				SimpleDateFormat vFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				if(null != pattern && "" != pattern){
					SimpleDateFormat sdformat = new SimpleDateFormat(pattern);
					pageContext.getOut().print(sdformat.format(sdformat.parse(timeValue)));
				}else{
					SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
					pageContext.getOut().print(sdformat.format(vFormat.parse(timeValue)));
				}
				
			}else{
				pageContext.getOut().print("");
			}
		}catch(Exception e){
			e.getMessage();
			System.out.println(e);
		}
		return this.SKIP_BODY;//表示不用处理标签体，直接调用doEndTag()方法
	}
	
	public int doEndTag(){
		return this.EVAL_PAGE;//继续执行后续的JSP页面内容
	}
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		try {
			
			this.value = (null != value ? ExpressionEvaluatorManager.evaluate("value", value.toString(), Object.class,this,pageContext):null);
		} catch (JspException e) {
			this.value = null;
			e.printStackTrace();
		}
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
}
