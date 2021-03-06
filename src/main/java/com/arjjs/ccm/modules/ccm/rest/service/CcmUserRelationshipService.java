/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmUserGroup;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmUserRelationship;
import com.arjjs.ccm.modules.ccm.view.entity.VCcmTeam;
import com.arjjs.ccm.modules.ccm.rest.dao.CcmUserRelationshipDao;

/**
 * 用户关系Service
 * @author fu
 * @version 2018-03-08
 */
@Service
@Transactional(readOnly = true)
public class CcmUserRelationshipService extends CrudService<CcmUserRelationshipDao, CcmUserRelationship> {
	@Autowired
	private CcmUserRelationshipDao ccmUserRelationshipDao;

	public CcmUserRelationship get(String id) {
		return super.get(id);
	}
	
	public List<CcmUserRelationship> findList(CcmUserRelationship ccmUserRelationship) {
		return super.findList(ccmUserRelationship);
	}
	
	public Page<CcmUserRelationship> findPage(Page<CcmUserRelationship> page, CcmUserRelationship ccmUserRelationship) {
		return super.findPage(page, ccmUserRelationship);
	}
	
	@Transactional(readOnly = false)
	public void save(CcmUserRelationship ccmUserRelationship) {
		super.save(ccmUserRelationship);
	}
	
	@Transactional(readOnly = false)
	public void insertUserRelationship(List<CcmUserRelationship> ccmUserRelationship) {
		dao.insertUserRelationship(ccmUserRelationship);
	}
	
	@Transactional(readOnly = false)
	public void delete(CcmUserRelationship ccmUserRelationship) {
		super.delete(ccmUserRelationship);
	}

	public List<VCcmTeam> findTeamListByGroup(CcmUserGroup ccmUserGroup) {
		return ccmUserRelationshipDao.findTeamListByGroup(ccmUserGroup);
	}
	
	@Transactional(readOnly = false)
	public void deleteByGroupAndUser(CcmUserRelationship ccmUserRelationship) {
		ccmUserRelationshipDao.deleteByGroupAndUser(ccmUserRelationship);
		
	}
	
}