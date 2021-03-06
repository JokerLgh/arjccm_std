package com.arjjs.ccm.modules.ccm.rest.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestResult;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestType;
import com.arjjs.ccm.modules.ccm.service.entity.CcmCommunityWork;
import com.arjjs.ccm.modules.ccm.service.entity.CcmServiceGuidance;
import com.arjjs.ccm.modules.ccm.service.service.CcmCommunityWorkService;
import com.arjjs.ccm.modules.ccm.service.service.CcmServiceGuidanceService;
import com.arjjs.ccm.modules.ccm.sys.entity.CcmWorkReport;
import com.arjjs.ccm.modules.sys.entity.Dict;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.tool.CommUtilRest;

/**
 * 社区服务
 * 
 * @author arj
 * @version 2018-03-12
 */
@Controller
@RequestMapping(value = "${appPath}/rest/service/guidance")
public class CcmRestGuidance extends BaseController {

	@Autowired
	private CcmServiceGuidanceService ccmServiceGuidanceService;

	/**
	 * @see 获取单个办事指南
	 * @param id
	 *            办事指南ID
	 * @return
	 * @author arj
	 * @version 2018-03-13
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public CcmRestResult get(String userId, HttpServletRequest req, HttpServletResponse resp, String id)
			throws IOException {
		// 获取results
		CcmRestResult result = CommUtilRest.getResult(userId, req, resp, id);
		// 如果当前的 flag 为返回
		if (result.isReturnFlag()) {
			return result;
		}
		// 当前 是否为空
		CcmServiceGuidance ccmServiceGuidance = ccmServiceGuidanceService.get(id);
		result.setCode(CcmRestType.OK);
		result.setResult(ccmServiceGuidance);

		return result;
	}

	/**
	 * @see 查询服务指南信息
	 * @param pageNo
	 *            页码
	 * @param pageSiz
	 *            分页大小
	 * @return
	 * @author arj
	 * @version 2018-03-12
	 */
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public CcmRestResult query(String userId, HttpServletRequest req, HttpServletResponse resp,
			CcmServiceGuidance ccmServiceGuidance) throws IOException {
		// 获取结果
		CcmRestResult result = CommUtilRest.queryResult(userId, req, resp);
		// 如果当前的 flag 为返回
		if (result.isReturnFlag()) {
			return result;
		}
		// 设置当前的 日志不为空
		ccmServiceGuidance = (null == ccmServiceGuidance) ? new CcmServiceGuidance() : ccmServiceGuidance;
		// 获取userId
		User userEntity = new User();
		userEntity.setId(userId);
		ccmServiceGuidance.setCreateBy(userEntity);
		Page<CcmServiceGuidance> page = ccmServiceGuidanceService.findPage(new Page<CcmServiceGuidance>(req, resp),
				(null == ccmServiceGuidance) ? new CcmServiceGuidance() : ccmServiceGuidance);
		result.setCode(CcmRestType.OK);
		result.setResult(page.getList());
		// 输出结果
		return result;
	}

	/**
	 * @see 修改 服务指南
	 * @param ccmServiceGuidance
	 *            服务指南对象
	 * @author fuxinshuang
	 * @version 2018-03-13
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public CcmRestResult modify(String userId, CcmServiceGuidance ccmServiceGuidance, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		// 获取结果
		CcmRestResult result = CommUtilRest.queryResult(userId, req, resp);
		// 如果当前的 flag 为返回
		if (result.isReturnFlag()) {
			return result;
		}
		// 如果创建者为空
		if (null == ccmServiceGuidance.getCreateBy()) {
			ccmServiceGuidance.setCreateBy(new User(userId));
		}
		ccmServiceGuidance.setUpdateBy(new User(userId));
		ccmServiceGuidanceService.save(ccmServiceGuidance);
		// 返回
		result.setCode(CcmRestType.OK);
		result.setResult("成功");
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "selectType", method = RequestMethod.GET)
	public List<Dict> selectType(@RequestParam(required = false) String type,
			@RequestParam(required = false) String type2, Model model) {
		return DictUtils.getDictList(type);
	}

}