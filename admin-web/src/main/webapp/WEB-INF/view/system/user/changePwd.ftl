<!DOCTYPE html>
<html>

	<head>
		<#include "../../common/head.ftl">
		<title>修改登录密码</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>修改登录密码</legend>
			</fieldset>

			<form class="layui-form layui-form-pane" action="" id="myForm">
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>账户</label>
					<div class="layui-input-block">
						<input type="text" id="realName" name="realName"  value="${user.realName!}" lay-verify="required" class="layui-input" readonly>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>原密码</label>
					<div class="layui-input-block">
						<input type="password" id="oldpassword" name="oldpassword" lay-verify="required" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>新密码</label>
					<div class="layui-input-block">
						<input type="password" id="newpassword" name="newpassword" lay-verify="pass" class="layui-input" maxlength="12">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>确认新密码</label>
					<div class="layui-input-block">
						<input type="password" id="newpassword2" name="newpassword2" lay-verify="pass" class="layui-input" maxlength="12">
					</div>
				</div>
				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<button class="layui-btn" lay-submit lay-filter="mySubmit"><i class="layui-icon">&#xe61f;</i>保存</button>
						<button type="button" class="layui-btn layui-btn-primary" onclick="layer_close();"><i class="layui-icon">&#x1006;</i>取消</button>
					</div>
				</div>
			</form>
		</div>

		<script>
			layui.use(['layer', 'form'], function() {
				var layer = layui.layer,
				form = layui.form();
				
				form.verify({
					  pass: [
					    /^[\S]{6,12}$/
					    ,'密码必须6到12位，且不能出现空格'
					  ] 
					});
				
				//监听提交
				form.on('submit(mySubmit)', function(data) {
					if($("#newpassword").val() != $("#newpassword2").val()){
						layer.msg("两次密码输入不一样", {icon:5,time:1000});
						return ;
					}
				
					 $("#myForm").ajaxSubmit({
				            type: 'post', 
				            url: '${ctx!}/system/userinfo/changepwd',
				            dataType: "json",
				            success: function (data) {
				            	if (data.status) {
				            		layer.msg(data.message, {icon:6,time:1000}, function(){
				            			logout();
				            		});
				        		}else {
				        			layer.msg(data.message, {icon:5,time:1000});
				        		}
				            },
				            error: function () {
				            	layer.msg("发送新增请求失败", {icon:5,time:1000});
				            }
				        });
				        
					return false;
				});
			});
			
			function logout(){
			    $.post("${ctx!}/logout", function(data){
			        if (data.status){
			        	parent.location.reload();
			        }
			    }, "json");
			}
		</script>
	</body>

</html>