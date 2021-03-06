<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车辆布控记录管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
<script src="${ctxStatic}/ccm/event/js/ccmEventIncident.js"
	type="text/javascript"></script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/car/ccmCarControl/">数据列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmCarControl"
		action="${ctx}/car/ccmCarControl/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>车牌号码：</label> <form:input path="plateNumber"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li><label>车牌颜色：</label> <form:select path="plateColor"
					class="input-large">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_plate_color')}"
						itemLabel="label" itemValue="value" htmlEscape="false"
						maxlength="2" class="input-medium" />
				</form:select></li>
			<li class="btns"><a
				onclick="parent.LayerDialog('${ctx}/car/ccmCarControl/form', '添加', '1100px', '700px')"
				class="btn btn-success"><i class="icon-plus"></i> 添加</a></li>
			<li class="btns"><a href="javascript:;" id="btnSubmit"
				class="btn btn-primary"> <i class="icon-search"></i> 查询
			</a></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>车牌号码</th>
				<th>车牌颜色</th>
				<th>布控开始时间</th>
				<th>布控结束时间</th>
				<th>布控原因</th>
				<th>更新时间</th>
				<th>布控描述</th>
				<shiro:hasPermission name="car:ccmCarControl:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ccmCarControl">
				<tr>
					<td><a class="btnList"
						onclick="parent.LayerDialog('${ctx}/car/ccmCarControl/form?id=${ccmCarControl.id}', '编辑', '1100px', '700px')">
							${ccmCarControl.plateNumber}</a></td>
					<td>${fns:getDictLabel(ccmCarControl.plateColor, 'ccm_plate_color', '无')}</td>
					<td><fmt:formatDate value="${ccmCarControl.startTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${ccmCarControl.endTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${fns:getDictLabel(ccmCarControl.controlReason, 'ccm_controller_reason', '无')}</td>
					<td><fmt:formatDate value="${ccmCarControl.updateDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${ccmCarControl.remarks}</td>
					<shiro:hasPermission name="car:ccmCarControl:edit">
						<td><a class="btnList"
							onclick="parent.LayerDialog('${ctx}/car/ccmCarControl/form?id=${ccmCarControl.id}', '编辑', '1100px', '700px')"
							title="修改"><i class="icon-pencil"></i></a> <a class="btnList"
							href="${ctx}/car/ccmCarControl/delete?id=${ccmCarControl.id}"
							onclick="return confirmx('确认要删除该车辆布控记录吗？', this.href)" title="删除"><i
								class="icon-trash"></i></a></td>
					</shiro:hasPermission>



				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>