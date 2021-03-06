<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>巡逻结果管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#btnSubmit").on("click" ,function(){
			$("#searchForm").submit();
		})
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/patrol/ccmPatrolResult/">数据列表</a></li>
		<%-- <shiro:hasPermission name="patrol:ccmPatrolResult:edit">
			<li><a href="${ctx}/patrol/ccmPatrolResult/form">巡逻结果添加</a></li>
		</shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmPatrolResult"
		action="${ctx}/patrol/ccmPatrolResult/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>计划名称：</label> <form:input path="plan.name"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li><label>本次巡逻结果：</label> <form:select path="status"
					class="input-medium">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('ccm_patrol_resttype')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查询" /></li> -->
					<li class="btns">
			<a href="javascript:;" id="btnSubmit" class="btn btn-primary">
                <i class="icon-search"></i> 查询 </a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>巡逻结果编号</th>
				<th>计划名称</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>本次巡逻结果</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="patrol:ccmPatrolResult:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ccmPatrolResult">
				<tr>
					<td><a
						href="${ctx}/patrol/ccmPatrolResult/form?id=${ccmPatrolResult.id}">
							${ccmPatrolResult.name} </a></td>
					<td><a
						href="${ctx}/patrol/ccmPatrolResult/form?id=${ccmPatrolResult.id}">
							${ccmPatrolResult.plan.name} </a></td>
					<td><fmt:formatDate value="${ccmPatrolResult.beginDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${ccmPatrolResult.endDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${fns:getDictLabel(ccmPatrolResult.status, 'ccm_patrol_resttype', '')}
					</td>
					<td><fmt:formatDate value="${ccmPatrolResult.updateDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${ccmPatrolResult.remarks}</td>
					<shiro:hasPermission name="patrol:ccmPatrolResult:edit">
						<td><a class="btnList"
							href="${ctx}/patrol/ccmPatrolResult/form?id=${ccmPatrolResult.id}" title="修改"><i class="icon-pencil"></i></a>
							<a class="btnList"
							href="${ctx}/patrol/ccmPatrolResult/delete?id=${ccmPatrolResult.id}"
							onclick="return confirmx('确认要删除该巡逻结果吗？', this.href)" title="删除"><i class="icon-trash"></i></a>
						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>