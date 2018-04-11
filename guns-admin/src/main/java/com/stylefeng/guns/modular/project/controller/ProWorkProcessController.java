package com.stylefeng.guns.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.BigProject;
import com.stylefeng.guns.common.persistence.model.ProWorkProcessBackup;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.service.IBigProjectService;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.IProWorkProcessBackupService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.modular.project.service.IProWorkProcessService;
import com.stylefeng.guns.common.persistence.model.ProWorkProcess;

import java.util.Date;
import java.util.Map;

/**
 * 工作进度控制器
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Controller
@RequestMapping("/proWorkProcess")
public class ProWorkProcessController extends BaseController {

    @Autowired
    private IProWorkProcessService proWorkProcessService;
    @Autowired
    private IProWorkProcessBackupService proWorkProcessBackupService;
    private String PREFIX = "/project/proWorkProcess/";
    @Autowired
    private IBigProjectService iBigProjectService;
    @Autowired
    private IFollowProjectService followProjectService;

    /**
     * 跳转到工作进度首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proWorkProcess.html";
    }

    /**
     * 跳转到添加工作进度
     */
    @RequestMapping("/proWorkProcess_add")
    public String proWorkProcessAdd() {
        return PREFIX + "proWorkProcess_add.html";
    }

    /**
     * 跳转到修改工作进度
     */
    @RequestMapping("/proWorkProcess_update/{proWorkProcessId}")
    public String proWorkProcessUpdate(@PathVariable Integer proWorkProcessId, Model model) {
        return PREFIX + "proWorkProcess_edit.html";
    }

    /**
     * 获取工作进度列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam Map<String,Object> map) {
        Page<ProWorkProcess> proWorkProcess = null;
        try {
            proWorkProcess = proWorkProcessService.searchList(map);
        } catch (Exception e) {
            RecordLogTools.error("queryProWorkProcess", e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.ERROR_MGS);
        }
        return super.packForBT(proWorkProcess);
    }

    /**
     * 新增工作进度
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Object add(ProWorkProcess proWorkProcess) {
        if(proWorkProcess != null){
            proWorkProcess.setCreateTime(new Date());//创建时间
            proWorkProcess.setFolType(2);//重大项目
            proWorkProcess.setCurrentTime(System.currentTimeMillis()+"");
            proWorkProcess.setUserId(ShiroKit.getUser().getId());
            proWorkProcess.setUserName(ShiroKit.getUser().getName());
            Boolean bool=proWorkProcessService.insert(proWorkProcess);
            if(!bool){
                return new FailTip(HttpReStatus.NOT_FOUND,"新增失败！");
            }
            return new SuccessTip(HttpReStatus.OK,"新增成功！");
        }
        return new ErrorTip(HttpReStatus.BAD_REQUEST,"参数不能为空！");
    }

    /**
     * 删除工作进度
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object delete(@PathVariable("id") Integer id) {
        if (id != null && id > 0) {
            ProWorkProcess proWorkProcess=proWorkProcessService.selectById(id);
            if(proWorkProcess!=null){
                proWorkProcess.setUpdateTime(new Date());//更新时间
                proWorkProcess.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
                boolean bool = proWorkProcessService.updateById(proWorkProcess);
                if (!bool){
                    return new FailTip(HttpReStatus.NOT_FOUND,"修改失败！");
                }else {
                    new ProWorkProcess().deleteById(Integer.parseInt(id.toString()));
                    return SUCCESS_TIP;
                }
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ERROR_MGS);
    }
    /**
     * 审核工作进度（新增）
     */
    @RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object checkProWorkProcess(@PathVariable("id") Integer id) {
        if (id != null && id > 0) {
            ProWorkProcess proWorkProcess=proWorkProcessService.selectById(id);
            if(proWorkProcess!=null){
                proWorkProcess.setUpdateTime(new Date());//更新时间
                proWorkProcess.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
                proWorkProcess.setIsBack("1");
                boolean bool = proWorkProcessService.updateById(proWorkProcess);
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
     * 修改工作进度
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(ProWorkProcess proWorkProcess) {
        if(proWorkProcess != null){
            Integer proId=proWorkProcess.getProId();
            proWorkProcess.setUpdateTime(new Date());//更新时间
            proWorkProcess.setUserId(ShiroKit.getUser().getId());//操作人
            proWorkProcess.setUserName(ShiroKit.getUser().getName());//操作人名字
            proWorkProcess.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
            //获取当前人的最高等级，1科室 2平台 3街道
            Integer deptLv = followProjectService.queryDeptLv(proId, 2);//常规项目有洽谈，履约。重大是工作进度
            if(deptLv != null){
                if(deptLv == 1){
                    proWorkProcess.setIsBack("1");
                    //同步备份表(触发器实现了，无需再写)
//                    ProWorkProcessBackup proWorkProcessBackup=new ProWorkProcessBackup();
//                    BeanUtils.copyProperties(proWorkProcess,proWorkProcessBackup);
//                    boolean bool = proWorkProcessBackupService.updateById(proWorkProcessBackup);
//                    if (!bool){
//                        return new FailTip(HttpReStatus.NOT_FOUND,"同步备份表失败！");
//                    }
                }else {
                    proWorkProcess.setIsBack("0");
                }
            }
            boolean bool = proWorkProcessService.updateById(proWorkProcess);
            if (!bool){
                return new FailTip(HttpReStatus.NOT_FOUND,"修改失败！");
            }
            //先判断是否是最新的一条
            int proWorkProcessMaxId=proWorkProcessService.selectproWorkProcessMaxId(proId).intValue();
            if(proWorkProcess.getId().intValue() == proWorkProcessMaxId){
                //最新的一条，需要同步主表
                BigProject bigProject=iBigProjectService.selectById(proId);
                if(bigProject != null){

                    if(deptLv != null){
                        if(deptLv == 1){
                            bigProject.setIsBack("1");
                        }else {
                            bigProject.setIsBack("0");
                        }
                    }
                    bigProject.setIsMerge("-1");
                    bigProject.setUpdateTime(new Date());
                    bigProject.setUpdateUserId(ShiroKit.getUser().getId());
                    bigProject.setUpdateWorkTime(new Date());
                    bigProject.setWorkProcess(proWorkProcess.getWorkProcess());
                    boolean bool2 = iBigProjectService.updateById(bigProject);
                    if(!bool2){
                        return new FailTip(HttpReStatus.NOT_FOUND,"更新主表失败！");
                    }
                }
            }

            return new SuccessTip(HttpReStatus.OK,"修改成功！");
        }
        return new ErrorTip(HttpReStatus.BAD_REQUEST,"参数不能为空！");

    }

    /**
     * 工作进度详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
