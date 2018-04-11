package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.common.persistence.model.ProTalkBackup;
import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.project.service.IProTalkBackUpService;
/**
 * 洽谈信息备份表控制器
 *
 * @author monkey
 * @Date 2017-12-21 08:52:32
 */
@Controller
@RequestMapping("/proTalkBackUp")
public class ProTalkBackUpController extends BaseController {

    @Autowired
    private IProTalkBackUpService proTalkBackUpService;
    private String PREFIX = "/api/proTalkBackUp/";

    /**
     * 跳转到洽谈信息备份表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proTalkBackUp.html";
    }

    /**
     * 跳转到添加洽谈信息备份表
     */
    @RequestMapping("/proTalkBackUp_add")
    public String proTalkBackUpAdd() {
        return PREFIX + "proTalkBackUp_add.html";
    }

    /**
     * 跳转到修改洽谈信息备份表
     */
    @RequestMapping("/proTalkBackUp_update/{proTalkBackUpId}")
    public String proTalkBackUpUpdate(@PathVariable Integer proTalkBackUpId, Model model) {
        return PREFIX + "proTalkBackUp_edit.html";
    }

    /**
     * 获取洽谈信息备份表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增洽谈信息备份表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProTalkBackup proTalkBackUp) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除洽谈信息备份表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改洽谈信息备份表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 洽谈信息备份表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
