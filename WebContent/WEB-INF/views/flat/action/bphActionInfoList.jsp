<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>执行动作配置管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/alarm.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#btnSubmit').click(function(){
				$('#searchForm').submit();
			});
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
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">预案管理</span>--%>
<div class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/action/bphActionInfo/">执行动作列表</a></li>
		<shiro:hasPermission name="action:bphActionInfo:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/action/bphActionInfo/form">执行动作添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bphActionInfo" action="${ctx}/action/bphActionInfo/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>动作名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<%-- <shiro:hasPermission name="action:bphActionInfo:edit"><li class="btns"><a class="btn btn-primary" type="button" href="${ctx}/action/bphActionInfo/form">新增</a></li></shiro:hasPermission> --%>
			<li class="first-line"><label>已关联过程：</label>
				<form:select class="input-medium" path="bphStepInfo.name">
					<option value="">全部</option>
					<c:forEach items="${stepList}" var="bphStepInfo">
					<option  itemValue="${bphStepInfo.name}" htmlEscape="false">${bphStepInfo.name}</option>
					</c:forEach>
				</form:select>
			</li>
<%--			<li class="btns">--%>
<%--			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>  -->--%>
<%--			</li>--%>
<%--			<li class="clearfix"></li>--%>
		</ul>


	<div class="clearfix pull-right btn-box">
		<a href="javascript:;" id="btnSubmit" style="width: 49px;
    /*margin-top: 25px;*/display:inline-block;float: right;" class="btn btn-primary">
			<%--<i class="icon-search"></i> --%><span style="font-size: 12px">查询</span> </a>
	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient" style="table-layout:fixed;">
		<thead>
			<tr>
				<th>序号</th>
				<th>动作名称</th>
				<th>动作类型</th>
				<th>默认通知标题</th>
				<th>已关联过程</th>
				<th>动作执行次数</th>
				<shiro:hasPermission name="action:bphActionInfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bphActionInfo" varStatus="status">
			<tr>
				<td style="height: 50px">${status.index+1}</td>
				<td style="height: 50px">${bphActionInfo.name}</td>
				<td style="height: 50px">${fns:getDictLabel(bphActionInfo.type, 'bph_action_type', '')}</td>
				<td style="height: 50px">${bphActionInfo.title}</td>
				<td style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;height: 50px" title="${bphActionInfo.bphStepInfo.name}">${bphActionInfo.bphStepInfo.name}</td>
				<td style="height: 50px">${bphActionInfo.num}</td>
				<shiro:hasPermission name="action:bphActionInfo:edit">
					<td style="height: 50px">
	    				<a class="btnList" href="${ctx}/action/bphActionInfo/form?id=${bphActionInfo.id}" title="修改"><i class="icon-pencil"></i></a>
						<a class="btnList" href="${ctx}/action/bphActionInfo/delete?id=${bphActionInfo.id}" onclick="return confirmx('确认要删除该执行动作配置吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>