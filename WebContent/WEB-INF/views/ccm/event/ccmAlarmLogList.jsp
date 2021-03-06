<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>告警日志管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript"
			src="${ctxStatic}/plm/storage/ajaxMessageAlert.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click" ,function(){
				var begin = new Date($("[name='beginCreateDate']").val());
				var end = new Date($("[name='endCreateDate']").val());
				if(begin.getTime() > end.getTime()){
					messageAlert("开始时间大于结束时间！", "error");
					return false;
				}
				$("#searchForm").submit();
			})
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/event/ccmAlarmLog/">告警日志列表</a></li>
	<!-- <shiro:hasPermission name="event:ccmAlarmLog:edit"><li><a href="${ctx}/event/ccmAlarmLog/form">告警日志添加</a></li></shiro:hasPermission> -->	
	</ul>
	<form:form id="searchForm" modelAttribute="ccmAlarmLog" action="${ctx}/event/ccmAlarmLog/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>告警类型：</label>
				<form:select path="alarmType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_alarm_log_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>发生时间：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmAlarmLog.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmAlarmLog.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>描述信息：</label>
				<form:input path="remarks" htmlEscape="false" maxlength="255" class="input-medium"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
			<li class="btns">
			<a href="javascript:;" id="btnSubmit" class="btn btn-primary">
                <i class="icon-search"></i> 查询 </a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<%--<th>对象id</th>--%>
				<th>告警类型</th>
				<th>发生时间</th>
				<th>截至时间</th>
				<th>描述信息</th>
				<th>操作</th>
				<!--<shiro:hasPermission name="event:ccmAlarmLog:edit"><th>操作</th></shiro:hasPermission>-->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmAlarmLog">
			<tr>
				<%--<td><a href="${ctx}/event/ccmAlarmLog/form?id=${ccmAlarmLog.id}">--%>
					<%--${ccmAlarmLog.objId}--%>
				<%--</a></td>--%>
				<td><a href="${ctx}/event/ccmAlarmLog/form?id=${ccmAlarmLog.id}">
						${ccmAlarmLog.objId}
				</a>
					${fns:getDictLabel(ccmAlarmLog.alarmType, 'ccm_alarm_log_type', '')}
				</td>
				<td>
					<fmt:formatDate value="${ccmAlarmLog.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${ccmAlarmLog.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${ccmAlarmLog.remarks}
				</td>
				<td>
					<c:if test="${not empty ccmAlarmLog.id && ccmAlarmLog.alarmType=='01'}">
    					<a onclick="parent.LayerDialog1('','${ctx}/event/ccmAlarmLog/ToMap?id=${ccmAlarmLog.id}',
    						'${ccmAlarmLog.createBy.name}:<fmt:formatDate value="${ccmAlarmLog.createDate}" pattern="yyyy-MM-dd"/>轨迹点位',
    						'1000px', '600px')">当日轨迹点位</a>
    				</c:if>
				</td>
				<shiro:hasPermission name="event:ccmAlarmLog:edit"><td>
    				<a href="${ctx}/event/ccmAlarmLog/form?id=${ccmAlarmLog.id}">修改</a>
					<a href="${ctx}/event/ccmAlarmLog/delete?id=${ccmAlarmLog.id}" onclick="return confirmx('确认要删除该告警日志吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>