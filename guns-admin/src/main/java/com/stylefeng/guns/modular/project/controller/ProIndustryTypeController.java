package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IProIndustryTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.ProIndustryType;
/**
 * 产业分类表控制器
 *
 * @author monkey
 * @Date 2018-01-04 10:41:23
 */
@Controller
@RequestMapping("/pro_industryType")
public class ProIndustryTypeController extends BaseController {

    @Autowired
    private IProIndustryTypeService proIndustryTypeService;
    private String PREFIX = "/api/pro_industryType/";

    /**
     * 跳转到产业分类表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "pro_industryType.html";
    }

    /**
     * 跳转到添加产业分类表
     */
    @RequestMapping("/pro_industryType_add")
    public String pro_industryTypeAdd() {
        return PREFIX + "pro_industryType_add.html";
    }

    /**
     * 跳转到修改产业分类表
     */
    @RequestMapping("/pro_industryType_update/{pro_industryTypeId}")
    public String pro_industryTypeUpdate(@PathVariable Integer pro_industryTypeId, Model model) {
        return PREFIX + "pro_industryType_edit.html";
    }

    /**
     * 获取产业分类表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增产业分类表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProIndustryType proIndustryType) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除产业分类表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改产业分类表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 产业分类表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
