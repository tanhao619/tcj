package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import com.stylefeng.guns.modular.project.service.IProBuildPlaceService;
import com.stylefeng.guns.common.persistence.model.ProBuildPlace;
/**
 * 拟建设地点控制器
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Controller
@RequestMapping("/proBuildPlace")
public class ProBuildPlaceController extends BaseController {

    @Autowired
    private IProBuildPlaceService proBuildPlaceService;
    private String PREFIX = "/project/proBuildPlace/";

    /**
     * 跳转到拟建设地点首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proBuildPlace.html";
    }

    /**
     * 跳转到添加拟建设地点
     */
    @RequestMapping("/proBuildPlace_add")
    public String proBuildPlaceAdd() {
        return PREFIX + "proBuildPlace_add.html";
    }

    /**
     * 跳转到修改拟建设地点
     */
    @RequestMapping("/proBuildPlace_update/{proBuildPlaceId}")
    public String proBuildPlaceUpdate(@PathVariable Integer proBuildPlaceId, Model model) {
        return PREFIX + "proBuildPlace_edit.html";
    }

    /**
     * 获取拟建设地点列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增拟建设地点
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProBuildPlace proBuildPlace) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除拟建设地点
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改拟建设地点
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 拟建设地点详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
