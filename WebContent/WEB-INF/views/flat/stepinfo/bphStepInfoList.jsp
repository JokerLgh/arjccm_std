<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预案过程管理</title>
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
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/stepinfo/bphStepInfo/">数据列表</a></li>
		<shiro:hasPermission name="stepinfo:bphStepInfo:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/stepinfo/bphStepInfo/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bphStepInfo" action="${ctx}/stepinfo/bphStepInfo/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>过程名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li class="first-line"><label>已关联预案：</label>
				<form:select class="input-medium" path="bphPlanInfo.name">
					<option value="">全部</option>
					<c:forEach items="${planList}" var="bphPlanInfo">
					<option  itemValue="${bphPlanInfo.name}" htmlEscape="false">${bphPlanInfo.name}</option>
					</c:forEach>
				</form:select>
			</li>
<%--			<li class="btns">--%>
<%--			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> -->--%>

<%--			</li>--%>
<%--			<li class="clearfix"></li>--%>
		</ul>


	<div class="clearfix pull-right btn-box">
		<a href="javascript:;" id="btnSubmit" style="width: 49px;margin-right: 14px;
    margin-bottom: 20px;/*margin-top: 25px;*/display:inline-block;float: right;margin-left: 20px" class="btn btn-primary">
			<%--<i class="icon-search"></i> --%><span style="font-size: 12px">查询</span> </a>
	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient" style="table-layout:fixed;">
		<thead>
			<tr>
				<th style="width:10%">序号</th>
				<th style="width:20%">过程名称</th>
				<th style="width:30%">已关联预案</th>
				<th style="width:20%">已关联执行动作</th>
				<shiro:hasPermission name="stepinfo:bphStepInfo:edit"><th style="width:10%">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bphStepInfo" varStatus="status">
			<tr>
				<td style="height: 50px">${status.index+1}</td>
				<td style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;height: 50px" title="${bphStepInfo.name}"><a href="${ctx}/stepinfo/bphStepInfo/form?id=${bphStepInfo.id}">${bphStepInfo.name}</a></td>
				<td style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;height: 50px" title="${bphStepInfo.bphPlanInfo.name}">${bphStepInfo.bphPlanInfo.name}</td>
				<td style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;height: 50px" title="${bphStepInfo.bphActionInfo.name}">${bphStepInfo.bphActionInfo.name}</td>
				<shiro:hasPermission name="stepinfo:bphStepInfo:edit">
					<td style="height: 50px">
	    				<a class="btnList" href="${ctx}/stepinfo/bphStepInfo/form?id=${bphStepInfo.id}" title="修改"><i class="icon-pencil"></i></a>
						<a class="btnList" href="${ctx}/stepinfo/bphStepInfo/delete?id=${bphStepInfo.id}" onclick="return confirmx('确认要删除该预案过程吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>