/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.proposal.dao;

import java.util.List;

import com.arjjs.ccm.common.persistence.CrudDao;
import com.arjjs.ccm.common.persistence.annotation.MyBatisDao;
import com.arjjs.ccm.modules.ccm.proposal.entity.CcmDatabaseProposal;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestProposal;

/**
 * 公告建议管理DAO接口
 * @author cdz
 * @version 2019-09-16
 */
@MyBatisDao
public interface CcmDatabaseProposalDao extends CrudDao<CcmDatabaseProposal> {
	List<CcmRestProposal> getProposalInfo(CcmDatabaseProposal ccmDatabaseProposal);
}