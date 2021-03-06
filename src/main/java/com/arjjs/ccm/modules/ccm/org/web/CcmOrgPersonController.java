/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgControl;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgPerson;
import com.arjjs.ccm.modules.ccm.org.service.CcmOrgControlService;
import com.arjjs.ccm.modules.ccm.org.service.CcmOrgPersonService;
import com.arjjs.ccm.modules.sys.entity.Area;

/**
 * 自治组织人员管理Controller
 * @author liuyongjian
 * @version 2019-08-13
 */
@Controller
@RequestMapping(value = "${adminPath}/org/ccmOrgPerson")
public class CcmOrgPersonController extends BaseController {

	@Autowired
	private CcmOrgPersonService ccmOrgPersonService;
	
	@Autowired
	private CcmOrgControlService ccmOrgControlService;
	
	@ModelAttribute
	public CcmOrgPerson get(@RequestParam(required=false) String id) {
		CcmOrgPerson entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ccmOrgPersonService.get(id);
		}
		if (entity == null){
			entity = new CcmOrgPerson();
		}
		return entity;
	}
	
	@RequiresPermissions("org:ccmOrgPerson:view")
	@RequestMapping(value = {"list", ""})
	public String list(CcmOrgPerson ccmOrgPerson, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmOrgPerson> page = ccmOrgPersonService.findPage(new Page<CcmOrgPerson>(request, response), ccmOrgPerson); 
		model.addAttribute("page", page);
		return "ccm/org/ccmOrgPersonList";
	}
	


	
	@RequiresPermissions("org:ccmOrgPerson:view")
	@RequestMapping(value = { "orgpersonList" })
	public String orgpersonList() {
		return "ccm/org/orgpersonList";
	}
	
	@RequiresPermissions("org:ccmOrgMessage:view")
	@RequestMapping(value = { "orgmessageList" })
	public String orgmessageList() {
		return "ccm/org/orgmessageList";
	}
	
	@RequiresPermissions("org:ccmOrgMessage:view")
	@RequestMapping(value = { "ccmOrgMessageList" })
	public String ccmOrgMessageList(String id,String parentIds,String type,Model model) {
		CcmOrgPerson ccmOrgPerson=new CcmOrgPerson();
		ccmOrgPerson.setArea(new Area(id));
		ccmOrgPerson.setDuty(type);
		List<CcmOrgPerson> findList = ccmOrgPersonService.findList(ccmOrgPerson);
		model.addAttribute("list", findList);
		return "ccm/org/ccmOrgMessageList";
	}
	

	@RequiresPermissions("org:ccmOrgPerson:view")
	@RequestMapping(value = "form")
	public String form(CcmOrgPerson ccmOrgPerson, Model model) {
		model.addAttribute("ccmOrgPerson", ccmOrgPerson);
		
		List<CcmOrgControl> orgList = ccmOrgControlService.findList(new CcmOrgControl());
		
		model.addAttribute("list", orgList);
		return "ccm/org/ccmOrgPersonForm";
	}

	@RequiresPermissions("org:ccmOrgPerson:edit")
	@RequestMapping(value = "save")
	public String save(CcmOrgPerson ccmOrgPerson, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmOrgPerson)){
			return form(ccmOrgPerson, model);
		}
		ccmOrgPersonService.save(ccmOrgPerson);
		addMessage(redirectAttributes, "保存人员成功");
		return "redirect:"+Global.getAdminPath()+"/org/ccmOrgPerson/?repage";
	}
	
	@RequiresPermissions("org:ccmOrgPerson:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmOrgPerson ccmOrgPerson, RedirectAttributes redirectAttributes) {
		ccmOrgPersonService.delete(ccmOrgPerson);
		addMessage(redirectAttributes, "删除人员成功");
		return "redirect:"+Global.getAdminPath()+"/org/ccmOrgPerson/?repage";
	}

}