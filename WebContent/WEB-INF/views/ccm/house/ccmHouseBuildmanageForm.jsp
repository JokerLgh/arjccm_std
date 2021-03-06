<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>建筑物管理</title>
<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
<meta name="decorator" content="default"/>
<!-- 鱼骨图 -->
<link rel="stylesheet" href="${ctxStatic}/ccm/event/css/fishBonePop.css" />
<script type="text/javascript" src="${ctxStatic}/ccm/event/js/fishBonePop.js"></script>
<script type="text/javascript" src="${ctxStatic}/ccm/event/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		$("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		$("td").css({"padding":"8px"});
		$("td").css({"border":"0px dashed #CCCCCC"});

		//创建案事件历史
		var jsonString = '${documentList}';
           data = JSON.parse(jsonString);
		$(".fishBone1").fishBone(data, '${ctx}','deal');
		$(".fishBone2").fishBone(data, '${ctx}','read');
	});
    //时间合格标记
    /*var flag=false;
    function initAge(data) {
        // console.log("1111")
        var date=new Date(data);
        var time=new Date().getTime()-date.getTime();
        var age=time/1000/60/60/24/365;
        age=parseInt(age);
        if(time<0 || age>150 ){
            //时间不合法
            $("#tshi").show()
            flag=false;
        }else{
            $("#tshi").hide();
            flag=true;
        }
    }*/
	function saveForm(){
		var areaId = $("#areaId").val();
		var html1 = '<label for="" class="error">必填信息<label>';
		if(areaId.length!=0){
			$("#show1").html("");
		}else{
			$("#show1").html(html1);
			$("#areaName").focus();
		}
		//禁止页面提交
		if(areaId.length==0){
            $("#show1").html(html1);
            $("#areaName").focus();
            event.preventDefault();
        }
	}

	function ThisLayerDialog(src, title, height, width) {
		parent.drawForm = parent.layer.open({
			type: 2,
			title: title,
			area: [height, width],
			fixed: true, //固定
			maxmin: false,
			/*   btn: ['关闭'], //可以无限个按钮 */
			content: src,
			zIndex: '1992',
			cancel: function () {
				//防止取消和删除效果一样
				window.isCancel = true;
			},
			end: function () {
				if (!window.isCancel) {
					$("#areaPoint")[0].value = parent.areaPoint;
					parent.areaPoint = null;
					$("#areaMap")[0].value = parent.areaMap;
					parent.areaMap = null;
				}
				window.isCancel = null;
			}
		});
	}

</script>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
<ul class="nav nav-tabs hide1">
	<li><a style="width: 140px;text-align:center" href="${ctx}/report/ccmReportOthers/houseAndBuild">数据统计</a></li>
	<li><a style="width: 140px;text-align:center" href="${ctx}/house/ccmHouseBuildmanage/">数据列表</a></li>
	<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/house/ccmHouseBuildmanage/form?id=${ccmHouseBuildmanage.id}">数据<shiro:hasPermission name="house:ccmHouseBuildmanage:edit">${not empty ccmHouseBuildmanage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="house:ccmHouseBuildmanage:edit">查看</shiro:lacksPermission></a></li>
	<%--   <c:if test="${not empty ccmHouseBuildmanage.id}">
          <li><a href="${ctx}/log/ccmLogTail/formPro?relevance_id=${ccmHouseBuildmanage.id}&relevance_table=ccm_house_buildmanage">跟踪信息<shiro:hasPermission name="log:ccmLogTail:edit">${not empty ccmLogTail.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="log:ccmLogTail:edit">查看</shiro:lacksPermission></a></li>
      </c:if> --%>
</ul>
<form:form id="inputForm" modelAttribute="ccmHouseBuildmanage" action="${ctx}/house/ccmHouseBuildmanage/save" method="post" class="form-horizontal">
	<form:hidden path="id"/>
	<sys:message content="${message}"/>
	<h4 class="color-bg6">建筑物信息</h4>
	<table border="0px"
		   style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;">
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>小区（单位）名称：</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="512" class="input-xlarge required"/>
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>所属网格：</label>
					<div class="controls">
						<sys:treeselect id="area" name="area.id" value="${ccmHouseBuildmanage.area.id}" labelName="area.name" labelValue="${ccmHouseBuildmanage.area.name}" title="网格" url="/tree/ccmTree/treeDataArea?type=7&areaid=" cssClass="" allowClear="true" notAllowSelectParent="true"/>
						<span class="help-inline"><font color="red" id="show1"></font> </span>
					</div>
				</div>
			</td>
            <td></td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>建筑物名称：</label>
					<div class="controls">
						<form:input path="buildname" htmlEscape="false" maxlength="512" class="input-xlarge required"/>

					</div>
				</div>
			</td>
            <td>
                <div>
                    <label class="control-label">建筑面积（平方米）：</label>
                    <div class="controls">
                        <form:input path="floorArea" htmlEscape="false" maxlength="10" class="input-xlarge number positiveNumber " />
                    </div>
                </div>
            </td>
            <td>
                <div>
                    <label class="control-label"> <span class="help-inline"><font color="red">*</font> </span>层数：</label>
                    <div class="controls">
                        <form:input path="pilesNum" htmlEscape="false" maxlength="3" class="input-xlarge required digits" min="0" type= "number"/>
                    </div>
                </div>
            </td>

		</tr>
        <tr>
            <td  >
                <div>
                    <label class="control-label">建筑物图片：</label>
                    <div class="controls">
                        <form:hidden id="images" path="images" htmlEscape="false" maxlength="255" class="input-xlarge"/>
                        <sys:ckfinder input="images" type="images" uploadPath="/photo/LouDong" selectMultiple="false" maxWidth="240" maxHeight="360"/>
                    </div>
                </div>
            </td>
        </tr>
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>单元数：</label>
					<div class="controls">
						<form:input path="elemNum" htmlEscape="false" maxlength="2" class="input-xlarge required digits"  min="0"  type= "number" />

					</div>
				</div>
			</td>
            <td>
                <div>
                    <label class="control-label">建筑物户数：</label>
                    <div class="controls">
                        <form:input path="buildNum" htmlEscape="false" maxlength="4"  min="0"  class="input-xlarge digits" type= "number" />
                    </div>
                </div>
            </td>
            <td>
                <div>
                    <label class="control-label">建筑物人数：</label>
                    <div class="controls">
                        <form:input path="buildPeo" htmlEscape="false" maxlength="6" class="input-xlarge digits"  min="0"  type= "number" />
                    </div>
                </div>
            </td>
		</tr>

		<tr>
			<td>
				<div>
					<label class="control-label">图标：</label>
					<div class="controls">
						<sys:iconselect id="image" name="image" value="${ccmHouseBuildmanage.image}"/>
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>所在地行政区划代码：</label>
					<div class="controls">
						<form:input path="residence" htmlEscape="false" maxlength="6" class="input-xlarge required"/>

					</div>
				</div>
			</td>

			<td>
				<div>
					<label class="control-label">所在地详细地址：</label>
					<div class="controls">
						<form:input path="residencedetail" htmlEscape="false" maxlength="64" class="input-xlarge "/>
					</div>
				</div>
			</td>
                <td>
                    <div>
                        <label class="control-label"> <span class="help-inline"><font color="red">*</font> </span>建筑物编号：</label>
                        <div class="controls">
                            <form:input path="code" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

                        </div>
                    </div>
                </td>
        </tr>
        <tr>
<%--		<c:if test="${not empty ccmHouseBuildmanage.id}"></c:if>--%>
			<shiro:hasPermission name="house:ccmHouseBuildmanage:edit">
			<td>
				<div>
					<label class="control-label">中心点坐标：</label>
					<div class="controls">
						<form:input path="areaPoint" readonly="true" htmlEscape="false" maxlength="40" class="input-xlarge" />
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">区域图：</label>
					<div class="controls">
						<form:input path="areaMap" readonly="true" htmlEscape="false" maxlength="2000" class="input-xlarge "/>
						<a onclick="ThisLayerDialog('${ctx}/event/ccmEventIncident/drawForm?areaMap='+$('#areaMap').val()+'&areaPoint='+$('#areaPoint').val(), '标注', '1100px', '700px');"
						   class="btn hide1 btn-primary">标 注</a>
					</div>
				</div>
			</td>
		</tr>
		</shiro:hasPermission>

        </tr>
	</table>
	<%--<div style="margin-top:50px"></div>--%>
	<h4 class="color-bg6">建筑物楼栋长信息</h4>
	<table border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;">
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>楼栋长姓名：</label>
					<div class="controls">
						<form:input path="buildPname" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>性别：</label>
					<div class="controls">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label"
										   itemValue="value" htmlEscape="false" class="required"/>
					</div>
				</div>
			</td>
		</tr>
		<tr>
            <td>
                <div>
                    <label class="control-label"><span class="help-inline"><font color="red">*</font> </span>民族：</label>
                    <div class="controls">
                        <form:select path="nation" class="input-xlarge required">
                            <form:options items="${fns:getDictList('sys_volk')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>

                    </div>
                </div>
            </td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>手机号码：</label>
					<div class="controls">
						<form:input path="tel" htmlEscape="false" maxlength="11" class="input-xlarge required mobile"/>

					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">固定电话：</label>
					<div class="controls">
						<form:input path="phone" htmlEscape="false" maxlength="12" class="input-xlarge simplePhone"/>
					</div>
				</div>
			</td>
		</tr>
		<tr>

			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>政治面貌：</label>
					<div class="controls">
						<form:select path="content" class="input-xlarge required">
							<form:options items="${fns:getDictList('sys_ccm_poli_stat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>

					</div>
				</div>
			</td>
            <td>
                <div>
                    <label class="control-label"> <span class="help-inline"><font color="red">*</font> </span>出生日期：</label>
                    <div class="controls">
                        <input name="birthday" type="text" readonly="readonly" onchange="initAge(this.value)" maxlength="20"
                               class="input-medium Wdate required dateVerify" value="<fmt:formatDate
							   value="${ccmHouseBuildmanage.birthday}" pattern="yyyy-MM-dd"/>"
                               onclick="WdatePicker({minDate:'{%y-150}-%M-%d',maxDate: '%y-%M-%d',dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

                    </div>
                </div>
            </td>
            <td>
                <div>
                    <label class="control-label"> <span class="help-inline"><font color="red">*</font> </span>学历：</label>
                    <div class="controls">
                        <form:select path="education" class="input-xlarge required">
                            <form:options items="${fns:getDictList('sys_ccm_degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>

                    </div>
                </div>
            </td>
		</tr>
		<tr>
			<td colspan="2">
				<div>
					<label class="control-label">备注信息：</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " />
					</div>
				</div>
			</td>
		</tr>

	</table>


	<%-- <table border="1px"
        style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
    <%@include file="/WEB-INF/views/include/ccmlog.jsp" %>
    </table> --%>

	<div class="form-actions">
		<shiro:hasPermission name="house:ccmHouseBuildmanage:edit"><input id="btnSubmit" class="btn btn-primary" onclick="saveForm()" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		<input id="btnCancel" class="btn"  type="button" value="返 回" onclick="history.go(-1)"/>
	</div>
</form:form><br>
<c:if test="${documentNumber > 0}">
	<shiro:hasPermission name="log:ccmLogTail:edit">
		<h4>&nbsp;跟踪信息：</h4>
		<br>
		<div class="fishBone1" ></div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="log:ccmLogTail:edit">
		<h4>&nbsp;查看跟踪信息：</h4>
		<br>
		<div class="fishBone2" ></div>
	</shiro:lacksPermission>
</c:if>

</body>
</html>
