package com.stylefeng.guns.modular.myplatform.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.modular.myplatform.dto.PendingListDto;
import com.stylefeng.guns.modular.myplatform.dto.UpdateListDto;
import com.stylefeng.guns.modular.myplatform.service.IMyPlatformService;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
/**
 * 我的工作台控制器
 *
 * @author lgg
 * @Date 2017-11-08 09:58:39
 */
@Controller
@RequestMapping("/myPlatform")
public class MyPlatformController extends BaseController {

    @Autowired
    private IMyPlatformService myPlatformService;
    private String PREFIX = "/api/myPlatform/";

    /**
     * 跳转到我的工作台首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "myPlatform.html";
    }

    /**
     * 跳转到添加我的工作台
     */
    @RequestMapping("/myPlatform_add")
    public String myPlatformAdd() {
        return PREFIX + "myPlatform_add.html";
    }

    /**
     * 跳转到修改我的工作台
     */
    @RequestMapping("/myPlatform_update/{myPlatformId}")
    public String myPlatformUpdate(@PathVariable Integer myPlatformId, Model model) {
        return PREFIX + "myPlatform_edit.html";
    }

    /**
     * 我的工作台-待处理项目列表
     */
    @ApiOperation(value = "待处理项目列表", notes = "获取分页列表", tags={ "myPlatform", })
    @RequestMapping (value = "/PendingList", method = RequestMethod.GET)
    @ResponseBody
    public Object PendingList() {
        Page<PendingListDto> zatePage = myPlatformService.pendingList();
        if(zatePage == null){
            return  null;
        }else{
            return super.packForBT(zatePage);
        }

    }
    /**
     * 我的工作台-跟踪项目更新提醒列表
     */
    @ApiOperation(value = "跟踪项目跟踪提醒列表", notes = "获取分页列表", tags={ "myPlatform", })
    @RequestMapping (value = "/updateList", method = RequestMethod.GET)
    @ResponseBody
    public Object updateList() {
        Page<UpdateListDto> Page = myPlatformService.updateList();
        return super.packForBT(Page);
    }
    /**
     * 我的工作台-项目一周未更新列表
     */
    @ApiOperation(value = "项目一周未更新列表", notes = "获取分页列表", tags={ "myPlatform", })
    @RequestMapping (value = "/noUpdateList", method = RequestMethod.GET)
    @ResponseBody
    public Object noUpdateList() {
        Page<UpdateListDto> Page = myPlatformService.noUpdateList();
        return super.packForBT(Page);
    }
    /**
     * 获取我的工作台列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增我的工作台
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Object object) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除我的工作台
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids1") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改我的工作台
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 我的工作台详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
