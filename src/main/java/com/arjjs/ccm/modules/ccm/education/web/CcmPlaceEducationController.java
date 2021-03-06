/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.education.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.education.entity.CcmPlaceEducation;
import com.arjjs.ccm.modules.ccm.education.service.CcmPlaceEducationService;
import com.arjjs.ccm.modules.ccm.place.entity.CcmBasePlace;
import com.arjjs.ccm.modules.ccm.place.service.CcmBasePlaceService;
import com.arjjs.ccm.tool.CommUtil;

/**
 * 文化教育场所Controller
 * 
 * @author ljd
 * @version 2019-04-26
 */
@Controller
@RequestMapping(value = "${adminPath}/education/ccmPlaceEducation")
public class CcmPlaceEducationController extends BaseController {

	@Autowired
	private CcmPlaceEducationService ccmPlaceEducationService;
	@Autowired
	private CcmBasePlaceService ccmBasePlaceService;

	@ModelAttribute
	public CcmPlaceEducation get(@RequestParam(required = false) String id) {
		CcmPlaceEducation entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmPlaceEducationService.get(id);
		}
		if (entity == null) {
			entity = new CcmPlaceEducation();
		}
		return entity;
	}

	/**
	 * 查询列表数据
	 * 
	 * @param ccmPlaceEducation
	 * @param request
	 * @param response
	 * @param model
	 * @param type
	 * @return
	 */
	@RequiresPermissions("education:ccmPlaceEducation:view")
	@RequestMapping(value = { "{type}", " " })
	public String list(CcmPlaceEducation ccmPlaceEducation, HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("type") String type) {
		ccmPlaceEducation.setType(type);
		Page<CcmPlaceEducation> page = ccmPlaceEducationService.findPage(new Page<CcmPlaceEducation>(request, response),
				ccmPlaceEducation);
		List<CcmPlaceEducation> findList = ccmPlaceEducationService.findList(ccmPlaceEducation);
		for (CcmPlaceEducation education : findList) {
			education.setCcmBasePlace(ccmBasePlaceService.get(education.getBasePlaceId()));
		}
		page.setList(findList);
		model.addAttribute("page", page);

		if (StringUtils.isNoneBlank(ccmPlaceEducation.getType()) && "01".equals(ccmPlaceEducation.getType())) {
			// 学校
			return "ccm/education/ccmPlaceEducationList";
		} else if ((StringUtils.isNoneBlank(ccmPlaceEducation.getType()))
				&& ("02".equals(ccmPlaceEducation.getType()))) {
			// 研究院
			return "ccm/education/ccmPlaceEducationGraduateList";
		} else if (StringUtils.isNoneBlank(ccmPlaceEducation.getType()) && ("03".equals(ccmPlaceEducation.getType()))) {
			// 美术馆或博物馆
			return "ccm/education/ccmPlaceEducationMuseumList";
		} else {
			return "ccm/education/ccmPlaceEducationList";
		}
	}

	@RequiresPermissions("education:ccmPlaceEducation:view")
	@RequestMapping(value = { "form" })
	public String form(CcmPlaceEducation ccmPlaceEducation, Model model, String type) {
		CcmBasePlace ccmBasePlace = new CcmBasePlace();
		ccmBasePlace.setId(ccmPlaceEducation.getBasePlaceId());
		CcmBasePlace ccmBasePlace2 = ccmBasePlaceService.get(ccmBasePlace);
		ccmPlaceEducation.setCcmBasePlace(ccmBasePlace2);
		model.addAttribute("ccmPlaceEducation", ccmPlaceEducation);
		if ("01".equals(ccmPlaceEducation.getType())) {
			// 学校
			return "ccm/education/ccmPlaceEducationForm";
		} else if ("02".equals(ccmPlaceEducation.getType())) {
			// 研究院
			return "ccm/education/ccmPlaceEducationGraduateForm";
		} else if (("03".equals(ccmPlaceEducation.getType()))) {
			// 美术馆或博物馆
			return "ccm/education/ccmPlaceEducationMuseumForm";
		} else {
			return "ccm/education/ccmPlaceEducationForm";
		}
	}

	@RequiresPermissions("education:ccmPlaceEducation:edit")
	@RequestMapping(value = "save/{type}")
	public void save(CcmPlaceEducation ccmPlaceEducation, Model model, RedirectAttributes redirectAttributes,
			HttpServletResponse response, @PathVariable("type") String type) {
		if (!beanValidator(model, ccmPlaceEducation)) {
		}
		// 处理场所基本信息
		if (null == ccmPlaceEducation.getBasePlaceId() || "".equals(ccmPlaceEducation.getBasePlaceId())) {
			CcmBasePlace ccmBasePlace = ccmPlaceEducation.getCcmBasePlace();
			String id = UUID.randomUUID().toString();
			ccmBasePlace.setId(id);
			ccmBasePlace.setIsNewRecord(true);
			ccmBasePlace.setPlaceType("ccm_place_education");
			ccmBasePlaceService.save(ccmBasePlace);
			ccmPlaceEducation.setCcmBasePlace(ccmBasePlace);
			ccmPlaceEducation.setBasePlaceId(id);
		} else {
			CcmBasePlace ccmBasePlace = ccmPlaceEducation.getCcmBasePlace();
			ccmBasePlace.setId(ccmPlaceEducation.getBasePlaceId());
			ccmBasePlace.setPlaceType("ccm_place_education");
			ccmBasePlaceService.save(ccmBasePlace);
			ccmPlaceEducation.setCcmBasePlace(ccmBasePlace);
		}

		ccmPlaceEducation.setType(type);
		// TODO 首先保存基础场所表数据，之后把生成id存到houseId里
		ccmPlaceEducationService.save(ccmPlaceEducation);
		addMessage(redirectAttributes, "保存文化教育场所成功");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ("01".equals(type)) {
			CommUtil.openWinExpDiv(out, "保存学校成功");
		}
		if ("02".equals(type)) {
			CommUtil.openWinExpDiv(out, "保存研究院成功");
		}
		if ("03".equals(type)) {
			CommUtil.openWinExpDiv(out, "保存美术馆或博物馆成功");
		}
	}

	@RequiresPermissions("education:ccmPlaceEducation:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmPlaceEducation ccmPlaceEducation, RedirectAttributes redirectAttributes) {
		if (null != ccmPlaceEducation.getBasePlaceId() && !("".equals(ccmPlaceEducation.getBasePlaceId()))) {
			CcmBasePlace ccmBasePlace = ccmBasePlaceService.get(ccmPlaceEducation.getBasePlaceId());
			ccmBasePlaceService.delete(ccmBasePlace);
		}
		ccmPlaceEducationService.delete(ccmPlaceEducation);
		addMessage(redirectAttributes, "删除文化教育场所成功");
		return "redirect:" + Global.getAdminPath() + "/education/ccmPlaceEducation/" + ccmPlaceEducation.getType()
				+ "?repage";
	}

}