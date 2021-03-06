/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.house.web;

import com.arjjs.ccm.common.beanvalidator.BeanValidators;
import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.DateUtils;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.utils.excel.ImportExcel;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseHeresy;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseHeresyService;
import com.arjjs.ccm.modules.ccm.log.entity.CcmLogTail;
import com.arjjs.ccm.modules.ccm.log.service.CcmLogTailService;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPeople;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPeopleService;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.EntityTools;
import com.arjjs.ccm.tool.ExportExcel;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.ibatis.annotations.Param;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 涉教人员Controller
 * @author liang
 * @version 2018-03-07
 */
@Controller
@RequestMapping(value = "${adminPath}/house/ccmHouseHeresy")
public class CcmHouseHeresyController extends BaseController {

	@Autowired
	private CcmHouseHeresyService ccmHouseHeresyService;
	@Autowired
	private CcmPeopleService ccmPeopleService;
	@Autowired
	private CcmLogTailService ccmLogTailService;

	@ModelAttribute
	public CcmHouseHeresy get(@RequestParam(required=false) String id,@RequestParam(value = "peopleId", required = false) String peopleId) {
		CcmHouseHeresy entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ccmHouseHeresyService.get(id);
		}else if(StringUtils.isNotBlank(peopleId)) {
			entity = ccmHouseHeresyService.getPeopleALL(peopleId);
		}
		if (entity == null){
			entity = new CcmHouseHeresy();
			// 如果 peopleId 不为空 则 添加该ID
			if (StringUtils.isNotBlank(peopleId)) {
				entity.setPeopleId(peopleId);
			}
		}
		return entity;
	}
	
	@RequestMapping(value = {"list", ""})
	public String list(@Param("tableType")String tableType,CcmHouseHeresy ccmHouseHeresy, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<CcmHouseHeresy> page = ccmHouseHeresyService.findPage(new Page<CcmHouseHeresy>(request, response), ccmHouseHeresy); 
//		model.addAttribute("page", page);

		Page<CcmHouseHeresy> page = new Page();
		String permissionKey = request.getParameter("permissionKey");
		User user = UserUtils.getUser();
		if (user != null && "1".equals(user.getHasPermission()) ) {//有涉密权限
			page = ccmHouseHeresyService.findPage(new Page<CcmHouseHeresy>(request, response), ccmHouseHeresy);
		} else if (user != null && "0".equals(user.getHasPermission())) {//无涉密权限
			if (user.getPermissionKey() != null && user.getPermissionKey().equals(permissionKey)) {
				page = ccmHouseHeresyService.findPage(new Page<CcmHouseHeresy>(request, response), ccmHouseHeresy);
			} else {
				model.addAttribute("message", "涉密权限不正确！");
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("permissionKey", permissionKey);
		if(StringUtils.isBlank(tableType)) {
			return "ccm/house/ccmHouseHeresyList";
		}else {
			return "ccm/house/emphasis/ccmHouseHeresyList";
		}
	}

	@RequiresPermissions("house:ccmHouseHeresy:view")
	@RequestMapping(value = "form")
	public String form(CcmHouseHeresy ccmHouseHeresy, Model model) {
		CcmLogTail ccmLogTailDto = new CcmLogTail();
		ccmLogTailDto.setRelevanceId(ccmHouseHeresy.getId());
		ccmLogTailDto.setRelevanceTable("ccm_House_Heresy");
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
		model.addAttribute("ccmHouseHeresy", ccmHouseHeresy);
		return "ccm/house/ccmHouseHeresyForm";
	}

	@RequiresPermissions("house:ccmHouseHeresy:edit")
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, CcmHouseHeresy ccmHouseHeresy, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmHouseHeresy)){
//			return form(ccmHouseHeresy, model);
		}
		// 更新 当前人 是涉教人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmHouseHeresy.getPeopleId());
		ccmHouseHeresyService.save(ccmHouseHeresy,ccmPop);
		if(ccmPop!=null){
			ccmPop.setIsHeresy(1);
			ccmPeopleService.save(ccmPop);
		}
		addMessage(redirectAttributes, "保存涉教人员成功");
//		return "redirect:"+Global.getAdminPath()+"/house/ccmHouseHeresy/?repage";

		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存涉教人员成功");
		ccmPop.setMqTitle("新增重点人员(涉教人员)");
	}
	
	@RequiresPermissions("house:ccmHouseHeresy:edit")
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request, CcmHouseHeresy ccmHouseHeresy, RedirectAttributes redirectAttributes) {
		ccmHouseHeresyService.delete(ccmHouseHeresy);
		addMessage(redirectAttributes, "删除涉教人员成功");
		// 更新 当前人不再 是涉教人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmHouseHeresy.getPeopleId());
		String permissionKey = request.getParameter("permissionKey");
		if(ccmPop!=null){
			ccmPop.setIsHeresy(0);
			ccmPeopleService.save(ccmPop);
		}
		
		addMessage(redirectAttributes, "保存涉教人员成功");
		return "redirect:"+Global.getAdminPath()+"/house/ccmHouseHeresy/?repage&permissionKey=" + permissionKey;
	}
	
	//人员标记处
		@RequiresPermissions("house:ccmHouseHeresy:view")
		@RequestMapping(value = "specialform")
		public String specialform(CcmPeople ccmPeople, Model model) {
			model.addAttribute("ccmPeople", ccmPeople);
			CcmHouseHeresy heresy = ccmHouseHeresyService.getPeopleALL(ccmPeople.getId());
			if (heresy == null){
				heresy = new CcmHouseHeresy();
			}
			model.addAttribute("ccmHouseHeresy", heresy);
			return "/ccm/house/pop/ccmHousePoPHeresyForm";
		}
		
		//保存+标记
		@RequiresPermissions("house:ccmHouseHeresy:edit")
		@RequestMapping(value = "savePop")
		public String savePop(CcmHouseHeresy ccmHouseHeresy, Model model, RedirectAttributes redirectAttributes) {
			if (!beanValidator(model, ccmHouseHeresy)){
				return form(ccmHouseHeresy, model);
			}
			ccmHouseHeresyService.save(ccmHouseHeresy);
			// 更新 当前人 是涉教人员
			CcmPeople ccmPop = ccmPeopleService.get(ccmHouseHeresy.getPeopleId());
			if(ccmPop!=null){
				ccmPop.setIsHeresy(1);
				ccmPeopleService.save(ccmPop);
			}
			
			addMessage(redirectAttributes, "保存重点上访人员成功");
			return "redirect:"+Global.getAdminPath()+"/pop/ccmPeople/?repage";
			
			
			
		}

		/**
		 * 导出涉教人员数据
		 * 
		 * @param user
		 * @param request
		 * @param response
		 * @param redirectAttributes
		 * @return
		 */
		@RequiresPermissions("house:ccmHouseHeresy:view")
		@RequestMapping(value = "export", method = RequestMethod.POST)
		public String exportFile(CcmHouseHeresy ccmHouseHeresy, HttpServletRequest request,
				HttpServletResponse response, RedirectAttributes redirectAttributes) {
			String [] strArr={"姓名","联系方式","人口类型","现住门（楼）详址","公民身份号码","关注程度","痴迷程度","教派名称","是否参加转化培训班"};
			try {
				String fileName = "HeresyPeople" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

				List<CcmHouseHeresy> list = new ArrayList<CcmHouseHeresy>();
				String permissionKey = request.getParameter("permissionKey");
				User user = UserUtils.getUser();
				if (user != null && "1".equals(user.getHasPermission())) {//有涉密权限
					list = ccmHouseHeresyService.findList(ccmHouseHeresy);
				} else if (user != null && "0".equals(user.getHasPermission())) {//无涉密权限
					if (user.getPermissionKey() != null && user.getPermissionKey().equals(permissionKey)) {
						list = ccmHouseHeresyService.findList(ccmHouseHeresy);
					}
				}
				
//				List<CcmHouseHeresy> list = ccmHouseHeresyService.findList(ccmHouseHeresy);
				new ExportExcel("涉教人员数据", CcmHouseHeresy.class,strArr).setDataList(list).write(response, fileName).dispose();
				return null;
			} catch (Exception e) {
				addMessage(redirectAttributes, "导出涉教人员失败！失败信息：" + e.getMessage());
			}
			return "redirect:" + adminPath + "/house/ccmHouseHeresy/?repage";
		}
		
		
		/**
		 * 导入涉教人员数据
		 * 
		 * @param file
		 * @param redirectAttributes
		 * @return
		 */
		@RequiresPermissions("house:ccmHouseHeresy:view")
		@RequestMapping(value = "import", method = RequestMethod.POST)
		public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
			if (Global.isDemoMode()) {
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/house/ccmHouseHeresy/?repage";
			}
			try {
				int successNum = 0;
				int failureNum = 0;
				StringBuilder failureMsg = new StringBuilder();
				ImportExcel ei = new ImportExcel(file, 1, 0);
				List<CcmHouseHeresy> list = ei.getDataList(CcmHouseHeresy.class);
				for (CcmHouseHeresy HouseHeresy : list) {
					try {
						if(EntityTools.isEmpty(HouseHeresy)){
							continue;
						}
						
						if(StringUtils.isBlank(HouseHeresy.getAtteType()) ||
								StringUtils.isBlank(HouseHeresy.getLevel()) ||
								StringUtils.isBlank(HouseHeresy.getHeresyName()) ||
								HouseHeresy.getIsStudy()==null
						){
							StringBuilder str = new StringBuilder();
							str.append("(");
							if(StringUtils.isBlank(HouseHeresy.getAtteType())) {
								str.append("关注程度信息错误;");
							}
							if(StringUtils.isBlank(HouseHeresy.getLevel())) {
								str.append("痴迷程度信息错误;");
							}
							if(StringUtils.isBlank(HouseHeresy.getHeresyName())) {
								str.append("教派名称信息错误;");
							}
							if(HouseHeresy.getIsStudy()==null) {
								str.append("是否参加转化培训班信息错误;");
							}
							str.append(")");
							failureMsg.append("<br/>涉教人员名 " + HouseHeresy.getName() + " 导入失败：必填项为空。"+str.toString());
							continue;
						}
						
						// 如果当前用户的身份未填写或者为空或者身份证号码位数不够18位则应该进行 剔除
						if (StringUtils.isBlank(HouseHeresy.getIdent()) || HouseHeresy.getIdent().length() != 18) {
							failureMsg.append("<br/>实有人口名" + HouseHeresy.getName() + " 导入失败：" + "身份证信息错误。");
							HouseHeresy.setName(HouseHeresy.getName() + "，失败原因：身份证信息错误");
							failureNum++;
							continue;
						}
						
						CcmPeople ccmPeople=new CcmPeople();
						ccmPeople.setIdent(HouseHeresy.getIdent());
						List<CcmPeople> list1 = ccmPeopleService.findList(ccmPeople);
						CcmHouseHeresy HouseHeresyFind;

						if (list1.isEmpty()){
							failureMsg.append("<br/>涉教人员名 " + HouseHeresy.getName() + " 导入失败：实有人口表中无此人");
							continue;
						}else{
							ccmPeople.setId(list1.get(0).getId());
							ccmPeople.setUpdateBy(UserUtils.getUser());
							ccmPeople.setUpdateDate(new Date());
							ccmPeople.setIsHeresy(1);
							ccmPeopleService.updatePeople(ccmPeople);
							HouseHeresyFind=ccmHouseHeresyService.getPeopleALL(list1.get(0).getId());
							BeanValidators.validateWithException(validator, HouseHeresy);
							if(HouseHeresyFind == null){
								HouseHeresy.setPeopleId(list1.get(0).getId());
								ccmHouseHeresyService.save(HouseHeresy);
								successNum++;
							}else{
								failureMsg.append("<br/>涉教人员名 " + HouseHeresy.getName() + " 导入失败：记录已存在");
							}
						}
					} catch (ConstraintViolationException ex) {
						failureMsg.append("<br/>涉教人员名 " + HouseHeresy.getName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList) {
							failureMsg.append(message + "; ");
							failureNum++;
						}
					} catch (Exception ex) {
						failureMsg.append("<br/>登录名 " + HouseHeresy.getName() + " 导入失败：" + ex.getMessage());
					}
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条涉教人员，导入信息如下：");
				}
				addMessage(redirectAttributes, "已成功导入 " + successNum + " 条涉教人员" + failureMsg);
			} catch (Exception e) {
				addMessage(redirectAttributes, "导入涉教人员失败！失败信息：" + e.getMessage());
			}
			return "redirect:" + adminPath + "/house/ccmHouseHeresy/?repage";
		}
		 
		
		
}