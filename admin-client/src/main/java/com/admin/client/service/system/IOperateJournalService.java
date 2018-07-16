package com.admin.client.service.system;

import com.admin.client.model.system.OperateJournal;
import com.admin.client.plugins.base.BaseService;
import com.admin.client.plugins.base.page.PageResult;

import java.util.Map;

/**
 * 系统操作日志Service
 *
 * @author LJ
 * @date 2017年3月31日 上午11:51:29
 */
public interface IOperateJournalService extends BaseService<OperateJournal> {

    /**
     * 添加日志记录
     * addExceptionJournaladdExceptionJournal
     *
     * @param userId           用户ID
     * @param operateName      操作名称
     * @param requestPath      请求路径
     * @param requestParameter 请求参数
     * @param ipAddr           IP地址
     * @param journalType      日志类型：1操作日志,2异常日志
     * @param e                异常
     */
    void addOperateJournal(Integer userId, String operateName, String requestPath,
                           String requestParameter, String ipAddr, Integer journalType, Exception e);

    /**
     * 分页查询系统操作日志列表
     *
     * @param pageIndex
     * @param pageSize
     * @param map       (属性条件)
     * @return PageResult<OperateJournal> 系统操作日志列表
     */
    PageResult<OperateJournal> queryPage(Map<String, Object> map, Integer pageIndex, Integer pageSize);

}
