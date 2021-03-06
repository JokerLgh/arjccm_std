/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.service;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.modules.ccm.org.dao.CcmOrgAreaDao;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgArea;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.SearchTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域扩展表（社区、网格）Service
 * 
 * @author pengjianqiang
 * @version 2018-01-29
 */
@Service
@Transactional(readOnly = true)
public class CcmOrgAreaService extends CrudService<CcmOrgAreaDao, CcmOrgArea> {

	@Autowired
	private CcmOrgAreaDao ccmOrgAreaDao;

	public CcmOrgArea get(String id) {
		return super.get(id);
	}

	public List<CcmOrgArea> findList(CcmOrgArea ccmOrgArea) {
		return super.findList(ccmOrgArea);
	}
	
	public List<CcmOrgArea> getAreaMap(CcmOrgArea ccmOrgArea) {
		return dao.getAreaMap(ccmOrgArea);
	}

	public Page<CcmOrgArea> findPage(Page<CcmOrgArea> page, CcmOrgArea ccmOrgArea) {
		return super.findPage(page, ccmOrgArea);
	}

	@Transactional(readOnly = false)
	public void save(CcmOrgArea ccmOrgArea) {
		super.save(ccmOrgArea);
	}

	@Transactional(readOnly = false)
	public void delete(CcmOrgArea ccmOrgArea) {
		super.delete(ccmOrgArea);
	}

	//
	public CcmOrgArea GetItByUserId() {
		return ccmOrgAreaDao.GetItByUserId(UserUtils.getUser().getId());
	}
	public CcmOrgArea GetItByUserId(String userId) {
		return ccmOrgAreaDao.GetItByUserId(userId);
	}

	// 首页社区弹框：社区网格（通过父键查子集）area借用ccmOrgArea
	public List<Area> findAreaNet(Area area2) {
		// TODO Auto-generated method stub
		return ccmOrgAreaDao.findAreaNet(area2);
	}

	// 社区网格外表（通过社区网格id查外表）
	public CcmOrgArea findCcmOrgArea(CcmOrgArea ccmOrgArea2) {
		// TODO Auto-generated method stub
		return ccmOrgAreaDao.findCcmOrgArea(ccmOrgArea2);
	}

	// 更新点坐标
	@Transactional(readOnly = false)
	public boolean updateCoordinates(CcmOrgArea ccmOrgArea) {
		if (null == ccmOrgAreaDao.findCcmOrgArea(ccmOrgArea) ) {
			ccmOrgArea.preInsert();
			return ccmOrgAreaDao.insert(ccmOrgArea) > 0;
		} else {
			ccmOrgArea.preUpdate();
			return ccmOrgAreaDao.updateCoordinates(ccmOrgArea) > 0;
		}
	}

	//区域全部数据
	public List<SearchTab> findListAllData(CcmOrgArea ccmOrgArea) {
		return ccmOrgAreaDao.findListAllData(ccmOrgArea);
	}

	public Page<CcmOrgArea> findTownPage(Page<CcmOrgArea> page, CcmOrgArea ccmOrgArea) {
		ccmOrgArea.setPage(page);
		page.setList(ccmOrgAreaDao.findTownList(ccmOrgArea));
		return page;
	}
	
	// 社区网格外表（通过社区网格id查外表/不关联sys_area表）
		public CcmOrgArea getCcmOrgArea(CcmOrgArea ccmOrgArea2) {
			// TODO Auto-generated method stub
			return ccmOrgAreaDao.getCcmOrgArea(ccmOrgArea2);
		}
}