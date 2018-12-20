package com.admin.web.controller.system;

import com.admin.client.constant.Constant;
import com.admin.client.model.system.AdminMenu;
import com.admin.client.model.system.RoleInfo;
import com.admin.client.model.system.UserInfo;
import com.admin.client.result.BaseResult;
import com.admin.client.service.system.IAdminMenuService;
import com.admin.client.service.system.IDictionaryService;
import com.admin.client.service.system.IRoleInfoService;
import com.admin.client.service.system.IUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author songj
 * @ClassName ${NAME}
 * @date 2017年3月17日 上午11:07:48
 */
@Controller
public class HomeController {

    private static Logger logger = LogManager.getLogger(HomeController.class);

    @Autowired
    IUserInfoService iUserInfoService;

    @Autowired
    IRoleInfoService iRoleInfoService;

    @Autowired
    IAdminMenuService iAdminMenuService;

    @Autowired
    private IDictionaryService iDictionaryService;


    /**
     * 登录页
     */
    @RequestMapping("/loginpage")
    public String loginPage() {
        return "system/login";
    }

    /**
     * 登录页
     */
    @RequestMapping("/")
    public String login() {
        return "system/login";
    }

    /**
     * 登录
     */
    @RequestMapping(value = "/login",params = {"verifyCode"})
    @ResponseBody
    public BaseResult login(HttpServletRequest request, @RequestParam String mobile, @RequestParam String password,
                            @RequestParam String verifyCode) {
        try {
            String code = (String) request.getSession().getAttribute("IMAGE_VERIFY_CODE");
            if (StringUtils.isBlank(code)) {
                return BaseResult.failed("图片验证码失效");
            }
            if (!code.equalsIgnoreCase(verifyCode)) {
                return BaseResult.failed("图片验证码错误");
            }
            //密码加密
            UserInfo user = iUserInfoService.queryForLogin(mobile.trim(), password.trim());
            if (user == null) {
                return BaseResult.failed("用户名或密码错误");
            }
            user.setLoginTime(new Date());
            iUserInfoService.updateBySelective(user);
            request.getSession().setAttribute(Constant.SESSION_USER, user);
            RoleInfo role = iRoleInfoService.getById(user.getRoleId());
            request.getSession().setAttribute("USER_ROLE", role);
            return BaseResult.sucMsg("登陆成功");
        } catch (Exception e) {
            logger.error("用户登陆时出现异常", e);
            return BaseResult.failed("登陆失败");
        }
    }

    /**
     * 登录
     */
    @RequestMapping("/login")
    @ResponseBody
    public BaseResult login(HttpServletRequest request,HttpServletResponse response, @RequestParam String mobile, @RequestParam String password) {
        try {
            //密码加密
            UserInfo user = iUserInfoService.queryForLogin(mobile.trim(), password.trim());
            if (user == null) {
                return BaseResult.failed("用户名或密码错误");
            }
            user.setLoginTime(new Date());
            iUserInfoService.updateBySelective(user);
            RoleInfo role = iRoleInfoService.getById(user.getRoleId());
            Map result = new HashMap<String,Object>(){{
                put("user",user);
                put("userRole",role);
            }};

            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            response.setHeader("Access-Control-Allow-Credentials","true");
            response.setHeader("Cache-Control", "no-cache");
            return BaseResult.success(result);
        } catch (Exception e) {
            logger.error("用户登陆时出现异常", e);
            return BaseResult.failed("登陆失败");
        }
    }


    /**
     * 首页
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        UserInfo user = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        RoleInfo role = iRoleInfoService.getById(user.getRoleId());

        String[] menuArray = role.getMenuIds().split(",");
        List<AdminMenu> menuList = iAdminMenuService.queryPart(menuArray);

        List<AdminMenu> newMenuList = new LinkedList<AdminMenu>();
        for (AdminMenu bigItem : menuList) {
            if (bigItem.getParentId() == 0) {
                newMenuList.add(bigItem);
            }
        }
        treeTidy(newMenuList, menuList);
        request.setAttribute("systemQQ", iDictionaryService.queryValue("SYSTEM_QQ"));
        request.setAttribute("menuList", newMenuList);
        return "system/index";
    }


    /**
     * 递归整理菜单的父子关系
     *
     * @param newMenuList 新的集合
     * @param menuList    查询出来的集合
     */
    public void treeTidy(List<AdminMenu> newMenuList, List<AdminMenu> menuList) {
        for (AdminMenu bigItem : newMenuList) {
            for (AdminMenu smallItem : menuList) {
                if (bigItem.getId().equals(smallItem.getParentId())) {
                    if (bigItem.getChildren() == null) {
                        bigItem.setChildren(new LinkedList());
                    }
                    bigItem.getChildren().add(smallItem);
                }
            }
            //三级及其以上菜单的话，需要下面的代码 禁用三级菜单
            //if (bigItem.getChildren() != null) {
            //    treeTidy(bigItem.getChildren(), menuList);
            //}
        }
    }


    /**
     * 解锁
     */
    @RequestMapping("/unlock")
    @ResponseBody
    public BaseResult unlock(HttpServletRequest request, @RequestParam String mobile, @RequestParam String password) {
        try {
            UserInfo user = iUserInfoService.queryForLogin(mobile.trim(), password.trim());
            if (user == null) {
                return BaseResult.failed("fail", "用户名或密码错误");
            }
            user.setLoginTime(new Date());
            iUserInfoService.updateBySelective(user);
            request.getSession().setAttribute(Constant.SESSION_USER, user);
            RoleInfo role = iRoleInfoService.getById(user.getRoleId());
            request.getSession().setAttribute("USER_ROLE", role);
            return new BaseResult("ok", "登陆成功");
        } catch (Exception e) {
            logger.error("用户登陆时出现异常", e);
            return BaseResult.failed("fail", "登陆失败");
        }
    }

    /**
     * 退出
     */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public BaseResult logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute(Constant.SESSION_USER);
        request.getSession().invalidate();
        return BaseResult.success();
    }

    /**
     * 权限拒绝页面
     */
    @RequestMapping("/denied")
    public String denied() {
        return "system/denied";
    }

    /**
     * 主页
     */
    @RequestMapping("/main")
    public String mainPage(HttpServletRequest request) {

        return "system/main";
    }
}