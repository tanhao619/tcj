package com.stylefeng.guns.modular.oa.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.oa.service.IWorkflowConfigService;
import com.stylefeng.guns.common.persistence.model.WorkflowConfig;
/**
 * 项目管理控制器
 *
 * @author lgg
 * @Date 2017-12-17 23:40:08
 */
@Controller
@RequestMapping("/workflowConfig")
public class WorkflowConfigController extends BaseController {

    @Autowired
    private IWorkflowConfigService workflowConfigService;
    private String PREFIX = "/oa/workflowConfig/";

    /**
     * 跳转到项目管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workflowConfig.html";
    }

    /**
     * 跳转到添加项目管理
     */
    @RequestMapping("/workflowConfig_add")
    public String workflowConfigAdd() {
        return PREFIX + "workflowConfig_add.html";
    }

    /**
     * 跳转到修改项目管理
     */
    @RequestMapping("/workflowConfig_update/{workflowConfigId}")
    public String workflowConfigUpdate(@PathVariable Integer workflowConfigId, Model model) {
        return PREFIX + "workflowConfig_edit.html";
    }

    /**
     * 获取项目管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增项目管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkflowConfig workflowConfig) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除项目管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改项目管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 项目管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
