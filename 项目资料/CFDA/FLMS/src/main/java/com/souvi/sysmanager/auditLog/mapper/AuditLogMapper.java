package com.souvi.sysmanager.auditLog.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.auditLog.entity.AuditLog;

public interface AuditLogMapper {

	public int insertAuditLog(@Param("auditLog") AuditLog auditLog);

	public List<AuditLog> getAuditLogList(@Param("auditLog") AuditLog auditLog);

	public AuditLog getAuditLogByForeignId(@Param("foreignId") String foreignId);
}
