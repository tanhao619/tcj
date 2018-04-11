package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.project.service.IProCompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.common.persistence.model.ProCompany;
/**
 * 投资方公司控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/proCompany")
public class ProCompanyController extends BaseController {

    @Autowired
    private IProCompanyService proCompanyService;
    private String PREFIX = "/api/proCompany/";

    /**
     * 跳转到投资方公司首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proCompany.html";
    }

    /**
     * 跳转到添加投资方公司
     */
    @RequestMapping("/proCompany_add")
    public String proCompanyAdd() {
        return PREFIX + "proCompany_add.html";
    }

    /**
     * 跳转到修改投资方公司
     */
    @RequestMapping("/proCompany_update/{proCompanyId}")
    public String proCompanyUpdate(@PathVariable Integer proCompanyId, Model model) {
        return PREFIX + "proCompany_edit.html";
    }

    /**
     * 获取投资方公司列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增投资方公司
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProCompany proCompany) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除投资方公司
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改投资方公司
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 投资方公司详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
