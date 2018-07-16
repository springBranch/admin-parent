<!DOCTYPE html>
<html>
	<head>
		<#include "../../common/head.ftl">
		<title>字典管理</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<a class="layui-r" href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#x1002;</i></a>
			<fieldset class="layui-elem-field layui-field-title">
				<legend>系统管理  > 字典列表</legend>
			</fieldset>
			<form class="layui-form layui-form-pane" action="${ctx!}/system/dictionary/list" method="post" id="myForm">
				<input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex }"/>	
				<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>	
				<div class="layui-search">
					<div class="layui-input-inline">
						<input type="text" value="${dictKey!}" name="dictKey" id="dictKey" placeholder="KEY值" class="layui-input">
					</div>
					<div class="layui-input-inline">
						<input type="text" value="${dictKeyName!}" name="dictKeyName" id="dictKeyName" placeholder="KEY名" class="layui-input">
					</div>
					<div class="layui-input-inline">
						<button class="layui-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
					</div>
				</div>		
			</form>
			<div class="layui-menu">
					<span class="layui-l">
						<@buttonTag name="添加字典" menuLink="/system/dictionary/toadd" onclick="dict_add()" icon="&#xe608;" />
					</span>
				<span class="layui-r">共有数据：<strong>${page.total!}</strong> 条</span>
			</div>
			<div class="layui-form">
				<table class="layui-table">
					<thead>
						<tr>
							<th>序号</th>
							<th>KEY</th>
							<th>VALUE</th>
							<th>备注</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<#if page?? && page.rows??>
							<#list page.rows as item>
								<tr>
									<td>${item_index + 1 }</td>
									<td class="text-m">${item.dictKey! }</td>
									<td class="text-m">${item.dictValue! }</td>
									<td class="text-m">${item.comments! }</td>
									<td>
										<@buttonTag name="修改" menuLink="/system/dictionary/toedit" onclick="dict_edit('${item.id!}')" class="layui-btn-small layui-btn-warm" />
										<@buttonTag name="删除" menuLink="/system/dictionary/delete" onclick="dict_del('${item.id!}')" class="layui-btn-small layui-btn-danger" />
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
			
			//添加字典
			function dict_add(){
				layer.open({
					type: 2,
					area: ['50%', '70%'],
					title: '系统管理    > 添加字典',
					content: '${ctx!}/system/dictionary/toadd'
				});
			}
			
			//修改字典
			function dict_edit(param){
				layer.open({
					type: 2,
					area: ['50%', '70%'],
					title: '系统管理    > 修改字典',
					content: '${ctx!}/system/dictionary/toedit?id=' + param
				});
			}
			
			//删除字典
			function dict_del(id){
				layer.confirm('确认要删除吗？',function(index){
					$.post("${ctx!}/system/dictionary/delete", 
					{
						id : id
					}, 
					function(result){
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