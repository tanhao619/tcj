package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.common.persistence.model.ProContractType;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IProContractTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目合同类型管理控制器
 *
 * @author monkey1
 * @Date 2017-11-16 21:07:25
 */
@Controller
@RequestMapping("/proContractType")
public class ProContractTypeController extends BaseController {

    @Autowired
    private IProContractTypeService proContractTypeService;
    private String PREFIX = "/api/proContractType/";

    /**
     * 跳转到项目合同类型管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proContractType.html";
    }

    /**
     * 跳转到添加项目合同类型管理
     */
    @RequestMapping("/proContractType_add")
    public String proContractTypeAdd() {
        return PREFIX + "proContractType_add.html";
    }

    /**
     * 跳转到修改项目合同类型管理
     */
    @RequestMapping("/proContractType_update/{proContractTypeId}")
    public String proContractTypeUpdate(@PathVariable Integer proContractTypeId, Model model) {
        return PREFIX + "proContractType_edit.html";
    }

    /**
     * 获取项目合同类型管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增项目合同类型管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProContractType proContractType) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除项目合同类型管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改项目合同类型管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 项目合同类型管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
