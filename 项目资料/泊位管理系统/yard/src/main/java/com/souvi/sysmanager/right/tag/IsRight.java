package com.souvi.sysmanager.right.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.context.ContextLoader;
//import org.springframework.web.context.WebApplicationContext;

import com.souvi.common.Constant;
import com.souvi.sysmanager.right.listener.RightListener;
import com.souvi.sysmanager.role.entity.Role;

public class IsRight extends TagSupport {
	
	private static Logger logger = LoggerFactory.getLogger(IsRight.class);
	
	//private WebApplicationContext context= ContextLoader.getCurrentWebApplicationContext();
	//private SiteManageService siteManageService = (SiteManageService) context.getBean("siteManageService");
	
	private String rightCode;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4903997924650574482L;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		List<Role> roleList = (List<Role>)request.getSession().getAttribute(Constant.USER_SESSION_ROLE);
		boolean isRight = RightListener.isRight(roleList, rightCode);
		//logger.info(" siteManageService : " + siteManageService);
		if(logger.isDebugEnabled()){
			logger.debug(rightCode + " : " + isRight);
		}
		if(!isRight){
			return SKIP_BODY;
		}
		return EVAL_BODY_INCLUDE;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
	
}
