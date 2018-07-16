<!DOCTYPE html>
<html>

	<head>
		<#include "../../common/head.ftl">
		<@css_version paths=["plugins/zTree/css/zTreeStyle/zTreeStyle.css"]/>
		<@js_version paths=["plugins/zTree/js/jquery.ztree.core.js", "plugins/zTree/js/jquery.ztree.excheck.js"]/>
		<title>修改角色</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>修改角色</legend>
			</fieldset>

			<form class="layui-form layui-form-pane" action="" id="myForm">
				<input type="hidden" id="id" name="id" value="${roleInfo.id!}"> 
				<div class="layui-form-item">
					<label class="layui-form-label"><span class="color-red">*</span>角色名称</label>
					<div class="layui-input-block">
						<input type="text" id="roleName" name="roleName" value="${roleInfo.roleName! }"  lay-verify="required" class="layui-input">
					</div>
				</div>
				<#if roleList??>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="color-red">*</span>上级角色</label>
						<div class="layui-input-block">
							<select name="parentId" id="parentId" lay-filter="parentId" class="select">
								<#list roleList as item>
									<option value="${item.id!}" <#if roleInfo.parentId==item.id >selected</#if>>${item.roleName!}</option>
								</#list>
							</select>
						</div>
					</div>
				</#if>
				<div class="layui-form-item">
					<label class="layui-form-label">描述</label>
					<div class="layui-input-block">
						<input type="text" id="description" name="description" value="${roleInfo.description! }" class="layui-input">
					</div>
				</div>
				
				<div class="layui-form-item">
					<label class="layui-form-label">权限分配：</label>
					<div class="layui-input-block">
						<input type="hidden" id="menuIds" name="menuIds"> 
						<fieldset class="layui-elem-field layui-field-title">
							<ul id="menuTree" class="ztree"></ul> 
						</fieldset>
					</div>
				</div>
				
				
				<div class="layui-form-item">
					<div class="layui-input-block">
						<@buttonTag name="保存" menuLink="/system/roleinfo/edit" layFilter="mySubmit" class="layui-btn-warm"/>
						<button type="button" class="layui-btn layui-btn-primary" onclick="layer_close();"><i class="layui-icon">&#x1006;</i>取消</button>
					</div>
				</div>
			</form>
		</div>

		<script>
			layui.use(['layer','tree','form'], function() {
				var layer = layui.layer,
				form = layui.form();
				
				form.on('select(parentId)', function (data) {
				 	var parentId = $('#parentId').val();
					if(parentId!=""){
				  	  inittree(parentId);
					}
		        });
		        
				//监听提交
				form.on('submit(mySubmit)', function(data) {

					var treeObj = $.fn.zTree.getZTreeObj("menuTree"),nodes = treeObj.getCheckedNodes(true);
			      	var selNode = [];
					for(var i = 0; i < nodes.length; i++){
			        	selNode[i] = nodes[i].id;
			        }
					$("#menuIds").val(selNode);
					$("#myForm").ajaxSubmit({
			            url: "${ctx}/system/roleinfo/edit",
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
			
			
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		
		$(function(){
	        //初始化菜单
	        inittree(${parentRole.id!});
	        
		});
		
		
		function inittree(roleId){
			$.post('${ctx}/system/roleinfo/inittree',
				{
					roleId : roleId,
					rIds : "${roleInfo.menuIds!}"
				},
				function(data) {
	    			if (data.status) {
						var zNodes = data.data;
		    			var obj = eval(zNodes);
		    			$.fn.zTree.init($("#menuTree"), setting, obj);
		    			var code;
		    			var zTree = $.fn.zTree.getZTreeObj("menuTree");
		    			zTree.expandAll(true);
		    			if (!code) code = $("#code");
		    			code.empty();
		    		}
	    		}, "json");	
		}
		
		</script>
	</body>

</html>