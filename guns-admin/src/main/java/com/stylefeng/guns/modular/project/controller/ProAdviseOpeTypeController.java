package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.common.persistence.model.ProAdviseOpeType;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IProAdviseOpeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目实施类型管理控制器
 *
 * @author monkey1
 * @Date 2017-11-16 21:06:50
 */
@Controller
@RequestMapping("/proAdviseOpeType")
public class ProAdviseOpeTypeController extends BaseController {

    @Autowired
    private IProAdviseOpeTypeService proAdviseOpeTypeService;
    private String PREFIX = "/api/proAdviseOpeType/";

    /**
     * 跳转到项目实施类型管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proAdviseOpeType.html";
    }

    /**
     * 跳转到添加项目实施类型管理
     */
    @RequestMapping("/proAdviseOpeType_add")
    public String proAdviseOpeTypeAdd() {
        return PREFIX + "proAdviseOpeType_add.html";
    }

    /**
     * 跳转到修改项目实施类型管理
     */
    @RequestMapping("/proAdviseOpeType_update/{proAdviseOpeTypeId}")
    public String proAdviseOpeTypeUpdate(@PathVariable Integer proAdviseOpeTypeId, Model model) {
        return PREFIX + "proAdviseOpeType_edit.html";
    }

    /**
     * 获取项目实施类型管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增项目实施类型管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProAdviseOpeType proAdviseOpeType) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除项目实施类型管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改项目实施类型管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 项目实施类型管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
