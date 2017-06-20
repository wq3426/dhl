package com.souvi.sysmanager.right.tag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.souvi.common.Constant;
import com.souvi.sysmanager.right.listener.RightListener;
import com.souvi.sysmanager.role.entity.Role;

public class IsNotRight extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -482115068005181599L;
	
	private static Logger logger = LoggerFactory.getLogger(IsNotRight.class);
	
	private String rightCode;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
		List<Role> roleList = (List<Role>)request.getSession().getAttribute(Constant.USER_SESSION_ROLE);
		boolean isRight = RightListener.isRight(roleList, rightCode);
		//logger.info(" siteManageService : " + siteManageService);
		if(logger.isDebugEnabled()){
			logger.debug(rightCode + " : " + isRight);
		}
		if(isRight){
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
