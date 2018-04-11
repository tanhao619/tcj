package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.project.service.IProWorkProcessBackupService;
import com.stylefeng.guns.common.persistence.model.ProWorkProcessBackup;
/**
 * 工作进度备份表控制器
 *
 * @author monkey
 * @Date 2017-12-21 08:52:32
 */
@Controller
@RequestMapping("/proWorkProcessBackup")
public class ProWorkProcessBackupController extends BaseController {

    @Autowired
    private IProWorkProcessBackupService proWorkProcessBackupService;
    private String PREFIX = "/api/proWorkProcessBackup/";

    /**
     * 跳转到工作进度备份表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proWorkProcessBackup.html";
    }

    /**
     * 跳转到添加工作进度备份表
     */
    @RequestMapping("/proWorkProcessBackup_add")
    public String proWorkProcessBackupAdd() {
        return PREFIX + "proWorkProcessBackup_add.html";
    }

    /**
     * 跳转到修改工作进度备份表
     */
    @RequestMapping("/proWorkProcessBackup_update/{proWorkProcessBackupId}")
    public String proWorkProcessBackupUpdate(@PathVariable Integer proWorkProcessBackupId, Model model) {
        return PREFIX + "proWorkProcessBackup_edit.html";
    }

    /**
     * 获取工作进度备份表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增工作进度备份表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProWorkProcessBackup proWorkProcessBackup) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除工作进度备份表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改工作进度备份表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 工作进度备份表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
