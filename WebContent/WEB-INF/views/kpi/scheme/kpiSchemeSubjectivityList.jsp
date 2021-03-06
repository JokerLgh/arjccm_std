<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效主观评分管理</title>
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
		<li class="active"><a href="${ctx}/scheme/kpiSchemeSubjectivity/">数据列表</a></li>
		<shiro:hasPermission name="scheme:kpiSchemeSubjectivity:edit"><li><a href="${ctx}/scheme/kpiSchemeSubjectivity/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="kpiSchemeSubjectivity" action="${ctx}/scheme/kpiSchemeSubjectivity/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>被考核人：</label>
				<sys:treeselect id="userId" name="userId.id" value="${kpiSchemeSubjectivity.userId.id}" labelName="userId.name" labelValue="${kpiSchemeSubjectivity.userId.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>评分人：</label>
				<sys:treeselect id="scorerId" name="scorerId.id" value="${kpiSchemeSubjectivity.scorerId.id}" labelName="scorerId.name" labelValue="${kpiSchemeSubjectivity.scorerId.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>KPI编号：</label>
				<form:input path="kpiId" htmlEscape="false" maxlength="64" class="input-medium"/>
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
				<th>被考核人</th>
				<th>评分人</th>
				<th>KPI编号</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="scheme:kpiSchemeSubjectivity:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="kpiSchemeSubjectivity">
			<tr>
				<td><a href="${ctx}/scheme/kpiSchemeSubjectivity/form?id=${kpiSchemeSubjectivity.id}">
					${kpiSchemeSubjectivity.userId.name}
				</a></td>
				<td>
					${kpiSchemeSubjectivity.scorerId.name}
				</td>
				<td>
					${kpiSchemeSubjectivity.kpiId}
				</td>
				<td>
					<fmt:formatDate value="${kpiSchemeSubjectivity.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${kpiSchemeSubjectivity.remarks}
				</td>
				<shiro:hasPermission name="scheme:kpiSchemeSubjectivity:edit"><td>
    				<a href="${ctx}/scheme/kpiSchemeSubjectivity/form?id=${kpiSchemeSubjectivity.id}">修改</a>
					<a href="${ctx}/scheme/kpiSchemeSubjectivity/delete?id=${kpiSchemeSubjectivity.id}" onclick="return confirmx('确认要删除该绩效主观评分吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>