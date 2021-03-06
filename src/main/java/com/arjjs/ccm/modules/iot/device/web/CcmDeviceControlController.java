/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.iot.device.web;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.fence.entity.CcmElectronicFence;
import com.arjjs.ccm.modules.ccm.fence.service.CcmElectronicFenceService;
import com.arjjs.ccm.modules.iot.device.entity.CcmDeviceControl;
import com.arjjs.ccm.modules.iot.device.service.CcmDeviceControlService;
import com.arjjs.ccm.modules.iot.warning.entity.CcmEarlyWarning;
import com.arjjs.ccm.modules.iot.warning.service.CcmEarlyWarningService;
import com.arjjs.ccm.tool.CommUtil;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 布控Controller
 * @author lhf
 * @version 2019-07-23
 */
@Controller
@RequestMapping(value = "${adminPath}/device/ccmDeviceControl")
public class CcmDeviceControlController extends BaseController {

	@Autowired
	private CcmDeviceControlService ccmDeviceControlService;

	@Autowired
	private CcmEarlyWarningService ccmEarlyWarningService;

	@Autowired
	private CcmElectronicFenceService ccmElectronicFenceService;
	
	@ModelAttribute
	public CcmDeviceControl get(@RequestParam(required=false) String id) {
		CcmDeviceControl entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ccmDeviceControlService.get(id);
		}
		if (entity == null){
			entity = new CcmDeviceControl();
		}
		return entity;
	}
	
	@RequiresPermissions("device:ccmDeviceControl:view")
	@RequestMapping(value = {"list", ""})
	public String list(CcmDeviceControl ccmDeviceControl, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmDeviceControl> page = ccmDeviceControlService.findPage(new Page<CcmDeviceControl>(request, response), ccmDeviceControl);
		model.addAttribute("page", page);
		model.addAttribute("controlBy",ccmDeviceControl.getControlBy());
		return "iot/device/ccmDeviceControlList";
	}

	@RequiresPermissions("device:ccmDeviceControl:view")
	@RequestMapping(value = "form")
	public String form(CcmDeviceControl ccmDeviceControl, Model model) {
		List<CcmElectronicFence> fenceList = ccmElectronicFenceService.findList(new CcmElectronicFence());
		model.addAttribute("ccmDeviceControl", ccmDeviceControl);
		model.addAttribute("fenceList",fenceList);
		return "iot/device/ccmDeviceControlForm";
	}

	@RequiresPermissions("device:ccmDeviceControl:edit")
	@RequestMapping(value = "save")
	public void save(CcmDeviceControl ccmDeviceControl, Model model,HttpServletResponse response) {
		if (!beanValidator(model, ccmDeviceControl)){
//			return form(ccmDeviceControl, model);
		}
		ccmDeviceControlService.save(ccmDeviceControl);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommUtil.openWinExpDiv(out, "保存布控成功");
	}
	
	@RequiresPermissions("device:ccmDeviceControl:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmDeviceControl ccmDeviceControl, RedirectAttributes redirectAttributes) {
		ccmDeviceControlService.delete(ccmDeviceControl);
		addMessage(redirectAttributes, "删除布控成功");
		return "redirect:"+Global.getAdminPath()+"/device/ccmDeviceControl/?repage";
	}

	@RequiresPermissions("device:ccmDeviceControl:view")
	@RequestMapping(value = {"earlyWarningList"})
	public String earlyWarningList(CcmEarlyWarning ccmEarlyWarning, HttpServletRequest request, HttpServletResponse response, Model model, String controlBy) {
		Page<CcmEarlyWarning> page = ccmEarlyWarningService.findPage(new Page<CcmEarlyWarning>(request, response), ccmEarlyWarning);
		model.addAttribute("page", page);
		model.addAttribute("controlBy", controlBy);
		return "iot/device/ccmEarlyWarningList";
	}

	@RequiresPermissions("device:ccmDeviceControl:edit")
	@RequestMapping(value = "earlyWarningDelete")
	public String delete(CcmEarlyWarning ccmEarlyWarning, RedirectAttributes redirectAttributes, String controlBy) {
		ccmEarlyWarningService.delete(ccmEarlyWarning);
		addMessage(redirectAttributes, "删除预警记录成功");
		redirectAttributes.addAttribute("idCard",ccmEarlyWarning.getIdCard());
		redirectAttributes.addAttribute("controlBy",controlBy);
		return "redirect:"+Global.getAdminPath()+"/device/ccmDeviceControl/earlyWarningList?&repage";
	}

	//得到车辆布控数量
	@ResponseBody
	@RequestMapping(value = "getCount")
	public List getCount(CcmDeviceControl ccmDeviceControl) {
		List<Map<String,String>> count = ccmDeviceControlService.getCount(ccmDeviceControl);
		return count;
	}
}