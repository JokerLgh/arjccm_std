<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>地图</title>
<script type="text/javascript">
	var ctx = '${ctx}', ctxStatic = '${ctxStatic}';
</script>
<link rel="stylesheet" href="${ctxStatic}/ol/ol.css" type="text/css">
<link
	href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${ctxStatic}/ccm/liveVideo/css/livedemo.css">
<link rel="stylesheet"
	href="${ctxStatic}/ccm/liveVideo/css/video-js.css">
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/js/draw/css/p-ol3.min.css"
	type="text/css">
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/js/draw/css/defaults.css"
	type="text/css">
<link rel="stylesheet" href="${ctxStatic}/modules/map/css/map.css"
	type="text/css">
<link rel="stylesheet" href="${ctxStatic}/modules/map/css/house.css"
	type="text/css">
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css"
	rel="stylesheet" />

<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/ol/ol.js"></script>

<%-- <script src="${ctxStatic}/ccm/liveVideo/js/video.min.js"></script>
<script src="${ctxStatic}/ccm/liveVideo/js/videojs5.flvjs.js"></script>
<script src="${ctxStatic}/ccm/liveVideo/js/videojs-contrib-hls.js"></script>
<script src="${ctxStatic}/ccm/liveVideo/js/videojs-ie8.min.js"></script>
<script src="${ctxStatic}/ccm/liveVideo/js/livedemo.js"></script>
<script type="text/javascript">
	videojs.options.flash.swf = "${ctxStatic}/ccm/liveVideo/js/video-js.swf";
</script> --%>

<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js"
	type="text/javascript"></script>
<script
	src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<script src="${ctxStatic}/modules/map/js/draw/js/p-ol3.debug.js"></script>
<script src="${ctxStatic}/jquery-ui-1.12.1/jquery-ui.min.js"
	type="text/javascript"></script>
<link href="${ctxStatic}/modules/map/css/pop-info-animate.css"
	rel="stylesheet" />
<link href="${ctxStatic}/layer-v3.1.1/layer/theme/default/layer.css"
	rel="stylesheet" />
<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css"
	type="text/css" rel="stylesheet">
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/css/publicinstitutions.css">
<script src="${ctxStatic}/layer-v3.1.1/layer/layer.js"></script>
<link href="${ctxStatic}/layim/layui/css/layui.css" type="text/css"
	rel="stylesheet">
<script src="${ctxStatic}/layim/layui/layui.js"></script>
<script src="${ctxStatic}/modules/map/js/mapconfig.js"></script>
<script src="${ctxStatic}/modules/map/js/commonMap.js"></script>
<script src="${ctxStatic}/modules/map/js/mapBuildSpe.js"></script>
<script src="${ctxStatic}/common/common.js"></script>
<script src="${ctxStatic}/modules/map/js/draw/js/appIndex.js"></script>
<script src="${ctxStatic}/modules/map/js/QianXi.js"></script>
<script src="${ctxStatic}/ccm/sys/js/GISDataIndex.js"></script>
<script src="${ctxStatic}/ccm/sys/js/GISData.js"></script>

<style>
#toolCenter {
	width: 250px;
}

.tab-item {
	display: none
}

.tab-item.active {
	display: block;
}

.tab-title li {
	cursor: pointer;
	height: 13%;
	text-align: center;
	overflow: hidden;
}

.tab-title li.active {
	background: #fff;
}

#contentLeft {
	background: #f0f3f4 !important;
}

#left.mapIndex {
	background: #fff !important;
}

.nav-icon {
	margin: auto;
	margin-right: 2px;
}

.nav-name {
	display: block;
	text-align: center;
	margin-top: -10px;
	color: #a0a0a1;
}

.tab-title li.active .nav-name, .tab-title li.active .nav-icon {
	color: #44a5ff;
}

.input-medium.Wdate, .input-medium {
	width: 158px;
}

.ul-form label {
	margin-left: 22px;
}

.firstbtn {
	margin-left: 6px;
}

#mapKey {
	z-index: 1992;
	display: block;
	opacity: 1;
}

.mapView {
	position: absolute;
	z-index: 199202;
	top: 10px;
	left: 14px;
	width: 350px;
	height: 240px;
	border-radius: 8px;
	background-color: #fff;
	border: 1px solid #ccc;
}

.mapView p {
	margin: 0px;
	color: #FFF;
	background-color: #1491FF;
	text-align: center;
	line-height: 30px;
	font-weight: bold;
}

.mapView .mapView-p-head {
	position: absolute;
	width: 100%;
	background-color: #fff;
}

.mapView .mapView-p-head>.head_first {
	width: 300px;
}

.mapView .mapView-p-head>.head_second {
	position: absolute;
	z-index: 1;
	margin-top: -3px;
	width: 300px;
}

.form-control {
	outline: none !important;
	box-shadow: none !important;
}

.btn .green :not(.btn-outline) {
	color: #fff;
	background-color: #32c5d2;
	border-color: #32c5d2;
}

.mapListTop {
	position: absolute;
	z-index: 199202;
	top: 251px;
	left: 14px;
	width: 340px;
	height: 30px;
	border: 1px solid #ccc;
	border-top-right-radius: 8px;
	border-top-left-radius: 8px;
	padding-top: 7px;
	padding-left: 10px;
	background-color: #fff;
	font-size: 12px;
	opacity: 0.9;
	overflow: hidden;
}

.map_main {
	position: absolute;
	z-index: 199202;
	left: 14px;
	top: 289px;
	width: 350px;
	height: calc(100% - 230px - 57px - 25px - 32px);
	border: 1px solid #ccc;
	background-color: #fff;
	opacity: 0.9;
	overflow: auto;
	border-bottom-right-radius: 8px;
	border-bottom-left-radius: 8px;
}

.map_list_data {
	min-height: 85px;
	border-bottom: 1px solid #e5e5e5;
	font-size: 12px;
	color: #8c8c8c;
	cursor: pointer;
}

.col-center {
	margin-right: 90px;
	margin-left: 30px !important;
}

.col-center .col-row {
	text-align: left;
	padding: 3px 3px;
	line-height: 1.2;
	min-height: 25px;
	color: #666667;
	box-sizing: border-box;
}

.col-left {
	float: left;
}

.col-right {
	float: right;
	width: 75px;
	margin: 10px 10px;
	text-align: right;
}

#showMapKey {
	position: absolute;
	z-index: 2;
	top: 10px;
	left: 14px;
	width: 40px;
	height: 32px;
	background-color: #fff;
	color: #fff;
	cursor: pointer;
	border-radius: 5px;
	line-height: 32px;
	text-align: center;
	box-sizing: border-box;
	box-shadow: 1px 2px 1px rgba(0, 0, 0, .15);
}

#showMapKey img {
	margin-top: 4px;
}

.layui-tab-title li {
	padding: 0 5px;
}

.layui-tab-title li {
	min-width: 60px;
}

.layui-tab {
	margin: 0px;
}

.layui-tab-content {
	padding: 0 10px;
}

.col-center .col-row .n-blue {
	color: #1972c1;
	text-decoration: none;
	outline: 0;
	cursor: pointer;
	font-weight: bold;
}

.map_list_data:hover {
	background-color: #efefef;
}

.mainBottom {
	position: absolute;
	z-index: 199301;
	left: 14px;
	bottom: 21px;
	width: 350px;
	height: 30px;
	border: 1px solid #ccc;
	border-bottom-right-radius: 8px;
	border-bottom-left-radius: 8px;
	background-color: #fff;
	font-size: 12px;
	opacity: 0.9;
	overflow: hidden;
}

.layui-laypage {
	margin: 0;
}
#myTabContent table td {
	text-align: left;
}
#pageList .layui-box>a,#pageList .layui-box>span{
	background: #092D52;
	color: #fff;
}
</style>

</head>
<body style="overflow: hidden;" onload="init()">

	<div id="content" class="row-fluid">

		<div id="right" style="overflow: hidden;">
			<div id="showMapKey" style="width: 0px; height: 0px; display: none;">
				<img src="${ctxStatic}/images/GIS_list.png">
			</div>
			<div id="mapKey">
				<div class="mapView">
					<p id="mapView-p">
						GIS数据<span
							style="float: right; font-size: 20px; padding-right: 10px; color: #FFF; cursor: pointer;">×</span>
					</p>
					<div class="mapView-p-head">
						<div class="layui-tab layui-tab-brief" lay-filter="allTab">
							<ul class="layui-tab-title" id="GIStab">
								<li class="layui-this" type="event"><i
									class="nav-icon iconfont  icon-shishibaojing1"></i>事件</li>
								<li type="build"><i
									class="nav-icon iconfont  icon-loupandizhiku"></i>房屋</li>
								<li type="place"><i
									class="nav-icon iconfont   icon-changsuoguanli"></i>场所</li>
								<li type="people"><i
									class="nav-icon iconfont   icon-renkouguanli"></i>人口</li>
								<li type="video"><i
									class="nav-icon iconfont   icon-shipinjiankong"></i>监控</li>
							</ul>
							<div class="layui-tab-content">
								<div class="layui-tab-item layui-show">
									<ul class="ul-form">
										<li><input placeholder="请选择开始时间"
											name="beginHappenDateEvent" id="beginHappenDateEvent"
											type="text" maxlength="20" class="input-medium Wdate"
											value=""
											onclick="WdatePicker({maxDate: '#F{$dp.$D(\'endHappenDateEvent\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
											style="height: 30px; margin-top: 10px;"> - <input
											placeholder="请选择结束时间" name="endHappenDateEvent"
											id="endHappenDateEvent" type="text" maxlength="20"
											class="input-medium Wdate" value=""
											onclick="WdatePicker({minDate:'#F{$dp.$D(\'beginHappenDateEvent\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
											style="height: 30px; margin-top: 10px;"></li>
										<li><sys:treeselect id="areaEvent" name="areaEvent"
												value="" labelName="area.name" placeholder="请选择辖区范围 ..."
												labelValue="" title="区域" url="/sys/area/treeData"
												cssClass="" allowClear="true" notAllowSelectParent="false"
												cssStyle="width: 111px;height:30px;" /> &nbsp; <input
											id="caseName" placeholder="请输入事件名称 ..." name="caseName"
											class="input-medium" type="text" value="" maxlength="100"
											style="height: 30px; margin-top: 0px; margin-bottom: 10px;">
										</li>
										<li>
											<a href="javascript:;" style="margin-left: 10px;"
											   onclick="selectQuery('Circle','event')"> <img
													src="${ctxStatic}/images/draw_circle2.png"  title="点圆查询"
													style="height: 30px;"></a> <%--					<i class="icon-search"></i> 点圆查询 </a>--%>
											<a href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Box','event')">
												<img src="${ctxStatic}/images/draw_rect2.png" title="拉框查询"
													 style="height: 30px;">
											</a> <%--					<i class="icon-search"></i> 拉框查询 </a>--%> <a
												href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Polygon','event')">
											<img src="${ctxStatic}/images/draw_poly2.png" title="多边形查询"
												 style="height: 30px;">
										</a> <%--					<i class="icon-search"></i> 多边形查询 </a>--%>
											<a href="javascript:;" id="btnSubmitEvent" style="margin-left: 15px;margin-right: 10px"
											class="btn btn-primary firstbtn"> <i class="icon-search"></i>
												查询
										</a> <a href="javascript:;" id="ClearSubmitEvent"
											style="background: #f50b4b !important;"
											class="btn btn-primary"> <i class="icon-remove"></i> 清空
										</a> </li>
									</ul>
								</div>
								<div class="layui-tab-item">
									<ul class="ul-form">
										<li><input id="buildaddress" placeholder="请输入楼栋地址 ..."
											name="buildaddress" class="input-medium" type="text" value=""
											maxlength="100" style="height: 30px; margin-top: 10px;">
											&nbsp; <input id="buildPname" placeholder="请输入楼栋长 ..."
											name="buildPname" class="input-medium" type="text" value=""
											maxlength="100" style="height: 30px; margin-top: 10px;">
										</li>
										<li><input id="buildname" placeholder="请输入楼栋名称 ..."
											name="buildname" class="input-medium" type="text" value=""
											maxlength="100"
											style="height: 30px; margin-top: 0px; margin-bottom: 10px;">
											&nbsp; <sys:treeselect id="areaBuild" name="areaBuild"
												value="" labelName="area.name" placeholder="请选择辖区范围 ..."
												labelValue="" title="区域" url="/sys/area/treeData"
												cssClass="" allowClear="true" notAllowSelectParent="false"
												cssStyle="width: 110px;height:30px;" /></li>
										<li>
											<a href="javascript:;" style="margin-left: 10px;"
											   onclick="selectQuery('Circle','build')"> <img
													src="${ctxStatic}/images/draw_circle.png"  title="点圆查询"
													style="height: 30px;"></a> <%--					<i class="icon-search"></i> 点圆查询 </a>--%>
											<a href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Box','build')">
												<img src="${ctxStatic}/images/draw_rect.png" title="拉框查询"
													 style="height: 30px;">
											</a> <%--					<i class="icon-search"></i> 拉框查询 </a>--%> <a
												href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Polygon','build')">
											<img src="${ctxStatic}/images/draw_poly.png" title="多边形查询"
												 style="height: 30px;">
										</a>
											<a href="javascript:;" id="btnSubmitBuild" style="margin-left: 15px; margin-right: 10px;"
											class="btn btn-primary firstbtn"> <i class="icon-search"></i>
												查询
										</a> <a href="javascript:;" id="ClearSubmitBuild"
											style="background: #f50b4b !important;"
											class="btn btn-primary"> <i class="icon-remove"></i> 清空
										</a> <%--					<i class="icon-search"></i> 多边形查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Polygon','build')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 多边形查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Circle','build')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 点圆查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Box','build')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 拉框查询 </a>--%></li>
									</ul>
								</div>
								<div class="layui-tab-item">
									<ul class="ul-form">
										<!--  <li>
			<input id="keyPointList" placeholder="请选择重点属性 " name="keyPointList" class="input-medium" type="text" value="" maxlength="100" style="height: 30px;margin-top: 10px;">
 		     &nbsp;
			<input id="placeType" placeholder="请选择场所类型 " name="placeType" class="input-medium" type="text" value="" maxlength="100" style="height: 30px;margin-top: 10px; ">
			</li> -->
										<li style="margin-top: 10px;"><input id="placeName"
											placeholder="请输入场所名称 ..." name="placeName"
											class="input-medium" type="text" value="" maxlength="100"
											style="height: 30px; margin-top: 0px; margin-bottom: 10px;">
											&nbsp; <sys:treeselect id="areaPlace" name="areaPlace"
												value="" labelName="area.name" placeholder="请选择辖区范围 ..."
												labelValue="" title="区域" url="/sys/area/treeData"
												cssClass="" allowClear="true" notAllowSelectParent="false"
												cssStyle="width: 110px;height:30px;" /></li>
										<li>
											<a href="javascript:;" style="margin-left: 10px;"
											   onclick="selectQuery('Circle','place')"> <img
													src="${ctxStatic}/images/draw_circle.png"  title="点圆查询"
													style="height: 30px;"></a> <%--					<i class="icon-search"></i> 点圆查询 </a>--%>
											<a href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Box','place')">
												<img src="${ctxStatic}/images/draw_rect.png" title="拉框查询"
													 style="height: 30px;">
											</a> <%--					<i class="icon-search"></i> 拉框查询 </a>--%> <a
												href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Polygon','place')">
											<img src="${ctxStatic}/images/draw_poly.png" title="多边形查询"
												 style="height: 30px;">
										</a>
											<a href="javascript:;" id="btnSubmitPlac" style="margin-left: 15px; margin-right: 10px;"
											class="btn btn-primary firstbtn"> <i class="icon-search"></i>
												查询
										</a> <a href="javascript:;" id="ClearSubmitPlace"
											style="background: #f50b4b !important;"
											class="btn btn-primary"> <i class="icon-remove"></i> 清空
										</a> <%--				<a href="javascript:;" onclick="selectQuery('Polygon','place')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 多边形查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Circle','place')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 点圆查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Box','place')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 拉框查询 </a>--%></li>
									</ul>
								</div>
								<div class="layui-tab-item">
									<ul class="ul-form">
										<li><select id="pType" name="pType"
											placeholder="请选择人员类型 " class="input-medium"
											style="margin-bottom: 0">
												<option value="" selected="selected">全部</option>
												<!-- <option value="10">户籍</option>
					<option value="20">流动</option>
					<option value="30">境外</option>
					<option value="40">未落户</option> -->
										</select> <!-- 			<input id="pType"  name="pType" class="input-medium" type="text" value="" maxlength="100" style="height: 30px;margin-top: 10px;">
 --> &nbsp; <input id="pIdent" placeholder="请输入身份证号  ..." name="pIdent"
											class="input-medium" type="text" value="" maxlength="100"
											style="height: 30px; margin-top: 10px;"></li>
										<li><input id="pName" placeholder="请输入姓名 ..."
											name="pName" class="input-medium" type="text" value=""
											maxlength="100"
											style="height: 30px; margin-top: 0px; margin-bottom: 10px;">

											&nbsp; <sys:treeselect id="areaPeople" name="areaPeople"
												value="" labelName="area.name" placeholder="请选择辖区范围 ..."
												labelValue="" title="区域" url="/sys/area/treeData"
												cssClass="" allowClear="true" notAllowSelectParent="false"
												cssStyle="width: 111px;height:30px;" /></li>
										<li><select id="importantType" name="importantType"
											placeholder="请选择重点人员类型 " class="input-medium"
											style="margin-bottom: 0">
												<option value="" selected="selected">全部</option>
										</select></li>
										<li style="margin-top: 10px;">
											<a href="javascript:;" style="margin-left: 10px;"
											   onclick="selectQuery('Circle','people')"> <img
													src="${ctxStatic}/images/draw_circle.png"  title="点圆查询"
													style="height: 30px;"></a> <%--					<i class="icon-search"></i> 点圆查询 </a>--%>
											<a href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Box','people')">
												<img src="${ctxStatic}/images/draw_rect.png" title="拉框查询"
													 style="height: 30px;">
											</a> <%--					<i class="icon-search"></i> 拉框查询 </a>--%> <a
												href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Polygon','people')">
											<img src="${ctxStatic}/images/draw_poly.png" title="多边形查询"
												 style="height: 30px;">
										</a>
											<a href="javascript:;" id="btnSubmitPeople" style="margin-left: 15px;margin-right: 10px;"
											class="btn btn-primary firstbtn"> <i class="icon-search"></i>
												查询
										</a> <a href="javascript:;" id="ClearSubmitPeople"
											style="background: #f50b4b !important;"
											class="btn btn-primary"> <i class="icon-remove"></i> 清空
										</a>  <%--				<a href="javascript:;" onclick="selectQuery('Polygon','people')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 多边形查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Circle','people')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 点圆查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Box','people')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 拉框查询 </a>--%></li>
									</ul>
								</div>
								<div class="layui-tab-item">
									<ul class="ul-form">
										<li><input id="videoIP" placeholder="请输入IP地址 ... "
											name="videoIP" class="input-medium" type="text" value=""
											maxlength="100" style="height: 30px; margin-top: 10px;">
											&nbsp; <input id="videoName" placeholder="请输入名称  ..."
											name="videoName" class="input-medium" type="text" value=""
											maxlength="100" style="height: 30px; margin-top: 10px;">
										</li>
										<li><sys:treeselect id="areaVideo" name="areaVideo"
												value="" labelName="area.name" placeholder="请选择辖区范围 ..."
												labelValue="" title="区域" url="/sys/area/treeData"
												cssClass="" allowClear="true" notAllowSelectParent="false"
												cssStyle="width: 110px;height:30px;" /> &nbsp;</li>
										<li>
											<a href="javascript:;" style="margin-left: 10px;"
											   onclick="selectQuery('Circle','video')"> <img
													src="${ctxStatic}/images/draw_circle.png"  title="点圆查询"
													style="height: 30px;"></a> <%--					<i class="icon-search"></i> 点圆查询 </a>--%>
											<a href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Box','video')">
												<img src="${ctxStatic}/images/draw_rect.png" title="拉框查询"
													 style="height: 30px;">
											</a> <%--					<i class="icon-search"></i> 拉框查询 </a>--%> <a
												href="javascript:;" style="margin-left: 10px;" onclick="selectQuery('Polygon','video')">
											<img src="${ctxStatic}/images/draw_poly.png" title="多边形查询"
												 style="height: 30px;">
										</a>
											<a href="javascript:;" id="btnSubmitVideo" style="margin-left: 15px;margin-right: 10px;"
											class="btn btn-primary firstbtn"> <i class="icon-search"></i>
												查询
										</a> <a href="javascript:;" id="ClearSubmitVideo"
											style="background: #f50b4b !important;"
											class="btn btn-primary"> <i class="icon-remove"></i> 清空
										</a> <%--				<a href="javascript:;" onclick="selectQuery('Polygon','video')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 多边形查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Circle','video')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 点圆查询 </a>--%> <%--				<a href="javascript:;" onclick="selectQuery('Box','video')" style="background: #f50b4b !important;" class="btn btn-primary">--%>
											<%--					<i class="icon-search"></i> 拉框查询 </a>--%></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--列表部分-->
				<div class="mapListTop">
					已查询到<span style="color: #836FFF" id="pageNum">0</span>条数据
				</div>
				<div class="map_main">
					<div style="padding: 8px; height: 97%;">
						<div style="height: 100%;" id="IncidentList">
							<ul style="height: 100%;" class="datalist" id="datalist">
								<!-- 			  <li><div class="map_list_data" onclick=""><div class="col-left"><a class="new-no-0" href="javascript:;"></a></div><div class="col-center" style="margin-right: 50px;"><div class="col-row"> <span class="n-blue">事件测试111</span></div><div class="col-row">浙江省杭州市西湖区文三路496杭州银行西苑支行西北80米</div><div class="col-row">2017-11-14 10:05:34</div></div></div></li>
             -->
							</ul>
						</div>
					</div>
				</div>
				<div class="mainBottom">
					<div id="pageList" style="width: 365px"></div>
				</div>
			</div>

			<div id="FullBody" style="position: relative;">

				<!--  工具栏-->
				<div id="tool">
					<div id="toolCenter">
						<div class="row-fluid tool-container">
							<div class="span3 tool-list" id="DrawFlag" style="width: 27%;">
								<i class="tool-icon tool-draw"></i><span>标绘</span> <i
									class="tool-icon tool-arrow-up"></i> <b class="tool-gap"></b>
							</div>
							<div class="span2  tool-list"
								onclick="Map.selectQuery('Polygon')" style="width: 23%;"
								id="switchMap">
								<i class="tool-icon tool-select"></i><span>框选</span> <b
									class="tool-gap"></b>
							</div>
							<div class="span2  tool-list" onclick="Map.switchMap()"
								style="width: 23%;" id="switchMap">
								<i class="tool-icon tool-map"></i><span>切换</span> <b
									class="tool-gap"></b>
							</div>
							<div class="span2 tool-list" onclick="Map.fullScreen()"
								style="width: 20%;" id="fullScreen">
								<i class="tool-icon tool-full"></i><span>全屏</span>
							</div>
						</div>
					</div>
					<div class="detail-box" id="toolDetailBox">
						<ul id="boxul" class="boxinfo">
							<li class="clearfix tool-list" onclick="PointBox()"><i
								class="tool-icon tool-point"></i><span>点标</span></li>
							<li class="clearfix tool-list" onclick="LineBox()"><i
								class="tool-icon tool-line"></i><span>线标</span></li>
							<li class="clearfix tool-list" onclick="PolygonBox()"><i
								class="tool-icon tool-polygon"></i><span>面标</span></li>
							<li class="clearfix tool-list" onclick="ArrowBox()"><i
								class="tool-icon tool-arrow"></i><span>箭头</span></li>
							<li class="clearfix tool-list" onclick="TextBox()"><i
								class="tool-icon tool-text"></i><span>文字</span></li>

						</ul>
					</div>
					<!-- 点 -->
					<div class="tag-panl" id="PointBox">
						<div class="row-fluid tag-panl-header">
							<div class="span10">
								<span style="margin-left: 5px">标绘</span>
							</div>
							<div class="span2 tag-panl-close">
								<i class="icon-remove"></i>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">
							<div class="span4"></div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="点标绘"
									onclick="activate(P.PlotTypes.MARKER)"> <i
									class="tool-icon  tool-point"></i>
								</span>

							</div>
							<div class="span4"></div>
						</div>
					</div>
					<!-- 线 -->
					<div class="tag-panl" id="LineBox">
						<div class="row-fluid tag-panl-header">
							<div class="span10">
								<span style="margin-left: 5px">标绘</span>
							</div>
							<div class="span2 tag-panl-close">
								<i class="icon-remove"></i>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="弧线标绘"
									onclick="activate(P.PlotTypes.ARC)"> <i
									class="tool-icon ">弧</i>
								</span>

							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="曲线标绘"
									onclick="activate(P.PlotTypes.CURVE)"> <i
									class="tool-icon">曲</i>
								</span>
							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="折线标绘"
									onclick="activate(P.PlotTypes.POLYLINE)"> <i
									class="tool-icon">折</i>
								</span>
							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="自由线标绘"
									style="margin-left: -3px;"
									onclick="activate(P.PlotTypes.FREEHAND_LINE)"> <i
									class="tool-icon">自</i>
								</span>
							</div>
						</div>
					</div>
					<!-- 面 -->
					<div class="tag-panl" id="PolygonBox">
						<div class="row-fluid tag-panl-header">
							<div class="span10">
								<span style="margin-left: 5px">标绘</span>
							</div>
							<div class="span2 tag-panl-close">
								<i class="icon-remove"></i>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="圆标绘"
									onclick="activate(P.PlotTypes.CIRCLE)"> <i
									class="tool-icon ">圆</i>
								</span>

							</div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="椭圆标绘"
									onclick="activate(P.PlotTypes.ELLIPSE)"> <i
									class="tool-icon">椭</i>
								</span>
							</div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="弓形标绘"
									onclick="activate(P.PlotTypes.LUNE)"> <i
									class="tool-icon">弓</i>
								</span>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="扇形标绘"
									style="margin-left: -3px;"
									onclick="activate(P.PlotTypes.SECTOR)"> <i
									class="tool-icon">扇</i>
								</span>
							</div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="曲线面标绘"
									onclick="activate(P.PlotTypes.CLOSED_CURVE)"> <i
									class="tool-icon ">曲</i>
								</span>

							</div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="多边形标绘"
									onclick="activate(P.PlotTypes.POLYGON)"> <i
									class="tool-icon">多</i>
								</span>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">

							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="矩形标绘"
									onclick="activate(P.PlotTypes.RECTANGLE)"> <i
									class="tool-icon">矩</i>
								</span>
							</div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="自由面标绘"
									onclick="activate(P.PlotTypes.FREEHAND_POLYGON)"> <i
									class="tool-icon">自</i>
								</span>
							</div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="聚集地标绘"
									onclick="activate(P.PlotTypes.GATHERING_PLACE)"> <i
									class="tool-icon">聚</i>
								</span>
							</div>
						</div>
					</div>
					<!-- 箭头 -->
					<div class="tag-panl" id="ArrowBox">
						<div class="row-fluid tag-panl-header">
							<div class="span10">
								<span style="margin-left: 5px">标绘</span>
							</div>
							<div class="span2 tag-panl-close">
								<i class="icon-remove"></i>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="钳击标绘"
									onclick="activate(P.PlotTypes.DOUBLE_ARROW)"> <i
									class="tool-icon ">钳</i>
								</span>

							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="直箭头圆标绘"
									onclick="activate(P.PlotTypes.STRAIGHT_ARROW)"> <i
									class="tool-icon">直</i>
								</span>
							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="细直箭头标绘"
									onclick="activate(P.PlotTypes.FINE_ARROW)"> <i
									class="tool-icon">细</i>
								</span>
							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="突击方向标绘"
									style="margin-left: -3px;"
									onclick="activate(P.PlotTypes.ASSAULT_DIRECTION)"> <i
									class="tool-icon">突</i>
								</span>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">

							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="进攻方向面标绘"
									onclick="activate(P.PlotTypes.ATTACK_ARROW)"> <i
									class="tool-icon ">进</i>
								</span>

							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="进攻方向（尾）标绘"
									onclick="activate(P.PlotTypes.TAILED_ATTACK_ARROW)"> <i
									class="tool-icon">尾</i>
								</span>
							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="分队战斗行动标绘"
									onclick="activate(P.PlotTypes.SQUAD_COMBAT)"> <i
									class="tool-icon">分</i>
								</span>
							</div>
							<div class="span3">
								<span class="tag-panl-polygon tag-panl-span" title="分队战斗行动（尾）标绘"
									onclick="activate(P.PlotTypes.TAILED_SQUAD_COMBAT)"> <i
									class="tool-icon">尾</i>
								</span>
							</div>
						</div>
					</div>

					<!-- 文字  -->
					<div class="tag-panl" id="TextBox">
						<div class="row-fluid tag-panl-header">
							<div class="span10">
								<span style="margin-left: 5px">文字</span>
							</div>
							<div class="span2 tag-panl-close">
								<i class="icon-remove"></i>
							</div>
						</div>
						<div class="row-fluid tag-panl-center">
							<div class="span4"></div>
							<div class="span4">
								<span class="tag-panl-polygon tag-panl-span" title="文字标绘"
									onclick="Map.drawText(true)"> <i
									class="tool-icon  tool-text"></i>
								</span>

							</div>
							<div class="span4"></div>
						</div>
					</div>

					<!-- 图层列表 -->
					<!-- <div class="tag-panl" id="LayersBox"
				style="min-width: 250px; width: 250px;">
				<div class="row-fluid tag-panl-header">
					<div class="span10">
						<span style="margin-left: 5px">图层列表</span>
					</div>
					<div class="span2 tag-panl-close">
						<i class="icon-remove"></i>
					</div>
				</div>
				<div class="row-fluid tag-panl-center">
					<div class="span1"></div>
					<div class="span11">
						<ul id="tree" class="ztree"></ul>
					</div>
				</div>
			</div> -->
					<!-- 图层列表 -->
				</div>
				<!-- 工具栏 -->
				<div id="delete-wrapper">
					<div id="btn-delete" style="display: none;">删 除</div>
				</div>
				<!--  工具栏-->
				<div id="map" class="map" tabindex="0">

					<div id="detailsDialog"></div>
				</div>

				<div id="popup" class="ol-popup">
					<div class="">
						<a href="#" id="popup-closer"
							class="ol-popup-closer  icon-remove " title="关闭"
							onmouseover="$(this).addClass('jbox-close-hover');"
							onmouseout="$(this).removeClass('jbox-close-hover');"
							style="position: absolute; display: block; cursor: pointer; top: 4px; right: 11px; width: 15px; height: 15px; color: #666"></a>
						<div class="jbox-title-panel" style="height: 25px;">
							<div class="jbox-title">信息</div>
						</div>
						<div class="jbox-state">
							<div id="popup-content" style="padding: 8px 15px;"></div>
						</div>
					</div>
				</div>
				<div id="pubMap"></div>

				<!-- 楼栋住户信息 -->
				<button type="button" data-toggle="modal" data-target="#myModal"
					id="buildBtn" style="display: none"></button>
				<div id="myModal" class="modal hide fade jbox" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
					style="width: 1300px; margin-left: -30%;z-index: 1993;">
					<div class="jbox-container">
						<a href="#" id="popup-closer" class="ol-popup-closer jbox-close"
							title="关闭" onmouseover="$(this).addClass('jbox-close-hover');"
							onmouseout="$(this).removeClass('jbox-close-hover');"
							style="position: absolute; display: block; cursor: pointer; top: 4px; right: 11px; width: 15px; height: 15px;"
							data-dismiss="modal" aria-hidden="true"></a>
						<div class="jbox-title-panel" style="height: 25px;">
							<div class="jbox-title">信息</div>
						</div>
						<!-- 楼栋 -->
						<div class="jbox-state">
							<div id="popup-content">
								<div class="modal-body" id="build-details"
									style="padding: 3px 0px 0 0;"></div>
								<!-- 房屋 -->
								<div class="modal-body" id="house-details"
									style="padding: 3px 15px;"></div>
								<!--人口-->
								<div class="modal-body" id="pop-details"></div>
							</div>
							<div class="jbox-button-panel"
								style="height: 4px; padding: 0 15px 20px; text-align: right;"
								id="modal-footer">
								<button class="jbox-button" style="padding: 0px 10px 0px 10px;"
									data-dismiss="modal" aria-hidden="true">关闭</button>
							</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		
		<div id="pubMap" ></div> 
		
		<%-- <!-- 公共机构 -->
		<div class="unfold" style="bottom:3px; left:368px">
			<div class="relevance-bg" style="display: block;">
				<div class="br-yuan"></div>
			</div>
			<div class="relevance"
				style="width: 0; height: 0;">
				<div class="re-header">
					<div class="re-close"></div>
				</div>
				<div class="re-center clearfix">
					<div>
						<!--学校  -->
						<div class="pub-flag" onclick="xuexiaoFun(this)">
							<span class="pub-icon icon-xuexiao"></span> <span
								class="pub-name">学校</span>
						</div>
						<!-- 医院 -->
						<div class="pub-flag" onclick="yiyuanFun(this)">
							<span class="pub-icon icon-yiyuan"></span> <span class="pub-name">医院</span>
						</div>
						<!-- 加油站 -->
						<div class="pub-flag" onclick="jiayouzhanFun(this)">
							<span class="pub-icon icon-jiayouzhan"></span> <span
								class="pub-name">加油站</span>
						</div>
						<!-- 商场超市 -->
						<div class="pub-flag" onclick="shangchangFun(this)">
							<span class="pub-icon icon-shangchang"></span> <span
								class="pub-name">商场超市</span>
						</div>
						<!-- 娱乐场所 -->
						<div class="pub-flag" onclick="yuleFun(this)">
							<span class="pub-icon icon-yule"></span> <span class="pub-name">娱乐场所</span>
						</div>
						<!-- 宾馆 -->
						<div class="pub-flag" onclick="binguanFun(this)">
							<span class="pub-icon icon-bingguan"></span> <span
								class="pub-name">酒店宾馆</span>
						</div>
						<!-- 涉危涉爆单位 -->
						<div class="pub-flag" onclick="sheweishebaoFun(this)">
							<span class="pub-icon icon-sheweishebao"></span> <span
								class="pub-name">涉危涉爆</span>
						</div>

						<!-- 视频监控 -->
						<div class="pub-flag" onclick="shipinjiankongFun(this)" id="VideoFlag">
							<span class="pub-icon icon-shipins"></span> <span class="pub-name">视频监控</span>
						</div>
						<!-- 警员-->
						<div class="pub-flag" onclick="jingyuanFun(this)">
							<span class="pub-icon icon-jingyuan"></span> <span
								class="pub-name">工作人员</span>
						</div>
						<c:if test="${sysConfig.objId eq 'xinmishi'}">
						<!-- 警务室-->
						<div class="pub-flag" onclick="jingwushiFun(this)">
							<span class="pub-icon icon-jingwushi"></span> <span
								class="pub-name">警务室</span>
						</div>
						<!-- 工作站-->
						<div class="pub-flag" onclick="gongzuozhanFun(this)">
							<span class="pub-icon icon-gongzuozhan"></span> <span
								class="pub-name">工作站</span>
						</div>
						<!-- 广播站-->
						<div class="pub-flag" onclick="guangbozhanFun(this)">
							<span class="pub-icon icon-guangbozhan"></span> <span
								class="pub-name">广播站</span>
						</div>
						<!-- 警车-->
						 <!-- <div class="pub-flag" onclick="jingcheFun(this)">
							<span class="pub-icon icon-jingche"></span> <span
								class="pub-name">警车</span>
						</div>  -->
						</c:if>
						<!-- 机顶盒" -->
						<div class="pub-flag" onclick="SetTopBoxFun(this)">
							<span class="pub-icon icon-Settopbox"></span> <span
								class="pub-name">机顶盒</span>
						</div>
					</div>
				</div>
			</div>
		</div> 

		<!-- 公共机构 --> --%>
	</div>
	</div>    
     <!-- /content -->
    </div>


	<script type="text/javascript">
		// 人员定位
		var leftWidth = 0; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right, #right iframe");

		function wSize() {
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({
				"overflow-x" : "auto",
				"overflow-y" : "auto"
			});
			mainObj.css("width", "auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width(
					$("#content").width() - leftWidth - $("#openClose").width()
							- 5);

		}
		function wSizeWidth() {
			if (!$("#openClose").is(":hidden")) {
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left")
						.width());
				$("#right").width(
						$("#content").width() - leftWidth
								- $("#openClose").width() - 4);
			} else {
				$("#right").width("100%");
			}
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b) {
			$.fn.jerichoTab.resize();
		} // </c:if>
		
	    /*布局改变*/
 	    $(".relevance-bg").click(function(){
	        $(".relevance").animate({"width":"400px","height":"150px"},300)
	        $(".relevance-bg").hide();
	        $('.unfold').css('bottom','3px');
	        $('.unfold').css('left','368px')
	        $('.map-2').hide();
	        $('.map-4').show();
	    })
	    $(".re-close").click(function(){
	        $(".relevance").animate({"width":0,"height":0},300)
	        $(".relevance-bg").show()
	        $('.unfold').css('bottom','3px');
	        $('.unfold').css('left','368px')
	        $('.map-4').hide();
	        $('.map-2').show();
	    }) 
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
		<div id="innerdiv" style="position:absolute;">
			<img id="bigimg" style="border:5px solid #fff;" src="" />
		</div>
	</div>

</body>
</html>