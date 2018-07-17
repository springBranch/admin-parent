<!DOCTYPE html>
<html>
	<head>
		<#include "../../common/head.ftl">
		<title>用户管理</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<form class="layui-form layui-form-pane" action="${ctx!}/system/userinfo/list" method="post" id="myForm">
				<input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex }"/>	
				<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
				<div class="layui-search">
					<div class="layui-input-inline">
						<input type="text" value="${realName!}" name="realName" id="realName" placeholder="用户姓名" class="layui-input">
					</div>
					<div class="layui-input-inline">
						<button class="layui-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
					</div>
				</div>		
			</form>

			<div class="layui-menu">
					<span class="layui-l">
						<@buttonTag name="添加用户" menuLink="/system/userinfo/toadd" onclick="user_add()" icon="&#xe608;" />
					</span>
				<span class="layui-r">共有数据：<strong>${page.total!}</strong> 条</span>
			</div>
			<div class="layui-form">
				<table class="layui-table">
					<thead>
						<tr>
							<th>序号</th>
							<th>姓名</th>
							<th>角色</th>
							<th>手机号</th>
							<th>创建时间</th>
							<th>最后一次登录时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<#if page?? && page.rows??>
							<#list page.rows as item>
								<tr>
									<td>${item_index + 1 }</td>
									<td>${item.realName!}</td>
									<td>${item.roleName!}</td>
									<td>${item.userMobile!}</td>
									<td>${item.createTime?if_exists?string("yyyy-MM-dd HH:mm:ss")}</td>
									<td><#if item.loginTime??>${item.loginTime?string("yyyy-MM-dd HH:mm:ss")}</#if></td>
									<td>
										<@buttonTag name="修改" menuLink="/system/userinfo/toedit" onclick="user_edit('${item.id!}')" class="layui-btn-small layui-btn-warm" />
										<@buttonTag name="删除" menuLink="/system/userinfo/delete" onclick="user_del('${item.id!}')" class="layui-btn-small layui-btn-danger" />
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
			
			//添加用户
			function user_add(){
				layer.open({
					type: 2,
					area: ['60%', '90%'],
					title: '系统管理    > 添加用户',
					content: '${ctx}/system/userinfo/toadd'
				});
			}
			//修改用户
			function user_edit(id){
				layer.open({
					type: 2,
					area: ['60%', '90%'],
					title: '系统管理    > 修改用户',
					content: '${ctx}/system/userinfo/toedit?id=' + id
				});
			}
			//删除用户
			function user_del(id){
				layer.confirm('用户删除须谨慎，确认要删除吗？',function(index){
					var url = '${ctx}/system/userinfo/delete?id=' + id;
					$.post(url,function(result) {
			    		if (result.status) {
			    			layer.msg(result.message, {icon:1,time:1000}, function(){
								location.reload(); //刷新列表
							});
			    		}else{
			    			layer.msg(result.message,{icon:1,time:2000});
			    		}
			    	}, "json");
				});
			}
		</script>
	</body>

</html>