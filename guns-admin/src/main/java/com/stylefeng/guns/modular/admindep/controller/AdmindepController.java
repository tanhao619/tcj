package com.stylefeng.guns.modular.admindep.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.GovaffDatamanAdmindep;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.modular.admindep.service.IAdmindepService;
import com.stylefeng.guns.modular.util.IdsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门信息控制器
 *
 * @author lgg
 * @Date 2017-10-26 15:23:14
 */
@Controller
@RequestMapping("/admindep")
public class AdmindepController extends BaseController {

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
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(GovaffDatamanAdmindep govaffDatamanAdmindep) {
        boolean insertBool = admindepService.insert(govaffDatamanAdmindep);
        if (!insertBool){
            return new FailTip(HttpReStatus.SERVER_ERROR,"新增失败！!");
        }
        return  new SuccessTip(HttpReStatus.OK,"新增成功！");
    }

    /**
     * 删除部门信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        Map<String,Object> reMap = new HashMap<String,Object>();
        List<Integer> reIds = IdsUtils.encapIds(ids);
        if (CollectionUtils.isEmpty(reIds)){
            return new ErrorTip(HttpReStatus.BAD_REQUEST,"删除失败!");
        }

        boolean delBool = admindepService.deleteBatchIds(reIds);
        if (!delBool){
            return new ErrorTip(HttpReStatus.BAD_REQUEST, Constant.DEL_ERROR);
        }
        return new SuccessTip(HttpReStatus.OK,"删除成功！");
    }


    /**
     * 修改部门信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(GovaffDatamanAdmindep govaffDatamanAdmindep) {
        boolean bool = admindepService.updateById(govaffDatamanAdmindep);
        if (!bool){
            return new ErrorTip(HttpReStatus.NOT_FOUND,"更新失败！");
        }
        return new SuccessTip(HttpReStatus.OK,"更新成功！");
    }

    /**
     * 部门信息详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail(Integer id) {
        GovaffDatamanAdmindep govaffDatamanAdmindep = admindepService.selectById(id);
        if (govaffDatamanAdmindep == null){
            return new FailTip(HttpReStatus.NOT_FOUND ,"数据未找到!123");
        }
        return govaffDatamanAdmindep;
    }
}
