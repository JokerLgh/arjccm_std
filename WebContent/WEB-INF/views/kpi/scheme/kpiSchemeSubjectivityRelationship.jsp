<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效主观评分管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".remarks").each(function(){
				var val = $(this).val();
				var valarr = val.split(";");
				var text = '';
				for(var i=0 ; i<valarr.length-1 ; i++){
					var data = valarr[i].split(",");
					valData='['+data[1]+','+data[2]+'';
					text+=''+valData+';\n';
				}
				$(this).val(text);
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function saveForm(){
			var state = ${kpiScheme.state};
			var kid = "${kpiSchemeKpi.id}";
			if(state=="0"||state==""){
				$('#contentTable tbody tr .remarks').each(function(i,dome){
					 var id=$(dome).attr('attrId');
					 var val=$(dome).attr("data");
					 $.post('${ctx}/scheme/kpiSchemeSubjectivity/saveList/', {
					    	"user.id": id, 
					    	"remarks": val, 
					    	"kpiId":kid,
					    }, function(data) {
					    
					    });
				})
				top.$.jBox.tip('保存成功！ ');
				var index = parent.layer.getFrameIndex(window.name); 
				parent.layer.close(index);
			}else if(state=="1"){
				top.$.jBox.tip('它的方案状态为发布状态，不可编辑！ ');
			}else if(state=="2"){
				top.$.jBox.tip('它的方案状态为冻结状态，不可编辑！ ');
			}
		}
		function remarksAdd(remarksId,name){
			var id = remarksId;
			var remarks = encodeURI($('#'+remarksId).attr("data"));
			top.$.jBox.tip('正在打开！ ');
			top.LayerDialog1('remarksAddId','${ctx}/scheme/kpiSchemeSubjectivity/remarksAdd?id='+id+'&remarks='+remarks, name+'的考评关系', '1000px', '650px')
				
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/scheme/kpiSchemeSubjectivity/relationship?kpiId=${kpiSchemeKpi.id}">数据列表</a></li>
	</ul>
	<span style="font-size: 15px">
	所属方案：<span style="color: red">${kpiScheme.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	所属部门：<span style="color: red">${kpiScheme.office.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	考核人员类别：<span style="color: red">${fns:getDictLabel(kpiScheme.userType, 'sys_user_type', '')}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	KPI名称：<span style="color: red">${kpiSchemeKpi.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	KPI分数：<span style="color: red">${kpiSchemeKpi.score}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</span>
	<c:if test="${not empty schemeUserLst}">
		<input id="" style="width:100px!important;margin: 5px 5px" class="btn btn-primary" onclick="saveForm()" type="button" value="保 存 全 部"/>
	</c:if>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>被考核人工号</th>
				<th>姓名</th>
				<th>评分人及权重</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${schemeUserLst}" var="kpiFinalScore">
			<tr>
				<td>
					${kpiFinalScore.user.no}
				</td>
				<td>
					${kpiFinalScore.user.name}<!--${kpiFinalScore.kpiId}${kpiFinalScore.user.id}${kpiFinalScore.remarks}-->
				</td>
				<td width="40%">
					<textarea rows="3" style="width: 95%" id="remarks${kpiFinalScore.user.id}"  attrId="${kpiFinalScore.user.id}" class="input-xlarge remarks" disabled="true" data="${kpiFinalScore.remarks}">${kpiFinalScore.remarks}</textarea>
				</td>
				<td width="20%">
    				<a class="btnList" onclick="remarksAdd('remarks${kpiFinalScore.user.id}','${kpiFinalScore.user.name}')"><i class="icon-plus"></i></a>
				</td>
				
			</tr>
		</c:forEach>
		<c:if test="${not empty schemeUserLst}">
			<tr>
				<td colspan="3"></td>
				<td>
					<input id="" style="width: 100px!important;" class="btn btn-primary" onclick="saveForm()" type="button" value="保 存 全 部"/>
				</td>
			</tr>
		</c:if>
		</tbody>
	</table>
</body>
</html>