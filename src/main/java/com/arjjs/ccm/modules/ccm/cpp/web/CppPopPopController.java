/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.cpp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
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
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.cpp.dao.CppPopPopDao;
import com.arjjs.ccm.modules.ccm.cpp.entity.CppPopPop;
import com.arjjs.ccm.modules.ccm.cpp.entity.CppPopVehile;
import com.arjjs.ccm.modules.ccm.cpp.entity.CppPopPop;
import com.arjjs.ccm.modules.ccm.cpp.service.CppPopPopService;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPeople;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.RetrueJson;

/**
 * 人际关系Controller
 * 
 * @author liuxue
 * @version 2018-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/cpp/cppPopPop")
public class CppPopPopController extends BaseController {

	@Autowired
	private CppPopPopService cppPopPopService;
	@Autowired
	private CppPopPopDao cppPopPopDao;

	@ModelAttribute
	public CppPopPop get(@RequestParam(required = false) String id) {
		CppPopPop entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = cppPopPopService.get(id);
		}
		if (entity == null) {
			entity = new CppPopPop();
		}
		return entity;
	}
	
	@RequestMapping("relation")
	@ResponseBody
	public List<CppPopPop> getByRelation(String idCard1) {
		return cppPopPopDao.getByIdCard(idCard1);
	}

	@RequiresPermissions("cpp:cppPopPop:view")
	@RequestMapping(value = { "list", "" })
	public String list(CppPopPop cppPopPop, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CppPopPop> page = cppPopPopService.findPage(new Page<CppPopPop>(request, response), cppPopPop);
		model.addAttribute("page", page);
		return "ccm/pop/cpp/cppPopPopList";
	}

	@ResponseBody
	@RequestMapping(value = { "ajaxlist" })
	public RetrueJson ajsxlist(CppPopPop cppPopPop, HttpServletRequest request, HttpServletResponse response,
			Model model, String idCard) {
		RetrueJson retrueJson = new RetrueJson();
		if(idCard!=null&&idCard.length()!=0) {
			cppPopPop.setIdCard1(idCard);
			List<CppPopPop> list = cppPopPopService.findList(cppPopPop);
			for (CppPopPop cppPopPop2 : list) {
				/* if(cppPopPop.getType().equals("02")){ */
				cppPopPop2.setType(DictUtils.getDictLabel(cppPopPop2.getType(), "cpp_pop_pop_type", ""));
				/* } */
			}
			retrueJson.setObject1(list);
		}else {
			retrueJson.setObject1(null);
		}
		return retrueJson;
	}

	@ResponseBody
	@RequestMapping(value = { "getIdCard" })
	public String getIdCard(HttpServletRequest request, HttpServletResponse response, String idCard) {
		CcmPeople ccmPeople = new CcmPeople();
		ccmPeople.setIdent(idCard);
		CcmPeople ccmPeople2 = cppPopPopService.getIdCard(ccmPeople);
		if (ccmPeople2 != null) {
			return ccmPeople2.getName();
		}
		return "";
	}

	@RequestMapping(value = "form")
	public String form(CppPopPop cppPopPop, Model model, String idCard, HttpServletRequest request) {
		if (StringUtils.isBlank(cppPopPop.getId())) {
			cppPopPop.setIdCard1(idCard);
		}
		model.addAttribute("cppPopPop", cppPopPop);
		model.addAttribute("idCard", idCard);
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		request.getSession().setAttribute("addTime", sdf.format(dt));
		request.getSession().setAttribute("idCardAdd", idCard);
		return "ccm/pop/cpp/cppPopPopForm";
	}

	@RequestMapping(value = "save")
	public void save(CppPopPop cppPopPop, Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		PrintWriter out;
		out = response.getWriter();
		logger.info("人际关系保存走save方法========》》》》》》》》");
		if (!beanValidator(model, cppPopPop)) {
			/* return form(cppPopPop, model); */
			CommUtil.openWinExpDivValidator(out, "请输入正确格式的信息");
		} else {
			cppPopPopService.save(cppPopPop);
			addMessage(redirectAttributes, "保存成功");
			request.getSession().setAttribute("savePop", cppPopPop);
			CommUtil.openWinExpDiv(out, "保存成功");
		}
	}

	@RequestMapping(value = "saveAdd")
	public void saveAdd(CppPopPop cppPopPop, Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		PrintWriter out;
		out = response.getWriter();

		cppPopPopService.save(cppPopPop);
		out.println("<script language='javascript'>cppPopPopApp()</script>");

	}

	@ResponseBody
	@RequestMapping(value = "saveAddList")
	public List<CppPopPop> saveAddList(CppPopPop cppPopPop, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {
		cppPopPop.setIdCard1((String) request.getSession().getAttribute("idCardAdd"));
		cppPopPop.setAddTime((String) request.getSession().getAttribute("addTime"));
		List<CppPopPop> list = cppPopPopService.findListAddTime(cppPopPop);
		return list;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxsave")
	public String ajaxsave(CppPopPop cppPopPop, Model model, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		CcmPeople ccmPeople = new CcmPeople();
		ccmPeople.setIdent(cppPopPop.getIdCard1());
		CcmPeople ccmPeople2 = cppPopPopService.getIdCard(ccmPeople);
		String name1 = ccmPeople2.getName();
		String name2 = cppPopPop.getOtherName();
		cppPopPop.setName1(name1);
		cppPopPop.setName2(name2);
		List<CppPopPop> list = cppPopPopService.findList(cppPopPop);
		String result = "ok";
		if(list.size()!=0) {
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getIdCard1().equalsIgnoreCase(cppPopPop.getIdCard2())
						|| list.get(i).getIdCard2().equalsIgnoreCase(cppPopPop.getIdCard2())) {
					result="fail";
					break;
				}
			}
		}
		if(result=="ok") {
			cppPopPopService.save(cppPopPop);
		}
		
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "ajaxdelete")
	public RetrueJson ajsxdelete(CppPopPop cppPopPop, RedirectAttributes redirectAttributes) {
		RetrueJson retrueJson = new RetrueJson();
		cppPopPopService.delete(cppPopPop);
		retrueJson.setMessage("删除成功");
		retrueJson.IsSuccess(true);

		return retrueJson;
	}

}