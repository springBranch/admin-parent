<!DOCTYPE html>
<html>

	<head>
		<#include "../../common/head.ftl">
		<title>添加用户</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>添加用户</legend>
			</fieldset>

			<form class="layui-form layui-form-pane" action="" id="myForm">
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>手机号</label>
					<div class="layui-input-block">
						<input type="text" id="userMobile" name="userMobile" lay-verify="phone" class="layui-input" maxlength="11">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>用户姓名</label>
					<div class="layui-input-block">
						<input type="text" id="realName" name="realName" lay-verify="required" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>登录密码</label>
					<div class="layui-input-block">
						<input type="password" id="userPwd" name="userPwd" lay-verify="pass" class="layui-input" maxlength="12">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>角色分配</label>
					<div class="layui-input-block">
						<select name="roleId" id="roleId" class="select">
							<#if roleList??>
								<#list roleList as item>
								<option value="${item.id}">${item.roleName}</option>
								</#list>
							</#if>
						</select>
					</div>
				</div>
				
				<#if userList??>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>用户上级</label>
					<div class="layui-input-block">
						<select name="creator" id="creator" class="select">
								<#list userList as item>
								<option value="${item.id}">${item.realName}</option>
								</#list>
						</select>
					</div>
				</div>
				</#if>
				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<@buttonTag name="保存" menuLink="/system/userinfo/add" layFilter="mySubmit" class="layui-btn-warm"/>
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
			      $("#myForm").ajaxSubmit({
			            url: "${ctx!}/system/userinfo/add",
			            type: "POST",
			            dataType: "json",
			            success: function(data){
			            	if (data.status) {
			            		layer.msg(data.message, {icon:6,time:1000}, function(){
			            			var index = parent.layer.getFrameIndex(window.name);
				            			parent.location.reload(); //刷新列表
						    			parent.layer.close(index); //关闭窗口
			            		});
			        		}else {
			        			layer.msg(data.message, {icon:5,time:1000});
			        		}
			            },
			            error: function () {
			            	layer.alert("发送新增请求失败");
			            }
			        });
				        
					return false;
				});
				
			});
			
		</script>
	</body>

</html>