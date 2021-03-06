/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.cpp.web;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.cpp.dao.CppPopVehileDao;
import com.arjjs.ccm.modules.ccm.cpp.entity.CppPopVehile;
import com.arjjs.ccm.modules.ccm.cpp.service.CppPopVehileService;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.RetrueJson;
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

/**
 * 人车/联系/网络关系表Controller
 * @author liuxue
 * @version 2018-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/cpp/cppPopVehile")
public class CppPopVehileController extends BaseController {

	@Autowired
	private CppPopVehileService cppPopVehileService;
	@Autowired
	private CppPopVehileDao cppPopVehileDao;
	
	@ModelAttribute
	public CppPopVehile get(@RequestParam(required=false) String id) {
		CppPopVehile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = cppPopVehileService.get(id);
		}
		if (entity == null){
			entity = new CppPopVehile();
		}
		return entity;
	}
	
	@RequestMapping("relation")
	@ResponseBody
	public List<CppPopVehile> getByRelation(String idCard) {
		return cppPopVehileDao.getByRelation(idCard);
	}
	

	@RequestMapping(value = {"list", ""})
	public String list(CppPopVehile cppPopVehile, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CppPopVehile> page = cppPopVehileService.findPage(new Page<CppPopVehile>(request, response), cppPopVehile); 
		model.addAttribute("page", page);
		return "ccm/cpp/cppPopVehileList";
	}
	@ResponseBody
	@RequestMapping(value = {"ajaxlist"})
	public RetrueJson ajsxlist(CppPopVehile cppPopVehile, HttpServletRequest request, HttpServletResponse response, Model model) {
		RetrueJson retrueJson = new RetrueJson();
		String idcard = cppPopVehile.getIdCard();
		if(idcard!=null && idcard.length()!=0) {
			List <CppPopVehile> list = cppPopVehileService.findList(cppPopVehile);
			for (CppPopVehile cppPopVehile2 : list) {
				if(cppPopVehile.getType().equals("02")){
					cppPopVehile2.setSubType(DictUtils.getDictLabel(cppPopVehile2.getSubType(), "cpp_phone_type", ""));
				}else if(cppPopVehile.getType().equals("01")){
					cppPopVehile2.setSubType(DictUtils.getDictLabel(cppPopVehile2.getSubType(), "cpp_vehile_type", ""));
				}else if(cppPopVehile.getType().equals("03")){
					cppPopVehile2.setSubType(DictUtils.getDictLabel(cppPopVehile2.getSubType(), "cpp_web_type", ""));
				}
			}
			retrueJson.setObject1(list);
		}else {
			retrueJson.setObject1(null);
		}
		return retrueJson;
	}
	
	
	
	@RequestMapping(value = "form")
	public String form(CppPopVehile cppPopVehile, Model model) {
		model.addAttribute("cppPopVehile", cppPopVehile);
		return "ccm/pop/cpp/cppPopVehileForm";
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxform")
	public RetrueJson ajsxform(CppPopVehile cppPopVehile, Model model) {
		model.addAttribute("cppPopVehile", cppPopVehile);
		RetrueJson retrueJson = new RetrueJson();
		retrueJson.setObject1(cppPopVehile);
		return retrueJson;
	}
	

	
	@RequestMapping(value = "save")
	public void save(CppPopVehile cppPopVehile, Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException {
		PrintWriter out;
		out = response.getWriter();
		
		if (!beanValidator(model, cppPopVehile)){
			/*return form(cppPopVehile, model);*/
			CommUtil.openWinExpDivValidator(out, "请输入正确格式的信息");
		}else{
		cppPopVehileService.save(cppPopVehile);
		addMessage(redirectAttributes, "保存成功");
		request.getSession().setAttribute("saveVehile", cppPopVehile);
		CommUtil.openWinExpDivFunction(out, "保存成功","cppPopVehile",cppPopVehile.getType());
		}
	}

	@ResponseBody
	@RequestMapping(value = "ajaxsave")
	public String ajaxsave(CppPopVehile cppPopVehile, Model model, HttpServletRequest request, HttpServletResponse response,RedirectAttributes redirectAttributes) throws IOException {
		PrintWriter out;
		out = response.getWriter();
		String result = "ok";
		if (!beanValidator(model, cppPopVehile)){
			/*return form(cppPopVehile, model);*/
//			CommUtil.openWinExpDivValidator(out, "请输入正确格式的信息");
			result = "fail";
		}else{
			cppPopVehileService.save(cppPopVehile);
			addMessage(redirectAttributes, "保存成功");
			request.getSession().setAttribute("saveVehile", cppPopVehile);
			CommUtil.openWinExpDivFunction(out, "保存成功","cppPopVehile",cppPopVehile.getType());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "ajaxdelete")
	public RetrueJson ajsxdelete(CppPopVehile cppPopVehile, RedirectAttributes redirectAttributes) {
		RetrueJson retrueJson = new RetrueJson();
		cppPopVehileService.delete(cppPopVehile);
			retrueJson.setMessage("删除成功");
			retrueJson.IsSuccess(true);
		
		return retrueJson;
	}

}