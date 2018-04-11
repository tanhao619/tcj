package com.stylefeng.guns.modular.oa.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.Workflow;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.oa.dao.WorkflowStepDao;
import com.stylefeng.guns.modular.oa.dto.*;
import com.stylefeng.guns.modular.oa.service.IWorkflowService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.stylefeng.guns.modular.oa.service.IWorkflowStepService;
import com.stylefeng.guns.common.persistence.model.WorkflowStep;

import java.util.List;
import java.util.Map;

/**
 * OA管理控制器
 *
 * @author lgg
 * @Date 2017-12-04 13:06:58
 */
@Controller
@RequestMapping("/workflowStep")
public class WorkflowStepController extends BaseController {

    @Autowired
    private IWorkflowStepService workflowStepService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private WorkflowStepDao workflowStepDao;
    private String PREFIX = "/api/oa/workflowStep/";

    /**
     * 跳转到OA管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "workflowStep.html";
    }

    /**
     * 跳转到添加OA管理
     */
    @RequestMapping("/workflowStep_add")
    public String workflowStepAdd() {
        return PREFIX + "workflowStep_add.html";
    }

    /**
     * 跳转到修改OA管理
     */
    @RequestMapping("/workflowStep_update/{workflowStepId}")
    public String workflowStepUpdate(@PathVariable Integer workflowStepId, Model model) {
        return PREFIX + "workflowStep_edit.html";
    }


    /**
     * 获取【我审批的】-未审批数
     */
    @RequestMapping(value = "/shenpi/unApprNum")
    @ResponseBody
    public Object unShenpiApprNum( @RequestParam Map<String,Object> map) {
        Integer unShenpiApprNum =  workflowStepService.getUnShenpiApprNum();
        return unShenpiApprNum;
    }
    /**
     * 获取【我审批的】列表&搜索 = 我的账号已审批的流程 + 2、我的角色可审批的流程
     */
    @RequestMapping(value = "/shenpi/list")
    @ResponseBody
    public Object faqiList( @RequestParam Map<String,Object> map) {
        Page<ShenPiWorkflowDTO> shenPiListPage = workflowStepService.shenpiList(map);
        return super.packForBT(shenPiListPage);
    }

    /**
     *  【发起】-详情审批流程
     * @return
     */
    @RequestMapping(value = "/faqi/detailflows")
    @ResponseBody
    public Object getFaqiDetailflows(Integer workflowId,String flowType,String fenleiType) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 收文审批
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){
            SWDetailFlowsDTO shouWenDetailflows = workflowStepService.getShouWenDetailflows(workflowId, flowType, fenleiType);
            return shouWenDetailflows;
        }else{
            NormalDetailFlowsDTO detailflows  = workflowStepService.getDetailflows(workflowId, flowType, fenleiType);
            return detailflows;
        }

    }

    /**
     *  【我审批的】-详情审批流程 ,用于审批未审批的流程 和 查看已审批的流程详情
     * @return
     */
    @RequestMapping(value = "/shenpi/detailflows")
    @ResponseBody
    public Object getSpDetailflows(Integer workflowId,String flowType,String fenleiType) {
         Workflow workflow = workflowService.selectById(workflowId);
       // 收文审批
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){
            SWDetailFlowsDTO shouWenDetailflows = workflowStepService.getShouWenDetailflows(workflowId, flowType, fenleiType);
            return shouWenDetailflows;
        }else{
            NormalDetailFlowsDTO normalDetailflows = workflowStepService.getDetailflows(workflowId, flowType, fenleiType);
            return normalDetailflows;
        }
    }
    /**
     *  【归档】-详情审批流程
     * @return
     */
    @RequestMapping(value = "/guidang/detailflows")
    @ResponseBody
    public Object getGuidangDetailflows(Integer workflowId,String flowType,String fenleiType) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 收文审批
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){
            SWDetailFlowsDTO shouWenDetailflows = workflowStepService.getShouWenDetailflows(workflowId, flowType, fenleiType);
            return shouWenDetailflows;
        }else{
            NormalDetailFlowsDTO detailflows  = workflowStepService.getDetailflows(workflowId, flowType, fenleiType);
            return detailflows;
        }
    }
    /**
     * 审批一般审批流程
     */
    @RequestMapping(value = "/shenpi/apprNormalflow")
    @ResponseBody
    public Object apprflow(Integer workflowId,String step,String type,String spAdvice) {
        Boolean iscanAppr = workflowStepService.isCanAppr(workflowId);
        if (iscanAppr){
            Boolean rebool = workflowStepService.apprNormalflow(workflowId, step, type, spAdvice);
            if (rebool){
                return new SuccessTip(HttpReStatus.OK,"审批成功！");
            }
            return  new FailTip(HttpReStatus.BAD_REQUEST,"审批失败！");
        }else {
            return  new FailTip(HttpReStatus.FORBIDDEN,"没有该流程审批权限！");
        }

    }
    /**
     * 审批收文审批流程
     */
    @RequestMapping(value = "/shenpi/apprShouWenflow")
    @ResponseBody
    public Object apprShouWenflow(@RequestParam(value = "userFPDTOs",required = false) String  userFPDTOsStr,Integer workflowId, String step,String step2Type) {
        Boolean iscanApprShouWen = workflowStepService.isCanAppr(workflowId);
        if ("[]".equals(userFPDTOsStr)){// 分配人未选
            return  new FailTip(HttpReStatus.BAD_REQUEST,"请选择分配人！");
        }
        String thisSWExsitUserNames = thisSWExsitUsers(workflowId, userFPDTOsStr, step);
        if (!"-1".equals(thisSWExsitUserNames)){// 被分配人已经在该流程中存在
            return  new FailTip(HttpReStatus.BAD_REQUEST,thisSWExsitUserNames);
        }

        if (iscanApprShouWen){
            Boolean rebool = workflowStepService.apprShouWenflow(userFPDTOsStr,workflowId, step,step2Type);
            if (rebool){
                return new SuccessTip(HttpReStatus.OK,"审批成功！");
            }
            return  new FailTip(HttpReStatus.BAD_REQUEST,"审批失败！");
        }else {
            return  new FailTip(HttpReStatus.FORBIDDEN,"没有该流程审批权限！");
        }

    }
    /**
     * 收文分配页面
     */
    @RequestMapping("/sw_fenpei_user")
    public String swfenpeiuser(Integer workflowId,String step,Model model) {
        SWFPUsersDTO swFenpeiUsers = workflowStepService.getFenpeiUsers(workflowId, step);
        model.addAttribute("workflowId",workflowId);
        model.addAttribute("step",step);
        return "/api/oa/shenpi/" + "sw_fenpei_user.html";
    }
    /**
     * 收文的办结详情页
     */
    @RequestMapping("/obtain_banjie_detail")
    public String obtainBanjieDetail(Integer workflowId,Model model) {
        model.addAttribute("workflowId",workflowId);
        return "/api/oa/" + "obtain_banjie_detail.html";
    }
    @RequestMapping(value = "/banjieDetail")
    @ResponseBody
    public Object banjieDetail(Integer workflowId) {
        List<BJDetailDTO> banjieDetails = workflowStepService.getBanjieDetail(workflowId);
        return banjieDetails;
    }
    /**
     * 收文分配人
     */
    @RequestMapping(value = "/shenpi/SWfenpeiUsers")
    @ResponseBody
    public Object getFenpeiUsers(Integer workflowId,String step) {
        SWFPUsersDTO swFenpeiUsers = workflowStepService.getFenpeiUsers(workflowId, step);
        return  swFenpeiUsers;
    }
    /**
     * 新增OA管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(WorkflowStep workflowStep) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除OA管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改OA管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * OA管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    private String thisSWExsitUsers(Integer workflowId,String userFPDTOsStr,String step){
        String msg = "-1";
        List<UserFPDTO> userFPDTOS = ParamsUtils.escapUserSWDTOS(userFPDTOsStr);
        List<Integer> thisSWExsitUserIds = workflowStepDao.selectOneSWExsitUserIds(workflowId);
        if (!CollectionUtils.isEmpty(userFPDTOS) && !CollectionUtils.isEmpty(thisSWExsitUserIds)){
            String thisSWExsitUserNames = "";
            for (UserFPDTO userFPDTO : userFPDTOS) {
                if (thisSWExsitUserIds.contains(userFPDTO.getUserId())){
                    thisSWExsitUserNames += userFPDTO.getUserName() + "、";
                }
            }
            if(StringUtils.hasText(thisSWExsitUserNames)){
                if ("4".equals(step)){
                    msg = "科长"+thisSWExsitUserNames.substring(0,thisSWExsitUserNames.length()-1)+"已被指派任务，无法再次指派。";
                }
                if ("5".equals(step)){
                    msg = "科员"+thisSWExsitUserNames.substring(0,thisSWExsitUserNames.length()-1)+"已被指派任务，无法再次指派。";
                }
            }

        }
        return msg;
    }
}
