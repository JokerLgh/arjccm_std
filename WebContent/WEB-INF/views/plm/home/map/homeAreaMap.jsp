<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%--<%@ include file="/WEB-INF/views/include/head.jsp"%>--%>
<html>
<head>
<meta charset="UTF-8">
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
<%--<script src="${ctxStatic}/modules/map/js/draw/js/p-ol3.debug.js"></script>--%>
<script src="${ctxStatic}/jquery-ui-1.12.1/jquery-ui.min.js"
	type="text/javascript"></script>
<link href="${ctxStatic}/modules/map/css/pop-info-animate.css"
	rel="stylesheet" />
<link href="${ctxStatic}/layer-v3.1.1/layer/theme/default/layer.css"
	rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/css/publicinstitutions.css">
<script src="${ctxStatic}/layer-v3.1.1/layer/layer.js"></script>
<script src="${ctxStatic}/modules/map/js/mapconfig.js"></script>
<script src="${ctxStatic}/modules/map/js/commonMap.js"></script>
<script src="${ctxStatic}/modules/map/js/QianXi.js"></script>
<script src="${ctxStatic}/common/home/js/homeMapIndex.js"></script>
<%--<script src="${ctxStatic}/modules/map/js/draw/js/appIndex.js"></script>--%>
<script src="${ctxStatic}/modules/map/js/mapBuildSpe.js"></script>

<style>
#tool {
	right: 71px;
}
#toolCenter {
	width: 250px;
}
#DrawFlag .tool-gap {
	left: -1px;
}
.layer-common {
	width: 100%;
	height: 100%;
	position: relative;
	padding: 10px;
}
.layer-common-header {
	display: inline-block;
	padding: 5px 30px;
	border: 1px solid #0343a3;
	transform: skew(-20deg);
	background: #0343a3;
	color: #fff;
	font-weight: bold;
	position: absolute;
	z-index: 9999;
}
.layer-show {
	border: 1px solid #10559a;
}

.ztree li span{
  max-width:200px;
}
#right{
   height: 100%;
   width: 100%;
}
#content{
margin-top: 8px;
}
</style>

</head>
<body  style="overflow: hidden; ">

      <div  id="treeLeft" class="ztree" style="overflow: auto; float: left; width: 100px;height: 100px;margin-bottom: 2px ; display: none;"> </div>

    <div id="content" class="row-fluid " style=" height:${porheight}px; width:100%;">


     <div   onload="init()" id="right" style="overflow: hidden; ">
	<!-- 人员定位信息  -->
	<input type="hidden" value="${areaPoint}" id="areaPoint" />
	<input type="hidden" value="${ccmPeople.name}" id="ccmPeopleName" />
	<input type="hidden" value="${ccmPeople.sex}" id="ccmPeopleSex" />
	<input type="hidden" value="${ccmPeople.birthday}"
		id="ccmPeopleBirthday" />
	<input type="hidden" value="${ccmPeople.areaComId}"
		id="ccmPeopleareaComId" />
	<input type="hidden" value="${ccmPeople.areaGridId}"
		id="ccmPeopleareaGridId" />
	<input type="hidden" value="${ccmPeople.ident}" id="ccmPeopleIdent" />
	<input type="hidden" value="${ccmPeople.residencedetail}"
		id="ccmPeopleresidencedetail" />
	<!-- 人员定位信息   -->
	<!-- 案事件定位信息 -->
	<input type="hidden" value="${areaPointIncident}" id="AlarmAreaPoint" />
	<input type="hidden" value="${ccmEventIncidentAll.caseName}"
		id="ccmEventIncidentCaseName" />
	<input type="hidden" value="${ccmEventIncidentAll.culName}"
		id="ccmEventIncidentCulName" />
	<input type="hidden" value="${ccmEventIncidentAll.happenDate}"
		id="ccmEventIncidentHappenDate" />
	<input type="hidden" value="${ccmEventIncidentAll.casePlace}"
		id="ccmEventIncidentCasePlace" />
	<input type="hidden" value="${ccmEventIncidentAll.happenPlace}"
		id="ccmEventIncidentHappenPlace" />
	<input type="hidden" value="${ccmEventIncidentAll.caseCondition}"
		id="ccmEventIncidentCaseCondition" />
    <input type="hidden" value='${geoJSONIncidentBuildmanage}'
		id="geoJSONIncidentBuildmanage"/>
	<input type="hidden" value='${geoJSONIncidentCcmDevice}'
		id="geoJSONIncidentCcmDevice"/>

	<!-- 案事件定位信息 -->

	<!-- 今日案事件定位信息 -->

	<div id="layEventIncident" attrName="${caseName}"
		attrCoordinates="${areaPointIncidentMap}" attrid="${eventIncidentId}"
		attrccmEventIncident='${ccmEventIncident}' happenDate="${happenDate}"
		style="display: none"></div>


	<input type="hidden" value="${caseName}" id="TodayCaseName" />
	<input type="hidden" value="${eventIncidentId}" id="eventIncidentId" />
	<input type="hidden" value="${ccmEventIncident.culName}"
		id="TodayCulName" />
	<input type="hidden" value="${ccmEventIncident.happenDate}"
		id="TodayHappenDate" />
	<input type="hidden" value="${ccmEventIncident.casePlace}"
		id="TodayCasePlace" />
	<input type="hidden" value="${ccmEventIncident.happenPlace}"
		id="TodayHappenPlace" />
	<input type="hidden" value="${ccmEventIncident.caseCondition}"
		id="TodayCaseCondition" />
	<input type="hidden" value="${ccmEventIncident.file1}" id="TodatFile" />
	<input type="hidden" value="${ccmEventIncident.status}"
		id="TodatStatus" />
	<input type="hidden" value="${ccmEventIncident.eventScale}"
		id="TodatEventScale" />
	<input type="hidden" value="${ccmPeopleIncident.name}"
		id="ccmPeopleIncidentName" />
	<input type="hidden" value="${ccmPeopleIncident.sex}"
		id="ccmPeopleIncidentSex" />
	<input type="hidden" value="${ccmPeopleIncident.birthday}"
		id="ccmPeopleIncidentBirthday" />
	<input type="hidden" value="${ccmPeopleIncident.images}"
		id="ccmPeopleIncidentImages" />
	<!--视频id  -->
	<input type="hidden" value="${ccmDeviceIncident.id}"
		id="ccmDeviceIncidentId" />


	<!--网格信息  -->
	<input type="hidden" value="${areaIncident.name}" id="areaIncidentName" />
	<input type="hidden" value="${ccmOrgAreaIncident.netManName}"
		id="ccmOrgAreaIncidentNetManName" />
	<input type="hidden" value="${ccmOrgAreaIncident.telephone}"
		id="ccmOrgAreaIncidentNetManTelephone" />
	<input type="hidden" value="${ccmOrgAreaIncident.netNum}"
		id="ccmOrgAreaIncidentNetManNetNum" />
	<input type="hidden" value="${ccmOrgAreaIncident.mannum}"
		id="ccmOrgAreaIncidentNetManMannum" />
	<input type="hidden" value="${ccmOrgAreaIncident.icon}"
		id="ccmOrgAreaIncidentNetManManIcon" />
	<input type="hidden" value="${netMapIncident}" id="netMapIncident" />
	<input type="hidden" value="${ccmOrgAreaIncident.videoSafetyNum}"
		id="videoSafetyNum" />
	<input type="hidden" value="${ccmOrgAreaIncident.definitionNum}"
		id="definitionNum" />
	<input type="hidden" value="${ccmOrgAreaIncident.netPeoName}"
		id="netPeoName" />
	<input type="hidden" value="${ccmOrgAreaIncident.netPeoNum}"
		id="netPeoNum" />

	<!--处置信息  -->

	<input type="hidden" value="${areaLiveIncident}" id="areaLiveIncident" />

	<input type="hidden" value="${ccmOrgAreaLiveIncident.netManName}"
		id="ccmOrgAreaLiveIncidentnetManName" />
	<input type="hidden" value="${ccmOrgAreaLiveIncident.telephone}"
		id="ccmOrgAreaLiveIncidenttelephone" />

	<input type="hidden" value="${ccmOrgAreaLiveIncident}"
		id="ccmOrgAreaLiveIncident" />
	<!--处置信息--当前网格  -->
	<c:forEach items="${vCcmTeamListIncident}" var="vLIs">

		<input type="hidden" value="${vLIs.name}" attrid="${vLIs.id}"
			attrtel="${vLIs.phone}" attrphoto="${vLIs.photo}" name="vLIsname" />
	</c:forEach>

	<c:forEach items="${vCcmOrgListIncident}" var="vCcmOrgListIncident">
		<input type="hidden" value="${vCcmOrgListIncident.primaryPerson.name}"
			attrid="${vCcmOrgListIncident.primaryPerson.id }"
			attrtel="${vCcmOrgListIncident.phone}"
			attrphoto="${vCcmOrgListIncident.primaryPerson.remarks}"
			name="vLIsname" />
	</c:forEach>
	<!--处置信息--当前网格  -->

	<!--处置信息--所属网格  -->
	<c:forEach items="${vCcmTeamLiveListIncident}" var="suoshuvLIs">
		<input type="hidden" value="${suoshuvLIs.name}"
			attrtel="${suoshuvLIs.phone}" attrid="${suoshuvLIs.id}"
			attrphoto="${suoshuvLIs.photo}" name="suoshuvLIs" />
	</c:forEach>

	<c:forEach items="${vCcmOrgLiveListIncident}"
		var="vCcmOrgLiveListIncident">
		<input type="hidden"
			value="${vCcmOrgLiveListIncident.primaryPerson.name}"
			attrid="${vCcmOrgLiveListIncident.primaryPerson.id}"
			attrtel="${vCcmOrgLiveListIncident.phone}"
			attrphoto="${vCcmOrgLiveListIncident.primaryPerson.remarks}"
			name="suoshuvLIs" />
	</c:forEach>
	<!--处置信息--所属网格  -->
	<!--处置信息  -->
	<input type="hidden" value="${vCcmTeamListIncident}"
		id="vCcmOrgLiveListIncident" />
	<input type="hidden" value="${vCcmTeamLiveListIncident}"
		id="vCcmTeamLiveListIncident" />

	<!-- 今日案事件定位信息 -->

	<div id="FullBody" style="position: relative;">
		<!-- 缩放控件 -->
		<%-- <div id="zoombar" class="zoombar"
			style="position: absolute; top: 24px; left: 7px; height: 300px; z-index: 9">
			<div style="position: absolute; width: 63px; height: 62px;">
				<img style="position: relative; width: 63px; height: 62px"
					src="${ctxStatic}/modules/map/images/zoom/zoompanbar_bg.png" />
				<div id="Control.PanZoomBar.panup"
					style="position: absolute; left: 24px; top: 5px; width: 16px; height: 16px; cursor: pointer;"
					class="olButton olpanup" onclick="Map.panDirection('north')">
					<img id="Control.PanZoomBar.panup_innerImage"
						style="position: relative; width: 16px; height: 16px;"
						class="olAlphaImg"
						src="${ctxStatic}/modules/map/images/zoom/north-mini.png">
				</div>
				<div id="Control.PanZoomBar.panleft"
					style="position: absolute; left: 6px; top: 23px; width: 16px; height: 16px; cursor: pointer;"
					class="olButton olpanleft" onclick="Map.panDirection('west')">
					<img id="Control.PanZoomBar.panleft_innerImage"
						style="position: relative; width: 16px; height: 16px;"
						class="olAlphaImg"
						src="${ctxStatic}/modules/map/images/zoom/west-mini.png">
				</div>
				<div id="Control.PanZoomBar.panright"
					style="position: absolute; left: 42px; top: 23px; width: 16px; height: 16px; cursor: pointer;"
					class="olButton olpanright" onclick="Map.panDirection('east')">
					<img id="Control.PanZoomBar.panright_innerImage"
						style="position: relative; width: 16px; height: 16px;"
						class="olAlphaImg"
						src="${ctxStatic}/modules/map/images/zoom/east-mini.png">
				</div>
				<div id="Control.PanZoomBar.pandown"
					style="position: absolute; left: 24px; top: 39px; width: 16px; height: 16px; cursor: pointer;"
					class="olButton olpandown" onclick="Map.panDirection('south')">
					<img id="Control.PanZoomBar.pandown_innerImage"
						style="position: relative; width: 16px; height: 16px;"
						class="olAlphaImg"
						src="${ctxStatic}/modules/map/images/zoom/south-mini.png">
				</div>
			</div>
			<div id="Control.PanZoomBar.zoomin"
				style="position: absolute; left: 24px; top: 63px; width: 16px; height: 16px; cursor: pointer;"
				class="olButton olzoomin" onclick="Map.zoomInOut('in')">
				<img id="Control.PanZoomBar.zoomin_innerImage"
					style="position: relative; width: 16px; height: 16px;"
					class="olAlphaImg"
					src="${ctxStatic}/modules/map/images/zoom/zoom-plus-mini.png">
			</div>
			<div
				style="background-image: url(&quot;${ctxStatic}/modules/map/images/zoom/zoombar.png&quot;); left: 24px; top: 79px; width: 16px; height: 150px; position: absolute; cursor: pointer;"
				id="ControlPanZoomBar" class="olButton olPanZoomBar">
				<div id="PanZoomBar" class="PanZoomBar"
					style="position: absolute; left: 0px; top: 54px; width: 16px; height: 16px; cursor: move;">
					<img id="Control.PanZoomBar.OpenLayers.Map_7_innerImage"
						style="position: relative; width: 16px; height: 16px;"
						class="olAlphaImg"
						src="${ctxStatic}/modules/map/images/zoom/slider.png" />
				</div>
				<div id="ControlPanZoomIndex"
					style="position: absolute; width: 66px; height: 161px; left: 17px; top: 0px; overflow: hidden">
					<img id=""
						style="position: absolute; left: 0px; top: 133px; width: 65px; height: 16px; display: none"
						src="${ctxStatic}/modules/map/images/zoom/city-index.png" /> <img
						id=""
						style="position: absolute; left: 0px; top: 108px; width: 65px; height: 16px;"
						src="${ctxStatic}/modules/map/images/zoom/district-index.png" />
					<img id=""
						style="position: absolute; left: 0px; top: 82px; width: 65px; height: 16px;"
						src="${ctxStatic}/modules/map/images/zoom/street-index.png" /> <img
						id=""
						style="position: absolute; left: 0px; top: 55px; width: 65px; height: 16px;"
						src="${ctxStatic}/modules/map/images/zoom/community-index.png" />
					<img id=""
						style="position: absolute; left: 0px; top: 29px; width: 65px; height: 16px;"
						src="${ctxStatic}/modules/map/images/zoom/grid-index.png" />
				</div>
			</div>
			<div id="Control.PanZoomBar.zoomout"
				style="position: absolute; left: 24px; top: 227px; width: 16px; height: 16px; cursor: pointer;"
				class="olButton olzoomout" onclick="Map.zoomInOut('out')">
				<img id="Control.PanZoomBar.zoomout_innerImage"
					style="position: relative; width: 16px; height: 16px;"
					class="olAlphaImg"
					src="${ctxStatic}/modules/map/images/zoom/zoom-minus-mini.png">
			</div>
		</div>
		<div id="overly" class="overlay"></div>
		<!-- 缩放控件 -->
         --%>
		<button type="button" data-toggle="modal" data-target="#myModal1"
			id="videoBtn" style="display: none"></button>
		<div id="myModal1" class="modal hide fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<button type="button" class="close video-close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">视频监控</h3>
			</div>
			<div class="modal-body">
				<div id="video">
					<!-- <video id="videoElement"
					class="video-js vjs-default-skin vjs-big-play-centered"
					controlspreload="auto" width="540" height="400">
				</video> -->
					<!-- <video id="videoHtml5"
				autoplay	controls width="540" height="400">
				</video> -->

				</div>
			</div>
			<div class="modal-footer">
				<button class="btn video-close" data-dismiss="modal"
					aria-hidden="true">关闭</button>
			</div>
		</div>
		<!--  工具栏-->



		</div>
		<!-- 工具栏 -->
		<div id="delete-wrapper">
			<div id="btn-delete" style="display: none;">删 除</div>
		</div>
		<!--  工具栏-->
		<div id="map" class="map" tabindex="0">
			<!-- 	<div id="layerControl" class="layerControl">
			<div class="title">
				<label>图层列表</label>
			</div>
			<ul id="tree" class="ztree"></ul>
		</div> -->
			<%--<div id="detailsDialog"></div>--%>

		</div>
		<!-- 动态添加文字 -->
		<!-- <div id="TextOverlay" onclick="activeDelBtn()">

	</div> -->
		<!-- 	<div id="popup" class="ol-popup jbox">
		<div class="jbox-container">
			<a href="#" id="popup-closer" class="ol-popup-closer jbox-close"
				title="关闭" onmouseover="$(this).addClass('jbox-close-hover');"
				onmouseout="$(this).removeClass('jbox-close-hover');"
				style="position: absolute; display: block; cursor: pointer; top: 11px; right: 11px; width: 15px; height: 15px;"></a>
			<div class="jbox-title-panel" style="height: 25px;">
				<div class="jbox-title">信息</div>
			</div>
			<div class="jbox-state">
				<div id="popup-content" style="padding: 8px 15px;"></div>
				<div class="jbox-button-panel"
					style="height: 25px; padding: 5px 0 5px 0; text-align: right;">
					<span class="jbox-bottom-text"
						style="float: left; display: block; line-height: 25px;"></span>
					<button class="jbox-button" id="popup-closer1"
						style="padding: 0px 10px 0px 10px;">关闭</button>
				</div>
			</div>
		</div>

	</div> -->

		<!-- <div id="popup" class="ol-popup  bb">
			<div class="">
				<a href="#" id="popup-closer" class="ol-popup-closer  icon-remove "
					title="关闭" onmouseover="$(this).addClass('jbox-close-hover');"
					onmouseout="$(this).removeClass('jbox-close-hover');"
					style="position: absolute; display: block; cursor: pointer; top: 4px; right: 11px; width: 15px; height: 15px; color: #fff"></a>
				<div class="jbox-title-panel" style="height: 25px;">
					<div class="jbox-title">信息</div>
				</div>
				<div class="jbox-state">
					<div id="popup-content" style="padding: 8px 15px;"></div>
				</div>
			</div>
		</div> -->
		 <%--  辖区管理员信息 --%>
<%--			<div id="popup" class="ol-popup  bb">
			<div class="">
				<a href="#" id="popup-closer" class="ol-popup-closer  icon-remove "
					title="关闭" onmouseover="$(this).addClass('jbox-close-hover');"
					onmouseout="$(this).removeClass('jbox-close-hover');"
					style="position: absolute; display: block; cursor: pointer; top: 4px; right: 11px; width: 15px; height: 15px; color: #fff"></a>
				<div class="jbox-title-panel" style="height: 25px;">
					<div class="jbox-title">信息</div>
				</div>
				<div class="jbox-state">
					<div id="popup-content" style="padding: 8px 15px;"></div>
				</div>
			</div>
		</div>--%>
		<div id="pubMap" ></div>
		<!-- 楼栋住户信息 -->
		<button type="button" data-toggle="modal" data-target="#myModal"
			id="buildBtn" style="display: none"></button>
		<div id="myModal" class="modal hide fade jbox" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
			style="width: 1300px; margin-left: -33.85%;">
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
		<!-- 楼栋住户信息 -->

	</div>
	</div>
     <!-- /content -->
    </div>

</body>
</html>