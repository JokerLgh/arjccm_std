/**
 * Copyright &copy; 2012-2018 All rights reserved.
 */
package com.arjjs.ccm.modules.plm.home.web;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.plm.home.entity.PlmPortalDetail;
import com.arjjs.ccm.modules.plm.home.entity.PlmPortalDict;
import com.arjjs.ccm.modules.plm.home.entity.PlmPortalPlan;
import com.arjjs.ccm.modules.plm.home.service.PlmPortalDetailService;
import com.arjjs.ccm.modules.plm.home.service.PlmPortalDictService;
import com.arjjs.ccm.modules.plm.home.service.PlmPortalPlanService;
import com.arjjs.ccm.modules.sys.utils.DictUtils;

/**
 * 门户明细Controller
 * 
 * @author liuxue
 * @version 2018-07-04
 */
@Controller
@RequestMapping(value = "${adminPath}/home/plmPortalDetail")
public class PlmPortalDetailController extends BaseController {

	@Autowired
	private PlmPortalDetailService plmPortalDetailService;
	@Autowired
	private PlmPortalDictService plmPortalDictService;
	@Autowired
	private PlmPortalPlanService plmPortalPlanService;

	// 父类id plmPortalPlan ip
	private String parentId;

	@ModelAttribute
	public PlmPortalDetail get(@RequestParam(required = false) String id) {
		PlmPortalDetail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = plmPortalDetailService.get(id);
		}
		if (entity == null) {
			entity = new PlmPortalDetail();
		}
		return entity;
	}

	@RequestMapping(value = { "list", "" })
	public String list(PlmPortalDetail plmPortalDetail, HttpServletRequest request, HttpServletResponse response,
			Model model, String pid) {
		if (StringUtils.isNotBlank(pid)) {
			parentId = pid;
		}
		PlmPortalPlan plmPortalPlan = plmPortalPlanService.get(parentId);
		plmPortalDetail.setParent(plmPortalPlan);
		List<PlmPortalDetail> portletDetaillist = plmPortalDetailService.findList(plmPortalDetail);
		model.addAttribute("portletlist", portletDetaillist);
		return "plm/home/plmPortalPlanCustom";
	}

	/**
	 * 预修改使用方案form
	 * 
	 * @param plmPortalDetail
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(PlmPortalDetail plmPortalDetail, Model model) {
		model.addAttribute("plmPortalDetail", plmPortalDetail);
		PlmPortalDict plmPortalDict = new PlmPortalDict();
		List<PlmPortalDict> plmPortalDictList = plmPortalDictService.findList(plmPortalDict);
		model.addAttribute("plmPortalDictList", plmPortalDictList);
		return "plm/home/plmHomeFormFan";
	}

	/**
	 * 明细编辑form
	 * 
	 * @param plmPortalDetail
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "formDetail")
	public String formDetail(PlmPortalDetail plmPortalDetail, Model model) {
		PlmPortalPlan plmPortalPlan = plmPortalPlanService.get(parentId);
		plmPortalDetail.setParent(plmPortalPlan);
		String type = plmPortalPlan.getType();
		plmPortalDetail.setType(type);
		plmPortalDetail.setExtend1(plmPortalPlan.getExtend1());
		model.addAttribute("plmPortalDetail", plmPortalDetail);

		// 只查询选择平台的门户目录
		PlmPortalDict plmPortalDict = new PlmPortalDict();
		plmPortalDict.setExtend1(plmPortalPlan.getExtend1());
		List<PlmPortalDict> plmPortalDictList = plmPortalDictService.findList(plmPortalDict);
		model.addAttribute("plmPortalDictList", plmPortalDictList);
		model.addAttribute("home_latItudelist", DictUtils.getDictList("home_latItude" + type));
		return "plm/home/plmPortalDetailForm";
	}

	/**
	 * 编辑门户明细信息
	 * 
	 * @param plmPortalDetail
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save(PlmPortalDetail plmPortalDetail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, plmPortalDetail)) {
			return form(plmPortalDetail, model);
		}
		PlmPortalDict plmPortalDict = plmPortalDictService.get(plmPortalDetail.getPortalDictId());
		plmPortalDetail.setExtend1(plmPortalDict.getExtend1());
		plmPortalDetailService.save(plmPortalDetail);
		addMessage(redirectAttributes, "保存门户明细成功");
		return "redirect:" + Global.getAdminPath() + "/home/plmPortalDetail/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "itudeValidate")
	public String itudeValidate(String longItude, String latItude, String ylongItude, String ylatItude) {
		PlmPortalDetail plmPortalDetail = new PlmPortalDetail();
		PlmPortalPlan plmPortalPlan = plmPortalPlanService.get(parentId);
		plmPortalDetail.setParent(plmPortalPlan);
		List<PlmPortalDetail> portletDetaillist = plmPortalDetailService.findList(plmPortalDetail);
		// 判断 该位置是否有窗口存在
		String ok = "1";
		if (portletDetaillist != null && portletDetaillist.size() >= 1) {
			char LongItude[] = longItude.toCharArray();
			for (char c : LongItude) {
				Iterator<PlmPortalDetail> it = portletDetaillist.iterator();
				while (it.hasNext()) {
					PlmPortalDetail plmPortalDetail2 = it.next();
					if (StringUtils.isNotBlank(ylongItude) && plmPortalDetail2.getLongItude().equals(ylongItude)
							&& plmPortalDetail2.getLatItude().equals(ylatItude)) {
						// 在编辑时 遍历集合时跳过 要修改的PlmPortalDetail对象
					} else {
						if (plmPortalDetail2.getLongItude().indexOf(String.valueOf(c)) != -1
								|| String.valueOf(c).equals(plmPortalDetail2.getLongItude())) {
							String LatItude2 = plmPortalDetail2.getLatItude();
							if (latItude.indexOf(LatItude2) != -1 || LatItude2.indexOf(latItude) != -1
									|| LatItude2.equals(latItude)) {
								ok = "0";
							}
						}
					}
				}
			}
		}
		return ok;
	}

	@ResponseBody
	@RequestMapping(value = "delete")
	public String delete(PlmPortalDetail plmPortalDetail, RedirectAttributes redirectAttributes) {
		plmPortalDetailService.delete(plmPortalDetail);
		return "删除成功";
	}
}