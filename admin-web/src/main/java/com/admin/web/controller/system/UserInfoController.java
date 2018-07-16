package com.admin.web.controller.system;

import com.admin.client.constant.Constant;
import com.admin.client.constant.RoleInfoEnum;
import com.admin.client.model.system.RoleInfo;
import com.admin.client.model.system.UserInfo;
import com.admin.client.service.system.IRoleInfoService;
import com.admin.client.service.system.IUserInfoService;
import com.admin.client.result.BaseResult;
import com.admin.client.plugins.base.SearchCondition;
import com.admin.client.plugins.base.page.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 用户管理
 *
 * @author LJ
 * @date 2017年3月17日 上午11:08:18
 */
@Controller
@RequestMapping("/system/userinfo")
public class UserInfoController {

    @Autowired
    IUserInfoService iUserInfoService;

    @Autowired
    IRoleInfoService iRoleInfoService;

    /**
     * 修改密码页
     */
    @RequestMapping("/changepwdpage")
    public String changePasswordPage(HttpServletRequest request) {
        UserInfo user = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        request.setAttribute("user", user);
        return "system/user/changePwd";
    }

    /**
     * 修改密码
     */
    @RequestMapping("/changepwd")
    @ResponseBody
    public BaseResult changePassword(HttpServletRequest request, @RequestParam String oldpassword,
                                     @RequestParam String newpassword) {
        UserInfo user = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        if (!user.getUserPwd().equals(DigestUtils.md5DigestAsHex(oldpassword.getBytes()))) {
            return BaseResult.failed("fail", "原密码错误");
        }
        user.setUserPwd(DigestUtils.md5DigestAsHex(newpassword.getBytes()));
        iUserInfoService.updateBySelective(user);
        return new BaseResult("ok", "密码修改成功");
    }

    /**
     * 用户菜单
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Integer pageIndex, Integer pageSize, String realName) {
        pageIndex = pageIndex == null ? 1 : pageIndex;
        pageSize = pageSize == null ? Constant.PAGE_SIZE : pageSize;

        UserInfo loginer = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("realName", realName);

        if (!loginer.getRoleName().equals(RoleInfoEnum.ROLE_INFO_1.getKey())) {
            List<Integer> allIdList = iUserInfoService.queryAllSubTree(loginer.getId());
            map.put("userIdList", allIdList);
        }

        PageResult<UserInfo> page = iUserInfoService.queryPage(pageIndex, pageSize, map);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", page.getPageCount(pageSize));
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("realName", realName);
        return "system/user/userList";
    }

    /**
     * 添加用户页
     */
    @RequestMapping("/toadd")
    public String toadd(HttpServletRequest request) {
        UserInfo loginer = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        List<RoleInfo> roleList = iRoleInfoService.queryList(loginer.getRoleId());
        request.setAttribute("roleList", roleList);

        if (RoleInfoEnum.ROLE_INFO_1.getKey().equals(loginer.getRoleName())) {
            List<UserInfo> userList = iUserInfoService.getByCondition(new SearchCondition());
            request.setAttribute("userList", userList);
        } else {
            List<Integer> allIdList = iUserInfoService.queryAllSubTree(loginer.getId());
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userIdList", allIdList);
            List<UserInfo> userList = new ArrayList<UserInfo>();
            userList.add(loginer);
            List<UserInfo> list = iUserInfoService.queryList(map);
            if (list != null && list.size() > 0) {
                userList.addAll(list);
            }
            request.setAttribute("userList", userList);
        }

        return "system/user/userAdd";
    }

    /**
     * 添加用户
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(HttpServletRequest request, @RequestParam String userMobile, @RequestParam String realName,
                          @RequestParam Integer creator, @RequestParam String userPwd, @RequestParam Integer roleId) {

        if (StringUtils.isBlank(userMobile)) {
            return BaseResult.failed("fail", "用户手机号不能为空.");
        }
        if (StringUtils.isBlank(realName)) {
            return BaseResult.failed("fail", "用户姓名不能为空.");
        }
        if (StringUtils.isBlank(userPwd)) {
            return BaseResult.failed("fail", "用户密码不能为空.");
        }
        if (roleId == null) {
            return BaseResult.failed("fail", "角色不能为空.");
        }
        if (creator == null) {
            return BaseResult.failed("fail", "上级用户不能为空.");
        }

        if (iUserInfoService.getOneByField("userMobile", userMobile) != null) {
            return BaseResult.failed("fail", "该手机号已存在.");
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setRealName(realName);
        userInfo.setUserMobile(userMobile.trim());
        userInfo.setUserPwd(DigestUtils.md5DigestAsHex(userPwd.trim().getBytes()));
        userInfo.setRoleId(roleId);
        userInfo.setCreator(creator);
        userInfo.setCreateTime(new Date());
        iUserInfoService.save(userInfo);
        return BaseResult.sucMsg("添加用户成功");
    }

    /**
     * 修改用户页
     */
    @RequestMapping("/toedit")
    public String toedit(HttpServletRequest request, @RequestParam Integer id) {
        UserInfo loginer = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        List<RoleInfo> roleList = iRoleInfoService.queryList(loginer.getRoleId());
        request.setAttribute("roleList", roleList);

        if (RoleInfoEnum.ROLE_INFO_1.getKey().equals(loginer.getRoleName())) {
            List<UserInfo> userList = iUserInfoService.getByCondition(new SearchCondition());
            request.setAttribute("userList", userList);
        }

        UserInfo userInfo = iUserInfoService.getById(id);
        request.setAttribute("userInfo", userInfo);
        return "system/user/userEdit";
    }

    /**
     * 修改用户
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult edit(HttpServletRequest request, @RequestParam Integer userId, @RequestParam String userMobile,
                           @RequestParam String realName, String userPwd, @RequestParam Integer roleId, Integer creator) {
        if (userMobile == null || userMobile.trim().equals("")) {
            return BaseResult.failed("fail", "用户手机号不能为空.");
        }
        if (realName == null || realName.trim().equals("")) {
            return BaseResult.failed("fail", "用户姓名不能为空.");
        }

        UserInfo loginer = (UserInfo) request.getSession().getAttribute(Constant.SESSION_USER);
        if (roleId == null || roleId == 0) {
            roleId = loginer.getRoleId();
        }

        UserInfo userInfo = iUserInfoService.getById(userId);
        if (userInfo == null) {
            return BaseResult.failed("fail", "该用户已不存在.");
        }
        userInfo.setUserMobile(userMobile.trim());
        userInfo.setRealName(realName);
        if (userPwd != null && !userPwd.trim().equals("")) {
            userInfo.setUserPwd(DigestUtils.md5DigestAsHex(userPwd.trim().getBytes()));
        }
        if (creator != null) {
            userInfo.setCreator(creator);
        }

        userInfo.setRoleId(roleId);
        iUserInfoService.updateBySelective(userInfo);
        return new BaseResult("ok", "修改用户成功");
    }

    /**
     * 删除用户
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam Integer id) {
        iUserInfoService.deleteById(id);
        return new BaseResult("ok", "删除用户成功");
    }


}