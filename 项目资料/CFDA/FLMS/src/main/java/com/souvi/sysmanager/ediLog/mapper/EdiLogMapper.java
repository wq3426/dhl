package com.souvi.sysmanager.ediLog.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.ediLog.entity.EdiLog;

public interface EdiLogMapper {

	//edi日志列表
	List<EdiLog> getediLogList(@Param("elg")EdiLog elg);

	//通过id查询edi日志列表
	List<EdiLog> selectEdiLogByID(@Param("ediId")String ediId);

	//更改重发原因
	boolean updataEdiLog(@Param("ediId")String ediId, @Param("repeatedCause")String repeatedCause, @Param("retransMission")String retransMission, @Param("retransMissionTime")Date retransMissionTime);

}
