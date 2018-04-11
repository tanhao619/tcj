package com.stylefeng.guns.modular.zate.controller;

import com.stylefeng.guns.common.persistence.model.ZateLandimg;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.zate.service.IZateLandimgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 土地载体资源土地点位图控制器
 *
 * @author lgg
 * @Date 2017-11-07 23:21:32
 */
@Controller
@RequestMapping("/zateLandimg")
public class ZateLandimgController extends BaseController {

    @Autowired
    private IZateLandimgService zateLandimgService;
    private String PREFIX = "/api/zateLandimg/";

    /**
     * 跳转到土地载体资源土地点位图首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "zateLandimg.html";
    }

    /**
     * 跳转到添加土地载体资源土地点位图
     */
    @RequestMapping("/zateLandimg_add")
    public String zateLandimgAdd() {
        return PREFIX + "zateLandimg_add.html";
    }

    /**
     * 跳转到修改土地载体资源土地点位图
     */
    @RequestMapping("/zateLandimg_update/{zateLandimgId}")
    public String zateLandimgUpdate(@PathVariable Integer zateLandimgId, Model model) {
        return PREFIX + "zateLandimg_edit.html";
    }

    /**
     * 获取土地载体资源土地点位图列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增土地载体资源土地点位图
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ZateLandimg zateLandimg) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除土地载体资源土地点位图
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改土地载体资源1土地点位图
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 土地载体资源土地点位图详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
