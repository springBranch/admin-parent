package com.admin.web.servlet;

import com.admin.client.utils.ImageVerifyCodeUtils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 图片验证码
 *
 * @author LJ
 */
public class AuthImageServlet extends HttpServlet implements Servlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        //生成随机字串  
        String verifyCode = ImageVerifyCodeUtils.generateVerifyCode(4);
        //存入会话session  
        HttpSession session = request.getSession();
        session.setAttribute("IMAGE_VERIFY_CODE", verifyCode.toLowerCase());
        //生成图片  
        int w = 95, h = 38;
        ImageVerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

    }
}
