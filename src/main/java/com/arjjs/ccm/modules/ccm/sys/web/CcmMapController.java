package com.arjjs.ccm.modules.ccm.sys.web;

import com.arjjs.ccm.common.gis.MapUtil;
import com.arjjs.ccm.common.gis.Point;
import com.arjjs.ccm.common.persistence.Page;
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
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventCasedeal;
import com.arjjs.ccm.modules.ccm.event.entity.CcmEventIncident;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventCasedealService;
import com.arjjs.ccm.modules.ccm.event.service.CcmEventIncidentService;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseBuildmanage;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseSchoolrim;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseBuildmanageService;
import com.arjjs.ccm.modules.ccm.house.service.CcmHouseSchoolrimService;
import com.arjjs.ccm.modules.ccm.org.entity.*;
import com.arjjs.ccm.modules.ccm.org.service.*;
import com.arjjs.ccm.modules.ccm.patrol.entity.CcmTracingpoint;
import com.arjjs.ccm.modules.ccm.patrol.service.CcmPatrolPointService;
import com.arjjs.ccm.modules.ccm.patrol.service.CcmTracingpointService;
import com.arjjs.ccm.modules.ccm.place.entity.CcmBasePlace;
import com.arjjs.ccm.modules.ccm.place.service.CcmBasePlaceService;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmAreaPoint;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPeople;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmPopTenant;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPeopleService;
import com.arjjs.ccm.modules.ccm.pop.service.CcmPopTenantService;
import com.arjjs.ccm.modules.ccm.religion.entity.CcmPlaceReligion;
import com.arjjs.ccm.modules.ccm.religion.service.CcmPlaceReligionService;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestResult;
import com.arjjs.ccm.modules.ccm.rest.entity.CcmRestType;
import com.arjjs.ccm.modules.ccm.sys.entity.SysConfig;
import com.arjjs.ccm.modules.ccm.sys.entity.SysDicts;
import com.arjjs.ccm.modules.ccm.sys.service.CcmMapService;
import com.arjjs.ccm.modules.ccm.sys.service.SysConfigService;
import com.arjjs.ccm.modules.ccm.sys.service.SysDictsService;
import com.arjjs.ccm.modules.flat.realtimeSituation.entity.DataList;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.service.AreaService;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.CommUtil;
import com.arjjs.ccm.tool.EchartType;
import com.arjjs.ccm.tool.Tool;
import com.arjjs.ccm.tool.geoJson.Features;
import com.arjjs.ccm.tool.geoJson.GeoJSON;
import com.arjjs.ccm.tool.geoJson.Geometry;
import com.arjjs.ccm.tool.geoJson.Properties;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Controller
 * 
 * @author arj
 * @version 2018-01-20
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/map")
public class CcmMapController extends BaseController {

	// 楼栋管理
	@Autowired
	private CcmHouseBuildmanageService ccmHouseBuildmanageService;
	// 设备管理
	@Autowired
	private CcmDeviceService ccmDeviceService;
	// 广播站
	@Autowired
	private CcmDeviceBroadcastService ccmDeviceBroadcastService;
	// 公共社区管理
	@Autowired
	private CcmOrgCommonalityService ccmOrgCommonalityService;
	// 工作站，警务室视频资源
	@Autowired
	private CcmOrgDeviceService ccmOrgDeviceService;
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
	// 移动设备
	@Autowired
	private CcmMobileDeviceService ccmMobileDeviceService;
	//App轨迹
	@Autowired
	private CcmTracingpointService ccmTracingpointService;
	//轨迹点位
	@Autowired
	private CcmPatrolPointService ccmPatrolPointService;
	//处理状态
	@Autowired
	private CcmEventCasedealService ccmEventCasedealService;
	//场所
	@Autowired
	private CcmBasePlaceService ccmBasePlaceService;

	@Autowired
	private SysConfigService sysConfigService;

	@Autowired
	private SysAreaService sysAreaService;

	@Autowired
	private SysDictsService sysDictsService;

	@Autowired
	private CcmMapService ccmMapService;

	@Autowired
	private AreaService areaService;

	//宗教
	@Autowired
	private CcmPlaceReligionService ccmPlaceReligionService;


	private static SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * @see  /生成视频地图信息点位图
	 * @param ccmDevice
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("ccmsys:ccmLiveVideo:view")
	@ResponseBody
	@RequestMapping(value = "deviceiveMap")
	public GeoJSON deviceiveMap(CcmDevice ccmDevice,@RequestParam(required = false) String ids, Model model) {
		
		if(StringUtils.isNotEmpty(ids)) {
			ccmDevice.setMore1("a.id in("+ids+")");
			}
		
		// 查询地图视频信息
		List<CcmDevice> ccmdevicelist = ccmDeviceService.findList(ccmDevice);
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmDevice device : ccmdevicelist) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(device.getId());
			// 3 properties 展示属性信息
			properties.setName(device.getName());
			properties.setIcon(device.getImagePath());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("设备名称", device.getName());
			map_P.put("设备编号", device.getCode());
			//IP地址表中没数据，去掉
			map_P.put("监控类型", device.getType());
			map_P.put("安装位置", device.getAddress());
			map_P.put("设备状态", device.getStatus());
			map_P.put("监控设备类型", device.getType());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(device.getCoordinate())) {
				// 获取中心点的值
				String[] centpoint = device.getCoordinate().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(device.getCoordinate()) ? (",") : device.getCoordinate()).split(",");
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
			// 5 geometry 配置参数
			// 配置视频信号信息
			Map<String, String> map_V = new HashMap<String, String>();
			// 协议，设备参数信息
			map_V.put("protocol", device.getProtocol());
			map_V.put("param", device.getParam());
			map_V.put("ip", device.getIp());
			map_V.put("port", device.getPort());
			map_V.put("username", device.getAccount());
			map_V.put("password", device.getPassword());
			map_V.put("typeVidicon", device.getTypeVidicon());
			map_V.put("code", device.getCode());
			properties.setVideo(map_V);
		}

		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}
    /**
	 * @see -生成广播站地图信息点位图
	 * @param deviceBroadcast
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "deviceBroadcastMap")
	public GeoJSON deviceBroadcastMap(CcmDeviceBroadcast deviceBroadcast, @RequestParam(required = false) String ids, Model model) {

		if(StringUtils.isNotEmpty(ids)) {
            deviceBroadcast.setMore1("a.id in("+ids+")");
			}

		// 查询地图视频信息
		List<CcmDeviceBroadcast> deviceBroadcastlist = ccmDeviceBroadcastService.findList(deviceBroadcast);
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmDeviceBroadcast broadcast : deviceBroadcastlist) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(broadcast.getId());
			// 3 properties 展示属性信息
			properties.setName(broadcast.getName());
			properties.setIcon("broadcast.png");
			// 点击后展示详细属性值
			Map<String, Object> map_P = new LinkedHashMap<String, Object>();
			// 创建附属信息
			//map_P.put("IP地址", device.getIp());
			map_P.put("广播站名称", broadcast.getName());
			map_P.put("编号", broadcast.getCode());
			map_P.put("位置", broadcast.getAddress());
			//map_P.put("设备状态", device.getStatus());
			map_P.put("下发广播", broadcast.getCode());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(broadcast.getCoordinate())) {
				// 获取中心点的值
				String[] centpoint = broadcast.getCoordinate().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(broadcast.getCoordinate()) ? (",") : broadcast.getCoordinate()).split(",");
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
			// 5 geometry 配置参数
			// 配置广播站信息
			Map<String, String> map_V = new HashMap<String, String>();
			// 编号，设备参数信息
			map_V.put("code", broadcast.getCode());
			map_V.put("param", broadcast.getParam());
			map_V.put("ip", broadcast.getIp());
			map_V.put("port", broadcast.getComPort());
			map_V.put("username", broadcast.getAccount());
			map_V.put("password", broadcast.getPassword());
			//properties.setVideo(map_V);
		}

		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	} /**
	 * @see /生成警务室/工作站地图信息-点位图
	 * @param /deviceBroadcast
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "orgCommonlityMap")
	public GeoJSON orgCommonlityMap(@RequestParam(required = false) String type, @RequestParam(required = false) String ids, Model model) {

		CcmOrgCommonality ccmOrgCommonalityDto = new CcmOrgCommonality();
		if(StringUtils.isNotEmpty(ids)) {
			ccmOrgCommonalityDto.setMore1("a.id in("+ids+")");

		}

		ccmOrgCommonalityDto.setType(type);
		// 查询地图生成公共机构 list
		List<CcmOrgCommonality> ccmOrgCommonalityList = new ArrayList<>();

		if (!StringUtils.isEmpty(type)) {
			ccmOrgCommonalityList = ccmOrgCommonalityService.findCommonalityList(ccmOrgCommonalityDto);
		}
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmOrgCommonality ccmOrgCommonality : ccmOrgCommonalityList) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(ccmOrgCommonality.getId());
			// 3 properties 展示属性信息
			properties.setName(ccmOrgCommonality.getName());
			String icon = type.equals("10") ? "policeroom.png" :"workstation.png";
			properties.setIcon(icon);
			// 创建附属信息
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			// 创建附属信息
			map.put("id", ccmOrgCommonality.getId());
			map.put("机构名称", ccmOrgCommonality.getName());
			map.put("机构类型", DictUtils.getDictLabel(ccmOrgCommonality.getType(), "ccm_org_commonality_type", ""));
			map.put("机构联系电话", ccmOrgCommonality.getOrgTel());
			map.put("负责人姓名", ccmOrgCommonality.getPrincipalName());
			map.put("负责人电话", ccmOrgCommonality.getPrincipalTel());
			map.put("所属区域", ccmOrgCommonality.getArea().toString() + "");

			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(ccmOrgCommonality.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = ccmOrgCommonality.getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(ccmOrgCommonality.getAreaPoint()) ? (",") : ccmOrgCommonality.getAreaPoint()).split(",");
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
			// 5 geometry 配置参数
			// 配置工作站，警务室 视频源信息
			Map<String, String> map_V = new HashMap<String, String>();
			// 编号，设备参数信息封装
			List<CcmDeviceVo> ccmDeviceVos = ccmOrgDeviceService.dealDeviceInfo(ccmOrgCommonality.getId());
			properties.setVideoList(ccmDeviceVos);
		}

		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	
	/**
	 * @see 生成区域地图信息-区域图
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("org:ccmOrgArea:view")
	@ResponseBody
	@RequestMapping(value = "orgAreaMap")
	public GeoJSON orgAreaMap(@RequestParam(required = false) String type, Model model,@RequestParam(required = false) String ids) {
		
		// 创建相关查询对象
		CcmOrgArea ccmorgareaDto1 = new CcmOrgArea();
		CcmOrgArea ccmorgareaDto2 = new CcmOrgArea();
		CcmOrgArea ccmorgareaDto5 = new CcmOrgArea();
		//获得登录用户所在区域
		Area userArea=UserUtils.getUser().getOffice().getArea();
		if(StringUtils.isNotEmpty(ids) &&
				!type.equals("7") && !type.equals("8") && !type.equals("9")) {
			ccmorgareaDto1.setMore1("a.area_id in("+ids+")");
			ccmorgareaDto2.setMore1("a.area_id in("+ids+")");
			ccmorgareaDto5.setMore1("a.area_id in("+ids+")");
			
			
			}

			ccmorgareaDto1.setUserArea(userArea);
			ccmorgareaDto2.setUserArea(userArea);
			ccmorgareaDto5.setUserArea(userArea);
		
		
		// 区域信息
		List<CcmOrgArea> OrgArealist1 = new ArrayList<>();
		// 网格信息
		List<CcmOrgArea> OrgArealist2 = new ArrayList<>();
		// 街道信息
		List<CcmOrgArea> OrgArealist5 = new ArrayList<>();
		// 3-2-1 3种情况。
		if (type.equals("1")) {
			// 注入社区
			ccmorgareaDto1.setType("6");
			OrgArealist1 = ccmOrgAreaService.findList(ccmorgareaDto1);
		} else if (type.equals("2")) {
			// 注入网格
			ccmorgareaDto2.setType("7");
			OrgArealist2 = ccmOrgAreaService.findList(ccmorgareaDto2);
			//修改原因：网格显示改为编码  修改人liujindan 20190218
			/*for (int i = 0; i < OrgArealist2.size(); i++) {
				OrgArealist2.get(i).getArea().setName(OrgArealist2.get(i).getArea().getCode());
			}*/
		} else if (type.equals("4")) {
			// 注入街道
			ccmorgareaDto5.setType("5");
			OrgArealist5 = ccmOrgAreaService.findList(ccmorgareaDto5);
		} else if (type.equals("5")) {
			// 注入街道社区
			ccmorgareaDto1.setType("6");
			OrgArealist1 = ccmOrgAreaService.findList(ccmorgareaDto1);
			ccmorgareaDto5.setType("5");
			OrgArealist5 = ccmOrgAreaService.findList(ccmorgareaDto5);
		}else if (type.equals("6")) {
			// 注入街道网格
			ccmorgareaDto5.setType("5");
			OrgArealist5 = ccmOrgAreaService.findList(ccmorgareaDto5);
			ccmorgareaDto2.setType("7");
			OrgArealist2 = ccmOrgAreaService.findList(ccmorgareaDto2);
		}else if (type.equals("3")) {
			// 注入社区网格
			ccmorgareaDto1.setType("6");
			OrgArealist1 = ccmOrgAreaService.findList(ccmorgareaDto1);
			ccmorgareaDto2.setType("7");
			OrgArealist2 = ccmOrgAreaService.findList(ccmorgareaDto2);
		}else if(type.equals("7")){
			// 查询指定街道 区下的社区 村
			Area area = areaService.get(ids);
			ccmorgareaDto1.setUserArea(area);
			ccmorgareaDto1.setType("6");
			OrgArealist1 = ccmOrgAreaService.findList(ccmorgareaDto1);
		}else if(type.equals("8")){
			// 查询指定社区 村下的网格
			Area area = areaService.get(ids);
			ccmorgareaDto2.setUserArea(area);
			ccmorgareaDto2.setType("7");
			OrgArealist2 = ccmOrgAreaService.findList(ccmorgareaDto2);
		}else if(type.equals("9")){
			// 查询指定网格
			ccmorgareaDto2.setAreaId(ids);
			ccmorgareaDto2.setType("7");
			OrgArealist2 = ccmOrgAreaService.findList(ccmorgareaDto2);
		}else {
			// 3这种情况默认为两者都查询
			ccmorgareaDto1.setType("6");
			ccmorgareaDto2.setType("7");
			ccmorgareaDto5.setType("5");
			OrgArealist1 = ccmOrgAreaService.findList(ccmorgareaDto1);
			OrgArealist2 = ccmOrgAreaService.findList(ccmorgareaDto2);
			OrgArealist5 = ccmOrgAreaService.findList(ccmorgareaDto5);
		}
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();

		// 社区数组
		for (CcmOrgArea orgarea : OrgArealist1) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(orgarea.getId());
			// 3 properties 展示属性信息
			properties.setName(orgarea.getArea() + "");
			properties.setIcon(orgarea.getIcon());
			properties.setType("5"); //层级
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("id1", orgarea.getId());
			map_P.put("管理员姓名", orgarea.getNetManName());
			map_P.put("性别", orgarea.getSex());
			map_P.put("政治面貌", DictUtils.getDictLabel(orgarea.getPolitics(), "sys_ccm_poli_stat", ""));
			map_P.put("手机号码", orgarea.getTelephone());
			map_P.put("人口", orgarea.getMannum() + "");
			map_P.put("重点人员", orgarea.getKeyPersonnelNum() + "");
			map_P.put("工作人员数量", orgarea.getNetPeoNum() + "");
			map_P.put("名称", orgarea.getArea().getName());
			map_P.put("户数", orgarea.getNetNum());
			properties.addInfo(map_P);
			properties.setColor(orgarea.getAreaColor());
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Polygon");
			// 添加中心点位
			if (!StringUtils.isEmpty(orgarea.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = orgarea.getAreaPoint().split(",");
				// 添加图层中心点位信息
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = (StringUtils.isEmpty(orgarea.getAreaMap()) ? ";" : orgarea.getAreaMap()).split(";");
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
					String[] a = { "", "" };
					Coordinateslist.add(a);
				}
			}
			// 根据格式要求 两层list
			CoordinateslistR.add(Coordinateslist);
			// 获取最后的结果
			geometry.setCoordinates(CoordinateslistR);
		}
		// 网格数组
		for (CcmOrgArea orgarea : OrgArealist2) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(orgarea.getId());
			// 3 properties 展示属性信息
			properties.setName(orgarea.getArea() + "");
			properties.setIcon(orgarea.getIcon());
			properties.setType("6");   //层级
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("id1", orgarea.getId());
			map_P.put("管理员姓名", orgarea.getNetManName());
			map_P.put("性别", orgarea.getSex());
			map_P.put("政治面貌", DictUtils.getDictLabel(orgarea.getPolitics(), "sys_ccm_poli_stat", ""));
			map_P.put("手机号码", orgarea.getTelephone());
			map_P.put("人口", orgarea.getMannum() + "");
			map_P.put("重点人员", orgarea.getKeyPersonnelNum() + "");
			map_P.put("工作人员数量", orgarea.getNetPeoNum() + "");
			map_P.put("名称", orgarea.getArea().getName());
			map_P.put("户数", orgarea.getNetNum());
			properties.addInfo(map_P);
			properties.setColor(orgarea.getAreaColor());
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Polygon");
			if (!StringUtils.isEmpty(orgarea.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = orgarea.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = (StringUtils.isEmpty(orgarea.getAreaMap()) ? ";" : orgarea.getAreaMap()).split(";");
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
					String[] a = { "", "" };
					Coordinateslist.add(a);
				}
			}
			// 根据格式要求 两层list
			CoordinateslistR.add(Coordinateslist);
			// 获取最后的结果
			geometry.setCoordinates(CoordinateslistR);

		}
		
		// 街道数组
		for (CcmOrgArea orgarea : OrgArealist5) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(orgarea.getId());
			// 3 properties 展示属性信息
			properties.setName(orgarea.getArea() + "");
			properties.setIcon(orgarea.getIcon());
			properties.setType("4"); //层级
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("id1", orgarea.getId());
			map_P.put("管理员姓名", orgarea.getNetManName());
			map_P.put("性别", orgarea.getSex());
			map_P.put("政治面貌", DictUtils.getDictLabel(orgarea.getPolitics(), "sys_ccm_poli_stat", ""));
			map_P.put("手机号码", orgarea.getTelephone());
			map_P.put("人口", orgarea.getMannum() + "");
			map_P.put("所属层级", "4");
			map_P.put("重点人员", orgarea.getKeyPersonnelNum() + "");
			map_P.put("工作人员数量", orgarea.getNetPeoNum() + "");
			map_P.put("名称", orgarea.getArea().getName());
			map_P.put("户数", orgarea.getNetNum());
			properties.addInfo(map_P);
			properties.setColor(orgarea.getAreaColor());
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Polygon");
			// 添加中心点位
			if (!StringUtils.isEmpty(orgarea.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = orgarea.getAreaPoint().split(",");
				// 添加图层中心点位信息
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = (StringUtils.isEmpty(orgarea.getAreaMap()) ? ";" : orgarea.getAreaMap()).split(";");
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
					String[] a = { "", "" };
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
	 * @see 生成重点场所地图信息-区域图
	 * @param type
	 *            重点类型信息 理论值：1~63
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("org:ccmOrgNpse:view")
	@ResponseBody
	@RequestMapping(value = "ccmOrgNpseMap")
	public GeoJSON ccmOrgNpseMap(@RequestParam(required = false) String type,@RequestParam(required = false) String ids, Model model) {
		// 返回学校类型
		CcmHouseSchoolrim ccmHouseSchoolrimDto = new CcmHouseSchoolrim();
		
		// 1:安全生产重点场所,2:消防生产重点场所 ,3:治安重点场所
		// 4:物流安全重点场所,5:其他重点场所 ,6:学校信息
		List<CcmHouseSchoolrim> Schoolrimlist = new ArrayList<>();
		// 返回数据结果
		String compImpoType = CommUtil.ReturnCompImpoType(type);
		// 当前是否为学校
		if (compImpoType.contains("06")) {
			if(StringUtils.isNotEmpty(ids)) {
				ccmHouseSchoolrimDto.setMore1("a.id in("+ids+")");
				}
			// 06 为学校
			Schoolrimlist = ccmHouseSchoolrimService.findList(ccmHouseSchoolrimDto);
			// 当前06为学校 ，01-05是企业类型重点类型去除该内容。
			compImpoType = compImpoType.replaceAll(",06", "");
			compImpoType = compImpoType.replaceAll("06", "");
		}
		// compImpoType
		CcmOrgNpse ccmOrgNpseDto = new CcmOrgNpse();
		//ccmOrgNpseDto.setCompImpoType(compImpoType);
		  ccmOrgNpseDto.setCompImpoType("");
		// 生成 重点场所 list
		List<CcmOrgNpse> ccmOrgNpseList = new ArrayList<>();
		// 01-05 的企业类型数据 判断当前不为空
		if (!StringUtils.isEmpty(compImpoType)) {
			if(StringUtils.isNotEmpty(ids)) {
				ccmOrgNpseDto.setMore1("a.id in("+ids+")");
				}
			
			ccmOrgNpseList = ccmOrgNpseService.findList(ccmOrgNpseDto);
		}

		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 学校
		for (CcmHouseSchoolrim cSchoolrim : Schoolrimlist) {

			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(cSchoolrim.getId());
			// 3 properties 展示属性信息
			properties.setName(cSchoolrim.getSchoolName());
			properties.setIcon(cSchoolrim.getImage());
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建附属信息
			map.put("学校地址", cSchoolrim.getSchoolAdd());
			map.put("学校所属主管教育行政部门", cSchoolrim.getSchoolEducDepa());
			// map.put("学校办学类型", cSchoolrim.getSchoolType() + "");
			map.put("在校学生人数", cSchoolrim.getSchoolNum() + "");
			map.put("所属区域", cSchoolrim.getArea().toString() + "");
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Polygon");
			if (!StringUtils.isEmpty(cSchoolrim.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = cSchoolrim.getAreaPoint().split(",");
				// 图层中心点
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = cSchoolrim.getAreaMap().split(";");
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
					String[] a = { "", "" };
					Coordinateslist.add(a);
				}
			}
			// 根据格式要求 两层list
			CoordinateslistR.add(Coordinateslist);
			// 获取最后的结果
			geometry.setCoordinates(CoordinateslistR);
		}
		// 重点场所
		for (CcmOrgNpse ccmorgnpse : ccmOrgNpseList) {
			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
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
			if(ccmorgnpse.getArea()!=null && ccmorgnpse.getArea().getId()!=null && !"".equals(ccmorgnpse.getArea().getId())){
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
					String[] a = { "", "" };
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
	 * @see 生成重点人员地图信息 点位图
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "specialPeoMap")
	public GeoJSON specialPeoMap(@RequestParam(required = false) String type, Model model) {
		// Step1 获取所有的租房对象
		List<CcmPopTenant> PopTenantList = ccmPopTenantService.findList(new CcmPopTenant());
		// 递归循环 租房list 获取Map
		Map<String, CcmPopTenant> TenantMap = new HashMap<>();

		// 生成相应的Map 为了后容易获取
		for (CcmPopTenant tenant : PopTenantList) {
			TenantMap.put(tenant.getId(), tenant);
		}
		// Step2 返回人员查询的对象
		CcmPeople cmPeopleDto = CommUtil.ReturnPeoType(type);
		// 如果该数据为空
		if (null == cmPeopleDto) {
			return null;
		}
		// 查询了所有的重点人员,默认必须为有数据
		List<CcmPeople> ccmPeopleList = ccmPeopleService.findSpeList(cmPeopleDto);
		// id,List<People>
		Map<String, Object> RoomMap = CommUtil.GetRoomPeoMap(ccmPeopleList);

		// Step3 创建Geo对象 并返回
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (String RoomEntry : RoomMap.keySet()) {
			// 返回重点人员与租房对象
			CcmPopTenant ccmPopTenant = (CcmPopTenant) TenantMap.get(RoomEntry);
			// 获取List
			List<CcmPeople> ccmPeopleList_R = (List<CcmPeople>) RoomMap.get(RoomEntry);
			if (null == ccmPopTenant) {
				continue;
			} else if (null == ccmPeopleList_R || ccmPeopleList_R.size() == 0) {
				continue;
			}
			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(ccmPopTenant.getId());
			// 3 properties 展示属性信息
			properties.setName(ccmPopTenant.getHousePlace());
			properties.setIcon(ccmPopTenant.getImage());
			Map<String, Object> map = new HashMap<String, Object>();
			// 一条房屋信息 + 多名人员信息
			map.put("房屋信息", ccmPopTenant.getHouseBuild() + ccmPopTenant.getHouseName());
			// 获取人员List
			map.put("重点人员", ccmPeopleList_R);
			// 创建附属信息
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			if (StringUtils.isNotEmpty(ccmPopTenant.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = ccmPopTenant.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(ccmPopTenant.getAreaPoint()) ? (",") : ccmPopTenant.getAreaPoint())
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
	 * @see 生成事件地图信息-区域图
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "eventIncidentMap")
	public GeoJSON eventIncidentMap(Model model) {
        
		// 获取相关的事件查询对象
		CcmEventIncident ccmEventIncidentDto = new CcmEventIncident();
		Area userArea=UserUtils.getUser().getOffice().getArea();
		ccmEventIncidentDto.setUserArea(userArea);
		List<CcmEventIncident> incidentList = ccmEventIncidentService.findList(ccmEventIncidentDto);

		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmEventIncident incident : incidentList) {
			// 如果当前的面积为空 则跳过显示
			if (StringUtils.isBlank(incident.getAreaMap())) {
				continue;
			}
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(incident.getId());
			// 3 properties 展示属性信息
			properties.setName(incident.getCaseName());
			properties.setIcon(incident.getImage());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("案发地详细地址", incident.getHappenPlace());
			map_P.put("案发日期", (incident.getHappenDate() == null) ? "" : time.format(incident.getHappenDate()) + "  ");
			map_P.put("案事件模块分类", incident.getEventKind());
			map_P.put("案事件情况", incident.getCaseCondition());
			map_P.put("主犯姓名", incident.getCulName());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Polygon");
			if (StringUtils.isNotEmpty(incident.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = incident.getAreaPoint().split(",");
				// 图层中心点
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = incident.getAreaMap().split(";");
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
					String[] a = { "", "" };
					Coordinateslist.add(a);
				}
			}
			// 根据格式要求 两层list
			CoordinateslistR.add(Coordinateslist);
			// 获取最后的结果
			geometry.setCoordinates(CoordinateslistR);
		}
		// 添加数据
		geoJSON.setFeatures(featureList);
		return geoJSON;
	}

	/**
	 * @see 重点人员楼栋-区域图 暂时 使用 用作 首页 findBuildListBySpecilPop 写了 一个 查询重点类型
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "buildMapSpecialPop")
	public GeoJSON buildMapSpecialPop(@RequestParam(required = false) String type,
			@RequestParam(required = false) String ids,
			@RequestParam(required = false) String buildname,
			@RequestParam(required = false) String buildPname,
			@RequestParam(required = false) String residencedetail,
			Model model) {
		// CcmPeople cmPeopleDto = new CcmPeople();
		// 返回人员查询的对象
		// CcmPeople cmPeopleDto = CommUtil.ReturnPeoType("127");
		// TODO 查询含有特殊人群的楼栋地图信息
		// List<String> BuildSpeIDs =
		// ccmHouseBuildmanageService.findBuildListBySpecilPop(cmPeopleDto);
		CcmHouseBuildmanage ccmHouseBuildmanage= new CcmHouseBuildmanage();
		if(StringUtils.isNotEmpty(ids)) {
			ccmHouseBuildmanage.setMore1("a.id in("+ids+")");
		}
		if(StringUtils.isNotEmpty(buildname)) {
			ccmHouseBuildmanage.setBuildname(buildname);
		}
		if(StringUtils.isNotEmpty(buildPname)) {
			ccmHouseBuildmanage.setBuildPname(buildPname);
		}
		if(StringUtils.isNotEmpty(residencedetail)) {
			ccmHouseBuildmanage.setResidencedetail(residencedetail);
		}
		List<CcmHouseBuildmanage> ccmHouseBuildmanageList = ccmHouseBuildmanageService
				.findList(ccmHouseBuildmanage);
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
			// map.put("id", Buildmanage.getId());
			map.put("楼栋名称", Buildmanage.getBuildname());
			map.put("所属小区", Buildmanage.getName());
			map.put("楼栋长", Buildmanage.getBuildPname());
			map.put("楼栋长电话", Buildmanage.getTel());
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
					String[] a = { "", "" };
					Coordinateslist.add(a);
				}
			}
			// 根据格式要求 两层list
			CoordinateslistR.add(Coordinateslist);
			// 获取最后的结果
			geometry.setCoordinates(CoordinateslistR);
		}
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		geoJSON.setFeatures(featureList);
		return geoJSON;
	}

	/**
	 * @see 生成城市部件地图信息-点位图
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "cityComponentsMap")
	public GeoJSON cityComponentsMap(@RequestParam(required = false) String type,@RequestParam(required = false) String ids,Model model) {
		// 城市部件 查询对象
		CcmCityComponents ccmCityComponents =CommUtil.ReturnCityComponentsType(type);
		
		if(StringUtils.isNotEmpty(ids)) {
			ccmCityComponents.setMore1("a.id in("+ids+")");
			}
		// 城市部件结果list
		List<CcmCityComponents> ccmCityComponentsList = ccmCityComponentsService.findList(ccmCityComponents);
		// 生成GeoJson 对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 城市部件数组
		for (CcmCityComponents cityComponents : ccmCityComponentsList) {
			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(cityComponents.getId());
			// 3 properties 展示属性信息
			properties.setName(cityComponents.getName());
			properties.setIcon(cityComponents.getImagePath());
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建附属信息
			map.put("部件类型", DictUtils.getDictLabel(cityComponents.getType(), "ccm_city_components_type", ""));
			map.put("编号", cityComponents.getCode());
			map.put("主管部门名称", cityComponents.getCompetentDepartmentName());
			map.put("权属部门电话", cityComponents.getOwnershipDepartmentName());
			map.put("养护部门名称", cityComponents.getMaintainDepartmentName());
			map.put("养护部门电话", cityComponents.getMaintainDepartmentTel());
			map.put("部件状态", cityComponents.getStatus());
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			if ("01".equals(cityComponents.getSpatialForm())) {

				// 编辑图形结构为点
				geometry.setType("Point");
				if (!StringUtils.isEmpty(cityComponents.getAreaPoint())) {
					// 获取中心点的值
					String[] centpoint = cityComponents.getAreaPoint().split(",");
					geoJSON.setCentpoint(centpoint);
					// 添加图形中心点位信息
					properties.setCoordinateCentre(centpoint);
				}
				// 添加具体数据
				// 封装的list
				List<String> Coordinateslist = new ArrayList<>();
				// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
				String[] a = (StringUtils.isEmpty(cityComponents.getAreaPoint()) ? (",")
						: cityComponents.getAreaPoint()).split(",");
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
			} else if ("02".equals(cityComponents.getSpatialForm())) {
				// 点位信息 测试为面
				geometry.setType("LineString");
				// 添加中心点位
				if (!StringUtils.isEmpty(cityComponents.getAreaPoint())) {
					// 获取中心点的值
					String[] centpoint = cityComponents.getAreaPoint().split(",");
					// 添加图层中心点位信息
					geoJSON.setCentpoint(centpoint);
					// 添加图形中心点位信息
					properties.setCoordinateCentre(centpoint);
				}
				// 以下是线
				String[] coordinates = (StringUtils.isEmpty(cityComponents.getAreaMap()) ? ";"
						: cityComponents.getAreaMap()).split(";");
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
						String[] a = { "", "" };
						Coordinateslist.add(a);
					}
				}
				// 获取最后的结果
				geometry.setCoordinates(Coordinateslist);

			}

			else if ("03".equals(cityComponents.getSpatialForm())) {
				// 点位信息 测试为面
				geometry.setType("Polygon");
				// 添加中心点位
				if (!StringUtils.isEmpty(cityComponents.getAreaPoint())) {
					// 获取中心点的值
					String[] centpoint = cityComponents.getAreaPoint().split(",");
					// 添加图层中心点位信息
					geoJSON.setCentpoint(centpoint);
					// 添加图形中心点位信息
					properties.setCoordinateCentre(centpoint);
				}
				// 添加具体数据
				// 封装的list 以有孔与无孔进行添加
				List<Object> CoordinateslistR = new ArrayList<>();
				// 以下是无孔面积数组
				String[] coordinates = (StringUtils.isEmpty(cityComponents.getAreaMap()) ? ";"
						: cityComponents.getAreaMap()).split(";");
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
						String[] a = { "", "" };
						Coordinateslist.add(a);
					}
				}
				// 根据格式要求 两层list
				CoordinateslistR.add(Coordinateslist);
				// 获取最后的结果
				geometry.setCoordinates(CoordinateslistR);

			}
			geoJSON.setFeatures(featureList);
		}

		return geoJSON;
	}

	/**
	 * @see 生成城市部件地图信息-点位图
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "landMap")
	public GeoJSON landMap( @RequestParam(required = false)String ids, Model model) {
		// 土地 查询对象
		CcmLand ccmLand = new CcmLand();
		if(StringUtils.isNotEmpty(ids)) {
		ccmLand.setMore1("a.id in("+ids+")");
		}
		// 土地结果list
		List<CcmLand> ccmLandList = ccmLandService.findList(ccmLand);
		
		
		
		// 生成GeoJson 对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 土地数组
		for (CcmLand land : ccmLandList) {
			if (!StringUtils.isEmpty(land.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = land.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
			}
			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(land.getId());
			// 3 properties 展示属性信息
			properties.setName(land.getName());
			// properties.setIcon(land.getImagePath());
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建附属信息
			map.put("地块编码", land.getCode());
			map.put("地块面积", land.getLandArea());
			map.put("用地性质", DictUtils.getDictLabel(land.getLandUsage(), "ccm_land_usage", ""));
			map.put("土地用途", DictUtils.getDictLabel(land.getType(), "ccm_land_type", ""));
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Polygon");
			// 添加中心点位
			if (!StringUtils.isEmpty(land.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = land.getAreaPoint().split(",");
				// 添加图层中心点位信息
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = (StringUtils.isEmpty(land.getAreaMap()) ? ";" : land.getAreaMap()).split(";");
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
					String[] a = { "", "" };
					Coordinateslist.add(a);
				}
			}
			// 根据格式要求 两层list
			CoordinateslistR.add(Coordinateslist);
			// 获取最后的结果
			geometry.setCoordinates(CoordinateslistR);

		}
		geoJSON.setFeatures(featureList);
		return geoJSON;
	}
	

	/**
	 * @see 移动设备信息-点位图
	 * @param CcmMobileDevice
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("ccmsys:ccmMobileDevice:view")
	@ResponseBody
	@RequestMapping(value = "deviceMobileMap")
	public GeoJSON deviceMobileMap(CcmMobileDevice ccmMobileDevice, Model model) {
		
		Area userArea=UserUtils.getUser().getOffice().getArea();
		ccmMobileDevice.setUserArea(userArea);
		// 查询地图视频信息
		List<CcmMobileDevice> ccmMobileDevicelist = ccmMobileDeviceService.findList(ccmMobileDevice);
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmMobileDevice deviceMobile : ccmMobileDevicelist) {
			if(deviceMobile.getIsOnline().equals("1")){
				// 特征,属性
				Features featureDto = new Features();
				Properties properties = new Properties();
				// 1 type 默认不填
				// 2 id 添加
				featureDto.setId(deviceMobile.getId());
				// 3 properties 展示属性信息
				properties.setName(deviceMobile.getvCcmTeam().getName());
				properties.setIcon("");
				// 点击后展示详细属性值
				Map<String, Object> map_P = new HashMap<String, Object>();
				// 创建附属信息
				map_P.put("设备编号", deviceMobile.getId());
				map_P.put("设备唯一标识码", deviceMobile.getDeviceId());
//			map_P.put("是否授权使用", deviceMobile.getIsVariable());
//			map_P.put("登录用户", deviceMobile.getvCcmTeam().getName());
				map_P.put("电话", deviceMobile.getvCcmTeam().getPhone()+"/"+deviceMobile.getvCcmTeam().getMobile());
				map_P.put("是否越界", deviceMobile.getIsAlarm());
				map_P.put("是否在线", deviceMobile.getIsOnline());
				map_P.put("电子围栏区域", deviceMobile.getEfenceScope());

				properties.addInfo(map_P);
				featureList.add(featureDto);
				featureDto.setProperties(properties);
				// 4 geometry 配置参数
				Geometry geometry = new Geometry();
				featureDto.setGeometry(geometry);
				// 点位信息 测试为点
				geometry.setType("Point");
				// 为中心点赋值
				if (!StringUtils.isEmpty(deviceMobile.getAreaPoint())) {
					// 获取中心点的值
					String[] centpoint = deviceMobile.getAreaPoint().split(",");
					// 图层中心的
					geoJSON.setCentpoint(centpoint);
					// 图形中心点
					properties.setCoordinateCentre(centpoint);
				}
				// 添加具体数据
				// 封装的list
				List<String> Coordinateslist = new ArrayList<>();
				// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
				String[] a = (StringUtils.isEmpty(deviceMobile.getAreaPoint()) ? (",") : deviceMobile.getAreaPoint()).split(",");
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

		}
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	/**
	 * @see 移动设备点位历史轨迹
	 * @param CcmMobileDevice
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deviceMobileTrace", method = RequestMethod.GET)
	public CcmRestResult deviceMobileTrace(HttpServletRequest req, HttpServletResponse resp, CcmTracingpoint ccmTracingpoint) throws IOException {
		if (ccmTracingpoint == null) {
			return null;
		}
		CcmRestResult result = new CcmRestResult();
		if (null == ccmTracingpoint.getBeginCurDate() || "".equals(ccmTracingpoint.getBeginCurDate())) {//开始时间为空的话，默认赋值今天
			Date beginDate = new Date();
			beginDate.setHours(0);
			beginDate.setMinutes(0);
			beginDate.setSeconds(0);
			ccmTracingpoint.setBeginCurDate(beginDate);
		}
		if (null == ccmTracingpoint.getEndCurDate() || "".equals(ccmTracingpoint.getEndCurDate())) {
			Date endDate = new Date();
			ccmTracingpoint.setEndCurDate(endDate);
		}
		
		List<CcmTracingpoint> tlist = ccmTracingpointService.findList(ccmTracingpoint);
		if(tlist.size()>0){
			result.setReturnFlag(true);
		}else{
			result.setReturnFlag(false);
		}
		result.setCode(CcmRestType.OK);
		result.setResult(tlist);
		// 输出结果
		return result;
	}
	
	/**
	 * @see 告警日志移动设备点位历史轨迹
	 * @param CcmMobileDevice
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deviceMobileAlarm", method = RequestMethod.GET)
	public List<String> deviceMobileAlarm(HttpServletRequest req, HttpServletResponse resp, CcmTracingpoint ccmTracingpoint) throws IOException {
		if (ccmTracingpoint == null) {
			return null;
		}
		List<String> list = new ArrayList<>();
		String points = "";
		String areas = "";
		String areasPoint = "";
		if (null == ccmTracingpoint.getBeginCurDate() || "".equals(ccmTracingpoint.getBeginCurDate())) {//开始时间为空的话，默认赋值今天
			Date beginDate = new Date();
			beginDate.setHours(0);
			beginDate.setMinutes(0);
			beginDate.setSeconds(0);
			ccmTracingpoint.setBeginCurDate(beginDate);
		}else{
			Date beginDate = ccmTracingpoint.getBeginCurDate();
			beginDate.setHours(0);
			beginDate.setMinutes(0);
			beginDate.setSeconds(0);
			ccmTracingpoint.setBeginCurDate(beginDate);
		}
		if (null == ccmTracingpoint.getEndCurDate() || "".equals(ccmTracingpoint.getEndCurDate())) {
			Date endDate = new Date();
			ccmTracingpoint.setEndCurDate(endDate);
		}else{
			Date endDate = ccmTracingpoint.getEndCurDate();
			endDate.setHours(23);
			endDate.setMinutes(59);
			endDate.setSeconds(59);
			ccmTracingpoint.setEndCurDate(endDate);
		}
		
		List<CcmTracingpoint> tlist = ccmTracingpointService.findList(ccmTracingpoint);//轨迹点位
		if(tlist.size()>0){
			for(int i=tlist.size()-1;i>=0;i--){
				points += tlist.get(i).getAreaPoint()+";";//轨迹点位
			}
			
		}
		CcmMobileDevice ccmMobileDevice = new CcmMobileDevice();//电子围栏
		ccmMobileDevice.setDeviceId(ccmTracingpoint.getDeviceId());
		List<CcmMobileDevice> slist = ccmMobileDeviceService.findList(ccmMobileDevice);
		if(slist.size()>0){
			areas = slist.get(0).getEfenceScope();//电子围栏
			areasPoint = slist.get(0).getEfencePoint();//电子围栏中心点
		}
		list.add(points);//轨迹点位
		list.add(areas);//电子围栏
		list.add(areasPoint);//电子围栏中心点
		
		// 输出结果
		return list;
	}
	
	
	/**
	 * @see 巡逻路线
	 * @param CcmMobileDevice
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ccmPatrolPointPlanMap", method = RequestMethod.GET)
	public String ccmPatrolPointPlanMap(HttpServletRequest req, HttpServletResponse resp, CcmTracingpoint ccmTracingpoint) throws IOException {
		List<EchartType> list = new ArrayList<>();
		List<EchartType> map = new ArrayList<>();
		list = ccmPatrolPointService.findPatrolPointPlanMap();
		String type = "暂无数据";
		String name = "";
		String point = "";
		for(EchartType l:list){
			if(!type.equals(l.getType())){
				if("暂无数据".equals(type)){
					
				}else{
					EchartType echartType = new EchartType();
					echartType.setType(type);
					echartType.setTypeO(name);
					point = point.substring(0, point.length()-1);
					echartType.setValue(point);
					map.add(echartType);
				}
				type = l.getType();
				name = l.getTypeO();
				point = l.getValue() + ";";
			}else{
				point += l.getValue() + ";";
			}
		}
		EchartType echartType = new EchartType();
		echartType.setType(type);
		echartType.setTypeO(name);
		point = point.substring(0, point.length()-1);
		echartType.setValue(point);
		map.add(echartType);
		
		// 输出结果
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{});
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String listString = JSONArray.fromObject(map,config).toString(); //
		
		return listString;
	}
	
	/**
	 * @see 定时请求事件处理状态
	 * @param CcmEventCasedeal
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getEventCasedealMap", method = RequestMethod.GET)
	public String getEventCasedealMap(HttpServletRequest req, HttpServletResponse resp, CcmEventCasedeal ccmEventCasedeal) throws IOException {
		ccmEventCasedeal = ccmEventCasedealService.getEventCasedealMap(ccmEventCasedeal);
		// 输出结果
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"createBy","updateBy","currentUser","createDate","remarks",
				"objId","objType","status","status","handleUser","handleDeadline","isSupervise","handleDate","handleStatus","handleFeedback",
				"checkDate","checkUser","checkOpinion","checkScore","beginHandleDeadline","endHandleDeadline","isExtension","isCheck","objTypeLable","statusLable",
				"dbName","delFlag","global","isNewRecord","page","sqlMap"});//排除
		config.setIgnoreDefaultExcludes(false);  //设置默认忽略
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		String string = JSONArray.fromObject(ccmEventCasedeal,config).toString(); //
		
		return string;
	}
	/**
	 * @see 生成事件地图信息-点位图（框选查询）
	 * @param ccmEventIncident
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryEventIncidentMapBySelection")
	public GeoJSON queryEventIncidentMapBySelection(CcmEventIncident ccmEventIncident, String pointsString, DataList dataList,String selectionMode,
										 HttpServletRequest request, HttpServletResponse response) {
		//可以选择父节点查询
		if(ccmEventIncident.getArea()!=null&&ccmEventIncident.getArea().getId()!=null&&!ccmEventIncident.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmEventIncident.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmEventIncident.setUserArea(area);
			ccmEventIncident.setArea(null);
		}
		// 查询地图事件信息
		List<CcmEventIncident> ccmEventIncidentlist = ccmEventIncidentService.findList(ccmEventIncident);
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmEventIncident eventIncident : ccmEventIncidentlist) {

			//判断是否在区域内
			String coordiante1=eventIncident.getAreaPoint();
			//判断范围内点工具
			MapUtil mapUtil=new MapUtil();
			//String[] coordiante1=ccmHouseSchoolrim12.getAreaPoint().split(",");
			//double x=Double.parseDouble(coordiante1[0]);
			//double y=Double.parseDouble(coordiante1[1]);
			//Point sPoint= new Point(x, y);
			if(coordiante1 == null || "".equals(coordiante1)){
				continue;
			}
			Point sPoint=CommUtil.toPoint(coordiante1);
			boolean result=false;
			if(selectionMode.equals("polygon")){
				List<Point> points=new ArrayList<Point>();
				String[] s1=pointsString.split(";");
				for (int i = 0; i < s1.length; i++) {
					String[] s2=s1[i].split(",");
					double x=Double.parseDouble(s2[0]);
					double y=Double.parseDouble(s2[1]);
					Point point= new Point(x, y);
					points.add(point);
				}
				result = mapUtil.isPointInPolygon(sPoint, points);
			}
			if(selectionMode.equals("circle")){
				result=Tool.isInCircle(sPoint.X,sPoint.Y, Double.parseDouble(dataList.getX()),
						Double.parseDouble(dataList.getY()), dataList.getRadius());
			}

			if (!result) {
				continue;
			}

			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(eventIncident.getId());
			// 3 properties 展示属性信息
			properties.setName(eventIncident.getCaseName());
			properties.setIcon(eventIncident.getFile1());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("事件名称", eventIncident.getCaseName());
			map_P.put("事件情况", eventIncident.getCaseCondition());
			map_P.put("事发时间", (eventIncident.getHappenDate() == null) ? "" : time.format(eventIncident.getHappenDate()) + "  ");
			map_P.put("事发地址", eventIncident.getHappenPlace());
			map_P.put("主犯姓名", eventIncident.getCulName());

			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(eventIncident.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = eventIncident.getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(eventIncident.getAreaPoint()) ? (",") : eventIncident.getAreaPoint()).split(",");
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
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}
	/**
	 * @see 生成楼栋地图信息-点位图（框选查询）
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryBuildMapBySelection")
	public GeoJSON queryBuildMapBySelection(CcmHouseBuildmanage ccmHouseBuildmanage, String pointsString, DataList dataList,String selectionMode,
								 HttpServletRequest request, HttpServletResponse response) {

		//可以选择父节点查询
		if(ccmHouseBuildmanage.getArea()!=null&&ccmHouseBuildmanage.getArea().getId()!=null&&!ccmHouseBuildmanage.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmHouseBuildmanage.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmHouseBuildmanage.setUserArea(area);
			ccmHouseBuildmanage.setArea(null);
		}
		// 查询地图楼栋信息
		List<CcmHouseBuildmanage> ccmHouseBuildmanageList = ccmHouseBuildmanageService.findList(ccmHouseBuildmanage);


		// 返回对象
		GeoJSON geoJSON = new GeoJSON();

		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmHouseBuildmanage Buildmanage : ccmHouseBuildmanageList) {

			//判断是否在区域内
			String coordiante1=Buildmanage.getAreaPoint();
			//判断范围内点工具
			MapUtil mapUtil=new MapUtil();
			//String[] coordiante1=ccmHouseSchoolrim12.getAreaPoint().split(",");
			//double x=Double.parseDouble(coordiante1[0]);
			//double y=Double.parseDouble(coordiante1[1]);
			//Point sPoint= new Point(x, y);
			if(coordiante1 == null || "".equals(coordiante1)){
				continue;
			}
			Point sPoint=CommUtil.toPoint(coordiante1);
			boolean result=false;
			if(selectionMode.equals("polygon")){
				List<Point> points=new ArrayList<Point>();
				String[] s1=pointsString.split(";");
				for (int i = 0; i < s1.length; i++) {
					String[] s2=s1[i].split(",");
					double x=Double.parseDouble(s2[0]);
					double y=Double.parseDouble(s2[1]);
					Point point= new Point(x, y);
					points.add(point);
				}
				result = mapUtil.isPointInPolygon(sPoint, points);
			}
			if(selectionMode.equals("circle")){
				result=Tool.isInCircle(sPoint.X,sPoint.Y, Double.parseDouble(dataList.getX()),
						Double.parseDouble(dataList.getY()), dataList.getRadius());
			}
			if (!result) {
				continue;
			}


			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(Buildmanage.getId());
			// 3 properties 展示属性信息
			properties.setName(Buildmanage.getName());
			properties.setIcon(Buildmanage.getImages());
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建附属信息
			// map.put("id", Buildmanage.getId());
			map.put("楼栋名称", Buildmanage.getBuildname());
			map.put("所属小区", Buildmanage.getName());
			map.put("楼栋长", Buildmanage.getBuildPname());
			map.put("楼栋长电话", Buildmanage.getTel());
			map.put("层数", Buildmanage.getPilesNum() + "");
			map.put("单元数", Buildmanage.getElemNum() + "");
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(Buildmanage.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = Buildmanage.getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}

			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(Buildmanage.getAreaPoint()) ? (",") : Buildmanage.getAreaPoint()).split(",");
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
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		geoJSON.setFeatures(featureList);
		return geoJSON;
	}

	/**
	 * @see 生成场所地图信息-点位图（框选查询）
	 * @param ccmBasePlace
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryPlaceMapBySelection")
	public GeoJSON queryPlaceMapBySelection(CcmBasePlace ccmBasePlace, String pointsString, DataList dataList,String selectionMode,HttpServletRequest request, HttpServletResponse response) {

		//可以选择父节点查询
		if(ccmBasePlace.getArea()!=null&&ccmBasePlace.getArea().getId()!=null&&!ccmBasePlace.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmBasePlace.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmBasePlace.setUserArea(area);
			ccmBasePlace.setArea(null);
		}
		// 查询地图场所信息
		List<CcmBasePlace> ccmBasePlacelist = ccmBasePlaceService.findList(ccmBasePlace);

		// 返回对象
		GeoJSON geoJSON = new GeoJSON();

		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmBasePlace basePlace : ccmBasePlacelist) {

			//判断是否在区域内
			String coordiante1=basePlace.getAreaPoint();
			//判断范围内点工具
			MapUtil mapUtil=new MapUtil();

			if(coordiante1 == null || "".equals(coordiante1)){
				continue;
			}
			Point sPoint=CommUtil.toPoint(coordiante1);
			boolean result=false;
			if(selectionMode.equals("polygon")){
				List<Point> points=new ArrayList<Point>();
				String[] s1=pointsString.split(";");
				for (int i = 0; i < s1.length; i++) {
					String[] s2=s1[i].split(",");
					double x=Double.parseDouble(s2[0]);
					double y=Double.parseDouble(s2[1]);
					Point point= new Point(x, y);
					points.add(point);
				}
				result = mapUtil.isPointInPolygon(sPoint, points);
			}
			if(selectionMode.equals("circle")){
				result=Tool.isInCircle(sPoint.X,sPoint.Y, Double.parseDouble(dataList.getX()),
						Double.parseDouble(dataList.getY()), dataList.getRadius());
			}
			if (!result) {
				continue;
			}


			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(basePlace.getId());
			// 3 properties 展示属性信息
			properties.setName(basePlace.getPlaceName());
			properties.setIcon(basePlace.getPlacePicture());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("场所名称", basePlace.getPlaceName());
			map_P.put("重点属性", basePlace.getKeyPoint());
			map_P.put("场所面积", basePlace.getPlaceArea());
			map_P.put("场所用途", basePlace.getPlaceUse());
			map_P.put("负责人姓名", basePlace.getLeaderName());
			map_P.put("负责人电话", basePlace.getLeaderContact());
			map_P.put("主管单位名称", basePlace.getGoverningBodyName());
			map_P.put("地址", basePlace.getAddress());
			map_P.put("场所类型", basePlace.getPlaceType());
			map_P.put("场所图片", basePlace.getPlacePicture());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(basePlace.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = basePlace.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}

			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(basePlace.getAreaPoint()) ? (",") : basePlace.getAreaPoint()).split(",");
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
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}
	/**
	 * @see 生成人口地图信息-点位图（框选查询）
	 * @param CcmPeople
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryPeopleMapBySelection")
	public GeoJSON queryPeopleMapBySelection(CcmPeople ccmPeople, String pointsString, DataList dataList,String selectionMode,String importantType, HttpServletRequest request, HttpServletResponse response) {
		if(StringUtils.isNotEmpty(importantType)) {
        	switch(importantType) {
        	case "a":
        		ccmPeople.setIsKym(1);
        		break;
        	case "b":
        		ccmPeople.setIsRelease(1);
        		break;
        	case "c":
        		ccmPeople.setIsRectification(1);
        		break;
        	case "d":
        		ccmPeople.setIsAids(1);
        		break;
        	case "e":
        		ccmPeople.setIsDangerous(1);
        		break;
        	case "f":
        		ccmPeople.setIsDrugs(1);
        		break;
        	case "g":
        		ccmPeople.setIsHeresy(1);
        		break;
        	case "l":
        		ccmPeople.setIsPsychogeny(1);
        		break;
        	case "m":
        		ccmPeople.setIsHarmNational(1);
        		break;
        	case "n":
        		ccmPeople.setIsCriminalOffense(1);
        		break;
        	case "o":
        		ccmPeople.setIsDispute(1);
        		break;
        	case "p":
        		ccmPeople.setIsDeliberatelyIllegal(1);
        		break;
        	}
        }
		//可以选择父节点查询
		if(ccmPeople.getAreaGridId()!=null&&ccmPeople.getAreaGridId().getId()!=null&&!ccmPeople.getAreaGridId().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmPeople.getAreaGridId().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmPeople.setUserArea(area);
			ccmPeople.setAreaGridId(null);
		}
		// 查询地图人口信息
		List<CcmPeople> ccmPeoplelist = ccmPeopleService.findList(ccmPeople);

		//
		CcmPeople ccmPeople2 = new CcmPeople();
		String[] listLimite = new String[ccmPeoplelist.size()];
		if (ccmPeoplelist.size() > 0) {
			for (int i = 0; i < ccmPeoplelist.size(); i++) {
				listLimite[i] = ccmPeoplelist.get(i).getId();
			}
			ccmPeople2.setListLimite(listLimite);
			ccmPeoplelist = ccmPeopleService.findListLimite(ccmPeople2);// 数组查询id
		}


		// 返回对象
		GeoJSON geoJSON = new GeoJSON();

		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmPeople people : ccmPeoplelist) {

			//判断是否在区域内
			String coordiante1=people.getAreaPoint();
			//判断范围内点工具

			if(coordiante1 == null || "".equals(coordiante1)){
				continue;
			}
			Point sPoint=CommUtil.toPoint(coordiante1);
			boolean result=false;
			if(selectionMode.equals("polygon")){
				List<Point> points=new ArrayList<Point>();
				String[] s1=pointsString.split(";");
				for (int i = 0; i < s1.length; i++) {
					String[] s2=s1[i].split(",");
					double x=Double.parseDouble(s2[0]);
					double y=Double.parseDouble(s2[1]);
					Point point= new Point(x, y);
					points.add(point);
				}
				result = MapUtil.isPointInPolygon(sPoint, points);
			}
			if(selectionMode.equals("circle")){
				result=Tool.isInCircle(sPoint.X,sPoint.Y, Double.parseDouble(dataList.getX()),
						Double.parseDouble(dataList.getY()), dataList.getRadius());
			}
			if (!result) {
				continue;
			}

			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(people.getId());
			// 3 properties 展示属性信息
			properties.setName(people.getName());
			properties.setIcon(people.getImages());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("姓名", people.getName());
			map_P.put("身份号码", people.getIdent());
			map_P.put("出生日期", (people.getBirthday() == null) ? "" : time.format(people.getBirthday()) + "  ");
			map_P.put("户籍详址", people.getDomiciledetail());
			map_P.put("现住详址", people.getResidencedetail());
			map_P.put("联系方式", people.getTelephone());
			map_P.put("所在单位", people.getOfficeName());
			map_P.put("人口类型", people.getType());
			if(people.getBuildId()!=null){
				map_P.put("住所楼栋名称", people.getBuildId().getBuildname());
				map_P.put("住所楼栋id", people.getBuildId().getId());
				map_P.put("住所楼栋区域", people.getBuildId().getAreaMap());
			}

			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(people.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = people.getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);

				// 添加具体数据
				// 封装的list
				List<String> Coordinateslist = new ArrayList<>();
				// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
				String[] a = (StringUtils.isEmpty(people.getAreaPoint()) ? (",") : people.getAreaPoint()).split(",");
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
			}else if (people.getBuildId()!=null&&!StringUtils.isEmpty(people.getBuildId().getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = people.getBuildId().getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);

				// 添加具体数据
				// 封装的list
				List<String> Coordinateslist = new ArrayList<>();
				// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
				String[] a = (StringUtils.isEmpty(people.getBuildId().getAreaPoint()) ? (",") : people.getBuildId().getAreaPoint()).split(",");
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
		}
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	/**
	 * @see 生成视频地图信息-点位图（分页模式）
	 * @param ccmDevice
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDeviceMapBySelection")
	public GeoJSON queryDeviceMapBySelection(CcmDevice ccmDevice,String pointsString, DataList dataList,String selectionMode,
								  HttpServletRequest request, HttpServletResponse response) {
		//可以选择父节点查询
		if(ccmDevice.getArea()!=null&&ccmDevice.getArea().getId()!=null&&!ccmDevice.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmDevice.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmDevice.setUserArea(area);
			ccmDevice.setArea(null);
		}
		// 查询地图视频信息
		List<CcmDevice> ccmdevicelist = ccmDeviceService.findList(ccmDevice);

		// 返回对象
		GeoJSON geoJSON = new GeoJSON();

		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmDevice device : ccmdevicelist) {
			//判断是否在区域内
			String coordiante1=device.getCoordinate();
			//判断范围内点工具
			MapUtil mapUtil=new MapUtil();

			if(coordiante1 == null || "".equals(coordiante1)){
				continue;
			}
			Point sPoint=CommUtil.toPoint(coordiante1);
			boolean result=false;
			if(selectionMode.equals("polygon")){
				List<Point> points=new ArrayList<Point>();
				String[] s1=pointsString.split(";");
				for (int i = 0; i < s1.length; i++) {
					String[] s2=s1[i].split(",");
					double x=Double.parseDouble(s2[0]);
					double y=Double.parseDouble(s2[1]);
					Point point= new Point(x, y);
					points.add(point);
				}
				result = mapUtil.isPointInPolygon(sPoint, points);
			}
			if(selectionMode.equals("circle")){
				result=Tool.isInCircle(sPoint.X,sPoint.Y, Double.parseDouble(dataList.getX()),
						Double.parseDouble(dataList.getY()), dataList.getRadius());
			}
			if (!result) {
				continue;
			}

			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(device.getId());
			// 3 properties 展示属性信息
			properties.setName(device.getName());
			properties.setIcon(device.getImagePath());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("IP地址", device.getIp());
			map_P.put("登陆账号", device.getAccount());
			map_P.put("安装位置", device.getAddress());
			map_P.put("设备状态", device.getStatus());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(device.getCoordinate())) {
				// 获取中心点的值
				String[] centpoint = device.getCoordinate().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(device.getCoordinate()) ? (",") : device.getCoordinate()).split(",");
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
			// 5 geometry 配置参数
			// 配置视频信号信息
			Map<String, String> map_V = new HashMap<String, String>();
			// 协议，设备参数信息
			map_V.put("protocol", device.getProtocol());
			map_V.put("param", device.getParam());
			map_V.put("ip", device.getIp());
			map_V.put("port", device.getPort());
			map_V.put("username", device.getAccount());
			map_V.put("password", device.getPassword());
			properties.setVideo(map_V);
		}
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	/**
	 * @see 生成事件地图信息-点位图（分页模式）
	 * @param ccmEventIncident
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryEventIncidentMap")
	public GeoJSON queryEventIncidentMap(CcmEventIncident ccmEventIncident,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) int pageNo, @RequestParam(required = false) int pageSize) {
		//分页参数处理
		Page pageIn = new Page<CcmEventIncident>(request, response);
		if (pageNo != 0) {
			pageIn.setPageNo(pageNo);
		}
		if (pageSize != 0) {
			pageIn.setPageSize(pageSize);
		}
		
		// 查询地图事件信息
		List<CcmEventIncident> ccmEventIncidentlist = new ArrayList<CcmEventIncident>();
		//可以选择父节点查询
		if(ccmEventIncident.getArea()!=null&&ccmEventIncident.getArea().getId()!=null&&!ccmEventIncident.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmEventIncident.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmEventIncident.setUserArea(area);
			ccmEventIncident.setArea(null);
		}
		Page<CcmEventIncident> page = ccmEventIncidentService.findPage(pageIn, ccmEventIncident);
		ccmEventIncidentlist = page.getList();
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		geoJSON.setCount(page.getCount());
		geoJSON.setPageNo(page.getPageNo());
		geoJSON.setPageSize(page.getPageSize());
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmEventIncident eventIncident : ccmEventIncidentlist) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(eventIncident.getId());
			// 3 properties 展示属性信息
			properties.setName(eventIncident.getCaseName());
			properties.setIcon(eventIncident.getFile1());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new LinkedHashMap<String, Object>();
			// 创建附属信息
			map_P.put("事件名称", eventIncident.getCaseName());
			map_P.put("事件情况", eventIncident.getCaseCondition());
			map_P.put("事发时间", (eventIncident.getHappenDate() == null) ? "" : time.format(eventIncident.getHappenDate()) + "  ");
			map_P.put("事发地址", eventIncident.getHappenPlace());
			map_P.put("主犯姓名", eventIncident.getCulName());
			map_P.put("事件ID", eventIncident.getId());
			
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(eventIncident.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = eventIncident.getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(eventIncident.getAreaPoint()) ? (",") : eventIncident.getAreaPoint()).split(",");
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
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}
    

	/**
	 * @see 生成人口地图信息-点位图（分页模式）
	 * @param CcmPeople
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryPeopleMap")
	public GeoJSON queryPeopleMap(CcmPeople ccmPeople, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) int pageNo, @RequestParam(required = false) int pageSize, @RequestParam(required = false) String importantType) {
		//分页参数处理
		Page pageIn = new Page<CcmPeople>(request, response);
		if (pageNo != 0) {
			pageIn.setPageNo(pageNo);
		}
		if (pageSize != 0) {
			pageIn.setPageSize(pageSize);
		}

		// 查询地图人口信息
		List<CcmPeople> ccmPeoplelist = new ArrayList<CcmPeople>();



        long t1 = System.currentTimeMillis();
        if(StringUtils.isNotEmpty(importantType)) {
        	switch(importantType) {
        	case "a":
        		ccmPeople.setIsKym(1);
        		break;
        	case "b":
        		ccmPeople.setIsRelease(1);
        		break;
        	case "c":
        		ccmPeople.setIsRectification(1);
        		break;
        	case "d":
        		ccmPeople.setIsAids(1);
        		break;
        	case "e":
        		ccmPeople.setIsDangerous(1);
        		break;
        	case "f":
        		ccmPeople.setIsDrugs(1);
        		break;
        	case "g":
        		ccmPeople.setIsHeresy(1);
        		break;
        	case "l":
        		ccmPeople.setIsPsychogeny(1);
        		break;
        	case "m":
        		ccmPeople.setIsHarmNational(1);
        		break;
        	case "n":
        		ccmPeople.setIsCriminalOffense(1);
        		break;
        	case "o":
        		ccmPeople.setIsDispute(1);
        		break;
        	case "p":
        		ccmPeople.setIsDeliberatelyIllegal(1);
        		break;
        	}
        }
		//可以选择父节点查询
		if(ccmPeople.getAreaGridId()!=null&&ccmPeople.getAreaGridId().getId()!=null&&!ccmPeople.getAreaGridId().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmPeople.getAreaGridId().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmPeople.setUserArea(area);
			ccmPeople.setAreaGridId(null);
		}
		Page<CcmPeople> page = ccmPeopleService.findPage(pageIn, ccmPeople);

        List<CcmPeople> ccmPeopleNewList = ccmPeopleService.queryPeopleInfo(page);

        long t2 = System.currentTimeMillis();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t2 - t1);

        System.out.println("查询列表耗时: " + c.get(Calendar.MINUTE) + "分 "
                + c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
                + " 毫秒");
		//List<CcmPeople> list = page.getList();
	//	CcmPeople ccmPeople2 = new CcmPeople();
	//	String[] listLimite = new String[list.size()];
//		if (list.size() > 0) {
//			for (int i = 0; i < list.size(); i++) {
//				listLimite[i] = list.get(i).getId();
//			}
//
//			ccmPeople2.setListLimite(listLimite);
//			ccmPeoplelist = ccmPeopleService.findListLimite(ccmPeople2);// 数组查询id
//
//		}


		
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		geoJSON.setCount(page.getCount());
		geoJSON.setPageNo(page.getPageNo());
		geoJSON.setPageSize(page.getPageSize());
		
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmPeople people : ccmPeopleNewList) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(people.getId());
			// 3 properties 展示属性信息
			properties.setName(people.getName());
			properties.setIcon(people.getImages());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new LinkedHashMap<String, Object>();
			// 创建附属信息
			map_P.put("姓名", people.getName());
			map_P.put("身份号码", people.getIdent());
			map_P.put("出生日期", (people.getBirthday() == null) ? "" : time.format(people.getBirthday()) + "  ");
			map_P.put("户籍详址", people.getDomiciledetail());
			map_P.put("现住详址", people.getResidencedetail());
			map_P.put("联系方式", people.getTelephone());
			map_P.put("所在单位", people.getOfficeName());
			map_P.put("人口类型", people.getType());
			if(people.getBuildId()!=null){
				map_P.put("住所楼栋名称", people.getBuildId().getBuildname());
				map_P.put("住所楼栋id", people.getBuildId().getId());
				map_P.put("住所楼栋区域", people.getBuildId().getAreaMap());
				map_P.put("单元数", people.getBuildId().getElemNum());
				map_P.put("层数", people.getBuildId().getPilesNum());
			}
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (people.getBuildId()!=null&&!StringUtils.isEmpty(people.getBuildId().getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = people.getBuildId().getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			if (people.getBuildId()!=null){
				List<String> Coordinateslist = new ArrayList<>();
				// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
				String[] a = (StringUtils.isEmpty(people.getBuildId().getAreaPoint()) ? (",") : people.getBuildId().getAreaPoint()).split(",");
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

		}
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}

        long t5 = System.currentTimeMillis(); // 排序后取得当前时间
        Calendar c3 = Calendar.getInstance();
        c3.setTimeInMillis(t5 - t1);

        System.out.println("查询总耗时: " + c3.get(Calendar.MINUTE) + "分 "
                + c3.get(Calendar.SECOND) + "秒 " + c3.get(Calendar.MILLISECOND)
                + " 毫秒");
		return geoJSON;
	}
	

	/**
	 * @see 生成场所地图信息-点位图（分页模式）
	 * @param ccmBasePlace
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryPlaceMap")
	public GeoJSON queryPlaceMap(CcmBasePlace ccmBasePlace, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) int pageNo, @RequestParam(required = false) int pageSize) {
		//分页参数处理
		Page pageIn = new Page<CcmBasePlace>(request, response);
		if (pageNo != 0) {
			pageIn.setPageNo(pageNo);
		}
		if (pageSize != 0) {
			pageIn.setPageSize(pageSize);
		}
		
		// 查询地图场所信息
		List<CcmBasePlace> ccmBasePlacelist = new ArrayList<CcmBasePlace>();
		//可以选择父节点查询
		if(ccmBasePlace.getArea()!=null&&ccmBasePlace.getArea().getId()!=null&&!ccmBasePlace.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmBasePlace.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmBasePlace.setUserArea(area);
			ccmBasePlace.setArea(null);
		}
		Page<CcmBasePlace> page = ccmBasePlaceService.findPage(pageIn, ccmBasePlace);
		ccmBasePlacelist = page.getList();
		
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();

		geoJSON.setCount(page.getCount());
		geoJSON.setPageNo(page.getPageNo());
		geoJSON.setPageSize(page.getPageSize());
		
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmBasePlace basePlace : ccmBasePlacelist) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(basePlace.getId());
			// 3 properties 展示属性信息
			properties.setName(basePlace.getPlaceName());
			properties.setIcon(basePlace.getPlacePicture());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("场所名称", basePlace.getPlaceName());
			map_P.put("重点属性", basePlace.getKeyPoint());
			map_P.put("场所面积", basePlace.getPlaceArea());
			map_P.put("场所用途", basePlace.getPlaceUse());
			map_P.put("负责人姓名", basePlace.getLeaderName());
			map_P.put("负责人电话", basePlace.getLeaderContact());
			map_P.put("主管单位名称", basePlace.getGoverningBodyName());
			map_P.put("地址", basePlace.getAddress());
			map_P.put("场所类型", basePlace.getPlaceType());
			map_P.put("场所图片", basePlace.getPlacePicture());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(basePlace.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = basePlace.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}

			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(basePlace.getAreaPoint()) ? (",") : basePlace.getAreaPoint()).split(",");
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
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	/**
	 * @see 生成视频地图信息-点位图（分页模式）
	 * @param ccmDevice
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryDeviceMap")
	public GeoJSON queryDeviceMap(CcmDevice ccmDevice,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) int pageNo, @RequestParam(required = false) int pageSize) {
		//分页参数处理
		Page pageIn = new Page<CcmEventIncident>(request, response);
		if (pageNo != 0) {
			pageIn.setPageNo(pageNo);
		}
		if (pageSize != 0) {
			pageIn.setPageSize(pageSize);
		}

		List<SysDicts> sysDicts = sysDictsService.findAllListByType("ccm_device_status");
		Map sysDictsMap = Maps.newHashMap();
		for (SysDicts sysDict : sysDicts) {
			sysDictsMap.put(sysDict.getValue(),sysDict.getLabel());
		}

		// 查询地图视频信息
		List<CcmDevice> ccmdevicelist = new ArrayList<CcmDevice>();

		//可以选择父节点查询
		if(ccmDevice.getArea()!=null&&ccmDevice.getArea().getId()!=null&&!ccmDevice.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmDevice.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmDevice.setUserArea(area);
			ccmDevice.setArea(null);
		}
		Page<CcmDevice> page = ccmDeviceService.findPage(pageIn, ccmDevice);
		ccmdevicelist = page.getList();
		
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		geoJSON.setCount(page.getCount());
		geoJSON.setPageNo(page.getPageNo());
		geoJSON.setPageSize(page.getPageSize());
		
		List<Features> featureList = new ArrayList<Features>();
		// 数组
		for (CcmDevice device : ccmdevicelist) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(device.getId());
			// 3 properties 展示属性信息
			properties.setName(device.getName());
			properties.setIcon(device.getImagePath());
			// 点击后展示详细属性值
			Map<String, Object> map_P = new LinkedHashMap<String, Object>();
			// 创建附属信息
			map_P.put("设备名称", device.getName());
			map_P.put("设备编号", device.getCode());
			//map_P.put("IP地址", device.getIp());
			map_P.put("安装位置", device.getAddress());
			if(StringUtils.isEmpty(device.getStatus())) {
				map_P.put("设备状态", "未知");
			}else {
				map_P.put("设备状态", sysDictsMap.get(device.getStatus()));
			}
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(device.getCoordinate())) {
				// 获取中心点的值
				String[] centpoint = device.getCoordinate().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(device.getCoordinate()) ? (",") : device.getCoordinate()).split(",");
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
			// 5 geometry 配置参数
			// 配置视频信号信息
			Map<String, String> map_V = new HashMap<String, String>();
			// 协议，设备参数信息
			map_V.put("protocol", device.getProtocol());
			map_V.put("param", device.getParam());
			map_V.put("ip", device.getIp());
			map_V.put("port", device.getPort());
			map_V.put("username", device.getAccount());
			map_V.put("password", device.getPassword());
			properties.setVideo(map_V);
		}
		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	/**
	 * @see 生成楼栋地图信息-点位图（分页模式）
	 * @param ccmHouseBuildmanage
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryBuildMap")
	public GeoJSON queryBuildMap(CcmHouseBuildmanage ccmHouseBuildmanage,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = false) int pageNo, @RequestParam(required = false) int pageSize) {
		//分页参数处理
		Page pageIn = new Page<CcmHouseBuildmanage>(request, response);
		if (pageNo != 0) {
			pageIn.setPageNo(pageNo);
		}
		if (pageSize != 0) {
			pageIn.setPageSize(pageSize);
		}
        Long t1 = System.currentTimeMillis();
		// 查询地图楼栋信息
		List<CcmHouseBuildmanage> ccmHouseBuildmanageList = new ArrayList<CcmHouseBuildmanage>();
		//可以选择父节点查询
		if(ccmHouseBuildmanage.getArea()!=null&&ccmHouseBuildmanage.getArea().getId()!=null&&!ccmHouseBuildmanage.getArea().getId().equals("")){
			SysArea sysArea = sysAreaService.get(ccmHouseBuildmanage.getArea().getId());
			Area area = new Area();
			area.setId(sysArea.getId());
			area.setParentIds(sysArea.getParentIds());
			ccmHouseBuildmanage.setUserArea(area);
			ccmHouseBuildmanage.setArea(null);
		}
		Page<CcmHouseBuildmanage> page = ccmHouseBuildmanageService.findPageNew(pageIn, ccmHouseBuildmanage);
        long t2 = System.currentTimeMillis(); // 排序后取得当前时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(t2 - t1);

        System.out.println("查询地图楼栋信息 耗时: " + c.get(Calendar.MINUTE) + "分 "
                + c.get(Calendar.SECOND) + "秒 " + c.get(Calendar.MILLISECOND)
                + " 毫秒");

        ccmHouseBuildmanageList = page.getList();
		
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		geoJSON.setCount(page.getCount());
		geoJSON.setPageNo(page.getPageNo());
		geoJSON.setPageSize(page.getPageSize());
		
		List<Features> featureList = new ArrayList<Features>();
		// 数组
        Long t3 = System.currentTimeMillis();
		for (CcmHouseBuildmanage Buildmanage : ccmHouseBuildmanageList) {
			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(Buildmanage.getId());
			// 3 properties 展示属性信息
			properties.setName(Buildmanage.getName());
			properties.setIcon(Buildmanage.getImages());
			Map<String, Object> map = new HashMap<String, Object>();
			// 创建附属信息
			// map.put("id", Buildmanage.getId());
			map.put("楼栋名称", Buildmanage.getBuildname());
			map.put("所属小区", Buildmanage.getName());
			map.put("楼栋长", Buildmanage.getBuildPname());
			map.put("楼栋长电话", Buildmanage.getTel());
			map.put("层数", Buildmanage.getPilesNum() + "");
			map.put("单元数", Buildmanage.getElemNum() + "");
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(Buildmanage.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = Buildmanage.getAreaPoint().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(Buildmanage.getAreaPoint()) ? (",") : Buildmanage.getAreaPoint()).split(",");
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
        long t4 = System.currentTimeMillis(); // 排序后取得当前时间
        Calendar c1 = Calendar.getInstance();
        c1.setTimeInMillis(t4 - t3);

        System.out.println("地图楼栋封闭GSON 耗时: " + c1.get(Calendar.MINUTE) + "分 "
                + c1.get(Calendar.SECOND) + "秒 " + c1.get(Calendar.MILLISECOND)
                + " 毫秒");
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		geoJSON.setFeatures(featureList);
        long t5 = System.currentTimeMillis(); // 排序后取得当前时间
        Calendar c2 = Calendar.getInstance();
        c2.setTimeInMillis(t5 - t1);
        System.out.println("地图楼栋查询总耗时: " + c2.get(Calendar.MINUTE) + "分 "
                + c2.get(Calendar.SECOND) + "秒 " + c2.get(Calendar.MILLISECOND)
                + " 毫秒");
		return geoJSON;
	}
	

	/**
	 * @see 地图-数据采集对比统计
	 * @param queryType 查询类型：1、建筑物楼栋；2、房屋；3、人口；4、企业；5、场所；
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "queryCollectStat")
	public GeoJSON queryCollectStat(@RequestParam(required = false) String queryType,
			@RequestParam(required = false) String parentId,
			HttpServletRequest request, HttpServletResponse response) {

		SysArea sysArea = new SysArea();
		
		//无指定区域id时，则查询系统级别下的各区域的数据：系统应用级别：1街道；2区县；3市
		//存在指定区域时则查询该区域下级区域的数据（统计子区域）
		List<SysArea> areaList = new ArrayList<SysArea>();
		if (parentId == null || "".equals(parentId)) {
			SysConfig sysConfig = sysConfigService.get("system_level");
			String level = sysConfig.getParamStr();//系统应用级别：1街道；2区县；3市
			String type = "";//区域类型：1国家、2省、3地市、4区县、5街道、6社区
			if ("1".equals(level)) {//1街道
				type = "6";
			} else if ("2".equals(level)) {//2区县
				type = "5";
			} else if ("3".equals(level)) {//3市  TODO  未做待完善
				type = "4";
			}
			SysArea sysArea2 = new SysArea();
			sysArea2.setType(type);
			sysArea.setType(type);  //主查询方法中查询人口统计数据时使用
			areaList = sysAreaService.findList(sysArea2);
		} else {
			SysArea sysArea2 = new SysArea();
			SysArea parent = new SysArea();
			parent.setId(parentId);
			sysArea2.setParent(parent);
			areaList = sysAreaService.findList(sysArea2);
			if (areaList != null) {
				sysArea.setType(areaList.get(0).getType());  //主查询方法中查询人口统计数据时使用
			}
		}
		StringBuffer areaIds = new StringBuffer();//查询出来的id，拼接为字符串
		areaIds.append("id in(");
		for (SysArea sysArea2 : areaList) {
			areaIds.append("'").append(sysArea2.getId()).append("',");
		}
		areaIds.append("'')");
		
		sysArea.setMore1(areaIds.toString());
		List<SysArea> list =  new ArrayList<SysArea>();
		
		if ("1".equals(queryType)) {//1建筑物楼栋
			list = sysAreaService.queryBuildCollectStat(sysArea);
		} else if ("2".equals(queryType)) {//2房屋
			list = sysAreaService.queryRoomCollectStat(sysArea);
		} else if ("3".equals(queryType)) {//3人口
			list = sysAreaService.queryPeopleCollectStat(sysArea);
		} else if ("4".equals(queryType)) {//4企业
			list = sysAreaService.queryNpseCollectStat(sysArea);
		} else if ("5".equals(queryType)) {//5场所
			list = sysAreaService.queryPlaceCollectStat(sysArea);
		}
		
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		for (SysArea bean : list) {
			// 特征属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(bean.getId());
			// 3 properties 展示属性信息
			properties.setName(bean.getName());
			// 创建附属信息
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("数量", bean.getNum());
			properties.addInfo(map);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为面
			geometry.setType("Polygon");
			if (!StringUtils.isEmpty(bean.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = bean.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list 以有孔与无孔进行添加
			List<Object> CoordinateslistR = new ArrayList<>();
			// 以下是无孔面积数组
			String[] coordinates = (StringUtils.isEmpty(bean.getAreaMap()) ? ";" : bean.getAreaMap()).split(";");
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
					String[] a = { "", "" };
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
	 * 根据SN号查询机顶盒信息
	 * @param snNum
	 * @param model
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "buildBox")
	public GeoJSON buildBox(CcmPeople ccmPeople ,@RequestParam(required = false) String snNum, Model model) {
		ccmPeople.setMore1("ALL");
//		List<CcmPeople> ccmPeopleList = ccmPeopleService.findList(ccmPeople);
		List<CcmPeople> ccmPeopleList = ccmPeopleService.findListBuildBox(ccmPeople);
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		for (CcmPeople ccmPeople1 : ccmPeopleList) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(ccmPeople1.getId());
			// 3 properties 展示属性信息
			properties.setName(ccmPeople1.getName());
			properties.setIcon("box.png");
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			// 创建附属信息
			map_P.put("网格", ccmPeople1.getAreaGridId().getName());
			map_P.put("楼栋", ccmPeople1.getResidencedetail());
			map_P.put("电话", ccmPeople1.getTelephone());
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(ccmPeople1.getMore2())) {
				// 获取中心点的值
				String[] centpoint = ccmPeople1.getMore2().split(",");
				// 图层中心的
				geoJSON.setCentpoint(centpoint);
				// 图形中心点
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(ccmPeople1.getMore2()) ? (",") : ccmPeople1.getMore2()).split(",");
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

		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}

	@RequestMapping(value = "todevice")
	public String formIncident(String code, Model model) {
		CcmDeviceBroadcast deviceBroad = new CcmDeviceBroadcast();
		if("" != code && code != null) {
			deviceBroad.setCode(code);
			//根据code查询广播站信息
			List<CcmDeviceBroadcast> list = ccmDeviceBroadcastService.getByCode(deviceBroad);
			if(list.size()>0) {
				model.addAttribute("deviceBroadcast", list.get(0));
			}else {
				model.addAttribute("deviceBroadcast", deviceBroad);
			}
			return "ccm/broadcast/deviceUpload";
		}else {
			model.addAttribute("deviceBroadcast", deviceBroad);
			return "ccm/broadcast/deviceUpload";
		}
		
	}

	/**
	 * @see  地图信息点位图
	 * @param type   类型:
	 *                  1、人口；2：楼栋 ；3、出租屋（只查出租的）；
	 *                  4、城市部件（按部件类型区分）；5、企业（按风险等级区分）；
	 *                  6、重点人员（按重点人员类型区分）；7、事件（按事件分级区分）
	 * @param areaId 区域ID
	 * @param name  类型对应名称
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "queryAreaPoint")
	public List<CcmAreaPoint> queryAreaPoint(String type,String areaId,String name) {
		return  ccmMapService.queryAreaPoint(type,areaId,name);
	}



	/**
	 * 宗教地图
	 * @param type
	 * @param model
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "religionshow")
	public GeoJSON religionshow(CcmPlaceReligion ccmPlaceReligion) {
		List<CcmPlaceReligion> ccmBasePlaceList = ccmPlaceReligionService.findList(ccmPlaceReligion);
		// 返回对象
		GeoJSON geoJSON = new GeoJSON();
		List<Features> featureList = new ArrayList<Features>();
		for (CcmPlaceReligion basePlace : ccmBasePlaceList) {
			// 特征,属性
			Features featureDto = new Features();
			Properties properties = new Properties();
			// 1 type 默认不填
			// 2 id 添加
			featureDto.setId(basePlace.getId());
			// 3 properties 展示属性信息
			properties.setName(basePlace.getPlaceName());
			if(basePlace.getReligionType().equals("01")){
				properties.setIcon("jiduj.png");
			} else if(basePlace.getReligionType().equals("02")){
				properties.setIcon("yisilanj.png");
			} else if(basePlace.getReligionType().equals("03")){
				properties.setIcon("foj.png");
			} else if(basePlace.getReligionType().equals("04")){
				properties.setIcon("daoj.png");
			} else if(basePlace.getReligionType().equals("05")){
				properties.setIcon("tianzhuj.png");
			}
			// 点击后展示详细属性值
			Map<String, Object> map_P = new HashMap<String, Object>();
			properties.addInfo(map_P);
			featureList.add(featureDto);
			featureDto.setProperties(properties);
			// 4 geometry 配置参数
			Geometry geometry = new Geometry();
			featureDto.setGeometry(geometry);
			// 点位信息 测试为点
			geometry.setType("Point");
			// 为中心点赋值
			if (!StringUtils.isEmpty(basePlace.getAreaPoint())) {
				// 获取中心点的值
				String[] centpoint = basePlace.getAreaPoint().split(",");
				geoJSON.setCentpoint(centpoint);
				// 添加图形中心点位信息
				properties.setCoordinateCentre(centpoint);
			}
			// 添加具体数据
			// 封装的list
			List<String> Coordinateslist = new ArrayList<>();
			// 当前是否为空如果为空则进行添加空数组 ，否则进行拆分添加数据
			String[] a = (StringUtils.isEmpty(basePlace.getAreaPoint()) ? (",") : basePlace.getAreaPoint()).split(",");
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

		// 添加数据
		geoJSON.setFeatures(featureList);
		// 如果无数据
		if (featureList.size() == 0) {
			return null;
		}
		return geoJSON;
	}
}