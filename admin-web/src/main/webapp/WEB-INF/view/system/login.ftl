<!DOCTYPE HTML>

<html>

	<head>
		<title>${systemName!}</title>
		<#include "../common/head.ftl">
		<@css_version paths=["css/login.css"]/>
        <#--太影响性能了 cpu过高-->
		<#--<@js_version paths=["js/login.js","js/jparticle.js"]/>	-->
		<@js_version paths=["js/login.js"]/>
	</head>

	<body class="beg-login-bg">
		<div class="larry-canvas" id="canvas"></div>
		<div class="beg-login-box">
			<header>
				<h1>${systemName!}</h1>
			</header>
			<div class="beg-login-main">
				<form action="" class="layui-form" method="post" id="myForm">
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe612;</i>
                    </label>
						<input type="text" name="mobile" lay-verify="phone" value="13262872357" autocomplete="off" placeholder="请输入手机号" class="layui-input" maxlength="11">
					</div>
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe642;</i>
                    </label>
						<input type="password" name="password" lay-verify="required" value="13262872357" autocomplete="off" placeholder="请输入密码" class="layui-input">
					</div>
					<div class="layui-form-item">
						<label class="beg-login-icon">
                        <i class="layui-icon">&#xe60d;</i>
                    </label>
						<input type="text" name="verifyCode"  lay-verify="required" autocomplete="off" value="1234" placeholder="请输入图片验证码" class="layui-input" maxlength="4">
					</div>
					<div class="layui-form-item">
						<div class="beg-pull-left beg-login-remember" id="authImage">
							<img src="${ctx}/authimage" onclick="javascript:this.src='${ctx}/authimage?r='+Math.random();" title="点击刷新" class="beg-login-code-img" >
						</div>
						<div class="beg-pull-right">
							<button class="layui-btn layui-btn-primary" lay-submit lay-filter="login">
                            	<i class="layui-icon">&#xe650;</i> 登录
                        	</button>
						</div>
						<div class="beg-clear"></div>
					</div>
					<div class="layui-form-item" style="text-align: center;">
						<#--<span>Copyright @ 管理公司</span>-->
					</div>
				</form>
			</div>
		</div>
	</body>

</html>