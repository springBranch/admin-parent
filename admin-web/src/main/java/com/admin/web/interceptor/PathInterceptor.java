package com.admin.web.interceptor;

import com.admin.client.result.BaseResult;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author song.j
 * @create 2018-12-19 16:16:00
 **/
public class PathInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(RequestMethod.OPTIONS.toString())){
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            //response.setHeader("Access-Control-Allow-Methods","*");
            //response.setHeader("Access-Control-Allow-Headers","content-type, Accept, Authorization, X-Requested-With, Origin, Accept");
            //
            //ServletOutputStream outputStream = response.getOutputStream();
            //outputStream.write(BaseResult.success().toString().getBytes());
            //outputStream.flush();
            return true;
        }
        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        //response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods","GET, POST, PATCH, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers","content-type, Accept, Authorization, X-Requested-With, Origin, Accept");
        //super.afterCompletion(request,response,handler,ex);
    }
}
