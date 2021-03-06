<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>警情通知记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
<div class="back-list">
	<form:form id="searchForm" modelAttribute="bphAlarmNotify" action="${ctx}/alarmnotify/bphAlarmNotify/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>接警人员：</label>
				<form:input path="receiveUserName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bph_alarm_info_typecode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('read_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>接警人员</th>
				<th>类型</th>
				<th>内容</th>
				<th>状态</th>
	<%--			<th>创建者</th>
				<th>创建时间</th>
				<th>更新者</th>--%>
				<th>更新时间</th>
				<%--<th>备注信息</th>--%>
        <%--	<shiro:hasPermission name="alarmnotify:bphAlarmNotify:edit"><th>操作</th></shiro:hasPermission>--%>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bphAlarmNotify">
			<tr>
				<td style="height: 50px"><%--<a href="${ctx}/alarmnotify/bphAlarmNotify/form?id=${bphAlarmNotify.id}">--%>
					${bphAlarmNotify.receiveUserName}
				</a></td>
				<td style="height: 50px">
					${fns:getDictLabel(bphAlarmNotify.type, 'bph_alarm_info_typecode', '')}
				</td>
				<td style="height: 50px">
					${bphAlarmNotify.content}
				</td>
				<td style="height: 50px">
					${fns:getDictLabel(bphAlarmNotify.status, 'read_flag', '')}
				</td>
				<%--<td>
					${bphAlarmNotify.createBy.id}
				</td>
				<td>
					<fmt:formatDate value="${bphAlarmNotify.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${bphAlarmNotify.updateBy.id}
				</td>--%>
				<td style="height: 50px">
					<fmt:formatDate value="${bphAlarmNotify.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
					<%--<td>
                        ${bphAlarmNotify.remarks}
                    </td>--%>
			<%--	<shiro:hasPermission name="alarmnotify:bphAlarmNotify:edit"><td>
    				&lt;%&ndash;<a href="${ctx}/alarmnotify/bphAlarmNotify/form?id=${bphAlarmNotify.id}">修改</a>&ndash;%&gt;
					<a href="${ctx}/alarmnotify/bphAlarmNotify/delete?id=${bphAlarmNotify.id}" onclick="return confirmx('确认要删除该警情通知记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>--%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</div>
</body>
</html>