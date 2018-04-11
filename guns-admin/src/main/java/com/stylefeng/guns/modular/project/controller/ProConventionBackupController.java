package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.project.service.IProConventionBackupService;
import com.stylefeng.guns.common.persistence.model.ProConventionBackup;
/**
 * 履约信息备份表控制器
 *
 * @author monkey
 * @Date 2017-12-21 08:52:32
 */
@Controller
@RequestMapping("/proConventionBackup")
public class ProConventionBackupController extends BaseController {

    @Autowired
    private IProConventionBackupService proConventionBackupService;
    private String PREFIX = "/api/proConventionBackup/";

    /**
     * 跳转到履约信息备份表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proConventionBackup.html";
    }

    /**
     * 跳转到添加履约信息备份表
     */
    @RequestMapping("/proConventionBackup_add")
    public String proConventionBackupAdd() {
        return PREFIX + "proConventionBackup_add.html";
    }

    /**
     * 跳转到修改履约信息备份表
     */
    @RequestMapping("/proConventionBackup_update/{proConventionBackupId}")
    public String proConventionBackupUpdate(@PathVariable Integer proConventionBackupId, Model model) {
        return PREFIX + "proConventionBackup_edit.html";
    }

    /**
     * 获取履约信息备份表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增履约信息备份表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProConventionBackup proConventionBackup) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除履约信息备份表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改履约信息备份表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 履约信息备份表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
