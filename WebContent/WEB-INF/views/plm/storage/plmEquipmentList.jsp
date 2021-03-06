<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>装备物资管理</title>
<meta name="decorator" content="default" />
<!-- 列表缩略图切换 -->
<!--自适应  -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="${ctxStatic}/bootstrap/2.3.1/css_flat/bootstrap-responsive.css"
	rel="stylesheet">
<link rel="stylesheet" href="${ctxStatic}/common/list/list.css">
<script type="text/javascript" src="${ctxStatic}/common/list/list.js"></script>
<!-- /列表缩略图切换 -->
<style type="text/css">
.input-select {
	width: 117px;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btnSubmit').click(function(){
			$('#searchForm').submit();
		});
	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
<%-- <script type="text/javascript" src="${ctxStatic}/plm/storage/plmEquipmentType.js"></script>  --%>
</head>
<body>
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">应急物资保障</span>--%>
<div class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/storage/plmEquipment/">物资列表</a></li>
		
	</ul>
	<form:form id="searchForm" modelAttribute="plmEquipment"
		action="${ctx}/storage/plmEquipment/" method="post"
		class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form pull-left">
			
			<li class="first-line"><label>物资名称：</label> <form:input path="name"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li class="first-line"><label>物资编号：</label> <form:input path="code"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li class="first-line"><label>规格型号：</label> <form:input path="spec"
					htmlEscape="false" maxlength="64" class="input-medium" /></li>
			<li class="first-line"><label>使用人：</label> <sys:treeselect id="user" name="user.id"
					value="${plmEquipment.user.id}" labelName="user.name"
					labelValue="${plmEquipment.user.name}" title="用户"
					url="/sys/office/treeData?type=3" cssClass="input-select"
					allowClear="true" notAllowSelectParent="true" /></li>
			<li class="first-line"><label>使用人部门：</label> <sys:treeselect id="userJob"
					name="userJob.id" value="${plmEquipment.userJob.id}"
					labelName="userJob.name" labelValue="${plmEquipment.userJob.name}"
					title="部门" url="/sys/office/treeData?type=2"
					cssClass="input-select" allowClear="true"
					notAllowSelectParent="true" /></li>
			<li class="second-line"><label>状态：</label> <form:select path="type"
					class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('plm_equipment_status')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
			<c:if test="${officeFlag != 1}">
				<li class="second-line"><label>物资类别：</label> <form:select path="typeId"
																		 class="input-medium" dictTyep="plm_equipment_type">
					<form:option value="" label="未选择" />
					<form:options items="${fns:getDictList('plm_equipment_type')}"
								  itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li>
				<li class="second-line"><label>物资子类：</label> <form:select path="typeChild"
																		 class="input-medium">
					<form:option value="" label="未选择" />
					<form:options
							items="${fns:getDictList('plm_equipment_type_child')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select></li></c:if>

			<%--<li class="clearfix"></li>--%>
		</ul>
	<div class="clearfix pull-right btn-box">
		<a href="javascript:;" id="btnSubmit" style="width: 49px;margin-right: 14px;
    margin-bottom: 20px;/*margin-top: 25px;*/display:inline-block;float: right;margin-left: 20px" class="btn btn-primary">
				<%--<i class="icon-search"></i> --%><span style="font-size: 12px">查询</span> </a>
	</div>
	</form:form>
	<sys:message content="${message}" />
	<!-- 列表缩略图切换按钮 -->
	<div id="switchbtn">
		<a class="thumbnailbtn"><i class="icon-th "></i></a>&nbsp; <a
			class="listbtn" style="margin-right: 20px"> <i class="icon-th-list2 "></i></a>
	</div>
	<!--/列表缩略图切换按钮 -->
	<div id="prodInfo_List">
		<table id="contentTable"
			class="table table-striped table-bordered table-condensed table-gradient">
			<thead>
				<tr>
					<th>所在仓库</th>
					<th>物资名称</th>
					<th>物资编号</th>
					<th>规格型号</th>
					<th>物资类别</th>
					<th>物资子类</th>
					<th>使用人</th>
					<th>使用人部门</th>
					<th>状态</th>
					<shiro:hasPermission name="storage:plmEquipment:edit">
						<th>操作</th>
					</shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="plmEquipment">
					<tr>
						<td style="height: 50px"><a
							href="${ctx}/storage/plmEquipment/form?id=${plmEquipment.id}">
								${plmEquipment.storage.name} </a></td>
						<td style="height: 50px">${plmEquipment.name}</td>
						<td style="height: 50px">${plmEquipment.code}</td>
						<td style="height: 50px">${plmEquipment.spec}</td>
						<td style="height: 50px">${fns:getDictLabel(plmEquipment.typeId, 'plm_equipment_type', '')}
						</td>
						<td style="height: 50px">${fns:getDictLabel(plmEquipment.typeChild, 'plm_equipment_type_child', '')}
						</td>
						<td style="height: 50px">${plmEquipment.user.name}</td>
						<td style="height: 50px">${plmEquipment.userJob.name}</td>
						<td style="height: 50px">${fns:getDictLabel(plmEquipment.type, 'plm_equipment_status', '')}
						</td>
						<shiro:hasPermission name="storage:plmEquipment:edit">
							<td style="height: 50px"><a
								href="${ctx}/storage/plmEquipment/form?id=${plmEquipment.id}" class="btnList"><i title="修改" class="icon-pencil"></i></a>
								<a
								href="${ctx}/storage/plmEquipment/delete?id=${plmEquipment.id}"
								onclick="return confirmx('确认要删除该装备物资吗？', this.href)" class="btnList"><i title="删除" class="icon-trash"></i></a></td>
						</shiro:hasPermission>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<!-- 缩略图 -->
	<ul style="padding-top: 50px">
	<div id="prodInfo_small">
		<div class="row">
			<c:forEach items="${page.list}" var="plmEquipment">
				<div class="span4 spandiv">
					<div class="thumbnail">
						<a href="${ctx}/storage/plmEquipment/form?id=${plmEquipment.id}">
							<h4 title="${plmEquipment.storage.name} ">所在仓库:${plmEquipment.storage.name}</h4>
						</a> </a>
						<div class="caption row-fluid">
							<div class=" spanimg" style="width: 30%">
								<img src="${plmEquipment.imgUrl}"
									onerror='this.src="${ctxStatic}/common/list/images/timg.jpg"'
									alt="通用的占位符缩略图" />
							</div>
							<div class="spantext  " style="width: 63%; margin-left: 7%">
								<p title="${plmEquipment.name}">物资名称:${plmEquipment.name}</p>
								<p title="${plmEquipment.user.name}">使用人:${plmEquipment.user.name}</p>
								<p
									title="${fns:getDictLabel(plmEquipment.type, 'plm_equipment_status', '')}">状态:${fns:getDictLabel(plmEquipment.type, 'plm_equipment_status', '')}
								</p>
							</div>
						</div>

						<div class="footbtn" style="text-align: right;">
							<shiro:hasPermission name="storage:plmEquipment:edit">
								<a href="${ctx}/storage/plmEquipment/form?id=${plmEquipment.id}" class="btnList"><i title="修改" class="icon-pencil"></i></a>
								<a
									href="${ctx}/storage/plmEquipment/delete?id=${plmEquipment.id}"
									onclick="return confirmx('确认要删除该装备物资吗？', this.href)" class="btnList"><i title="删除" class="icon-trash"></i></a>
							</shiro:hasPermission>

						</div>
					</div>
				</div>

			</c:forEach>
		</div>
	</div>
	</ul>
	<!-- /缩略图 -->
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>