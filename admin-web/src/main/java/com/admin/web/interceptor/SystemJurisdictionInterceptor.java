package com.admin.web.interceptor;

import com.admin.client.constant.Constant;
import com.admin.client.model.system.AdminMenu;
import com.admin.client.model.system.RoleInfo;
import com.admin.client.model.system.UserInfo;
import com.admin.client.service.system.IAdminMenuService;
import com.admin.client.service.system.IOperateJournalService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 权限校验与异常日志拦截器
 *
 * @author LJ
 * @date 2017年3月17日 上午11:09:05
 */
public class SystemJurisdictionInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LogManager.getLogger(SystemJurisdictionInterceptor.class);

    @Autowired
    IAdminMenuService iAdminMenuService;

    @Autowired
    IOperateJournalService iOperateJournalService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        UserInfo loginUser = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/loginpage");
            return false;
        }
        String url = request.getServletPath();
        logger.info(">>>>>    请求接口：{}", url);
        logger.info(">>>>>    请求参数：{}", this.getParameterMap(request).toString());
        AdminMenu menu = iAdminMenuService.getOneByField("link", url);
        if (menu != null) {
            logger.info(">>>>>    请求接口名：{}", menu.getName());
            RoleInfo roleInfo = (RoleInfo) request.getSession().getAttribute("USER_ROLE");
            if (roleInfo != null && StringUtils.isNotBlank(roleInfo.getMenuIds())) {
                String[] split = roleInfo.getMenuIds().split(",");
                for (String menuId : split) {
                    if (menuId.equals(String.valueOf(menu.getId()))) {
                        return true;
                    }
                }
            }
        } else {
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/denied");
        return false;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        UserInfo user = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        AdminMenu menu = iAdminMenuService.getOneByField("link", request.getServletPath());

        String operateName = menu != null && menu.getName() != null ? menu.getName() : "WEB调用异常";
        String requestPath = request.getServletPath();
        String requestParameter = this.getParameterMap(request).toString();
        String ipAddr = request.getRemoteAddr();

        if (ex != null) {
            logger.error(">>>>>>发生异常-请求参数:{}", requestParameter, ex);
            iOperateJournalService.addOperateJournal(user.getId(), operateName, requestPath, requestParameter, ipAddr, 2, ex);
        } else {
            logger.info("用户:{},操作名称:{},请求路径:{},请求参数:{},ip地址:{},日志类型:1", user.getId(), operateName, requestPath, requestParameter, ipAddr, ex);
            // 关闭业务日志
            //if (menu != null && menu.getIsJournal().equals(1)) {
            //    RoleInfo roleInfo = (RoleInfo) request.getSession().getAttribute("USER_ROLE");
            //    if (roleInfo != null && StringUtils.isNotBlank(roleInfo.getMenuIds())) {
            //        String[] split = roleInfo.getMenuIds().split(",");
            //        for (String menuId : split) {
            //            if (menuId.equals(String.valueOf(menu.getId()))) {
            //                iOperateJournalService.addOperateJournal(user.getId(), operateName, requestPath, requestParameter, ipAddr, 1, ex);
            //                break;
            //            }
            //        }
            //    }
            //}
        }
    }


    /**
     * 获取请求参数
     * getParameterMap
     *
     * @param request
     * @return
     */
    private Map<String, String> getParameterMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Map<String, String[]> paraMap = request.getParameterMap();
        for (String paraName : paraMap.keySet()) {
            map.put(paraName, Arrays.toString(paraMap.get(paraName)));
        }
        return map;
    }
}
