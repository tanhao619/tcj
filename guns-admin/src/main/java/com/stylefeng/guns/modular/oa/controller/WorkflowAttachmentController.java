package com.stylefeng.guns.modular.oa.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.oa.service.IWorkflowAttachmentService;
import com.stylefeng.guns.common.persistence.model.WorkflowAttachment;
/**
 * OA管理控制器
 *
 * @author lgg
 * @Date 2017-12-04 20:07:47
 */
@Controller
@RequestMapping("/workflowAttachment")
public class WorkflowAttachmentController extends BaseController {

    @Autowired
    private IWorkflowAttachmentService workflowAttachmentService;
    private String PREFIX = "/oa/workflowAttachment/";

    /**
     * 跳转到OA管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workflowAttachment.html";
    }

    /**
     * 跳转到添加OA管理
     */
    @RequestMapping("/workflowAttachment_add")
    public String workflowAttachmentAdd() {
        return PREFIX + "workflowAttachment_add.html";
    }

    /**
     * 跳转到修改OA管理
     */
    @RequestMapping("/workflowAttachment_update/{workflowAttachmentId}")
    public String workflowAttachmentUpdate(@PathVariable Integer workflowAttachmentId, Model model) {
        return PREFIX + "workflowAttachment_edit.html";
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
    public Object add(WorkflowAttachment workflowAttachment) {
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
