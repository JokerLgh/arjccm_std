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
	href="${ctxStatic}/ccm/liveVideo/css/livedemo.css"/>
<link rel="stylesheet"
	href="${ctxStatic}/ccm/liveVideo/css/video-js.css"/>
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/js/draw/css/p-ol3.min.css"
	type="text/css"/>
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/js/draw/css/defaults.css"
	type="text/css"/>
<link rel="stylesheet" href="${ctxStatic}/modules/map/css/map.css"
	type="text/css"/>
<link rel="stylesheet" href="${ctxStatic}/modules/map/css/house.css"
	type="text/css"/>
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" href="${ctxStatic}/DataTables/css/jquery.dataTables.css">
<script src="${ctxStatic}/DataTables/js/jquery.dataTables.js" type="text/javascript"></script>
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
	<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet">
<link rel="stylesheet"
	href="${ctxStatic}/modules/map/css/publicinstitutions.css">
<script src="${ctxStatic}/layer-v3.1.1/layer/layer.js"></script>
<link href="${ctxStatic}/layim/layui/css/layui.css" type="text/css" rel="stylesheet">
<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet">
<script src="${ctxStatic}/layim/layui/layui.js"></script>
<script src="${ctxStatic}/modules/map/js/mapconfig.js"></script>
<script src="${ctxStatic}/modules/map/js/commonMap.js"></script>
<script src="${ctxStatic}/common/common.js"></script>
<script src="${ctxStatic}/common/index/Scripts/js/echarts.min.js"></script>
<script src="${ctxStatic}/ccm/sys/js/dataAnalysisIndex.js"></script>
<script src="${ctxStatic}/ccm/sys/js/dataAnalysis.js"></script>

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
	height: 100px;
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

.btn.green:not (.btn-outline ) {
	color: #fff;
	background-color: #32c5d2;
	border-color: #32c5d2;
}

.mapListTop {
	position: absolute;
	z-index: 199202;
	top: 180px;
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
    top: 120px;
    width: 350px;
    height: calc(100% - 64px - 57px - 25px);
    border: 1px solid #ccc;
    background-color: #fff;
    opacity: 0.9;
    overflow: auto;
    border-radius: 8px;
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
.mainBottom{
    position: absolute;
    z-index: 199301;
    left: 14px;
    bottom: 25px;
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
#primaryPersonName{
height:30px;
width: 113px;
}
.input-append {
   margin-bottom: 0px;
}
.map-lend{
    border-radius: 8px;
    background-color: #fff;
    border: 1px solid #ccc;
    height: 146px;
    position: absolute;
    bottom: 19px;
    left: 380px;
    z-index: 199202;
}
.map-lend-head{
  text-align: center;
  line-height: 22px;
  border-bottom: 1px solid #ccc;
}
.fourColorsLegend{
     width: 100%;
    line-height: 26px;
}
.color-bg {
    width: 30px;
    height: 20px;
    float: left;
    margin-top: 2px;
    margin-right: 5px;
}
.color-bg1{
  background: #ffc143;
}
.color-bg2 {
    background: #ffc858;
}
.color-bg3 {
    background: #ffdd95;
}
.color-bg4 {
    background: #ffe3aa;
}
.datalist{
 height:100%;
}
</style>

</head>
<body  style="overflow: hidden;">
    <div id="content" class="row-fluid" style="width:100%;height:100%">
     
     <div  id="right" style="overflow: hidden;">   
         <div id="showMapKey" style="width: 0px; height: 0px; display: none;">
			<img src="${ctxStatic}/images/GIS_list.png">
		</div>
       <div id="mapKey">
			<div class="mapView">
					<p id="mapView-p">数据采集分析<span style="float: right; font-size: 20px; padding-right: 10px; color: #FFF; cursor: pointer;">×</span></p>
					<div class="mapView-p-head">
						<div class="layui-tab-content">
							<ul class="ul-form"  style="margin-top:10px;">
		                     <select id="pType" name="pType" placeholder="请选择人员类型 " class="input-medium" style="margin-bottom:0">
								 <option value="1">建筑物楼栋</option>
								 <option value="2">房屋</option>
								 <option value="3">人口</option>
								 <option value="4">企业</option>
								 <option value="5">场所</option>
						    </select>
							<a href="javascript:;" id="btnSubmitQueryCollect" onclick="QueryCollect()" class="btn btn-primary firstbtn">
							 <i class="icon-search"></i>
										查询
								</a>
								 <a href="javascript:;" id="ClearSubmit"
									style="background: #f50b4b !important;" class="btn btn-primary">
										<i class="icon-remove"></i> 清空
								</a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="map_main">
			<div style="padding:8px;height:97%;">
			<div style="height:100%;" id="IncidentList">
			    <div style="height:100%;" class="datalist" id="AreaList"></div>  
			</div>
			</div>
			</div>
		</div>
		
	<div id="FullBody" style="position: relative;width:100%;height:100%" >
		<div id="map" class="map" tabindex="0">
        <div id="mapLend" class="map-lend">
          <div class="map-lend-head">采集数量</div>
            <div class="map-lend-num">
				 <div style="margin:10px" class="clearfix">
					<div class="fourColorsLegend"><div class="color-bg color-bg1"></div><span id="FourColor1">&gt;=301</span></div>
					<div class="fourColorsLegend"><div class="color-bg color-bg2"></div><span id="FourColor2">201-300</span></div>
					<div class="fourColorsLegend"><div class="color-bg color-bg3"></div><span id="FourColor3">101-200</span></div>
					<div class="fourColorsLegend"><div class="color-bg color-bg4"></div><span id="FourColor4">0-100</span></div>
				 </div>
			 </div>
		</div>
	    </div>
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
			frameObj.height(strs[0]);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width());
	

		}
		function wSizeWidth() {
			$("#right").width("100%");
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b) {
			$.fn.jerichoTab.resize();
		} // </c:if>
		

		
	</script>
     <script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>