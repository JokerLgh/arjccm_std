package com.arjjs.ccm.modules.ccm.rest.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.line.entity.CcmLineProtect;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestResult;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestType;
import com.arjjs.ccm.modules.ccm.rest.service.CcmRestOfficeService;
import com.arjjs.ccm.modules.ccm.service.entity.CcmServiceDuty;
import com.arjjs.ccm.modules.ccm.service.service.CcmServiceDutyService;
import com.arjjs.ccm.modules.ccm.sys.entity.CcmWorkReport;
import com.arjjs.ccm.modules.ccm.sys.service.CcmWorkReportService;
import com.arjjs.ccm.modules.sys.entity.Office;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.service.OfficeService;
import com.arjjs.ccm.tool.CommUtilRest;
import com.arjjs.ccm.tool.Tree;
import com.fay.tree.service.IFayTreeNode;
import com.fay.tree.util.FayTreeUtil;

/**
 * 工作职责
 * 
 * @author arj
 * @version 2018-03-12
 */
@Controller
@RequestMapping(value = "${appPath}/rest/service/ccmServiceDuty")
public class CcmRestServiceDuty extends BaseController {

	@Autowired
	private CcmServiceDutyService ccmServiceDutyService;

	/**
	 * @see 获取单个工作职责
	 * @param id 工作职责ID
	 * @return
	 * @author arj
	 * @version 2018-03-12
	 */

	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public CcmRestResult get(String userId, HttpServletRequest req, HttpServletResponse resp, String id)
			throws IOException {
		CcmRestResult result = new CcmRestResult();
		
		if (id == null || "".equals(id)) {//参数id不对
			result.setCode(CcmRestType.ERROR_PARAM);
			return result;
		}
		
		CcmServiceDuty ccmServiceDuty = ccmServiceDutyService.get(id);
		
		result.setCode(CcmRestType.OK);
		result.setResult(ccmServiceDuty);

		return result;
	}

	/**
	 * @see 查询服务信息
	 * @param pageNo  页码
	 * @param pageSiz 分页大小
	 * @return
	 * @author arj
	 * @version 2018-03-12
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public CcmRestResult query(String userId, HttpServletRequest req, HttpServletResponse resp,
			CcmServiceDuty ccmServiceDuty) throws IOException {
		CcmRestResult result = new CcmRestResult();

		Page<CcmServiceDuty> page = ccmServiceDutyService.findPage(new Page<CcmServiceDuty>(req, resp), ccmServiceDuty);
	
		result.setCode(CcmRestType.OK);
		result.setResult(page.getList());
		
		return result;
	}

	
	
	
	
	
	
	
	
	
	
	
}