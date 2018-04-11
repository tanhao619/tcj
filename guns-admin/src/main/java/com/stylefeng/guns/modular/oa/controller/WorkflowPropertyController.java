package com.stylefeng.guns.modular.oa.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.oa.service.IWorkflowPropertyService;
import com.stylefeng.guns.common.persistence.model.WorkflowProperty;
/**
 * OA管理控制器
 *
 * @author lgg
 * @Date 2017-12-04 20:07:17
 */
@Controller
@RequestMapping("/workflowProperty")
public class WorkflowPropertyController extends BaseController {

    @Autowired
    private IWorkflowPropertyService workflowPropertyService;
    private String PREFIX = "/oa/workflowProperty/";

    /**
     * 跳转到OA管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workflowProperty.html";
    }

    /**
     * 跳转到添加OA管理
     */
    @RequestMapping("/workflowProperty_add")
    public String workflowPropertyAdd() {
        return PREFIX + "workflowProperty_add.html";
    }

    /**
     * 跳转到修改OA管理
     */
    @RequestMapping("/workflowProperty_update/{workflowPropertyId}")
    public String workflowPropertyUpdate(@PathVariable Integer workflowPropertyId, Model model) {
        return PREFIX + "workflowProperty_edit.html";
    }

    /**
     * 获取OA管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增OA管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkflowProperty workflowProperty) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除OA管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改OA管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * OA管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
