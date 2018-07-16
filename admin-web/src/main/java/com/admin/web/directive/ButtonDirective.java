package com.admin.web.directive;

import com.admin.client.constant.Constant;
import com.admin.client.model.system.AdminMenu;
import com.admin.client.model.system.RoleInfo;
import com.admin.client.model.system.UserInfo;
import com.admin.client.service.system.IAdminMenuService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 用户权限按钮
 *
 * @author LJ
 * @date 2017年3月29日 下午1:18:13
 */
public class ButtonDirective implements TemplateDirectiveModel {

    @Autowired
    IAdminMenuService iAdminMenuService;


    @Override
    @SuppressWarnings("rawtypes")
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        UserInfo loginer = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        if (loginer != null) {
            String menuLink = params.get("menuLink").toString();
            if (StringUtils.isNotBlank(menuLink)) {
				AdminMenu menu = iAdminMenuService.getOneByField("link", menuLink);
                if (menu != null) {
                    RoleInfo roleInfo = (RoleInfo) request.getSession().getAttribute("USER_ROLE");
                    if (roleInfo != null && StringUtils.isNotBlank(roleInfo.getMenuIds())) {
                        String[] split = roleInfo.getMenuIds().split(",");
                        for (String menuId : split) {
                            if (menuId.equals(String.valueOf(menu.getId()))) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("<button type=\"button\" ");
                                if (params.get("layFilter") != null) {
                                    sb.append("lay-filter=\"");
                                    sb.append(params.get("layFilter").toString());
                                    sb.append("\" lay-submit ");
                                }
                                sb.append("class=\"layui-btn ");
                                if (params.get("class") != null) {
                                    sb.append(params.get("class").toString());
                                }
                                sb.append("\" ");

                                if (params.get("onclick") != null) {
                                    sb.append("onclick=\"");
                                    sb.append(params.get("onclick").toString());
                                    sb.append("\" ");
                                }
                                sb.append(">");

                                sb.append("<i class=\"layui-icon\">");
                                if (params.get("icon") != null) {
                                    sb.append(params.get("icon").toString());
                                }
                                sb.append("</i>");
                                if (params.get("name") != null) {
                                    sb.append(params.get("name").toString());
                                } else {
                                    sb.append(menu.getName());
                                }
                                sb.append("</button>");
                                env.getOut().write(sb.toString());
                            }
                        }
                    }
                }
            }
        }

    }
}
