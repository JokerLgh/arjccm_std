/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.iot.configure.web;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.iot.configure.entity.CcmReportConfigure;
import com.arjjs.ccm.modules.iot.configure.service.CcmReportConfigureService;
import com.arjjs.ccm.tool.CommUtil;

/**
 * 报警配置Controller
 * 
 * @author cby
 * @version 2019-07-25
 */
@Controller
@RequestMapping(value = "${adminPath}/configure/ccmReportConfigure")
public class CcmReportConfigureController extends BaseController {

	@Autowired
	private CcmReportConfigureService ccmReportConfigureService;

	@ModelAttribute
	public CcmReportConfigure get(@RequestParam(required = false) String id) {
		CcmReportConfigure entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmReportConfigureService.get(id);
		}
		if (entity == null) {
			entity = new CcmReportConfigure();
		}
		return entity;
	}

	@RequiresPermissions("configure:ccmReportConfigure:view")
	@RequestMapping(value = { "list", "" })
	public String list(CcmReportConfigure ccmReportConfigure, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<CcmReportConfigure> page = ccmReportConfigureService
				.findPage(new Page<CcmReportConfigure>(request, response), ccmReportConfigure);
		model.addAttribute("page", page);
		return "iot/configure/ccmReportConfigureList";
	}

	@RequiresPermissions("configure:ccmReportConfigure:view")
	@RequestMapping(value = "form")
	public String form(CcmReportConfigure ccmReportConfigure, Model model) {
		model.addAttribute("ccmReportConfigure", ccmReportConfigure);
		return "iot/configure/ccmReportConfigureForm";
	}

	@RequiresPermissions("configure:ccmReportConfigure:view")
	@RequestMapping(value = "detail")
	public String detail(CcmReportConfigure ccmReportConfigure, Model model) {
		model.addAttribute("ccmReportConfigure", ccmReportConfigure);
		return "iot/configure/ccmReportConfigureDetail";
	}

	@RequiresPermissions("configure:ccmReportConfigure:edit")
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, CcmReportConfigure ccmReportConfigure,
			Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmReportConfigure)) {
			// return form(ccmReportConfigure, model);
		}

		List<CcmReportConfigure> list = ccmReportConfigureService.findList(ccmReportConfigure);
		if(list.size()>0){
			addMessage(redirectAttributes, "保存报警配置失败,相关报警类型只能保存一次");
			PrintWriter out = response.getWriter();
			CommUtil.openWinExpDiv(out, "保存报警配置失败,相关报警类型只能保存一次");
			return;
		}
		if (StringUtils.isNotEmpty(ccmReportConfigure.getReportMusic())) {
			String reportMusic = ccmReportConfigure.getReportMusic();
			int spilt = reportMusic.lastIndexOf("/");
			ccmReportConfigure.setRemarks(reportMusic.substring(spilt + 1, reportMusic.length()));
		}else {
			ccmReportConfigure.setRemarks("");
		}

		ccmReportConfigureService.save(ccmReportConfigure);
		addMessage(redirectAttributes, "保存报警配置成功");
		// return
		// "redirect:"+Global.getAdminPath()+"/configure/ccmReportConfigure/?repage";

		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存报警配置成功");

	}

	@RequiresPermissions("configure:ccmReportConfigure:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmReportConfigure ccmReportConfigure, RedirectAttributes redirectAttributes) {
		ccmReportConfigureService.delete(ccmReportConfigure);
		addMessage(redirectAttributes, "删除报警配置成功");
		return "redirect:" + Global.getAdminPath() + "/configure/ccmReportConfigure/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "getList")
	public List getList(CcmReportConfigure ccmReportConfigure, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		List<CcmReportConfigure> list = ccmReportConfigureService.findList(ccmReportConfigure);
		return list;
	}
}