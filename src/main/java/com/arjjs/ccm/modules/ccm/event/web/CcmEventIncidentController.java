/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.event.web;

import com.arjjs.ccm.common.config.Global;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventCasedeal;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventIncident;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventCasedealService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.line.entity.CcmLineProtect;
import com.arjjs.ccm.modules.ccm.line.service.CcmLineProtectService;
import com.arjjs.ccm.modules.ccm.log.entity.CcmLogTail;
import com.arjjs.ccm.modules.ccm.log.service.CcmLogTailService;
import com.arjjs.ccm.modules.ccm.message.entity.CcmMessage;
import com.arjjs.ccm.modules.ccm.message.service.CcmMessageService;
import com.arjjs.ccm.modules.ccm.org.entity.SysArea;
import com.arjjs.ccm.modules.ccm.org.service.SysAreaService;
import com.arjjs.ccm.modules.ccm.rest.web.CcmRestEvent;
import com.arjjs.ccm.modules.ccm.sys.entity.SysDicts;
import com.arjjs.ccm.modules.ccm.sys.service.SysDictsService;
import com.arjjs.ccm.modules.pbs.sys.utils.UserUtils;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.entity.Office;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.service.OfficeService;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.DateJsonValueProcessor;
import com.arjjs.ccm.tool.DateTools;
import com.arjjs.ccm.tool.EchartType;
import com.arjjs.ccm.tool.geoJson.GeoJSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 案事件登记Controller
 *
 * @author arj
 * @version 2018-01-10
 */
@Controller
@RequestMapping(value = "${adminPath}/event/ccmEventIncident")
public class CcmEventIncidentController extends BaseController {

    @Autowired
    private CcmEventIncidentService ccmEventIncidentService;
    @Autowired
    private CcmLineProtectService ccmLineProtectService;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SysDictsService sysDictsService;
    @Autowired
    private CcmEventCasedealService ccmEventCasedealService;
    @Autowired
    private CcmMessageService ccmMessageService;
    @Autowired
    private SysAreaService sysAreaService;
    @Autowired
    private CcmLogTailService ccmLogTailService;

    @ModelAttribute
    public CcmEventIncident get(@RequestParam(required = false) String id,
                                @RequestParam(required = false) String eventPath) {
        CcmEventIncident entity = null;
        if (StringUtils.isNotBlank(id)) {
            entity = ccmEventIncidentService.get(id);
        }
        if (entity == null) {
            entity = new CcmEventIncident();
        }
        return entity;
    }

    @RequiresPermissions("event:ccmEventIncident:view")
    @ResponseBody
    @RequestMapping(value = "getById")
    public CcmEventIncident getById(CcmEventIncident ccmEventIncident) {
        return ccmEventIncident;
    }
    
    @ResponseBody
    @RequestMapping(value = "getById/{id}")
    public CcmEventIncident getById(@PathVariable String id) {

        if (StringUtils.isNotEmpty(id)) {
            return ccmEventIncidentService.get(id);
        } else {
            return new CcmEventIncident();
        }


    }


    //涉及线路
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "formIncident")
    public String formIncident(CcmEventIncident ccmEventIncident, Model model) {
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentFormIncident";
    }

    //涉及线路的菜单列表跳转（到护路护线修改）
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "formJump")
    public String formJump(CcmEventIncident ccmEventIncident, Model model) {
        //查询相关线路
        CcmLineProtect ccmLineProtect = ccmLineProtectService.get(ccmEventIncident.getOtherId());
        //查询相关案事件
        List<CcmEventIncident> ccmEventIncidentList = ccmLineProtectService.findList(ccmLineProtect.getId());
        model.addAttribute("ccmEventIncidentList", ccmEventIncidentList);
        model.addAttribute("ccmLineProtect", ccmLineProtect);
        return "ccm/line/ccmLineProtectForm";
    }

    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "saveIncident")
    public String saveIncident(CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, ccmEventIncident)) {
            return form(ccmEventIncident, model);
        }
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
        addMessage(redirectAttributes, "保存案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/line/ccmLineProtect/?repage";
    }

    @ResponseBody
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "archiveIncident")
    public Object archiveIncident(CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
        if (!beanValidator(model, ccmEventIncident)) {
//            return form(ccmEventIncident, model);
        }
        ccmEventIncident.setStatus("02");
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());

        //将信息插入日志
        User user = UserUtils.getUser();
        CcmLogTail ccmLogTail = new CcmLogTail();
//		ccmLogTail.setType("");
//		ccmLogTail.setTailPerson(ccmLogTail.getTailPerson());
        ccmLogTail.setTailTime(new Date());
        ccmLogTail.setTailContent(user.getName()+"将事件："+ccmEventIncident.getCaseName()+"归档");
        ccmLogTail.setTailCase("任务归档");
        ccmLogTail.setRelevanceTable("ccm_event_incident");
        ccmLogTail.setRelevanceId(ccmEventIncident.getId());
        ccmLogTailService.save(ccmLogTail);

        HashMap<String,Object> result = Maps.newHashMapWithExpectedSize(2);
        result.put("code", "200");

        return result;
    }

    /**
     * @param ccmEventCasedeal 事件处理原型
     * @param model
     * @return
     * @see 返回只读页面
     */
    @RequiresPermissions("event:ccmEventIncident:viewRead")
    @RequestMapping(value = "readformIncident")
    public String readformIncident(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
        model.addAttribute("CcmEventCasedealList", CcmEventCasedealList);
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentFormReadyOnly";
    }

    //线路案事件列表
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "listLine")
    public String listLine(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                           Model model) {
        Page<CcmEventIncident> page = ccmEventIncidentService.findPageLine(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        return "ccm/event/eventIncident/ccmEventIncidentListLine";
    }

    //师生案事件列表
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "listStudent")
    public String listStudent(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                              Model model) {
        Page<CcmEventIncident> page = ccmEventIncidentService.findPageStudent(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        return "ccm/event/eventIncident/ccmEventIncidentListStudent";
    }

    //命案列表
    @RequestMapping(value = "listMurder")
    public String listMurder(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                             Model model) {
        Page<CcmEventIncident> page = ccmEventIncidentService.findPageMurder(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        return "ccm/event/eventIncident/ccmEventIncidentListMurder";
    }

    //师生案事件列表修改
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "formStudent")
    public String formStudent(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
        // 返回查询结果
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("documentList", jsonDocumentList);
        model.addAttribute("documentNumber", CcmEventCasedealList.size());
        model.addAttribute("CcmEventCasedealList", CcmEventCasedealList);
        return "ccm/event/eventIncident/ccmEventIncidentFormStudent";
    }

    //命案修改
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "formMurder")
    public String formMurder(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
        // 返回查询结果
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        config.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("documentList", jsonDocumentList);
        model.addAttribute("documentNumber", CcmEventCasedealList.size());
        model.addAttribute("CcmEventCasedealList", CcmEventCasedealList);
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentFormMurder";
    }

    //线路案事件列表修改
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "formLine")
    public String formLine(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
        // 返回查询结果
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("documentList", jsonDocumentList);
        model.addAttribute("documentNumber", CcmEventCasedealList.size());
        model.addAttribute("CcmEventCasedealList", CcmEventCasedealList);
        ccmEventIncident = ccmEventIncidentService.getLine(ccmEventIncident.getId());
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentFormLine";
    }

    //线路案事件列表删除
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "deleteLine")
    public String deleteLine(CcmEventIncident ccmEventIncident, RedirectAttributes redirectAttributes) {
        ccmEventIncidentService.delete(ccmEventIncident);
        addMessage(redirectAttributes, "删除案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listLine/?repage";
    }

    //师生案事件列表删除
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "deleteStudent")
    public String deleteStudent(CcmEventIncident ccmEventIncident, RedirectAttributes redirectAttributes) {
        ccmEventIncidentService.delete(ccmEventIncident);
        addMessage(redirectAttributes, "删除案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listStudent/?repage";
    }

    //命案列表删除
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "deleteMurder")
    public String deleteMurder(CcmEventIncident ccmEventIncident, RedirectAttributes redirectAttributes) {
        ccmEventIncidentService.delete(ccmEventIncident);
        addMessage(redirectAttributes, "删除案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listMurder/?repage";
    }


    //线路案事件保存
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "saveLine")
    public String saveLine(CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, ccmEventIncident)) {
            return form(ccmEventIncident, model);
        }
        if (ccmEventIncident.getLine() != null && ccmEventIncident.getLine().getId() != null && !"".equals(ccmEventIncident.getLine())) {
            ccmEventIncident.setOtherId(ccmEventIncident.getLine().getId());
        }
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
        addMessage(redirectAttributes, "保存案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listLine/?repage";
    }

    //师生案事件保存
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "saveStudent")
    public String saveStudent(CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, ccmEventIncident)) {
            return form(ccmEventIncident, model);
        }
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
        addMessage(redirectAttributes, "保存案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listStudent/?repage";
    }

    //命案保存
    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "saveMurder")
    public String saveMurder(CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, ccmEventIncident)) {
            return formMurder(ccmEventIncident, model);
        }
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
        addMessage(redirectAttributes, "保存案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/listMurder/?repage";
    }


    //
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "form")
    public String form(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
		/*for (CcmEventCasedeal ccmEventCasedeal : CcmEventCasedealList) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(ccmEventCasedeal.getCreateDate());
//			ccmEventCasedeal.setDealDate(date);todo
		}*/
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "createDate", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("CcmEventCasedealList", jsonDocumentList);
        model.addAttribute("CasedealListNumber", CcmEventCasedealList.size());
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        if(ccmEventIncident.getIsDispatch()!=null&&ccmEventIncident.getIsDispatch().equals("1")){
            model.addAttribute("ccmEventCasedeal",new CcmEventCasedeal());
        }
        model.addAttribute("isDispatch",ccmEventIncident.getIsDispatch());
        return "ccm/event/eventIncident/ccmEventIncidentForm";
    }

    @RequestMapping(value = "historyLegacyForm")
    public String historyLegacyForm(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
		/*for (CcmEventCasedeal ccmEventCasedeal : CcmEventCasedealList) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(ccmEventCasedeal.getCreateDate());
//			ccmEventCasedeal.setDealDate(date);todo
		}*/
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "createDate", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("CcmEventCasedealList", jsonDocumentList);
        model.addAttribute("CasedealListNumber", CcmEventCasedealList.size());
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentHistoryLegacyForm";
    }

    /**
     * 案事件详情，用于弹出层dialog的显示，方法内容和form里面的一样  pengjianqiang
     *
     * @param ccmEventIncident
     * @param model
     * @return
     */
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "detail")
    public String detail(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
		/*for (CcmEventCasedeal ccmEventCasedeal : CcmEventCasedealList) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(ccmEventCasedeal.getCreateDate());
//			ccmEventCasedeal.setHandleDate(date);
		}*/
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "createDate", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("CcmEventCasedealList", jsonDocumentList);
        model.addAttribute("CasedealListNumber", CcmEventCasedealList.size());
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentDetail";
    }

    /**
     * 今日前10条案事件
     *
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getListToday")
    public List<CcmEventIncident> getListToday(Model model) {
        List<CcmEventIncident> list = ccmEventIncidentService.getListToday();
        return list;
    }

    /**
     * 事件区域分布
     */
    @ResponseBody
    @RequestMapping(value = "getSafeDisData")
    public String getSafeDisData(CcmEventIncident ccmEventIncident, Model model) {
        List<EchartType> listSafeDis = ccmEventIncidentService.getSafeDisData(ccmEventIncident); //安全事故分布
        EchartType newEchartType = new EchartType();//非空判断
        newEchartType.setType("暂无数据");
        newEchartType.setValue("0");
        if (listSafeDis.size() == 0) {
            listSafeDis.add(newEchartType);
        }
        JsonConfig config = new JsonConfig();//PingJson
        config.setExcludes(new String[]{""});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String listSafeDisString = JSONArray.fromObject(listSafeDis, config).toString(); //Json
        return listSafeDisString;
    }


    /**
     * 事件区域分布
     */
    @ResponseBody
    @RequestMapping(value = "getSafeDisDataecharts")
    public Map<String, Object> getSafeDisDataecharts(CcmEventIncident ccmEventIncident, Model model) {
        int num = 4;
        Map map = Maps.newHashMap();
        List<EchartType> listSafeDis = ccmEventIncidentService.getSafeDisData(ccmEventIncident); //安全事故分布
        List<String> listdata = Lists.newArrayList();
        List<String> listtype = Lists.newArrayList();
        List<Integer> listMaxnum = Lists.newArrayList();
//		Collections.sort(listSafeDis, Comparator.comparing(EchartType::getValue));
//		List<EchartType> reslist = listSafeDis.stream().sorted((s1, s2) -> s1.getValue().compareTo(s2.getValue())).collect(Collectors.toList());
        listSafeDis.sort(Comparator.comparing(EchartType::getValue));
//		int Maxnum = Integer.parseInt(listSafeDis.get(0).getValue())+10;
        int Maxnum = getMaxNumber(listSafeDis.get(0).getValue());
        for (int i = 0; i < listSafeDis.size(); i++) {
            if (i > num) {
                break;
            }
            listdata.add(listSafeDis.get(i).getValue());
            listtype.add(listSafeDis.get(i).getType());
            listMaxnum.add(Maxnum);
        }
        map.put("type", listtype);
        map.put("data", listdata);
        map.put("Maxnum", listMaxnum);
        return map;
    }


    public static int getMaxNumber(String a) {
        if (a == null) {
            return 0;
        }
        int len = a.length();
        if (len <= 2) {
            return Integer.parseInt(a) + 10;
        } else if (len <= 4) {
            return Integer.parseInt(a) + 100;
        } else {
            return Integer.parseInt(a) + 1000;
        }
    }


    @ResponseBody
    @RequestMapping(value = "getSafeAnalysisData")
    public Map<String, Object> getSafeAnalysisData(Model model) {
        CcmEventIncident ccmEventIncident = new CcmEventIncident();
        String[] eventTypes = new String[]{"01", "03"};
        ccmEventIncident.setEventTypes(eventTypes);
        EchartType newEchartType = new EchartType();//非空判断
        newEchartType.setType("暂无数据");
        newEchartType.setValue("0");
        List<EchartType> listSafeLevel = ccmEventIncidentService.getSafeAnalysisLevel(ccmEventIncident); //事故级别
        if (listSafeLevel.size() == 0) {
            listSafeLevel.add(newEchartType);
        }
        List<SysDicts> sysDictLevelList = sysDictsService.findAllListByType("ccm_case_grad");
        SysDicts sysDicts = new SysDicts();
        sysDicts.setValue("99");
        sysDicts.setLabel("其他");
        sysDictLevelList.add(sysDicts);
        List<EchartType> listSafeLevelNew = Lists.newArrayList();
        for (SysDicts sysDictLevel : sysDictLevelList) {
            String valueNew = "0";
            for (EchartType echartLevel : listSafeLevel) {
                String echartId = echartLevel.getTypeO();
                if (StringUtils.isNotBlank(echartId) && echartId.equals(sysDictLevel.getValue())) {
                    String value = echartLevel.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        valueNew = value;
                        break;
                    }
                }
            }
            EchartType echartType = new EchartType();
            echartType.setTypeO(sysDictLevel.getValue());
            echartType.setType(sysDictLevel.getLabel());
            echartType.setValue(valueNew);
            listSafeLevelNew.add(echartType);
        }
        List<EchartType> listSafeType = ccmEventIncidentService.getSafeAnalysisType(ccmEventIncident); //事故类型
        if (listSafeType.size() == 0) {
            listSafeType.add(newEchartType);
        }
        List<SysDicts> sysDictTypeList = sysDictsService.findAllListByType("bph_alarm_info_typecode");
        List<EchartType> listSafeTypeNew = Lists.newArrayList();
        for (SysDicts sysDictType : sysDictTypeList) {
            String valueNew = "0";
            for (EchartType echartSafeType : listSafeType) {
                String echartId = echartSafeType.getTypeO();
                if (StringUtils.isNotBlank(echartId) && echartId.equals(sysDictType.getValue())) {
                    String value = echartSafeType.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        valueNew = value;
                        break;
                    }
                }
            }
            EchartType echartType = new EchartType();
            echartType.setTypeO(sysDictType.getValue());
            echartType.setType(sysDictType.getLabel());
            echartType.setValue(valueNew);
            listSafeTypeNew.add(echartType);
        }
        List<EchartType> listSafeHappen = ccmEventIncidentService.findSumByMon(ccmEventIncident); //事故发生趋势
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        List<String> listdata = DateTools.getSixMonth(df.format(new Date()));
        List<EchartType> listSafeHappenNew = Lists.newArrayList();
        for (String data : listdata) {
            String valueNew = "0";
            for (EchartType echartHappen : listSafeHappen) {
                String type = echartHappen.getType();
                if (StringUtils.isNotBlank(type)) {
                    try {
                        type = df.format(df.parse(type));
                        if (type.equals(data)) {
                            String value = echartHappen.getValue();
                            if (StringUtils.isNotBlank(value)) {
                                valueNew = value;
                                break;
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
            EchartType echartType = new EchartType();
            echartType.setType(data);
            echartType.setValue(valueNew);
            listSafeHappenNew.add(echartType);
        }
        Map<String, Object> map = Maps.newHashMap();
        map.put("level", listSafeLevelNew);
        map.put("type", listSafeTypeNew);
        map.put("happen", listSafeHappenNew);
        return map;
    }

    /**
     * 事故发生趋势
     */
    @ResponseBody
    @RequestMapping(value = "getSafeHappenData")
    public String getSafeHappenData(Model model) {
        CcmEventIncident ccmEventIncident = new CcmEventIncident();
        ccmEventIncident.setEventType("01");
        List<EchartType> listSafeHappen = ccmEventIncidentService.findSumByMon(ccmEventIncident); //事故发生趋势
        EchartType newEchartType = new EchartType();//非空判断
        newEchartType.setType("暂无数据");
        newEchartType.setValue("0");
        if (listSafeHappen.size() == 0) {
            listSafeHappen.add(newEchartType);
        }
        JsonConfig config = new JsonConfig();//PingJson
        config.setExcludes(new String[]{""});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String listSafeHappenString = JSONArray.fromObject(listSafeHappen, config).toString(); //Json
        return listSafeHappenString;
    }

    /**
     * 事故类型
     */
    @ResponseBody
    @RequestMapping(value = "getSafeTypeData")
    public String getSafeTypeData(Model model) {
        CcmEventIncident ccmEventIncident = new CcmEventIncident();
        ccmEventIncident.setEventType("01");
        List<EchartType> listSafeType = ccmEventIncidentService.getSafeTypeData(ccmEventIncident); //事故类型
        EchartType newEchartType = new EchartType();//非空判断
        newEchartType.setType("暂无数据");
        newEchartType.setValue("0");
        if (listSafeType.size() == 0) {
            listSafeType.add(newEchartType);
        }
        JsonConfig config = new JsonConfig();//PingJson
        config.setExcludes(new String[]{""});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String listSafeTypeString = JSONArray.fromObject(listSafeType, config).toString(); //Json
        return listSafeTypeString;
    }

    /**
     * 事故级别
     */
    @ResponseBody
    @RequestMapping(value = "getSafeLevelData")
    public String getSafeLevelData(Model model) {
        CcmEventIncident ccmEventIncident = new CcmEventIncident();
        ccmEventIncident.setEventType("01");
        List<EchartType> listSafeLevel = ccmEventIncidentService.getSafeLevelData(ccmEventIncident); //事故级别
        EchartType newEchartType = new EchartType();//非空判断
        newEchartType.setType("暂无数据");
        newEchartType.setValue("0");
        if (listSafeLevel.size() == 0) {
            listSafeLevel.add(newEchartType);
        }
        JsonConfig config = new JsonConfig();//PingJson
        config.setExcludes(new String[]{""});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String listSafeLevelString = JSONArray.fromObject(listSafeLevel, config).toString(); //Json
        return listSafeLevelString;
    }


    //本月事件发生前十——社区/街道//近一年重点人员帮扶/事件发生趋势图
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "findEventMapJump")
    public String findEventMapJump(Model model) {
        return "ccm/event/map/eventMapJump";
    }


    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = {"list", ""})
    public String list(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        Page<CcmEventIncident> page = ccmEventIncidentService.findPage(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        if ("1".equals(ccmEventIncident.getHistoryLegacy())) {
            return "ccm/event/eventIncident/ccmEventIncidentHistoryLegacyList";
        } else {
            return "ccm/event/eventIncident/ccmEventIncidentList";
        }
    }
    
    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = {"listCheck"})
    public String listCheck(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        Page<CcmEventIncident> page = ccmEventIncidentService.findPage(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        return "ccm/event/eventIncident/ccmEventIncidentCheck";
    }
    
    @RequestMapping(value = "/saveIncidentPlace", method = RequestMethod.POST)
    public void saveIncidentPlace(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String deleteType = ccmEventIncident.getDeleteType();
			String placeId = ccmEventIncident.getPlaceId();
			String[] ids = ccmEventIncident.getIds();
			if(StringUtils.isNotBlank(deleteType) && deleteType.equals("2")) {
				List<String> eventIdList = ccmEventIncidentService.getIncidentPlace(ccmEventIncident);
				for (int i = 0; i < ids.length; i++) {
					boolean bool = true;
					for (String eventId : eventIdList) {
						if(eventId.equals(ids[i])) {
							bool = false;
							break;
						}
					}
					if(bool) {
						CcmEventIncident ccmEventIncidentNew = new CcmEventIncident();
						ccmEventIncidentNew.setId(UUID.randomUUID().toString());
						ccmEventIncidentNew.setEventId(ids[i]);
						ccmEventIncidentNew.setPlaceId(placeId);
						ccmEventIncidentService.saveIncidentPlace(ccmEventIncidentNew);
					}
				}
			}else {
				ccmEventIncidentService.deleteIncidentPlace(ccmEventIncident);
				for (int i = 0; i < ids.length; i++) {
					CcmEventIncident ccmEventIncidentNew = new CcmEventIncident();
					ccmEventIncidentNew.setId(UUID.randomUUID().toString());
					ccmEventIncidentNew.setEventId(ids[i]);
					ccmEventIncidentNew.setPlaceId(placeId);
					ccmEventIncidentService.saveIncidentPlace(ccmEventIncidentNew);
				}
			}
			PrintWriter out = response.getWriter();
			CommUtil.openWinExpDiv(out, "保存成功");
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @RequestMapping(value = "geteventList")
	@ResponseBody
    public Object geteventList(String placeId) {
		if (StringUtils.isBlank(placeId)) {
			return null;
		}
		CcmEventIncident ccmEventIncident = new CcmEventIncident();
		ccmEventIncident.setPlaceId(placeId);
		List<CcmEventIncident> listccmEventIncident = ccmEventIncidentService.getIncidents(ccmEventIncident);
		if(listccmEventIncident.size() <= 0) {
			return null;
		}
		return listccmEventIncident;
	}

    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "save")
    public void save(HttpServletRequest request,
                     HttpServletResponse response, CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes) throws IOException {
        boolean isSave = false;
        if ("".equals(ccmEventIncident.getId()) || ccmEventIncident.getId() == null) {
            ccmEventIncident.setStatus("01");
            isSave = true;
        }else if(ccmEventIncident.getStatus()!=null&&ccmEventIncident.getStatus().equals("02")){//将事件设为完结归档
            //将信息插入日志
            User user = UserUtils.getUser();
            CcmLogTail ccmLogTail = new CcmLogTail();
            ccmLogTail.setTailTime(new Date());
            ccmLogTail.setTailContent(user.getName()+"将事件："+ccmEventIncident.getCaseName()+"归档");
            ccmLogTail.setTailCase("任务归档");
            ccmLogTail.setRelevanceTable("ccm_event_incident");
            ccmLogTail.setRelevanceId(ccmEventIncident.getId());
            ccmLogTailService.save(ccmLogTail);
        }
        ccmEventIncident.setReportType("0");
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());

        PrintWriter out = response.getWriter();
        CommUtil.openWinExpDiv(out, "保存成功");


        if(isSave){
            User user1 = UserUtils.getUser();
            ccmLogTailService.addEventLogTail(ccmEventIncident,user1);
            SysArea entity = sysAreaService.get(ccmEventIncident.getArea().getId());
            List<String> parentIds = Arrays.asList(entity.getParentIds().split(","));
            // 查询需要发送的用户
            List<User> assignUser = ccmEventCasedealService.findAssignUser(ccmEventIncident.getArea().getId(), parentIds);

            //拼接数据
            if(assignUser.size()>0){
                List<CcmMessage> list = new ArrayList<CcmMessage>();
                for (User user : assignUser) {
                    CcmMessage ccmMessage = new CcmMessage();
                    ccmMessage.preInsert();
                    ccmMessage.setType("01");//事件上报消息

                    Date createDate = ccmEventIncident.getCreateDate();
                    String str = "MM-dd HH:mm:ss";
                    SimpleDateFormat sdf = new SimpleDateFormat(str);
                    ccmMessage.setContent("事件上报："+sdf.format(createDate)+"："+ccmEventIncident.getCaseName());
                    ccmMessage.setReadFlag("0");//未读
                    ccmMessage.setObjId(ccmEventIncident.getId());
                    ccmMessage.setUserId(user.getId());
                    list.add(ccmMessage);
                }
                //批量添加
                ccmMessageService.insertEventAll(list);

                CcmRestEvent.sendMessageToMq(list);
            }
        }
    }

    @RequestMapping(value = "historyLegacySave")
    public void historyLegacySave(HttpServletRequest request, HttpServletResponse response, CcmEventIncident ccmEventIncident, Model model, RedirectAttributes redirectAttributes) throws IOException {
        if ("".equals(ccmEventIncident.getId()) || ccmEventIncident.getId() == null) {
            ccmEventIncident.setStatus("01");
        }
        ccmEventIncidentService.save(ccmEventIncident,UserUtils.getUser());
        PrintWriter out = response.getWriter();
        CommUtil.openWinExpDiv(out, "保存成功");
    }

    @RequestMapping(value = "historyLegacyDelete")
    public String historyLegacyDelete(CcmEventIncident ccmEventIncident, RedirectAttributes redirectAttributes) {
        ccmEventIncidentService.delete(ccmEventIncident);
        addMessage(redirectAttributes, "删除案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/list/?repage&historyLegacy=1";
    }

    @RequiresPermissions("event:ccmEventIncident:edit")
    @RequestMapping(value = "delete")
    public String delete(CcmEventIncident ccmEventIncident, RedirectAttributes redirectAttributes) {
        ccmEventIncidentService.delete(ccmEventIncident);
        addMessage(redirectAttributes, "删除案事件登记成功");
        return "redirect:" + Global.getAdminPath() + "/event/ccmEventIncident/list/?repage";
    }


    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<CcmLineProtect> list = ccmLineProtectService.findList(new CcmLineProtect());
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                CcmLineProtect c = list.get(i);
                if ((StringUtils.isBlank(extId) || (extId != null && !extId.equals(c.getId())))) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("id", c.getId());
                    map.put("pId", "0");
                    map.put("name", c.getName());
                    mapList.add(map);
                }
            }
        }
        return mapList;
    }

    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "map")
    public String map(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                      Model model) {
        //系统级别
/*		SysConfig sysConfig = new SysConfig();
		sysConfig = sysConfigService.get("system_level");
		String level = sysConfig.getParamStr();
		model.addAttribute("level", level);
		model.addAttribute("ccmEventAmbi", ccmEventAmbi);*/
        return "dma/schoolsafe/dmaSchoolSafeMap";
    }

    /**
     * 根据综治组织队伍性别统计情况
     *
     * @param model
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "getItemByPropertyec")
    public List<com.arjjs.ccm.tool.EchartType> getItemByPropertyec(Model model) {
        // 返回对象结果
        List<EchartType> list = ccmEventIncidentService.getItemByPropertyec();
        return list;
    }


    /**
     * @author wangyikai
     * @方法描述 通过时间和案件类型查询警情信息四色图
     * 从扁平化项目中移植过来，在地图上进行事件分析的展示
     */
    @ResponseBody
    @RequestMapping(value = "queryAlarmInfoByDateAndAlarmTypeToFourColor")
    public GeoJSON queryAlarmInfoByDateAndAlarmTypeToFourColor(CcmEventIncident ccmEventIncident) {
        return ccmEventIncidentService.queryAlarmInfoByDateAndAlarmTypeToFourColor(ccmEventIncident);
    }

    /**
     * @author wangyikai
     * @方法描述 通过时间和案件类型查询警情信息热力图和聚合图
     * 从扁平化项目中移植过来，在地图上进行事件分析的展示
     */
    @ResponseBody
    @RequestMapping(value = "queryAlarmInfoByDateAndAlarmType")
    public GeoJSON queryAlarmInfoByDateAndAlarmType(CcmEventIncident ccmEventIncident) {
        return ccmEventIncidentService.queryAlarmInfoByDateAndAlarmType(ccmEventIncident);
    }

    @ResponseBody
    @RequestMapping(value = "incident")
    public int incident(CcmEventIncident ccmEventIncident) {
        return ccmEventIncidentService.incident(ccmEventIncident);
    }


    @RequestMapping(value = "drawForm")
    public String drawForm(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                           Model model) {
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentDrawPointForm";

    }

    //以区域为区分用图表显示涉及命案的案件
    @ResponseBody
    @RequestMapping(value = "getHomicideByArea")
    public Map<String, Object> getHomicideByArea(CcmEventIncident ccmEventIncident) {
        // 返回对象结果
        List<EchartType> list = ccmEventIncidentService.getHomicideByArea(ccmEventIncident);
        String[] type = null;
        String[] value = null;
        if (list.size() == 0) {
            type = new String[1];
            value = new String[1];
            type[0] = "中国";
            value[0] = "0";
        } else {
            type = new String[list.size()];
            value = new String[list.size()];
        }

        for (int i = 0; i < list.size(); i++) {
            //赋值
            type[i] = list.get(i).getType();
            value[i] = list.get(i).getValue();
        }
        Map<String, Object> data = new HashMap<>();
        data.put("type", type);
        data.put("value", value);
        return data;
    }

    @RequiresPermissions({"user"})
    @ResponseBody
    @RequestMapping({"officeTreeData"})
    public List<Map<String, Object>> officeTreeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String type, @RequestParam(required = false) Long grade, @RequestParam(required = false) Boolean isAll, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Office> list = ccmEventIncidentService.findOfficeList();

        for (int i = 0; i < list.size(); ++i) {
            Office e = (Office) list.get(i);
            if ((StringUtils.isBlank(extId) || extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1) && (type == null || type != null && (!type.equals("1") || type.equals(e.getType()))) && (grade == null || grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()) && "1".equals(e.getUseable())) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("pIds", e.getParentIds());
                map.put("name", e.getName());
                if (type != null && "3".equals(type)) {
                    map.put("isParent", true);
                }

                mapList.add(map);
            }
        }

        return mapList;
    }

    // 柱形图
    @ResponseBody
    @RequestMapping(value = "getCountEventByNumber")
    public Object getCountEvent() {

        List dateList = getMonth(12);
        List dataList = Lists.newArrayList();

        List<EchartType> typeList = ccmEventIncidentService.getcountevent("12");

        int temp;
        for (int i = 0; i < dateList.size(); i++) {
            temp = 0;
            for (int j = 0; j < typeList.size(); j++) {

                if (dateList.get(i).equals(typeList.get(j).getType())) {
                    dataList.add(typeList.get(j).getValue());
                    temp = temp + 1;
                }

                if (j == typeList.size() - 1 && temp == 0) {
                    dataList.add("0");
                }
            }

        }

        HashMap<Object, Object> map = Maps.newHashMapWithExpectedSize(2);
        map.put("dateList", dateList);
        map.put("dataList", dataList);


        return map;
    }


    // 柱形图+折线图
    @ResponseBody
    @RequestMapping(value = "getCountEventByLine")
    public Object getCountEventByLine() {

        List dateList = getMonth(12);
        List lineList = Lists.newArrayList();


        List<EchartType> typeList = ccmEventIncidentService.findSolveByMon();
        for (EchartType echartType : typeList) {

        }

        int temp;
        for (int i = 0; i < dateList.size(); i++) {
            temp = 0;
            for (int j = 0; j < typeList.size(); j++) {

                if (dateList.get(i).equals(typeList.get(j).getType())) {
                    lineList.add(typeList.get(j).getValue());
                    temp = temp + 1;
                }

                if (j == typeList.size() - 1 && temp == 0) {
                    lineList.add("0.00");
                }
            }

        }

        HashMap<Object, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("dateList", dateList);
        map.put("lineList", lineList);


        return map;
    }


    private List<String> getMonth(Integer number) {
        // 月份正常传入 如：1=1
        List<String> dateList = Lists.newArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        for (int i = number; i > 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - i);
            Date date = calendar.getTime();
            dateList.add(sdf.format(date));
        }

        return dateList;
    }


    @RequestMapping(value = "mapvForm")
    public String mapvForm(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventIncidentService.findList(ccmEventIncident.getId());
		/*for (CcmEventCasedeal ccmEventCasedeal : CcmEventCasedealList) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(ccmEventCasedeal.getCreateDate());
//			ccmEventCasedeal.setDealDate(date);todo
		}*/
        JsonConfig config = new JsonConfig();
        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "createDate", "updateDate", "sqlMap"});
        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
        model.addAttribute("CcmEventCasedealList", jsonDocumentList);
        model.addAttribute("CasedealListNumber", CcmEventCasedealList.size());
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentMapvForm";
    }

    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "dispatch")
    public String dispatch(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                       Model model) {
        ccmEventIncident.setStatus("01");//只查询未完结的
        Page<CcmEventIncident> page = ccmEventIncidentService.findDispatchPage(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        return "ccm/event/eventIncident/ccmEventIncidentDispatchList";
    }

    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "archive")
    public String archive(CcmEventIncident ccmEventIncident, HttpServletRequest request, HttpServletResponse response,
                           Model model) {
        Page<CcmEventIncident> page = ccmEventIncidentService.findArchivePage(new Page<CcmEventIncident>(request, response),
                ccmEventIncident);
        model.addAttribute("page", page);
        return "ccm/event/eventIncident/ccmEventIncidentArchiveList";
 }

    @RequiresPermissions("event:ccmEventIncident:view")
    @RequestMapping(value = "archiveForm")
    public String archiveForm(CcmEventIncident ccmEventIncident, Model model) {
        List<CcmLogTail> CcmLogTailList = ccmEventIncidentService.findEventProcessLogTail(ccmEventIncident.getId());
        List<CcmEventCasedeal> CcmEventCasedealList = ccmEventCasedealService.findListByEventId(ccmEventIncident);
        for (CcmLogTail ccmLogTail : CcmLogTailList) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String date = sdf.format(ccmLogTail.getTailTime());
//			ccmEventCasedeal.setDealDate(date);todo
		}
//        JsonConfig config = new JsonConfig();
//        config.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "dbName", "global", "page", "createDate", "updateDate", "sqlMap"});
//        config.setIgnoreDefaultExcludes(false);  //设置默认忽略
//        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//        String jsonDocumentList = JSONArray.fromObject(CcmEventCasedealList, config).toString();
//        model.addAttribute("CcmEventCasedealList", jsonDocumentList);
        model.addAttribute("CcmEventCasedealList",CcmEventCasedealList);
        model.addAttribute("CcmLogTailList", CcmLogTailList);
        model.addAttribute("CcmLogTailListNumber", CcmLogTailList.size());
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        return "ccm/event/eventIncident/ccmEventIncidentArchiveForm";
    }
    
    @RequiresPermissions({"user"})
    @ResponseBody
    @RequestMapping({"treeDataOfEvent"})
    public List<Map<String, Object>> treeDataOfEvent(@RequestParam(required = false) String extId, HttpServletResponse response) {
      List<Map<String, Object>> mapList = Lists.newArrayList();
      SysArea area = new SysArea();
      area.setParentIds(UserUtils.getUser().getOffice().getArea().getId());
      List<SysArea> list = sysAreaService.findByPid(area);
      for (int i = 0; i < list.size(); i++) {
    	  SysArea e = (SysArea)list.get(i);
        if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
          Map<String, Object> map = Maps.newHashMap();
          map.put("id", e.getId());
          map.put("pId", e.getParent().getId());
          map.put("name", e.getName());
          mapList.add(map);
        } 
      } 
      return mapList;
    }
}