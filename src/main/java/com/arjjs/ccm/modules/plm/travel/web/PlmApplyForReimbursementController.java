/**
 * Copyright &copy; 2012-2018 All rights reserved.
 */
package com.arjjs.ccm.modules.plm.travel.web;

import java.util.Map;

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
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.act.entity.Act;
import com.arjjs.ccm.modules.act.service.ActTaskService;
import com.arjjs.ccm.modules.act.service.ActUtConfigService;
import com.arjjs.ccm.modules.plm.act.entity.PlmAct;
import com.arjjs.ccm.modules.plm.act.service.PlmActService;
import com.arjjs.ccm.modules.plm.travel.entity.PlmApplyForReimbursement;
import com.arjjs.ccm.modules.plm.travel.service.PlmApplyForReimbursementService;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.PlmTypes;
import com.arjjs.ccm.tool.pdf.PdfDocumentGenerator;
import com.arjjs.ccm.tool.pdf.ResourceUitle;
import com.arjjs.ccm.tool.pdf.exception.DocumentGeneratingException;
import com.google.common.collect.Maps;

/**
 * 报销申请Controller
 * @author dongqikai
 * @version 2018-07-16
 */
@Controller
@RequestMapping(value = "${adminPath}/travel/plmApplyForReimbursement")
public class PlmApplyForReimbursementController extends BaseController {

	@Autowired
	private PlmApplyForReimbursementService plmApplyForReimbursementService;
	
	@Autowired
	private ActTaskService actTaskService;
	@Autowired	
	private PlmActService plmActService;
	@Autowired
	private ActUtConfigService<PlmApplyForReimbursement> actUtConfigService;
	
	@ModelAttribute
	public PlmApplyForReimbursement get(@RequestParam(required=false) String id) {
		PlmApplyForReimbursement entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = plmApplyForReimbursementService.get(id);
			//添加业务流程主表信息
			if(entity!=null) {
				PlmAct plmAct = plmActService.getByTable(entity.getId(), PlmTypes.REIMBURSEMENT_APPLY);
				if(plmAct!=null) {
					entity.setPlmAct(plmAct);
				}
			}
		}
		if (entity == null){
			entity = new PlmApplyForReimbursement();
		}
		return entity;
	}
	
	
	@RequestMapping(value = {"list", ""})
	public String list(PlmApplyForReimbursement plmApplyForReimbursement, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PlmApplyForReimbursement> page = plmApplyForReimbursementService.findPage(new Page<PlmApplyForReimbursement>(request, response), plmApplyForReimbursement); 
		model.addAttribute("page", page);
		return "plm/travel/plmApplyForReimbursementList";
	}

	
	@RequestMapping(value = "form")
	public String form(PlmApplyForReimbursement plmApplyForReimbursement, Model model) {
		String viewPage = "plmApplyForReimbursementForm";
		if (StringUtils.isBlank(plmApplyForReimbursement.getId())) {
			plmApplyForReimbursement.setApplyer(UserUtils.getUser());
			plmApplyForReimbursement.setDepartment(UserUtils.getUser().getOffice());
		}
		if (StringUtils.isNotBlank(plmApplyForReimbursement.getProcInsId())) {
			Act act = plmApplyForReimbursement.getAct();
			String taskDefKey = act.getTaskDefKey();
			if (act.isFinishTask()) {
				viewPage = "plmApplyForReimbursementView";
			} else if ("modify".equals(taskDefKey)) {
				viewPage = "plmApplyForReimbursementForm";
			}else if ("processEnd".equals(taskDefKey)) {
				viewPage = "plmApplyForReimbursementAudit";
				//终点无审核 去掉驳回按钮
				model.addAttribute("rejectedBtn", false);
			}else {
				viewPage = "plmApplyForReimbursementAudit";
				//终点审核 加驳回按钮
				model.addAttribute("rejectedBtn", true);
			} 
		}
		String cancelFlag = "0";
		if (StringUtils.isNotBlank(plmApplyForReimbursement.getCancelFlag()) && "02".equals(plmApplyForReimbursement.getCancelFlag())) {
			cancelFlag = "1";
		}
		model.addAttribute("cancelFlag", cancelFlag);
		model.addAttribute("plmApplyForReimbursement", plmApplyForReimbursement);
		return "plm/travel/" + viewPage;
	}

	
	@RequestMapping(value = "save")
	public String save(PlmApplyForReimbursement plmApplyForReimbursement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, plmApplyForReimbursement)){
			return form(plmApplyForReimbursement, model);
		}
		plmApplyForReimbursementService.save(plmApplyForReimbursement);
		//parameter ： 1、业务流程主表  2、流程配置id 3、业务表*
			plmActService.save(plmApplyForReimbursement.getPlmAct(),PlmTypes.REIMBURSEMENT_APPLY,plmApplyForReimbursement.getId());
		
		addMessage(redirectAttributes, "保存报销申请成功");
		return "redirect:" + Global.getAdminPath() + "/act/task/apply/";
	}
	
	
	@RequestMapping(value = "apply")
	public String apply(PlmApplyForReimbursement plmApplyForReimbursement, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, plmApplyForReimbursement)){
			return form(plmApplyForReimbursement, model);
		}
		plmApplyForReimbursementService.save(plmApplyForReimbursement);
		if (StringUtils.isBlank(plmApplyForReimbursement.getProcInsId())) {
			Map<String, String> returnMap = actUtConfigService.getProcInsId(PlmTypes.REIMBURSEMENT_APPLY, plmApplyForReimbursement, plmApplyForReimbursement.getId());
			plmApplyForReimbursement.setProcInsId(returnMap.get("procInsId"));
			plmApplyForReimbursementService.save(plmApplyForReimbursement);
			//4、保存业务流程主表*
			plmApplyForReimbursement.getPlmAct().setTitle(returnMap.get("title"));
			plmActService.save(plmApplyForReimbursement.getPlmAct(),PlmTypes.REIMBURSEMENT_APPLY,plmApplyForReimbursement.getId(),plmApplyForReimbursement.getProcInsId());
		} else {
			plmApplyForReimbursement.getAct().setComment(("yes".equals(plmApplyForReimbursement.getAct().getFlag())?"[重申] ":"[撤销] ")
					+ plmApplyForReimbursement.getAct().getComment());
			Map<String, Object> vars = Maps.newHashMap();
			
			vars.put("pass", "yes".equals(plmApplyForReimbursement.getAct().getFlag())? "1" : "0");
			actTaskService.complete(plmApplyForReimbursement.getAct().getTaskId(), plmApplyForReimbursement.getAct().getProcInsId(),
					plmApplyForReimbursement.getAct().getComment(), "", vars);
			//如果销毁，将业务流程主表状态置位“已销毁”*
			if(!"yes".equals(plmApplyForReimbursement.getAct().getFlag())){				
				plmActService.updateStatusToDestory(plmApplyForReimbursement.getPlmAct());
			}
		}
		addMessage(redirectAttributes, "提交报销申请成功");
		return "redirect:" + Global.getAdminPath() + "/act/task/apply/";
	}
	

	@RequestMapping(value = "saveAudit")
	public String saveAudit(PlmApplyForReimbursement plmApplyForReimbursement, Model model) {
		if (StringUtils.isBlank(plmApplyForReimbursement.getAct().getFlag())
				|| StringUtils.isBlank(plmApplyForReimbursement.getAct().getComment())){
			addMessage(model, "请填写审核意见。");
			return form(plmApplyForReimbursement, model);
		}
		// 对不同环节的业务逻辑进行操作*
				String taskDefKey = plmApplyForReimbursement.getAct().getTaskDefKey();
				// 最后一步流程且   需要审核
				if ("auditEnd".equals(taskDefKey)) {							
					// 若为最后一步审核，通过，将业务流程主表状态置位“已结束”
					if ("yes".equals(plmApplyForReimbursement.getAct().getFlag())) {
						plmActService.updateStatusToEnd(plmApplyForReimbursement.getPlmAct());      
					}
				}
				// 最后一步流程  不需要审核
				else if ("processEnd".equals(taskDefKey)) {				
					// 将业务流程主表状态置位“已结束”			
						plmActService.updateStatusToEnd(plmApplyForReimbursement.getPlmAct());      
					
				}
				// 未知环节，直接返回
				else if (StringUtils.isBlank(taskDefKey)) {
					return "redirect:" + adminPath + "/act/task/todo/";
				}
				 // 针对所有具有添加督办的环节，若为isSup字段不为空，添加督办信息
				if (StringUtils.isNotBlank(plmApplyForReimbursement.getPlmAct().getIsSup())) {
					plmActService.updateSup(plmApplyForReimbursement.getPlmAct());
				}
		plmApplyForReimbursementService.auditSave(plmApplyForReimbursement);
		return "redirect:" + adminPath + "/act/task/todo/";
	}
	
	
	@RequestMapping(value = "delete")
	public String delete(PlmApplyForReimbursement plmApplyForReimbursement, RedirectAttributes redirectAttributes) {
		plmApplyForReimbursementService.delete(plmApplyForReimbursement);
		addMessage(redirectAttributes, "删除报销申请成功");
		return "redirect:"+Global.getAdminPath()+"/travel/plmApplyForReimbursement/?repage";
	}

	@ResponseBody
	@RequestMapping(value = "printPdfIo")
	public void printPdfIo(PlmApplyForReimbursement plmApplyForReimbursement, Model model, RedirectAttributes redirectAttributes ,HttpServletRequest request ,HttpServletResponse response ) throws DocumentGeneratingException{
		
		
		      //将对象转成map 并将时间类型格式化"yyyy-MM-dd"
		      Map<String, Object> purmap=ResourceUitle.transBean2Map(plmApplyForReimbursement);	
		      
		      //有数据字典的  要换成名称plmApplyForReimbursement.type, 'reimburse_type', ''
		      purmap.put("type",DictUtils.getDictLabel(plmApplyForReimbursement.getType(),"reimburse_type",""));	
		      if(StringUtils.isNotBlank(plmApplyForReimbursement.getPlmAct().getIsSup())){
		    	  purmap.put("isSup",DictUtils.getDictLabel(plmApplyForReimbursement.getPlmAct().getIsSup(),"yes_no",""));
		      }
		      //流转信息  actProcIns
		      plmApplyForReimbursement.getAct().setProcInsId(plmApplyForReimbursement.getProcInsId());
		      
			//1: ProcInsId   2审批内容跨列数Colspan      3审批标题跨列数titleColspan
		      purmap.put("actProcIns",actTaskService.histoicTable(plmApplyForReimbursement.getProcInsId(), "5" ,"1"));
		      
		      
		     //获取项目根路径
		     String path=request.getSession().getServletContext().getRealPath("/");
		       // classpath 中模板路径
				String template = "WEB-INF/views/plm/travel/plmApplyForReimbursementViewTemplate.html";
				PdfDocumentGenerator pdfGenerator = new PdfDocumentGenerator();
				// 生成pdf 返回流			
				pdfGenerator.generate(template, purmap, path,response);			          		            
		              
		            		
	}
	
}