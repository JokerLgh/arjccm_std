<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>自定义日程表管理代码</title>

<link href="${ctxStatic}/calendar/css/main.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/calendar/css/dailog.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/calendar/css/calendar.css" rel="stylesheet" type="text/css" /> 
<link href="${ctxStatic}/calendar/css/dp.css" rel="stylesheet" type="text/css" />   
<link href="${ctxStatic}/calendar/css/alert.css" rel="stylesheet" type="text/css" />     
<link href="${ctxStatic}/calendar/css/blackbird.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet">

<style type="text/css">
div .sfbtn{
  font-size: 20px;
 width:9px;
}
</style>
</head>

<body>

<div>

<div id="calhead" style="padding-left:1px;padding-right:1px;">          
	<div class="cHead">
		<div class="ftitle">我的日程</div>
		<div id="loadingpannel" class="ptogtitle loadicon" style="display: none;">正在加载数据...</div>
		
	</div>          

	<div id="caltoolbar" class="ctoolbar">
		<div id="faddbtn" class="fbutton">
			<div><span title='点击新建日程' class="addcal">新建日程</span></div>
		</div>
		<div class="btnseparator"></div>
		<div id="showtodaybtn" class="fbutton">
			<div><span title='点击返回当前日程 ' class="showtoday">今天</span></div>
		</div>
		<div class="btnseparator"></div>
		<div id="showdaybtn" class="fbutton">
			<div><span title='日' class="showdayview">日</span></div>
		</div>
		<div  id="showweekbtn" class="fbutton fcurrent">
			<div><span title='周' class="showweekview">周</span></div>
		</div>
		<div id="showmonthbtn" class="fbutton">
			<div><span title='月' class="showmonthview">月</span></div>
		</div>
		<div class="btnseparator"></div>
		<div id="showreflashbtn" class="fbutton">
			<div><span title='刷新' class="showdayflash">刷新</span></div>
		</div>
		<div class="btnseparator"></div>
		<div id="sfprevbtn" title="上一个"  class="fbutton">
			<span class="fprev sfbtn"><i class=" icon-caret-left"></i></span>
		</div>
		
		<div class="fshowdatep ">
			<div>
				<!-- <input type="hidden" name="txtshow" id="hdtxtshow" /> -->
				<span id="txtdatetimeshow">Loading</span>
			</div>
		</div>
		<div id="sfnextbtn" title="下一个" class="fbutton">
			<span class="fnext sfbtn"><i class="icon-caret-right"></i></span>
		</div>
		<!-- <div class="btnseparator"></div>
		<div id="changetochinese" class="fbutton">
			<div><span title='中文' class="chinese">中文</span></div>
		</div>
		<div id="changetoenglish" class="fbutton">
			<div><span title='英文' class="english">英文</span></div>
		</div>
		<div id="changetoenglishau" class="fbutton">
			<div><span title='英文' class="english_au">英文</span></div>
		</div> -->
		<div class="btnseparator"></div>         
		<div class="clear"></div>
	</div>
</div>

<div style="padding:1px;">
	<div class="t1 chromeColor">&nbsp;</div>
	<div class="t2 chromeColor">&nbsp;</div>
	<div id="dvCalMain" class="calmain printborder">
		<div id="gridcontainer" style="overflow-y: visible;"></div>
	</div>
	<div class="t2 chromeColor">&nbsp;</div>
	<div class="t1 chromeColor">&nbsp;</div>   
</div>

</div>

<script src="${ctxStatic}/calendar/js/jquery.min.js" type="text/javascript"></script>  
<script src="${ctxStatic}/calendar/js/Common.js" type="text/javascript"></script>    
<script src="${ctxStatic}/calendar/js/blackbird.js" type="text/javascript"></script> 
<script src="${ctxStatic}/calendar/js/datepicker_lang_zh_CN.js" type="text/javascript"></script>     
<script src="${ctxStatic}/calendar/js/jquery.datepicker.js" type="text/javascript"></script>
<script src="${ctxStatic}/calendar/js/jquery.alert.js" type="text/javascript"></script>    
<script src="${ctxStatic}/calendar/js/jquery.ifrmdailog.js" defer="defer" type="text/javascript"></script>
<script src="${ctxStatic}/calendar/js/xgcalendar_lang_zh_CN.js" type="text/javascript"></script>  
<script src="${ctxStatic}/calendar/js/xgcalendar.js?v=1.2.0.4" type="text/javascript"></script> 
  
<script type="text/javascript">
$(document).ready(function() {
	
	var showdate='${date}';
	var keyList = eval('${list}'); 
	for (var i = 0; i < keyList.length; i++) {
		keyList[i][2]=new Date(keyList[i][2]+8*3600*1000);
		keyList[i][3]=new Date(keyList[i][3]+8*3600*1000);
	}
	
	
//iframe去边距
$("#right", parent.document).css("padding","0px")
//[id,title,start,end，全天日程，跨日日程,循环日程,theme(颜色),'地点','参与人']          
var view="week";          
﻿ /* __CURRENTDATA=[['6147','你好啊',new Date(1531988321907),new Date(1531988421907),0,0,0,2,0,'21','']]; */

var op = {
	view: view,
	theme:3,
	showday: new Date(Number(showdate)),
	EditCmdhandler:Edit,
	DeleteCmdhandler:Delete,
	ViewCmdhandler:View,    
	onWeekOrMonthToDay:wtd,
	onBeforeRequestData: cal_beforerequest,
	onAfterRequestData: cal_afterrequest,
	onRequestDataError: cal_onerror, 
	url: "calendar?mode=get" ,  
	quickAddUrl: "${ctx}/calendar/plmCalendar/quickadd" ,  
	quickUpdateUrl: "calendar.php?mode=quickupdate" ,  
	quickDeleteUrl:  "calendar.php?mode=quickdelete" //快速删除日程的
   /* timeFormat:" hh:mm t", //t表示上午下午标识,h 表示12小时制的小时，H表示24小时制的小时,m表示分钟
	tgtimeFormat:"ht" //同上 */             
};
var $dv = $("#calhead");
var _MH = document.documentElement.clientHeight;
var dvH = $dv.height() + 2;
op.height = _MH - dvH;
op.eventItems = keyList;

var p = $("#gridcontainer").bcalendar(op).BcalGetOp();
if (p && p.datestrshow) {
	$("#txtdatetimeshow").text(p.datestrshow);
}
$("#caltoolbar").noSelect();

$("#hdtxtshow").datepicker({ picker: "#txtdatetimeshow", showtarget: $("#txtdatetimeshow"),
onReturn:function(r){                          
				var p = $("#gridcontainer").BCalGoToday(r).BcalGetOp();
				if (p && p.datestrshow) {
					$("#txtdatetimeshow").text(p.datestrshow);
				}
		 } 
});
function cal_beforerequest(type)
{
	var t="正在加载数据...";
	switch(type)
	{
		case 1:
			
			
			
			t="正在加载数据...";
			break;
		case 2:                      
		case 3:  
		case 4:    
			t="正在处理请求...";                                   
			break;
	}
	$("#errorpannel").hide();
	$("#loadingpannel").html(t).show();    
}
function cal_afterrequest(type)
{
	switch(type)
	{
		case 1:
			$("#loadingpannel").hide();
			break;
		case 2:
		case 3:
		case 4:
			$("#loadingpannel").html("操作成功!");
			window.setTimeout(function(){ $("#loadingpannel").hide();},2000);
		break;
	}              
   
}
function cal_onerror(type,data)
{
	$("#errorpannel").show();
}
function Edit(data)
{
   var eurl="${ctx}/calendar/plmCalendar/formdate?data="+data;   
	if(data)
	{     
		var url = StrFormat(eurl,data);
		
		OpenModelWindow(url,{ width: 1000, height: 450, caption:"管理日程",onclose:function(){
		   $("#gridcontainer").BCalReload();
		}});
	}
}    
function View(data)
{    
	var vurl="${ctx}/calendar/plmCalendar/formTan?id="+data[0];   
	if(data)
	{   
		var url = StrFormat(vurl,data);
		OpenModelWindow(url,{ width: 1000, height:450, caption: "编辑日程"});
	}                
}    
function Delete(data,callback)
{  
	$.alerts.okButton="确定";  
	$.alerts.cancelButton="取消";  
	hiConfirm("是否要删除该日程?", '确认',function(r){ r && callback(0);});           
}
function wtd(p)
{        
   if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}
	$("#caltoolbar div.fcurrent").each(function() {
		$(this).removeClass("fcurrent");
	})
	$("#showdaybtn").addClass("fcurrent");
}
//显示日视图
$("#showdaybtn").click(function(e) {
	//document.location.href="#day";
	$("#caltoolbar div.fcurrent").each(function() {
		$(this).removeClass("fcurrent");
	})
	$(this).addClass("fcurrent");
	var p = $("#gridcontainer").BCalSwtichview("day").BcalGetOp();
	if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}
});
//显示周视图
$("#showweekbtn").click(function(e) {
	//document.location.href="#week";
	$("#caltoolbar div.fcurrent").each(function() {
		$(this).removeClass("fcurrent");
	})
	$(this).addClass("fcurrent");
	var p = $("#gridcontainer").BCalSwtichview("week").BcalGetOp();
	if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}

});
//显示月视图
$("#showmonthbtn").click(function(e) {
	//document.location.href="#month";
	$("#caltoolbar div.fcurrent").each(function() {
		$(this).removeClass("fcurrent");
	})
	$(this).addClass("fcurrent");
	var p = $("#gridcontainer").BCalSwtichview("month").BcalGetOp();
	if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}
});

$("#showreflashbtn").click(function(e){
	$("#gridcontainer").BCalReload();
});

//点击新增日程
$("#faddbtn").click(function(e) {
	var url ="${ctx}/calendar/plmCalendar/formTan";
	OpenModelWindow(url,{ width:1000, height: 450, caption: "新增日程"});
});
//点击回到今天
$("#showtodaybtn").click(function(e) {
	var p = $("#gridcontainer").BCalGoToday().BcalGetOp();
	if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}


});
//上一个
$("#sfprevbtn").click(function(e) {
	var p = $("#gridcontainer").BCalPrev().BcalGetOp();
	if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}

});
//下一个
$("#sfnextbtn").click(function(e) {
	var p = $("#gridcontainer").BCalNext().BcalGetOp();
	if (p && p.datestrshow) {
		$("#txtdatetimeshow").text(p.datestrshow);
	}
});
$("#changetochinese").click(function(e){
	location.href="?lang=zh-cn";
});
$("#changetoenglish").click(function(e){
	location.href="?lang=en-us";
});
 $("#changetoenglishau").click(function(e){
	location.href="?lang=en-au";
});

});
</script>

<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">

</div>
</body>
</html>