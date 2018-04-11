package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.project.service.IProCategaryService;
import com.stylefeng.guns.common.persistence.model.ProCategary;
/**
 * 行业分类控制器
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Controller
@RequestMapping("/proCategary")
public class ProCategaryController extends BaseController {

    @Autowired
    private IProCategaryService proCategaryService;
    private String PREFIX = "/project/proCategary/";

    /**
     * 跳转到行业分类首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proCategary.html";
    }

    /**
     * 跳转到添加行业分类
     */
    @RequestMapping("/proCategary_add")
    public String proCategaryAdd() {
        return PREFIX + "proCategary_add.html";
    }

    /**
     * 跳转到修改行业分类
     */
    @RequestMapping("/proCategary_update/{proCategaryId}")
    public String proCategaryUpdate(@PathVariable Integer proCategaryId, Model model) {
        return PREFIX + "proCategary_edit.html";
    }

    /**
     * 获取行业分类列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增行业分类
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProCategary proCategary) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除行业分类
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改行业分类
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 行业分类详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
