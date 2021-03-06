<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>人车/联系/网络关系表管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript"
        src="${ctxStatic}/ccm/validator/validator.js"></script>
<script type="text/javascript" src="${ctxStatic}/ccm/validator.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				function getUrlParam(name) {
				    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
				    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
				    if (r != null) return unescape(r[2]); return null; //返回参数值
				}
				var hide1=getUrlParam('hide1');
				var hide2=getUrlParam('hide2');
				if(hide1!=null&&hide2!=null){
					if(hide1=="true"){
						$('.hide1').show();
						$('.form-actions').hide();
					}
					if(hide2=="true"){
						$('.form-actions').hide();
						$('.hide2').show();
						
					}
				}else{
					$('.hide1').show();
					$('.hide2').show();
				}
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
									    //校验
                                        if(${cppPopVehile.type}==01){//车辆
                                            var carId = $("#textName").val();
                                            var html1 = '<label for="" class="error">车牌号码不符合规范 *<label>';
                                            if (isVehicleNumber(carId)) {
                                                $("#showCar").html("*");
                                            } else {
                                                $("#showCar").html(html1);
                                                return false;
                                            }
                                        }else if(${cppPopVehile.type}==03){//网络账号
                                            if(currentSubType=="04"){//类型选择邮箱的时候
                                                var email = $("#textName").val();
                                                var html1 = '<label for="" class="error">邮箱不符合规范 *<label>';
                                                if (isEmail(email)) {
                                                    $("#netAccount").html("*");
                                                } else {
                                                    $("#netAccount").html(html1);
                                                    return false;
                                                }
                                            }else{
                                                var account = $("#textName").val();
                                                var html1 = '<label for="" class="error">账号长度超过限制 *<label>';
                                                var express = /^.{1,20}$/;
                                                var result = express.test(account);
                                                if (result) {
                                                    $("#netAccount").html("*");
                                                } else {
                                                    $("#netAccount").html(html1);
                                                    return false;
                                                }
                                            }
                                        }
                                        //提交表单
                                        var otherTypeName="";
                                        if($("#otherTypeName")[0]){
                                            otherTypeName=$("#otherTypeName").val();
                                        }
                                        loading('正在提交，请稍等...');
                                        $.post("${ctx}/cpp/cppPopVehile/ajaxsave", {
                                            id:$("#id").val(),
                                            idCard:$("#idCard").val(),
                                            type:$("#type").val(),
                                            subType:$("#subType").val(),
                                            textName:$("#textName").val(),
                                            otherTypeName:otherTypeName
                                        }, function(data) {
                                            if(data == 'fail'){
                                                alert('信息不符合规范，请重新输入');
                                                return;
                                            }else{
                                            //     cppPopPopApp()
                                                if(${cppPopVehile.type}==01){
                                                    parent.window.frames['mainFrame'].vehileVehileList();
                                                }
                                                if(${cppPopVehile.type}==02){
                                                    parent.window.frames['mainFrame'].vehilePhoneList();
                                                }
                                                if(${cppPopVehile.type}==03){
                                                    parent.window.frames['mainFrame'].vehileWebList();
                                                }
                                            }
                                            closeTip();
                                            var index = parent.layer.getFrameIndex(window.name);
                                            parent.layer.close(index);
                                        });
                                        // form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#btnSubmit").removeAttr('disabled');
					$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
				
			});
    function deletex(id) {
		 confirmx('确认要删除该条信息吗？', function () {
			
			 
			 $.post("${ctx}/cpp/cppPopVehile/ajaxdelete",{						  										
					"id":id								
					}, function(data) {
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
						parent.alertInfo(data.message);
						if('${cppPopVehile.type}'=='02'){
							parent.window.frames['mainFrame'].vehilePhoneList();
						}else if ('${cppPopVehile.type}'=='01') {
							parent.window.frames['mainFrame'].vehileVehileList();
						}else if ('${cppPopVehile.type}'=='03') {
							parent.window.frames['mainFrame'].vehileWebList();
						}else if ('${cppPopVehile.type}'=='04') {
							parent.window.frames['mainFrame'].vehileBasicList();
						}
						
				   });	
		})
	}
	//车牌号验证方法
	function isVehicleNumber(vehicleNumber) {
		var xreg=/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))/;
		var creg=/^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$/;
		if(vehicleNumber.length == 7){
			return creg.test(vehicleNumber);
		} else if(vehicleNumber.length == 8){
			return xreg.test(vehicleNumber);
		} else{
			return false;
		}
	}
	//邮箱验证方法
    function isEmail(email){
        if (email.length>0) {
            var express = /^[A-Za-z0-9]+([-_.][A-Za-z0-9]+)*@([A-Za-z0-9]+[-.])+[A-Za-z0-9]{2,5}$/;
            return express.test(email);
        }else{
            return false;
        }
    }
    var currentSubType = "";
    function subTypeChange() {
        $("#showCar").html("*");
        $("#netAccount").html("*");
        currentSubType = $("#subType").val();
    }
</script>
<style type="text/css">
.form-horizontal .control-label{
width: 80px;
}
.form-horizontal .controls{
  margin-left: 100px;
}
</style>
</head>
<body>

	<form:form id="inputForm" modelAttribute="cppPopVehile"
		action="${ctx}/cpp/cppPopVehile/save" method="post"
		class="form-horizontal hide1">
		<sys:message content="${message}" />
		<form:hidden path="id" />
		<form:hidden path="idCard" />
		<c:if test="${cppPopVehile.type==02}">
		<form:hidden path="type" value="02" />
        <div class="control-group" style="padding-top: 5px">
			<label class="control-label"><span class="help-inline"><font color="red" >*</font></span>类型：</label>
			<div class="controls">
				<form:select path="subType" class="input-xlarge required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('cpp_phone_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red" >*</font></span>联系方式：</label>
			<div class="controls">
				<form:input path="textName" htmlEscape="false" maxlength="64"
					class="input-xlarge required phone" />

			</div>
		</div>
		</c:if>
		
		
		
		<c:if test="${cppPopVehile.type==01}">
		<form:hidden path="type" value="01" />
        <div class="control-group" style="padding-top: 8px">
			<label class="control-label"><span class="help-inline"><font color="red" >*</font></span>车辆类型：</label>
			<div class="controls">
				<form:select cssStyle="max-height:100px;" path="subType" class="input-xlarge required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('cpp_vehile_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red" id="showCar">*</font></span>车牌号：</label>
			<div class="controls">
				<form:input path="textName" htmlEscape="false" maxlength="64"
					class="input-xlarge" />

			</div>
		</div>
		</c:if>
		
		<c:if test="${cppPopVehile.type==03}">
		<form:hidden path="type" value="03" />
        <div class="control-group" style="padding-top: 5px">
			<label class="control-label"><span class="help-inline"><font color="red" >*</font></span>账号类型：</label>
			<div class="controls">
				<form:select path="subType" onchange="subTypeChange()" class="input-xlarge required">
					<form:option value="" label="" />
					<form:options items="${fns:getDictList('cpp_web_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>

			</div>
		</div>
		<div class="control-group" style="padding-top: 5px">
			<label class="control-label"><span class="help-inline"><font color="red" id="netAccount">*</font></span>网络账号：</label>
			<div class="controls">
				<form:input path="textName" htmlEscape="false" maxlength="64"
					class="input-xlarge required" />

			</div>
		</div>
		</c:if>
		
		<c:if test="${cppPopVehile.type==04}">
		<form:hidden path="type" value="04" />
        <div class="control-group" style="padding-top: 5px">
			<label class="control-label"><span class="help-inline"><font color="red" >*</font></span>补充类型：</label>
			<div class="controls">
				<form:input path="otherTypeName" htmlEscape="false" maxlength="20"
					class="input-xlarge required" />

			</div>
		</div>
		<div class="control-group" style="padding-top: 5px">
			<label class="control-label"><span class="help-inline"><font color="red" >*</font></span>补充信息：</label>
			<div class="controls">
				<form:input path="textName" htmlEscape="false" maxlength="64"
					class="input-xlarge required" />

			</div>
		</div>
		</c:if>
		
		<div class="form-actions">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;&nbsp;
					<c:if test="${not empty cppPopVehile.id}">
			    <input id="btnDelete" class="btn btn-danger" type="button" onclick="deletex('${cppPopVehile.id}')" value="删除" />
		</c:if>
		</div>
	</form:form>
</body>
</html>