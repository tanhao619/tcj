package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IUnitLiableService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.UnitLiable;
/**
 * 责任单位控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/unitLiable")
public class UnitLiableController extends BaseController {

    @Autowired
    private IUnitLiableService unitLiableService;
    private String PREFIX = "/api/unitLiable/";

    /**
     * 跳转到责任单位首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "unitLiable.html";
    }

    /**
     * 跳转到添加责任单位
     */
    @RequestMapping("/unitLiable_add")
    public String unitLiableAdd() {
        return PREFIX + "unitLiable_add.html";
    }

    /**
     * 跳转到修改责任单位
     */
    @RequestMapping("/unitLiable_update/{unitLiableId}")
    public String unitLiableUpdate(@PathVariable Integer unitLiableId, Model model) {
        return PREFIX + "unitLiable_edit.html";
    }

    /**
     * 获取责任单位列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增责任单位
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UnitLiable unitLiable) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除责任单位
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改责任单位
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 责任单位详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
