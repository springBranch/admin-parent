<!DOCTYPE html>
<html>

	<head>
		<#include "../../common/head.ftl">
		<title>菜单管理</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<a class="layui-r" href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#x1002;</i></a>
			<fieldset class="layui-elem-field layui-field-title">
				<legend>系统管理  > 菜单列表</legend>
			</fieldset>
			<form class="layui-form layui-form-pane" action="${ctx!}/system/adminmenu/list" method="post" id="myForm">
				<input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex }"/>	
				<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>	
				<input type="hidden" name="parentId" id="parentId" value="${parentId }"/>
			</form>
			<div class="layui-menu">
				<#if parentId gt 0 >
					<span class="layui-l">
						<button class="layui-btn" onclick="menu_goback(${grandParentId })">
						<i class="layui-icon">&#xe603;</i> 返回上一级</button>
					</span>
				</#if>
					<span class="layui-l">
						<@buttonTag name="添加菜单" menuLink="/system/adminmenu/toadd" onclick="menu_add()" icon="&#xe608;" />
					</span>
				<span class="layui-r">共有数据：<strong>${page.total!}</strong> 条</span>
			</div>
			<div class="layui-form">
				<table class="layui-table">
					<thead>
						<tr>
							<th>序号</th>
							<th>菜单名称</th>
							<th>菜单链接</th>
							<#if parentId gt 0 >
								<th>记录日志</th>
							</#if>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<#if page?? && page.rows??>
							<#list page.rows as item>
								<tr>
									<td>${item_index + 1 }</td>
									<td>${item.name! }</td>
									<td>${item.link! }</td>
									<#if parentId gt 0 >
										<td><#if item.isJournal==1 >是<#else>否</#if></td>
									</#if>
									<td>
										<@buttonTag name="子菜单" menuLink="/system/adminmenu/list" onclick="menu_children('${item.id!}')" class="layui-btn-small" />
										<@buttonTag name="修改" menuLink="/system/adminmenu/toedit" onclick="menu_edit('${item.id!}')" class="layui-btn-small layui-btn-warm" />
										<@buttonTag name="删除" menuLink="/system/adminmenu/delete" onclick="menu_del('${item.id!}')" class="layui-btn-small layui-btn-danger" />
									</td>
								</tr>
							</#list>
						</#if>
					</tbody>
				</table>
			</div>
			<div class="admin-table-page">
				<div id="page" class="page layui-r">
				</div>
			</div>
		</div>

		<script>
			layui.use(['layer','form', 'laypage'], function() {
				var layer = layui.layer,
					form = layui.form(),
					laypage = layui.laypage;

				//分页
				laypage({
					cont: 'page',
					pages: ${pageCount!},
					curr: ${pageIndex!},
					jump: function(obj, first) {
						if(!first) {
							layer.load(0, { shade: false });
							$("#pageIndex").val(obj.curr)
							$("#myForm").submit();
						}
					}
				});

			});
			
			// 返回上级菜单
			function menu_goback(param){
				var v_url = '${ctx}/system/adminmenu/list?parentId=' + param;
				window.location.href=v_url;
			}
			
			//添加菜单
			function menu_add(){
				layer.open({
					type: 2,
					area: ['50%', '70%'],
					title: '系统管理    > 添加菜单',
					content: '${ctx!}/system/adminmenu/toadd?parentId=${parentId!}'
				});
			}
			
			//子菜单
			function menu_children(param){
				var v_url = '${ctx!}/system/adminmenu/list?parentId=' + param;
				window.location.href=v_url;
			}
			
			//修改菜单
			function menu_edit(param){
				layer.open({
					type: 2,
					area: ['50%', '70%'],
					title: '系统管理    > 修改菜单',
					content: '${ctx!}/system/adminmenu/toedit?id=' + param
				});
			}
			
			//删除菜单
			function menu_del(id){
				layer.confirm('确认要删除吗？',function(index){
					$.get("${ctx!}/system/adminmenu/delete?id=" + id, {}, function(result){
						if(result.status){
							layer.msg(result.message, {icon:1,time:1000}, function(){
								location.reload(); //刷新列表
							});
						}else{
							layer.msg(result.message, {icon:1,time:2000}, function(){
								location.reload(); //刷新列表
							});
						}
					}, "json");
				});
			}
		</script>
	</body>

</html>