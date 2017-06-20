package com.souvi.sysmanager.login.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.common.Constant;
import com.souvi.sysmanager.user.entity.User;
import com.souvi.sysmanager.user.mapper.userMapper;

@Service
public class GlobalServiceUtil {

	@Autowired
	private userMapper userMapper;
	/**
	 * 获取系统一级审批人或者二级审批人的邮箱
	 * roleId角色Id
	 * projectId项目Id
	 */
	public String getUserMailByRoleId(String roleId,String projectId) {
		List<User> userlist = userMapper.getUserMailByRoleId(roleId, projectId);
		String[] array = new String[userlist.size()];
		int a=0;
		for(User user: userlist){
			array[a]=user.getEmail();
			a++;
		}
		return StringUtils.join(array, ",");
	}
	/**
	 * 获取该公司或者产品下已经审批的一级审批人邮箱，或者提交人邮箱
	 * roleId角色Id
	 * foreignId公司主键Id或者产品主键Id
	 */
	public String getUserMailByAuditStage(int auditStage,String foreignId) {
		List<User> userlist = userMapper.getUserMailByAuditStage(auditStage, foreignId);
		String[] array = new String[userlist.size()];
		int a=0;
		for(User user: userlist){
			array[a]=user.getEmail();
			a++;
		}
		return StringUtils.join(array, ",");
	}
	/**
	 * 得到审批阶段需要发送邮件的邮箱
	 * auditStage:0提交，1一级审批，2二级审批
	 * auditStatus:审批通过Id,审批不通过Id
	 * foreignId:auditStage=0提交时为项目Id;auditStage=1且审批通过时为项目Id,审批不通过时为公司或者产品Id;auditStage=2时为公司或者产品Id;
	 * @return
	 */
	public String gerAuditUserEmail(int auditStage,String foreignId,String auditStatus){
		String email="";
		
		if(auditStage==Constant.AuditLog_Submit){
			//提交人提交,发邮件给全部一级审批人.
			email=this.getUserMailByRoleId(Constant.Role_FirstAduit, foreignId);
		}else if(auditStage==Constant.AuditLog_FirstAduit && auditStatus.equalsIgnoreCase(Constant.AuditType_Approval)){
			//一级审批人审批，且审批通过。发邮件给全部二级审批人.
			email=this.getUserMailByRoleId(Constant.Role_SecondAduit, foreignId);
		}else if(auditStage==Constant.AuditLog_FirstAduit && auditStatus.equalsIgnoreCase(Constant.AuditType_NotApproval)){
			//一级审批人审批，且审批不通过。发邮件给提交人.
			email=this.getUserMailByAuditStage(Constant.AuditLog_Submit, foreignId);
		}else if(auditStage==Constant.AuditLog_SecondAduit && auditStatus.equalsIgnoreCase(Constant.AuditType_Approval)){
			//二级审批人审批，且审批通过。发邮件给给提交人.
			email=this.getUserMailByAuditStage(Constant.AuditLog_Submit, foreignId);
		}else if(auditStage==Constant.AuditLog_SecondAduit && auditStatus.equalsIgnoreCase(Constant.AuditType_NotApproval)){
			//二级审批人审批，且审批不通过。发邮件给提交人和上一级的一级审批人
			email=this.getUserMailByAuditStage(Constant.AuditLog_Submit, foreignId);
			email+=this.getUserMailByAuditStage(Constant.AuditLog_SecondAduit, foreignId);
		}
		return email;
	}
}
