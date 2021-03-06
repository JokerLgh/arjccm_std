/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.hospital.web;

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
import com.arjjs.ccm.modules.ccm.hospital.entity.CcmPlaceHospital;
import com.arjjs.ccm.modules.ccm.hospital.service.CcmPlaceHospitalService;
import com.arjjs.ccm.modules.ccm.place.entity.CcmBasePlace;
import com.arjjs.ccm.modules.ccm.place.service.CcmBasePlaceService;
import com.arjjs.ccm.tool.CommUtil;

/**
 * 医疗卫生场所Controller
 * 
 * @author ljd
 * @version 2019-04-28
 */
@Controller
@RequestMapping(value = "${adminPath}/hospital/ccmPlaceHospital")
public class CcmPlaceHospitalController extends BaseController {

	@Autowired
	private CcmPlaceHospitalService ccmPlaceHospitalService;
	@Autowired
	private CcmBasePlaceService ccmBasePlaceService;

	@ModelAttribute
	public CcmPlaceHospital get(@RequestParam(required = false) String id) {
		CcmPlaceHospital entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmPlaceHospitalService.get(id);
		}
		if (entity == null) {
			entity = new CcmPlaceHospital();
		}
		return entity;
	}

	@RequiresPermissions("hospital:ccmPlaceHospital:view")
	@RequestMapping(value = { "{type}" })
	public String list(CcmPlaceHospital ccmPlaceHospital, HttpServletRequest request, HttpServletResponse response,
			Model model, @PathVariable("type") String type) {
		ccmPlaceHospital.setType(type);
		Page<CcmPlaceHospital> page = ccmPlaceHospitalService.findPage(new Page<CcmPlaceHospital>(request, response),
				ccmPlaceHospital);
		List<CcmPlaceHospital> findList = ccmPlaceHospitalService.findList(ccmPlaceHospital);
		for (CcmPlaceHospital hospital : findList) {
			hospital.setCcmBasePlace(ccmBasePlaceService.get(hospital.getBasePlaceId()));
		}
		page.setList(findList);
		model.addAttribute("page", page);
		if (StringUtils.isNoneBlank(ccmPlaceHospital.getType()) && "01".equals(ccmPlaceHospital.getType())) {
			// 医院
			return "ccm/hospital/ccmPlaceHospitalList";
		} else if ((StringUtils.isNoneBlank(ccmPlaceHospital.getType())) && ("02".equals(ccmPlaceHospital.getType()))) {
			// 私人诊所
			return "ccm/hospital/ccmPlacePrivateHospitalList";
		} else if (StringUtils.isNoneBlank(ccmPlaceHospital.getType()) && ("03".equals(ccmPlaceHospital.getType()))) {
			// 药店
			return "ccm/hospital/ccmPlaceHospitalStoreList";
		} else {
			return "ccm/hospital/ccmPlaceHospitalList";
		}
	}

	@RequiresPermissions("hospital:ccmPlaceHospital:view")
	@RequestMapping(value = "form")
	public String form(CcmPlaceHospital ccmPlaceHospital, Model model) {
		CcmBasePlace ccmBasePlace = new CcmBasePlace();
		ccmBasePlace.setId(ccmPlaceHospital.getBasePlaceId());
		CcmBasePlace ccmBasePlace2 = ccmBasePlaceService.get(ccmBasePlace);
		ccmPlaceHospital.setCcmBasePlace(ccmBasePlace2);
		model.addAttribute("ccmPlaceHospital", ccmPlaceHospital);

		if (StringUtils.isNoneBlank(ccmPlaceHospital.getType()) && "01".equals(ccmPlaceHospital.getType())) {
			// 医院
			return "ccm/hospital/ccmPlaceHospitalForm";
		} else if ((StringUtils.isNoneBlank(ccmPlaceHospital.getType())) && ("02".equals(ccmPlaceHospital.getType()))) {
			// 私人诊所
			return "ccm/hospital/ccmPlacePrivateHospitalForm";
		} else if (StringUtils.isNoneBlank(ccmPlaceHospital.getType()) && ("03".equals(ccmPlaceHospital.getType()))) {
			// 药店
			return "ccm/hospital/ccmPlaceHospitalStoreForm";
		} else {
			return "ccm/hospital/ccmPlaceHospitalForm";
		}

	}

	@RequiresPermissions("hospital:ccmPlaceHospital:edit")
	@RequestMapping(value = "save/{type}")
	public void save(CcmPlaceHospital ccmPlaceHospital, Model model, RedirectAttributes redirectAttributes,
			HttpServletResponse response, @PathVariable("type") String type) {
		if (!beanValidator(model, ccmPlaceHospital)) {
		}
		// 处理场所基本信息
		if (null == ccmPlaceHospital.getBasePlaceId() || "".equals(ccmPlaceHospital.getBasePlaceId())) {
			CcmBasePlace ccmBasePlace = ccmPlaceHospital.getCcmBasePlace();
			String id = UUID.randomUUID().toString();
			ccmBasePlace.setId(id);
			ccmBasePlace.setIsNewRecord(true);
			ccmBasePlace.setPlaceType("ccm_place_hospital");
			ccmBasePlaceService.save(ccmBasePlace);
			ccmPlaceHospital.setCcmBasePlace(ccmBasePlace);
			ccmPlaceHospital.setBasePlaceId(id);
		} else {
			CcmBasePlace ccmBasePlace = ccmPlaceHospital.getCcmBasePlace();
			ccmBasePlace.setId(ccmPlaceHospital.getBasePlaceId());
			ccmBasePlace.setPlaceType("ccm_place_hospital");
			ccmBasePlaceService.save(ccmBasePlace);
			ccmPlaceHospital.setCcmBasePlace(ccmBasePlace);
		}

		ccmPlaceHospital.setType(type);
		// TODO 首先保存基础场所表数据，之后把生成id存到houseId里
		ccmPlaceHospitalService.save(ccmPlaceHospital);
		addMessage(redirectAttributes, "保存医疗卫生场所成功");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if ("01".equals(type)) {
			CommUtil.openWinExpDiv(out, "保存医院成功");
		}
		if ("02".equals(type)) {
			CommUtil.openWinExpDiv(out, "保存私人诊所成功");
		}
		if ("03".equals(type)) {
			CommUtil.openWinExpDiv(out, "保存药店成功");
		}
	}

	@RequiresPermissions("hospital:ccmPlaceHospital:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmPlaceHospital ccmPlaceHospital, RedirectAttributes redirectAttributes) {
		ccmPlaceHospitalService.delete(ccmPlaceHospital);
		if (null != ccmPlaceHospital.getBasePlaceId() && !("".equals(ccmPlaceHospital.getBasePlaceId()))) {
			CcmBasePlace ccmBasePlace = ccmBasePlaceService.get(ccmPlaceHospital.getBasePlaceId());
			ccmBasePlaceService.delete(ccmBasePlace);
		}
		addMessage(redirectAttributes, "删除医疗卫生场所成功");
		return "redirect:" + Global.getAdminPath() + "/hospital/ccmPlaceHospital/" + ccmPlaceHospital.getType()
				+ "?repage";
	}

}