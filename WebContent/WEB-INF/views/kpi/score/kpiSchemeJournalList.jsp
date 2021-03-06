<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>特殊考核项管理</title>
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
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">绩效考核</span>--%>
<div class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/score/kpiSchemeJournal/">数据列表</a></li>
		<shiro:hasPermission name="score:kpiSchemeJournal:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/score/kpiSchemeJournal/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="kpiSchemeJournal" action="${ctx}/score/kpiSchemeJournal/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>被考核人：</label>
				<sys:treeselect id="user" name="user.id" value="${kpiSchemeJournal.user.id}" labelName="user.name" labelValue="${kpiSchemeJournal.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="first-line"><label>通报形式：</label>
				<form:input path="notifyType" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="first-line"><label>通报人姓名：</label>
				<form:input path="notifyStaffName" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="first-line"><label>通报开始日期：</label>
				<input name="beginNotifyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${kpiSchemeJournal.beginNotifyDate}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> </li>
			<li class="first-line"><label>通报结束日期：</label>	<input name="endNotifyDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${kpiSchemeJournal.endNotifyDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</li>
<!-- 			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
	</ul>



	<div class="clearfix pull-right btn-box">
		<a href="javascript:;" id="btnSubmit" class="btn btn-primary" style="width: 49px;display:inline-block;float: right;">
			<i></i><span style="font-size: 12px">查询</span>  </a>
	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<thead>
			<tr>
				<th>被考核人</th>
				<th>分数</th>
				<th>通报日期</th>
				<th>通报形式</th>
				<th>通报人姓名</th>
				<shiro:hasPermission name="score:kpiSchemeJournal:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="kpiSchemeJournal">
			<tr>
				<td style="height: 50px"><a href="${ctx}/score/kpiSchemeJournal/form?id=${kpiSchemeJournal.id}">
					${kpiSchemeJournal.user.name}
				</a></td>
				<td style="height: 50px">
					${kpiSchemeJournal.score}
				</td>
				<td style="height: 50px">
					<fmt:formatDate value="${kpiSchemeJournal.notifyDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="height: 50px">
					${kpiSchemeJournal.notifyType}
				</td>
				<td style="height: 50px">
					${kpiSchemeJournal.notifyStaffName}
				</td>
				<shiro:hasPermission name="score:kpiSchemeJournal:edit"><td style="height: 50px">
    				<a class="btnList" href="${ctx}/score/kpiSchemeJournal/form?id=${kpiSchemeJournal.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/score/kpiSchemeJournal/delete?id=${kpiSchemeJournal.id}" onclick="return confirmx('确认要删除该绩效流水吗？', this.href)" title="删除"><i class="icon-remove-sign"></i> </a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>