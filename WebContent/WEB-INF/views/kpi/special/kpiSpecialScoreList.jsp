<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专项考核管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			var contexturl = $(".context").attr("content");
			
			$("#btn").click(function(){
				var sum=0;
				var param="";
				var data=new Array();
				$("#tbody").find("tr").each(function(index, content){
					if($(content).find("[name='weights']").val()!=null){
						sum+=Number($(content).find("[name='weights']").val());
					}
	            
			/* 		param += "&kpiSpecialScoreList["+index+"].id="+$(content).find("[name='id']").val() 
						  + "&kpiSpecialScoreList["+index+"].weights="+$(content).find("[name='weights']").val() 
						  + "&kpiSpecialScoreList["+index+"].remarks="+$(content).find("[name='remarks']").val();  */
					var id = $(content).find("[name='id']").val();
					var weights = $(content).find("[name='weights']").val();
					var remarks = $(content).find("[name='remarks']").val();
						  
					data.push({"id":id,"weights": weights,"remarks": remarks});
				});

				if(sum!=100){
					alert("添加权重合计应为100")
					return;
				}
				 $.ajax({
						url : contexturl + "/special/kpiSpecialScore/saveList",
						type : "post",
						cache : false,
						data : {'param':JSON.stringify(data)},
						dataType : "html",
						success : function(result) {
							/* $("#btn").attr("disabled", false); */
							location.reload(); 
							$("#btn").attr("disabled", false);
						},
						error : function() {
						}
					});
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
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">业务考核</span>--%>
<div class="back-list">
	<div class="context" content="${ctx}"></div>
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/special/kpiSpecialScore/">专项考核列表</a></li>
		<%-- <shiro:hasPermission name="special:kpiSpecialScore:edit"><li><a href="${ctx}/special/kpiSpecialScore/form">专项考核添加</a></li></shiro:hasPermission> --%>
	</ul>
	<%-- <form:form  modelAttribute="kpiSpecialScore"  class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>唯一主键ID（自增）：</label>
				<form:input path="id" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>key名称：</label>
				<form:input path="keyname" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>创建时间：</label>
				<input name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${kpiSpecialScore.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>更新时间：</label>
				<input name="updateDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${kpiSpecialScore.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li> 
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>--%>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<thead>
			<tr>
				<th>名称</th>
				<th>权重</th>
				<th>备注信息</th>
			</tr>
		</thead>
		<tbody id="tbody">
		<c:forEach items="${page.list}" var="kpiSpecialScore">
			<tr>
				<td>
					${kpiSpecialScore.name}
					<input  type="hidden" name="id" value="${kpiSpecialScore.id}"/>
				</td>
				<td>
					<input  maxlength="64" class="input-xlarge " type="text" name="weights" value="${kpiSpecialScore.weights}"  data-id="${kpiSpecialScore.id}"/>
				</td>
				<td>
					<input  maxlength="64" class="input-xlarge " type="text" name="remarks" value="${kpiSpecialScore.remarks}"/>
				</td>
				<%-- <shiro:hasPermission name="special:kpiSpecialScore:edit"><td>
    				<a href="${ctx}/special/kpiSpecialScore/form?id=${kpiSpecialScore.id}">修改</a>
				</td></shiro:hasPermission> --%>
			</tr>
		</c:forEach>
		
		</tbody>
	</table>
	<div><input id="btn" class="btn btn-primary"  style="float: right;margin-right: 14px;" type="submit" value="保存"/></div>
</body>
</html>