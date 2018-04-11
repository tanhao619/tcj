package com.stylefeng.guns.modular.oa.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.oa.dao.WorkflowDao;
import com.stylefeng.guns.modular.oa.dto.*;
import com.stylefeng.guns.modular.oa.service.*;
import com.stylefeng.guns.modular.project.service.IProWorkProcessService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.util.file.FileUploadUtil;
import com.stylefeng.guns.modular.zate.dto.FileDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * OA管理控制器
 *
 * @author lgg
 * @Date 2017-12-04 12:59:19
 */
@Controller
@RequestMapping("/workflow")
public class WorkflowController extends BaseController {

    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private WorkflowDao workflowDao;
    @Autowired
    private IWorkflowStepService workflowStepService;
    @Autowired
    private IWorkflowAttachmentService workflowAttachmentService;
    @Autowired
    private IWorkflowReadService workflowReadService;
    private String PREFIX = "/api/oa/workflow/";

    /**
     * 跳转到OA管理首页
     */
    @RequestMapping("")
    public String index() {
        return "/api/oa/" + "index.html";
    }

    /**
     * 跳转到添加OA管理
     */
    @RequestMapping("/workflow_add")
    public String workflowAdd(Model model) {
        // 发起审批-审批人
        List<FaqiRoleDTO> apprRoles = workflowService.selectApprRole("");
        model.addAttribute("apprRoles",apprRoles);
        return "/api/oa/" + "add.html";
    }

    /**
     * 跳转到修改OA管理
     */
    @RequestMapping("/workflow_update/{workflowId}")
    public String workflowUpdate(@PathVariable Integer workflowId, Model model) {
        return PREFIX + "workflow_edit.html";
    }

    /**
     * 获取OA管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list( @RequestParam Map<String,Object> map) {
        Page<WorkflowDTO> workflowPage = workflowService.list(map);
        return super.packForBT(workflowPage);
    }

    /**
     * 办结列表
     * @param map
     * @return
     */
    @RequestMapping(value = "/banjie/list")
    @ResponseBody
    public Object banjieList( @RequestParam Map<String,Object> map) {
        Page<BanjieWorkflowDTO> banjieWorkflowPage = workflowService.banjieList(map);
        return super.packForBT(banjieWorkflowPage);
    }
    /**
     * 获取【办结页】-未办结数量
     */
    @RequestMapping(value = "/banjie/unApprNum")
    @ResponseBody
    public Object unBanjieNum( @RequestParam Map<String,Object> map) {
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        parasMapE.put("userId", ShiroKit.getUser().getId());
        Page<BanjieWorkflowDTO> page = new PageFactory<BanjieWorkflowDTO>().defaultPage();
        List<BanjieWorkflowDTO> banjieFlows =  workflowDao.banjieList(null,page,parasMapE);
        long unBanjieNum = 0;
        if (!CollectionUtils.isEmpty(banjieFlows)){
            unBanjieNum = banjieFlows.stream().filter(p -> "否".equals(p.getIsBanjie())).count();
        }

        return unBanjieNum;
    }
    /**
     *  【办结】-详情审批流程
     * @return
     */
    @RequestMapping(value = "/banjie/detailflows")
    @ResponseBody
    public Object getBanjieDetailflows(Integer workflowId,String flowType,String fenleiType) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 审批
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){
            SWDetailFlowsDTO shouWenDetailflows = workflowStepService.getBanjieDetailflows(workflowId, flowType, fenleiType);
            return shouWenDetailflows;
        }
        return null;
    }
    /**
     *  【办结】-详情审批流程
     * @return
     */
    @RequestMapping(value = "/banjie/bJSWworkflow")
    @ResponseBody
    public Object bJSWworkflow(Integer workflowId) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 审批
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){
            Boolean iscanAppr = workflowStepService.isCanAppr(workflowId);
            if (iscanAppr){
                Boolean rebool = workflowStepService.bJSWworkflow(workflowId);
                if (rebool){
                    return new SuccessTip(HttpReStatus.OK,"办结成功！");
                }
                return  new FailTip(HttpReStatus.BAD_REQUEST,"办结失败！");
            }else {
                return  new FailTip(HttpReStatus.FORBIDDEN,"没有该收文办结权限！");
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST,"办结失败！");
    }
    /**
     * 获取【办结页】-指派科室
     */
    @RequestMapping(value = "/banjie/assignDept")
    @ResponseBody
    public Object assignDept() {
         Map<String,Object> map = new HashMap<String,Object>();
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        parasMapE.put("userId", ShiroKit.getUser().getId());
        Page<BanjieWorkflowDTO> assignDeptpage = new PageFactory<BanjieWorkflowDTO>().defaultPage();
        List<BanjieWorkflowDTO> banjieFlows =  workflowDao.banjieList(null,assignDeptpage,parasMapE);
        List<String> reAssignDeptList = new ArrayList<String>();
        if (!CollectionUtils.isEmpty(banjieFlows)){
            for (BanjieWorkflowDTO banjieFlow : banjieFlows) {
                if(!reAssignDeptList.contains(banjieFlow.getAssignDept())){
                    reAssignDeptList.add(banjieFlow.getAssignDept());
                }
            }
        }
        return reAssignDeptList;
    }
    /**
     * 获取归档列表
     */
    @RequestMapping(value = "/guidang/list")
    @ResponseBody
    public Object guidangList( @RequestParam Map<String,Object> map) {
        if (ShiroKit.isBGSZR()){// 办公室主任 可看到除 收文/发文审批 以外的归档。
            Page<GuidangWorkflowDTO> guidangWorkflowList = workflowService.bgszrGuidangList(map);
            return super.packForBT(guidangWorkflowList);
        }else {// 办公室 收文/归档科员 可看到 收文/发文审批的归档
            if (ShiroKit.isSWGD()){
                Page<GuidangWorkflowDTO> swgdGuidangWorkflowDTOPage = workflowService.swgdGuidangList(map);
                return super.packForBT(swgdGuidangWorkflowDTOPage);
            }
        }
        return null;

    }
    /**
     * 获取【归档页】-归档未读数
     */
    @RequestMapping(value = "/guidang/unReadNum")
    @ResponseBody
    public Object unReadNum( @RequestParam Map<String,Object> map) {
        long unreadNum = 0;
        if (ShiroKit.isBGSZR()){// 办公室主任 可看到除 收文/发文审批 以外的归档。
            Page<GuidangWorkflowDTO> guidangWorkflowpage = workflowService.bgszrGuidangList(map);
            List<GuidangWorkflowDTO> guidangWorkflowList = guidangWorkflowpage.getRecords();
            unreadNum = guidangWorkflowList.stream().filter(p -> "未读".equals(p.getReadStatus())).count();
        }else {// 办公室 收文/归档科员 可看到 收文/发文审批的归档
            if (ShiroKit.isSWGD()){
                Page<GuidangWorkflowDTO> swgdGuidangWorkflowDTOPage = workflowService.swgdGuidangList(map);
                List<GuidangWorkflowDTO> records = swgdGuidangWorkflowDTOPage.getRecords();
                unreadNum = records.stream().filter(p -> "未读".equals(p.getReadStatus())).count();
            }
        }
        return unreadNum;
    }
    /**
     * 归档阅读
     */
    @RequestMapping(value = "/guidang/read")
    @ResponseBody
    public Object guidangRead(Integer workflowId) {
        String userAccount = "";
        if (ShiroKit.isSWGD()){
            userAccount = Constant.SWGD;
        }
        if (ShiroKit.isBGSZR()){
            userAccount = Constant.BGSZR;
        }
        List<WorkflowRead> workReads = workflowReadService.selectList(new EntityWrapper<WorkflowRead>().
                eq("workflowId", workflowId).and()
                .eq("readStatus","0")
                .and().eq("userAccount",userAccount));
        if (!CollectionUtils.isEmpty(workReads)){
            WorkflowRead workflowRead = workReads.get(0);
            workflowRead.setReadStatus("1");
            workflowReadService.updateById(workflowRead);
            return new SuccessTip(HttpReStatus.OK,"设置已读成功！！");
        }
        return new FailTip(HttpReStatus.OK,"设置已读失败或已经阅读！！");
    }

    /**
     * 跳转到归档导出页面
     */
    @RequestMapping("/guidang/exportConfig")
    public String exportConfig(Model model) {

        return "/api/oa/guidang/" + "guidang_export.html";
    }
    /**
     * 新增OA管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam Map<Object, Object> map) {

        // key - 审批类型 typeFQ 审批人-roleIdFQ  审批附件- attachments 收文编号-ordnumSW
        if (isFaqiParmLegitimacy(map)) {
            // 如果是收文审批，判断收文编号是否存在
            if (Constant.OA_SHOWWEN_TYPE.equals(map.get("typeFQ")) && notUniqueOrdnumSW(map)){
                return new FailTip(HttpReStatus.BAD_REQUEST,"收文编号已存在！");
            }
            // 如果是请假审批，判断请假时间是否重复
            if (Constant.OA_QINGJIA_TYPE.equals(map.get("typeFQ")) && isRetainQJTime(map)){
                return new FailTip(HttpReStatus.BAD_REQUEST,"请假时间有重复!");
            }
            Boolean relean = workflowService.insertWorkflow(map);
            if (!relean){
                return new FailTip(HttpReStatus.BAD_REQUEST,"发起审批失败！");
            }
            return  new SuccessTip(HttpReStatus.OK,"发起审批成功！");
        } else {
            return new FailTip(HttpReStatus.BAD_REQUEST, Constant.OA_ADD_ERROR);
        }
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

    /**
     * 【我发起的】-发起人角色
     */
    @RequestMapping(value = "/faqiRole")
    @ResponseBody
    public Object getFaqiRole() {
        Integer uid = ShiroKit.getUser().getId();
        List<Role> faqiRoles = workflowDao.getFaqiRole(uid);
        List<FaqiRoleDTO> faqiRoleDTOs = faqiRoles.stream().map(p -> {
            FaqiRoleDTO faqiRoleDTO = new FaqiRoleDTO();
            BeanUtils.copyProperties(p, faqiRoleDTO);
            return faqiRoleDTO;
        }).collect(Collectors.toList());
        return faqiRoleDTOs;
    }


    /**
     * 【我发起的】-发起审批-审批人
     */
    @RequestMapping(value = "/apprRole")
    @ResponseBody
    public Object getFaqiApprRole(String type) {
        List<FaqiRoleDTO> apprRoles = workflowService.selectApprRole(type);
        return apprRoles;
    }

    /**
     * 发起审批-上传审批附件
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public Object upload(@RequestPart("file") MultipartFile file) {
        FileDto fileDto = null;
        try {
            fileDto = FileUploadUtil.upload(file);
        } catch (Exception e) {
            RecordLogTools.error("上传附件失败！",e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.FAIL_UPLOAD);
        }
        if (fileDto == null){
            return new FailTip(HttpReStatus.SERVER_ERROR, Constant.FAIL_UPLOAD);
        }
        return fileDto;
    }

    /**
     * 【我发起的】-审批记录
     */
    @RequestMapping(value = "/apprRecord")
    @ResponseBody
    public Object getApprRecord(Integer workflowId) {

        return null;
    }

    /**
     * 【我发起的】-撤销审批
     * @param workflowId
     * @return
     */
    @RequestMapping(value = "/revokeShenpi")
    @ResponseBody
    public Object revokeShenpi(Integer workflowId) {
        // 撤销审批--需要做什么：1、把workflow审批状态置为撤销 2、设置审批完成时间
        try {
            // 1、把workflow审批状态置为撤销
            Workflow workflow = workflowService.selectById(workflowId);
            workflow.setStatus(Constant.OA_STATUS_CHEXIAO);
            workflow.setEndTime(new Date());
            workflowService.updateById(workflow);
            // 2、不删除掉workflow_step的下一步流程记录
            //workflowStepService.delete(new EntityWrapper<WorkflowStep>().eq("workflowId",workflowId));
            // 3、不删除workflowId相关的workflow_property，因为已撤销的审批也要显示workflow_property中的东西
            // workflowPropertyService.delete(new EntityWrapper<WorkflowProperty>().eq("workflowId",workflowId));
            return  new SuccessTip(HttpReStatus.OK,"撤销成功！");
        } catch (Exception e) {
            return new FailTip(HttpReStatus.BAD_REQUEST, "撤销失败！");
        }
    }

    /**
     * 【我发起的】-请假的销假
     */
    @RequestMapping(value = "/faqi/xiaojia")
    @ResponseBody
    public Object xiaojia(Integer workflowId) {

        if (ToolUtil.isNotEmpty(workflowId)){
            WorkflowProperty workflowProperty = new WorkflowProperty();
            workflowProperty.setType("1");
            workflowProperty.setName("qjXJBtn");
            workflowProperty.setValue("true");
            workflowProperty.setWorkflowId(workflowId);
            workflowProperty.setCreateTime(new Date());
            boolean bool = workflowPropertyService.insert(workflowProperty);
            if (bool){
                return  new SuccessTip(HttpReStatus.OK,"销假成功！");
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST, "销假失败！");
    }

    private Boolean isFaqiParmLegitimacy(Map<Object, Object> map){
        Boolean reBool = false;
        if (!map.isEmpty() && !"".equals(map.get("typeFQ")) &&
                !"".equals(map.get("roleIdFQ")) &&
                !"".equals(map.get("attachments")) &&
                map.get("attachments") !=null){
            reBool = true;
            // 请假参数的验证
            if (Constant.OA_QINGJIA_TYPE.equals(map.get("typeFQ"))){
                if (!"".equals(map.get("qjTimeB")) && !"".equals(map.get("qjAMPMB"))
                        && !"".equals(map.get("qjTimeE")) && !"".equals(map.get("qjAMPME"))
                        &&  !"".equals(map.get("qjTotalDays"))){
                    String qjTotalDays = (String)map.get("qjTotalDays");
                    try {// qjTotalDays必须是数字类型的字符串
                        double v = Double.parseDouble(qjTotalDays);
                        if (v > 0.0){
                            reBool = true;
                        }
                    }catch (Exception e){
                        reBool = false;
                    }
                }else{
                    reBool = false;
                }
            }
        }
        return reBool;
    }
    /**
     * 收文编号唯一
     */
    @Autowired
    private IWorkflowPropertyService workflowPropertyService;
    private Boolean notUniqueOrdnumSW(Map<Object, Object> map){
        Boolean reBool = false;
        List<WorkflowProperty> pros = workflowPropertyService.selectList(new EntityWrapper<WorkflowProperty>()
                .eq("name", "ordnumSW")
                .and()
                .eq("value", (String) map.get("ordnumSW")));
       if (!CollectionUtils.isEmpty(pros)){//已撤销的收文审批的收文作废
           List<Integer> workflowIds = pros.stream().map(p -> p.getWorkflowId()).collect(Collectors.toList());
           List<Workflow> workflows = workflowService.selectBatchIds(workflowIds);
           long count = workflows.stream().filter(p -> p.getStatus() != null && !"6".equals(p.getStatus())).count();
           if (count > 0){
               reBool = true;
           }
       }
        return reBool;
    }
    /**
     * 请假时间无交集，不给通过
     */
    private Boolean isRetainQJTime(Map<Object, Object> map){
        map.put("userId",ShiroKit.getUser().getId());
        Boolean reBool = false;
        Integer exsitQJCount = workflowDao.selectExsitQJCount(map);
        if (ToolUtil.isNotEmpty(exsitQJCount) && exsitQJCount > 0){
            reBool = true;
        }
        return reBool;
    }
}
