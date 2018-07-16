<!DOCTYPE html>

<html>

	<head>
		<title>${systemName!}</title>
		<#include "../common/head.ftl">
		<@css_version paths=["css/global.css","css/index.css"]/>
		<@js_version paths=["js/index.js"]/>
	</head>

	<body>
	<#assign roleName = Session.session_user.roleName!>
	<#assign realName = Session.session_user.realName!>
	<#assign userMobile = Session.session_user.userMobile!>
		<div class="layui-layout layui-layout-admin" style="border-bottom: solid 5px #1aa094;">
			<div class="layui-header header header-demo">
				<div class="layui-main">
					<div class="admin-login-box">
						<a class="logo" style="left: 0;" href="${ctx}/index">
							<span style="font-size: 22px;">${systemName!}</span>
						</a>
						<div class="admin-side-toggle">
							<i class="layui-icon">&#xe632;</i>
						</div>
						<div class="admin-side-full">
							<i class="layui-icon">&#xe630;</i>
						</div>
					</div>
					<ul class="layui-nav admin-header-item">
						<li class="layui-nav-item">
							${roleName!}
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;" class="admin-header-user">
								<img src="${ctx}/images/0.jpg" />
								<span id="userName">${realName!}</span>
								<input type="hidden" id="userMobile" value="${userMobile!}">
							</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:;" onclick="contactAdmin();"><i class="layui-icon">&#xe611;</i>联系管理员</a>
								</dd>
								<dd>
									<a href="javascript:;" onclick="changePwd();"><i class="layui-icon">&#xe620;</i>修改密码</a>
								</dd>
								<dd id="lock">
									<a href="javascript:;"><i class="layui-icon">&#xe64f;</i>锁屏	</a>
								</dd>
								<dd>
									<a href="javascript:;" id="logout"><i class="layui-icon">&#xe640;</i>注销</a>
								</dd>
							</dl>
						</li>
					</ul>
				</div>
			</div>
			<div class="layui-side" id="admin-side">
				<div class="layui-side-scroll list" id="admin-navbar-side">

					<!-- 左侧菜单 -->
					<#if menuList?? >
						<ul>
							<#list menuList as oneItem>
							<li>
								<a href="#" class="inactive active">${oneItem.name!}</a>
								<#if oneItem.children?? >
									<ul style="display: none" >
										<#list oneItem.children as twoItem>
										<li>
											<a href="#" id="${twoItem.id!}" <#if twoItem.link?? && twoItem.link!="">data-url="${ctx}${twoItem.link!}" class="datacss" <#else> class="inactive active"</#if> >${twoItem.name!}</a>
											<#if twoItem.children?? >
												<ul>
													<#list twoItem.children as threeItem>
														<li><a href="#" id="${threeItem.id!}" data-url="${ctx}${threeItem.link!}" class="datacss">${threeItem.name!}</a></li>
													</#list>
												</ul>
											</#if>
										</li>
										</#list>
									</ul>
								</#if>
							</li>
							</#list>
						</ul>
					</#if>

				</div>
			</div>
			<div class="layui-body" style="bottom: 0;border-left: solid 5px #1AA094;" id="admin-body">
				<div class="layui-tab admin-nav-card layui-tab-brief" lay-filter="admin-tab">
					<ul class="layui-tab-title">
						<li class="layui-this" onclick="openMenu('index')">
							<i class="layui-icon">&#xe609;</i>
							<cite id="tab_index">主页</cite>
						</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show">
							<iframe src="${ctx!}/main"></iframe>
						</div>
					</div>
				</div>
			</div>
		</div>

		<!--锁屏模板 start-->
		<script type="text/template" id="lock-temp">
			<div class="admin-header-lock" id="lock-box">
				<div class="admin-header-lock-img">
					<img src="${ctx}/images/0.jpg" />
				</div>
				<div class="admin-header-lock-name" id="lockUserName"></div>
				<input type="text" class="admin-header-lock-input" value="输入密码解锁.." name="lockPwd" id="lockPwd" />
				<button class="layui-btn layui-btn-small" id="unlock">解锁</button>
			</div>
		</script>
		<!--锁屏模板 end -->

		<script>
			layui.use('layer',function() {
				var $ = layui.jquery,
					layer = layui.layer;

				$("#logout").click(function(){
				   $.post("${ctx}/logout",function(data){
				        if (data.status){
				        	window.location.href = "${ctx}/loginpage";
				        }
				    }, "json");
				});

			});

			//联系管理员
			function contactAdmin(){
				layer.alert('管理员      QQ : ${systemQQ!}', {
					 title:'联系方式'
				    ,skin: 'layui-layer-molv'
				    ,closeBtn: 0
				    ,anim: 4 //动画类型
				  });
			}
			//修改密码
			function changePwd(){
				layer.open({
					type: 2,
					area: ['50%', '70%'],
					title: '系统管理    > 修改密码',
					content: '${ctx!}/system/userinfo/changepwdpage'
				});
			}
			</script>
	</body>

</html>