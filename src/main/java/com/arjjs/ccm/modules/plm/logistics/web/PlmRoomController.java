/**
 * Copyright &copy; 2012-2018 All rights reserved.
 */
package com.arjjs.ccm.modules.plm.logistics.web;

import java.util.ArrayList;
import java.util.Date;
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
import com.arjjs.ccm.common.utils.DateUtils;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.plm.act.entity.PlmAct;
import com.arjjs.ccm.modules.plm.logistics.entity.PlmRoom;
import com.arjjs.ccm.modules.plm.logistics.entity.PlmRoomApply;
import com.arjjs.ccm.modules.plm.logistics.service.PlmRoomMeetingApplyService;
import com.arjjs.ccm.modules.plm.logistics.service.PlmRoomService;
import com.arjjs.ccm.tool.Select2Type;

import net.sf.json.JSONArray;

/**
 * 会议/接待室管理Controller
 * @author fu
 * @version 2018-06-26
 */
@Controller
@RequestMapping(value = "${adminPath}/logistics/plmRoom")
public class PlmRoomController extends BaseController {

	@Autowired
	private PlmRoomService plmRoomService;
	@Autowired
	private PlmRoomMeetingApplyService plmRoomApplyService;
	
	@ModelAttribute
	public PlmRoom get(@RequestParam(required=false) String id) {
		PlmRoom entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = plmRoomService.get(id);
		}
		if (entity == null){
			entity = new PlmRoom();
		}
		return entity;
	}
	
	@RequiresPermissions("logistics:plmRoom:view")
	@RequestMapping(value = {"list", ""})
	public String list(PlmRoom plmRoom, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<PlmRoom> page = plmRoomService.findPage(new Page<PlmRoom>(request, response), plmRoom); 
		model.addAttribute("page", page);
		if("01".equals(plmRoom.getCategory())){
			return "plm/logistics/plmRoomMeetingList";
		}else{
			return "plm/logistics/plmRoomReceptionList";
		}
		
	}

	@RequiresPermissions("logistics:plmRoom:view")
	@RequestMapping(value = "form")
	public String form(PlmRoom plmRoom, Model model) {
		model.addAttribute("plmRoom", plmRoom);
		if("01".equals(plmRoom.getCategory())){
			return "plm/logistics/plmRoomMeetingForm";
		}else{
			return "plm/logistics/plmRoomReceptionForm";
		}
	}

	@RequiresPermissions("logistics:plmRoom:edit")
	@RequestMapping(value = "save")
	public String save(PlmRoom plmRoom, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, plmRoom)){
			return form(plmRoom, model);
		}
		plmRoomService.save(plmRoom);
		addMessage(redirectAttributes, "保存成功");
		if("01".equals(plmRoom.getCategory())){
			addMessage(redirectAttributes, "保存会议室成功");
			return "redirect:"+Global.getAdminPath()+"/logistics/plmRoom?category=01";
		}else{
			addMessage(redirectAttributes, "保存接待室成功");
			return "redirect:"+Global.getAdminPath()+"/logistics/plmRoom?category=02";
		}
	}
	
	@RequiresPermissions("logistics:plmRoom:edit")
	@RequestMapping(value = "delete")
	public String delete(PlmRoom plmRoom, RedirectAttributes redirectAttributes) {
		plmRoomService.delete(plmRoom);
		if("01".equals(plmRoom.getCategory())){
			addMessage(redirectAttributes, "删除会议室成功");
			return "redirect:"+Global.getAdminPath()+"/logistics/plmRoom?category=01";
		}else{
			addMessage(redirectAttributes, "删除接待室成功");
			return "redirect:"+Global.getAdminPath()+"/logistics/plmRoom?category=02";
		}
	}
	
	@RequiresPermissions("logistics:plmRoom:view")
	@RequestMapping(value = "scheduList")
	public String scheduList(PlmRoom plmRoom, Model model,PlmRoomApply plmRoomApply,HttpServletRequest request, HttpServletResponse response) {
		plmRoomApply.setRoom(plmRoom);
		//只查询已通过审核流程状态
//		PlmAct plmAct = new PlmAct();
//		plmAct.setStatus("04");
//		plmRoomApply.setPlmAct(plmAct);
//		plmRoomApply.setCreateBy(null);
		List<PlmRoomApply> applylist = plmRoomApplyService.getroombyuserIdlist(plmRoomApply);
		List<Object>  list=new ArrayList<Object>(); 
		for (PlmRoomApply plmRoomApply2 : applylist) {
			Date start = plmRoomApply2.getStartTime();
			Date end = plmRoomApply2.getEndTime();
			int day = 1;
			if(DateUtils.formatDate(start).equals(DateUtils.formatDate(end))){
				day = 0;
			}
			list.add(new Object[]{plmRoomApply2.getId(),plmRoomApply2.getSubject().replaceAll("\'(.*?)\'", "‘$1’").replaceAll("\'", "‘"),plmRoomApply2.getStartTime().getTime(),plmRoomApply2.getEndTime().getTime(),0,day,0,0,1,plmRoomApply2.getCategory()});
		}
		JSONArray json = JSONArray.fromObject(list);
		model.addAttribute("list", json);	
		String showdate=String.valueOf(System.currentTimeMillis());
		model.addAttribute("date", showdate);	
		return "plm/logistics/plmCalendar";
	}	
	
	@ResponseBody
	@RequestMapping(value = {"selectList"})
	public List<Select2Type> selectList(Model model,PlmRoom plmRoom) {
		List<Select2Type> list = plmRoomService.findSelect2Type(plmRoom); 
		return list;
	}
}