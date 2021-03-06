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
import com.arjjs.ccm.modules.ccm.event.service.CcmEventCasedealService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseRelease;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseReleaseService;
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
 * 安置帮教人员Controller
 * 
 * @author arj
 * @version 2018-01-04
 */
@Controller
@RequestMapping(value = "${adminPath}/house/ccmHouseRelease")
public class CcmHouseReleaseController extends BaseController {

	@Autowired
	private CcmHouseReleaseService ccmHouseReleaseService;
	@Autowired
	private CcmPeopleService ccmPeopleService;
	@Autowired
	private CcmLogTailService ccmLogTailService;
	@Autowired
	private CcmEventIncidentService ccmEventIncidentService;
	@Autowired
	private CcmEventCasedealService ccmEventCasedealService;

	@ModelAttribute
	public CcmHouseRelease get(@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "peopleId", required = false) String peopleId) {
		CcmHouseRelease entity = null;

		if (StringUtils.isNotBlank(id)) {
			entity = ccmHouseReleaseService.get(id);
		} else if (StringUtils.isNotBlank(peopleId)) {
			entity = ccmHouseReleaseService.getPeopleALL(peopleId);
		}
		if (entity == null) {
			entity = new CcmHouseRelease();
			// 如果 peopleId 不为空 则 添加该ID
			if (StringUtils.isNotBlank(peopleId)) {
				entity.setPeopleId(peopleId);
			}
		}
		return entity;
	}

	/**
	 * @see 列表页
	 * @param ccmHouseRelease
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "list", "" })
	public String list(@Param("tableType")String tableType,CcmHouseRelease ccmHouseRelease, HttpServletRequest request, HttpServletResponse response,
			Model model) {
//		Page<CcmHouseRelease> page = ccmHouseReleaseService.findPage(new Page<CcmHouseRelease>(request, response),
//				ccmHouseRelease);
//		model.addAttribute("page", page);

		Page<CcmHouseRelease> page = new Page();
		String permissionKey = request.getParameter("permissionKey");
		User user = UserUtils.getUser();
		if (user != null && "1".equals(user.getHasPermission()) ) {//有涉密权限
			page = ccmHouseReleaseService.findPage(new Page<CcmHouseRelease>(request, response), ccmHouseRelease);
		} else if (user != null && "0".equals(user.getHasPermission())) {//无涉密权限
			if (user.getPermissionKey() != null && user.getPermissionKey().equals(permissionKey)) {
				page = ccmHouseReleaseService.findPage(new Page<CcmHouseRelease>(request, response), ccmHouseRelease);
			} else {
				model.addAttribute("message", "涉密权限不正确！");
			}
		}
		model.addAttribute("page", page);
		model.addAttribute("permissionKey", permissionKey);
		if(StringUtils.isBlank(tableType)) {
			return "ccm/house/ccmHouseReleaseList";
		}else {
			return "ccm/house/emphasis/ccmHouseReleaseList";
		}
	}

	/**
	 * @see  管理 - 更新表单页面
	 * @param ccmHouseRelease
	 * @param model
	 * @return
	 */
	@RequiresPermissions("house:ccmHouseRelease:view")
	@RequestMapping(value = "form")
	public String form(CcmHouseRelease ccmHouseRelease, Model model) {
		// 创建 查询对象 建立查询条件
		CcmLogTail ccmLogTailDto = new CcmLogTail();
		ccmLogTailDto.setRelevanceId(ccmHouseRelease.getId());
		ccmLogTailDto.setRelevanceTable("ccm_house_release");
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
		model.addAttribute("ccmHouseRelease", ccmHouseRelease);
		return "ccm/house/ccmHouseReleaseForm";
	}

	/**
	 * @see 管理 更新操作
	 * @param ccmHouseRelease
	 * @param model
	 * @param redirectAttributes
	 * @return
	 * @throws IOException 
	 */
	@RequiresPermissions("house:ccmHouseRelease:edit")
	@RequestMapping(value = "save")
	public void save(HttpServletRequest request, HttpServletResponse response, CcmHouseRelease ccmHouseRelease, Model model, RedirectAttributes redirectAttributes) throws IOException {
		if (!beanValidator(model, ccmHouseRelease)) {
//			return form(ccmHouseRelease, model);
		}
		// 更新 当前人 是 安置帮教人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmHouseRelease.getPeopleId());
		ccmHouseReleaseService.save(ccmHouseRelease,ccmPop);

		if(ccmPop!=null){
			ccmPop.setIsRelease(1);
			ccmPeopleService.save(ccmPop);
		}
		addMessage(redirectAttributes, "保存安置帮教人员成功");
//		return "redirect:" + Global.getAdminPath() + "/house/ccmHouseRelease/?repage";

		PrintWriter out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存安置帮教人员成功");
		ccmPop.setMqTitle("新增重点人员(安置帮教人员)");
	}

	/**
	 * @see 人员标记登记 操作
	 * @param ccmHouseRelease
	 * @param model
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("house:ccmHouseRelease:edit")
	@RequestMapping(value = "savePop")
	public String savePop(CcmHouseRelease ccmHouseRelease, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, ccmHouseRelease)) {
			return form(ccmHouseRelease, model);
		}
		ccmHouseReleaseService.save(ccmHouseRelease);
		// 更新 当前人 是 安置帮教人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmHouseRelease.getPeopleId());
		if(ccmPop!=null){
			ccmPop.setIsRelease(1);
			ccmPeopleService.save(ccmPop);
		}
		
		addMessage(redirectAttributes, "保存安置帮教人员成功");
		return "redirect:" + Global.getAdminPath() + "/pop/ccmPeople/?repage";
	}

	/**
	 * @see 删除操作
	 * @param ccmHouseRelease
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("house:ccmHouseRelease:edit")
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request, CcmHouseRelease ccmHouseRelease, RedirectAttributes redirectAttributes) {
		ccmHouseReleaseService.delete(ccmHouseRelease);
		// 更新 当前人 是 安置帮教人员
		CcmPeople ccmPop = ccmPeopleService.get(ccmHouseRelease.getPeopleId());
		String permissionKey = request.getParameter("permissionKey");
		if(ccmPop!=null){
			ccmPop.setIsRelease(0);
			ccmPeopleService.save(ccmPop);
		}
		
		addMessage(redirectAttributes, "删除安置帮教人员成功");
		return "redirect:" + Global.getAdminPath() + "/house/ccmHouseRelease/?repage&permissionKey=" + permissionKey;
	}
	
	/**
	 * @see 人员标记 登记跳转页面
	 * @param ccmPeople
	 * @param model
	 * @return
	 */
	@RequiresPermissions("house:ccmHouseRelease:view")
	@RequestMapping(value = "specialform")
	public String specialform(CcmPeople ccmPeople, Model model) {
		model.addAttribute("ccmPeople", ccmPeople);
		CcmHouseRelease Release = ccmHouseReleaseService.getPeopleALL(ccmPeople.getId());
		if (Release == null){
			Release = new CcmHouseRelease();
		}
		model.addAttribute("ccmHouseRelease", Release);
		return "/ccm/house/pop/ccmHousePoPReleaseForm";
	}
	
	/**
	 * 导出安置帮教人员数据
	 * 
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("house:ccmHouseRelease:view")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(CcmHouseRelease ccmHouseRelease, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		String [] strArr={"姓名","联系方式","人口类型","现住门（楼）详址","公民身份号码","是否累犯","原判刑期","释放日期","衔接日期","安置日期","原罪名","服刑场所","犯罪是否重新","危险性评估类型","衔接情况","安置情况","关注程度"};
		try {
			String fileName = "ReleasePeople" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";

			List<CcmHouseRelease> list = new ArrayList<CcmHouseRelease>();
			String permissionKey = request.getParameter("permissionKey");
			User user = UserUtils.getUser();
			if (user != null && "1".equals(user.getHasPermission())) {//有涉密权限
				list = ccmHouseReleaseService.findList(ccmHouseRelease);
			} else if (user != null && "0".equals(user.getHasPermission())) {//无涉密权限
				if (user.getPermissionKey() != null && user.getPermissionKey().equals(permissionKey)) {
					list = ccmHouseReleaseService.findList(ccmHouseRelease);
				}
			}
			
//			List<CcmHouseRelease> list = ccmHouseReleaseService.findList(ccmHouseRelease);
			new ExportExcel("安置帮教人员数据", CcmHouseRelease.class,strArr).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出安置帮教人员失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/house/ccmHouseRelease/?repage";
	}

	/**
	 * 导入安置帮教人员数据
	 * 
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("house:ccmHouseRelease:view")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/house/ccmHouseRelease/?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<CcmHouseRelease> list = ei.getDataList(CcmHouseRelease.class);
			for (CcmHouseRelease HouseRelease : list) {
				try {

					if(EntityTools.isEmpty(HouseRelease)){
						continue;
					}
					
					if(StringUtils.isBlank(HouseRelease.getRecidivism()) ||
							StringUtils.isBlank(HouseRelease.getSentence()) ||
							HouseRelease.getReleDate()==null ||
							HouseRelease.getJoinDate()==null ||
							HouseRelease.getPlaceDate()==null ||
							StringUtils.isBlank(HouseRelease.getOrigCha()) ||
							StringUtils.isBlank(HouseRelease.getServinGplace()) ||
							HouseRelease.getReoffend()==null ||
							StringUtils.isBlank(HouseRelease.getRisk()) ||
							StringUtils.isBlank(HouseRelease.getJoinCond()) ||
							StringUtils.isBlank(HouseRelease.getPlacement()) ||
							StringUtils.isBlank(HouseRelease.getAtteType())
					){
						StringBuilder str = new StringBuilder();
						str.append("(");
						if(StringUtils.isBlank(HouseRelease.getRecidivism())) {
							str.append("是否累犯信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getSentence())) {
							str.append("原判刑期信息错误;");
						}
						if(HouseRelease.getReleDate()==null) {
							str.append("释放日期信息错误;");
						}
						if(HouseRelease.getJoinDate()==null) {
							str.append("衔接日期信息错误;");
						}
						if(HouseRelease.getPlaceDate()==null) {
							str.append("安置日期信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getOrigCha())) {
							str.append("原罪名信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getServinGplace())) {
							str.append("服刑场所信息错误;");
						}
						if(HouseRelease.getReoffend()==null) {
							str.append("是否重新犯罪信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getRisk())) {
							str.append("危险性评估类型信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getJoinCond())) {
							str.append("衔接情况信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getPlacement())) {
							str.append("安置情况信息错误;");
						}
						if(StringUtils.isBlank(HouseRelease.getAtteType())) {
							str.append("关注程度信息错误;");
						}
						str.append(")");
						failureMsg.append("<br/>安置帮教人员名 " + HouseRelease.getName() + " 导入失败：必填项为空。"+str.toString());
						continue;
					}
					
					// 如果当前用户的身份未填写或者为空或者身份证号码位数不够18位则应该进行 剔除
					if (StringUtils.isBlank(HouseRelease.getIdent()) || HouseRelease.getIdent().length() != 18) {
						failureMsg.append("<br/>实有人口名" + HouseRelease.getName() + " 导入失败：" + "身份证信息错误。");
						HouseRelease.setName(HouseRelease.getName() + "，失败原因：身份证信息错误");
						failureNum++;
						continue;
					}
					
					CcmPeople ccmPeople=new CcmPeople();
					ccmPeople.setIdent(HouseRelease.getIdent());
					List<CcmPeople> list1 = ccmPeopleService.findList(ccmPeople);
					CcmHouseRelease HouseReleaseFind;

					if (list1.isEmpty()){
						failureMsg.append("<br/>安置帮教人员名 " + HouseRelease.getName() + " 导入失败：实有人口表中无此人");
						continue;
					}else{
						ccmPeople.setId(list1.get(0).getId());
						ccmPeople.setUpdateBy(UserUtils.getUser());
						ccmPeople.setUpdateDate(new Date());
						ccmPeople.setIsRelease(1);
						ccmPeopleService.updatePeople(ccmPeople);
						HouseReleaseFind=ccmHouseReleaseService.getPeopleALL(list1.get(0).getId());
						BeanValidators.validateWithException(validator, HouseRelease);
						if(HouseReleaseFind == null){
							HouseRelease.setPeopleId(list1.get(0).getId());
							ccmHouseReleaseService.save(HouseRelease);
							successNum++;
						}else{
							failureMsg.append("<br/>安置帮教人员名 " + HouseRelease.getName() + " 导入失败：记录已存在");
						}
					}			
				} catch (ConstraintViolationException ex) {
					failureMsg.append("<br/>安置帮教人员名 " + HouseRelease.getName() + " 导入失败：");
					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
					for (String message : messageList) {
						failureMsg.append(message + "; ");
						failureNum++;
					}
				} catch (Exception ex) {
					System.out.println(ex);
					failureMsg.append("<br/>登录名 " + HouseRelease.getName() + " 导入失败：" + ex.getMessage());
				}
			}
			if (failureNum > 0) {
				failureMsg.insert(0, "，失败 " + failureNum + " 条安置帮教人员，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条安置帮教人员" + failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入安置帮教人员失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/house/ccmHouseRelease/?repage";
	}
	 

}