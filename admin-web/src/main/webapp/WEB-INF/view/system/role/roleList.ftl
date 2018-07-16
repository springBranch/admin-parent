<!DOCTYPE html>
<html>
<head>
		<#include "../../common/head.ftl">
    <title>角色管理</title>
</head>

<body>
<div class="layui-tab-content ">
    <a class="layui-r" href="javascript:location.replace(location.href);" title="刷新"><i class="layui-icon" style="font-size: 30px; color: #1E9FFF;">&#x1002;</i></a>
    <fieldset class="layui-elem-field layui-field-title">
        <legend>系统管理 > 角色列表</legend>
    </fieldset>
    <form class="layui-form layui-form-pane" action="${ctx!}/system/roleinfo/list" method="post" id="myForm">
        <input type="hidden" name="pageIndex" id="pageIndex" value="${pageIndex }"/>
        <input type="hidden" name="pageSize" id="pageSize" value="${pageSize }"/>
    </form>

    <div class="layui-menu">
				<span class="layui-l">
					<@buttonTag name="添加角色" menuLink="/system/roleinfo/toadd" onclick="role_add()" icon="&#xe608;" />
                </span>
        <span class="layui-r">共有数据：<strong>${page.total!}</strong> 条</span>
    </div>
    <div class="layui-form">
        <table class="layui-table">
            <thead>
            <tr>
                <th>序号</th>
                <th>角色名</th>
                <th>创建时间</th>
                <th>上级角色</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
						<#if page?? && page.rows??>
							<#list page.rows as item>
								<tr>
                                    <td>${item_index + 1 }</td>
                                    <td>${item.roleName!}</td>
                                    <td>${item.createTime?string("yyyy-MM-dd")}</td>
                                    <td>${item.parentName!}</td>
                                    <td>${item.description!}</td>
                                    <td>
										<@buttonTag name="修改" menuLink="/system/roleinfo/toedit" onclick="role_edit('${item.id!}')" class="layui-btn-small layui-btn-warm" />
										<@buttonTag name="删除" menuLink="/system/roleinfo/delete" onclick="role_del('${item.id!}')" class="layui-btn-small layui-btn-danger" />
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
    layui.use(['layer', 'form', 'laypage'], function () {
        var layer = layui.layer,
                form = layui.form(),
                laypage = layui.laypage;

        //分页
        laypage({
            cont: 'page',
            pages: ${pageCount!},
            curr: ${pageIndex!},
            jump: function (obj, first) {
                if (!first) {
                    layer.load(0, {shade: false});
                    $("#pageIndex").val(obj.curr)
                    $("#myForm").submit();
                }
            }
        });

    });

    //添加角色
    function role_add() {
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            title: '系统管理    > 添加角色',
            content: '${ctx!}/system/roleinfo/toadd'
        });
    }

    //修改角色
    function role_edit(id) {
        layer.open({
            type: 2,
            area: ['100%', '100%'],
            title: '系统管理    > 修改角色',
            content: '${ctx!}/system/roleinfo/toedit?id=' + id
        });
    }

    //删除角色
    function role_del(id) {
        layer.confirm('角色删除须谨慎，确认要删除吗？', function (index) {
            var url = '${ctx!}/system/roleinfo/delete?id=' + id;
            $.post(url, function (result) {
                if (result.status) {
                    layer.msg(result.message, {icon: 1, time: 1000}, function () {
                        location.reload(); //刷新列表
                    });
                } else {
                    layer.msg(result.message, {icon: 1, time: 2000});
                }
            }, "json");
        });
    }
</script>
</body>

</html>