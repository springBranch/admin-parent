package com.admin.web.controller.system;


import com.admin.client.constant.Constant;
import com.admin.client.model.system.Dictionary;
import com.admin.client.plugins.base.page.PageResult;
import com.admin.client.result.BaseResult;
import com.admin.client.service.system.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据字典管理
 *
 * @author LJ
 * @date 2017年3月17日 上午11:06:56
 */
@Controller
@RequestMapping("/system/dictionary")
public class DictionaryController {

    @Autowired
    private IDictionaryService iDictionaryService;

    /**
     * 数据字典列表
     */
    @RequestMapping("/list")
    public String list(HttpServletRequest request, String dictKey, String dictKeyName, Integer pageIndex, Integer pageSize) {
        pageIndex = (pageIndex == null || pageIndex == 0) ? 1 : pageIndex;
        pageSize = (pageSize == null || pageSize == 0) ? Constant.PAGE_SIZE : pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dictKey", dictKey);
        map.put("dictKeyName", dictKeyName);

        PageResult<Dictionary> page = iDictionaryService.queryPage(map, pageIndex, pageSize);
        request.setAttribute("dictKey", dictKey);
        request.setAttribute("dictKeyName", dictKeyName);
        request.setAttribute("page", page);
        request.setAttribute("pageCount", page.getPageCount(pageSize));
        request.setAttribute("pageIndex", pageIndex);
        request.setAttribute("pageSize", pageSize);
        return "system/dict/dictList";
    }

    /**
     * 新增数据字典页面
     */
    @RequestMapping("/toadd")
    public String toadd(HttpServletRequest request) {
        return "system/dict/dictAdd";
    }

    /**
     * 新增数据字典
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@ModelAttribute Dictionary dictionary) {
        Dictionary dict = iDictionaryService.queryObject(dictionary.getDictKey());
        if (dict == null) {
            iDictionaryService.save(dictionary);
            return new BaseResult("ok", "添加成功");
        }
        return BaseResult.failed("数据值重复");
    }

    /**
     * 修改数据字典页
     */
    @RequestMapping("/toedit")
    public String toedit(HttpServletRequest request, @RequestParam String id) {
        Dictionary dictionary = iDictionaryService.getById(id);
        request.setAttribute("dictionary", dictionary);
        return "system/dict/dictEdit";
    }

    /**
     * 修改数据字典
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult edit(@ModelAttribute Dictionary dictionary) {
        iDictionaryService.updateBySelective(dictionary);
        return new BaseResult("ok", "修改成功");
    }

    /**
     * 删除数据字典
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam Integer id) {
        iDictionaryService.deleteById(id);
        return new BaseResult("ok", "删除成功");
    }

}
