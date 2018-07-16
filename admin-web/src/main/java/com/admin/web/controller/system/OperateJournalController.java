package com.admin.web.controller.system;

import com.admin.client.constant.Constant;
import com.admin.client.model.system.OperateJournal;
import com.admin.client.service.system.IOperateJournalService;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.plugins.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统操作日志Controller
 *
 * @author LJ
 * @date 2017年3月31日 下午12:05:46
 */
@Controller
@RequestMapping("/system/journal")
public class OperateJournalController {

    @Autowired
    IOperateJournalService iOperateJournalService;

    /**
     * 菜单列表
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, Integer pageIndex, Integer pageSize, String strDate,
                       String endDate, Integer journalType, String userName, String operateName, String requestPath) {
        pageIndex = (pageIndex == null || pageIndex == 0) ? 1 : pageIndex;
        pageSize = (pageSize == null || pageSize == 0) ? Constant.PAGE_SIZE : pageSize;
        strDate = StringUtils.isNotBlank(strDate) ? strDate : DateUtil.getDate(new Date());

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("journalType", journalType);
        map.put("userName", userName);
        map.put("operateName", operateName);
        map.put("requestPath", requestPath);
        map.put("strDate", strDate);
        map.put("endDate", endDate);

        PageResult<OperateJournal> page = iOperateJournalService.queryPage(map, pageIndex, pageSize);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", page.getPageCount(pageSize));
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("journalType", journalType);
        request.setAttribute("userName", userName);
        request.setAttribute("operateName", operateName);
        request.setAttribute("requestPath", requestPath);
        request.setAttribute("strDate", strDate);
        request.setAttribute("endDate", endDate);

        return "system/journal/journalList";
    }

    /**
     * 日志详情
     */
    @RequestMapping("/todetail")
    public String todetail(HttpServletRequest request, @RequestParam String id) {
        OperateJournal journal = iOperateJournalService.getById(id);
        request.setAttribute("journal", journal);
        return "system/journal/journalDetail";
    }
}
