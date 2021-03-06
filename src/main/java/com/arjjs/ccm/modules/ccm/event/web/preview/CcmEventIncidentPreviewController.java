/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.event.web.preview;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventAmbi;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventIncident;
import com.arjjs.ccm.modules.ccm.event.entity.preview.CcmEventIncidentPreview;
import com.arjjs.ccm.modules.ccm.event.entity.preview.CcmEventIncidentSimilarty;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventAmbiService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.event.service.preview.CcmEventIncidentPreviewService;
import com.arjjs.ccm.modules.ccm.sys.entity.SysConfig;
import com.arjjs.ccm.modules.ccm.sys.service.SysConfigService;
import com.arjjs.ccm.modules.pbs.sys.utils.UserUtils;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.SimilarityUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 事件预处理Controller
 * 
 * @author lgh
 * @version 2019-05-06
 */
@Controller
@RequestMapping(value = "${adminPath}/preview/ccmEventIncidentPreview")
public class CcmEventIncidentPreviewController extends BaseController {

	@Autowired
	private CcmEventIncidentPreviewService ccmEventIncidentPreviewService;
	@Autowired
	private CcmEventIncidentService ccmEventIncidentService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private CcmEventAmbiService ccmEventAmbiService;

	@ModelAttribute
	public CcmEventIncidentPreview get(@RequestParam(required = false) String id) {
		CcmEventIncidentPreview entity = null;
		if (StringUtils.isNotBlank(id)) {
			entity = ccmEventIncidentPreviewService.get(id);
		}
		if (entity == null) {
			entity = new CcmEventIncidentPreview();
		}
		return entity;
	}

	@RequiresPermissions("preview:ccmEventIncidentPreview:view")
	@RequestMapping(value = { "{reportType}" })
	public String list(CcmEventIncidentPreview ccmEventIncidentPreview, HttpServletRequest request,
			HttpServletResponse response, Model model, @PathVariable("reportType") String reportType,
			@RequestParam(required = false) String status) {
		ccmEventIncidentPreview.setStatus(status);
		Page<CcmEventIncidentPreview> page = ccmEventIncidentPreviewService
				.findPage(new Page<CcmEventIncidentPreview>(request, response), ccmEventIncidentPreview);
		model.addAttribute("page", page);
		// App上报页面
		if ("1".equals(reportType)) {
			return "ccm/event/preview/ccmEventIncidentPreviewList";
		}
		// 短信预处理页面
		if ("2".equals(reportType)) {
			return "ccm/event/preview/ccmEventIncidentMessPreviewList";
		}
		// 热线预处理
		if ("3".equals(reportType)) {
			return "ccm/event/preview/ccmEventIncidentHotPreviewList";
		}
		// 网站预处理
		if ("4".equals(reportType)) {
			return "ccm/event/preview/ccmEventIncidentNetPreviewList";
		}
		// 机顶盒预处理
		if ("5".equals(reportType)) {
			return "ccm/event/preview/ccmEventIncidentBoxPreviewList";
		}
		return "ccm/event/preview/ccmEventIncidentPreviewList";
	}

	@RequiresPermissions("preview:ccmEventIncidentPreview:view")
	@RequestMapping(value = "form/{reportType}")
	public String form(CcmEventIncidentPreview ccmEventIncidentPreview, Model model,
			@PathVariable("reportType") String reportType) {
		model.addAttribute("ccmEventIncidentPreview", ccmEventIncidentPreview);
		// App上报页面
		if ("1".equals(reportType)) {
			model.addAttribute("reportType", "1");
//			return "ccm/event/preview/ccmEventIncidentPreviewForm";
		}
		// 短信预处理页面
		if ("2".equals(reportType)) {
			model.addAttribute("reportType", "2");
//			return "ccm/event/preview/ccmEventIncidentPreviewMessForm";
		}
		// 热线预处理
		if ("3".equals(reportType)) {
			model.addAttribute("reportType", "3");
//			return "ccm/event/preview/ccmEventIncidentPreviewHotForm";
		} // 网站预处理
		if ("4".equals(reportType)) {
			model.addAttribute("reportType", "4");
//			return "ccm/event/preview/ccmEventIncidentPreviewNetForm";
		}
		if ("5".equals(reportType)) {
			model.addAttribute("reportType", "5");
//			return "ccm/event/preview/ccmEventIncidentPreviewBoxForm";
		}
		return "ccm/event/preview/ccmEventIncidentPreviewForm";
	}

	@RequiresPermissions("preview:ccmEventIncidentPreview:edit")
	@RequestMapping(value = "save/{reportType}")
	public void save(CcmEventIncidentPreview ccmEventIncidentPreview, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes, @PathVariable("reportType") String reportType) {
		ccmEventIncidentPreview.setReportType(reportType);

		if (!beanValidator(model, ccmEventIncidentPreview)) {

		}

		// 判断是添加还是修改
		if (StringUtils.isEmpty(ccmEventIncidentPreview.getId())) { // 添加
			String id = UUID.randomUUID().toString();

			// 判断预处理系统是否开启
			SysConfig sysConfig = sysConfigService.get("preview_system_config");
			if (sysConfig.getParamInt() == 1) { // 开启

				// 判断事件类型是否是矛盾纠纷
				if (!"99".equals(ccmEventIncidentPreview.getEventSort())) { // 是
					CcmEventAmbi ccmEventAmbi = new CcmEventAmbi();
					if (StringUtils.isNotEmpty(ccmEventIncidentPreview.getCaseName())) {
						ccmEventAmbi.setName(ccmEventIncidentPreview.getCaseName());
					}
					if (null != ccmEventIncidentPreview.getHappenDate()) {
						ccmEventAmbi.setSendDate(ccmEventIncidentPreview.getHappenDate());
					}
					if (StringUtils.isNoneEmpty(ccmEventIncidentPreview.getCaseCondition())) {
						ccmEventAmbi.setEventSket(ccmEventIncidentPreview.getCaseCondition());
					}
					if (StringUtils.isNoneEmpty(ccmEventIncidentPreview.getCasePlace())) {
						Area area = new Area();
						area.setId(ccmEventIncidentPreview.getCasePlace());
						ccmEventAmbi.setArea(area);
					}
					if (StringUtils.isNotEmpty(ccmEventIncidentPreview.getHappenPlace())) {
						ccmEventAmbi.setSendAdd(ccmEventIncidentPreview.getHappenPlace());
					}
					if (StringUtils.isNotEmpty(ccmEventIncidentPreview.getCaseScope())) {
						ccmEventAmbi.setEventScale(ccmEventIncidentPreview.getCaseScope());
					}
					if (StringUtils.isNotEmpty(ccmEventIncidentPreview.getEventSort())) {
						ccmEventAmbi.setEventType(ccmEventIncidentPreview.getEventSort());
					}
					if (StringUtils.isNotEmpty(ccmEventIncidentPreview.getFile1())) {
						ccmEventAmbi.setFile(ccmEventIncidentPreview.getFile1());
					}
					ccmEventAmbi.setStatus("01");
					ccmEventAmbi.setCaseId(id);
					ccmEventAmbi.setId(UUID.randomUUID().toString());
					ccmEventAmbi.setIsNewRecord(true);
					ccmEventAmbiService.save(ccmEventAmbi);
				}

				ccmEventIncidentPreview.setStatus("01");
				ccmEventIncidentPreview.setId(id);
				ccmEventIncidentPreview.setIsNewRecord(true);
				ccmEventIncidentPreviewService.save(ccmEventIncidentPreview);
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if ("1".equals(reportType)) {
					CommUtil.openWinExpDiv(out, "保存app上报事件成功");
				}
				if ("2".equals(reportType)) {
					CommUtil.openWinExpDiv(out, "保存短信上报事件成功");
				}
				if ("3".equals(reportType)) {
					CommUtil.openWinExpDiv(out, "保存热线上报事件成功");
				}
				if ("4".equals(reportType)) {
					CommUtil.openWinExpDiv(out, "保存网站上报事件成功");
				}
				if ("5".equals(reportType)) {
					CommUtil.openWinExpDiv(out, "保存机顶盒上报事件成功");
				}
			} else { // 关闭
				CcmEventIncident ccmEventIncident = new CcmEventIncident();
				try {
					PropertyUtils.copyProperties(ccmEventIncident, ccmEventIncidentPreview);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ccmEventIncident.setId(id);
				ccmEventIncidentService.save(ccmEventIncident, UserUtils.getUser());
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (IOException e) {
					e.printStackTrace();
				}
				CommUtil.openWinExpDiv(out, "该事件已成功记录至事件管理系统");
			}
		} else { // 修改
			ccmEventIncidentPreviewService.save(ccmEventIncidentPreview);
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if ("1".equals(reportType)) {
				CommUtil.openWinExpDiv(out, "保存app上报事件成功");
			}
			if ("2".equals(reportType)) {
				CommUtil.openWinExpDiv(out, "保存短信上报事件成功");
			}
			if ("3".equals(reportType)) {
				CommUtil.openWinExpDiv(out, "保存热线上报事件成功");
			}
			if ("4".equals(reportType)) {
				CommUtil.openWinExpDiv(out, "保存网站上报事件成功");
			}
			if ("5".equals(reportType)) {
				CommUtil.openWinExpDiv(out, "保存机顶盒上报事件成功");
			}
		}

	}

	@ResponseBody
	@RequiresPermissions("preview:ccmEventIncidentPreview:edit")
	@RequestMapping(value = "pass")
	public Object IsPass(CcmEventIncidentPreview ccmEventIncidentPreview, Model model,
			RedirectAttributes redirectAttributes, @RequestParam(required = true) String status,HttpServletResponse response) {
		ccmEventIncidentPreview.setStatus(status);
		// 审核通过后将对应数据插到事件表中
		if (status.equals("02")) {
			CcmEventIncident ccmEventIncident = new CcmEventIncident();
			try {
				PropertyUtils.copyProperties(ccmEventIncident, ccmEventIncidentPreview);
				ccmEventIncident.setIsNewRecord(true);
				ccmEventIncident.getArea().setId(ccmEventIncidentPreview.getCasePlace());
				ccmEventIncident.setStatus("01");//未完结
				ccmEventIncident.setStick("0");//不置顶
				ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
			} catch (Exception e) {
			}
		}
		ccmEventIncidentPreviewService.save(ccmEventIncidentPreview);

		HashMap<String,Object> result = Maps.newHashMapWithExpectedSize(2);
		result.put("code", "200");
		
		return result;
	}

	@RequiresPermissions("preview:ccmEventIncidentPreview:edit")
	@RequestMapping(value = "delete")
	public String delete(CcmEventIncidentPreview ccmEventIncidentPreview, RedirectAttributes redirectAttributes) {
		ccmEventIncidentPreviewService.delete(ccmEventIncidentPreview);
		addMessage(redirectAttributes, "删除预处理事件成功");
		return "redirect:" + Global.getAdminPath() + "/preview/ccmEventIncidentPreview/"
				+ ccmEventIncidentPreview.getReportType() + "?repage";
	}

	@RequiresPermissions("preview:ccmEventIncidentPreview:edit")
	@RequestMapping(value = "check/delete")
	public void delete1(CcmEventIncidentPreview ccmEventIncidentPreview, HttpServletResponse response) {

		String[] ids = ccmEventIncidentPreview.getId().split("\\,");
		ccmEventIncidentPreview = ccmEventIncidentPreviewService.get(ids[0]);
		ccmEventIncidentPreviewService.delete(ccmEventIncidentPreview);
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		CommUtil.openWinExpDiv(out, "移除重复事件成功");
	}

	// 查重比对

	@RequiresPermissions("preview:ccmEventIncidentPreview:view")
	@RequestMapping(value = "check/list")
	public String similarityList(CcmEventIncidentPreview ccmEventIncidentPreview, Model model) {

		List<CcmEventIncidentSimilarty> simList = Lists.newArrayList();
		Double similarity = null;
		if (null != ccmEventIncidentPreview.getSimilarity() && !"".equals(ccmEventIncidentPreview.getSimilarity())) {
			similarity = Double.parseDouble(ccmEventIncidentPreview.getSimilarity());
		} else {
			similarity = 0.7d;
		}

		List<CcmEventIncidentPreview> previewList = ccmEventIncidentPreviewService.findList(ccmEventIncidentPreview);

		for (int i = 0; i < previewList.size(); i++) {
			for (int j = i + 1; j < previewList.size(); j++) {
				double titleSim = SimilarityUtil.sim(previewList.get(i).getCaseName(),
						previewList.get(j).getCaseName());
				double contentSim = SimilarityUtil.sim(previewList.get(i).getCaseCondition(),
						previewList.get(j).getCaseCondition());
				if (titleSim >= similarity || contentSim >= similarity) {
					if (Double.isNaN(contentSim)){
						contentSim=0.0;
					}
					CcmEventIncidentSimilarty similarty = new CcmEventIncidentSimilarty();
					similarty.setEventA(previewList.get(i));
					similarty.setEventB(previewList.get(j));
					similarty.setTitleSim(String.format("%.1f", titleSim * 100));
					similarty.setContentSim(String.format("%.1f", contentSim * 100));
					simList.add(similarty);
				}

			}
		}

		model.addAttribute("simList", simList);

		return "ccm/event/preview/ccmEventIncidentPreviewComparList";
	}

	@RequiresPermissions("preview:ccmEventIncidentPreview:view")
	@RequestMapping(value = "check/form")
	public String similarityForm(Model model, @RequestParam(required = true) String idA,
			@RequestParam(required = true) String idB) {

		CcmEventIncidentPreview eventA = ccmEventIncidentPreviewService.get(idA);
		CcmEventIncidentPreview eventB = ccmEventIncidentPreviewService.get(idB);
		model.addAttribute("eventA", eventA);
		model.addAttribute("eventB", eventB);
		return "ccm/event/preview/ccmEventIncidentPreviewComparForm";
	}

}