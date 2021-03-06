<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<meta name="decorator" content="blank" />

<style type="text/css">
#main {
	padding: 0;
	margin: 0;
}

#main .container-fluid {
	padding: 0;
}

#header {
	margin: 0;
	position: static;
	border-bottom: 0px solid #5b6e84;
}

#header li {
	font-size: 14px;
	_font-size: 12px;
	/*margin-left: 5px;*/
}

#header .brand {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 26px;
	padding-left: 13px;
}

#footer {
	margin: 8px 0 0 0;
	padding: 3px 0 0 0;
	font-size: 11px;
	text-align: center;
	border-top: 2px solid #0663A2;
}

#footer, #footer a {
	color: #999;
}

#left {
	overflow-x: hidden;
	overflow-y: auto;
}

#left .collapse {
	position: static;
}

#userControl>li>a { /*color:#fff;*/
	text-shadow: none;
}

#userControl>li>a:hover, #user #userControl>li.open>a {
	background: transparent;
}

#productName {
	color: #fff;
	font-weight: 600;
}

.navbar .nav>li>a {
	padding: 14px 15px 14px;
}

.navbar .brand {
	padding: 17px 40px 3px 10px;
}

#userControl li {
	line-height: 46px
}

.system-nav {
	width: 700px;
	margin: auto;
	margin-top: 5%;
}

.system-nav li {
	width: 131px;
	height: 80px;
	background: #fafafa;
	border: 1px solid #e6e6e6;
	border-radius: 4px;
	float: left;
	text-align: center;
	line-height: 10px;
	margin: 10px 1px;
	position: relative;
}

.system-nav li a {
	margin: auto;
	width: 126px;
	display: block;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap
}

.system-nav .menu .nav-icon {
	margin-top: 17px;
	color: #33aaff;
}

.dropdown-menu.system-nav>li>a:hover {
	background: none;
	color: #eea807;
}

.system-nav li:hover,.system-nav li.active  {
	border: 1px solid #20c694;
}

.system-nav li:hover>a,.system-nav li.active>a {
	color: #eea807;
	background: none;
}

.menu-use {
	border-radius: 2px 2px 10px 0;
	width: 56px;
	height: 20px;
	line-height: 20px;
	font-size: 12px;
	position: absolute;
	background: #20c694;
	color: #fff;
	display: none
}
.system-nav li.active .menu-use{
	display: block;
}
#userControl{
  margin-top: 5px;
}



</style>
<c:set var="tabmode"
	value="${empty cookie.tabmode.value ? '0' : cookie.tabmode.value}" />
<c:if test="${tabmode eq '1'}">
	<link rel="Stylesheet"
		href="${ctxStatic}/jerichotab/css/jquery.jerichotab.css" />
	<script type="text/javascript"
		src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script>
</c:if>


<link rel="stylesheet" href="${ctxStatic}/dist/css/layui.css">

<script type="text/javascript"
	src="${ctxStatic}/activemq/amq_jquery_adapter.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/activemq/amq.js"
	charset="utf-8"></script>
<script type="text/javascript" src="${ctxStatic}/activemq/amq_init.js"
    charset="utf-8"></script>
	<script type="text/javascript" src="${ctxStatic}/ccm/sys/js/rmqKeyPeople.js"
			charset="utf-8"></script>
<script src="${ctxStatic}/common/qrcode.min.js" type="text/javascript"></script>
<script type="text/javascript"  >
	window.onload = function() {
		initRabbitMQ();
		initRabbitMQKeyProple();
	};

	//判断pid是否存在   跳转三亚可视化界面
	if(location.search.indexOf("pid=")!=-1){
		//如果是并且是大数据可视化 系统
		if(location.search.split("=")[1]=="b9c27a2a629141f4a7355a5bf987944c"){
			//去掉页头重新刷新页面(去除iframe)
			/* location.href="${ctx}/sys/map/statIndex"; */
			/* location.href="${ctx}/sys/map/statLivelihood"; */
			/* location.href="${ctx}/sys/map/statLivelihoodQuLiang"; */
			location.href="${ctx}/sys/map/statIndexCool";
		}
	}


	//判断pid是否存在	跳转新密宗教可视化界面
	if(location.search.indexOf("pid=")!=-1){
		//如果是并且是大数据可视化 系统
		if(location.search.split("=")[1]=="8bb3257be8ae448a9d3e80d4075291ef"){
			//去掉页头重新刷新页面(去除iframe)
			/* location.href="${ctx}/sys/map/statIndex"; */
			/* location.href="${ctx}/sys/map/statLivelihood"; */
			/* location.href="${ctx}/sys/map/statLivelihoodQuLiang"; */
			location.href="${ctx}/sys/map/statIndexForZj";
		}
	}


$(document).ready(function() {
    // <c:if test="${tabmode eq '1'}"> 初始化页签
    ///arjccm/a/sys/map/indexMap
    $.fn.initJerichoTab({
        renderTo: '#right',
        uniqueId: 'jerichotab',
        contentCss: {
            'height': $('#right').height() - tabTitleHeight
        },
        tabs: [],
        loadOnce: true,
        tabWidth: 110,
        titleHeight: tabTitleHeight
    }); //</c:if>
    // 绑定菜单单击事件
    var _menuUrl="${url}";
    var _menuId="${id}";
     var $menu=$('#menu a.menu,#menu1 a.menu');
  $menu.click(function() {
    	//判断是否为首页
    	var aHref=$(this).attr('href');
    	if(aHref=="${ctx}/flat/realtimeSituation/realtimeSituation" || aHref=="${ctx}/home/plmHome/list" || aHref=="${ctx}/sys/map/indexMap"||aHref=="${ctx}/house/ccmHouseBuildmanage/ToMap"||aHref=="${ctx}/sys/map/GISData"||aHref=="${ctx}/sys/map/eventAnalysis"||aHref=="${ctx}/sys/map/dataAnalysis"||aHref=="${ctx}/sys/map/APPTerminal" || aHref=="${ctx}/flat/planManage/planManage?isShow=0" || aHref=="${ctx}/../static/template/flat/realtimeAlarm/bphRealtimeAlarm.html"){
    		$('#right').css('padding','0px')
    	}else{
    		$('#right').css('padding','0px')
    	}
    	
        // 一级菜单焦点
        $("#menu li.menu").removeClass("active");
        $(this).parent().addClass("active");
        // 左侧区域隐藏
        if ($(this).attr("target") == "mainFrame") {
            $("#left,#openClose").hide();
            wSizeWidth();
            // <c:if test="${tabmode eq '1'}"> 隐藏页签
            $(".jericho_tab").hide();
            $("#mainFrame").show(); //</c:if>
            return true;
        }
        // 左侧区域显示
        $("#left,#openClose").show();
        if (!$("#openClose").hasClass("close")) {
            $("#openClose").click();
        }
        // 显示二级菜单
        var menuId = "#menu-" + $(this).attr("data-id");
        
        if ($(menuId).length > 0) {
            $("#left .accordion").hide();
            $(menuId).show();

            if(_menuUrl){
            	$(menuId + " .accordion-body a").each(function(index){
            		if($(this).attr('href')==_menuUrl){
            			 $(menuId + " .accordion-heading:eq("+index+") a").addClass('active');
                         //
                         if (!$(menuId + " .accordion-body:eq("+index+")").hasClass('in')) {
                             $(menuId + " .accordion-heading:eq("+index+") a").click();
                         }
                         if (!$(menuId + " .accordion-body li:eq("+index+") ul:first").is(":visible")) {
                             $(menuId + " .accordion-body a:eq("+index+") i").click();
                         }
            		}
            	})
            	  
                   _menuUrl="";
            }else{
            	   $(menuId + " .accordion-heading:first a").addClass('active');
                   // 初始化点击第一个二级菜单
                   if (!$(menuId + " .accordion-body:first").hasClass('in')) {
                       $(menuId + " .accordion-heading:first a").click();
                   }
                   if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")) {
                       $(menuId + " .accordion-body a:first span").click();
                   }
            }


            
            $(menuId + " .accordion-heading:first a").addClass('active');
            // 初始化点击第一个二级菜单
            if (!$(menuId + " .accordion-body:first").hasClass('in')) {
                $(menuId + " .accordion-heading:first a").click();
            }
            if($(menuId + " .accordion-body li:first ul:first").length>0){
            	 if (!$(menuId + " .accordion-body li:first ul:first").is(":visible")) {
                     $(menuId + " .accordion-body a:first span").click();
                 }
            }
           
            // 初始化点击第一个三级菜单
            $(menuId + " .accordion-body li:first li:first a:first span").click();
        } else {
            // 获取二级菜单数据
            $.get($(this).attr("data-href"),
            function(data) {
                if (data.indexOf("id=\"loginForm\"") != -1) {
                    alert('未登录或登录超时。请重新登录，谢谢！');
                    top.location = "${ctx}";
                    return false;
                }
                
                $("#left .accordion").hide();
                $("#left").append(data);
              //  $(menuId + " .accordion-heading:first a").addClass('active');
                // 链接去掉虚框
                $(menuId + " a").bind("focus",
                function() {
                    if (this.blur) {
                        this.blur()
                    };
                });
                // 二级标题
                $(menuId + " .accordion-heading a").click(function() {
                	$('.accordion-heading>a').removeClass('active');
                    $(menuId + " .accordion-toggle i").removeClass('icon-chevron-down').addClass('icon-chevron-right');
                    if (!$($(this).attr('data-href')).hasClass('in')) {
                    	if($($(this).attr('data-href')).hasClass('collapseflag') ){
                    		$('.accordion-body').removeClass('in');
                    		$('.accordion-body.collapse').css('height','0');
                    	}
                        $(this).children("i").removeClass('icon-chevron-right').addClass('icon-chevron-down');
                        $('.accordion-heading a').removeClass('active');
                    	$(this).addClass('active')
                    }else{
                    	if($($(this).attr('data-href')).hasClass('collapseflag') ){
                    		$('.accordion-body').removeClass('in');
                    		$('.accordion-body.collapse').css('height','0');
                    	}
                    }
                });
                // 二级内容
                $(menuId + " .accordion-body a").click(function() {
                	//判断是否地地图标注,综治队伍
                	var aHref=$(this).attr('href');
                	if(aHref=="${ctx}/house/ccmHouseBuildmanage/ToMap"||aHref=="${ctx}/view/vCcmTeam/index"||aHref=="${ctx}/sys/map/indexiot"){
                		$('#right').css('padding','0px');
                		$("#right").width(
        						$("#content").width() - leftWidth
        								- $("#openClose").width() - 5);
                	}else{
                		$('#right').css('padding','8px')
                		$("#right").width(
        						$("#content").width() - leftWidth
        								- $("#openClose").width() - 25);
                	}
                	
                    $(menuId + " li").removeClass("active");
                    $(menuId + " li a").removeClass("active");
                    $(menuId + " li i").removeClass("icon-white");
                    $(this).parent().addClass("active");
                    $(this).addClass("active");
                    $(this).children("i").addClass("icon-white");
                    _menuUrl="";
                });
                // 展现三级
                $(menuId + " .accordion-inner a").click(function() {
                    var href = $(this).attr("data-href");
                    if ($(href).length > 0) {
                        $(href).toggle().parent().toggle();
                        return false;
                    }
                    _menuUrl="";
                    // <c:if test="${tabmode eq '1'}"> 打开显示页签
                    return addTab($(this)); // </c:if>
                });
                
                
                if(_menuUrl){
                	$(menuId + " .accordion-body a").each(function(index){
                		if($(this).attr('href')==_menuUrl){
                			
                       	 if(!$(menuId + " .accordion-body a:eq("+index+")").parent().parent().parent().parent().parent().children().children().children().hasClass('icon-chevron-down')){
                              	$(menuId + " .accordion-body a:eq("+index+")").parent().parent().parent().parent().parent().children().children().click()
                              }
                         	// 默认选中第一个菜单
                             $(menuId + " .accordion-body a:eq("+index+") i").click();
                             $(menuId + " .accordion-body li:eq("+index+") li:first a:first i").click();
                		}
                	})
       
                 	
                    _menuUrl="";
                }else{
                	 // 默认选中第一个菜单
                    $(menuId + " .accordion-body a:first span").click();
                    $(menuId + " .accordion-body li:first li:first a:first span").click();
                }
               
            });
        }
        // 大小宽度调整
        wSizeWidth();
        return false;
    });
    // 初始化点击第一个一级菜单
   // $("#menu a.menu:first span").click();
    
    
    

    if(_menuId){
    	$("#menu a.menu").each(function(index){
    		if($("#menu a.menu:eq("+index+")").attr('data-id')==_menuId){
    			$("#menu a.menu:eq("+index+") span").click();
    		}
    	})
    	
    	_menuId="";
    	
    }else{
    	
    	$("#menu a.menu:first span").click();
    }
    // <c:if test="${tabmode eq '1'}"> 下拉菜单以选项卡方式打开
    $("#userInfo .dropdown-menu a").mouseup(function() {
        return addTab($(this), true);
    }); // </c:if>
    // 鼠标移动到边界自动弹出左侧菜单
    $("#openClose").mouseover(function() {
        if ($(this).hasClass("open")) {
            $(this).click();
        }
    });
    // 获取通知数目  <c:set var="oaNotifyRemindInterval" value="${fns:getConfig('oa.notify.remind.interval')}"/>
    function getNotifyNum() {
        $.get("${ctx}/oa/oaNotify/self/count?updateSession=0&t=" + new Date().getTime(),
        function(data) {
            var num = parseFloat(data);
            if (num > 0) {
                $("#notifyNum,#notifyNum2").show().html("(" + num + ")");
            } else {
                $("#notifyNum,#notifyNum2").hide()
            }
        });
    }
    getNotifyNum(); //<c:if test="${oaNotifyRemindInterval ne '' && oaNotifyRemindInterval ne '0'}">
    setInterval(getNotifyNum, '${oaNotifyRemindInterval}'); //</c:if>
    //生成下载APP二维码
    $.get("${ctx}/appupload/sysAppUpload/getAppInfo",function(data){
    	var qrcode = new QRCode("qrcode",{
    		width:150,
    		height:150,
    		colorDark:'#000000',
    		colorLight:'#ffffff',
    		correctLevel:QRCode.CorrectLevel.H
    	});
        qrcode.makeCode(data);
	})
	
});
// <c:if test="${tabmode eq '1'}"> 添加一个页签
function addTab($this, refresh) {
    $(".jericho_tab").show();
    $("#mainFrame").hide();
    $.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: $this.text(),
        closeable: true,
        data: {
            dataType: 'iframe',
            dataLink: $this.attr('href')
        }
    }).loadData(refresh);
    return false;
} // </c:if>
</script>   	
  <script language="javascript">
	   function videoSubmit(){ 
		   document.getElementById("loginForm").action="${dz_hangzhoudao_link_video}";
		   document.getElementById("loginForm").submit();
	   }
	   function pbsSubmit(){ 
		   document.getElementById("loginForm").action="${dz_hangzhoudao_link_pbs}";
		   document.getElementById("loginForm").submit();
	   }
  </script>

</head>
<body class="sysIndex-body" style="background-color:black">
	<div id="main" style="position: absolute;top:0;width:100%;height:100%">
		<form id="loginForm" class="form-signin" action="" method="post">
			<input type="hidden" id="username" name="username" value="${user.loginName}">
			<input type="hidden" id="password" name="password" value="${user.newPassword}">
			<input type="hidden" id="userid" name="userid" value="${user.id}">
		</form>	
		<div id="header" class="navbar navbar-fixed-top">
			<div class="navbar-inner" style="height: 70px;">
				<div class="liuG"></div>
				<%-- <div class="brand"><span id="productName">${fns:getConfig('productName')}</span></div> --%>
				<div class="brand">
					<a href="${ctx}/sys/map/statIndexCool" target="" style="text-align: center;position: relative;" >
					<img class="logo" src="${ctxStatic}/images/logo.png" style="width:45px;height:45px;margin-right:7px;margin-left:30px;margin-top:-1px ;"/></i>
					   <span id="productName" style="width:162px;height:49px;font-size:26px;font-family:MicrosoftYaHei;font-weight:400;color:rgba(255,255,255,1);line-height:27px;text-shadow:0px 3px 7px rgba(0, 0, 0, 0.3);position: relative;top: -13px; left: -5px">${fns:getConfig('productName_part1')}</span>
						<span style="display: block; top: -20px;left: 73px;width:220px;color:rgba(204, 204, 204, 1); font-size: 17px;font-weight: bold;position: relative;margin-left: -22px;margin-right: -28px;margin-bottom: -15px;">${fns:getConfig('productName_part2')}</span>
					</a>
				</div>
				<ul id="userControl" class="nav pull-right" style="z-index: 1992">
					<li id="AppInfo" class="dropdown" style="margin-left: 1px;">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="App下载 ">
							<i class="icon-mobile-phone" style="font-size: 20px;"></i>
						</a>
						<ul class="dropdown-menu"  >
							<li><i id="qrcode"></i></li>
						</ul>
					</li>
					|
					<%-- <li><a href="${ctx}/" title="返回主面板">主面板</a></li> --%>
					<!-- <li><a href="#" target="_blank" onclick="pbsSubmit();"title="访问智慧党建系统">智慧党建</a></li>
					<li><a href="#" target="_blank" onclick="videoSubmit();" title="访问视频融合系统">视频融合</a></li> -->
					<%--<li><a href="${pageContext.request.contextPath}${fns:getFrontPath()}" title="访问网页" target="_blank"><i class="icon-globe"></i></a></li>--%>
					<%-- <li><a href="${ctx}/sys/map/statIndexCom" title="访问大数据主页"><i class="iconfont  icon-ai-home"></i></a></li> --%>
					<li><a href="${ctx}/sys/map/projectIndex" title="主面板"><i class="iconfont  icon-ai-shouye"></i></a></li>
					<%-- <li><a href="${pageContext.request.contextPath}${fns:getFrontPath()}/index-${fnc:getCurrentSiteId()}.html" target="_blank" title="访问网站主页"><i class="icon-home"></i></a></li>
					--%><li id="themeSwitch" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="主题切换"><i class="icon-th-large"></i></a>
						<ul class="dropdown-menu">
							<c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
			<%-- 				<li><a href="javascript:cookie('tabmode','${tabmode eq '1' ? '0' : '1'}');location=location.href">${tabmode eq '1' ? '关闭' : '开启'}页签模式</a></li> --%>
						</ul>
						<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
					</li> 
					<li id="sysSwitch" class="dropdown">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" title="系统切换"><i class="icon-th-list"></i></a>
						<ul class="dropdown-menu system-nav ">
						<c:set var="pid" value="${pid}" />
						<c:set var="activeMenu" value="false" />
                          <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.parent.id eq '1'&&menu.isShow eq '1'}">
						        <c:if test="${menu.id eq pid}">
								   <c:set var="activeMenu" value="true" />
									<c:set var="activeMenuName" value="${menu.name}" />
								</c:if>
								<li class="menu ${not empty activeMenu && activeMenu? ' active' : ''}">
									<c:if test="${empty menu.href}">
									       <div class="menu-use">使用中</div>
										<a class="menu" style="padding:0" href="${ctx}/index?pid=${menu.id}"
											data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" target="${menu.name=="大数据可视化" ? "_top" : "false"}" 	data-id="${menu.id}">
											<i class="nav-icon iconfont  icon-${menu.icon}"></i>
											<span>${menu.name}</span>
										</a>
									</c:if>
									 <c:if test="${not empty menu.href}">
									  <div class="menu-use">使用中</div>
										<a class="menu" style="padding:0" 
											href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}">
											<i class="nav-icon iconfont  icon-${menu.icon} "></i>
											<span>${menu.name}</span>
										</a>
									</c:if>
								</li>
							</c:if>
							<c:set var="activeMenu" value="false"/>
						</c:forEach>
							<% session.setAttribute("activeMenuName", pageContext.getAttribute("activeMenuName")); %>

						</ul>
						<!--[if lte IE 6]><script type="text/javascript">$('#sysSwitch').hide();</script><![endif]-->
					</li> 
					<li id="userInfo" class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#" title="个人信息">
						<img style=" width: 29px; height: 29px; border-radius: 100%;margin-right: 18px; margin-top: -9px" src="${fns:getUser().photo}"/>
							<%-- <c:if test="!${cookie.theme.value eq 'gradient' }">
								${fns:getUser().name}&nbsp;
						    </c:if> --%>
						   <%--  ${cookie.theme.value eq 'gradient' ? '' : '<span id="notifyNum"
							class="label label-info hide"></span><span class="caret" style="margin-top: 22px;"></span>'} --%>
							<!-- <span id="notifyNum"
							class="label label-info hide"></span><span class="caret" style="margin-top: 22px;"></span> -->
					</a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/sys/user/info" target="mainFrame"><i
									class="icon-user"></i>&nbsp; 个人信息</a></li>
							<li><a href="${ctx}/sys/user/modifyPwd" target="mainFrame"><i
									class="icon-lock"></i>&nbsp; 修改密码</a></li>
							<li><a href="${ctx}/oa/oaNotify/self" target="mainFrame"><i
									class="icon-bell"></i>&nbsp; 我的通知 <span id="notifyNum2"
									class="label label-info hide"></span></a></li>
							<li><a href="${ctx}/logout" title="退出登录"><i class="icon-signout"></i>&nbsp;退出登录</a></li>
						</ul></li>
					
					<li>&nbsp;</li>
				</ul>
				<%-- <c:if test="${cookie.theme.value eq 'cerulean'}">
					<div id="user" style="position:absolute;top:0;right:0;"></div>
					<div id="logo" style="background:url(${ctxStatic}/images/logo_bg.jpg) right repeat-x;width:100%;">
						<div style="background:url(${ctxStatic}/images/logo.jpg) left no-repeat;width:100%;height:70px;"></div>
					</div>
					<script type="text/javascript">
						$("#productName").hide();$("#user").html($("#userControl"));$("#header").prepend($("#user, #logo"));
					</script>
				</c:if> --%>
				<div class="nav-collapse">
					<ul id="menu" class="nav pgwMenuCustom" 
						style="*white-space: nowrap; float: none;">
						<c:set var="pid" value="${pid}" />
						<c:set var="firstMenu" value="false" />
						<c:forEach items="${fns:getMenuLists(pid)}" var="menu" varStatus="idxStatus">
							<c:if test="${menu.parent.id eq pid && menu.isShow eq '1'}">
<%--							<c:if test="${not empty menu.href}">
								<c:set var="firstMenu" value="false" />
							</c:if>--%>
								<c:if test="${not empty firstMenu && firstMenu}" >
									<c:set var="firstMenu" value="true" />
									<c:set var="firstMenuName" value="${menu.name}" />
								</c:if>
								<li
									class="menu ${not empty firstMenu && firstMenu ? ' active' : ''}">
									<c:if test="${empty menu.href}">
										<a class="menu" href="javascript:"
											data-href="${ctx}/sys/menu/tree?parentId=${menu.id}" 	data-id="${menu.id}" style="padding-bottom: 11px;">
											<i class="nav-icon iconfont  icon-${menu.icon}"></i>
											<span>${menu.name}</span></a>
									</c:if> <c:if test="${not empty menu.href}">
										<a class="menu"
											href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}" data-id="${menu.id}" target="mainFrame" style="padding-bottom: 11px;">
											<i class="nav-icon iconfont  icon-${menu.icon} "></i>
											<span>${menu.name}</span>
											</a>
									</c:if>
								</li>
								<c:if test="${firstMenu}">
									<c:set var="firstMenuId" value="${menu.id}" />
								</c:if>
								<c:set var="firstMenu" value="false"/>
							</c:if>
						</c:forEach>
						<% session.setAttribute("firstMenuName", pageContext.getAttribute("firstMenuName")); %>
						<%--
						<shiro:hasPermission name="cms:site:select">
						<li class="dropdown">
							<a class="dropdown-toggle" data-toggle="dropdown" href="#">${fnc:getSite(fnc:getCurrentSiteId()).name}<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<c:forEach items="${fnc:getSiteList()}" var="site"><li><a href="${ctx}/cms/site/select?id=${site.id}&flag=1">${site.name}</a></li></c:forEach>
							</ul>
						</li>
						</shiro:hasPermission> --%>
						<span class="layui-nav-bar"></span>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
		<div class="container-fluid">
			<div id="content" class="row-fluid">
				<div id="left" style = "width: 255px;height: 876px;display: block;background-color: white;clear:both;" >
					<%--
					<iframe id="menuFrame" name="menuFrame" src="" style="overflow:visible;" scrolling="yes" frameborder="no" width="100%" height="650"></iframe> --%>
				</div>
				<div id="openClose" class="close"style="display: block;margin-top: 3px;height: 879px;">&nbsp;</div>
				<div id="right" style = "width: 1630px;" >
					<iframe id="mainFrame" name="mainFrame" src=""
						style="overflow: visible;" scrolling="yes" frameborder="no"
						width="100%" height="856" allowfullscreen="true" allowtransparency="true"></iframe>
				</div>
			</div>
			<!--<div id="footer" class="row-fluid">
	            Copyright &copy; 2015-${fns:getConfig('copyrightYear')} ${fns:getConfig('productName')} - Powered By <a href="http://arjjs.com" target="_blank">ARJJS</a> ${fns:getConfig('version')}
			</div>  -->
			<!--  -->
		</div>
	</div>
	<script type="text/javascript">
	$('.pgwMenuCustom').pgwMenu({
		mainClassName: 'pgwMenuCustom',
		dropDownLabel: '菜单',
		viewMoreLabel: '<li class="icon-more-menu"></li><span style="margin-left: 18px;\n' +
				'\ttop: -14px;\n' +
				'\tposition: relative;">更多</span>'
	});
		var leftWidth = 200; // 左侧窗口大小
		var tabTitleHeight = 33; // 页签的高度
		var htmlObj = $("html"),bodyObj=$("body"), mainObj = $("#main");
		var headerObj = $("#header"), footerObj = $("#footer");
		var frameObj = $("#left, #openClose, #right, #right iframe");
		bodyObj.height($(window).height())
	    htmlObj.css('background',"url(${ctxStatic}/common/index/images/bg.jpg) no-repeat center")
	    htmlObj.css('background-size',"100% 100%")
		function wSize() {
			var minHeight = 500, minWidth = 980;
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({
				"overflow-x" : strs[1] < minWidth ? "auto" : "hidden",
				"overflow-y" : strs[0] < minHeight ? "auto" : "hidden"
			});
			bodyObj.height($(window).height())
		    htmlObj.css('background',"url(${ctxStatic}/common/index/images/bg.jpg) no-repeat center")
		     htmlObj.css('background-size',"100% 100%")
			mainObj.css("width", strs[1] < minWidth ? minWidth - 10 : "100%");
			//frameObj.height((strs[0] < minHeight ? minHeight : strs[0]) - headerObj.height() - footerObj.height() - (strs[1] < minWidth ? 42 : 28));
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0])
					- headerObj.height() - footerObj.height()
					- (strs[1] < minWidth ? 30 : 0));
			$("#openClose").height($("#openClose").height() - 5);// <c:if test="${tabmode eq '1'}"> 
			$(".jericho_tab iframe").height(
					$("#right").height() - tabTitleHeight); // </c:if>
			wSizeWidth();
		}
		function wSizeWidth() {
			if (!$("#openClose").is(":hidden")) {
				var leftWidth = ($("#left").width() < 0 ? 0 : $("#left")
						.width());
				$("#right").width(
						$("#content").width() - leftWidth
								- $("#openClose").width() - 25);
			} else {
				$("#right").width("100%");
			}
		}// <c:if test="${tabmode eq '1'}"> 
		function openCloseClickCallBack(b) {
			$.fn.jerichoTab.resize();
		} // </c:if>
		
		
		//详情弹框--不刷新父页面
		function LayerDialog(src, title, height, width){
			 layerIndex=parent.layer.open({
			  type: 2,
			  title: title,
			  area: [height, width],
			  fixed: true, //固定
			  maxmin: true,
			  id:'LayerDialog',
			   //btn: [ '确定',  '关闭'], //可以无限个按钮
			  content: src,
			  zIndex:'1992'
			});
		}
		//弹框--不刷新父页面  
		function LayerDialog2(src, title, height, width){
			 layerIndex=parent.layer.open({
			  type: 2,
			  title: title,
			  area: [height, width],
			  fixed: true, //固定
			  maxmin: false,
			/*   btn: ['关闭'], //可以无限个按钮 */
			  content: src,
			  zIndex:'1992'
			});
		}
		//详情弹框--不刷新父页面
		function LayerDialog3(src, title, height, width){
			parent.layer.open({
			  type: 2,
			  title: title,
			  area: [height, width],
			  fixed: true, //固定
			  maxmin: true,
			  id:'LayerDialog3',
			   //btn: [ '确定',  '关闭'], //可以无限个按钮
			  content: src,
			  zIndex:'1992',
			  success: function(layero, index){
				  var body = layer.getChildFrame('body', index);
                  //var iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                 // console.log(body.html()) //得到iframe页的body内容
                  body.find(".nav.nav-tabs").hide();
                  body.find(".form-actions").hide();
                  body.find(".fishBone1").hide();
                  body.find("h4").eq(2).hide();
                  
			  }
			});
		}
		//关闭弹窗
		function LayerCloseAll(){
			parent.layer.closeAll();
		}
		//获取打开弹窗列表的页面的location对象
		var $location=null
		function listlocationObject(location){
			 $location=location;
			 return false;
		}
		
		//刷新列表
       function refreshlist(){
			if( $location!=null){
				$location.reload() 

			}
	
		}  
		//详情弹框--不刷新父页面  删除 确定 关闭按钮
		function LayerDialog1(id,src, title, height, width){
			parent.layer.open({
			  type: 2,
			  title: title,
			  id:id||'',
			  area: [height, width],
			  fixed: true, //固定
			  maxmin: true,
			  btn: ['关闭'], //可以无限个按钮
			  content: src,
			  zIndex:'1992'
			});
		}
		function LayerDialog5(id,src, title, height, width){
			parent.layer.open({
			  type: 2,
			  title: title,
			  id:id||'',
			  area: [height, width],
			  fixed: true, //固定
			  maxmin: true,
			  // btn: ['关闭'], //可以无限个按钮
			  content: src,
			  zIndex:'1992'
			});
		}
		function alertInfo(strInfo) {
			top.$.jBox.tip(strInfo);

		}
		/*cppPopVehile执行子窗口的方法*/
		function cppPopVehile(type) {
			var iframeHtml = window.frames['mainFrame'];               //获取子窗口的句柄
            iframeHtml.saveRefresh(type);
		}
		
		/*cppPopPop执行子窗口的方法*/
		function cppPopPop(type) {
			var iframeHtml = window.frames['mainFrame'];               //获取子窗口的句柄
            iframeHtml.savePopRefresh();
		}
		$(function(){
			if($.cookie('theme')==undefined){
				$.cookie('theme','black',{ path: '/' })
			}
			var theme=$.cookie('theme');
			if(theme=="black"){
				   //粒子背景特效
			    $('body').particleground({
			        dotColor: '#E8DFE8',
			        lineColor: '#133b88'
			    });
					
			}else if(theme=="simple"){
				$("#menu .menu").on("mouseenter",function () {
					var pageY= $(this)[0].offsetLeft;//获取当前元素高度位置
					var pageWidth=$(this)[0].offsetWidth;
					$("#menu >span.layui-nav-bar").css({"left":pageY+"px"});
					$("#menu >span.layui-nav-bar").css({"width":pageWidth+"px"," opacity": "1"});
					}).on("mouseleave",function () {
						$("#menu >span.layui-nav-bar").css({"width":"0px"});
					});
				
			};
			
			
		})

	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
	<%@include file="/WEB-INF/views/include/layUI.jsp" %>
	<%@include file="/WEB-INF/views/layim/page/layim.jsp" %>
	
	
	
	
<%-- <script src="${ctxStatic}/dist/layui.js"></script> --%>
<!-- <script>

if(!/^http(s*):\/\//.test(location.href)){
  alert('请部署到localhost上查看该演示');
}

layui.use('layim', function(layim){
  
  //演示自动回复
  var autoReplay = [
    '您好，我现在有事不在，一会再和您联系。', 
    '你没发错吧？face[微笑] ',
    '不在外出中，请勿打扰，有事请留言face[哈哈] ',
    '你好，我是主人的美女秘书，有什么事就跟我说吧，等他回来我会转告他的。face[心] face[心] face[心] ',
    'face[威武] face[威武] face[威武] face[威武] ',
    '<（@￣︶￣@）>',
    '你要和我说话？你真的要和我说话？你确定自己想说吗？你一定非说不可吗？那你说吧，这是自动回复。',
    'face[黑线]  你慢慢说，别急……',
    '(*^__^*) face[嘻嘻] ，是贤心吗？'
  ];
  
  //基础配置
  layim.config({

    //初始化接口
    init: {
      url: '/arjccm/app/rest/chat/getList?id='+${fns:getUser().id}
      ,data: {}
    }
    
    //或采用以下方式初始化接口
    /*
    ,init: {
      mine: {
        "username": "LayIM体验者" //我的昵称
        ,"id": "100000123" //我的ID
        ,"status": "online" //在线状态 online：在线、hide：隐身
        ,"remark": "在深邃的编码世界，做一枚轻盈的纸飞机" //我的签名
        ,"avatar": "a.jpg" //我的头像
      }
      ,friend: []
      ,group: []
    }
    */
    

    //查看群员接口
    ,members: {
      url: '/arjccm/app/rest/chat/getMembers?id='
      ,data: {}
    }
    
    //上传图片接口
    ,uploadImage: {
      url: '/upload/image' //（返回的数据格式见下文）
      ,type: '' //默认post
    } 
    
    //上传文件接口
    ,uploadFile: {
      url: '/upload/file' //（返回的数据格式见下文）
      ,type: '' //默认post
    }
    
    ,isAudio: true //开启聊天工具栏音频
    ,isVideo: true //开启聊天工具栏视频
    
    //扩展工具栏
    ,tool: [{
      alias: 'code'
      ,title: '代码'
      ,icon: '&#xe64e;'
    }]
    
    //,brief: true //是否简约模式（若开启则不显示主面板）
    
    //,title: 'WebIM' //自定义主面板最小化时的标题
    //,right: '100px' //主面板相对浏览器右侧距离
    //,minRight: '90px' //聊天面板最小化时相对浏览器右侧距离
    ,initSkin: '5.jpg' //1-5 设置初始背景
    //,skin: ['aaa.jpg'] //新增皮肤
    //,isfriend: false //是否开启好友
    //,isgroup: false //是否开启群组
    //,min: true //是否始终最小化主面板，默认false
    ,notice: true //是否开启桌面消息提醒，默认false
    //,voice: false //声音提醒，默认开启，声音文件为：default.mp3
    
    ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
    ,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
    ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可
    
  });

  /*
  layim.chat({
    name: '在线客服-小苍'
    ,type: 'kefu'
    ,avatar: 'http://tva3.sinaimg.cn/crop.0.0.180.180.180/7f5f6861jw1e8qgp5bmzyj2050050aa8.jpg'
    ,id: -1
  });
  layim.chat({
    name: '在线客服-心心'
    ,type: 'kefu'
    ,avatar: 'http://tva1.sinaimg.cn/crop.219.144.555.555.180/0068iARejw8esk724mra6j30rs0rstap.jpg'
    ,id: -2
  });
  layim.setChatMin();*/

  //监听在线状态的切换事件
  layim.on('online', function(data){
    //console.log(data);
  });
  
  //监听签名修改
  layim.on('sign', function(value){
    //console.log(value);
  });

  //监听自定义工具栏点击，以添加代码为例
  layim.on('tool(code)', function(insert){
    layer.prompt({
      title: '插入代码'
      ,formType: 2
      ,shade: 0
    }, function(text, index){
      layer.close(index);
      insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
    });
  });
  
  //监听layim建立就绪
  layim.on('ready', function(res){

    //console.log(res.mine);
    
    layim.msgbox(5); //模拟消息盒子有新消息，实际使用时，一般是动态获得
  
    //添加好友（如果检测到该socket）
    layim.addList({
      type: 'group'
      ,avatar: "http://tva3.sinaimg.cn/crop.64.106.361.361.50/7181dbb3jw8evfbtem8edj20ci0dpq3a.jpg"
      ,groupname: 'Angular开发'
      ,id: "12333333"
      ,members: 0
    });
    layim.addList({
      type: 'friend'
      ,avatar: "http://tp2.sinaimg.cn/2386568184/180/40050524279/0"
      ,username: '刘杏梨'
      ,groupid: 2
      ,id: "1233333312121212"
      ,remark: "本人刘杏梨将结束外出工作"
    });
    
    setTimeout(function(){
      //接受消息（如果检测到该socket）
      layim.getMessage({
        username: "Hi"
        ,avatar: "http://qzapp.qlogo.cn/qzapp/100280987/56ADC83E78CEC046F8DF2C5D0DD63CDE/100"
        ,id: "10000111"
        ,type: "friend"
        ,content: "临时："+ new Date().getTime()
      });
      
      /*layim.getMessage({
        username: "贤心"
        ,avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1"
        ,id: "100001"
        ,type: "friend"
        ,content: "嗨，你好！欢迎体验LayIM。演示标记："+ new Date().getTime()
      });*/
      
    }, 3000);
  });

  //监听发送消息
  layim.on('sendMessage', function(data){
    var To = data.to;
    //console.log(data);
    
    if(To.type === 'friend'){
      layim.setChatStatus('<span style="color:#FF5722;">对方正在输入。。。</span>');
    }
    
    //演示自动回复
    setTimeout(function(){
      var obj = {};
      if(To.type === 'group'){
        obj = {
          username: '模拟群员'+(Math.random()*100|0)
          ,avatar: layui.cache.dir + 'images/face/'+ (Math.random()*72|0) + '.gif'
          ,id: To.id
          ,type: To.type
          ,content: autoReplay[Math.random()*9|0]
        }
      } else {
        obj = {
          username: To.name
          ,avatar: To.avatar
          ,id: To.id
          ,type: To.type
          ,content: autoReplay[Math.random()*9|0]
        }
        layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
      }
      layim.getMessage(obj);
    }, 1000);
  });

  //监听查看群员
  layim.on('members', function(data){
    //console.log(data);
  });
  
  //监听聊天窗口的切换
  layim.on('chatChange', function(res){
    var type = res.data.type;
    console.log(res.data.id)
    if(type === 'friend'){
      //模拟标注好友状态
      //layim.setChatStatus('<span style="color:#FF5722;">在线</span>');
    } else if(type === 'group'){
      //模拟系统消息
      layim.getMessage({
        system: true
        ,id: res.data.id
        ,type: "group"
        ,content: '模拟群员'+(Math.random()*100|0) + '加入群聊'
      });
    }
  });
  
  

});
</script> -->
</body>
</html>