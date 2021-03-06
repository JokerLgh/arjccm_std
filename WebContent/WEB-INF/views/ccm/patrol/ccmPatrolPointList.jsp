<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡逻点位管理</title>
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
		<li class="active"><a href="${ctx}/patrol/ccmPatrolPoint/">数据列表</a></li>
		<shiro:hasPermission name="patrol:ccmPatrolPoint:edit"><li><a href="${ctx}/patrol/ccmPatrolPoint/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmPatrolPoint" action="${ctx}/patrol/ccmPatrolPoint/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>坐标：</label>
				<form:input path="areaPoint" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>名称</th>
				<th>坐标</th>
				<th>属性</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="patrol:ccmPatrolPoint:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmPatrolPoint">
			<tr>
				<td><a href="${ctx}/patrol/ccmPatrolPoint/form?id=${ccmPatrolPoint.id}">
					${ccmPatrolPoint.name}
				</a></td>
				<td>
					${ccmPatrolPoint.areaPoint}
				</td>
				<td>
					${ccmPatrolPoint.property}
				</td>
				<td>
					<fmt:formatDate value="${ccmPatrolPoint.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${ccmPatrolPoint.remarks}
				</td>
				<shiro:hasPermission name="patrol:ccmPatrolPoint:edit"><td>
    				<a class="btnList" href="${ctx}/patrol/ccmPatrolPoint/form?id=${ccmPatrolPoint.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/patrol/ccmPatrolPoint/delete?id=${ccmPatrolPoint.id}" onclick="return confirmx('确认要删除该巡逻点位吗？', this.href)" title="删除"><i class="icon-trash"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>