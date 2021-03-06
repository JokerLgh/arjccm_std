package com.arjjs.ccm.modules.ccm.sys.web;

import com.arjjs.ccm.common.gis.MapUtil;
import com.arjjs.ccm.common.gis.Point;
import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.utils.IdGen;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.common.web.BaseController;
import com.arjjs.ccm.modules.ccm.broadcast.entity.CcmDeviceBroadcast;
import com.arjjs.ccm.modules.ccm.broadcast.service.CcmDeviceBroadcastService;
import com.arjjs.ccm.modules.ccm.ccmsys.entity.CcmDevice;
import com.arjjs.ccm.modules.ccm.ccmsys.entity.CcmMobileDevice;
import com.arjjs.ccm.modules.ccm.ccmsys.service.CcmDeviceService;
import com.arjjs.ccm.modules.ccm.ccmsys.service.CcmMobileDeviceService;
import com.arjjs.ccm.modules.ccm.citycomponents.entity.CcmCityComponents;
import com.arjjs.ccm.modules.ccm.citycomponents.entity.CcmLand;
import com.arjjs.ccm.modules.ccm.citycomponents.service.CcmCityComponentsService;
import com.arjjs.ccm.modules.ccm.citycomponents.service.CcmLandService;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventIncident;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseBuildmanage;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseSchoolrim;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseBuildmanageService;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseSchoolrimService;
import com.arjjs.ccm.modules.ccm.org.entity.*;
import com.arjjs.ccm.modules.ccm.org.service.*;
import com.arjjs.ccm.modules.ccm.place.entity.CcmBasePlace;
import com.arjjs.ccm.modules.ccm.place.service.CcmBasePlaceService;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPeople;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPopTenant;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPeopleService;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPopTenantService;
import com.arjjs.ccm.modules.ccm.religion.entity.CcmPlaceReligion;
import com.arjjs.ccm.modules.ccm.religion.service.CcmPlaceReligionService;
import com.arjjs.ccm.modules.ccm.sys.entity.SysConfig;
import com.arjjs.ccm.modules.ccm.sys.service.SysConfigService;
import com.arjjs.ccm.modules.ccm.view.entity.VCcmOrg;
import com.arjjs.ccm.modules.ccm.view.entity.VCcmTeam;
import com.arjjs.ccm.modules.ccm.view.service.VCcmOrgService;
import com.arjjs.ccm.modules.ccm.view.service.VCcmTeamService;
import com.arjjs.ccm.modules.flat.realtimeSituation.entity.DataList;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.entity.Office;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.service.OfficeService;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.*;
import com.arjjs.ccm.tool.geoJson.Features;
import com.arjjs.ccm.tool.geoJson.GeoJSON;
import com.arjjs.ccm.tool.geoJson.Geometry;
import com.arjjs.ccm.tool.geoJson.Properties;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Controller
 *
 * @author arj
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/map")
public class CcmMapOtherController extends BaseController {

    @Autowired
    private CcmPeopleService ccmPeopleService;
    // 公共社区管理
    @Autowired
    private CcmOrgCommonalityService ccmOrgCommonalityService;
    // 楼栋管理
    @Autowired
    private CcmHouseBuildmanageService ccmHouseBuildmanageService;
    // 房屋管理
    @Autowired
    private CcmPopTenantService ccmPopTenantService;
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
    // App电子围栏
    @Autowired
    private CcmMobileDeviceService ccmMobileDeviceService;
    //公共机构人员
    @Autowired
    private CcmOrgComPopService ccmOrgComPopService;
    //config
    @Autowired
    private SysConfigService sysConfigService;
    //机构
    @Autowired
    private VCcmOrgService vCcmOrgService;
    //用户
    @Autowired
    private VCcmTeamService vCcmTeamService;
    @Autowired
    private CcmEventIncidentService ccmEventIncidentService;
    //policeRoom
    @Autowired
    private CcmOrgComprehensiveService ccmOrgComprehensiveService;
    @Autowired
    private CcmBasePlaceService ccmBasePlaceService;
    @Autowired
    private OfficeService officeService;

    @Autowired
    private CcmDeviceBroadcastService ccmDeviceBroadcastService;

    @Autowired
    private CcmPlaceReligionService ccmPlaceReligionService;
    /**
     * @param CcmOrgCommonality
     * @param model
     * @return
     * @see 生成公共机构地图信息-区域图
     */
    //@RequiresPermissions("org:ccmOrgCommonality:view")
    @ResponseBody
    @RequestMapping(value = "ccmOrgCommonalityMap")
    public GeoJSON ccmOrgCommonalityMap(@RequestParam(required = false) String type, Model model, @RequestParam(required = false) String ids) {
        CcmOrgCommonality ccmOrgCommonalityDto = new CcmOrgCommonality();
        if (StringUtils.isNotEmpty(ids)) {
            ccmOrgCommonalityDto.setMore1("a.id in(" + ids + ")");

        }
        String commonalityType = CommUtil.ReturnCommonalityType(type);
        ccmOrgCommonalityDto.setType(commonalityType);
        // 生成公共机构 list
        List<CcmOrgCommonality> ccmOrgCommonalityList = new ArrayList<>();
        // 01-08 的公共机构类型数据 判断当前不为空
        if (!StringUtils.isEmpty(commonalityType)) {

            ccmOrgCommonalityList = ccmOrgCommonalityService.findCommonalityList(ccmOrgCommonalityDto);
        }

        // 返回对象
        GeoJSON geoJSON = new GeoJSON();
        List<Features> featureList = new ArrayList<Features>();

        // 公共机构
        for (CcmOrgCommonality ccmOrgCommonality : ccmOrgCommonalityList) {
            // 特征属性
            Features featureDto = new Features();
            Properties properties = new Properties();
            // 1 type 默认不填
            // 2 id 添加
            featureDto.setId(ccmOrgCommonality.getId());
            // 3 properties 展示属性信息
            properties.setName(ccmOrgCommonality.getName());
            properties.setIcon(ccmOrgCommonality.getImage());
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            // 创建附属信息
            map.put("id", ccmOrgCommonality.getId());
            map.put("机构名称", ccmOrgCommonality.getName());
            map.put("机构类型", DictUtils.getDictLabel(ccmOrgCommonality.getType(), "ccm_org_commonality_type", ""));
            map.put("机构联系电话", ccmOrgCommonality.getOrgTel());
            map.put("负责人姓名", ccmOrgCommonality.getPrincipalName());
            map.put("负责人电话", ccmOrgCommonality.getPrincipalTel());
            map.put("所属区域", ccmOrgCommonality.getArea().toString() + "");
            //公共机构人员
            CcmOrgComPop ccmOrgComPop = new CcmOrgComPop();
            ccmOrgComPop.setCommonalityId(ccmOrgCommonality);
            List<CcmOrgComPop> listCcmOrgComPops = ccmOrgComPopService.findList(ccmOrgComPop);
            if (listCcmOrgComPops.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.setExcludes(new String[]{"createBy", "updateBy", "createDate", "updateDate", "delFlag", "page",
                        "beginBirthday", "birthday", "commonalityId", "dbName", "files", "global", "id", "image", "isNewRecord",
                        "more1", "more2", "more3", "orgCode", "orgTel", "principalName", "principalTel", "sqlMap", "type",
                        "currentUser", "education", "endBirthday", "fixTel", "idenCode", "nation", "politics", "profExpertise"});
                config.setIgnoreDefaultExcludes(false);  //设置默认忽略
                config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                String listCcmOrgComPop = JSONArray.fromObject(listCcmOrgComPops, config).toString(); //
                map.put("公共机构人员", listCcmOrgComPop);
            } else {
                map.put("公共机构人员", "[{\"code\":\"\",\"idenNum\":\"\",\"images\":\"\",\"name\":\"\",\"remarks\":\"\",\"service\":\"\",\"sex\":\"\",\"telephone\":\"\"}]");
            }
            properties.addInfo(map);
            featureList.add(featureDto);
            featureDto.setProperties(properties);
            // 4 geometry 配置参数
            Geometry geometry = new Geometry();
            featureDto.setGeometry(geometry);
            // 点位信息 测试为面
            geometry.setType("Polygon");
            // 添加具体数据
            if (!StringUtils.isEmpty(ccmOrgCommonality.getAreaPoint())) {
                // 获取中心点的值
                String[] centpoint = ccmOrgCommonality.getAreaPoint().split(",");
                // 图层中心点
                geoJSON.setCentpoint(centpoint);
                // 图形中心点
                properties.setCoordinateCentre(centpoint);
            }
            // 封装的list 以有孔与无孔进行添加
            List<Object> CoordinateslistR = new ArrayList<>();
            // 以下是无孔面积数组
            String[] coordinates = (StringUtils.isEmpty(ccmOrgCommonality.getAreaMap()) ? ";"
                    : ccmOrgCommonality.getAreaMap()).split(";");
            // 返回无孔结果 2层数据一个数据源
            List<String[]> Coordinateslist = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates.length > 1) {
                    String corrdinate = coordinates[i];
                    // 以“，”为分割数据
                    String[] a = corrdinate.split(",");
                    Coordinateslist.add(a);
                } else {
                    // 补充一个空的数据源
                    String[] a = {"", ""};
                    Coordinateslist.add(a);
                }
            }
            // 根据格式要求 两层list
            CoordinateslistR.add(Coordinateslist);
            // 获取最后的结果
            geometry.setCoordinates(CoordinateslistR);
        }
        geoJSON.setFeatures(featureList);
        // 如果无数据
        if (featureList.size() == 0) {
            return null;
        }
        return geoJSON;

    }

    /**
     * @param ccmHouseBuildmanage
     * @param model
     * @return
     * @see 生成地图信息-区域图
     */
    //@RequiresPermissions("house:ccmHouseBuildmanage:view")
    @ResponseBody
    @RequestMapping(value = "buildmanageMap")
    public GeoJSON buildmanageMap(CcmHouseBuildmanage ccmHouseBuildmanage, Model model, @RequestParam(required = false) String ids) {

        //List<CcmHouseBuildmanage> houseBuildmanages = ccmHouseBuildmanageService.queryMapOtherBuildList(ccmHouseBuildmanage);

        Area userArea = UserUtils.getUser().getOffice().getArea();
        ccmHouseBuildmanage.setUserArea(userArea);
        if (StringUtils.isNotEmpty(ids)) {
            ccmHouseBuildmanage.setMore1("a.id in(" + ids + ")");

        }
        // 查询房屋地图信息
        List<CcmHouseBuildmanage> ccmHouseBuildmanages = ccmHouseBuildmanageService.findList(ccmHouseBuildmanage);
        // 返回对象
        GeoJSON geoJSON = new GeoJSON();
        List<Features> featureList = new ArrayList<Features>();
        // 数组
        for (CcmHouseBuildmanage Buildmanage : ccmHouseBuildmanages) {
            // 特征属性
            Features featureDto = new Features();
            Properties properties = new Properties();
            // 1 type 默认不填
            // 2 id 添加
            featureDto.setId(Buildmanage.getId());
            // 3 properties 展示属性信息
            // properties.setName(Buildmanage.getName());
            properties.setName(Buildmanage.getBuildname());
            properties.setIcon(Buildmanage.getImage());
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建附属信息
            map.put("id", Buildmanage.getId());
            // map.put("楼栋名称", Buildmanage.getBuildname());
            map.put("所属小区", Buildmanage.getName());
            map.put("电话", Buildmanage.getTel());
            if(Buildmanage.getPilesNum()!=null){
                map.put("层数", Buildmanage.getPilesNum() + "");
            }else{
                map.put("层数", "0");
            }
           if(Buildmanage.getElemNum()!=null){
               map.put("单元数", Buildmanage.getElemNum() + "");
           }else{
               map.put("单元数", "0");
           }

            properties.addInfo(map);
            featureList.add(featureDto);
            featureDto.setProperties(properties);
            // 4 geometry 配置参数
            Geometry geometry = new Geometry();
            featureDto.setGeometry(geometry);
            // 点位信息 测试为面
            geometry.setType("Polygon");
            // 获取中心点的值
            if (StringUtils.isNotEmpty(Buildmanage.getAreaPoint())) {
                String[] centpoint = Buildmanage.getAreaPoint().split(",");
                // 添加 图层中心点
                geoJSON.setCentpoint(centpoint);
                // 添加图形中心点
                properties.setCoordinateCentre(centpoint);
            }
            // 添加具体数据
            // 封装的list 以有孔与无孔进行添加
            List<Object> CoordinateslistR = new ArrayList<>();
            // 以下是无孔面积数组
            String[] coordinates = (StringUtils.isEmpty(Buildmanage.getAreaMap()) ? ";" : Buildmanage.getAreaMap())
                    .split(";");
            // 返回无孔结果 2层数据一个数据源
            List<String[]> Coordinateslist = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates.length > 0) {
                    String corrdinate = coordinates[i];
                    // 以“，”为分割数据
                    String[] a = corrdinate.split(",");
                    Coordinateslist.add(a);
                } else {
                    // 补充一个空的数据源
                    String[] a = {"", ""};
                    Coordinateslist.add(a);
                }
            }
            // 根据格式要求 两层list
            CoordinateslistR.add(Coordinateslist);
            // 获取最后的结果
            geometry.setCoordinates(CoordinateslistR);
        }
        geoJSON.setFeatures(featureList);
        // 如果无数据
        if (featureList.size() == 0) {
            return null;
        }
        return geoJSON;
    }

    /**
     * @param ccmHouseBuildmanage
     * @param model
     * @return
     * @see 生成指定的信息的 地图信息 1 重点人员 2 出租屋
     */
    @ResponseBody
    @RequestMapping(value = "buildmanageMapType")
    public GeoJSON buildmanageMapType(@RequestParam(required = false) String type, String flag, Model model) {
        // 基础数据对象Map
        Map<String, CcmHouseBuildmanage> ccmHouseBuildmanageMap = ccmHouseBuildmanageService
                .findMap(new CcmHouseBuildmanage());
        CcmPeople cmPeopleDto = new CcmPeople();
        // 返回指定的 ID-list
        List<String> BuildSpeIDs = new ArrayList<>();
        // 根据Type进行生成指定的build对象
        if ("3".equals(type)) {
            cmPeopleDto = CommUtil.ReturnMapBuildType("127", "a23"); // 默认全部的
            // 重点人员类型
            // 查询含有特殊人群的楼栋地图信息
            BuildSpeIDs = ccmHouseBuildmanageService.findBuildListBySpecilPop(cmPeopleDto);
        } else if ("1".equals(type)) {
            if ("".equals(flag) || flag == null) {
                cmPeopleDto = CommUtil.ReturnPeoTypeUNION("1023"); // 默认全部的 重点人员类型
                // 查询含有特殊人群的楼栋地图信息
                BuildSpeIDs = ccmHouseBuildmanageService.findBuildListBySpecilPopUNION(cmPeopleDto);
            }
            if (!"".equals(flag) && flag != null) {
                cmPeopleDto = CommUtil.ReturnPeoTypeFlag(flag); // flag查询重点人员类型
                // 查询含有特殊人群的楼栋地图信息
                BuildSpeIDs = ccmHouseBuildmanageService.findBuildListBySpecilPopUNION(cmPeopleDto);
            }


        } else {
            BuildSpeIDs = ccmHouseBuildmanageService.findBuildListByPrup();
        }
        // 初始化用于返回对象的List
        List<CcmHouseBuildmanage> ccmHouseBuildmanageList = new ArrayList<>();
        // 返回生成的对象List
        for (String id : BuildSpeIDs) {
            ccmHouseBuildmanageList.add(ccmHouseBuildmanageMap.get(id));
        }
        // 返回对象
        GeoJSON geoJSON = new GeoJSON();
        List<Features> featureList = new ArrayList<Features>();
        // 数组
        for (CcmHouseBuildmanage Buildmanage : ccmHouseBuildmanageList) {
            // 特征属性
            Features featureDto = new Features();
            Properties properties = new Properties();
            // 1 type 默认不填
            // 2 id 添加
            featureDto.setId(Buildmanage.getId());
            // 3 properties 展示属性信息
            properties.setName(Buildmanage.getName());
            properties.setIcon(Buildmanage.getImage());
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建附属信息
            map.put("id", Buildmanage.getId());
            map.put("楼栋名称", Buildmanage.getBuildname());
            map.put("电话", Buildmanage.getTel());
            map.put("层数", Buildmanage.getPilesNum() + "");
            map.put("单元数", Buildmanage.getElemNum() + "");
            properties.addInfo(map);
            featureList.add(featureDto);
            featureDto.setProperties(properties);
            // 4 geometry 配置参数
            Geometry geometry = new Geometry();
            featureDto.setGeometry(geometry);
            // 点位信息 测试为面
            geometry.setType("Polygon");
            // 获取中心点的值
            if (StringUtils.isNotEmpty(Buildmanage.getAreaPoint())) {
                String[] centpoint = Buildmanage.getAreaPoint().split(",");
                // 图层中心点
                geoJSON.setCentpoint(centpoint);
                // 图形中心点
                properties.setCoordinateCentre(centpoint);
            }
            // 添加具体数据
            // 封装的list 以有孔与无孔进行添加
            List<Object> CoordinateslistR = new ArrayList<>();
            // 以下是无孔面积数组
            String[] coordinates = (StringUtils.isEmpty(Buildmanage.getAreaMap()) ? ";" : Buildmanage.getAreaMap())
                    .split(";");
            // 返回无孔结果 2层数据一个数据源
            List<String[]> Coordinateslist = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates.length > 1) {
                    String corrdinate = coordinates[i];
                    // 以“，”为分割数据
                    String[] a = corrdinate.split(",");
                    Coordinateslist.add(a);
                } else {
                    // 补充一个空的数据源
                    String[] a = {"", ""};
                    Coordinateslist.add(a);
                }
            }
            // 根据格式要求 两层list
            CoordinateslistR.add(Coordinateslist);
            // 获取最后的结果
            geometry.setCoordinates(CoordinateslistR);
        }
        geoJSON.setFeatures(featureList);
        // 如果无数据
        if (featureList.size() == 0) {
            return null;
        }
        return geoJSON;
    }

    /**
     * @param type  人员类别
     * @param id    楼栋ID
     * @param model 房屋模型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getHousesSpe")
    public List<String> getHousesSpe(@RequestParam(required = false) String type,
                                     @RequestParam(required = false) String id, Model model) {
        CcmPopTenant ccmPopTenantDto = new CcmPopTenant();
        // 生成楼栋信息
        if (!StringUtils.isBlank(id)) {
            CcmHouseBuildmanage ccmHouseBuildmanageDto = new CcmHouseBuildmanage();
            ccmHouseBuildmanageDto.setId(id);
            ccmPopTenantDto.setBuildingId(ccmHouseBuildmanageDto);
        }
        // 设置房屋的重点人员类型
        ccmPopTenantDto.setMore1(CommUtil.ReturnPeoType(type).getMore1());
        // 返回对象结果
        List<String> lists = ccmPopTenantService.getStringSpecial(ccmPopTenantDto);
        // 返回重点人员 房屋的名单
        return lists;
    }


    /**
     * @param type  人员类别
     * @param id    楼栋ID
     * @param model 房屋模型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getHousesSpeMore")
    public List<SearchTabMM> getHousesSpeMore(@RequestParam(required = false) String type,
                                              @RequestParam(required = false) String id, Model model) {
        CcmPopTenant ccmPopTenantDto = new CcmPopTenant();
        // 生成楼栋信息
        if (!StringUtils.isBlank(id)) {
            CcmHouseBuildmanage ccmHouseBuildmanageDto = new CcmHouseBuildmanage();
            ccmHouseBuildmanageDto.setId(id);
            ccmPopTenantDto.setBuildingId(ccmHouseBuildmanageDto);
        }
        // 设置房屋的重点人员类型
        ccmPopTenantDto.setMore1(CommUtil.ReturnPeoTypeMore(type).getMore1());
        // 返回对象结果
        List<SearchTabMM> lists = ccmPopTenantService.getStringSpecialMore(ccmPopTenantDto);
        // 返回重点人员 房屋的名单
        return lists;
    }

    // 绘制地图信息
    @ResponseBody
    @RequestMapping(value = "updateMap")
    public String updateMap(String type, String id, @RequestParam(required = false) String areaMap,
                            @RequestParam(required = false) String areaPoint,
                            @RequestParam(required = false) String color) {
        // 创建更新信息并进行更新信息
        if (type.contains("area")) {
            type = "area";
        }
        boolean flag = false;
        String info = "设置";
        if ((areaMap == null || areaMap.equals("")) && (areaPoint == null || areaPoint.equals(""))) {
            info = "删除";
        }
        switch (type) {
            // 楼栋
            case "build": {
                CcmHouseBuildmanage ccmHouseBuildmanage = new CcmHouseBuildmanage();
                ccmHouseBuildmanage.setId(id);
                ccmHouseBuildmanage.setAreaMap(areaMap);
                ccmHouseBuildmanage.setAreaPoint(areaPoint);
                flag = ccmHouseBuildmanageService.updateCoordinates(ccmHouseBuildmanage);
                return info + "楼栋坐标" + (flag ? "成功" : "失败");
            }
            // 公共社区
            case "commonality": {
                CcmOrgCommonality ccmOrgCommonality = new CcmOrgCommonality();
                ccmOrgCommonality.setId(id);
                ccmOrgCommonality.setAreaMap(areaMap);
                ccmOrgCommonality.setAreaPoint(areaPoint);
                flag = ccmOrgCommonalityService.updateCoordinates(ccmOrgCommonality);
                return info + "公共社区坐标" + (flag ? "成功" : "失败");
            }
            // 重点部位
            case "npse": {
                CcmOrgNpse ccmOrgNpse = new CcmOrgNpse();
                ccmOrgNpse.setId(id);
                ccmOrgNpse.setAreaMap(areaMap);
                ccmOrgNpse.setAreaPoint(areaPoint);
                flag = ccmOrgNpseService.updateCoordinates(ccmOrgNpse);
                return info + "重点部位坐标" + (flag ? "成功" : "失败");
            }
            // 学校
            case "school": {
                CcmHouseSchoolrim ccmHouseSchoolrim = new CcmHouseSchoolrim();
                ccmHouseSchoolrim.setId(id);
                ccmHouseSchoolrim.setAreaMap(areaMap);
                ccmHouseSchoolrim.setAreaPoint(areaPoint);
                flag = ccmHouseSchoolrimService.updateCoordinates(ccmHouseSchoolrim);
                return info + "学校坐标" + (flag ? "成功" : "失败");
            }
            // 城市部件
            case "citycomponents": {
                CcmCityComponents ccmCityComponents = new CcmCityComponents();
                ccmCityComponents.setId(id);
                ccmCityComponents.setAreaMap(areaMap);
                ccmCityComponents.setAreaPoint(areaPoint);
                flag = ccmCityComponentsService.updateCoordinates(ccmCityComponents);
                return info + "城市部件坐标" + (flag ? "成功" : "失败");
            }
            // 摄像机
            case "camera": {
                CcmDevice ccmDevice = new CcmDevice();
                ccmDevice.setId(id);
                ccmDevice.setCoordinate(areaPoint);
                // 更新
                flag = ccmDeviceService.updateCoordinates(ccmDevice);
                return info + "摄像机坐标" + (flag ? "成功" : "失败");
            }
            // 区域图
            case "area": {
                CcmOrgArea ccmOrgArea = new CcmOrgArea();
                Area area = new Area();
                area.setId(id);
                ccmOrgArea.setArea(area);
                
                //从数据库表中获取已有数据，接口设置前进行判断，有值则进行赋值，没有值的话则用原来数据库中的数据
                CcmOrgArea ccmOrgAreaDB = new CcmOrgArea();
                CcmOrgArea ccmOrgAreaForm = new CcmOrgArea();
                ccmOrgAreaForm.setArea(area);
                ccmOrgAreaDB = ccmOrgAreaService.getCcmOrgArea(ccmOrgAreaForm);
                if (ccmOrgAreaDB != null && ccmOrgAreaDB.getId() != null && !"".equals(ccmOrgAreaDB.getId()) ) {//数据库中存在该数据
                	if (areaMap != null && !"".equals(areaMap)) {
                        ccmOrgArea.setAreaMap(areaMap);
                	} else {
                        ccmOrgArea.setAreaMap(ccmOrgAreaDB.getAreaMap());
                	}
                	
                	if (areaPoint != null && !"".equals(areaPoint)) {
                        ccmOrgArea.setAreaPoint(areaPoint);
                	} else {
                        ccmOrgArea.setAreaPoint(ccmOrgAreaDB.getAreaPoint());
                	}
                	
                	if (color != null && !"".equals(color)) {
                        ccmOrgArea.setAreaColor(color);
                	} else {
                        ccmOrgArea.setAreaColor(ccmOrgAreaDB.getAreaColor());
                	}

                    flag = ccmOrgAreaService.updateCoordinates(ccmOrgArea);
                    
                    return info + "区域地理信息" + (flag ? "成功" : "失败");
                    
                } else {//数据不存在则新增
                    ccmOrgArea.setAreaMap(areaMap);
                    ccmOrgArea.setAreaPoint(areaPoint);
                    ccmOrgArea.setAreaColor(color);
                    ccmOrgAreaService.save(ccmOrgArea);

                    return info + "区域地理信息成功";
                }
                
            }
            case "land": {
                CcmLand ccmLand = new CcmLand();
                ccmLand.setId(id);
                ccmLand.setAreaMap(areaMap);
                ccmLand.setAreaPoint(areaPoint);
                flag = ccmLandService.updateCoordinates(ccmLand);
                return info + "土地坐标" + (flag ? "成功" : "失败");
            }
            case "appEfence": {
                CcmMobileDevice ccmMobileDevice = new CcmMobileDevice();
                ccmMobileDevice.setId(id);
                ccmMobileDevice.setEfenceScope(areaMap);
                ccmMobileDevice.setEfencePoint(areaPoint);
                flag = ccmMobileDeviceService.updateCoordinates(ccmMobileDevice);
                return info + "电子围栏坐标" + (flag ? "成功" : "失败");
            }

            // 综治机构
            case "vccmorg": {
                Office office = new Office();
                office = officeService.get(id);

                CcmOrgComprehensive ccmOrgComprehensive = ccmOrgComprehensiveService.findOfficeId(id);
                if (ccmOrgComprehensive != null) {
                    ccmOrgComprehensive.setIsNewRecord(false);
                    ccmOrgComprehensive.setAreaMap(areaMap);
                    ccmOrgComprehensive.setAreaPoint(areaPoint);
                    flag = ccmOrgComprehensiveService.updateCoordinates(ccmOrgComprehensive);
                    return info + "综治机构坐标" + (flag ? "成功" : "失败");
                } else {
                    CcmOrgComprehensive ccmOrgComprehensive1 = new CcmOrgComprehensive();
                    ccmOrgComprehensive1.setIsNewRecord(true);
                    ccmOrgComprehensive1.setId(IdGen.uuid());
                    ccmOrgComprehensive1.setOffice(office);
                    ccmOrgComprehensive1.setAreaMap(areaMap);
                    ccmOrgComprehensive1.setAreaPoint(areaPoint);
                    ccmOrgComprehensive1.setCreateBy(UserUtils.getUser());
                    ccmOrgComprehensive1.setUpdateBy(UserUtils.getUser());
                    Date date1 = new Date();
                    ccmOrgComprehensive1.setUpdateDate(date1);
                    ccmOrgComprehensive1.setCreateDate(date1);
                    ccmOrgComprehensive1.setDelFlag("0");
                    flag = ccmOrgComprehensiveService.insertCoordinates(ccmOrgComprehensive1);
                    return info + "综治机构坐标" + (flag ? "成功" : "失败");
                }
            }
            // 场所
            case "basePlace": {
                CcmBasePlace ccmBasePlace = new CcmBasePlace();
                ccmBasePlace.setId(id);
                ccmBasePlace.setAreaMap(areaMap);
                ccmBasePlace.setAreaPoint(areaPoint);
                flag = ccmBasePlaceService.updateCoordinates(ccmBasePlace);
                return info + "场所坐标" + (flag ? "成功" : "失败");
            }
            // 广播站
            case "broadcast": {
                CcmDeviceBroadcast broadcast = new CcmDeviceBroadcast();
                broadcast.setId(id);
                broadcast.setCoordinate(areaPoint);
                // 更新
                flag = ccmDeviceBroadcastService.updateCoordinates(broadcast);
                return info + "广播站坐标" + (flag ? "成功" : "失败");
            }
            // 警务室
            case "policeroom": {
                CcmOrgCommonality ccmOrgCommonality = new CcmOrgCommonality();
                ccmOrgCommonality.setId(id);
                ccmOrgCommonality.setAreaMap(areaMap);
                ccmOrgCommonality.setAreaPoint(areaPoint);
                flag = ccmOrgCommonalityService.updateCoordinates(ccmOrgCommonality);
                return info + "警务室" + (flag ? "成功" : "失败");
            }
            // 社区(村)工作站
            case "workstation": {
                CcmOrgCommonality ccmOrgCommonality = new CcmOrgCommonality();
                ccmOrgCommonality.setId(id);
                ccmOrgCommonality.setAreaMap(areaMap);
                ccmOrgCommonality.setAreaPoint(areaPoint);
                flag = ccmOrgCommonalityService.updateCoordinates(ccmOrgCommonality);
                return info + "社区(村)工作站" + (flag ? "成功" : "失败");
            }
        }
        return "该类别有误！";
    }

    // 返回首页地图
    @RequestMapping(value = "indexMap", method = RequestMethod.GET)
    public String indexMap(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/map/mapIndex";
    }

    // 返回首页地图
    @RequestMapping(value = "indexiot", method = RequestMethod.GET)
    public String indexIot(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/map/indexIot";
    }

    // 返回可视化重点人员专题库
    @RequestMapping(value = "keyPersonal", method = RequestMethod.GET)
    public String importantPeople(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/index/keyPersonal";
    }

    // 返回可视化宗教专题库
    @RequestMapping(value = "religionIndex", method = RequestMethod.GET)
    public String religionIndex(CcmPlaceReligion ccmPlaceReligion,Model model,String religiontype) {
        //系统级别
        Map<String,Integer> map = Maps.newHashMap();
        List<CcmPlaceReligion> findList = ccmPlaceReligionService.findList(ccmPlaceReligion);
        List<CcmPlaceReligion> resList = Lists.newArrayList();
        int num = 1;
        if(religiontype == null){
            religiontype = "01";
        }
        for (CcmPlaceReligion religion : findList) {
            religion.setCcmBasePlace(ccmBasePlaceService.get(religion.getBasePlaceId()));
            if(map.containsKey(religion.getReligionType())){
                String type = religion.getReligionType();
                int value = map.get(type);
                 map.put(religion.getReligionType(), value+1);
            } else {
                map.put(religion.getReligionType(), num);
            }
            if(religiontype.equals(religion.getReligionType())){
                resList.add(religion);
            }
        }
        model.addAttribute("type", religiontype);
        model.addAttribute("map", map);
        model.addAttribute("list", resList);
        return "modules/sys/index/religionIndex";
    }

    // GIS数据
    @RequestMapping(value = "GISData", method = RequestMethod.GET)
    public String GISData(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/map/GISData";
    }

    // 事件统计分析
    @RequestMapping(value = "eventAnalysis", method = RequestMethod.GET)
    public String eventAnalysis(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/map/eventAnalysis";
    }

    // 数据采集分析
    @RequestMapping(value = "dataAnalysis", method = RequestMethod.GET)
    public String dataAnalysis(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/map/dataAnalysis";
    }

    // APP终端
    @RequestMapping(value = "APPTerminal", method = RequestMethod.GET)
    public String APPTerminal(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;
        model.addAttribute("sysConfig", sysConfig);
        return "modules/sys/map/APPTerminal";
    }

    //返回综治首页
    @RequestMapping(value = "indexZongZhi", method = RequestMethod.GET)
    public String indexzhongzhi(Model model) {
        return "modules/sys/index/indexZongZhi";
    }


    // 个人门户辖区地图
    @SuppressWarnings("static-access")
    @RequestMapping(value = {"homeAeraMap"})
    public String homeAeraMap(HttpServletRequest request, HttpServletResponse response,
                              String height, String width, String content, String divId) {

        request.setAttribute("porheight", height);
        //request.setAttribute("porwidth", width);
        //request.setAttribute("porcontent", content);
        //request.setAttribute("divId", divId);
        request.setAttribute("map", "true");
        return "plm/home/map/homeAreaMap";
    }

    //返回重点人员是否有位置地图
    @ResponseBody
    @RequestMapping(value = "getBuildKeyManMap")
    public String getBuildKeyManMap(Model model, String peopleId) {
        // 返回对象结果
        CcmPeople ccmPeople = new CcmPeople();
        ccmPeople = ccmPeopleService.get(peopleId);
        String areaPoint = "";
        if (ccmPeople != null && ccmPeople.getRoomId() != null && ccmPeople.getRoomId().getId() != null && !"".equals(ccmPeople.getRoomId().getId())) {
            CcmPopTenant ccmPopTenant = new CcmPopTenant();
            ccmPopTenant = ccmPopTenantService.get(ccmPeople.getRoomId().getId());
            if (ccmPopTenant != null && ccmPopTenant.getBuildingId() != null && ccmPopTenant.getBuildingId().getId() != null && !"".equals(ccmPopTenant.getBuildingId().getId())) {
                CcmHouseBuildmanage ccmHouseBuildmanage = new CcmHouseBuildmanage();
                ccmHouseBuildmanage = ccmHouseBuildmanageService.get(ccmPopTenant.getBuildingId().getId());
                if (ccmHouseBuildmanage != null) {
                    areaPoint = ccmHouseBuildmanage.getAreaPoint();
                }
            }
        }
        return areaPoint;
    }

    // 返回重点人员位置地图
    @RequestMapping(value = "buildKeyManMap", method = RequestMethod.GET)
    public String buildKeyManMap(Model model, String peopleId) {
        CcmPeople ccmPeople = new CcmPeople();
        ccmPeople = ccmPeopleService.get(peopleId);
        String areaPoint = "";
        if (ccmPeople != null && ccmPeople.getRoomId() != null && ccmPeople.getRoomId().getId() != null && !"".equals(ccmPeople.getRoomId().getId())) {
            CcmPopTenant ccmPopTenant = new CcmPopTenant();
            ccmPopTenant = ccmPopTenantService.get(ccmPeople.getRoomId().getId());
            if (ccmPopTenant != null && ccmPopTenant.getBuildingId() != null && ccmPopTenant.getBuildingId().getId() != null && !"".equals(ccmPopTenant.getBuildingId().getId())) {
                CcmHouseBuildmanage ccmHouseBuildmanage = new CcmHouseBuildmanage();
                ccmHouseBuildmanage = ccmHouseBuildmanageService.get(ccmPopTenant.getBuildingId().getId());
                if (ccmHouseBuildmanage != null) {
                    areaPoint = ccmHouseBuildmanage.getAreaPoint();
                }
            }
        }
        model.addAttribute("areaPoint", areaPoint);
        model.addAttribute("ccmPeople", ccmPeople);
        return "modules/sys/map/mapIndex";
    }


    //返回事件是否有位置地图
    @ResponseBody
    @RequestMapping(value = "getIncidentMap")
    public String getIncidentMap(Model model, String id, String type) {
        // 返回对象结果
        CcmEventIncident ccmEventIncident = new CcmEventIncident();
        ccmEventIncident = ccmEventIncidentService.get(id);
        String areaPointIncident = "";
        if (ccmEventIncident != null && ccmEventIncident.getAreaPoint() != null && !"".equals(ccmEventIncident.getAreaPoint()) && ccmEventIncident.getAreaPoint().indexOf(",") != -1) {
            areaPointIncident = ccmEventIncident.getAreaPoint();
        }
        return areaPointIncident;
    }

    // 返回事件位置地图
    @RequestMapping(value = "incidentMap", method = RequestMethod.GET)
    public String incidentMap(Model model, String id, String type) {
        CcmEventIncident ccmEventIncident = new CcmEventIncident();
        ccmEventIncident = ccmEventIncidentService.get(id);
        String areaPointIncident = "";
        if (ccmEventIncident != null && ccmEventIncident.getAreaPoint() != null && !"".equals(ccmEventIncident.getAreaPoint())) {
            areaPointIncident = ccmEventIncident.getAreaPoint();
        }

        String geoJSONIncidentBuildmanage = "";//定位事件周边重点人
        List<CcmHouseBuildmanage> ccmHouseBuildmanageLists = new ArrayList<>();
        String geoJSONIncidentCcmDevice = "";//定位事件周边摄像头
        List<CcmDevice> ccmdevicelists = new ArrayList<>();

        //定位事件周边重点人
        if (!"".equals(areaPointIncident) && areaPointIncident.indexOf(",") != -1) {
            String[] point = areaPointIncident.split(",");

            double lat1 = Double.parseDouble(point[0]);
            double lng1 = Double.parseDouble(point[1]);
            double lat2;
            double lng2;
            List<CcmHouseBuildmanage> ccmHouseBuildmanageList = ccmHouseBuildmanageService.findList(new CcmHouseBuildmanage());
            // 数组
            for (CcmHouseBuildmanage Buildmanage : ccmHouseBuildmanageList) {
                if (Buildmanage.getAreaPoint() != null && !"".equals(Buildmanage.getAreaPoint())) {
                    String[] point1 = Buildmanage.getAreaPoint().split(",");
                    if (point1.length == 2) {
                        lat2 = Double.parseDouble(point1[0]);
                        lng2 = Double.parseDouble(point1[1]);
                        double km = CommUtil.getDistance(lat1, lng1, lat2, lng2);
                        if (km <= 450d) {
                            ccmHouseBuildmanageLists.add(Buildmanage);
                        }

                    }
                }
            }

        }
        JsonConfig config1 = new JsonConfig();
        config1.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "createDate", "updateDate", "remarks",
                "floorArea", "buildNum", "buildPeo", "buildPname", "sex", "nation", "content", "birthday", "education",
                "phone", "residence", "residencedetail", "image", "area", "more1", "checkUser",
                "dbName", "delFlag", "global", "isNewRecord", "page", "sqlMap"});
        config1.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config1.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        geoJSONIncidentBuildmanage = JSONArray.fromObject(ccmHouseBuildmanageLists, config1).toString(); //Json


        //定位事件周边摄像头
        if (!"".equals(areaPointIncident) && areaPointIncident.indexOf(",") != -1) {
            String[] point = areaPointIncident.split(",");
            double lat1 = Double.parseDouble(point[0]);
            double lng1 = Double.parseDouble(point[1]);
            double lat2;
            double lng2;
            List<CcmDevice> ccmdevicelist = ccmDeviceService.findList(new CcmDevice());
            // 数组
            for (CcmDevice device : ccmdevicelist) {
                if (device.getCoordinate() != null && !"".equals(device.getCoordinate())) {
                    String[] point2 = device.getCoordinate().split(",");
                    if (point2.length == 2) {
                        lat2 = Double.parseDouble(point2[0]);
                        lng2 = Double.parseDouble(point2[1]);
                        double km = CommUtil.getDistance(lat1, lng1, lat2, lng2);
                        if (km <= 450d) {
                            ccmdevicelists.add(device);
                        }

                    }
                }
            }

        }
        JsonConfig config2 = new JsonConfig();
        config2.setExcludes(new String[]{"createBy", "updateBy", "currentUser", "createDate", "updateDate", "remarks",
                "code", "mask", "gateway", "proxy", "sdkPort", "channelNum", "parent", "typeId", "typeVidicon", "area",
                "companyId", "version", "description", "parentIds", "checkUser", "parentId",
                "dbName", "delFlag", "global", "isNewRecord", "page", "sqlMap"});
        config2.setIgnoreDefaultExcludes(false);  //设置默认忽略
        config2.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        geoJSONIncidentCcmDevice = JSONArray.fromObject(ccmdevicelists, config2).toString(); //Json


        model.addAttribute("areaPointIncident", areaPointIncident);
        model.addAttribute("ccmEventIncidentAll", ccmEventIncident);
        model.addAttribute("type", type);
        model.addAttribute("geoJSONIncidentBuildmanage", geoJSONIncidentBuildmanage);
        model.addAttribute("geoJSONIncidentCcmDevice", geoJSONIncidentCcmDevice);
        return "modules/sys/map/mapIndex";
    }


    // Jump
    @RequestMapping(value = "statJump", method = RequestMethod.GET)
    public String statJump(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        if ("hangzhoudaojie".equals(sysConfig.getObjId())) {//杭州道街
            //return "modules/sys/index/statStreetBackups";
            return "ccm/house/projectIndex";
        }
        /*else if("xinmishi".equals(sysConfig.getObjId())){//新密市
			return "modules/sys/index/statIndexXinmi";
		}*/
        else {
            //return "modules/sys/index/statDistrictBackups";
            return "ccm/house/projectIndex";
        }

    }

    // Jump大屏新密
    @RequestMapping(value = "statJumpXinmi", method = RequestMethod.GET)
    public String statJumpXinmi(Model model) {
        return "modules/sys/index/statIndexXinmi";
    }

    //返回项目index页面   ljd
    @RequestMapping(value = "projectIndex", method = RequestMethod.GET)
    public String projectIndex(Model model) {
        return "ccm/house/projectIndex";
    }


    // 返回社区概况统计或者区大屏
    @RequestMapping(value = "statIndexCom", method = RequestMethod.GET)
    public String statIndexCom(Model model, String flag) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;

        if ("hangzhoudaojie".equals(sysConfig.getObjId())) {//杭州道街
            return "modules/sys/index/statIndex";
        } else if ("xinmishi".equals(sysConfig.getObjId())) {//新密市
            return "modules/sys/index/statIndexXinmi";
        } else {
            return "modules/sys/index/statIndexCom";
        }

    }

    // 返回人口统计
    @RequestMapping(value = "statPop", method = RequestMethod.GET)
    public String statPop(Model model) {
        //系统级别
        SysConfig sysConfig = SysConfigInit.SYS_LEVEL_CONFIG;

        if ("xinmishi".equals(sysConfig.getObjId())) {//新密市人口
            return "modules/sys/index/statIndexXinmi";
        } else {
            return "modules/sys/index/statPopStatistics";
        }

    }

    // 返回事件统计
    @RequestMapping(value = "statIncidentStatistics", method = RequestMethod.GET)
    public String statIncidentStatistics(Model model) {
        //return "modules/sys/index/statSituationStatistics";
        return "modules/sys/index/statIncidentStatistics";
    }


    // 返回安全生产统计--曲梁
    @RequestMapping(value = "statNpseSafeQuLiang", method = RequestMethod.GET)
    public String statNpseSafeQuLiang(Model model) {
        return "modules/sys/index/statNpseSafeQuLiang";
    }


    // 返回安全生产统计
    @RequestMapping(value = "statNpseSafe", method = RequestMethod.GET)
    public String statNpseSafe(Model model) {

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        return "modules/sys/index/statNpseSafe";
    }


    // 返回民计民生统计--曲梁
    @RequestMapping(value = "statLivelihoodQuLiang", method = RequestMethod.GET)
    public String statLivelihoodQuLiang(Model model) {
        return "modules/sys/index/statLivelihoodQuLiang";
    }

    // 返回新可视化模块首页
    @RequestMapping(value = "statIndexCool", method = RequestMethod.GET)
    public String statIndexCool(Model model) {
        return "modules/sys/index/statIndexCool";
    }

    // 返回新密宗教可视化界面
    @RequestMapping(value = "statIndexForZj", method = RequestMethod.GET)
    public String statIndexForZj() {
        return "modules/sys/index/statIndexZj";
    }

    // 返回民计民生统计
    @RequestMapping(value = "statLivelihood", method = RequestMethod.GET)
    public String statLivelihood(Model model) {

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        return "modules/sys/index/statLivelihood";
    }


    // 返回平安建设统计--曲梁
    @RequestMapping(value = "statPublicSecurityQuLiang", method = RequestMethod.GET)
    public String statPublicSecurityQuLiang(Model model) {
        return "modules/sys/index/statPublicSecurityQuLiang";
    }


    // 返回平安建设统计
    @RequestMapping(value = "statPublicSecurity", method = RequestMethod.GET)
    public String statPublicSecurity(Model model) {

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        return "modules/sys/index/statPublicSecurity";
    }

    // 返回城市管理统计--曲梁
    @RequestMapping(value = "statCityManageQuLiang", method = RequestMethod.GET)
    public String statCityManageQuLiang(Model model) {
        return "modules/sys/index/statCityManageQuLiang";
    }

    // 返回城市管理统计
    @RequestMapping(value = "statCityManage", method = RequestMethod.GET)
    public String statCityManage(Model model) {

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        return "modules/sys/index/statCityManage";
    }


    // 返回经济建设统计--曲梁
    @RequestMapping(value = "statConstructionEconomicsQuLiang", method = RequestMethod.GET)
    public String statConstructionEconomicsQuLiang(Model model) {
        return "modules/sys/index/statConstructionEconomicsQuLiang";
    }


    // 返回经济建设统计
    @RequestMapping(value = "statConstructionEconomics", method = RequestMethod.GET)
    public String statConstructionEconomics(Model model) {

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        return "modules/sys/index/statConstructionEconomics";
    }

    // 返回智慧党建系统
    @RequestMapping(value = "statParty", method = RequestMethod.GET)
    public String statParty(Model model) {

        //登录用户信息
        User user = UserUtils.getUser();
        model.addAttribute("user", user);

        //系统链接
        SysConfig sysConfigLink1 = sysConfigService.get("dz_hangzhoudao_link_video");
        SysConfig sysConfigLink2 = sysConfigService.get("dz_hangzhoudao_link_pbs");
        String link1 = "";
        String link2 = "";
        if (sysConfigLink1 != null) {
            link1 = sysConfigLink1.getParamStr();
        }
        if (sysConfigLink2 != null) {
            link2 = sysConfigLink2.getParamStr();
        }
        model.addAttribute("dz_hangzhoudao_link_video", link1);
        model.addAttribute("dz_hangzhoudao_link_pbs", link2);

        return "modules/sys/index/statParty";
    }

    // 返回管理信息系统-区大屏
    @RequestMapping(value = "statIndex", method = RequestMethod.GET)
    public String statIndex(Model model) {
        return "modules/sys/index/statIndex";
    }

    // 返回网格管理信息-大屏
    @RequestMapping(value = "gridManagement", method = RequestMethod.GET)
    public String gridManagement(Model model) {
        // 获取网格地区的数量
        CcmOrgArea ccmOrgArea = new CcmOrgArea();
        ccmOrgArea.setType("7");
        Integer count = ccmOrgAreaService.findList(ccmOrgArea).size();
        model.addAttribute("count", count);
        return "modules/sys/index/gridManagement";
    }

    // 返回脱贫攻坚页面信息-大屏
    @RequestMapping(value = "shakeOffPoverty", method = RequestMethod.GET)
    public String shakeOffPoverty(Model model) {
        return "modules/sys/index/shakeOffPoverty";
    }

    // 返回党建架构页面信息-大屏
    @RequestMapping(value = "partyConstruction", method = RequestMethod.GET)
    public String partyConstruction(Model model) {
        return "modules/sys/index/partyConstruction";
    }

    // 区大屏-跳转页面
    @RequestMapping(value = "indexJump", method = RequestMethod.GET)
    public String indexJump(Model model, String id, String url) {
        model.addAttribute("id", id);
        model.addAttribute("url", url);
        return "modules/sys/sysIndex";
    }

    /**
     * @param ccmHouseBuildmanage
     * @param model
     * @return
     * @see 查看流动迁移的热力图
     */
    //@RequiresPermissions("house:ccmHouseBuildmanage:view")
    @ResponseBody
    @RequestMapping(value = "buildHeatMap")
    public GeoJSON buildHeatMap(CcmHouseBuildmanage ccmHouseBuildmanage, String type, Model model) {


        //type查询重点人员类型
        SearchTabMore searchTabMore = new SearchTabMore();
        if (type != null && !"".equals(type)) {
            searchTabMore = CommUtil.ReturnPeoTypeMoreEvery(type);
        }

        Area userArea = UserUtils.getUser().getOffice().getArea();
        searchTabMore.setUserArea(userArea);
        // 查询房屋地图信息
        List<CcmHouseBuildmanage> ccmHouseBuildmanages = ccmHouseBuildmanageService.getBuildByImmigration(searchTabMore);
        // 返回对象
        GeoJSON geoJSON = new GeoJSON();
        List<Features> featureList = new ArrayList<Features>();
        // 数组
        for (CcmHouseBuildmanage Buildmanage : ccmHouseBuildmanages) {
            // 特征属性
            Features featureDto = new Features();
            Properties properties = new Properties();
            // 1 type 默认不填
            // 2 id 添加
            featureDto.setId(Buildmanage.getId());
            // 3 properties 展示属性信息
            // properties.setName(Buildmanage.getName());
            properties.setName(Buildmanage.getBuildname());
            properties.setIcon(Buildmanage.getImage());
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建附属信息
            map.put("id", Buildmanage.getId());
            // map.put("楼栋名称", Buildmanage.getBuildname());
            map.put("count", Buildmanage.getMore1());
            properties.addInfo(map);
            featureList.add(featureDto);
            featureDto.setProperties(properties);
            // 4 geometry 配置参数
            Geometry geometry = new Geometry();
            featureDto.setGeometry(geometry);
            // 点位信息 测试为面
            geometry.setType("Point");
            if (!StringUtils.isEmpty(Buildmanage.getAreaPoint())) {
                // 获取中心点的值
                String[] centpoint = Buildmanage.getAreaPoint().split(",");
                geoJSON.setCentpoint(centpoint);
                // 添加图形中心点位信息
                properties.setCoordinateCentre(centpoint);
            }
            // 添加具体数据
            // 封装的list
            List<String> Coordinateslist = new ArrayList<>();
            // 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
            String[] a = (StringUtils.isEmpty(Buildmanage.getAreaPoint()) ? (",") : Buildmanage.getAreaPoint())
                    .split(",");
            // 填充数据
            if (a.length < 2) {
                Coordinateslist.add("");
                Coordinateslist.add("");
            } else {
                Coordinateslist.add(a[0]);
                Coordinateslist.add(a[1]);
            }
            // 装配点位
            geometry.setCoordinates(Coordinateslist);
        }
        geoJSON.setFeatures(featureList);
        // 如果无数据
        if (featureList.size() == 0) {
            return null;
        }
        return geoJSON;
    }


    /**
     * @param CcmOrgNpse
     * @param model
     * @return
     * @see 生成指定的信息的 非公有制所属行业模糊查询	  1、加油站	  2、商场超市	  3、娱乐场所  	4、酒店宾馆		5、涉危涉爆
     */
    @ResponseBody
    @RequestMapping(value = "findMapIndustry")
    public GeoJSON findMapIndustry(@RequestParam(required = false) String type, String flag, Model model) {
        CcmOrgNpse ccmOrgNpseDto = new CcmOrgNpse();
        // 生成 非公有制所属行业的 list
        List<CcmOrgNpse> ccmOrgNpseList = new ArrayList<>();
        if (!StringUtils.isEmpty(type)) {
            if ("1".equals(type)) {
                String[] industryList = {"加油站", "石油", "石化", "燃料"};
                ccmOrgNpseDto.setIndustryList(industryList);
                ccmOrgNpseList = ccmOrgNpseService.findList(ccmOrgNpseDto);
            }
            if ("2".equals(type)) {
                String[] industryList = {"超市", "商店", "小卖部", "商场", "购物", "商厦", "百货", "零售", "批发"};
                ccmOrgNpseDto.setIndustryList(industryList);
                ccmOrgNpseList = ccmOrgNpseService.findList(ccmOrgNpseDto);
            }
            if ("3".equals(type)) {
                String[] industryList = {"电影院", "电子综艺", "娱乐", "互联网", "量贩式", "唱歌", "上网", "台球", "棋牌", "网吧", "洗浴", "足疗", "游泳", "KTV", "ktv", "足浴", "酒吧", "夜店", "网咖", "电玩"};
                ccmOrgNpseDto.setIndustryList(industryList);
                ccmOrgNpseList = ccmOrgNpseService.findList(ccmOrgNpseDto);
            }
            if ("4".equals(type)) {
                String[] industryList = {"酒店", "宾馆", "旅馆", "住宿", "饭店", "民宿", "露营"};
                ccmOrgNpseDto.setIndustryList(industryList);
                ccmOrgNpseList = ccmOrgNpseService.findList(ccmOrgNpseDto);
            }
            if ("5".equals(type)) {
                ccmOrgNpseDto.setDangComp(1);
                ccmOrgNpseList = ccmOrgNpseService.findList(ccmOrgNpseDto);
            }
        }

        // 返回对象
        GeoJSON geoJSON = new GeoJSON();
        List<Features> featureList = new ArrayList<Features>();
        // 重点场所
        for (CcmOrgNpse ccmorgnpse : ccmOrgNpseList) {
            // 特征属性
            Features featureDto = new Features();
            Properties properties = new Properties();
            System.out.println(featureDto.getId());
            // 1 type 默认不填
            // 2 id 添加
            featureDto.setId(ccmorgnpse.getId());
            // 3 properties 展示属性信息
            properties.setName(ccmorgnpse.getCompName());
            properties.setIcon(ccmorgnpse.getIcon());
            Map<String, Object> map = new HashMap<String, Object>();
            // 创建附属信息
            map.put("工商执照注册号", ccmorgnpse.getCompId());
            map.put("企业地址", ccmorgnpse.getCompAdd());
            map.put("企业员工数", ccmorgnpse.getCompanyNum() + "");
            map.put("企业重点类型", DictUtils.getDictLabel(ccmorgnpse.getCompImpoType(), "comp_impo_type", ""));
            String name = "";
            if (ccmorgnpse.getArea() != null && ccmorgnpse.getArea().getId() != null && !"".equals(ccmorgnpse.getArea().getId())) {
                name = ccmorgnpse.getArea().getName();
            }
            map.put("所属区域", name);
            properties.addInfo(map);
            featureList.add(featureDto);
            featureDto.setProperties(properties);
            // 4 geometry 配置参数
            Geometry geometry = new Geometry();
            featureDto.setGeometry(geometry);
            // 点位信息 测试为面
            geometry.setType("Polygon");
            // 添加具体数据
            if (!StringUtils.isEmpty(ccmorgnpse.getAreaPoint())) {
                // 获取中心点的值
                String[] centpoint = ccmorgnpse.getAreaPoint().split(",");
                // 图层中心点
                geoJSON.setCentpoint(centpoint);
                // 图形中心点
                properties.setCoordinateCentre(centpoint);
            }
            // 封装的list 以有孔与无孔进行添加
            List<Object> CoordinateslistR = new ArrayList<>();
            // 以下是无孔面积数组
            String[] coordinates = (StringUtils.isEmpty(ccmorgnpse.getAreaMap()) ? ";" : ccmorgnpse.getAreaMap())
                    .split(";");
            // 返回无孔结果 2层数据一个数据源
            List<String[]> Coordinateslist = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates.length > 1) {
                    String corrdinate = coordinates[i];
                    // 以“，”为分割数据
                    String[] a = corrdinate.split(",");
                    Coordinateslist.add(a);
                } else {
                    // 补充一个空的数据源
                    String[] a = {"", ""};
                    Coordinateslist.add(a);
                }
            }
            // 根据格式要求 两层list
            CoordinateslistR.add(Coordinateslist);
            // 获取最后的结果
            geometry.setCoordinates(CoordinateslistR);
        }
        geoJSON.setFeatures(featureList);
        // 如果无数据
        if (featureList.size() == 0) {
            return null;
        }
        System.out.println("----------------------退出了findMapIndustry-------------");
        return geoJSON;
    }

    /**
     * @param VCcmOrg
     * @param model
     * @return
     * @see 生成警务室/工作站地图信息/工作人员信息-区域图
     */
    @ResponseBody
    @RequestMapping(value = "findMapVCcmOrgType")
    public GeoJSON findMapVCcmOrgType(@RequestParam(required = false) String type, String flag, Model model, @RequestParam(required = false) String ids) {
        VCcmOrg vCcmOrg = new VCcmOrg();

        if (StringUtils.isNotEmpty(ids)) {
            vCcmOrg.setSql("a.id in(" + ids + ")");
        }

        // 生成 非公有制所属行业的 list
        List<VCcmOrg> vCcmOrgList = new ArrayList<>();
        if (!StringUtils.isEmpty(type)) {
            if ("3".equals(type)) {
                vCcmOrg.setType(type);
                vCcmOrgList = vCcmOrgService.findList(vCcmOrg);
            }
            if ("4".equals(type)) {
                vCcmOrg.setType(type);
                vCcmOrgList = vCcmOrgService.findList(vCcmOrg);
            }
            if ("sql".equals(type)) {
                vCcmOrg.setType("");
                vCcmOrgList = vCcmOrgService.findList(vCcmOrg);
            }
        }

        // 返回对象
        GeoJSON geoJSON = new GeoJSON();
        List<Features> featureList = new ArrayList<Features>();

        // 公共机构
        for (VCcmOrg vcc : vCcmOrgList) {
            // 特征属性
            Features featureDto = new Features();
            Properties properties = new Properties();
            // 1 type 默认不填
            // 2 id 添加
            featureDto.setId(vcc.getId());
            // 3 properties 展示属性信息
            properties.setName(vcc.getName());
            properties.setIcon(vcc.getIcon());
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            // 创建附属信息
            map.put("id", vcc.getId());
            //map.put("机构名称", vcc.getName());
            map.put("机构类型", DictUtils.getDictLabel(vcc.getType(), "sys_office_type", ""));
            //map.put("机构联系电话", vcc.getPhone());
            map.put("负责人姓名", vcc.getMaster());
            map.put("负责人电话", vcc.getPhone());
            map.put("所属区域", vcc.getArea().toString() + "");
            //公共机构人员
            VCcmTeam vCcmTeam = new VCcmTeam();
            Office office = new Office();
            office.setId(vcc.getId());
            vCcmTeam.setOffice(office);
            List<VCcmTeam> vCcmTeamList = null;
            if ("xinmishi".equals(SysConfigInit.SYS_LEVEL_CONFIG.getObjId())) {
                vCcmTeamList = vCcmTeamService.findByOfficeParentIdsLike(office);
            } else {
                vCcmTeamList = vCcmTeamService.findList(vCcmTeam);
            }
            if (vCcmTeamList.size() > 0) {
                JsonConfig config = new JsonConfig();
                config.setExcludes(new String[]{"createBy", "updateBy", "createDate", "updateDate", "delFlag", "page",
                        "companyId", "office", "loginName", "password", "email", "mobile", "userType", "loginIp",
                        "loginDate", "loginFlag", "user", "nation", "politics", "birthday", "grade", "service",
                        "profExpertise", "profExpertises", "education", "teamType", "more1", "more2", "status", "groupId",
                        "ccmMobileDevice", "currentUser", "dbName", "global", "isNewRecord", "parent", "parentId", "parentIds",
                        "profExpertiseList", "sort", "sqlMap"});
                config.setIgnoreDefaultExcludes(false);  //设置默认忽略
                config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
                String listVCcmTeamList = JSONArray.fromObject(vCcmTeamList, config).toString(); //
                map.put("公共机构人员", listVCcmTeamList);
            } else {
                map.put("公共机构人员", "[{\"fixTel\":\"\",\"id\":\"\",\"idenNum\":\"\",\"name\":\"\",\"no\":\"\",\"phone\":\"\",\"photo\":\"\",\"remarks\":\"\",\"sex\":\"\"}]");
            }
            properties.addInfo(map);
            featureList.add(featureDto);
            featureDto.setProperties(properties);
            // 4 geometry 配置参数
            Geometry geometry = new Geometry();
            featureDto.setGeometry(geometry);
            // 点位信息 测试为面
            geometry.setType("Polygon");
            // 添加具体数据
            if (!StringUtils.isEmpty(vcc.getAreaPoint())) {
                // 获取中心点的值
                String[] centpoint = vcc.getAreaPoint().split(",");
                // 图层中心点
                geoJSON.setCentpoint(centpoint);
                // 图形中心点
                properties.setCoordinateCentre(centpoint);
            }
            // 封装的list 以有孔与无孔进行添加
            List<Object> CoordinateslistR = new ArrayList<>();
            // 以下是无孔面积数组
            String[] coordinates = (StringUtils.isEmpty(vcc.getAreaMap()) ? ";"
                    : vcc.getAreaMap()).split(";");
            // 返回无孔结果 2层数据一个数据源
            List<String[]> Coordinateslist = new ArrayList<>();
            for (int i = 0; i < coordinates.length; i++) {
                if (coordinates.length > 1) {
                    String corrdinate = coordinates[i];
                    // 以“，”为分割数据
                    String[] a = corrdinate.split(",");
                    Coordinateslist.add(a);
                } else {
                    // 补充一个空的数据源
                    String[] a = {"", ""};
                    Coordinateslist.add(a);
                }
            }
            // 根据格式要求 两层list
            CoordinateslistR.add(Coordinateslist);
            // 获取最后的结果
            geometry.setCoordinates(CoordinateslistR);
        }
        geoJSON.setFeatures(featureList);
        // 如果无数据
        if (featureList.size() == 0) {
            return null;
        }
        return geoJSON;

    }

    /**
     * zhanghao
     * @param 框选信息显示
     * @return
     */
/*	@RequestMapping(value="x")
	public String showSelect(List<Point> points,HttpServletRequest request) {
		//判断范围内点工具
		MapUtil mapUtil=new MapUtil();
		//查出楼栋集合
		CcmHouseBuildmanage ccmHouseBuildmanage1=new CcmHouseBuildmanage();
		List<CcmHouseBuildmanage> ccmHouseBuildmanageList=ccmHouseBuildmanageService.findList(ccmHouseBuildmanage1);
		//查出人集合
		CcmPeople ccmPeople1=new CcmPeople();
		List<CcmPeople> peopleList=ccmPeopleService.findList(ccmPeople1);
		//区域内楼栋坐标集合
		List<String> coordianteList=new ArrayList<String>();
		//区域内人集合
		List<CcmPeople> peopleInList=new ArrayList<CcmPeople>();
		//初始化区域人口总数
		int sumPeople=0;
		//获得区域内楼栋坐标
		int overseas=0;//境外
		int aids=0;//艾滋
		int drugs=0;//吸毒
		int release=0;//刑释解教
		int visit=0;//上访
		int psychogeny=0;//精神病
		int dangerous=0;//危险品从业人员
		int rectification=0;//社区矫正 
		int heresy=0;//涉教
		for (CcmHouseBuildmanage ccmHouseBuildmanage2 : ccmHouseBuildmanageList) {
			String[] coordiante1=ccmHouseBuildmanage2.getAreaPoint().split(",");
			double x=Double.parseDouble(coordiante1[0]);
			double y=Double.parseDouble(coordiante1[1]);
			Point point1= new Point(x, y);
			boolean result=mapUtil.isPointInPolygon(point1, points);
			if (result) {
				coordianteList.add(ccmHouseBuildmanage2.getAreaPoint());
				//计算区域人口总数
				sumPeople+=ccmHouseBuildmanage2.getBuildPeo();
				
			}
		}
		//区域人口总数
		System.out.println("区域人口总数is________________"+sumPeople);
		//各种统计
		for (CcmPeople people : peopleList) {
			CcmPopTenant house = ccmPopTenantService.get(people.getRoomId());
			if(house!=null){
				CcmHouseBuildmanage build = ccmHouseBuildmanageService.get(house.getBuildingId());
				String peoplePoint = build.getAreaPoint();
				for (String areaPoint : coordianteList) {
					if (areaPoint.equals(peoplePoint)) {
						if ("30".equals(people.getType())) {
							overseas++;
						}else if (people.getIsRelease()==1) {
							release++;
						}else if (people.getIsRectification()==1) {
							rectification++;
						}else if (people.getIsPsychogeny()==1) {
							psychogeny++;
						}else if (people.getIsDrugs()==1) {
							drugs++;
						}else if (people.getIsVisit()==1) {
							visit++;
						}else if (people.getIsDangerous()==1) {
							dangerous++;
						}else if (people.getIsHeresy()==1) {
							heresy++;
						}else if (people.getIsAids()==1) {
							aids++;
						}
						peopleInList.add(people);
					}
				}
			}
		}
		Map<String,Integer> peopleInArea=new HashMap<String,Integer>();
		peopleInArea.put("sumPeople", sumPeople);
		peopleInArea.put("overseas", overseas);
		peopleInArea.put("release", release);
		peopleInArea.put("rectification", rectification);
		peopleInArea.put("psychogeny", psychogeny);
		peopleInArea.put("drugs", drugs);
		peopleInArea.put("visit", visit);
		peopleInArea.put("dangerous", dangerous);
		peopleInArea.put("heresy", heresy);
		peopleInArea.put("aids", aids);
		request.setAttribute("information", peopleInArea);
		return "modules/sys/index/";
	}*/


    /**
     * zhanghao
     *
     * @param 框选信息显示
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "showSelect")
    public Map<String, Integer> showSelect(String points1, HttpServletRequest request, DataList dataList) {
        boolean isPolygon = null != points1 && !"".equals(points1);
        List<Point> points = new ArrayList<Point>();
        if (isPolygon) {//框选或多边形选择
            String[] s1 = points1.split(";");
            for (int i = 0; i < s1.length; i++) {
                String[] s2 = s1[i].split(",");
                double x = Double.parseDouble(s2[0]);
                double y = Double.parseDouble(s2[1]);
                Point point = new Point(x, y);
                points.add(point);
            }
        }
        //查出all楼栋集合
        CcmHouseBuildmanage ccmHouseBuildmanage1 = new CcmHouseBuildmanage();
        List<CcmHouseBuildmanage> ccmHouseBuildmanageList = ccmHouseBuildmanageService.findList(ccmHouseBuildmanage1);
        //allSchool
        CcmHouseSchoolrim ccmHouseSchoolrim1 = new CcmHouseSchoolrim();
        List<CcmHouseSchoolrim> ccmHouseSchoolrimList = ccmHouseSchoolrimService.findList(ccmHouseSchoolrim1);
        //allPoliceRoom
        CcmOrgComprehensive ccmOrgComprehensive1 = new CcmOrgComprehensive();
        List<CcmOrgComprehensive> ccmOrgComprehensiveList = ccmOrgComprehensiveService.findList(ccmOrgComprehensive1);
        //allPolice
        CcmMobileDevice ccmMobileDevice1 = new CcmMobileDevice();
        List<CcmMobileDevice> ccmMobileDeviceList = ccmMobileDeviceService.findList(ccmMobileDevice1);

        //区域内楼栋Id集合
        List<String> coordianteList = new ArrayList<String>();
        //初始化区域人口总数
        int sumPeople = 0;
        //初始化区域特殊人口数
        int pNum = 0;
        for (CcmHouseBuildmanage ccmHouseBuildmanage2 : ccmHouseBuildmanageList) {
            String coordiante1 = ccmHouseBuildmanage2.getAreaPoint();
            if (coordiante1 != null && !"".equals(coordiante1)) {
                boolean result = false;
                if (isPolygon) {
                    Point sPoint = CommUtil.toPoint(coordiante1);
                    result = MapUtil.isPointInPolygon(sPoint, points);
                } else {//圈选
                    String[] p1 = coordiante1.split(",");
                    double x = 0;
                    double y = 0;
                    if (!p1[0].isEmpty()) {
                        x = Double.parseDouble(p1[0]);
                    }
                    if (!p1[1].isEmpty()) {
                        y = Double.parseDouble(p1[1]);
                    }
                    result = Tool.isInCircle(x, y, Double.parseDouble(dataList.getX()),
                            Double.parseDouble(dataList.getY()), dataList.getRadius());
                }
                if (result) {
                    coordianteList.add(ccmHouseBuildmanage2.getId());
                    //计算区域人口总数
                    sumPeople += (ccmHouseBuildmanage2.getBuildPeo() == null ? 0 : ccmHouseBuildmanage2.getBuildPeo());
                }
            }
        }
        //people
        if (coordianteList.size() > 0 && coordianteList != null) {
            pNum = ccmPeopleService.countSelect(coordianteList);
        }
        //school
        int sumSchool = 0;
        for (CcmHouseSchoolrim ccmHouseSchoolrim2 : ccmHouseSchoolrimList) {
            String coordiante1 = ccmHouseSchoolrim2.getAreaPoint();
            //String[] coordiante1=ccmHouseSchoolrim12.getAreaPoint().split(",");
            //double x=Double.parseDouble(coordiante1[0]);
            //double y=Double.parseDouble(coordiante1[1]);
            //Point sPoint= new Point(x, y);
            if (coordiante1 != null && !"".equals(coordiante1)) {
                boolean result = false;
                if (isPolygon) {
                    Point sPoint = CommUtil.toPoint(coordiante1);
                    result = MapUtil.isPointInPolygon(sPoint, points);
                } else {
                    String[] p1 = coordiante1.split(",");
                    double x = 0;
                    double y = 0;
                    if (!p1[0].isEmpty()) {
                        x = Double.parseDouble(p1[0]);
                    }
                    if (!p1[1].isEmpty()) {
                        y = Double.parseDouble(p1[1]);
                    }
                    result = Tool.isInCircle(x, y, Double.parseDouble(dataList.getX()),
                            Double.parseDouble(dataList.getY()), dataList.getRadius());
                }
                if (result) {
                    //计算区域school总数
                    sumSchool++;
                }
            }

        }
        //policeRoom
        int sumPoliceRoom = 0;
        for (CcmOrgComprehensive CcmOrgComprehensive2 : ccmOrgComprehensiveList) {
            String coordiante1 = CcmOrgComprehensive2.getAreaPoint();
            if (coordiante1 != null && !"".equals(coordiante1)) {
                boolean result = false;
                if (isPolygon) {
                    Point sPoint = CommUtil.toPoint(coordiante1);
                    result = MapUtil.isPointInPolygon(sPoint, points);
                } else {
                    String[] p1 = coordiante1.split(",");
                    double x = 0;
                    double y = 0;
                    if (!p1[0].isEmpty()) {
                        x = Double.parseDouble(p1[0]);
                    }
                    if (!p1[1].isEmpty()) {
                        y = Double.parseDouble(p1[1]);
                    }
                    result = Tool.isInCircle(x, y, Double.parseDouble(dataList.getX()),
                            Double.parseDouble(dataList.getY()), dataList.getRadius());
                }
                if (result) {
                    //计算区域school总数
                    sumPoliceRoom++;
                }
            }
        }

        //police
        int sumPolice = 0;
        for (CcmMobileDevice CcmMobileDevice2 : ccmMobileDeviceList) {
            String coordiante1 = CcmMobileDevice2.getAreaPoint();
            if (coordiante1 != null && !"".equals(coordiante1)) {
                boolean result = false;
                if (isPolygon) {
                    Point sPoint = CommUtil.toPoint(coordiante1);
                    result = MapUtil.isPointInPolygon(sPoint, points);
                } else {
                    String[] p1 = coordiante1.split(",");
                    double x = 0;
                    double y = 0;
                    if (!p1[0].isEmpty()) {
                        x = Double.parseDouble(p1[0]);
                    }
                    if (!p1[1].isEmpty()) {
                        y = Double.parseDouble(p1[1]);
                    }
                    result = Tool.isInCircle(x, y, Double.parseDouble(dataList.getX()),
                            Double.parseDouble(dataList.getY()), dataList.getRadius());
                }
                if (result) {
                    //计算区域school总数
                    sumPolice++;
                }
            }
        }
        Map<String, Integer> inArea = new HashMap<String, Integer>();
        inArea.put("sumPeople", sumPeople);
        inArea.put("pNum", pNum);
        inArea.put("sumSchool", sumSchool);
        inArea.put("sumPoliceRoom", sumPoliceRoom);
        inArea.put("sumPolice", sumPolice);
        System.out.println(inArea.size());
        //request.setAttribute("s_information", inArea);
        return inArea;
    }


}