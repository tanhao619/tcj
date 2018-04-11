package com.stylefeng.guns.modular.admindep.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.modular.admindep.service.IAdmindepService;
import com.stylefeng.guns.modular.util.IdsUtils;
import io.swagger.annotations.*;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.common.persistence.model.GovaffDatamanAdmindep;

import java.util.*;

/**
 * 部门信息控制器
 *
 * @author lgg
 * @Date 2017-10-26 15:23:14
 */
//@Controller
//@RequestMapping("/admindep")
public class AdmindepController_guns extends BaseController {
    @Autowired
    private IAdmindepService admindepService;
    private String PREFIX = "/api/admindep/";

    /**
     * 跳转到部门信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "admindep.html";
    }

    /**
     * 跳转到添加部门信息
     */
    @RequestMapping("/admindep_add")
    public String admindepAdd() {
        return PREFIX + "admindep_add.html";
    }

    /**
     * 跳转到修改部门信息
     */
    @RequestMapping("/admindep_update/{admindepId}")
    public String admindepUpdate(@PathVariable Integer admindepId, Model model) {
        GovaffDatamanAdmindep govaffDatamanAdmindep = admindepService.selectById(admindepId);
        model.addAttribute(govaffDatamanAdmindep);
        return PREFIX + "admindep_edit.html";
    }

    /**
     * 获取部门信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(GovaffDatamanAdmindep govaffDatamanAdmindep,String searchTimes,String searchLikes) {

        Page<GovaffDatamanAdmindep> page = admindepService.searchList(govaffDatamanAdmindep,searchTimes,searchLikes);
        return super.packForBT(page);
    }

    /**
     * 新增部门信息
     */
    @ApiOperation(value = "新增部门", notes = "新增部门", tags={ "Admindep", })
    @PostMapping(value = "/add")
    @ResponseBody
    public Object add(@ApiParam(value = "部门信息", required = true) @RequestBody
                                  GovaffDatamanAdmindep govaffDatamanAdmindep) {
        boolean insertBool = admindepService.insert(govaffDatamanAdmindep);
        if (!insertBool){
            return new FailTip(HttpReStatus.SERVER_ERROR,"新增失败1!");
        }
        return  new SuccessTip(HttpReStatus.OK,"新增成功1！");
    }

    /**
     * 删除部门信息
     */
    @ApiOperation(value = "删除部门", notes = "根据部门id删除部门", tags={ "Admindep", })
    @PostMapping(value = "/delete")
    @ResponseBody
    public Object delete(@ApiParam(value = "如果是一个，则是对象唯一id，如果是多个，则是以逗号','分隔，例如：12,32,331",required=true ) @RequestParam(value = "ids") String ids) {
        Map<String,Object> reMap = new HashMap<String,Object>();
        List<Integer> reIds = IdsUtils.encapIds(ids);
        if (CollectionUtils.isEmpty(reIds)){
            return new ErrorTip(HttpReStatus.BAD_REQUEST,"删除失败！!");
        }

        boolean delBool = admindepService.deleteBatchIds(reIds);
        if (!delBool){
            return new ErrorTip(HttpReStatus.BAD_REQUEST,"删除1失败1！");
        }
        return new SuccessTip(HttpReStatus.OK,"删除2成功！");
    }


    /**
     * 修改部门信息
     */
    @ApiOperation(value = "更新部门信息", notes = "根据部门id更新部门信息", tags={ "Admindep", })
    @PostMapping(value = "/update")
    @ResponseBody
    public Object update(@ApiParam(value = "部门信息", required = true) @RequestBody
                                     GovaffDatamanAdmindep govaffDatamanAdmindep) {
        boolean bool = admindepService.updateById(govaffDatamanAdmindep);
        if (!bool){
            return new ErrorTip(HttpReStatus.NOT_FOUND,"更新失败1！");
        }
        return new SuccessTip(HttpReStatus.OK,"更新成功！！");
    }

    /**
     * 部门信息详情
     */
    @ApiOperation(value = "查看部门信息", notes = "查看部门信息", tags={ "Admindep", })
    @PostMapping(value = "/detail")
    @ResponseBody
    public Object detail(@ApiParam(value = "对象唯一2id", required = true) @PathVariable("id") Integer id) {
        GovaffDatamanAdmindep govaffDatamanAdmindep = admindepService.selectById(id);
        if (govaffDatamanAdmindep == null){
            return new FailTip(HttpReStatus.NOT_FOUND ,"数据未找1233到!");
        }
        return govaffDatamanAdmindep;
    }
}
