/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.know.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.modules.ccm.know.entity.CcmKnowInspect;
import com.arjjs.ccm.modules.ccm.org.dao.CcmOrgNpseDao;
import com.arjjs.ccm.tool.EchartType;
import com.arjjs.ccm.modules.ccm.know.dao.CcmKnowInspectDao;

/**
 * 检查记录Service
 * @author liang
 * @version 2018-05-31
 */
@Service
@Transactional(readOnly = true)
public class CcmKnowInspectService extends CrudService<CcmKnowInspectDao, CcmKnowInspect> {

	@Autowired
	private CcmKnowInspectDao ccmKnowInspectDao;
	
	
	
	public CcmKnowInspect get(String id) {
		return super.get(id);
	}
	
	public List<CcmKnowInspect> findList(CcmKnowInspect ccmKnowInspect) {
		return super.findList(ccmKnowInspect);
	}
	
	public Page<CcmKnowInspect> findPage(Page<CcmKnowInspect> page, CcmKnowInspect ccmKnowInspect) {
		return super.findPage(page, ccmKnowInspect);
	}
	
	@Transactional(readOnly = false)
	public void save(CcmKnowInspect ccmKnowInspect) {
		super.save(ccmKnowInspect);
	}
	
	@Transactional(readOnly = false)
	public void delete(CcmKnowInspect ccmKnowInspect) {
		super.delete(ccmKnowInspect);
	}

	//安全生产防范检查
	public List<EchartType> getTypeSafeData() {
		return ccmKnowInspectDao.getTypeSafeData();
	}

	//安全生产防范检查
	public List<EchartType> getTypeSafeDataecharts() {
		return ccmKnowInspectDao.getTypeSafeDataecharts();
	}


}