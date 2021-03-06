/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.book.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arjjs.ccm.modules.pbs.sys.utils.UserUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.google.common.collect.Lists;
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

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.book.entity.CcmDatabaseBook;
import com.arjjs.ccm.modules.ccm.book.service.CcmDatabaseBookService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * 资料库录入管理Controller
 * @author cdz
 * @version 2019-09-16
 */
@Controller
@RequestMapping(value = "${adminPath}/book/ccmDatabaseBook")
public class CcmDatabaseBookController extends BaseController {

	@Autowired
	private CcmDatabaseBookService ccmDatabaseBookService;
	
	@ModelAttribute
	public CcmDatabaseBook get(@RequestParam(required=false) String id) {
		CcmDatabaseBook entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = ccmDatabaseBookService.get(id);
		}
		if (entity == null){
			entity = new CcmDatabaseBook();
		}
		return entity;
	}
	
	@RequiresPermissions("book:ccmDatabaseBook:view")
	@RequestMapping(value = {"list", ""})
	public String list(CcmDatabaseBook ccmDatabaseBook, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CcmDatabaseBook> page = ccmDatabaseBookService.findPage(new Page<CcmDatabaseBook>(request, response), ccmDatabaseBook); 
		model.addAttribute("page", page);
		return "ccm/book/ccmDatabaseBookList";
	}

	@RequiresPermissions("book:ccmDatabaseBook:view")
	@RequestMapping(value = "form")
	public String form(CcmDatabaseBook ccmDatabaseBook, Model model) {
		model.addAttribute("ccmDatabaseBook", ccmDatabaseBook);
		return "ccm/book/ccmDatabaseBookForm";
	}

    @RequiresPermissions("book:ccmDatabaseBook:view")
    @RequestMapping(value = "index")
    public String index(CcmDatabaseBook ccmDatabaseBook, Model model) {
		List<CcmDatabaseBook> list = Lists.newArrayList();
		String id = ccmDatabaseBook.getId();
		List<CcmDatabaseBook> sourcelist = this.ccmDatabaseBookService.findListById(id);
		CcmDatabaseBook.sortList(list, sourcelist, CcmDatabaseBook.getRootId(), true);
		model.addAttribute("list", list);

		/*model.addAttribute("list", ccmDatabaseBookService.findList(ccmDatabaseBook));*/
		return "ccm/book/ccmDatabaseBookIndex";
    }

	@RequiresPermissions("book:ccmDatabaseBook:view")
	@RequestMapping(value = "add")
	public String add(CcmDatabaseBook ccmDatabaseBook, Model model) {
		if (ccmDatabaseBook.getParent() == null || ccmDatabaseBook.getParent().getId() == null) {
			ccmDatabaseBook.setParent(new CcmDatabaseBook(CcmDatabaseBook.getRootId()));
		}

		ccmDatabaseBook.setParent(this.ccmDatabaseBookService.getCcmDatabaseBook(ccmDatabaseBook.getParent().getId()));
		if (StringUtils.isBlank(ccmDatabaseBook.getId())) {
			List<CcmDatabaseBook> list = Lists.newArrayList();
			List<CcmDatabaseBook> sourcelist = this.ccmDatabaseBookService.findAllList();
			CcmDatabaseBook.sortList(list, sourcelist, ccmDatabaseBook.getParentId(), false);
			if (list.size() > 0) {
				ccmDatabaseBook.setSort(((CcmDatabaseBook)list.get(list.size() - 1)).getSort() + 30);
			}
		}

		model.addAttribute("menu", ccmDatabaseBook);
		return "ccm/book/ccmDatabaseBookAdd";
	}

	@RequiresPermissions("book:ccmDatabaseBook:edit")
	@RequestMapping(value = "saveAdd")
	public String saveAdd(CcmDatabaseBook ccmDatabaseBook, Model model, RedirectAttributes redirectAttributes) {
		if (!UserUtils.getUser().isAdmin()) {
			this.addMessage(redirectAttributes, new String[]{"越权操作，只有超级管理员才能添加或修改数据！"});
			return "redirect:" + this.adminPath + "/book/ccmDatabaseBook/list";
		} else if (Global.isDemoMode()) {
			this.addMessage(redirectAttributes, new String[]{"演示模式，不允许操作！"});
			return "redirect:" + this.adminPath + "/book/ccmDatabaseBook/list";
		} else if (!this.beanValidator(model, ccmDatabaseBook, new Class[0])) {
			return this.form(ccmDatabaseBook, model);
		} else {
			this.ccmDatabaseBookService.saveMenu(ccmDatabaseBook);
			this.addMessage(redirectAttributes, new String[]{"保存菜单'" + ccmDatabaseBook.getName() + "'成功"});
			return "redirect:" + this.adminPath + "/book/ccmDatabaseBook/list";
		}
	}

	@RequiresPermissions("book:ccmDatabaseBook:edit")
	@RequestMapping(value = "save")
	public void save(CcmDatabaseBook ccmDatabaseBook, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		if (!beanValidator(model, ccmDatabaseBook)){
			form(ccmDatabaseBook, model);
			return;
		}
		PrintWriter out = null;
		try{
			out = response.getWriter();
			ccmDatabaseBookService.save(ccmDatabaseBook);
			CommUtil.openWinExpDiv(out, "书籍录入成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*ccmDatabaseBookService.save(ccmDatabaseBook);
		addMessage(redirectAttributes, "书籍录入成功");*/
		/*return "redirect:"+Global.getAdminPath()+"/book/ccmDatabaseBook/?repage";*/
	}


	@RequiresPermissions({"sys:menu:edit"})
	@RequestMapping({"deleteIndex"})
	public String deleteIndex(CcmDatabaseBook menu, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			this.addMessage(redirectAttributes, new String[]{"演示模式，不允许操作！"});
			return "redirect:" + this.adminPath + "/book/ccmDatabaseBook/list";
		} else {
			this.ccmDatabaseBookService.deleteMenu(menu);
			this.addMessage(redirectAttributes, new String[]{"删除菜单成功"});
			return "redirect:" + this.adminPath + "/book/ccmDatabaseBook/list";
		}
	}

	@RequiresPermissions("book:ccmDatabaseBook:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmDatabaseBook ccmDatabaseBook, RedirectAttributes redirectAttributes) {
		ccmDatabaseBookService.delete(ccmDatabaseBook);
		addMessage(redirectAttributes, "删除书籍成功");
		return "redirect:"+Global.getAdminPath()+"/book/ccmDatabaseBook/?repage";
	}

	@RequiresPermissions({"user"})
	@ResponseBody
	@RequestMapping({"treeData"})
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<CcmDatabaseBook> list = this.ccmDatabaseBookService.findAllList();

		for(int i = 0; i < list.size(); ++i) {
			CcmDatabaseBook e = (CcmDatabaseBook)list.get(i);
			if ((StringUtils.isBlank(extId) || extId != null && !extId.equals(e.getId()))) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}

		return mapList;
	}

}