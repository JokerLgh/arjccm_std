<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>公共机构人员管理</title>
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
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">社会治安</span>--%>
<div class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/org/ccmOrgComPop/">数据列表</a></li>
		<shiro:hasPermission name="org:ccmOrgComPop:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/org/ccmOrgComPop/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmOrgComPop" action="${ctx}/org/ccmOrgComPop/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>所属机构：</label>
				<sys:treeselect id="commonalityId" name="commonalityId.id" value="${ccmOrgComPop.commonalityId.id}" labelName="commonalityId.name" labelValue="${ccmOrgComPop.commonalityId.name}"
					title="机构名称" url="/org/ccmOrgCommonality/treeData" cssClass="" allowClear="true" notAllowSelectParent="true" cssStyle="width: 150px"/>
			</li>
			<li class="first-line"><label>编号：</label>
				<form:input path="code" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="first-line"><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<!--<li><label>证件号码：</label>
				<form:input path="idenNum" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>  -->
			<li class="first-line"><label>性别：</label>
				<form:select path="sex" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<!--<li><label>民族：</label>
				<form:select path="nation" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sys_volk')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>  -->
			<li class="first-line"><label>政治面貌：</label>
				<form:select path="politics" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sys_ccm_poli_stat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="second-line"><label>学历：</label>
				<form:select path="education" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sys_ccm_degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<!--<li><label>职务：</label>
				<form:input path="service" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li>  -->
			<!--<li><label>专业特长：</label>
				<form:input path="profExpertise" htmlEscape="false" maxlength="80" class="input-medium"/>
			</li>  -->
			<li class="second-line"><label>手机号码：</label>
				<form:input path="telephone" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="second-line"><label>出生开始日期：</label>
				<input name="beginBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmOrgComPop.beginBirthday}" pattern="yyyy-MM-dd"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></li>
				<li class="second-line"><label>出生结束日期：</label><input name="endBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmOrgComPop.endBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
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
				<th>编号</th>
				<th>所属机构</th>
				<th>姓名</th>
				<th>性别</th>
				<th>民族</th>
				<th>政治面貌</th>
				<th>证件号码</th>
				<th>出生日期</th>
				<th>职务</th>
				<th>手机号码</th>
				<shiro:hasPermission name="org:ccmOrgComPop:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmOrgComPop">
			<tr>
				<td style="height: 50px">
					${ccmOrgComPop.code}
				</td>
				<td style="height: 50px">
					${ccmOrgComPop.commonalityId.name}
				</td>
				<td style="height: 50px"><a href="${ctx}/org/ccmOrgComPop/form?id=${ccmOrgComPop.id}">
					${ccmOrgComPop.name}
				</a></td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmOrgComPop.sex, 'sex', '')}
				</td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmOrgComPop.nation, 'sys_volk', '')}
				</td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmOrgComPop.politics, 'sys_ccm_poli_stat', '')}
				</td>
				<td style="height: 50px">
					${ccmOrgComPop.idenNum}
				</td>
				<td style="height: 50px">
					<fmt:formatDate value="${ccmOrgComPop.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="height: 50px">
					${ccmOrgComPop.service}
				</td>
				<td style="height: 50px">
					${ccmOrgComPop.telephone}
				</td>
				<shiro:hasPermission name="org:ccmOrgComPop:edit"><td style="height: 50px">
    				<a class="btnList" href="${ctx}/org/ccmOrgComPop/form?id=${ccmOrgComPop.id}"  title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/org/ccmOrgComPop/delete?id=${ccmOrgComPop.id}" onclick="return confirmx('确认要删除该公共机构人员吗？', this.href)"  title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>