<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>社区服务管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${ctxStatic}/ccm/service/js/ccmCommunityWorkinfo.js"></script>
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
		<li class="active"><a href="${ctx}/service/ccmCommunityWork/">数据列表</a></li>
		<shiro:hasPermission name="service:ccmCommunityWork:edit">
			<li><a href="${ctx}/service/ccmCommunityWork/form">数据添加</a></li>
		</shiro:hasPermission>
	</ul>
	<div class="ccmCommunityWorkType" style="display: none;"
		attrType1="${ccmCommunityWork.type1}"
		attrType2="${ccmCommunityWork.type2}" attrPurpose="list"></div>
	<form:form id="searchForm" modelAttribute="ccmCommunityWork"
		action="${ctx}/service/ccmCommunityWork/" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input id="label2" name="label2" type="hidden" value="${ccmCommunityWork.label2}" />
		<ul class="ul-form">
			<li><label>一级分类：</label> <form:select path="type1"
					class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_service_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<li><label>二级分类：</label> <form:select path="type2"
					class="input-medium">
				</form:select></li>
			<li><label>标题：</label> <form:input path="title"
					htmlEscape="false" maxlength="100" class="input-medium" /></li>
			<li><label>审核类型：</label> <form:select path="status"
					class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_service_handle')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>

			<li><label>创建时间：</label> <input name="beginCreateDate"
				type="text" readonly="readonly" maxlength="20"
				class="input-medium Wdate"
				value="<fmt:formatDate value="${ccmCommunityWork.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
				- <input name="endCreateDate" type="text" readonly="readonly"
				maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${ccmCommunityWork.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</li>
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
				<th>一级分类</th>
				<th>二级分类</th>
				<th>标题</th>
				<th>审核信息</th>
				<th>创建时间</th>
				<th>更新时间</th>
				<shiro:hasPermission name="service:ccmCommunityWork:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ccmCommunityWork">
				<tr>
					<td><a
						href="${ctx}/service/ccmCommunityWork/form?id=${ccmCommunityWork.id}">
							${fns:getDictLabel(ccmCommunityWork.type1, 'ccm_service_type', '')}
					</a></td>
					<td>
						${fns:getDictLabel(ccmCommunityWork.type2,ccmCommunityWork.type1 , '')}
					</td>
					<td>${ccmCommunityWork.title}</td>
					<td>${fns:getDictLabel(ccmCommunityWork.status,'ccm_service_handle', '')}
					</td>
					<td><fmt:formatDate value="${ccmCommunityWork.createDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td><fmt:formatDate value="${ccmCommunityWork.updateDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<shiro:hasPermission name="service:ccmCommunityWork:edit">
						<td><a class="btnList"
							href="${ctx}/service/ccmCommunityWork/form?id=${ccmCommunityWork.id}" title="修改"><i class="icon-pencil"></i></a>
							<a class="btnList"
							href="${ctx}/service/ccmCommunityWork/delete?id=${ccmCommunityWork.id}"
							onclick="return confirmx('确认要删除该社区服务吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>