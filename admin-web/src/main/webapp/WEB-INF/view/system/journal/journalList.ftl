<!DOCTYPE html>
<html>
	<head>
		<#include "../../common/head.ftl">
		<title>系统日志管理</title>
	</head>

	<body>
		<div class="layui-tab-content ">
			<form class="layui-form layui-form-pane" action="${ctx!}/system/journal/list" method="post" id="myForm">
				<input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex }"/>	
				<input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>	
				<div class="layui-search">
					<div class="layui-input-inline">
						<select id="journalType" name="journalType">
							<option value=" ">日志类型</option>
								<option value="1" <#if journalType?? && journalType == 1>selected="selected"</#if>>操作日志</option>
								<option value="2" <#if journalType?? && journalType == 2>selected="selected"</#if>>异常日志</option>
						</select>
					</div>
					<div class="layui-input-inline">
						<input type="text" value="${userName!}" name="userName" id="userName" placeholder="操作用户" class="layui-input">
					</div>
					<div class="layui-input-inline">
						<input type="text" value="${operateName!}" name="operateName" id="operateName" placeholder="操作事件" class="layui-input">
					</div>
					<div class="layui-input-inline">
						<input type="text" value="${requestPath!}" name="requestPath" id="requestPath" placeholder="请求路径" class="layui-input">
					</div>
					<div class="layui-input-inline">
						<input class="layui-input" placeholder="操作开始日" id="strDate" name="strDate" value="${strDate!}" 
					     	 onclick="layui.laydate({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
					</div>
					--
					<div class="layui-input-inline">
						<input class="layui-input" placeholder="操作截止日" id="endDate" name="endDate" value="${endDate!}"
					     	 onclick="layui.laydate({elem: this, istime: true, format: 'YYYY-MM-DD hh:mm:ss'})">
					</div>
					<div class="layui-input-inline">
						<button class="layui-btn"><i class="layui-icon">&#xe615;</i>搜索</button>
					</div>
				</div>		
			</form>
			<div class="layui-menu">
				<span class="layui-r">共有数据：<strong>${page.total!}</strong> 条</span>
			</div>
			<div class="layui-form">
				<table class="layui-table">
					<thead>
						<tr>
							<th>序号</th>
							<th>日志类型</th>
							<th>操作用户</th>
							<th>用户手机号</th>
							<th>操作事件</th>
							<th>操作路径</th>
							<th>操作时间</th>
							<th>操作IP</th>
							<th>查看详情</th>
						</tr>
					</thead>
					<tbody>
						<#if page?? && page.rows??>
							<#list page.rows as item>
								<tr>
									<td>${item_index + 1 }</td>
									<td>
										<#if item.journalType??> 
											<#if item.journalType == 1 >操作日志</#if>
											<#if item.journalType == 2 >异常日志</#if>
										</#if>
									</td>
									<td>${item.userName! }</td>
									<td>${item.userMobile! }</td>
									<td>${item.operateName! }</td>	
									<td >${item.requestPath! }</td>	
									<td>${item.createTime?if_exists?string("yyyy-MM-dd HH:mm:ss")}</td>	
									<td >${item.ipAddr! }</td>
									<td>
										<@buttonTag name="详情" menuLink="/system/journal/todetail" onclick="todetail('${item.id!}')" class="layui-btn-small" />
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
			layui.use(['layer','form', 'laydate', 'laypage'], function() {
				var layer = layui.layer,
					form = layui.form(),
					laydate = layui.laydate,
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
			
			function todetail(id){
				layer.open({
					type: 2,
					area: ['100%', '100%'],
					title: '系统管理    > 日志管理  > 日志详情',
					content: '${ctx!}/system/journal/todetail?id='+id
				});
			}
			
		</script>
	</body>

</html>