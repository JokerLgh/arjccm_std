/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.event.web;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventAmbi;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventCasedeal;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventIncident;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventRequest;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventAmbiService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventCasedealService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventRequestService;
import com.arjjs.ccm.modules.ccm.log.entity.CcmLogTail;
import com.arjjs.ccm.modules.ccm.log.service.CcmLogTailService;
import com.arjjs.ccm.modules.ccm.message.entity.CcmMessage;
import com.arjjs.ccm.modules.ccm.message.service.CcmMessageService;
import com.arjjs.ccm.modules.ccm.rest.web.CcmRestEvent;
import com.arjjs.ccm.modules.pbs.sys.utils.UserUtils;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.service.SystemService;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.MessageTools;
import com.google.common.collect.Maps;
import org.apache.ibatis.annotations.Param;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 事件处理Controller
 * 
 * @author arj
 * @version 2018-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/event/ccmEventCasedeal")
public class CcmEventCasedealController extends BaseController {

	@Autowired
	private CcmEventCasedealService ccmEventCasedealService;
	@Autowired
	private CcmEventIncidentService ccmEventIncidentService;
	@Autowired
	private CcmEventAmbiService ccmEventAmbiService;
	@Autowired
	private CcmEventRequestService ccmEventRequestService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CcmMessageService ccmMessageService;
	@Autowired
	private CcmLogTailService ccmLogTailService;
	/**
	 * 任务类型标识 自定义
	 */
	private final String TYPE="event_del_pop";

	// ccmEventCasedeal
	@ModelAttribute
	public CcmEventCasedeal get(@RequestParam(required = false) String id,
			@RequestParam(required = false) String eventIncidentId) {
		CcmEventCasedeal entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmEventCasedealService.get(id);
		}
		if (entity == null) {
			entity = new CcmEventCasedeal();
		}
		return entity;
	}

	/**
	 * @see 返回视图列表页面
	 * @param ccmEventCasedeal 事件处理原型
	 * @param request  
	 * @param response  
	 * @param model 
	 * @return
	 */
	@RequestMapping(value = { "list", "" })
	public String list(CcmEventCasedeal ccmEventCasedeal, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if(!"05".equals(ccmEventCasedeal.getHandleStatus())){
			ccmEventCasedeal.setStatus("01");//只显示未完结数据
		}
		Page<CcmEventCasedeal> page = ccmEventCasedealService.findPage(new Page<CcmEventCasedeal>(request, response),
				ccmEventCasedeal);
		model.addAttribute("page", page);
		if("01".equals(ccmEventCasedeal.getHandleStatus())){//待签收
			return "ccm/event/eventCasedeal/ccmEventCasedealUndisposedList";
		}else if("02".equals(ccmEventCasedeal.getHandleStatus())) {//处理中
			return "ccm/event/eventCasedeal/ccmEventCasedealBeingProcessedList";
		}else if("05".equals(ccmEventCasedeal.getHandleStatus())) {//已处理
			return "ccm/event/eventCasedeal/ccmEventCasedealProcessedList";
		}else {
			return "ccm/event/eventCasedeal/ccmEventCasedealList";
		}
	}
	
	/**
	 * @see 返回视图列表页面
	 * @param ccmEventCasedeal 事件督办处理
	 * @param request  
	 * @param response  
	 * @param model 
	 * @return
	 */
	@RequestMapping(value = "supervise")
	public String listSupervise(CcmEventCasedeal ccmEventCasedeal, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Page<CcmEventCasedeal> page = ccmEventCasedealService.findPage(new Page<CcmEventCasedeal>(request, response),
				ccmEventCasedeal);
		model.addAttribute("page", page);
		return "ccm/event/eventCasedeal/ccmEventCasedealSuperviseList";
	}

	/**
	 * @see  添加或修改表单页面
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:view")
	@RequestMapping(value = "form")
	public String form(CcmEventCasedeal ccmEventCasedeal, Model model) {
		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
		return "ccm/event/eventCasedeal/ccmEventCasedealForm";
	}

	/**
	 * @see 返回只读页面
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
//1113	@RequiresPermissions("event:ccmEventCasedeal:viewRead")
//	@RequestMapping(value = "readform")
//	public String readform(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormOnlyRead";
//	}
	
	
	
	
	
	/**
	 * @see 导航栏路线的事件处理添加页面-只读  （通过路线案事件登记界面进入用）
	 * @param ccmEventCasedeal 线路的事件处理原型
	 * @param model
	 * @return
	 */
//1115	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "readformSDLine")
//	public String readformSDLine(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormOnlyReadLine";
//	}
	
	/**
	 * @see 导航栏师生的事件处理添加页面-只读  （通过师生案事件登记界面进入用）
	 * @param ccmEventCasedeal 师生的事件处理原型
	 * @param model
	 * @return
	 */
//1116	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "readformSDStudent")
//	public String readformSDStudent(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormOnlyReadStudent";
//	}
	
	
	
	/**
	 * @see 导航栏路线的事件处理添加页面  （通过路线案事件登记界面进入用）
	 * @param ccmEventCasedeal 线路的事件处理原型
	 * @param model
	 * @return
	 */
//1119	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformSDLine")
//	public String dealformSDLine(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventIncident/ccmEventIncidentSDFormLine";
//	}
	/**
	 * @see 导航栏师生的事件处理添加页面  （通过师生案事件登记界面进入用）
	 * @param ccmEventCasedeal 师生的事件处理原型
	 * @param model
	 * @return
	 */
//111b	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformSDStudent")
//	public String dealformSDStudent(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventIncident/ccmEventIncidentSDFormStudent";
//	}
	
	/**
	 * @see 导航栏命案的事件处理添加页面  （通过命案案事件登记界面进入用）
	 * @param ccmEventCasedeal 命案的事件处理原型
	 * @param model
	 * @return
	 */
//1110	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformSDMurder")
//	public String dealformSDMurder(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventIncident/ccmEventIncidentSDFormMurder";
//	}
	
	/**
	 * 只读
	 * @see 导航栏命案的事件处理添加页面  （通过命案案事件登记界面进入用）
	 * @param ccmEventCasedeal 命案的事件处理原型
	 * @param model
	 * @return
	 */
//111a	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "readformSDMurder")
//	public String readformSDMurder(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventIncident/ccmEventIncidentSDFormMurderRead";
//	}
	
	/**
	 * @see 返回路线的案事件登记的处理添加页面  （通过路线案事件登记界面进入用）
	 * @param ccmEventCasedeal 线路的事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveLine")
	public String saveLine(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
			return form(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listLine/?repage";
	}
	/**
	 * @see 返回师生的案事件登记的处理添加页面  （通过师生案事件登记界面进入用）
	 * @param ccmEventCasedeal 师生的事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveStudent")
	public String saveStudent(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
			return form(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listStudent/?repage";
	}
	
	/**
	 * @see 返回命案的案事件登记的处理添加页面  （通过命案案事件登记界面进入用）
	 * @param ccmEventCasedeal 命案的事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveMurder")
	public String saveMurder(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
			return form(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listMurder/?repage";
	}
	
	/**
	 * @see 返回路线的案事件登记的处理添加页面  （通过路线案事件登记界面进入用）
	 * @param ccmEventCasedeal 线路的事件处理原型
	 * @param model
	 * @return
	 */
//1111	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformLine")
//	public String dealformLine(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormLine";
//	}
	
	/**
	 * @see 返回师生的案事件登记的处理添加页面  （通过师生案事件登记界面进入用）
	 * @param ccmEventCasedeal 师生的事件处理原型
	 * @param model
	 * @return
	 */
//1117	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformStudent")
//	public String dealformStudent(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormStudent";
//	}
	
	/**
	 * @see 返回命案的案事件登记的处理添加页面  （通过命案案事件登记界面进入用）
	 * @param ccmEventCasedeal 命案的事件处理原型
	 * @param model
	 * @return
	 */
//1112	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformMurder")
//	public String dealformMurder(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormMurder";
//	}
	
	
	/**
	 * @see 返回只读页面（通过案事件登记界面进入用）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
//1114	@RequiresPermissions("event:ccmEventCasedeal:viewRead")
//	@RequestMapping(value = "readformIncident")
//	public String readformIncident(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventCasedeal/ccmEventCasedealFormOnlyReadIncident";
//	}
	
	/**
	 * @see 返回案事件登记的处理添加页面  （通过案事件登记界面进入用）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:view")
	@RequestMapping(value = "dealform")
	public String dealform(CcmEventCasedeal ccmEventCasedeal, Model model) {
		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
		return "ccm/event/eventIncident/ccmEventIncidentSDForm";
	}
	
	/**
	 * @see 返回案事件登记的处理添加页面  （通过涉及线路的案事件登记界面进入用）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
//1118	@RequiresPermissions("event:ccmEventCasedeal:view")
//	@RequestMapping(value = "dealformIncident")
//	public String dealformIncident(CcmEventCasedeal ccmEventCasedeal, Model model) {
//		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
//		return "ccm/event/eventIncident/ccmEventIncidentSDFormIncident";
//	}

	/**
	 * @see 编辑事件处理页面
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request,
			HttpServletResponse response, CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) throws IOException {
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		if(!ccmEventCasedeal.getHandleStatus().equals("05")){//分配事件还没有反馈过

			User user = UserUtils.getUser();
			ccmLogTailService.eventFeedBackLogTail(ccmEventCasedeal,user);

			CcmMessage ccmMessage = new CcmMessage();
			ccmMessage.setType("02");//事件上报消息
			Date createDate = ccmEventCasedeal.getUpdateDate();
			String str = "MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(str);
			ccmMessage.setContent(sdf.format(createDate)+"："+ccmEventCasedeal.getCaseName()+"事件已被"+ccmEventCasedeal.getHandleUser().getName()+"处理");
			ccmMessage.setReadFlag("0");//未读
			ccmMessage.setObjId(ccmEventCasedeal.getId());
			ccmMessage.setUserId(ccmEventCasedeal.getCreateBy().getId());
			ccmEventCasedeal.setHandleStatus("05");
			ccmEventCasedealService.save(ccmEventCasedeal);
			//批量添加
			ccmMessageService.save(ccmMessage);
			CcmRestEvent.sendOneMessageToMq(ccmMessage);
		}

		/**修改对应的事件状态为进行中**/
//		CcmEventCasedeal ccmEventCasedeal2 = new CcmEventCasedeal();
//		ccmEventCasedeal2.setObjType(ccmEventCasedeal.getObjType());
//		ccmEventCasedeal2.setObjId(ccmEventCasedeal.getObjId());
//		List<CcmEventCasedeal> list = ccmEventCasedealService.findList(ccmEventCasedeal2);
//		if(ccmEventCasedeal.getManageType()!=null&&ccmEventCasedeal.getManageType().equals("1")){
//			CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventCasedeal.getObjId());
//			ccmEventIncident.setStatus("02");
//			ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());//修改案事件状态为：进行中
//		}else{
//			int flag = 0;
//			for(CcmEventCasedeal l:list){
//				if("05".equals(l.getHandleStatus())){
//					flag +=1;
//				}
//			}
//			if(list.size()==flag){
//				if ("ccm_event_incident".equals(ccmEventCasedeal.getObjType())) {
//					CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventCasedeal.getObjId());
//					ccmEventIncident.setStatus("02");
//					ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());//修改案事件状态为：进行中
//				} else if ("ccm_event_ambi".equals(ccmEventCasedeal.getObjType())) {
//					CcmEventAmbi ccmEventAmbi = ccmEventAmbiService.get(ccmEventCasedeal.getObjId());
//					ccmEventAmbi.setStatus("02");
//					ccmEventAmbiService.save(ccmEventAmbi);//修改矛盾纠纷状态为：进行中
//				}else if ("ccm_event_request".equals(ccmEventCasedeal.getObjType())) {
//					CcmEventRequest ccmEventRequest = ccmEventRequestService.get(ccmEventCasedeal.getObjId());
//					ccmEventRequest.setType("02");
//					ccmEventRequestService.save(ccmEventRequest);//修改矛盾纠纷状态为：进行中
//				}
//			}
//		}
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存成功");
	}

	/**
	 * @see 编辑事件处理页面-弹框
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveJump")
	public void saveJump(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		//return "redirect:" + Global.getAdminPath() + "/event/ccmEventCasedeal/?repage";
	}
	
	/**
	 * @see 案事件处理通用页面  （所有处理界面可以进入）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:view")
	@RequestMapping(value = "dealformCommon")
	public String dealformCommon(CcmEventCasedeal ccmEventCasedeal, Model model) {
		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
		return "ccm/event/eventIncident/ccmEventIncidentDealForm";
	}
	
	/**
	 * @see 案事件处理通用页面  （所有处理界面可以进入）Map
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:view")
	@RequestMapping(value = "dealformCommonMap")
	public String dealformCommonMap(CcmEventCasedeal ccmEventCasedeal, Model model) {
		if(ccmEventCasedeal.getHandleUser()!=null && ccmEventCasedeal.getHandleUser().getId()!=null && !"".equals(ccmEventCasedeal.getHandleUser().getId())){
			User user = new User();
			user = systemService.getUser(ccmEventCasedeal.getHandleUser().getId());
			ccmEventCasedeal.setHandleUser(user);
		}
		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
		return "ccm/event/eventIncident/ccmEventIncidentDealFormMap";
	}

	/**
	 * @see 用于下发事件处理任务页面 （所有处理界面可以进入）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveCasedealCommon")
	public void saveCasedealCommon(HttpServletRequest request,
			HttpServletResponse response, CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmEventCasedeal)) {
//			return dealform(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);

		/**修改对应的事件状态为进行中**/
		if ("ccm_event_incident".equals(ccmEventCasedeal.getObjType())) {
			CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventCasedeal.getObjId());
//			ccmEventIncident.setStatus("02");
			ccmEventIncident.setDoing("1");//将事件设为已分配
			ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());//修改案事件状态为：进行中
			ccmEventCasedeal.setCaseName(ccmEventIncident.getCaseName());

			//插入待签收消息
			CcmMessage ccmMessage = new CcmMessage();
			ccmMessage.setType("02");//事件上报消息
			Date createDate = new Date();
			String str = "MM-dd HH:mm:ss";
			SimpleDateFormat sdf = new SimpleDateFormat(str);
			ccmMessage.setContent(sdf.format(createDate)+"：事件待签收："+ccmEventIncident.getCaseName());
			ccmMessage.setReadFlag("0");//未读
			ccmMessage.setObjId(ccmEventCasedeal.getId());
			ccmMessage.setUserId(ccmEventCasedeal.getHandleUser().getId());
			//批量添加
			ccmMessageService.save(ccmMessage);
			CcmRestEvent.sendOneMessageToMq(ccmMessage);

			//处理人超期信息提醒
			ccmMessageService.deadlineMessage(ccmEventIncident,ccmEventCasedeal,ccmEventCasedeal.getHandleUser());
			//指派人超期信息提醒
			ccmMessageService.deadlineMessage(ccmEventIncident,ccmEventCasedeal,ccmEventCasedeal.getCreateBy());

		} else if ("ccm_event_ambi".equals(ccmEventCasedeal.getObjType())) {
			CcmEventAmbi ccmEventAmbi = ccmEventAmbiService.get(ccmEventCasedeal.getObjId());
			ccmEventAmbi.setStatus("02");
			ccmEventAmbiService.save(ccmEventAmbi);//修改矛盾纠纷状态为：进行中
		}else if ("ccm_event_request".equals(ccmEventCasedeal.getObjType())) {
			CcmEventRequest ccmEventRequest = ccmEventRequestService.get(ccmEventCasedeal.getObjId());
			ccmEventRequest.setType("02");
			ccmEventRequestService.save(ccmEventRequest);//修改矛盾纠纷状态为：进行中
		}
		User user = UserUtils.getUser();
		CcmLogTail ccmLogTail = new CcmLogTail();
//		ccmLogTail.setType("");
//		ccmLogTail.setTailPerson(ccmLogTail.getTailPerson());
		ccmLogTail.setTailTime(new Date());
		ccmLogTail.setTailContent(user.getName()+"将事件："+ccmEventCasedeal.getCaseName()+" 指派给："+ccmEventCasedeal.getHandleUser().getName());
		ccmLogTail.setTailCase("任务指派");
		ccmLogTail.setRelevanceTable("ccm_event_incident");
		ccmLogTail.setRelevanceId(ccmEventCasedeal.getObjId());
		ccmLogTailService.save(ccmLogTail);
		//通知到手机APP
		Map<String, String> map = Maps.newHashMap();
		map.put("type",TYPE);
		map.put("id", ccmEventCasedeal.getObjId());
		map.put("name", ccmEventCasedeal.getCaseName());
		map.put("objId", ccmEventCasedeal.getId());
		MessageTools.sendAppMsgByUserId(ccmEventCasedeal.getHandleUser().getId(), map);

		addMessage(redirectAttributes, "下发事件处理任务成功");
//		return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/list?repage";
		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "下发事件处理任务成功");

	}
	
	
	/**
	 * @see 用于下发事件处理任务页面 （所有处理界面可以进入）Map
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveCasedealCommonMap")
	public void saveCasedealCommonMap(HttpServletRequest request,
			HttpServletResponse response, CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
//			return dealform(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);

		/**修改对应的事件状态为进行中**/
		if ("ccm_event_incident".equals(ccmEventCasedeal.getObjType())) {
			CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventCasedeal.getObjId());
			ccmEventIncident.setStatus("02");
			ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());//修改案事件状态为：进行中
		} else if ("ccm_event_ambi".equals(ccmEventCasedeal.getObjType())) {
			CcmEventAmbi ccmEventAmbi = ccmEventAmbiService.get(ccmEventCasedeal.getObjId());
			ccmEventAmbi.setStatus("02");
			ccmEventAmbiService.save(ccmEventAmbi);//修改矛盾纠纷状态为：进行中
		}else if ("ccm_event_request".equals(ccmEventCasedeal.getObjType())) {
			CcmEventRequest ccmEventRequest = ccmEventRequestService.get(ccmEventCasedeal.getObjId());
			ccmEventRequest.setType("02");
			ccmEventRequestService.save(ccmEventRequest);//修改矛盾纠纷状态为：进行中
		}
		
		//addMessage(redirectAttributes, "下发事件处理任务成功");
		//return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/list?repage";
		//PrintWriter out = response.getWriter();
		//openWinExpDiv(out, "下发事件处理任务成功");
		//out.println("<script language='javascript'>parent.layer.close(parent.layer.getFrameIndex(window.name));</script>");	//关闭jBox
	}
	
	
	


	/**
	 * @see 案事件处理通用页面  （所有处理界面可以进入）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:view")
	@RequestMapping(value = "detail")
	public String detail(CcmEventCasedeal ccmEventCasedeal, Model model) {
		model.addAttribute("ccmEventCasedeal", ccmEventCasedeal);
		return "ccm/event/eventCasedeal/ccmEventCasedealDetail";
	}

	
	/**
	 * @see 编辑案事件登记的处理添加页面内提交的内容  （通过案事件登记界面进入用）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "savedeal")
	public String savedeal(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
			return dealform(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listLine?repage";
	}
	
	/**
	 * @see 编辑案事件登记的处理添加页面内提交的内容  （通过案事件登记界面进入用）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "saveCasedeal")
	public String saveCasedeal(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
			return dealform(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/list?repage";
	}

	/**
	 * @see 编辑案事件登记的处理添加页面内提交的内容  （通过涉及线路的案事件登记界面进入用）
	 * @param ccmEventCasedeal 事件处理原型
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "savedealIncident")
	public String savedealIncident(CcmEventCasedeal ccmEventCasedeal, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmEventCasedeal)) {
			return dealform(ccmEventCasedeal, model);
		}
		ccmEventCasedealService.save(ccmEventCasedeal);
		addMessage(redirectAttributes, "保存事件处理成功");
		return "redirect:" + Global.getAdminPath() + "/line/ccmLineProtect/?repage";
	}
	
	/**
	 * @see  删除事件处理信息
	 * @param ccmEventCasedeal 事件处理原型
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("event:ccmEventCasedeal:edit")
	@RequestMapping(value = "delete")
	public String delete(@Param("handleSta")String handleSta,CcmEventCasedeal ccmEventCasedeal, RedirectAttributes redirectAttributes) {
		ccmEventCasedealService.delete(ccmEventCasedeal);
		addMessage(redirectAttributes, "删除事件处理成功");
		if("01".equals(handleSta)){
			return "redirect:" + Global.getAdminPath() +"/event/ccmEventCasedeal/?repage&handleStatus=01";
		}else if("02".equals(handleSta)) {
			return "redirect:" + Global.getAdminPath() +"/event/ccmEventCasedeal/?repage&handleStatus=02";
		}else if("03".equals(handleSta)) {
			return "redirect:" + Global.getAdminPath() +"/event/ccmEventCasedeal/?repage&handleStatus=03";
		}else {
			return "redirect:" + Global.getAdminPath() +"/event/ccmEventCasedeal/?repage";
		}
	}
	@RequestMapping(value = "deleteSupervise")
	public String deleteSupervise(CcmEventCasedeal ccmEventCasedeal, RedirectAttributes redirectAttributes) {
		ccmEventCasedealService.delete(ccmEventCasedeal);
		addMessage(redirectAttributes, "删除事件处理成功");
		return "redirect:" + Global.getAdminPath() +"/event/ccmEventCasedeal/supervise?repage";
	}

	//签收
    @ResponseBody
	@RequestMapping(value = "signing")
	public Object signing(CcmEventCasedeal ccmEventCasedeal, RedirectAttributes redirectAttributes) {
//		ccmEventCasedeal.setStatus("02");//事件处理状态：处理中
//		ccmEventCasedeal.setManageType("01");//事件反馈状态：未办结
		ccmEventCasedeal.setHandleStatus("02");//任务处理状态:处理中
		ccmEventCasedealService.save(ccmEventCasedeal);
//		if ("ccm_event_incident".equals(ccmEventCasedeal.getObjType())) {
//			CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventCasedeal.getObjId());
//			ccmEventIncident.setStatus("02");
//			ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());//修改案事件状态为：进行中
//		}
		addMessage(redirectAttributes, "签收成功");

		User user = UserUtils.getUser();
		//插入日志
		ccmLogTailService.signingLogTail(ccmEventCasedeal,user);
		//消息表和MQ
		ccmMessageService.signingMessage(ccmEventCasedeal,user);
		HashMap<String,Object> result = Maps.newHashMapWithExpectedSize(2);
        result.put("code", "200");

        return result;
//		return "ccm/event/eventCasedeal/ccmEventCasedealList";
	}
    @ResponseBody
    @RequestMapping(value = "reject")
    public Object reject(CcmEventCasedeal ccmEventCasedeal, RedirectAttributes redirectAttributes) {
        ccmEventCasedeal.setHandleStatus("04");//任务处理状态:已拒绝
        ccmEventCasedealService.save(ccmEventCasedeal);
        addMessage(redirectAttributes, "已拒绝签收");

		CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventCasedeal.getObjId());
		ccmEventIncident.setDoing("0");
		ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
		User user = UserUtils.getUser();
		//插入日志
		ccmLogTailService.rejectLogTail(ccmEventCasedeal,user);
		//消息和MQ
		ccmMessageService.rejectMessage(ccmEventCasedeal,user);

		HashMap<String,Object> result = Maps.newHashMapWithExpectedSize(2);
        result.put("code", "200");

        return result;
//		return "ccm/event/eventCasedeal/ccmEventCasedealList";
    }

    @ResponseBody
    @RequestMapping(value = "getNumByHandelStatus")
    public Map getNumByHandelStatus(){
	    return ccmEventCasedealService.getNumByHandelStatus();
    }


    /*
    * 任务处理超期定时提醒
    * */
	public void detectDeadline(){
		//1.调用数据库查询即将超时的数据，任务截止时间-现在时间小于等于30分钟且大于当前时间，任务不是已反馈的
		List<CcmEventCasedeal> casedealList = ccmEventCasedealService.detectDeadline();
		//2.将数据推送至mq
		if(casedealList.size()>0){
			List<CcmMessage> list = new ArrayList<CcmMessage>();
			for (CcmEventCasedeal ccmEventCasedeal : casedealList) {
				String str = "MM-dd HH:mm:ss";
				SimpleDateFormat sdf = new SimpleDateFormat(str);
				Date date = new Date();
				// 处理人到期提醒
				CcmMessage ccmMessage = new CcmMessage();
				ccmMessage.preInsert();
				ccmMessage.setType("02");//任务消息
				ccmMessage.setContent(sdf.format(date)+"：任务："+ccmEventCasedeal.getCaseName()+" 即将超期，截止时间："+sdf.format(ccmEventCasedeal.getHandleDeadline()));
				ccmMessage.setReadFlag("0");//未读
				ccmMessage.setObjId(ccmEventCasedeal.getId());
				ccmMessage.setCreateBy(ccmEventCasedeal.getHandleUser());
				ccmMessage.setUpdateBy(ccmEventCasedeal.getHandleUser());
				ccmMessage.setUserId(ccmEventCasedeal.getHandleUser().getId());
				list.add(ccmMessage);
				// 发送人到期提醒
				CcmMessage ccmMessage2 = new CcmMessage();
				ccmMessage2.preInsert();
				ccmMessage2.setType("02");//任务消息
				ccmMessage2.setContent(sdf.format(date)+"：任务："+ccmEventCasedeal.getCaseName()+" 即将超期，截止时间："+sdf.format(ccmEventCasedeal.getHandleDeadline()));
				ccmMessage2.setReadFlag("0");//未读
				ccmMessage2.setObjId(ccmEventCasedeal.getId());
				ccmMessage2.setCreateBy(ccmEventCasedeal.getHandleUser());
				ccmMessage2.setUpdateBy(ccmEventCasedeal.getHandleUser());
				ccmMessage2.setUserId(ccmEventCasedeal.getCreateBy().getId());
				list.add(ccmMessage2);
			}
			//批量添加
			ccmMessageService.insertEventAll(list);
			CcmRestEvent.sendMessageToMq(list);
		}

	}
}