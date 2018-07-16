<!DOCTYPE html>
<html>

	<head>
		<#include "../../common/head.ftl">
		<title>派息确认</title>
	
	</head>

	<body>
		<div class="layui-tab-content ">
		
		<form class="layui-form layui-form-pane">
			<fieldset class="layui-elem-field layui-field-title">
				<legend>系统操作日志</legend>
			</fieldset>
			<div class="layui-form-item">
				<label class="layui-form-label">日志类型</label>
				<div class="layui-input-inline">
					<#if journal.journalType?? && journal.journalType == 1 >
						<input type="text" value="操作日志" class="layui-input" readonly >
					<#else>
						<input type="text" value="异常日志" class="layui-input" readonly >
					</#if>
				</div>
				<label class="layui-form-label">操作事件</label>
				<div class="layui-input-inline">
					<input type="text" value="${journal.operateName!}" class="layui-input" readonly >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">操作用户</label>
				<div class="layui-input-inline">
					<input type="text" value="${journal.userName!}" class="layui-input" readonly >
				</div>
				<label class="layui-form-label">用户手机号</label>
				<div class="layui-input-inline">
					<input type="text" value="${journal.userMobile!}" class="layui-input" readonly >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">操作时间</label>
				<div class="layui-input-inline">
					<input type="text" value="${journal.createTime?if_exists?string("yyyy-MM-dd HH:mm:ss")}" class="layui-input" readonly >
				</div>
				<label class="layui-form-label">操作路径</label>
				<div class="layui-input-inline">
					<input type="text" value="${journal.requestPath!}" class="layui-input" readonly >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">操作IP</label>
				<div class="layui-input-inline">
					<input type="text" value="${journal.ipAddr!}" class="layui-input" readonly >
				</div>
			</div>
		
			<div class="layui-form-item">
				<label class="layui-form-label">操作参数</label>
				<div class="layui-input-block">
					<textarea class="layui-textarea" style="min-height: 160px;width: 88%;" readonly>${journal.requestParameter!}</textarea>
				</div>
			</div>
		   <div class="layui-form-item">
				<label class="layui-form-label">异常记录</label>
				<div class="layui-input-block">
					<textarea class="layui-textarea" style="min-height: 160px;width: 88%;" readonly>${journal.exceptionJournal!}</textarea>
				</div>
			</div>
			
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button type="button" class="layui-btn layui-btn-primary" onclick="layer_close();"><i class="layui-icon">&#x1006;</i>取消</button>
				</div>
			</div>
			
			</form>
		</div>

		<script>
		
		    layui.use(['layer','form'], function() {
				var layer = layui.layer,
					form = layui.form();

			});
			
		</script>
	</body>
</html>