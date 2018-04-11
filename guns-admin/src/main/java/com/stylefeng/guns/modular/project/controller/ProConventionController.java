package com.stylefeng.guns.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import com.stylefeng.guns.modular.project.service.IProConventionBackupService;
import com.stylefeng.guns.modular.project.service.IProConventionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * 履约信息控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/proConvention")
public class ProConventionController extends BaseController {

    @Autowired
    private IProConventionService proConventionService;
    private String PREFIX = "/api/proConvention/";
    @Autowired
    private INormalProjectService iNormalProjectService;
    @Autowired
    private IFollowProjectService followProjectService;
    @Autowired
    private IProConventionBackupService proConventionBackupService;

    /**
     * 跳转到履约信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proConvention.html";
    }

    /**
     * 跳转到添加履约信息
     */
    @RequestMapping("/proConvention_add")
    public String proConventionAdd() {
        return PREFIX + "proConvention_add.html";
    }

    /**
     * 跳转到修改履约信息
     */
    @RequestMapping("/proConvention_update/{proConventionId}")
    public String proConventionUpdate(@PathVariable Integer proConventionId, Model model) {
        return PREFIX + "proConvention_edit.html";
    }

    /**
     * 获取履约信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增履约信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProConvention proConvention) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除履约信息
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改履约信息
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 履约信息详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }


    /**
     * 项目操作详情
     */
    @RequestMapping(value = "/proConventions",method = RequestMethod.GET)
    @ResponseBody
    public Object queryProConventions(@RequestParam Map<String,Object> map) {
        Page<ProConvention> historys = null;
        try {
            historys = proConventionService.searchList(map);
        } catch (Exception e) {
            RecordLogTools.error("queryProConventions", e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.ERROR_MGS);
        }
        return super.packForBT(historys);
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteProConventions(@PathVariable("id") Integer id) {

        if (id != null && id > 0) {
            ProConvention proConvention=proConventionService.selectById(id);
            if(proConvention!=null){
                proConvention.setUpdateTime(new Date());//更新时间
                proConvention.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
                boolean bool = proConventionService.updateById(proConvention);
                if (!bool){
                    return new FailTip(HttpReStatus.NOT_FOUND,"修改失败！");
                }else {
                    new ProConvention().deleteById(Integer.parseInt(id.toString()));
                    return SUCCESS_TIP;
                }
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ERROR_MGS);
    }
    /**
     * 审核通过履约信息（新增）
     */
    @RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object checkProConvention(@PathVariable("id") Integer id) {
        if (id != null && id > 0) {
            ProConvention proConvention=proConventionService.selectById(id);
            if(proConvention!=null){
                proConvention.setUpdateTime(new Date());//更新时间
                proConvention.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
                proConvention.setIsBack("1");
                boolean bool = proConventionService.updateById(proConvention);
                if (!bool){
                    return new FailTip(HttpReStatus.NOT_FOUND,"审核失败！");
                }else {
                    return new FailTip(HttpReStatus.OK,"审核成功！");
                }
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ERROR_MGS);
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object updateProConventions(ProConvention proConvention) {
        if(proConvention != null){
            Integer proId=proConvention.getProId();
            //获取当前人的最高等级，1科室 2平台 3街道
            Integer deptLv = followProjectService.queryDeptLv(proId, 1);//常规项目有洽谈，履约。重大是工作进度
            if(deptLv != null){
                if(deptLv == 1){
                    proConvention.setIsBack("1");
                    //同步备份表
//                    ProConventionBackup proConventionBackup=new ProConventionBackup();
//                    BeanUtils.copyProperties(proConvention,proConventionBackup);
//                    boolean bool = proConventionBackupService.updateById(proConventionBackup);
//                    if (!bool){
//                        return new FailTip(HttpReStatus.NOT_FOUND,"同步备份表失败！");
//                    }
                }else {
                    proConvention.setIsBack("0");
                }
            }
            proConvention.setUpdateTime(new Date());//更新时间
            proConvention.setUserId(ShiroKit.getUser().getId());
            proConvention.setUserName(ShiroKit.getUser().getName());
            proConvention.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
            boolean bool = proConventionService.updateById(proConvention);
            if (!bool){
                return new FailTip(HttpReStatus.NOT_FOUND,"修改失败！");
            }
            //先判断是否是最新的一条

            int proConventionMaxId=proConventionService.selectproConventionMaxId(proId).intValue();
            if(proConvention.getId().intValue() == proConventionMaxId){
                //最新的一条，需要同步主表
                NormalProject normalProject=iNormalProjectService.selectById(proId);
                if(normalProject != null){

                    if(deptLv != null){
                        if(deptLv == 1){
                            normalProject.setIsBack("1");
                        }else {
                            normalProject.setIsBack("0");
                        }
                    }
                    normalProject.setIsMergeT("-1");
                    normalProject.setUpdateTime(new Date());
                    normalProject.setConUpdateTime(new Date());
                    normalProject.setUpdateUserId(ShiroKit.getUser().getId());
                    normalProject.setProConventionInfo(proConvention.getProConventionInfo());
                    normalProject.setNextAdvise(proConvention.getNextAdvise());
                    boolean bool2 = iNormalProjectService.updateById(normalProject);
                    if(!bool2){
                        return new FailTip(HttpReStatus.NOT_FOUND,"更新主表失败！");
                    }
                }
            }

            return new SuccessTip(HttpReStatus.OK,"修改成功！");
        }
        return new ErrorTip(HttpReStatus.BAD_REQUEST,"参数不能为空！");
    }
}
