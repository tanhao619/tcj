package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IProAttachmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.ProAttachment;
/**
 * 附件信息控制器
 *
 * @author monkey
 * @Date 2017-11-07 15:56:52
 */
@Controller
@RequestMapping("/proAttachment")
public class ProAttachmentController extends BaseController {

    @Autowired
    private IProAttachmentService proAttachmentService;
    private String PREFIX = "/api/proAttachment/";

    /**
     * 跳转到附件信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proAttachment.html";
    }

    /**
     * 跳转到添加附件信息
     */
    @RequestMapping("/proAttachment_add")
    public String proAttachmentAdd() {
        return PREFIX + "proAttachment_add.html";
    }

    /**
     * 跳转到修改附件信息
     */
    @RequestMapping("/proAttachment_update/{proAttachmentId}")
    public String proAttachmentUpdate(@PathVariable Integer proAttachmentId, Model model) {
        return PREFIX + "proAttachment_edit.html";
    }

    /**
     * 获取附件信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增附件信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProAttachment proAttachment) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除附件信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改附件信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 附件信息详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
