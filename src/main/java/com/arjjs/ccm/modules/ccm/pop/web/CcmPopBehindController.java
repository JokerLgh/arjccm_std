/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.pop.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.arjjs.ccm.common.beanvalidator.BeanValidators;
import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.common.utils.DateUtils;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.tool.ExportExcel;
import com.arjjs.ccm.common.utils.excel.ImportExcel;
import com.arjjs.ccm.modules.ccm.log.entity.CcmLogTail;
import com.arjjs.ccm.modules.ccm.log.service.CcmLogTailService;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPeople;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPopBehind;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPeopleService;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPopBehindService;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.CommUtil;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * 留守人员Controller
 * 
 * @author arj
 * @version 2018-01-06
 */
@Controller
@RequestMapping(value = "${adminPath}/pop/ccmPopBehind")
public class CcmPopBehindController extends BaseController {

	@Autowired
	private CcmPopBehindService ccmPopBehindService;
	@Autowired
	private CcmPeopleService ccmPeopleService;
	@Autowired
	private CcmLogTailService ccmLogTailService;
	

	@ModelAttribute
	public CcmPopBehind get(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "peopleId", required = false) String peopleId) {
		CcmPopBehind entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmPopBehindService.get(id);
		} else if (StringUtils.isNotBlank(peopleId)) {
			entity = ccmPopBehindService.getPeopleALL(peopleId);
		}
		if (entity == null) {
			entity = new CcmPopBehind();
			// 如果 peopleId 不为空 则 添加该ID
			if (StringUtils.isNotBlank(peopleId)) {
				entity.setPeopleId(peopleId);
			}
		}
		return entity;
	}

	@RequiresPermissions("pop:ccmPopBehind:view")
	@RequestMapping(value = { "list", "" })
	public String list(CcmPopBehind ccmPopBehind, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<CcmPopBehind> page = ccmPopBehindService.findPage(new Page<CcmPopBehind>(request, response), ccmPopBehind);
		model.addAttribute("page", page);
		return "ccm/pop/ccmPopBehindList";
	}

	@RequiresPermissions("pop:ccmPopBehind:view")
	@RequestMapping(value = "form")
	public String form(CcmPopBehind ccmPopBehind, Model model) {
		// 创建 查询对象 建立查询条件
		CcmLogTail ccmLogTailDto = new CcmLogTail();
		ccmLogTailDto.setRelevanceId(ccmPopBehind.getId());
		ccmLogTailDto.setRelevanceTable("ccm_pop_behind");
		List<CcmLogTail > ccmLogTailList = ccmLogTailService.findListByObject(ccmLogTailDto);
		// 返回查询结果
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"createBy","updateBy","currentUser","dbName","global","page","createDate","updateDate","sqlMap"});
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String jsonDocumentList = JSONArray.fromObject(ccmLogTailList,config).toString(); 
		model.addAttribute("documentList", jsonDocumentList);
		model.addAttribute("documentNumber", ccmLogTailList.size());
		
		model.addAttribute("ccmLogTailList", ccmLogTailList);
		model.addAttribute("ccmPopBehind", ccmPopBehind);
		return "ccm/pop/ccmPopBehindForm";
	}

	@RequiresPermissions("pop:ccmPopBehind:edit")
	@RequestMapping(value = "save")
	public void save(CcmPopBehind ccmPopBehind, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (!beanValidator(model, ccmPopBehind)) {
			//return form(ccmPopBehind, model);
		}
		ccmPopBehindService.save(ccmPopBehind);
		// 更新 当前人 是 留守人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmPopBehind.getPeopleId());
		if(ccmPop!=null){
			ccmPop.setIsBehind(1);
			ccmPeopleService.save(ccmPop);
		} 
		addMessage(redirectAttributes, "保存留守人员成功");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommUtil.openWinExpDiv(out, "保存留守人员成功");
		
		//return "redirect:" + Global.getAdminPath() + "/pop/ccmPopBehind/?repage";
	}

	@RequiresPermissions("pop:ccmPopBehind:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmPopBehind ccmPopBehind, RedirectAttributes redirectAttributes) {
		ccmPopBehindService.delete(ccmPopBehind);
		CcmPeople ccmPop = ccmPeopleService.get(ccmPopBehind.getPeopleId());
		if(ccmPop!=null){
			ccmPop.setIsBehind(0);
			ccmPeopleService.save(ccmPop);
		}
		
		addMessage(redirectAttributes, "删除留守人员成功");
		return "redirect:" + Global.getAdminPath() + "/pop/ccmPopBehind/?repage";
	}
	
	@RequiresPermissions("pop:ccmPopBehind:edit")
	@RequestMapping(value = "savePop")
	public String savePop(CcmPopBehind ccmPopBehind, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmPopBehind)) {
			return form(ccmPopBehind, model);
		}
		ccmPopBehindService.save(ccmPopBehind);
		// 更新 当前人 是 留守人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmPopBehind.getPeopleId());
		if(ccmPop!=null){
			ccmPop.setIsBehind(1);
			ccmPeopleService.save(ccmPop);
		}
		
		addMessage(redirectAttributes, "保存留守人员成功");

		return "redirect:" + Global.getAdminPath() + "/pop/ccmPeople/?repage";
	}

	@RequiresPermissions("pop:ccmPopBehind:view")
	@RequestMapping(value = "specialform")
	public String specialform(CcmPeople ccmPeople, Model model) {
		model.addAttribute("ccmPeople", ccmPeople);
		CcmPopBehind psychogeny = ccmPopBehindService.getPeopleALL(ccmPeople.getId());
		if (psychogeny == null) {
			psychogeny = new CcmPopBehind();
		}
		model.addAttribute("ccmPopBehind", psychogeny);
		return "/ccm/house/pop/ccmHousePoPBehindForm";
	}

	/**
	 * 导出留守人员数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pop:ccmPopBehind:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(CcmPopBehind ccmPopBehind, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String [] strArr={"姓名","联系方式","人口类型","现住门（楼）详址","公民身份号码"};
		try {
			String fileName = "BehindPeople" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			List<CcmPopBehind> list = ccmPopBehindService.findList(ccmPopBehind);
			new ExportExcel("留守人员数据", CcmPopBehind.class,strArr).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出留守人员失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/pop/ccmPopBehind/?repage";
	}

	/**
	 * 导入留守人员数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("pop:ccmPopBehind:view")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/pop/ccmPopBehind/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CcmPopBehind> list = ei.getDataList(CcmPopBehind.class);
			for (CcmPopBehind PopBehind : list) {
				try {
					
					// 如果当前用户的身份未填写或者为空或者身份证号码位数不够18位则应该进行 剔除
					if (StringUtils.isBlank(PopBehind.getIdent()) || PopBehind.getIdent().length() != 18) {
						failureMsg.append("<br/>实有人口名" + PopBehind.getName() + " 导入失败：" + "身份证信息错误。");
						PopBehind.setName(PopBehind.getName() + "，失败原因：身份证信息错误");
						failureNum++;
						continue;
					}
					
					CcmPeople ccmPeople=new CcmPeople();
					ccmPeople.setIdent(PopBehind.getIdent());
					List<CcmPeople> list1 = ccmPeopleService.findList(ccmPeople);
					CcmPopBehind popBehindFind;

					if (list1.isEmpty()){
						popBehindFind=null;
					}else{
						ccmPeople.setId(list1.get(0).getId());
						ccmPeople.setUpdateBy(UserUtils.getUser());
						ccmPeople.setUpdateDate(new Date());
						ccmPeople.setIsBehind(1);
						ccmPeopleService.updatePeople(ccmPeople);
						popBehindFind=ccmPopBehindService.getPeopleALL(list1.get(0).getId());
						BeanValidators.validateWithException(validator, PopBehind);
						if(popBehindFind == null){
							PopBehind.setPeopleId(list1.get(0).getId());
							ccmPopBehindService.save(PopBehind);
							successNum++;
						}else{
							failureMsg.append("<br/>留守人员名 " + PopBehind.getName() + " 导入失败：记录已存在");
						}
					}
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>留守人员名 " + PopBehind.getName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					failureMsg.append("<br/>登录名 " + PopBehind.getName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条留守人员，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条留守人员" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入留守人员失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/pop/ccmPopBehind/?repage";
	}
	 
	
}