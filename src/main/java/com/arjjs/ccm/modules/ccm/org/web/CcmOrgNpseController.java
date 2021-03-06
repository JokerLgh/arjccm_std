/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.web;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.citycomponents.entity.CcmCityComponents;
import com.arjjs.ccm.modules.ccm.citycomponents.service.CcmCityComponentsService;
import com.arjjs.ccm.modules.ccm.log.entity.CcmLogTail;
import com.arjjs.ccm.modules.ccm.log.service.CcmLogTailService;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgCommonality;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgNpse;
import com.arjjs.ccm.modules.ccm.org.service.CcmOrgCommonalityService;
import com.arjjs.ccm.modules.ccm.org.service.CcmOrgNpseService;
import com.arjjs.ccm.modules.sys.entity.Dict;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.EchartType;
import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 非公有制经济组织Controller
 * @author wwh
 * @version 2018-01-26
 */
@Controller
@RequestMapping(value = "${adminPath}/org/ccmOrgNpse")
public class CcmOrgNpseController extends BaseController {

	@Autowired
	private CcmOrgNpseService ccmOrgNpseService;
	@Autowired
	private CcmLogTailService ccmLogTailService;
	@Autowired
	private CcmOrgCommonalityService ccmOrgCommonalityService;
	@Autowired
	private CcmCityComponentsService ccmCityComponentsService;
	
	
	@ModelAttribute
	public CcmOrgNpse get(@RequestParam(required=false) String id) {
		CcmOrgNpse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ccmOrgNpseService.get(id);
		}
		if (entity == null){
			entity = new CcmOrgNpse();
		}
		return entity;
	}
	
	//安全生产重点ListSafe
	@RequiresPermissions("org:ccmOrgNpse:view")
	@RequestMapping(value = "listSafe")
	public String listSafe(CcmOrgNpse ccmOrgNpse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmOrgNpse> page = ccmOrgNpseService.findPageSafe(new Page<CcmOrgNpse>(request, response), ccmOrgNpse); 
		model.addAttribute("page", page);
		return "ccm/org/ccmOrgNpseListSafe";
	}
	/**
	 * 危化企业
	 */
	@ResponseBody
	@RequestMapping(value = "getDangCompData")
	public String getDangCompData(Model model) {
		List<EchartType> listDangComp = ccmOrgNpseService.getDangComp(); //危化企业
		EchartType newEchartType = new EchartType();//非空判断
		newEchartType.setType("暂无数据");
		newEchartType.setValue("0");
		if(listDangComp.size()==0){
			listDangComp.add(newEchartType);
		}
		JsonConfig config = new JsonConfig();//PingJson
		config.setExcludes(new String[]{""});
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String listDangCompString = JSONArray.fromObject(listDangComp,config).toString(); //Json
		return listDangCompString;
	}

	/**
	 * 风险级别
	 */
	@ResponseBody
	@RequestMapping(value = "getRiskRankData")
	public List<String> getRiskRankData(Model model) {
		List<String> listOthersAll = new ArrayList<>();
		String[] type = {"02","03","04","05"};//高风险单位;//中等风险单位//一般风险单位//低风险单位
		for(int n=0;n<type.length;n++){
			CcmOrgNpse ccmOrgNpse = new CcmOrgNpse();
			ccmOrgNpse.setRiskRank(type[n]);
			int numRiskRank = ccmOrgNpseService.findNumRiskRank(ccmOrgNpse);//风险级别数量
			listOthersAll.add(numRiskRank+"");
		}
		return listOthersAll;
	}

	/**
	 * 首页概况各种总数查询
	 */
	@ResponseBody
	@RequestMapping(value = "getCompImpoType")
	public List<String> getCompImpoType(Model model) {
		List<String> listCompImpoType = new ArrayList<>();
		/*String[] type = {"02","03","01"};//安全生产重点//消防重点//物流安全
		for(int n=0;n<type.length;n++){
			CcmOrgNpse ccmOrgNpse = new CcmOrgNpse();
			ccmOrgNpse.setCompImpoType(type[n]);
			int numCompImpoType = ccmOrgNpseService.findCompImpoTypeData(ccmOrgNpse);//首页概况各种总数查询//安全生产重点//消防重点//物流安全
			listCompImpoType.add(numCompImpoType+"");
		}*/
		String s1 = "0";//安全生产重点
		String s2 = "0";//消防重点
		String s3 = "0";//物流安全
		List<EchartType> numCompImpoType = ccmOrgNpseService.findCompImpoType2(); //报表:非公有经济组织重点类型-无关联
		for(EchartType e:numCompImpoType){
			if("02".equals(e.getTypeO())){
				s1 = e.getValue();
			}
			if("03".equals(e.getTypeO())){
				s2 = e.getValue();
			}
			if("01".equals(e.getTypeO())){
				s3 = e.getValue();
			}
		}
		listCompImpoType.add(s1);//安全生产重点
		listCompImpoType.add(s2);//消防重点
		listCompImpoType.add(s3);//物流安全
		
		String[] com = {"03"};//消防
		for(int p=0;p<com.length;p++){
			CcmOrgCommonality ccmOrgCommonality = new CcmOrgCommonality();
			ccmOrgCommonality.setType(com[p]);
			List<CcmOrgCommonality> list = ccmOrgCommonalityService.findList(ccmOrgCommonality);
			listCompImpoType.add(list.size()+"");
		}
		String[] city = {"16","17"};//消防设施总数：消防栓+消防水源
		int sunCity = 0;
		for(int q=0;q<city.length;q++){
			CcmCityComponents ccmCityComponents = new CcmCityComponents();
			ccmCityComponents.setType(city[q]);
			List<CcmCityComponents> listCity = ccmCityComponentsService.findList(ccmCityComponents);//消防设施总数：消防栓+消防水源
			sunCity += listCity.size();
		}
		listCompImpoType.add(sunCity+"");
		
		return listCompImpoType;
	}

	/**
	 * 重点企业分布
	 */
	@ResponseBody
	@RequestMapping(value = "getCompImpoTypeTrue")
	public String getCompImpoTypeTrue(Model model) {
		List<EchartType> listCompImpoTypeTrue = ccmOrgNpseService.getCompImpoTypeTrue(); //重点企业分布
		EchartType newEchartType = new EchartType();//非空判断
		newEchartType.setType("暂无数据");
		newEchartType.setValue("0");
		if(listCompImpoTypeTrue.size()==0){
			listCompImpoTypeTrue.add(newEchartType);
		}
		JsonConfig config = new JsonConfig();//PingJson
		config.setExcludes(new String[]{""});
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String listCompImpoTypeTrueString = JSONArray.fromObject(listCompImpoTypeTrue,config).toString(); //Json
		return listCompImpoTypeTrueString;
	}

	/**
	 * 治安重点场所
	 */
	@ResponseBody
	@RequestMapping(value = "getSafePubData")
	public String getSafePubData(Model model) {
		//治安重点场所
		List<EchartType> listSafePub = ccmOrgNpseService.getSafePubData();
		EchartType newEchartType = new EchartType();
		newEchartType.setType("暂无数据");
		newEchartType.setValue("0");
		//非空判断
		if(listSafePub.size()==0){
			listSafePub.add(newEchartType);
		}
		JsonConfig config = new JsonConfig();//PingJson
		config.setExcludes(new String[]{""});
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String listSafePubString = JSONArray.fromObject(listSafePub,config).toString(); //Json
		return listSafePubString;
	}
	/**
	 * 企业数量-大屏-滨海新区社会网格化管理信息平台
	 */
	@ResponseBody
	@RequestMapping(value = "getqiyeNumData")
	public String getqiyeNumData(Model model) {
		String num = ccmOrgNpseService.getqiyeNumData(new CcmOrgNpse());
		return num;
	}
	/**
	 * 风险单位数据分析-按街道-大屏-滨海新区社会网格化管理信息平台
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "getwhatRiskRankData")
	public Map<String, String> getwhatRiskRankData(Model model) {
		// 返回对象结果
		Map<String, String> map = Maps.newHashMap();
		
		CcmOrgNpse ccmOrgNpse = new CcmOrgNpse();
		List<EchartType> list = ccmOrgNpseService.findSumByString(ccmOrgNpse);// 总数
		ccmOrgNpse.setRiskRank("02");
		List<EchartType> list2 = ccmOrgNpseService.findSumByString(ccmOrgNpse);// 高风险单位
		ccmOrgNpse.setRiskRank("03");
		List<EchartType> list3 = ccmOrgNpseService.findSumByString(ccmOrgNpse);// 较大风险单位
		ccmOrgNpse.setRiskRank("04");
		List<EchartType> list4 = ccmOrgNpseService.findSumByString(ccmOrgNpse);// 一般风险单位
		ccmOrgNpse.setRiskRank("05");
		List<EchartType> list5 = ccmOrgNpseService.findSumByString(ccmOrgNpse);// 低风险单位
		
		JsonConfig config = new JsonConfig();//PingJson
		config.setExcludes(new String[]{"typeO"});
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String listString = JSONArray.fromObject(list,config).toString(); //Json
		String list2String = JSONArray.fromObject(list2,config).toString(); //Json
		String list3String = JSONArray.fromObject(list3,config).toString(); //Json
		String list4String = JSONArray.fromObject(list4,config).toString(); //Json
		String list5String = JSONArray.fromObject(list5,config).toString(); //Json
		
		// 填充数据
		map.put("quan", listString);
		map.put("02", list2String);
		map.put("03", list3String);
		map.put("04", list4String);
		map.put("05", list5String);
		return map;
	}
	
	
	
	
	
	
	
	
	//
	@RequestMapping(value = {"list", ""})
	public String list(CcmOrgNpse ccmOrgNpse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmOrgNpse> page = ccmOrgNpseService.findPage(new Page<CcmOrgNpse>(request, response), ccmOrgNpse); 
		model.addAttribute("page", page);
		if("01".equals(ccmOrgNpse.getCompType())) {//法人企业
			return "ccm/org/corporate/ccmOrgCorporateList";
		}else if("02".equals(ccmOrgNpse.getCompType())) {//个体工商户
			return "ccm/org/individual/ccmOrgIndividualList";
		}else if("03".equals(ccmOrgNpse.getCompType())) {//机关单位
			return "ccm/org/office/ccmOrgOfficeList";
		}else if("04".equals(ccmOrgNpse.getCompType())) {//事业单位
			return "ccm/org/enterprise/ccmOrgEnterpriseList";
		}else if("05".equals(ccmOrgNpse.getCompType())) {//社团活动
			return "ccm/org/mass/ccmOrgMassList";
		}else {
			return "ccm/org/ccmOrgNpseList";
		}
	}
	
	@RequestMapping(value = {"listAll"})
	public String listAll(CcmOrgNpse ccmOrgNpse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmOrgNpse> page = ccmOrgNpseService.findPage(new Page<CcmOrgNpse>(request, response), ccmOrgNpse);
		model.addAttribute("page", page);
		return "ccm/org/ccmOrgNpseList";
	}

	@RequiresPermissions("org:ccmOrgNpse:view")
	@RequestMapping(value = "form")
	public String form(CcmOrgNpse ccmOrgNpse, Model model) {
		// 创建 查询对象 建立查询条件
		CcmLogTail ccmLogTailDto = new CcmLogTail();
		ccmLogTailDto.setRelevanceId(ccmOrgNpse.getId());
		ccmLogTailDto.setRelevanceTable("ccm_org_npse");
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
		model.addAttribute("ccmOrgNpse", ccmOrgNpse);
		return "ccm/org/ccmOrgNpseForm";
	}
	
	@RequiresPermissions("org:ccmOrgNpse:view")
	@RequestMapping(value = "formData")
	public String formData(CcmOrgNpse ccmOrgNpse, Model model) {
		// 创建 查询对象 建立查询条件
		CcmLogTail ccmLogTailDto = new CcmLogTail();
		ccmLogTailDto.setRelevanceId(ccmOrgNpse.getId());
		ccmLogTailDto.setRelevanceTable("ccm_org_npse");
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
		model.addAttribute("ccmOrgNpse", ccmOrgNpse);
		if("01".equals(ccmOrgNpse.getCompType())) {//法人企业
			return "ccm/org/corporate/ccmOrgCorporateForm";
		}else if("02".equals(ccmOrgNpse.getCompType())) {//个体工商户
			return "ccm/org/individual/ccmOrgIndividualForm";
		}else if("03".equals(ccmOrgNpse.getCompType())) {//机关单位
			return "ccm/org/office/ccmOrgOfficeForm";
		}else if("04".equals(ccmOrgNpse.getCompType())) {//事业单位
			return "ccm/org/enterprise/ccmOrgEnterpriseForm";
		}else{//社团活动
			return "ccm/org/mass/ccmOrgMassForm";
		}
	}
	
	

	@RequiresPermissions("org:ccmOrgNpse:edit")
	@RequestMapping(value = "save")
	public void save( HttpServletRequest request, HttpServletResponse response,CcmOrgNpse ccmOrgNpse, Model model, RedirectAttributes redirectAttributes) throws IOException {
		ccmOrgNpseService.save(ccmOrgNpse);
		PrintWriter out = null;
		out = response.getWriter();
		CommUtil.openWinExpDiv(out, "保存机构组织成功");
	}
	
	@RequiresPermissions("org:ccmOrgNpse:edit")
	@RequestMapping(value = "delete")
	public String delete(HttpServletRequest request, HttpServletResponse response,CcmOrgNpse ccmOrgNpse, RedirectAttributes redirectAttributes) {
		ccmOrgNpseService.delete(ccmOrgNpse);
		addMessage(redirectAttributes, "删除机构组织成功");
		return "redirect:"+Global.getAdminPath()+"/org/ccmOrgNpse/list?repage&compType="+ccmOrgNpse.getCompType();
	}
	
	@RequestMapping(value = "deleteData")
	public String deleteData(HttpServletRequest request, HttpServletResponse response,CcmOrgNpse ccmOrgNpse, RedirectAttributes redirectAttributes) {
		ccmOrgNpseService.delete(ccmOrgNpse);
		addMessage(redirectAttributes, "删除机构组织成功");
		return "redirect:"+Global.getAdminPath()+"/org/ccmOrgNpse/list?repage";
	}

	@ResponseBody
	@RequestMapping(value = "getOrgNpseByRegiType")
	public Map<String, Object> getOrgNpseByRegiType(CcmOrgNpse ccmOrgNpse, HttpServletRequest request, HttpServletResponse response,
												   Model model) {
		// 返回对象结果
		List<EchartType> list = ccmOrgNpseService.getOrgNpseByRegiType(ccmOrgNpse);

		String[] name = null;
		String[] value = null;

		List<Map<String, Object>> listData = new ArrayList<>();
		if(list.size()==0){
			List<Dict> dictList = DictUtils.getDictList("sys_ccm_regi_type");
			name = new String[dictList.size()];
			value = new String[dictList.size()];
			for (int i = 0; i < dictList.size(); i++) {
				name[i] = dictList.get(i).getLabel();
				value[i] = "0";
				Map<String, Object> tempMap = new HashMap<>();
				//查询并添加进去数量以及title
				tempMap.put("value", value[i]);
				tempMap.put("name", name[i]);
				listData.add(tempMap);
			}
		}else{
			name = new String[list.size()];
			value = new String[list.size()];
		}


		for (int i = 0; i < list.size(); i++) {
			//赋值
			list.get(i).setType(DictUtils.getDictLabel(list.get(i).getType(), "sys_ccm_regi_type", ""));

			name[i] = list.get(i).getType();
			value[i] = list.get(i).getValue();
			Map<String, Object> tempMap = new HashMap<>();
			//查询并添加进去数量以及title
			tempMap.put("value", value[i]);
			tempMap.put("name", name[i]);
			listData.add(tempMap);
		}
		Map<String, Object> data = new HashMap<>();
		data.put("name", name);
		data.put("value", listData);
		return data;
	}

	@RequestMapping(value = "mapvForm")
	public String mapvForm(CcmOrgNpse ccmOrgNpse, Model model) {
		// 创建 查询对象 建立查询条件
		CcmLogTail ccmLogTailDto = new CcmLogTail();
		ccmLogTailDto.setRelevanceId(ccmOrgNpse.getId());
		ccmLogTailDto.setRelevanceTable("ccm_org_npse");
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
		model.addAttribute("ccmOrgNpse", ccmOrgNpse);
		return "ccm/org/ccmOrgNpseMapvForm";
	}
}