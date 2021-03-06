]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">用户管理</span>--%>
<div class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/sys/role/">角色列表</a></li>
		<shiro:hasPermission name="sys:role:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/sys/role/form">角色添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<tr><th>角色名称</th><th>英文名称</th><th>归属机构</th><th>数据范围</th><shiro:hasPermission name="sys:role:edit"><th>操作</th></shiro:hasPermission></tr>
		<c:forEach items="${list}" var="role">
			<tr>
				<td style="height: 50px"><a href="form?id=${role.id}">${role.name}</a></td>
				<td style="height: 50px"><a href="form?id=${role.id}">${role.enname}</a></td>
				<td style="height: 50px">${role.office.name}</td>
				<td style="height: 50px">${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
				<shiro:hasPermission name="sys:role:edit"><td style="height: 50px">
					<a class="btnList" href="${ctx}/sys/role/assign?id=${role.id}" title="分配"><i class="icon-plus"></i></a>
					<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
						<a class="btnList" href="${ctx}/sys/role/form?id=${role.id}" title="修改"><i class="icon-pencil"></i></a>
					</c:if>
					<a class="btnList"  href="${ctx}/sys/role/delete?id=${role.id}" onclick="return confirmx('确认要删除该角色吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>	
			</tr>
		</c:forEach>
	</table>
</body>
</html>