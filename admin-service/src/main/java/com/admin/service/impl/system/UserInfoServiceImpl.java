package com.admin.service.impl.system;

import com.admin.client.model.system.UserInfo;
import com.admin.client.plugins.base.BaseServiceImpl;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.service.system.IUserInfoService;
import com.admin.client.utils.PageUtil;
import com.admin.service.mapper.system.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo> implements IUserInfoService {

	@Autowired
	private UserInfoMapper userInfoMapper;
	
	@Override
	public UserInfo queryForLogin(String userMobile, String userPwd){
        userPwd = DigestUtils.md5DigestAsHex(userPwd.trim().getBytes());
		return userInfoMapper.queryForLogin(userMobile, userPwd);
	}

	@Override
	public List<UserInfo> queryByRoleName(String roleName) {
		return userInfoMapper.queryByRoleName(roleName);
	}
	
	@Override
	public List<UserInfo> queryByRoleNames(List<String> roleNames) {
		return userInfoMapper.queryByRoleNames(roleNames);
	}

	@Override
	public List<UserInfo> queryByRoleId(Integer roleId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setRoleId(roleId);
		return userInfoMapper.select(userInfo);
	}
	
	@Override
	public List<UserInfo> queryByCreator(Integer creatorId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setCreator(creatorId);
		return userInfoMapper.select(userInfo);
	}

	@Override
	public List<UserInfo> queryByMobile(String mobile) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserMobile(mobile);
		return userInfoMapper.select(userInfo);
	}

	@Override
	public List<Integer> queryAllSubTree(Integer pointId) {
		List<Integer> allIdList = new ArrayList<Integer>();
		allIdList.add(pointId);
		loopTree(allIdList, allIdList);
		return allIdList;
	}

	@Override
	public PageResult<UserInfo> queryPage(Integer pageIndex, Integer pageSize,
                                          Map<String, Object> map) {
		//查询总数量
		int total = userInfoMapper.queryCount(map);
		if(pageIndex!=null && pageSize!=null){
			int start = PageUtil.calcStart(pageIndex, pageSize, total);
			map.put("start", start);
			map.put("length", pageSize);
		}
		List<UserInfo> list = userInfoMapper.query(map);
		PageResult<UserInfo> result = new PageResult<UserInfo>();
		result.setRows(list);
		result.setTotal(new Long(total));
		return result;
	}
	
	@Override
	public List<UserInfo> queryList(Map<String, Object> map){
		return userInfoMapper.queryList(map);
	}

	/**
	 * 遍历，将所有子孙id放入容器集合中
	 * @param collect 容器集合
	 * @param parentIdList 父ids
	 */
	private void loopTree(List<Integer> collect, List<Integer> parentIdList){
		List<Integer> subTree = userInfoMapper.queryByParentIds(parentIdList);
		if(!CollectionUtils.isEmpty(subTree)){
			collect.addAll(subTree);
			loopTree(collect, subTree);
			
		}
	}
}