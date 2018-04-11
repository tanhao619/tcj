package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.project.service.IProPackPositionService;
import com.stylefeng.guns.common.persistence.model.ProPackPosition;
/**
 * 策划包装定位控制器
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Controller
@RequestMapping("/proPackPosition")
public class ProPackPositionController extends BaseController {

    @Autowired
    private IProPackPositionService proPackPositionService;
    private String PREFIX = "/project/proPackPosition/";

    /**
     * 跳转到策划包装定位首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proPackPosition.html";
    }

    /**
     * 跳转到添加策划包装定位
     */
    @RequestMapping("/proPackPosition_add")
    public String proPackPositionAdd() {
        return PREFIX + "proPackPosition_add.html";
    }

    /**
     * 跳转到修改策划包装定位
     */
    @RequestMapping("/proPackPosition_update/{proPackPositionId}")
    public String proPackPositionUpdate(@PathVariable Integer proPackPositionId, Model model) {
        return PREFIX + "proPackPosition_edit.html";
    }

    /**
     * 获取策划包装定位列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增策划包装定位
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProPackPosition proPackPosition) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除策划包装定位
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改策划包装定位
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 策划包装定位详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
