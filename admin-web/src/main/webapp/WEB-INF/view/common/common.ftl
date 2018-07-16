<#assign ctx = rca.contextPath />

<#assign jsVersion = "1.0" >
<#assign cssVersion = "1.0" >

<#macro js_version paths>
	<#list paths as path>
		<script src="${ctx}/${path}?version=${jsVersion}"></script>
	</#list>
</#macro>

<#macro css_version paths>
	<#list paths as path>
		<link rel="stylesheet" href="${ctx}/${path}?version=${cssVersion}">
	</#list>
</#macro>

<#--
 * 从路径中获取文件名
 * ${getFileName(fileUrl)}
-->
<#function getFileName fileUrl >
    <#if fileUrl??>
		<#list fileUrl?split("/") as fname>
			<#if !fname_has_next>
			 	<#return fname>
			 </#if>
		</#list>
	</#if>
</#function>