<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>预处理事件管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/layer-v3.1.1/layer/theme/default/layer.css"
	rel="stylesheet" />
<script src="${ctxStatic}/layer-v3.1.1/layer/layer.js"></script>
	<script type="text/javascript"
			src="${ctxStatic}/plm/storage/ajaxMessageAlert.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#btnSubmit").on("click", function() {
				var begin = new Date($("[name='beginHappenDate']").val());
				var end = new Date($("[name='endHappenDate']").val());
				if(begin.getTime() > end.getTime()){
					messageAlert("开始时间大于结束时间！", "error");
					return false;
				}
				$("#searchForm").submit();
			});
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
	<form:form id="searchForm" modelAttribute="ccmEventIncidentPreview"
		action="${ctx}/preview/ccmEventIncidentPreview/check/list"
		method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<ul class="ul-form">
			<li><label>开始日期：</label> <input name="beginHappenDate"
				type="text" readonly="readonly" maxlength="20"
				class="input-medium Wdate"
				value="<fmt:formatDate value="${ccmEventIncidentPreview.beginHappenDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
				<label>结束日期：</label> <input name="endHappenDate" type="text"
				readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="<fmt:formatDate value="${ccmEventIncidentPreview.endHappenDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
			</li>
			<li><label>事件名称：</label> <form:input path="caseName"
					htmlEscape="false" maxlength="100" class="input-medium" /></li>
			<li><label>相似度阈值：</label> <form:input path="similarity"
					placeholder="请输入0-1的小数" htmlEscape="false" maxlength="100"
					class="input-medium" /></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary"
				type="submit" value="查 询" /></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>事件A名称</th>
				<th>事件A上报人</th>
				<th>事件A发生日期</th>
				<th>事件B名称</th>
				<th>事件B上报人</th>
				<th>事件B发生日期</th>
				<th>标题相似度</th>
				<th>内容相似度</th>
				<shiro:hasPermission name="preview:ccmEventIncidentPreview:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${simList}" var="CcmEventIncidentSimilarty">
				<tr>
					<td style="height: 50px"><a
						onclick="parent.LayerDialog('${ctx}/preview/ccmEventIncidentPreview/form/1?id=${CcmEventIncidentSimilarty.eventA.id}', '编辑', '1200px', '600px')">${CcmEventIncidentSimilarty.eventA.caseName}</a></td>
					<td style="height: 50px">${CcmEventIncidentSimilarty.eventA.reportPerson}</td>
					<td style="height: 50px"><fmt:formatDate
							value="${CcmEventIncidentSimilarty.eventA.happenDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td style="height: 50px"><a
						onclick="parent.LayerDialog('${ctx}/preview/ccmEventIncidentPreview/form/1?id=${CcmEventIncidentSimilarty.eventB.id}', '编辑', '1200px', '600px')">${CcmEventIncidentSimilarty.eventB.caseName}</a></td>
					<td style="height: 50px">${CcmEventIncidentSimilarty.eventB.reportPerson}</td>
					<td style="height: 50px"><fmt:formatDate
							value="${CcmEventIncidentSimilarty.eventB.happenDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<c:choose>
						<c:when
							test="${CcmEventIncidentSimilarty.titleSim >= 90.0 && CcmEventIncidentSimilarty.titleSim <= 100.0}">
							<td style="height: 50px"><font size="3" color="#d41e24">${CcmEventIncidentSimilarty.titleSim}%</font></td>
						</c:when>
						<c:when
							test="${CcmEventIncidentSimilarty.titleSim >= 80.0 && CcmEventIncidentSimilarty.titleSim < 90.0}">
							<td style="height: 50px"><font size="3" color="orange">${CcmEventIncidentSimilarty.titleSim}%</font></td>
						</c:when>
						<c:when
							test="${CcmEventIncidentSimilarty.titleSim >= 70.0 && CcmEventIncidentSimilarty.titleSim < 80.0}">
							<td style="height: 50px"><font size="3" color="#3bb4f2">${CcmEventIncidentSimilarty.titleSim}%</font></td>
						</c:when>
						<c:otherwise>
							<td style="height: 50px"><font size="3" color="black">${CcmEventIncidentSimilarty.titleSim}%</font></td>
						</c:otherwise>
					</c:choose>

					<c:choose>
						<c:when
							test="${CcmEventIncidentSimilarty.contentSim >= 90.0 && CcmEventIncidentSimilarty.contentSim <= 100.0}">
							<td style="height: 50px"><font size="3" color="#d41e24">${CcmEventIncidentSimilarty.contentSim}%</font></td>
						</c:when>
						<c:when
							test="${CcmEventIncidentSimilarty.contentSim >= 80.0 && CcmEventIncidentSimilarty.contentSim < 90.0}">
							<td style="height: 50px"><font size="3" color="orange">${CcmEventIncidentSimilarty.contentSim}%</font></td>
						</c:when>
						<c:when
							test="${CcmEventIncidentSimilarty.contentSim >= 70.0 && CcmEventIncidentSimilarty.contentSim < 80.0}">
							<td style="height: 50px"><font size="3" color="#3bb4f2">${CcmEventIncidentSimilarty.contentSim}%</font></td>
						</c:when>
						<c:otherwise>
							<td style="height: 50px"><font size="3" color="black">${CcmEventIncidentSimilarty.contentSim}%</font></td>
						</c:otherwise>
					</c:choose>

					<shiro:hasPermission name="preview:ccmEventIncidentPreview:edit">
						<td style="height: 50px"><a class="btnList"
							onclick="parent.LayerDialog('${ctx}/preview/ccmEventIncidentPreview/check/form?idA=${CcmEventIncidentSimilarty.eventA.id}&idB=${CcmEventIncidentSimilarty.eventB.id}', '编辑', '1200px', '600px')"
							title="对比"><i class="icon-eye-close"></i></a>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>