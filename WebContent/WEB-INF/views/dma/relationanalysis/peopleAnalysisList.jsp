<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实有人口管理</title>
	<meta name="decorator" content="default"/>

	<script type="text/javascript">
		if($.cookie('theme')==undefined){
			$('head').append('<link href="${ctxStatic}/bootstrap/2.3.1/css_black/custom.css" type="text/css" rel="stylesheet">');
			$('head').append('<link href="${ctxStatic}/bootstrap/2.3.1/css_black/custom.css" type="text/css" rel="stylesheet">');
		}else if($.cookie('theme')=='gradient'){
			$('head').append('<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/custom.css" type="text/css" rel="stylesheet">');
			$('head').append('<link href="${ctxStatic}/bootstrap/2.3.1/css_cerulean/custom.css" type="text/css" rel="stylesheet">');
		}else if($.cookie('theme')=='black'){
			$('head').append('<link href="${ctxStatic}/bootstrap/2.3.1/css_black/custom.css" type="text/css" rel="stylesheet">');
			$('head').append('<link href="${ctxStatic}/bootstrap/2.3.1/css_black/custom.css" type="text/css" rel="stylesheet">');
		}
		$(document).ready(function() {

		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function show(){
			var s = $("#che").prop('checked');
			if(s){
				$(".selectHidden").show();
			}else{
				$(".selectHidden").hide();
			}
		}
	</script>
	<script type="text/javascript"
	      src="${ctxStatic}/ccm/pop/js/ccmPeopleInfo.js">
	</script>
	<script type="text/javascript"
		src="${ctxStatic}/ccm/pop/js/ccmCommon.js"></script>
</head>
<body>
<div class="context" content="${ctx}"></div>
	
<%--	<ul class="nav nav-tabs">--%>
<%--		<li class="active"><a href="${ctx}/pop/ccmPeople/">数据列表</a></li>--%>
<%--	</ul>--%>
	<form:form id="searchForm" modelAttribute="ccmPeople" action="${ctx}/relationAnalysis/dmaPeopleAnalysis/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>人口类型：</label>
				<form:select path="type" class="input-small">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sys_ccm_people')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<li/>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="50" class=""  cssStyle="width:107px"/>
			<li/>
			<li><label>公民身份号码：</label>
				<form:input path="ident" htmlEscape="false" maxlength="18" class="input-medium"/>
			<li/>
			<li><label>是否常住：</label>
				<form:select path="isPermanent" class="input-small">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<li/>
			<li><label>更多</label>
				<input type="checkbox" id="che" onclick="show()">
			<li/>
				

	        <li class="btns">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" />
			</li>
		<li class="clearfix selectHidden hide"></li>

			
			<li class="selectHidden hide"><label>性别：</label>
				<form:select path="sex" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			<li class="selectHidden hide"><label>是否安置帮教：</label>
				<form:select path="isRelease" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			<li class="selectHidden hide"><label>是否社区矫正：</label>
				<form:select path="isRectification" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			<li class="selectHidden hide"><label style="width: 208px;">是否肇事肇祸等严重精神障碍：</label>
				<form:select path="isPsychogeny" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			
		<li class="clearfix selectHidden hide"></li>
          <li class="selectHidden hide"><label>是否吸毒：</label>
				<form:select path="isDrugs" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			
			<li class="selectHidden hide"><label>是否艾滋病危险：</label>
				<form:select path="isAids" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			
			<li class="selectHidden hide"><label>是否留守：</label>
				<form:select path="isBehind" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			<li class="selectHidden hide"><label>是否重点青少年：</label>
				<form:select path="isKym" class="input-small ">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
			    </form:select>
			<li/>
			
		<li class="clearfix selectHidden hide"></li>

			
			<li class="selectHidden hide"><label>所属社区：</label>
				<sys:treeselect id="areaComId" name="areaComId.id" value="${ccmPeople.areaComId.id}" 
					labelName="areaComId.name" 	labelValue="${ccmPeople.areaComId.name}"
					title="社区" url="/tree/ccmTree/treeDataArea?type=6" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			<li/>
			<li class="selectHidden hide"><label>出生日期：</label>
				<input name="beginBirthday" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${ccmPeople.beginBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
				<input name="endBirthday" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${ccmPeople.endBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			<li/>
		<li class="clearfix selectHidden hide"></li>
			<li class="selectHidden hide"><label>所属网格：</label>
				<sys:treeselect id="areaGridId" name="areaGridId.id" value="${ccmPeople.areaGridId.id}" 
					labelName="areaGridId.name" labelValue="${ccmPeople.areaGridId.name}"
					title="网格" url="/tree/ccmTree/treeDataArea?type=7&areaid=" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			<li/>
			<li class="selectHidden hide"><label>现住门（楼）详址：</label>
				<form:input path="residencedetail" htmlEscape="false" maxlength="50" class=""  cssStyle="width:300px"/>
			<li/>
	</ul>
		
		
		
<!-- 		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" style="float: right;margin-right: 3%"/> -->
		<!-- 
		<table id="alignRight" style="width:96%;" border="0px" >
			<tr style="border-bottom: 0px solid #808080">
				<td style="width:250px;">人口类型：
					<form:select path="type" class="input-medium">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('sys_ccm_people')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
				<td style="width:250px;">姓名：
					<form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
				</td>
				<td style="width:300px;">公民身份号码：
					<form:input path="ident" htmlEscape="false" maxlength="18" class="input-medium"/>
				</td>
				<td style="width:200px;">
					更多<input type="checkbox" id="che" onclick="show()">
				</td>
				<td align="right">
					<shiro:hasPermission name="pop:ccmPeople:edit">
						<input id="btnImport" class="btn btn-primary" type="button" value="导入" /> 
						<input id="btnExport" class="btn btn-primary" type="button" value="导出" />
					</shiro:hasPermission>
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();" />
				</td>
			</tr>		
			<tr class="selectHidden hide" >
				<td>性别：
					<form:select path="sex" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td>是否肇事肇祸等严重精神障碍：
					<form:select path="isPsychogeny" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td>是否重点青少年：
					<form:select path="isKym" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td>是否吸毒：
					<form:select path="isDrugs" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td></td>
			</tr>
			<tr class="selectHidden hide">
				<td>是否艾滋病危险人员：
					<form:select path="isAids" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td>是否安置帮教：
					<form:select path="isRelease" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td>是否社区矫正：
					<form:select path="isRectification" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td>是否留守：
					<form:select path="isBehind" class="input-small ">
						<form:option value="" label="全部"/>
						<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>
				<td></td>
			</tr>
			<tr class="selectHidden hide">
				<td>所属社区：
					<sys:treeselect id="areaComId" name="areaComId.id" value="${ccmPeople.areaComId.id}" labelName="areaComId.name" labelValue="${ccmPeople.areaComId.name}"
					title="社区" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="false"/>
				</td>
				<td>所属网格：
					<sys:treeselect id="areaGridId" name="areaGridId.id" value="${ccmPeople.areaGridId.id}" labelName="areaGridId.name" labelValue="${ccmPeople.areaGridId.name}"
					title="网格" url="/sys/area/treeData" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
				</td>
				<td colspan="3">出生日期：
					<input name="beginBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						value="<fmt:formatDate value="${ccmPeople.beginBirthday}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/> - 
					<input name="endBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
						value="<fmt:formatDate value="${ccmPeople.endBirthday}" pattern="yyyy-MM-dd"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
				</td>
			</tr>
		</table>
		 -->
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>公民身份号码</th>
				<th>人口类型</th>
				<th>姓名</th>
				<th>性别</th>
				<th>民族</th>
				<th>出生日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmPeople">
			<tr>
				<td>
						${ccmPeople.ident}
				</td>
				<td>
					${fns:getDictLabel(ccmPeople.type, 'sys_ccm_people', '')}
				</td>
				<td>
					${ccmPeople.name}
				</td>
				<td>
					${fns:getDictLabel(ccmPeople.sex, 'sex', '')}
				</td>
				<td>
						${fns:getDictLabel(ccmPeople.nation, 'sys_volk', '')}
				</td>
				<td>
					<fmt:formatDate value="${ccmPeople.birthday}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<a class="btnList" onclick="parent.LayerDialog('${ctx}/relationAnalysis/dmaPeopleAnalysis/peopleAnalays?ident=${ccmPeople.ident}&name=${ccmPeople.name}&residencedetail=${ccmPeople.residencedetail}', '关联分析', '900px', '800px')"
								  title="关联分析"><i class="icon-group"></i></a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>