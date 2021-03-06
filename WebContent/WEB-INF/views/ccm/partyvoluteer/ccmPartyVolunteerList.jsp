<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>志愿者管理管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/layer-v3.1.1/layer/theme/default/layer.css" />
	<script src="${ctxStatic}/layer-v3.1.1/layer/layer.js" type="text/javascript"></script>
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
		//详情弹框--不刷新父页面
		function LayerDialog(src, title, height, width) {
			layer.open({
				type : 2,
				title : title,
				area : [ height, width ],
				fixed : true, //固定
				maxmin : true,
				//btn: ['确定', '关闭'], //可以无限个按钮
				content : src,
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/partyvoluteer/ccmPartyVolunteer/">志愿者管理列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmPartyVolunteer" action="${ctx}/partyvoluteer/ccmPartyVolunteer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>选择社区：</label>
				<sys:treeselect id="community" name="community.id" value="${ccmPartyVolunteer.community.id}" labelName="" labelValue="${ccmPartyVolunteer.community.name}"
					title="区域" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>学历：</label>
				<form:select path="educationalBackground" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('education_background')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><a
					onclick="parent.parent.parent.LayerDialog('${ctx}/partyvoluteer/ccmPartyVolunteer/form', '添加', '1100px', '700px')"
					class="btn btn-success"><i class="icon-plus"></i> 添加</a></li>
			<li class="btns">
				<a href="javascript:;" id="btnSubmit" class="btn btn-primary"> 
					<i class="icon-search"></i> 查询
				</a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>所属社区</th>
				<th>性别</th>
				<th>学历</th>
				<th>年龄</th>
				<th>民族</th>
				<shiro:hasPermission name="partyvoluteer:ccmPartyVolunteer:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmPartyVolunteer">
			<tr>
				<td><a onclick="parent.parent.parent.LayerDialog('${ctx}/partyvoluteer/ccmPartyVolunteer/form?id=${ccmPartyVolunteer.id}', '修改', '1100px', '700px')">
					${ccmPartyVolunteer.name}
				</a></td>
				<td>
						${ccmPartyVolunteer.community.name}
				</td>
				<td>
						${ccmPartyVolunteer.sex}
				</td>
				<td>
						${ccmPartyVolunteer.educationalBackground}
				</td>
				<td>
						${ccmPartyVolunteer.age}
				</td>
				<td>
						${ccmPartyVolunteer.nation}
				</td>

				<shiro:hasPermission name="partyvoluteer:ccmPartyVolunteer:edit"><td>
    				<%--<a href="${ctx}/partyvoluteer/ccmPartyVolunteer/form?id=${ccmPartyVolunteer.id}">修改</a>--%>
					<%--<a href="${ctx}/partyvoluteer/ccmPartyVolunteer/delete?id=${ccmPartyVolunteer.id}" onclick="return confirmx('确认要删除该志愿者管理吗？', this.href)">删除</a>--%>
						<a  class="btnList"
							onclick="parent.parent.parent.LayerDialog('${ctx}/partyvoluteer/ccmPartyVolunteer/form?id=${ccmPartyVolunteer.id}', '修改', '1100px', '700px')"><i class="icon-pencil"></i></a>
						<a  class="btnList"
							href="${ctx}${ctx}/partyvoluteer/ccmPartyVolunteer/delete?id=${ccmPartyVolunteer.id}"
							onclick="return confirmx('确认要删除该党员信息管理吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>