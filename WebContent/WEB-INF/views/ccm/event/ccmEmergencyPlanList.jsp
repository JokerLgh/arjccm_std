<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>应急预案管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click" ,function(){
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
		<li class="active"><a href="${ctx}/event/ccmEmergencyPlan/">应急预案列表</a></li>
		<shiro:hasPermission name="event:ccmEmergencyPlan:edit"><li><a href="${ctx}/event/ccmEmergencyPlan/form">应急预案添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmEmergencyPlan" action="${ctx}/event/ccmEmergencyPlan/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>预案名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>事件分级：</label>
				<form:select path="eventScale" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_case_grad')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>事件类型：</label>
				<form:select path="eventType" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_case_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
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
				<th>预案名称</th>
				<th>事件分级</th>
				<th>事件类型</th>
				<th>工作原则</th>
				<shiro:hasPermission name="event:ccmEmergencyPlan:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmEmergencyPlan">
			<tr>
				<td><a href="${ctx}/event/ccmEmergencyPlan/form?id=${ccmEmergencyPlan.id}">
					${ccmEmergencyPlan.name}
				</a></td>
				<td>
					${fns:getDictLabel(ccmEmergencyPlan.eventScale, 'ccm_case_grad', '')}
				</td>
				<td>
					${fns:getDictLabel(ccmEmergencyPlan.eventType, 'ccm_case_type', '')}
				</td>
				<td>
					${ccmEmergencyPlan.principle}
				</td>
				<shiro:hasPermission name="event:ccmEmergencyPlan:edit"><td style="min-width: 130px;">
    				<a class="btnList" href="${ctx}/event/ccmEmergencyPlan/form?id=${ccmEmergencyPlan.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/event/ccmEmergencyPlan/delete?id=${ccmEmergencyPlan.id}" onclick="return confirmx('确认要删除该应急预案吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>