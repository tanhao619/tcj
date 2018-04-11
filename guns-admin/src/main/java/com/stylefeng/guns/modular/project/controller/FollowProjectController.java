package com.stylefeng.guns.modular.project.controller;

import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.FollowProject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.service.IBigProjectService;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import com.stylefeng.guns.modular.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 项目跟踪控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/followProject")
public class FollowProjectController extends BaseController {

    @Autowired
    private IFollowProjectService followProjectService;

    @Autowired
    private INormalProjectService normalProjectService;

    @Autowired
    private IBigProjectService bigProjectService;

    private String PREFIX = "/api/followProject/";

    /**
     * 跳转到项目跟踪首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "followProject.html";
    }

    /**
     * 跳转到添加项目跟踪
     */
    @RequestMapping("/followProject_add")
    public String followProjectAdd() {
        return PREFIX + "followProject_add.html";
    }

    /**
     * 跳转到修改项目跟踪
     */
    @RequestMapping("/followProject_update/{followProjectId}")
    public String followProjectUpdate(@PathVariable Integer followProjectId, Model model) {
        return PREFIX + "followProject_edit.html";
    }

    /**
     * 获取项目跟踪列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增项目跟踪
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(FollowProject followProject) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除项目跟踪
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改项目跟踪
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 项目跟踪详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
    /**
     * 项目跟踪详情
     */
    @RequestMapping(value = "/queryByProId", method = RequestMethod.GET)
    @ResponseBody
    public Object query(@RequestParam Map<String, Object> map) {

        return super.SUCCESS_TIP;
    }

    /**
     * 项目跟踪详情
     */
    @RequestMapping(value = "/updateFollowProject", method = RequestMethod.POST)
    @ResponseBody
    public Object updateFollowProject(@RequestParam Map<String, Object> map) {
        try {
            Object proId = map.get("proId");
            if (proId == null || proId == "null") {
                return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ERROR_MGS);
            }
            map.put("currentTime", StringUtil.set(System.currentTimeMillis()));
            map.put("isUpdate", true);
            Object folType = map.get("folType");
            //更新平台信息
            followProjectService.batchInsertFollowProjects(Integer.parseInt(proId.toString()), map);
            if (folType != null && folType != "null") {
                map.put("userId", ShiroKit.getUser().id);
                if (map.get("from").toString().equals("2")) {
                    bigProjectService.updateFolType(map);
                } else {
                    normalProjectService.updateFolType(map);
                }
            }

        } catch (Exception e) {
            RecordLogTools.error("updateFollowProject失败！", e);
            return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ERROR_MGS);
        }
        return super.SUCCESS_TIP;
    }

}
