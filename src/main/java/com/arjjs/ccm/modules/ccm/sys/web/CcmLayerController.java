package com.arjjs.ccm.modules.ccm.sys.web;

import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.ccmsys.entity.CcmDevice;
import com.arjjs.ccm.modules.ccm.ccmsys.service.CcmDeviceService;
import com.arjjs.ccm.modules.ccm.citycomponents.service.CcmCityComponentsService;
import com.arjjs.ccm.modules.ccm.citycomponents.service.CcmLandService;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventIncident;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseBuildmanageService;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseSchoolrimService;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgArea;
import com.arjjs.ccm.modules.ccm.org.service.CcmOrgAreaService;
import com.arjjs.ccm.modules.ccm.org.service.CcmOrgNpseService;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPeople;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPeopleService;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPopTenantService;
import com.arjjs.ccm.modules.ccm.view.entity.VCcmOrg;
import com.arjjs.ccm.modules.ccm.view.entity.VCcmTeam;
import com.arjjs.ccm.modules.ccm.view.service.VCcmOrgService;
import com.arjjs.ccm.modules.ccm.view.service.VCcmTeamService;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.service.AreaService;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.DateJsonValueProcessor;
import com.google.common.collect.Lists;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller
 *
 * @author arj
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/layer")
public class CcmLayerController extends BaseController {

    // 楼栋管理
    @Autowired
    private CcmHouseBuildmanageService ccmHouseBuildmanageService;
    // 设备管理
    @Autowired
    private CcmDeviceService ccmDeviceService;
    // 社会区域
    @Autowired
    private CcmOrgAreaService ccmOrgAreaService;
    // 土地管理
    @Autowired
    private CcmLandService ccmLandService;
    // 城市部件
    @Autowired
    private CcmCityComponentsService ccmCityComponentsService;
    // 重点场所
    @Autowired
    private CcmOrgNpseService ccmOrgNpseService;
    // 学校
    @Autowired
    private CcmHouseSchoolrimService ccmHouseSchoolrimService;
    // 人员管理
    @Autowired
    private CcmPeopleService ccmPeopleService;
    // 租房
    @Autowired
    private CcmPopTenantService ccmPopTenantService;
    // 事件管理
    @Autowired
    private CcmEventIncidentService ccmEventIncidentService;
    // 区域
    @Autowired
    private AreaService areaService;
    // 机构
    @Autowired
    private VCcmOrgService vCcmOrgService;
    // 用户
    @Autowired
    private VCcmTeamService vCcmTeamService;

    // 时间数据结果
    private static SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    // 返回结果
    @RequestMapping(value = "layerIndex", method = RequestMethod.GET)
    public String layerIndex() {
        return "modules/sys/layer/mapLayer";
    }

    // 通过layer页面返回 地图地址并对事件信息赋值
    @RequestMapping(value = "layMap", method = RequestMethod.GET)
    public String layMap(@RequestParam(required = false) String id, @RequestParam(required = false) String caseName,
                         @RequestParam(required = false) String areaPoint, Model model) {

        // 查询相关结果
        CcmEventIncident ccmEventIncidentDto = new CcmEventIncident();
        ccmEventIncidentDto.setId(id);
        CcmEventIncident ccmEventIncident = ccmEventIncidentService.get(ccmEventIncidentDto);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
            @Override
            public boolean apply(Object source, String name, Object value) {
                if (name.equals("area") || name.equals("createBy") || name.equals("updateBy")
                        || name.equals("currentUser") || name.equals("page") || name.equals("sqlMap")) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        ccmEventIncident.setCasePlace(ccmEventIncident.getArea().getName());
        jsonConfig.registerJsonValueProcessor(Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
        // 传入到页面数据
        model.addAttribute("areaPointIncidentMap", areaPoint);
        model.addAttribute("caseName", caseName);
        model.addAttribute("happenDate",
                ccmEventIncident.getHappenDate() == null ? "" : time.format(ccmEventIncident.getHappenDate()) + "  ");
        model.addAttribute("eventIncidentId", id);
        // model.addAttribute("ccmEventIncident",
        // JSONObject.fromObject(ccmEventIncident, jsonConfig).toString()
        // .replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2"));
        model.addAttribute("ccmEventIncident", ccmEventIncident);
        // 返回结果
        // String json = JSONObject.fromObject(ccmEventIncident,
        // jsonConfig).toString();
        // String t = json.replaceAll("\"(\\w+)\"(\\s*:\\s*)", "$1$2");

        // 网格
        Area area = new Area();
        area.setId(ccmEventIncident.getArea().getId());
        area = areaService.get(area);
        CcmOrgArea ccmOrgArea = new CcmOrgArea();
        ccmOrgArea.setArea(area);
        ccmOrgArea = ccmOrgAreaService.findCcmOrgArea(ccmOrgArea);
        model.addAttribute("areaIncident", area);
        model.addAttribute("ccmOrgAreaIncident", ccmOrgArea);
        model.addAttribute("netMapIncident", ccmOrgArea.getAreaMap());

        // 嫌疑人
        CcmPeople ccmPeopleIncident = new CcmPeople();
        boolean isfind = false;
        if(StringUtils.isNotEmpty(ccmEventIncident.getCulPaperid())) {
        	List<CcmPeople> ccmPeopleList = ccmPeopleService.getByIdent(ccmEventIncident.getCulPaperid());
        	if(ccmPeopleList.size()>0) {
        		isfind = true;
        		ccmPeopleIncident = ccmPeopleList.get(0);
        	}
        }
        if (ccmEventIncident.getCulName() != null && !"".equals(ccmEventIncident.getCulName()) && !isfind) {
            CcmPeople ccmPeople = new CcmPeople();
            ccmPeople.setName(ccmEventIncident.getCulName());
            List<CcmPeople> ccmPeopleList = ccmPeopleService.findSuspect(ccmPeople);
            int flag = 0;
            for (CcmPeople l : ccmPeopleList) {
                int flag2 = 0;
                flag2 += (l.getIsAids() != null && l.getIsAids() == 1) ? 1 : 0;
                flag2 += (l.getIsBehind() != null && l.getIsBehind() == 1) ? 1 : 0;
                flag2 += (l.getIsDangerous() != null && l.getIsDangerous() == 1) ? 1 : 0;
                flag2 += (l.getIsDrugs() != null && l.getIsDrugs() == 1) ? 1 : 0;
                flag2 += (l.getIsHeresy() != null && l.getIsHeresy() == 1) ? 1 : 0;
                flag2 += (l.getIsKym() != null && l.getIsKym() == 1) ? 1 : 0;
                flag2 += (l.getIsPsychogeny() != null && l.getIsPsychogeny() == 1) ? 1 : 0;
                flag2 += (l.getIsRectification() != null && l.getIsRectification() == 1) ? 1 : 0;
                flag2 += (l.getIsRelease() != null && l.getIsRelease() == 1) ? 1 : 0;
                flag2 += (l.getIsVisit() != null && l.getIsVisit() == 1) ? 1 : 0;
                if (flag <= flag2) {
                    flag = flag2;
                    isfind = true;
                    ccmPeopleIncident = l;
                }
            }
        }
        if (ccmPeopleIncident != null) {
            ccmPeopleIncident = ccmPeopleService.getSuspect(ccmPeopleIncident);
            model.addAttribute("ccmPeopleIncident", ccmPeopleIncident);
            // 人员类型
            if (ccmPeopleIncident != null) {
                String popTypeIncident = "";
                popTypeIncident += (ccmPeopleIncident.getIsAids() != null && ccmPeopleIncident.getIsAids() == 1)
                        ? "艾滋病患者," : "";
                popTypeIncident += (ccmPeopleIncident.getIsBehind() != null && ccmPeopleIncident.getIsBehind() == 1)
                        ? "留守人员," : "";
                popTypeIncident += (ccmPeopleIncident.getIsDangerous() != null
                        && ccmPeopleIncident.getIsDangerous() == 1) ? "危险品从业人员," : "";
                popTypeIncident += (ccmPeopleIncident.getIsDrugs() != null && ccmPeopleIncident.getIsDrugs() == 1)
                        ? "吸毒人员," : "";
                popTypeIncident += (ccmPeopleIncident.getIsHeresy() != null && ccmPeopleIncident.getIsHeresy() == 1)
                        ? "涉教人员," : "";
                popTypeIncident += (ccmPeopleIncident.getIsKym() != null && ccmPeopleIncident.getIsKym() == 1)
                        ? "重点青少年," : "";
                popTypeIncident += (ccmPeopleIncident.getIsPsychogeny() != null
                        && ccmPeopleIncident.getIsPsychogeny() == 1) ? "肇事肇祸等严重精神障碍患者," : "";
                popTypeIncident += (ccmPeopleIncident.getIsRectification() != null
                        && ccmPeopleIncident.getIsRectification() == 1) ? "社区矫正人员," : "";
                popTypeIncident += (ccmPeopleIncident.getIsRelease() != null && ccmPeopleIncident.getIsRelease() == 1)
                        ? "安置帮教人员," : "";
                popTypeIncident += (ccmPeopleIncident.getIsVisit() != null && ccmPeopleIncident.getIsVisit() == 1)
                        ? "重点上访人员," : "";
                if (!"".equals(popTypeIncident)) {
                    popTypeIncident = popTypeIncident.substring(0, popTypeIncident.length() - 1);
                }
                model.addAttribute("popTypeIncident", popTypeIncident);
            }
        }

        // 摄像机
        CcmDevice ccmDevice = new CcmDevice();
        List<CcmDevice> listccmDevice = Lists.newArrayList();
        if (ccmEventIncident.getAreaPoint() != null && !"".equals(ccmEventIncident.getAreaPoint())) {
            ccmDevice.setCoordinate(ccmEventIncident.getAreaPoint());
            List<CcmDevice> ccmDeviceList = ccmDeviceService.findList(ccmDevice);
            if (ccmDeviceList.size() > 0) {
                ccmDevice = ccmDeviceList.get(0);
                ccmDeviceList.get(0).setParam("");
                listccmDevice.add(ccmDevice);
            } else {
                String[] s1 = ccmDevice.getCoordinate().split(",");
                double lat1 = Double.parseDouble(s1[0]);
                double lng1 = Double.parseDouble(s1[1]);
                double distance = 0.0;
                double min = 1000d;
                int flag = 0;
                CcmDevice ccmDeviceNull = new CcmDevice();
                List<CcmDevice> ccmAllDeviceList = ccmDeviceService.findList(ccmDeviceNull);
                for (CcmDevice ccmDevice2 : ccmAllDeviceList) {
                    if (ccmDevice2.getCoordinate() != null && ccmDevice2.getCoordinate().contains(",")) {//存在坐标，且坐标用逗号隔开
                        String[] s2 = ccmDevice2.getCoordinate().split(",");
                        double lat2 = Double.parseDouble(s2[0]);
                        double lng2 = Double.parseDouble(s2[1]);
                        distance = CommUtil.getDistance(lat1, lng1, lat2, lng2);
                        if (distance <= min) {
                            min = distance;
                            ccmDevice = ccmDevice2;
                            ccmDevice2.setParam("");
                            listccmDevice.add(ccmDevice2);
                            flag = 1;
                        }
                    }
                }

                if (flag == 0) {
                    ccmDevice = null;
                }
            }
        }
        if (ccmDevice != null) {
            model.addAttribute("ccmDeviceIncident", ccmDevice);
            model.addAttribute("reslistccmDeviceIncident", listccmDevice);
        }

        // 家庭及同住
        if (ccmPeopleIncident != null && ccmPeopleIncident.getId() != null && !"".equals(ccmPeopleIncident.getId())) {
            /**** 户籍家庭成员 ****/
            List<CcmPeople> listAccount = new ArrayList<CcmPeople>();
            if (!StringUtils.isBlank(ccmPeopleIncident.getAccount())) {
                listAccount = ccmPeopleService.listAccount(ccmPeopleIncident);
            }
            model.addAttribute("listAccountIncident", listAccount);

            /**** 同一房屋人员 ****/
            List<CcmPeople> listRoomPeople = new ArrayList<CcmPeople>();
            if (!StringUtils.isBlank(ccmPeopleIncident.getRoomIdString())) {
                listRoomPeople = ccmPeopleService.getHousePopList(ccmPeopleIncident);
            }
            model.addAttribute("listRoomPeopleIncident", listRoomPeople);
        }

        // 事件所在地负责人
        // model.addAttribute("areaIncident", area);
        // model.addAttribute("ccmOrgAreaIncident", ccmOrgArea);
        VCcmOrg vCcmOrgNow = new VCcmOrg();
        vCcmOrgNow.setArea(area);
        List<VCcmOrg> vCcmOrgList = vCcmOrgService.findList(vCcmOrgNow);// 机构
        model.addAttribute("vCcmOrgListIncident", vCcmOrgList);
        VCcmTeam VCcmTeam1 = new VCcmTeam();
        if (area.getType().equals("7")) {
            VCcmTeam1.setId(area.getParentId());
        } else {
            VCcmTeam1.setId(area.getId());
        }

        List<VCcmTeam> vCcmTeamList = vCcmTeamService.findAreaList(VCcmTeam1);// 区域查用户
        model.addAttribute("vCcmTeamListIncident", vCcmTeamList);
        // 事件嫌疑人所住地负责人
        if (ccmPeopleIncident != null && ccmPeopleIncident.getId() != null && !"".equals(ccmPeopleIncident.getId())) {
            Area areaLiveIncident = new Area();
            areaLiveIncident = ccmPeopleIncident.getAreaGridId();
            areaLiveIncident = areaService.get(areaLiveIncident);// 嫌疑人所住地网格
            CcmOrgArea ccmOrgAreaLive = new CcmOrgArea();
            ccmOrgAreaLive.setArea(areaLiveIncident);
            ccmOrgAreaLive = ccmOrgAreaService.getCcmOrgArea(ccmOrgAreaLive);// 嫌疑人所住地网格外表
            model.addAttribute("areaLiveIncident", areaLiveIncident);
            model.addAttribute("ccmOrgAreaLiveIncident", ccmOrgAreaLive);

            if (areaLiveIncident.getId() != null && !"".equals(areaLiveIncident.getId())) {
                VCcmOrg vCcmOrgLiveNow = new VCcmOrg();
                vCcmOrgLiveNow.setArea(areaLiveIncident);
                List<VCcmOrg> vCcmOrgLiveList = vCcmOrgService.findList(vCcmOrgLiveNow);// 机构
                model.addAttribute("vCcmOrgLiveListIncident", vCcmOrgLiveList);
                VCcmTeam VCcmTeam2 = new VCcmTeam();
                VCcmTeam2.setId(areaLiveIncident.getId());
                List<VCcmTeam> vCcmTeamLiveList = vCcmTeamService.findAreaList(VCcmTeam2);// 区域查用户
                model.addAttribute("vCcmTeamLiveListIncident", vCcmTeamLiveList);
            }
        }

        return "modules/sys/map/mapIndex";
    }

}