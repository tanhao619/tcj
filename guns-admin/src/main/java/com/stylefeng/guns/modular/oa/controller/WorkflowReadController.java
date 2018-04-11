package com.stylefeng.guns.modular.oa.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.oa.service.IWorkflowReadService;
import com.stylefeng.guns.common.persistence.model.WorkflowRead;
/**
 * 项目管理控制器
 *
 * @author lgg
 * @Date 2018-01-04 19:07:27
 */
@Controller
@RequestMapping("/workflowRead")
public class WorkflowReadController extends BaseController {

    @Autowired
    private IWorkflowReadService workflowReadService;
    private String PREFIX = "/oa/workflowRead/";

    /**
     * 跳转到项目管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workflowRead.html";
    }

    /**
     * 跳转到添加项目管理
     */
    @RequestMapping("/workflowRead_add")
    public String workflowReadAdd() {
        return PREFIX + "workflowRead_add.html";
    }

    /**
     * 跳转到修改项目管理
     */
    @RequestMapping("/workflowRead_update/{workflowReadId}")
    public String workflowReadUpdate(@PathVariable Integer workflowReadId, Model model) {
        return PREFIX + "workflowRead_edit.html";
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
    public Object add(WorkflowRead workflowRead) {
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
