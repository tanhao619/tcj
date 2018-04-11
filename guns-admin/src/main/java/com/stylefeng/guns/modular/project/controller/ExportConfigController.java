package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IExportConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.common.persistence.model.ExportConfig;

import java.util.Map;

/**
 * 导出配置控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/exportConfig")
public class ExportConfigController extends BaseController {

    @Autowired
    private IExportConfigService exportConfigService;
    private String PREFIX = "/api/normalExportConfig/";

    /**
     * 跳转到导出配置首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "normalExportConfig.html";
    }

    /**
     * 跳转到添加导出配置
     */
    @RequestMapping("/exportConfig_add")
    public String exportConfigAdd() {
        return PREFIX + "exportConfig_add.html";
    }

    /**
     * 跳转到修改导出配置
     */
    @RequestMapping("/exportConfig_update/{exportConfigId}")
    public String exportConfigUpdate(@PathVariable Integer exportConfigId, Model model) {
        return PREFIX + "exportConfig_edit.html";
    }

    /**
     * 获取导出配置列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增导出配置
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ExportConfig exportConfig) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除导出配置
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改导出配置
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 导出配置详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    /**
     * 项目跟踪详情
     */
    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    @ResponseBody
    public Object exportExcel(@RequestParam Map<String, Object> map) {

        return super.SUCCESS_TIP;
    }

}
