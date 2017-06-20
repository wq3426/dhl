package com.souvi.sysmanager.ediLog.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.ediLog.entity.EdiLog;
import com.souvi.sysmanager.ediLog.mapper.EdiLogMapper;


@Service
public class EdiLogService {

	@Autowired
	private EdiLogMapper ediLogMapper;

	//edi日志列表
	public List<EdiLog> getediLogList(EdiLog elg) {
		// TODO Auto-generated method stub
		return ediLogMapper.getediLogList(elg);
	}

	//通过id查询edi日志列表
	public List<EdiLog> selectEdiLogByID(String ediId) {
		// TODO Auto-generated method stub
		return ediLogMapper.selectEdiLogByID(ediId);
	}
   //更改重发原因
	
	public boolean updataEdiLog(String ediId, String repeatedCause, String retransMission, Date retransMissionTime) {
		// TODO Auto-generated method stub
		return ediLogMapper.updataEdiLog(ediId,repeatedCause,retransMission,retransMissionTime);
	}

}
