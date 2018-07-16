
layui.use(['layer', 'form'], function() {
	var layer = layui.layer,
		device = layui.device()
		form = layui.form();
		
	// 浏览器兼容检查
    if (device.ie && device.ie < 10) {
        layer.alert('您当前使用的是古老的 IE' + device.ie + '内核浏览器 !</br> 建议使用谷歌内核浏览器 !</br>或将当前浏览器切换为极速模式 !', {icon:2});
    }

	form.on('submit(login)',function(data){
		
		 $("#myForm").ajaxSubmit({
	            type: 'post', 
	            url: 'login',
	            dataType: "json",
	            success: function (data) {
                    if (data.status) {
	            		layer.msg("登录成功，正在跳转首页。。。", { icon: 1, time: 1000 }, function(){
			            	location.href = "index";			
	            		});
	        		}else {
	        			layer.msg(data.message, {icon:2,time:1000});
	        		}
	            },
	            error: function () {
	            	layer.msg("发送新增请求失败", {icon:5,time:1000});
	            }
	        });
	        
		return false;
	});
	
	
	$(window).on('resize',function(){
        var w = $(window).width();
        var h = $(window).height();
        $('.larry-canvas').width(w).height(h);
    }).resize();
	
	$("#canvas").jParticle({
        background: "#141414",
        color: "#E5E5E5"
    });
});