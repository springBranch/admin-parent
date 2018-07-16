package com.admin.service.impl.system;

import com.admin.client.model.system.OperateJournal;
import com.admin.client.model.system.UserInfo;
import com.admin.client.plugins.base.BaseServiceImpl;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.service.system.IOperateJournalService;
import com.admin.client.service.system.IUserInfoService;
import com.admin.client.utils.PageUtil;
import com.admin.service.mapper.system.OperateJournalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperateJournalServiceImpl extends BaseServiceImpl<OperateJournal> implements IOperateJournalService {

    @Autowired
    private OperateJournalMapper operateJournalMapper;
    @Autowired
    private IUserInfoService userInfoService;

    @Override
    public void addOperateJournal(Integer userId, String operateName, String requestPath,
                                  String requestParameter, String ipAddr, Integer journalType, Exception e) {
        OperateJournal journal = new OperateJournal();
        if (userId != null) {
            UserInfo userInfo = userInfoService.getById(userId);
            if (userInfo != null) {
                journal.setUserId(userInfo.getId());
                journal.setUserName(userInfo.getRealName());
                journal.setUserMobile(userInfo.getUserMobile());
            }
        }
        journal.setOperateName(operateName);
        journal.setRequestPath(requestPath);
        journal.setRequestParameter(requestParameter);
        journal.setIpAddr(ipAddr);
        journal.setJournalType(journalType);
        if (e != null) {
            StringBuilder exStr = new StringBuilder();
            exStr.append(e.toString()).append("\r\n");
            StackTraceElement[] trace = e.getStackTrace();
            for (StackTraceElement s : trace) {
                exStr.append("\tat ").append(s).append("\r\n");
            }
            journal.setExceptionJournal(exStr.toString());
        }
        this.save(journal);
    }

    @Override
    public PageResult<OperateJournal> queryPage(Map<String, Object> map, Integer pageIndex, Integer pageSize) {
        //查询总数量
        int total = operateJournalMapper.queryCount(map);
        if (pageIndex != null && pageSize != null) {
            int start = PageUtil.calcStart(pageIndex, pageSize, total);
            map.put("start", start);
            map.put("length", pageSize);
        }
        List<OperateJournal> list = operateJournalMapper.query(map);
        PageResult<OperateJournal> result = new PageResult<OperateJournal>();
        result.setRows(list);
        result.setTotal(new Long(total));
        return result;
    }

}
