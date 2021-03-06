/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.iot.warning.web;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.iot.warning.entity.CcmEarlyWarning;
import com.arjjs.ccm.modules.iot.warning.service.CcmEarlyWarningService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 预警记录Controller
 * 
 * @author yiqingxuan
 * @version 2019-07-24
 */
@Controller
@RequestMapping(value = "${adminPath}/warning/ccmEarlyWarning")
public class CcmEarlyWarningController extends BaseController {

	@Autowired
	private CcmEarlyWarningService ccmEarlyWarningService;

	@ModelAttribute
	public CcmEarlyWarning get(@RequestParam(required = false) String id) {
		CcmEarlyWarning entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmEarlyWarningService.get(id);
		}
		if (entity == null) {
			entity = new CcmEarlyWarning();
		}
		return entity;
	}

	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "list", "" })
	public String list(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/warning/ccmEarlyWarningList";
	}

	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = "form")
	public String form(CcmEarlyWarning ccmEarlyWarning, Model model) {
		model.addAttribute("ccmEarlyWarning", ccmEarlyWarning);
		return "iot/warning/ccmEarlyWarningForm";
	}

	@RequiresPermissions("warning:ccmEarlyWarning:edit")
	@RequestMapping(value = "save")
	public String save(CcmEarlyWarning ccmEarlyWarning, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEarlyWarning)) {
			return form(ccmEarlyWarning, model);
		}
		ccmEarlyWarningService.save(ccmEarlyWarning);
		addMessage(redirectAttributes, "保存预警记录成功");
		return "redirect:" + Global.getAdminPath() + "/warning/ccmEarlyWarning/?repage";
	}

	@RequiresPermissions("warning:ccmEarlyWarning:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmEarlyWarning ccmEarlyWarning, RedirectAttributes redirectAttributes) {
		ccmEarlyWarningService.delete(ccmEarlyWarning);
		addMessage(redirectAttributes, "删除预警记录成功");
		return "redirect:" + Global.getAdminPath() + "/warning/ccmEarlyWarning/?repage";
	}

	@RequiresPermissions("warning:ccmEarlyWarning:edit")
	@RequestMapping(value = "deleteImportantList")
	public String deleteImportantList(CcmEarlyWarning ccmEarlyWarning, RedirectAttributes redirectAttributes) {
		ccmEarlyWarningService.delete(ccmEarlyWarning);
		addMessage(redirectAttributes, "删除重点人员记录成功");
		return "redirect:" + Global.getAdminPath() + "/warning/ccmEarlyWarning/importantList?repage";
	}

	/**
	 * 
	 * @Title: gasstationlist
	 * @Description : 加油站 @author： @date： 2019年7月24日下午1:37:36
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "gasstationlist" })
	public String gasstationlist(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ccmEarlyWarning.setIsPeople("1");
		ccmEarlyWarning.setPlace("5");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/gasstation/warninggasstationList";
	}

	/**
	 * 
	 * @Title: gasstationlist
	 * @Description : 停车场 @author： @date： 2019年7月24日下午1:37:54
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "parkinglist" })
	public String parkinglist(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ccmEarlyWarning.setIsPeople("1");
		ccmEarlyWarning.setPlace("3");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/parking/warningparkingList";
	}

	/**
	 * 
	 * @Title: parkinglist
	 * @Description : 小区门禁 @author： @date： 2019年7月24日下午1:43:35
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "communitylist" })
	public String communitylist(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ccmEarlyWarning.setIsPeople("1");
		ccmEarlyWarning.setPlace("2");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/community/warningcommunityList";
	}

	/**
	 * 
	 * @Title: communitylist
	 * @Description : 公共wifi @author： @date： 2019年7月24日下午1:47:44
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "wifilist" })
	public String wifilist(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ccmEarlyWarning.setIsPeople("1");
		ccmEarlyWarning.setPlace("4");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/wifi/warningwifiList";
	}

	/**
	 * 
	 * @Title: importantList
	 * @Description : 重点人员感知 @author： @date： 2019年7月25日上午10:00:50
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @param controlBy
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "importantList" })
	public String importantList(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ccmEarlyWarning.setIsPeople("1");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/warning/importantList";
	}

	/**
	 * 
	 * @Title: placeList
	 * @Description : 场所 @author： @date： 2019年7月25日下午3:12:48
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "placeList" })
	public String placeList(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		ccmEarlyWarning.setIsPeople("1");
		ccmEarlyWarning.setPlace("1");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/warning/placeList";
	}

	/**
	 * 
	 * @Title: dangerousCarList
	 * @Description : 危险品车辆预警 @author： @date： 2019年7月25日下午4:11:43
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "dangerousCarList" })
	public String dangerousCarList(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ccmEarlyWarning.setIsPeople("2");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/dangerous/dangerousCarControlwaringList";
	}

	/**
	 * 
	 * @Title: carBayonetwaringList
	 * @Description : 卡口预警 @author： @date： 2019年7月25日下午4:29:57
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "carBayonetwaringList" })
	public String carBayonetwaringList(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		ccmEarlyWarning.setIsPeople("2");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response),
				ccmEarlyWarning);
		model.addAttribute("page", page);
		return "iot/dangerous/carBayonetwaringList";
	}

	// 分类得到今天不同布控下的预警数量
	@ResponseBody
	@RequestMapping(value = "getSortCountToday")
	public List getSortCountToday(CcmEarlyWarning ccmEarlyWarning) {
		List<Map<String, String>> count = ccmEarlyWarningService.getSortCountToday(ccmEarlyWarning);
		return count;
	}

	// 分类得到近7天不同布控下的预警数量
	@ResponseBody
	@RequestMapping(value = "getSortCountWeek")
	public List getSortCountWeek(CcmEarlyWarning ccmEarlyWarning) {
		List<Map<String, String>> count = ccmEarlyWarningService.getSortCountWeek(ccmEarlyWarning);
		return count;
	}

	// 查询当天的预警数据
	@ResponseBody
	@RequestMapping(value = "getDataByToday")
	public List<CcmEarlyWarning> getDataByToday() {
		return ccmEarlyWarningService.getDataByToday();
	}

	/**
	 *
	 * @Title: spswaringList
	 * @Description : 轨迹查询 @author： @date： 2019年7月29日下午2:55:25
	 * @param ccmEarlyWarning
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = { "spswaringList" })
	public String spswaringList(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (ccmEarlyWarning.getBeginDate() == null) {
			Date beginDate = new Date();
			ccmEarlyWarning.setBeginDate(sdf3.parse(sdf.format(beginDate)));
		}
		if (ccmEarlyWarning.getEndDate() == null) {
			Date endDate = new Date();
			ccmEarlyWarning.setEndDate(sdf3.parse(sdf2.format(endDate)));
		}
		ccmEarlyWarning.setIsPeople("2");
		Page<CcmEarlyWarning> page = ccmEarlyWarningService
				.findPagegroupby(new Page<CcmEarlyWarning>(request, response), ccmEarlyWarning); // 分组 根据身份证号
		model.addAttribute("page", page);
		return "iot/warning/spsWarningList";
	}

	@RequiresPermissions("warning:ccmEarlyWarning:view")
	@RequestMapping(value = "getxylist")
	@ResponseBody
	public List<CcmEarlyWarning> getxylist(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request,
			HttpServletResponse response, Model model) throws Exception {
		List<CcmEarlyWarning> reslist = ccmEarlyWarningService.findListbyidcard(ccmEarlyWarning);
		return reslist;
	}
}