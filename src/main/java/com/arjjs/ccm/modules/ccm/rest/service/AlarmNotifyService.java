package com.arjjs.ccm.modules.ccm.rest.service;


import com.arjjs.ccm.modules.ccm.rest.dao.AlarmNotifyDao;
import com.arjjs.ccm.modules.ccm.rest.entity.AlarmNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class AlarmNotifyService {

	@Autowired
	private AlarmNotifyDao alarmNotifyDao;

	public List<AlarmNotify> queryAlarmNotifyList(String handlePoliceId) {

		return alarmNotifyDao.queryAlarmNotifyList(handlePoliceId);
	}

	public int selectAlarmNotifyTotal(String userId) {
		return alarmNotifyDao.selectAlarmNotifyTotal(userId);
	}

	public int selectAlarmNotifyTodoCount(String userId) {
		return alarmNotifyDao.selectAlarmNotifyTodoCount(userId);
	}

	@Transactional(readOnly = false)
	public int updateSatausByIdAndUserId(String id,String userId) {
		return alarmNotifyDao.updateSatausByIdAndUserId(id,userId);
	}
}
