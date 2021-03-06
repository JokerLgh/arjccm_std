<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数字化预案管理</title>
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
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/planinfo/bphPlanInfo/">数据列表</a></li>
		<shiro:hasPermission name="planinfo:bphPlanInfo:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/planinfo/bphPlanInfo/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="bphPlanInfo" action="${ctx}/planinfo/bphPlanInfo/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>预案名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>
			<li class="first-line"><label>警情类型：</label>
				<form:select path="typeCode" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('bph_alarm_info_typecode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
<%--			<li class="btns">--%>
<%--			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> -->--%>
<%--			</li>--%>
<%--			<li class="clearfix"></li>--%>
		</ul>


	<div class="clearfix pull-right btn-box">
		<a href="javascript:;" id="btnSubmit" style="width: 49px;
   /*margin-top: 25px;*/display:inline-block;float: right" class="btn btn-primary">
			<%--<i class="icon-search"></i> --%><span style="font-size: 12px">查询</span> </a>
	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient" style="table-layout:fixed;">
		<thead>
			<tr>
				<th style="width:10%">序号</th>
				<th style="width:15%">预案名称</th>
				<th style="width:10%">警情级别</th>
				<th style="width:10%">警情类型</th>
				<th style="width:20%">已关联过程</th>
				<!-- <th style="width:10%">预案启动数</th> -->
				<shiro:hasPermission name="planinfo:bphPlanInfo:edit"><th style="width:10%">操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="bphPlanInfo" varStatus="status">
			<tr>
				<td style="height: 50px">${status.index+1}</td>
				<td style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;height: 50px;" title="${bphPlanInfo.name}"><a href="${ctx}/planinfo/bphPlanInfo/form?id=${bphPlanInfo.id}">${bphPlanInfo.name}</a></td>
				<td style="height: 50px">${fns:getDictLabel(bphPlanInfo.isImportant, 'bph_plan_alarm_level', '')}</td>
				<td style="height: 50px">${fns:getDictLabel(bphPlanInfo.typeCode, 'bph_alarm_info_typecode', '')}</td>
				<%-- <td title="${bphPlanInfo.bphStepInfo.name}"><p style="width: 300px;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;">${bphPlanInfo.bphStepInfo.name}</p></td> --%>
				<td style="white-space: nowrap;text-overflow: ellipsis;overflow: hidden;height: 50px" title="${bphPlanInfo.bphStepInfo.name}">${bphPlanInfo.bphStepInfo.name}</td>
				<%-- <td>${bphPlanInfo.num}</td> --%>
				<shiro:hasPermission name="planinfo:bphPlanInfo:edit">
					<td style="height: 50px">
	    				<a class="btnList" href="${ctx}/planinfo/bphPlanInfo/form?id=${bphPlanInfo.id}" title="修改"><i class="icon-pencil"></i></a>
						<a class="btnList" href="${ctx}/planinfo/bphPlanInfo/delete?id=${bphPlanInfo.id}" onclick="return confirmx('确认要删除该数字化预案吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>