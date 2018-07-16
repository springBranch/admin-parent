<!DOCTYPE html>
<html>

	<head>
		<#include "../../common/head.ftl">
		<title>添加菜单</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>添加菜单</legend>
			</fieldset>

			<form class="layui-form layui-form-pane" action="" id="myForm">
				<input type="hidden" name="parentId" value="${parentId! }">
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>菜单名称</label>
					<div class="layui-input-block">
						<input type="text" id="name" name="name" lay-verify="required" class="layui-input">
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label">菜单链接</label>
					<div class="layui-input-block">
						<input type="text" id="link" name="link" class="layui-input">
					</div>
				</div>
				<#if parentId gt 0 >
					<div class="layui-form-item">
						<label class="layui-form-label">记录日志</label>
						<div class="layui-input-inline">
							<input type="hidden" id="isJournal" name="isJournal" value="0">
							<input type="checkbox" lay-skin="switch" lay-filter="switchJournal" lay-text="是|否">
						</div>
					</div>
				</#if>

				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<@buttonTag name="保存" menuLink="/system/adminmenu/add" layFilter="mySubmit" class="layui-btn-warm"/>
						<button type="button" class="layui-btn layui-btn-primary" onclick="layer_close();"><i class="layui-icon">&#x1006;</i>取消</button>
					</div>
				</div>
			</form>
		</div>

		<script>
			layui.use(['layer', 'form'], function() {
				var layer = layui.layer,
				form = layui.form();
				
				//监听指定开关
			    form.on('switch(switchJournal)', function(data){
			    	if(this.checked){
			    		$("#isJournal").val(1);
			    	}else{
			    		$("#isJournal").val(0);
			    	}
			      });
			      
				//监听提交
				form.on('submit(mySubmit)', function(data) {
					 $("#myForm").ajaxSubmit({
				            type: 'post', 
				            url: '${ctx}/system/adminmenu/add',
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