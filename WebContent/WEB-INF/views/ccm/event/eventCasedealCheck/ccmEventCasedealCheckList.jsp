<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>事件处理考核</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/layui/css/layui.css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/layui/layui.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/ccm/event/css/eventCasedeal.css" type="text/css">
	<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
	<script src="${ctxStatic}/ccm/event/js/eventCasedeal.js" type="text/javascript"></script>
</head>
<body>
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">事件管理</span>--%>
<ul class="back-list">
<div class="context" content="${ctx}"></div>
	<ul class="nav nav-tabs">
		<li class="active"><a class="nav-head" href="${ctx}/event/ccmEventCasedealCheck/">评分考核列表</a></li>

	</ul>
	<form:form id="searchForm" modelAttribute="ccmEventCasedeal"
		action="${ctx}/event/ccmEventCasedealCheck/" method="post"
		class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form pull-left">
			<li class="first-line"><label>事件名称：</label> <form:input path="caseName"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li class="first-line"><label>分类：</label>
				<form:select path="objType" class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_event_obj_table')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="first-line"><label>事件状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_event_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="first-line"><label>处理人员：</label>
				<sys:treeselect id="handleUser" name="handleUser.id" value="${ccmEventCasedeal.handleUser.id}" labelName="handleUser.name" labelValue="${ccmEventCasedeal.handleUser.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="first-line"><label>是否督办：</label>
				<form:select path="isSupervise" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="second-line"><label>处理状态：</label>
				<form:select path="handleStatus" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_task_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
<%--			<li><label>处理截至开始时间：</label>--%>
<%--				<input name="beginHandleDeadline" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
<%--					value="<fmt:formatDate value="${ccmEventCasedeal.beginHandleDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
<%--					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> </li>--%>
<%--			<li>--%>
<%--				<label>处理截至结束时间：</label>--%>
<%--				<input name="endHandleDeadline" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"--%>
<%--					value="<fmt:formatDate value="${ccmEventCasedeal.endHandleDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
<%--					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
<%--			</li>--%>
			<li class="second-line"><label>是否评价：</label>
				<form:radiobuttons name="evaluate" path="evaluate" items="${fns:getDictList('yes_no')}"
					itemLabel="label" itemValue="value" htmlEscape="false" class="" />
			</li>

<%--			<li class="clearfix"></li>--%>
		</ul>

	<sys:message content="${message}" />
	<div class="clearfix pull-right btn-box">

			<a href="javascript:;" id="btnSubmit" class="btn btn-primary" style="width: 49px;display:inline-block;float: right;">
				<i></i><span style="font-size: 12px">查询</span>  </a>

	</div>
	</form:form>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed table-gradient">
		<thead>
			<tr>
				<th>事件名称</th>
				<th>分类</th>
				<th>事件状态</th>
				<th>处理人员</th>
<%--				<th>处理截至时间</th>--%>
				<th>是否督办</th>
				<th>任务处理状态</th>
				<th>考评日期</th>
				<th>评分等级（满评五星）</th>
				<shiro:hasPermission name="event:ccmEventCasedealCheck:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ccmEventCasedeal">
				<tr>
				
					<td style="height: 50px">
						<c:if test="${ccmEventCasedeal.objType eq 'ccm_event_incident'}">
							<a onclick="parent.LayerDialog('${ctx}/event/ccmEventIncident/detail?id=${ccmEventCasedeal.objId}', '案事件详情', '1100px', '700px')">
								${ccmEventCasedeal.caseName} </a>
						</c:if>
						<c:if test="${ccmEventCasedeal.objType eq 'ccm_event_ambi'}">
							<a onclick="parent.LayerDialog('${ctx}/event/ccmEventAmbi/detail?id=${ccmEventCasedeal.objId}', '矛盾纠纷详情', '1100px', '700px')">
								${ccmEventCasedeal.caseName} </a>
						</c:if>
						<c:if test="${ccmEventCasedeal.objType eq 'ccm_event_request'}">
							<a onclick="parent.LayerDialog('${ctx}/event/ccmEventRequest/detail?id=${ccmEventCasedeal.objId}', '请求详情', '1100px', '700px')">
								${ccmEventCasedeal.caseName} </a>
						</c:if>
						<c:if test="${ccmEventCasedeal.objType eq 'ccm_wechat_event'}">
							<a onclick="parent.LayerDialog('${ctx}/event/wechat/ccmWechatEvent/detail?id=${ccmEventCasedeal.objId}', '微信上报详情', '1100px', '700px')">
								${fns:abbr(ccmEventCasedeal.caseName,15)}</a>
						</c:if>
					</td>
					 
					<td style="height: 50px">
						${fns:getDictLabel(ccmEventCasedeal.objType, 'ccm_event_obj_table', '')}
					</td>
					<td style="height: 50px">
					<span class="eventScaleCss statusCss-${ccmEventCasedeal.status}">
						${fns:getDictLabel(ccmEventCasedeal.status, 'ccm_event_status', '')}
						</span>
					</td>
					<td style="height: 50px">
						${ccmEventCasedeal.handleUser.name}
					</td>
					
					
<%--					<td>--%>
<%--						<c:choose>--%>
<%--							<c:when test="${ccmEventCasedeal.isExtension eq '1'}">--%>
<%--								<span style="color:#F00">--%>
<%--									<fmt:formatDate value="${ccmEventCasedeal.handleDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;超期！--%>
<%--								</span>--%>
<%--							</c:when>--%>
<%--							<c:otherwise>--%>
<%--								<fmt:formatDate value="${ccmEventCasedeal.handleDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
<%--							</c:otherwise>--%>
<%--						</c:choose>--%>
<%--					</td>--%>

					
					<td style="height: 50px">
						${fns:getDictLabel(ccmEventCasedeal.isSupervise, 'yes_no', '')}
					</td>
					<td style="height: 50px">
					<span class="eventScaleCss statusCss-${ccmEventCasedeal.handleStatus}">
						${fns:getDictLabel(ccmEventCasedeal.handleStatus, 'ccm_task_status', '')}
						</span>
					</td>
					<td style="height: 50px">
						<fmt:formatDate value="${ccmEventCasedeal.checkDate}" pattern="yyyy-MM-dd"/>
					</td>
					<td style="height: 50px">
					<div class="layui-rate" id="layui-rate-${ccmEventCasedeal.id}"></div>
					<script>
					layui.use(['rate'], function(){
						var gradeNum = '${ccmEventCasedeal.gradeNum}';
						if(gradeNum === undefined && gradeNum == null && gradeNum == ''){
							gradeNum = 0;
						}
						var rate = layui.rate;
						 //自定义文本
						  rate.render({
						    elem: '#layui-rate-${ccmEventCasedeal.id}'
						    ,value: gradeNum
						    ,length: gradeNum
						    ,readonly: true
						  });
					});
					</script>
					</td>
					<shiro:hasPermission name="event:ccmEventCasedealCheck:edit">
						<td style="height: 50px">
					
						<a  class="btnList" 
							onclick="parent.LayerDialog('${ctx}/event/ccmEventCasedealCheck/form?id=${ccmEventCasedeal.id}','处理', '1100px', '700px')"><i class="icon-pencil"></i></a>
						<a  class="btnList"
							href="${ctx}/event/ccmEventCasedealCheck/delete?id=${ccmEventCasedeal.id}"
							onclick="return confirmx('确认要删除该事件处理吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				        <a class="btnList" href="javascript:;" onclick="LocationOpen('${ccmEventCasedeal.id}')"  title="位置信息"><i class="icon-map-marker "></i></a>
						</td>
					</shiro:hasPermission>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</ul>
</body>
</html>