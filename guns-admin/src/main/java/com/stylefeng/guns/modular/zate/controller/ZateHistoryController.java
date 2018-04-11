package com.stylefeng.guns.modular.zate.controller;

import com.stylefeng.guns.common.persistence.model.ZateHistory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.zate.service.IZateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 载体资源信息变更控制器
 *
 * @author lgg
 * @Date 2017-11-07 23:19:57
 */
@Controller
@RequestMapping("/zateHistory")
public class ZateHistoryController extends BaseController {

    @Autowired
    private IZateHistoryService zateHistoryService;
    private String PREFIX = "/api/zateHistory/";

    /**
     * 跳转到载体资源信息变更首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "zateHistory.html";
    }

    /**
     * 跳转到添加载体资源信息变更
     */
    @RequestMapping("/zateHistory_add")
    public String zateHistoryAdd() {
        return PREFIX + "zateHistory_add.html";
    }

    /**
     * 跳转到修改载体资源信息变更
     */
    @RequestMapping("/zateHistory_update/{zateHistoryId}")
    public String zateHistoryUpdate(@PathVariable Integer zateHistoryId, Model model) {
        return PREFIX + "zateHistory_edit.html";
    }

    /**
     * 获取载体资源信息变更列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<ZateHistory> zateHistories = new ZateHistory().selectAll();
        return zateHistories;
    }

    /**
     * 新增载体资源信息变更
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ZateHistory zateHistory) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除载体资源信息变更
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改载体资源信息变更
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 载体资源信息变更详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
