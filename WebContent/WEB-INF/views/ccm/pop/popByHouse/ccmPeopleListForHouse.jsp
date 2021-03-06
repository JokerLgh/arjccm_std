<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>实有人口管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {

	});
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function show() {
		var s = $("#che").prop('checked');
		if (s) {
			$("#show").css("display", "block");
		} else {
			$("#show").css("display", "none");
		}
	}
	function LayerDialog(id,src, title, height, width) {
		parent.layer.open({
			type: 2,
			title: title,
			id: id || '',
			area: [height, width],
			fixed: true, //固定
			maxmin: true,
			btn: ['关闭'], //可以无限个按钮
			content: src,
			zIndex: '1',
			end: function () {
				location.reload();
			}
		});
	}
</script>
<script type="text/javascript"
	src="${ctxStatic}/ccm/pop/js/ccmPeopleInfo.js">
	
</script>
<style type="text/css">
#show {
	display: none;
}
</style>
</head>
<body>
<div class="back-list">
	<!-- 导入、导出模块 -->
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/pop/ccmPeople/import"
			method="post" enctype="multipart/form-data" class="form-search"
			style="padding-left: 20px; text-align: center;"
			onsubmit="loading('正在导入，请稍等...');">
			<br /> <input id="uploadFile" name="file" type="file"
				style="width: 330px" /><br /> <br /> <input id="btnImportSubmit"
				class="btn btn-primary" type="submit" value="导  入 " />
		</form>
	</div>

	<ul class="nav nav-tabs">
		<c:if test="${PeoTypeBy eq 'ByHouse' }">
			<li><a href="${ctx}/pop/ccmPopTenant/list">房屋列表</a></li>
			<li class="active"><a href="${ctx}/pop/ccmPeople/getPeoListByHouse?houseId=${houseId}&type=house&buildId=${buildId}&netId=${netId}">现有人员列表</a></li>
		</c:if>
		<c:if test="${PeoTypeBy eq 'ByHouse_hire' }">
			<li><a href="${ctx}/pop/ccmPopTenant/list/rent">房屋列表</a></li>
			<li class="active"><a href="${ctx}/pop/ccmPeople/getPeoListByHouse?houseId=${houseId}&type=hire&buildId=${buildId}&netId=${netId}">现有人员列表</a></li>
		</c:if>
			<%-- <shiro:hasPermission name="tenant:ccmTenantRecord:view">
			<li><a href="${ctx}/tenant/ccmTenantRecord/${houseId}">历史租客记录列表</a></li>
			</shiro:hasPermission> --%>
		
		<c:if test="${PeoTypeBy eq 'ByHouse_Build' }">
			<li><a href="${ctx}/house/ccmHouseBuildmanage/">楼栋列表</a></li>
			<li><a href="${ctx}/house/ccmHouseBuildmanage/form">楼栋添加</a></li>
			<li><a href="${ctx}/pop/ccmPopTenant/listBuild?buildingId.id=${buildId}&area.id=${netId}">房屋列表</a></li>
			<shiro:hasPermission name="pop:ccmPopTenant:edit">
				<li><a href="${ctx}/pop/ccmPopTenant/formBuild?buildingIdId=${buildId}&area.id=${netId}">房屋新增</a></li>
			</shiro:hasPermission>
			<li class="active"><a href="${ctx}/pop/ccmPeople/getPeoListByHouse?houseId=${houseId}&type=houseBuild&buildId=${buildId}&netId=${netId}">现有人员列表</a></li>
		</c:if>
		<!-- 当前的页面为 房屋 则进行展示  -->
		<c:if test="${PeoTypeBy eq 'ByHouse' }">
			<shiro:hasPermission name="pop:ccmPeople:edit">
				<li><a
					href="${ctx}/pop/ccmPeople/getPeoFromByHouse?houseId=${houseId}&type=house">成员新增</a></li>
			</shiro:hasPermission>
		</c:if>
		<c:if test="${PeoTypeBy eq 'ByHouse_hire' }">
			<shiro:hasPermission name="pop:ccmPeople:edit">
				<li><a
					href="${ctx}/pop/ccmPeople/getPeoFromByHouse?houseId=${houseId}&type=hire">成员新增</a></li>
			</shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmPopTenant" action="${ctx}/pop/ccmPopTenant/listBuild?buildingId.id=${buildId}&area.id=${netId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li class="btns">
				<a class="btn btn-primary" onclick="LayerDialog('','${ctx}/pop/ccmPeople/listPopAdd?houseId=${houseId}', '添加成员', '1200px', '700px')" title="添加成员">
					添加成员</a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>人口类型</th>
				<th>姓名</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>所属社区</th>
				<th>所属网格</th>
				<th>现住门（楼）详址</th>
					<shiro:hasPermission name="pop:ccmPeople:edit">
						<th>操作</th>
					</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="ccmPeople">
				<tr>
					<td>${fns:getDictLabel(ccmPeople.type, 'sys_ccm_people', '')}
					</td>
					<!--如果为房屋进行访问  -->
					<c:if test="${PeoTypeBy eq 'ByHouse' }">
						<td><a href="${ctx}/pop/ccmPeople/getPeoFromByHouse?id=${ccmPeople.id}&houseId=${ccmPeople.roomId.id}&buildId=${buildId}&type=house">
								${ccmPeople.name}</a></td>
					</c:if>
					<c:if test="${PeoTypeBy eq 'ByHouse_hire' }">
						<td><a href="${ctx}/pop/ccmPeople/getPeoFromByHouse?id=${ccmPeople.id}&houseId=${ccmPeople.roomId.id}&buildId=${buildId}&type=hire">
								${ccmPeople.name}</a></td>
					</c:if>
					<!--如果为楼栋进行访问  -->
					<c:if test="${PeoTypeBy eq 'ByHouse_Build' }">
						<td><a href="${ctx}/pop/ccmPeople/getPeoFromByHouse?id=${ccmPeople.id}&houseId=${houseId}&buildId=${buildId}&type=build">
								${ccmPeople.name}</a></td>
					</c:if>
					<td>${fns:getDictLabel(ccmPeople.sex, 'sex', '')}</td>
					<td><fmt:formatDate value="${ccmPeople.birthday}"
							pattern="yyyy-MM-dd" /></td>
					<td>${ccmPeople.areaComId.name}</td>
					<td>${ccmPeople.areaGridId.name}</td>
					<td>${ccmPeople.residencedetail}</td>
					<td><shiro:hasPermission name="pop:ccmPeople:edit">
						<c:if test="${PeoTypeBy eq 'ByHouse' }">
							<a class="btnList" href="${ctx}/pop/ccmPeople/getPeoFromByHouse?id=${ccmPeople.id}&houseId=${ccmPeople.roomId.id}&buildId=${buildId}&type=house"  title="修改"><i class="icon-pencil"></i></a>
						</c:if>
						<a class="btnList" href="${ctx}/pop/ccmPeople/deletePeople?id=${ccmPeople.id}&houseId=${houseId}&type=${PeoTypeBy}&buildId=${buildId}&netId=${netId}"
							onclick="return confirmx('确认要在此房屋中移除该成员吗？', this.href)"  title="移除成员"><i class="icon-remove-sign"></i></a>
					</shiro:hasPermission>
					<shiro:hasPermission name="tenant:ccmTenantRecord:view">
							<a class="btnList"
							   onclick="parent.LayerDialog(encodeURI('${ctx}/tenant/ccmTenantRecord/form?idCard=${ccmPeople.ident}&name=${ccmPeople.name}&houseId=${ccmPeople.roomIdString}&phoneNumber=${ccmPeople.telephone}'), '租房详情登记', '800px', '550px')"
								title="租房详情"><i class=" icon-time"></i></a>
					</shiro:hasPermission></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</div>
</body>
</html>