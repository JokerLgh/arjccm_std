<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>综治中心管理</title>
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
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">综治组织</span>--%>
<ul class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 112px"><a  class="nav-head" href="${ctx}/org/ccmOrgSyncentre/">数据列表</a></li>
		<shiro:hasPermission name="org:ccmOrgSyncentre:edit"><li style="width: 112px"><a href="${ctx}/org/ccmOrgSyncentre/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmOrgSyncentre" action="${ctx}/org/ccmOrgSyncentre/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>综治中心名称：</label>
				<form:input path="centreName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="first-line"><label>综治中心层级：</label>
				<form:select path="centreScale" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_ply_rat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="first-line"><label>负责人姓名：</label>
				<form:input path="respName" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->

<%--			<li class="clearfix"></li>--%>
		</ul>


	<div class="clearfix pull-right btn-box">

			<a href="javascript:;" id="btnSubmit" class="btn btn-primary" style="width: 49px;display:inline-block;float: right;">
				<i></i> <span style="font-size: 12px">查询</span> </a>

	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<thead>
			<tr>
				<th>综治中心名称</th>
				<th>综治中心联系方式</th>
				<th>综治中心层级</th>
				<th>负责人姓名</th>
				<th>负责人联系方式</th>
				<shiro:hasPermission name="org:ccmOrgSyncentre:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmOrgSyncentre">
			<tr>
				<td style="height: 50px"><a href="${ctx}/org/ccmOrgSyncentre/form?id=${ccmOrgSyncentre.id}">
					${ccmOrgSyncentre.centreName}
				</a></td>
				<td style="height: 50px">
					${ccmOrgSyncentre.centreTel}
				</td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmOrgSyncentre.centreScale, 'ccm_ply_rat', '')}
				</td>
				<td style="height: 50px">
					${ccmOrgSyncentre.respName}
				</td>
				<td style="height: 50px">
					${ccmOrgSyncentre.respTel}
				</td>
				<shiro:hasPermission name="org:ccmOrgSyncentre:edit"><td style="height: 50px">
    				<a class="btnList" href="${ctx}/org/ccmOrgSyncentre/form?id=${ccmOrgSyncentre.id}"  title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/org/ccmOrgSyncentre/delete?id=${ccmOrgSyncentre.id}" onclick="return confirmx('确认要删除该综治中心吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</ul>
</body>
</html>