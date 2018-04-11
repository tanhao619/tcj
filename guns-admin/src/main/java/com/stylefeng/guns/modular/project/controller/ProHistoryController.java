package com.stylefeng.guns.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.annotion.DataPermission;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.Zate;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.service.IProHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.common.persistence.model.ProHistory;

import java.util.Map;

/**
 * 项目操作控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/proHistory")
public class ProHistoryController extends BaseController {

    @Autowired
    private IProHistoryService proHistoryService;
    private String PREFIX = "/api/proHistory/";

    /**
     * 跳转到项目操作首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proHistory.html";
    }

    /**
     * 跳转到添加项目操作
     */
    @RequestMapping("/proHistory_add")
    public String proHistoryAdd() {
        return PREFIX + "proHistory_add.html";
    }

    /**
     * 跳转到修改项目操作
     */
    @RequestMapping("/proHistory_update/{proHistoryId}")
    public String proHistoryUpdate(@PathVariable Integer proHistoryId, Model model) {
        return PREFIX + "proHistory_edit.html";
    }

    /**
     * 获取项目操作列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增项目操作
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProHistory proHistory) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除项目操作
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改项目操作
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 项目操作详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }


    /**
     * 项目操作详情
     */
    @RequestMapping(value = "/proHistorys",method = RequestMethod.GET)
    @ResponseBody
    //@DataPermission(value = "proId")//用于数据权限拦截 add by lgg
    //lgg,there's not need  add Permission,because this param is more than one and dynamic
    public Object queryProHistory(@RequestParam Map<String,Object> map) {
        Page<ProHistory> historys = null;
        try {
            historys = proHistoryService.searchList(map);
        } catch (Exception e) {
            RecordLogTools.error("queryProHistory", e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.ERROR_MGS);
        }
        return super.packForBT(historys);
    }

}
