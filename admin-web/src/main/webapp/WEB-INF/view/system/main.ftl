<!DOCTYPE html>
<html>

<head>
    <title>${system_name!}</title>
		<#include "../common/head.ftl">
		<@css_version paths=["css/main.css"]/>
</head>

<body>
<#--<div class="row" id="infoSwitch">-->
<#--<a class="layui-r" href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#x1002;</i></a>-->
<#--<fieldset class="layui-elem-field layui-field-title">-->
<#--<legend style="text-align: center;font-size: 30px;">${systemName!}</legend>-->
<#--</fieldset>-->
<#--</div>-->
<div class="layui-tab-content ">
    欢迎进入${systemName!}
</div>

</body>
</html>