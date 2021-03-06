/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.log.web;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.log.entity.CcmLogTail;
import com.arjjs.ccm.modules.ccm.log.service.CcmLogTailService;
import com.arjjs.ccm.modules.pbs.sys.utils.UserUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.google.common.collect.Maps;
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
 * 跟踪信息Controller
 * 
 * @author arj
 * @version 2018-01-23
 */
@Controller
@RequestMapping(value = "${adminPath}/log/ccmLogTail")
public class CcmLogTailController extends BaseController {

	@Autowired
	private CcmLogTailService ccmLogTailService;

	@ModelAttribute
	public CcmLogTail get(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "relevance_id", required = false) String relevance_id,
			@RequestParam(value = "relevance_table", required = false) String relevance_table) {
		CcmLogTail entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmLogTailService.get(id);
		}
		if (entity == null) {
			entity = new CcmLogTail();
			// 传入值 进行数据填充 进行添加页面展示
			entity.setRelevanceId(relevance_id);
			entity.setRelevanceTable(relevance_table);
		}
		return entity;
	}

	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = { "list", "" })
	public String list(CcmLogTail ccmLogTail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmLogTail> page = ccmLogTailService.findPage(new Page<CcmLogTail>(request, response), ccmLogTail);
		model.addAttribute("page", page);
		return "ccm/log/ccmLogTailList";
	}

	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "form")
	public String form(CcmLogTail ccmLogTail, Model model) {
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailForm";
	}

	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "formPro")
	public String formPro(CcmLogTail ccmLogTail, Model model) {
		// 具体业务日志的页面
		String pageFormLog = CommUtil.GetPageFormForLog(ccmLogTail.getRelevanceTable());
		String pageListLog = CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable());
		
		// 具体业务日志的名称
		String pagePreString = CommUtil.GetStringForLog(ccmLogTail.getRelevanceTable());
		model.addAttribute("formPage", pageFormLog);
		model.addAttribute("listPage", pageListLog);
		
		model.addAttribute("PrevString", pagePreString);
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailProForm";
	}
	
	//老年人跟踪记录
	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "formProOlder")
	public String formProOlder(CcmLogTail ccmLogTail, Model model) {
		// 具体业务日志的页面
		String pageFormLog = CommUtil.GetPageFormForLog(ccmLogTail.getRelevanceTable());
		String pageListLog = CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable());
		
		// 具体业务日志的名称
		String pagePreString = CommUtil.GetStringForLog(ccmLogTail.getRelevanceTable());
		model.addAttribute("formPage", pageFormLog);
		model.addAttribute("listPage", pageListLog);
		
		model.addAttribute("PrevString", pagePreString);
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailProOlderForm";
	}
	//老年人跟踪记录保存
	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "saveProOlder")
	public void saveProOlder(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
			//return formPro(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		//return "redirect:" + Global.getAdminPath() + "/pop/ccmPeople/listOlder/?repage";
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存跟踪信息成功");
	}
	//特殊关怀跟踪记录
	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "formProCare")
	public String formProCare(CcmLogTail ccmLogTail, Model model) {
		// 具体业务日志的页面
		String pageFormLog = CommUtil.GetPageFormForLog(ccmLogTail.getRelevanceTable());
		String pageListLog = CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable());
		
		// 具体业务日志的名称
		String pagePreString = CommUtil.GetStringForLog(ccmLogTail.getRelevanceTable());
		model.addAttribute("formPage", pageFormLog);
		model.addAttribute("listPage", pageListLog);
		
		model.addAttribute("PrevString", pagePreString);
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailProCareForm";
	}
	//特殊关怀跟踪记录保存
	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "saveProCare")
	public void saveProCare(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
			//return formPro(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		//return "redirect:" + Global.getAdminPath() + "/pop/ccmPeople/listCare/?repage";
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存跟踪信息成功");
	}
	//党员跟踪记录
	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "formProCommunist")
	public String formProCommunist(CcmLogTail ccmLogTail, Model model) {
		// 具体业务日志的页面
		String pageFormLog = CommUtil.GetPageFormForLog(ccmLogTail.getRelevanceTable());
		String pageListLog = CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable());
		
		// 具体业务日志的名称
		String pagePreString = CommUtil.GetStringForLog(ccmLogTail.getRelevanceTable());
		model.addAttribute("formPage", pageFormLog);
		model.addAttribute("listPage", pageListLog);
		
		model.addAttribute("PrevString", pagePreString);
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailProCommunistForm";
	}
	//党员跟踪记录保存
	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "saveProCommunist")
	public void saveProCommunist(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
			//return formPro(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		//return "redirect:" + Global.getAdminPath() + "/pop/ccmPeople/listCommunist/?repage";
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存跟踪信息成功");
	}
	//常住人口跟踪记录
	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "formProPermanent")
	public String formProPermanent(CcmLogTail ccmLogTail, Model model) {
		// 具体业务日志的页面
		String pageFormLog = CommUtil.GetPageFormForLog(ccmLogTail.getRelevanceTable());
		String pageListLog = CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable());
		
		// 具体业务日志的名称
		String pagePreString = CommUtil.GetStringForLog(ccmLogTail.getRelevanceTable());
		model.addAttribute("formPage", pageFormLog);
		model.addAttribute("listPage", pageListLog);
		
		model.addAttribute("PrevString", pagePreString);
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailProPermanentForm";
	}
	//常住人口跟踪记录保存
	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "saveProPermanent")
	public void saveProPermanent(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
			//return formPro(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		//return "redirect:" + Global.getAdminPath() + "/pop/ccmPermanent/?repage";
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存跟踪信息成功");
	}
		
		
		
		
		
		
		
		
		
		
	
	//户籍人口日志
	@RequiresPermissions("log:ccmLogTail:view")
	@RequestMapping(value = "formProAccount")
	public String formProAccount(CcmLogTail ccmLogTail, Model model) {
		// 具体业务日志的页面
		String pageFormLog = CommUtil.GetPageFormForLog(ccmLogTail.getRelevanceTable());
		String pageListLog = CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable());
		
		// 具体业务日志的名称
		String pagePreString = CommUtil.GetStringForLog(ccmLogTail.getRelevanceTable());
		model.addAttribute("formPage", pageFormLog);
		model.addAttribute("listPage", pageListLog);
		
		model.addAttribute("PrevString", pagePreString);
		model.addAttribute("ccmLogTail", ccmLogTail);
		return "ccm/log/ccmLogTailProFormAccount";
	}

	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "savePro")
	public String savePro(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
//			return formPro(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
//		return "redirect:" + Global.getAdminPath() + CommUtil.GetPageForLog(ccmLogTail.getRelevanceTable())	+ "/?repage";
		return "redirect:" + Global.getAdminPath() + "/report/riskIncident"	+ "/?repage";
//		PrintWriter out = response.getWriter();
//		CommUtil.openWinExpDiv(out, "保存跟踪信息成功");
	}

	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "saveProdiv")
	public void saveProdiv(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
//			return formPro(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存跟踪信息成功");
	}


	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "saveProWorkReport")
	public String saveProWorkReport(HttpServletRequest request, HttpServletResponse response, CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmLogTail)) {
//			return formPro(ccmLogTail, model);
		}
		ccmLogTail.setTailPerson(UserUtils.getUser().getName());
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		return "redirect:" + Global.getAdminPath() + "/sys/ccmWorkReport/view?id=" + ccmLogTail.getRelevanceId() + "&repage";
	}

	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "save")
	public String save(CcmLogTail ccmLogTail, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmLogTail)) {
			return form(ccmLogTail, model);
		}
		ccmLogTailService.save(ccmLogTail);
		addMessage(redirectAttributes, "保存跟踪信息成功");
		return "redirect:" + Global.getAdminPath() + "/log/ccmLogTail/?repage";
	}

	@RequiresPermissions("log:ccmLogTail:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmLogTail ccmLogTail, RedirectAttributes redirectAttributes) {
		ccmLogTailService.delete(ccmLogTail);
		addMessage(redirectAttributes, "删除跟踪信息成功");
		return "redirect:" + Global.getAdminPath() + "/log/ccmLogTail/list?relevance_id="+ccmLogTail.getRelevanceId()+"&relevance_table="+ccmLogTail.getRelevanceTable()+"&repage";
	}

	@ResponseBody
	@RequestMapping(value = "saveIncident")
	public int saveIncident(CcmLogTail ccmLogTail,String ids) {
		return ccmLogTailService.insertLogTail(ccmLogTail, ids);
	}

    @ResponseBody
    @RequestMapping(value = "queryRecord")
    public List<CcmLogTail> queryRecord(CcmLogTail ccmLogTail) {
        return ccmLogTailService.findListByObject(ccmLogTail);
    }
	
}