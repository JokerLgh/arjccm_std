<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>意见建议管理</title>
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
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">通知公告</span>--%>
<ul class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 112px"><a class="nav-head" href="${ctx}/work/ccmWorkAdvise/">数据列表</a></li>
		<shiro:hasPermission name="work:ccmWorkAdvise:edit"><li style="width: 112px"><a style="text-align: center" href="${ctx}/work/ccmWorkAdvise/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmWorkAdvise" action="${ctx}/work/ccmWorkAdvise/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>开始日期：</label>
				<input name="beginDatas" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmWorkAdvise.beginDatas}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> </li>
			<li class="first-line"><label>结束日期：</label>	<input name="endDatas" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmWorkAdvise.endDatas}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->

<%--			<li class="clearfix"></li>--%>
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
				<th>提意人员</th>
				<th>建议时间</th>
				<th>内容</th>
				<th>回复</th>
				<shiro:hasPermission name="work:ccmWorkAdvise:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmWorkAdvise">
			<tr>
				<td style="height: 50px"><a href="${ctx}/work/ccmWorkAdvise/form?id=${ccmWorkAdvise.id}">
					${ccmWorkAdvise.createBy.name}
				</a></td>
				<td style="height: 50px">
					<fmt:formatDate value="${ccmWorkAdvise.datas}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td class="tp" style="height: 50px">
					${ccmWorkAdvise.details}
				</td>
				<td class="tp" style="height: 50px">
					${ccmWorkAdvise.reply}
				</td>
				<shiro:hasPermission name="work:ccmWorkAdvise:edit"><td style="height: 50px">
    				<a class="btnList" href="${ctx}/work/ccmWorkAdvise/form?id=${ccmWorkAdvise.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/work/ccmWorkAdvise/delete?id=${ccmWorkAdvise.id}" onclick="return confirmx('确认要删除该意见建议吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</ul>
</body>
</html>