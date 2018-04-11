package com.stylefeng.guns.modular.project.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.NormalProject;
import com.stylefeng.guns.common.persistence.model.ProTalkBackup;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import com.stylefeng.guns.modular.project.service.IProTalkBackUpService;
import com.stylefeng.guns.modular.project.service.IProTalkService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.common.persistence.model.ProTalk;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 洽谈信息控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/proTalk")
public class ProTalkController extends BaseController {

    @Autowired
    private IProTalkService proTalkService;
    private String PREFIX = "/api/proTalk/";
    @Autowired
    private INormalProjectService iNormalProjectService;
    @Autowired
    private IFollowProjectService followProjectService;
    @Autowired
    private IProTalkBackUpService proTalkBackUpService;

    /**
     * 跳转到洽谈信息首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "proTalk.html";
    }

    /**
     * 跳转到添加洽谈信息
     */
    @RequestMapping("/proTalk_add")
    public String proTalkAdd() {
        return PREFIX + "proTalk_add.html";
    }

    /**
     * 跳转到修改洽谈信息
     */
    @RequestMapping("/proTalk_update/{proTalkId}")
    public String proTalkUpdate(@PathVariable Integer proTalkId, Model model) {
        return PREFIX + "proTalk_edit.html";
    }

    /**
     * 获取洽谈信息列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增洽谈信息
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ProTalk proTalk) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除洽谈信息
     */
    @RequestMapping(value = "/batchDelete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {

        return SUCCESS_TIP;
    }


    /**
     * 修改洽谈信息
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public Object update(ProTalk proTalk) {
        if(proTalk != null){
            Integer proId=proTalk.getProId();
            //获取当前人的最高等级，1科室 2平台 3街道
            Integer deptLv = followProjectService.queryDeptLv(proId, 1);//常规项目有洽谈，履约。重大是工作进度
            if(deptLv != null){
                if(deptLv == 1){
                    proTalk.setIsBack("1");
                    //同步备份表
//                    ProTalkBackup proTalkBackup=new ProTalkBackup();
//                    BeanUtils.copyProperties(proTalk,proTalkBackup);
//                    boolean bool = proTalkBackUpService.updateById(proTalkBackup);
//                    if (!bool){
//                        return new FailTip(HttpReStatus.NOT_FOUND,"同步备份表失败！");
//                    }
                }else {
                    proTalk.setIsBack("0");
                }
            }
            proTalk.setUpdateTime(new Date());//更新时间
            proTalk.setUserId(ShiroKit.getUser().getId());
            proTalk.setUserName(ShiroKit.getUser().getName());
            proTalk.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
            boolean bool = proTalkService.updateById(proTalk);
            if (!bool){
                return new FailTip(HttpReStatus.NOT_FOUND,"修改失败！");
            }
            //先判断是否是最新的一条

            int proTalkMaxId=proTalkService.selectproTalkMaxId(proId).intValue();
            if(proTalk.getId().intValue() == proTalkMaxId){
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
                    normalProject.setIsMerge("-1");
                    normalProject.setUpdateTime(new Date());
                    normalProject.setTalkUpdateTime(new Date());
                    normalProject.setUpdateUserId(ShiroKit.getUser().getId());
                    normalProject.setProgress(proTalk.getProgress());
                    normalProject.setQuestion(proTalk.getQuestion());
                    normalProject.setNextStep(proTalk.getNextStep());
                    normalProject.setIsVisit(proTalk.getIsVisit());
                    normalProject.setVisitLv(proTalk.getVisitLv());
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
/**
 * 审核通过洽谈信息（新增）
 */
@RequestMapping(value = "/audit/{id}",method = RequestMethod.POST)
@ResponseBody
public Object checkProTalk(@PathVariable("id") Integer id) {
    if (id != null && id > 0) {
        ProTalk proTalk=proTalkService.selectById(id);
        if(proTalk!=null){
            proTalk.setUpdateTime(new Date());//更新时间
            proTalk.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
            proTalk.setIsBack("1");
            boolean bool = proTalkService.updateById(proTalk);
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
     * 洽谈信息详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    /**
     * 项目操作详情
     */
    @RequestMapping(value = "/proTalks",method = RequestMethod.GET)
    @ResponseBody
    public Object queryProTalks(@RequestParam Map<String,Object> map) {
        Page<ProTalk> proTalks = null;
        try {
            proTalks = proTalkService.searchList(map);
        } catch (Exception e) {
            RecordLogTools.error("queryProTalks", e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.ERROR_MGS);
        }
        return super.packForBT(proTalks);
    }

    /**
     * 项目删除
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteProTalks(@PathVariable("id") Integer id) {

        if (id != null && id > 0) {
            ProTalk proTalk=proTalkService.selectById(id);
            if(proTalk!=null){
                proTalk.setUpdateTime(new Date());//更新时间
                proTalk.setCurrentTime(System.currentTimeMillis()+"");//时间戳，用于触发器记录
                boolean bool = proTalkService.updateById(proTalk);
                if (!bool){
                    return new FailTip(HttpReStatus.NOT_FOUND,"修改失败！");
                }else {
                    new ProTalk().deleteById(Integer.parseInt(id.toString()));
                    return SUCCESS_TIP;
                }
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ERROR_MGS);
    }
}
