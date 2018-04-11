package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IProAreaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.ProArea;
/**
 * 项目地址及用地控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/proArea")
public class ProAreaController extends BaseController {

    @Autowired
    private IProAreaService proAreaService;
    private String PREFIX = "/api/proArea/";

    /**
     * 跳转到项目地址及用地首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proArea.html";
    }

    /**
     * 跳转到添加项目地址及用地
     */
    @RequestMapping("/proArea_add")
    public String proAreaAdd() {
        return PREFIX + "proArea_add.html";
    }

    /**
     * 跳转到修改项目地址及用地
     */
    @RequestMapping("/proArea_update/{proAreaId}")
    public String proAreaUpdate(@PathVariable Integer proAreaId, Model model) {
        return PREFIX + "proArea_edit.html";
    }

    /**
     * 获取项目地址及用地列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增项目地址及用地
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProArea proArea) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除项目地址及用地
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改项目地址及用地
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 项目地址及用地详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
