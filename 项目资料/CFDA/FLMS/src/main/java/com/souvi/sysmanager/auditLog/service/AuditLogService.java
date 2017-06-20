package com.souvi.sysmanager.auditLog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.common.KeyUtil;
import com.souvi.sysmanager.auditLog.entity.AuditLog;
import com.souvi.sysmanager.auditLog.mapper.AuditLogMapper;

@Service
public class AuditLogService {

	@Autowired
	private AuditLogMapper auditLogMapper;

	/**
	 * 添加审批日志
	 * 
	 * @param auditLog
	 * @return
	 */
	public int insertAuditLog(AuditLog auditLog) {
		auditLog.setAuditId(KeyUtil.getGuidID());
		auditLog.setAuditDate(new Date());
		return this.auditLogMapper.insertAuditLog(auditLog);
	}

	/**
	 * 查询审批日志
	 * 
	 * @param auditLog
	 * @return
	 */
	public List<AuditLog> getAuditLogList(AuditLog auditLog) {
		return this.auditLogMapper.getAuditLogList(auditLog);
	}

	/**
	 * 查询最近一次审批日志
	 * 
	 * @param foreignId
	 * @return
	 */
	public AuditLog getAuditLogByForeignId(String foreignId) {
		return this.auditLogMapper.getAuditLogByForeignId(foreignId);
	}
}
