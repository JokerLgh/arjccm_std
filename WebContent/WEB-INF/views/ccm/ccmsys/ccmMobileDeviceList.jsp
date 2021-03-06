<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>移动设备管理管理</title>
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
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">设备管理</span>--%>
<div class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/ccmsys/ccmMobileDevice/">数据列表</a></li>
		<shiro:hasPermission name="ccmsys:ccmMobileDevice:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/ccmsys/ccmMobileDevice/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmMobileDevice" action="${ctx}/ccmsys/ccmMobileDevice/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>设备唯一标识码：</label>
				<form:input path="deviceId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="first-line"><label>授权状态：</label>
				<form:select path="isVariable" class="input-small">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_mobile_device_var')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="first-line"><label>使用者：</label>
				<sys:treeselect id="vCcmTeam" name="vCcmTeam.id" value="${ccmMobileDevice.vCcmTeam.id}" labelName="vCcmTeam.name" labelValue="${ccmMobileDevice.vCcmTeam.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li class="first-line"><label>投入使用开始日期：</label>
				<input name="beginCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmMobileDevice.beginCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> </li>
			<li class="first-line"><label>投入使用结束日期：</label>	<input name="endCreateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmMobileDevice.endCreateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
<%--			<li class="clearfix"></li>--%>
		</ul>
	<div class="clearfix pull-right btn-box">
			<a href="javascript:;" id="btnSubmit" style="width: 49px;
   /*margin-top: 25px;*/display:inline-block;float: right" class="btn btn-primary">
				<%--<i class="icon-search"></i> --%><span style="font-size: 12px">查询</span> </a>
	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<thead>
			<tr>
				<th>设备唯一标识码</th>
				<th>授权状态</th>
				<th>设备状态</th>
				<th>使用者</th>
				<th>所属部门</th>
				<th>联系方式</th>
				<th>位置更新时间</th>
				<th>投入使用时间</th>
				<shiro:hasPermission name="ccmsys:ccmMobileDevice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmMobileDevice">
			<tr>
				<td style="height: 50px"><a href="${ctx}/ccmsys/ccmMobileDevice/form?id=${ccmMobileDevice.id}">
					${ccmMobileDevice.deviceId}
				</a></td>
				<td style="height: 50px">
					<c:choose>
						<c:when test="${ccmMobileDevice.isVariable eq '01'}">
							<span style="color:#F00">
								${fns:getDictLabel(ccmMobileDevice.isVariable, 'ccm_mobile_device_var', '')}
							</span>
						</c:when>
						<c:otherwise>
							${fns:getDictLabel(ccmMobileDevice.isVariable, 'ccm_mobile_device_var', '')}
						</c:otherwise>
					</c:choose>
				</td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmMobileDevice.vCcmTeam.status, 'ccm_mobile_device_status', '')} 
				</td> 
				<td style="height: 50px">
					${ccmMobileDevice.vCcmTeam.name}
				</td>
				<td style="height: 50px">
					${ccmMobileDevice.officeName}
				</td>
				<td style="height: 50px">
					${ccmMobileDevice.vCcmTeam.mobile}
				</td>
				<td style="height: 50px">
					<fmt:formatDate value="${ccmMobileDevice.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td style="height: 50px">
					<fmt:formatDate value="${ccmMobileDevice.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="ccmsys:ccmMobileDevice:edit"><td style="height: 50px">
    				<a class="btnList" href="${ctx}/ccmsys/ccmMobileDevice/form?id=${ccmMobileDevice.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/ccmsys/ccmMobileDevice/delete?id=${ccmMobileDevice.id}" onclick="return confirmx('确认要删除该移动设备吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>