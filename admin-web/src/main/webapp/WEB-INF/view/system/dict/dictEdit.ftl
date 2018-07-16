<!DOCTYPE html>
<html>
	<head>
		<#include "../../common/head.ftl">
		<title>修改字典</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>修改字典</legend>
			</fieldset>

			<form class="layui-form layui-form-pane" action="" id="myForm">
			 	<input type="hidden" name="id" id="id" value="${dictionary.id}"/>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>KEY</label>
					<div class="layui-input-block">
						<input type="text" id="dictKey" name="dictKey" value="${dictionary.dictKey!}" lay-verify="required" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>VALUE</label>
					<div class="layui-input-block">
						<input type="text" id="dictValue" name="dictValue" value="${dictionary.dictValue!}" lay-verify="required" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">备注</label>
					<div class="layui-input-block">
						<input type="text" id="comments" name="comments" value="${dictionary.comments!}" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-block">
						<@buttonTag name="保存" menuLink="/system/dictionary/edit" layFilter="mySubmit" class="layui-btn-warm"/>
						<button type="button" class="layui-btn layui-btn-primary" onclick="layer_close();"><i class="layui-icon">&#x1006;</i>取消</button>
					</div>
				</div>
			</form>
		</div>

		<script>
			layui.use(['layer', 'form'], function() {
				var layer = layui.layer,
				form = layui.form();
				//监听提交
				form.on('submit(mySubmit)', function(data) {
					 $("#myForm").ajaxSubmit({
				            type: 'post', 
				            url: '${ctx}/system/dictionary/edit',
				            dataType: "json",
				            success: function (data) {
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
				            	layer.msg("发送新增请求失败", {icon:5,time:1000});
				            }
				        });
				        
					return false;
				});
			});
		</script>
	</body>

</html>