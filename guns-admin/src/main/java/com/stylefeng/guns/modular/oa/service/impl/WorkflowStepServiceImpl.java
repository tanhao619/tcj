package com.stylefeng.guns.modular.oa.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.Const;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.oa.dao.WorkflowAttachmentDao;
import com.stylefeng.guns.modular.oa.dao.WorkflowPropertyDao;
import com.stylefeng.guns.modular.oa.dao.WorkflowStepDao;
import com.stylefeng.guns.modular.oa.dto.*;
import com.stylefeng.guns.modular.oa.service.IWorkflowPropertyService;
import com.stylefeng.guns.modular.oa.service.IWorkflowReadService;
import com.stylefeng.guns.modular.oa.service.IWorkflowService;
import com.stylefeng.guns.modular.system.dao.RoleDao;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.zate.dto.ZateImgDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.oa.service.IWorkflowStepService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 13:06:58
 */
@Service
public class WorkflowStepServiceImpl extends ServiceImpl<BaseMapper<WorkflowStep>,WorkflowStep> implements IWorkflowStepService {

    @Autowired
    private WorkflowStepDao workflowStepDao;
    @Autowired
    private WorkflowAttachmentDao workflowAttachmentDao;
    @Autowired
    private WorkflowPropertyDao workflowPropertyDao;
    @Autowired
    private IWorkflowPropertyService workflowPropertyService;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IWorkflowStepService workflowStepService;
    @Autowired
    private UserMgrDao userDao;
    @Autowired
    private IUserService userService;
    @Autowired
    private IWorkflowReadService workflowReadService;
    @Override
    public Page<ShenPiWorkflowDTO> shenpiList(Map<String,Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<ShenPiWorkflowDTO> page = new PageFactory<ShenPiWorkflowDTO>().defaultPage();
        // 列表：1、我的账号已审批的流程 + 2、我的角色可审批的流程
        parasMapE.put("userId", ShiroKit.getUser().getId());// 根据发起人id查询其所发起的oa
        parasMapE.put("ownRoleIds",ShiroKit.getUser().getRoleList());

        List<ShenPiWorkflowDTO> shenPiFlows =  workflowStepDao.shenpiList(null,page,parasMapE);

        // 封装收文编号
        escapOrdnumSW(shenPiFlows);
        // 封装附件
        escapAttms(shenPiFlows);
        page.setRecords(shenPiFlows);
        return page;
    }

    @Override
    public Integer getUnShenpiApprNum() {
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        Integer userId = ShiroKit.getUser().getId();
        Map<String, Object> parmsMap = new HashMap<>();
        parmsMap.put("roles",roleList);
        parmsMap.put("userId",userId);
        return workflowStepDao.getUnShenpiApprNum(parmsMap);
    }

    /**
     * 除收文外的其他审批的详情审批流程
     * @param workflowId
     * @param flowType
     * @param fenleiType
     * @return
     */
    @Override
    public NormalDetailFlowsDTO getDetailflows(Integer workflowId,String flowType,String fenleiType) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 封装公共属性
        NormalDetailFlowsDTO reNDFlowDTO = new NormalDetailFlowsDTO();
        if (ToolUtil.isNotEmpty(workflow) && !Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){

            // 流程详情 切入权限
            Map<String, Object> parmsMap = new HashMap<>();
            parmsMap.put("workflowId",workflowId);
            parmsMap.put("isCanAppr",this.isCanAppr(workflowId));
            List<NormalFlowDTO> normalFlowDTOS = workflowStepDao.selectAuthNormalFlowByWfwId(parmsMap);
            // 根据normalFlowDTOS封装 步骤流程线的类型与结束步骤
            NormalDetailFlowsDTO lineNormalFlowsDTO = escapFlowsLine(normalFlowDTOS);
            BeanUtils.copyProperties(lineNormalFlowsDTO,reNDFlowDTO);
            reNDFlowDTO.setNormalFlowDTOs(normalFlowDTOS);
            reNDFlowDTO.setWorkflowId(workflowId);
            reNDFlowDTO.setType(flowType);
            if(Constant.OA_QINGJIA_TYPE.equals(flowType)){// 请假审批封装时间
                QJTimePropDTO qjTimePropDTO = workflowPropertyService.escapQJTime(workflowId);
                reNDFlowDTO.setqJTimePropDTO(qjTimePropDTO);
            }
            // fenleiType:1.发起 2.审批 3.办结 4.归档

            // 1.发起 撤销按钮状态封装
            String revokeStatus = workflowStepDao.selectRevokeStatusByWfwId(workflowId);
            reNDFlowDTO.setRevokeStatus(revokeStatus);
            // 2.审批  判断是否有该流程步骤的审批权限 true能 false 不能
            reNDFlowDTO.setIsCanSpFlow(this.isCanAppr(workflowId)+"");
            reNDFlowDTO.setIsEnd(isFlowEnd(workflow)+"");

        }


        return reNDFlowDTO;
    }

    /**
     * 收文审批的详情审批流程
     * @param workflowId
     * @param flowType
     * @param fenleiType
     * @return
     */
    @Override
    public SWDetailFlowsDTO getShouWenDetailflows(Integer workflowId, String flowType, String fenleiType) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 封装公共属性
        SWDetailFlowsDTO reSWFlowDTO = new SWDetailFlowsDTO();
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){

            // 流程详情 切入权限
            Map<String, Object> parmsMap = new HashMap<>();
            parmsMap.put("workflowId",workflowId);
            parmsMap.put("isCanAppr",this.isCanAppr(workflowId));
            parmsMap.put("userId",ShiroKit.getUser().getId());
            escapUserFlowJueSe(workflowId,parmsMap);
            hasAnyKZflowsExsit(workflowId,parmsMap);
            List<SWFlowDTO> swFlowDTOS = workflowStepDao.selectAuthShowWenFlowByWfwId(parmsMap);
            // 根据swFlowDTOS封装 步骤流程线的类型与结束步骤
            SWDetailFlowsDTO lineSWFlowsDTO = escapShouWenFlowsLine(swFlowDTOS);
            BeanUtils.copyProperties(lineSWFlowsDTO,reSWFlowDTO);
            // 封装空的第六步
            SWFlowDTO sixStepSwFlowDTO = new SWFlowDTO();
            sixStepSwFlowDTO.setStep("6");
            swFlowDTOS.add(sixStepSwFlowDTO);
            reSWFlowDTO.setSWFlowDTOs(swFlowDTOS);
            reSWFlowDTO.setWorkflowId(workflowId);
            reSWFlowDTO.setType(flowType);

            // 1.发起 撤销按钮状态封装
            String revokeStatus = workflowStepDao.selectRevokeStatusByWfwId(workflowId);
            reSWFlowDTO.setRevokeStatus(revokeStatus);



        }


        return reSWFlowDTO;
    }

    /**
     * 办结页的详情审批流程
     * @param workflowId
     * @param flowType
     * @param fenleiType
     * @return
     */
    @Override
    public SWDetailFlowsDTO getBanjieDetailflows(Integer workflowId, String flowType, String fenleiType) {
        Workflow workflow = workflowService.selectById(workflowId);
        // 封装公共属性
        SWDetailFlowsDTO reSWFlowDTO = new SWDetailFlowsDTO();
        if (ToolUtil.isNotEmpty(workflow) && Constant.OA_SHOWWEN_TYPE.equals(workflow.getType())){

            // 流程详情 切入权限
            Map<String, Object> parmsMap = new HashMap<>();
            parmsMap.put("workflowId",workflowId);
            // 封装当前人id 跟指派科长的科长
            parmsMap.put("ownUserId",ShiroKit.getUser().getId());
            Integer KZUserId = workflowStepDao.selectBanjieKZByKYidAWfwId(parmsMap);
            parmsMap.put("KZUserId",KZUserId);

            // 封装FJZswGpSn与KZswGpSn
            parmsMap.put("userId",ShiroKit.getUser().getId());
            List<WorkflowStep> swGpSnSteps = workflowStepDao.selectWfwStepByWfwIdAUid(parmsMap);
            if (!CollectionUtils.isEmpty(swGpSnSteps)){
                WorkflowStep swGpSnStep = swGpSnSteps.get(0);
                if (StringUtils.hasText(swGpSnStep.getSwGpSn())){
                    parmsMap.put("KZswGpSn",swGpSnStep.getSwGpSn());
                    parmsMap.put("FJZswGpSn",swGpSnStep.getSwGpSn().split(",")[0]);
                }
            }
            List<SWFlowDTO> swFlowDTOS = workflowStepDao.selectAuthBanjieFlowByWfwId(parmsMap);
            // 封装lineStep跟lineType
            for (SWFlowDTO swFlowDTO : swFlowDTOS) {
                if ("6".equals(swFlowDTO.getStep())){
                    reSWFlowDTO.setLineStep("6");
                    if (swFlowDTO.getIsBanjie().indexOf("##已办结##") > 0){
                        reSWFlowDTO.setLineType("同意");
                        swFlowDTO.setBjDetail("办结详情##<p class='lookBanjieDetail' onclick='BanjieStep.bjDetail("+workflowId+")'>查看办结详情</p>##3");
                        break;
                    }
                    if("o待办结o".equals(swFlowDTO.getIsBanjie())){
                        reSWFlowDTO.setLineType("待审批");
                        swFlowDTO.setIsBanjie("");
                        swFlowDTO.setBjDetail("");
                    }
                }
            }

            reSWFlowDTO.setSWFlowDTOs(swFlowDTOS);
            reSWFlowDTO.setWorkflowId(workflowId);
            reSWFlowDTO.setType(flowType);

        }


        return reSWFlowDTO;
    }

    @Override
    public Boolean bJSWworkflow(Integer workflowId) {
        // 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
        Integer thisSwFlowweiBJNum = workflowStepDao.selectHaveAnyShouWenWeiBJ(workflowId);
        // 不是最会一个办结流程：把该收文stepworkflow
        // 1、状态置为3已办结，2、apprTime办结时间，3、把主体流程的状态改为 4办结中

        // 1、状态置为3已办结，2、apprTime办结时间
        Date date = new Date();
        Map<String, Object> parmsMap = new HashMap<>();
        parmsMap.put("workflowId",workflowId);
        parmsMap.put("userId",ShiroKit.getUser().getId());
        List<WorkflowStep> thisSwFlowweiBJFlows = workflowStepDao.selectWfwStepByWfwIdAUid(parmsMap);
        if (!CollectionUtils.isEmpty(thisSwFlowweiBJFlows)){
            WorkflowStep thisSWWBJstep = thisSwFlowweiBJFlows.get(0);
            thisSWWBJstep.setApprAdvice("3");
            thisSWWBJstep.setApprTime(date);
            workflowStepService.updateById(thisSWWBJstep);
        }

        //3、把主体流程的状态改为 4办结中
        Workflow dadWorkflow = workflowService.selectById(workflowId);
        if (ToolUtil.isNotEmpty(dadWorkflow)){
            if (thisSwFlowweiBJNum > 0 && thisSwFlowweiBJNum != 1){// 不是最会一个办结流程
                //3、把主体流程的状态改为 4办结中
                dadWorkflow.setStatus("4");
            }
            if (thisSwFlowweiBJNum == 1){// 是最后一个办结流程：
                //3、把主体流程的状态改为 5已办结
                dadWorkflow.setStatus("5");
                dadWorkflow.setEndTime(date);
                setUpWorkflowRead(workflowId);
            }
            workflowService.updateById(dadWorkflow);
        }
        return true;
    }

    /**
     * 审批一般审批流程
     * @param workflowId
     * @param type
     * @param spAdvice
     * @return
     */
    @Override
    public Boolean apprNormalflow(Integer workflowId,String step,String type,String spAdvice) {
        // 审批意见:0.未审批;1.不同意;2.同意; 3.收文办结;


/*        // Workflow 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
        // 如果审批1.不同意  主体流程状态立即置为  审批未通过
        if ("1".equals(spAdvice)){
            updateWorkFlowStatus(workflowId,"2");
        }
        // 如果审批流程为最后一步 且 审批通过 置审核状态为 3.审批通过
        String maxStep = workflowStepDao.selectMaxStepByWfwId(workflowId);
        if ("2".equals(type) && step.equals(maxStep)){
            updateWorkFlowStatus(workflowId,"3");
        }*/
        Boolean reBool = true;
        Map<String, Object> parmsMap = new HashMap<>();
        parmsMap.put("workflowId",workflowId);
        // 由于封装原因，流程step需减一
        step = Integer.parseInt(step) -1 +"";
        parmsMap.put("step",step);
        List<WorkflowStep> workflowStepsBySW = workflowStepDao.selectStepByWfwIdStep(parmsMap);
        // Workflow 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
        try {
            if (!CollectionUtils.isEmpty(workflowStepsBySW)){
                WorkflowStep nowStep = workflowStepsBySW.get(0);
                // 需要做的1、生成审批人userId、userName、apprTime、apprAdvice
                //       2、如果审批同意 ：生成下一步流程的待审批roleId 与 deptId
                // 1、公共审批步骤：生成审批人userId、userName、apprTime、apprAdvice
                Date date = new Date();
                nowStep.setUserId(ShiroKit.getUser().getId());
                nowStep.setUserName(ShiroKit.getUser().getName());
                nowStep.setApprAdvice(spAdvice);
                nowStep.setApprTime(date);
                // 不是第一步设置创建时间
                if (!"1".equals(nowStep.getStep())){
                    nowStep.setCreateTime(date);
                }

                // 如果审批1.不同意  主体流程状态立即置为  审批未通过  并设置主体流程结束时间
                if ("1".equals(spAdvice)){
                    updateWorkFlowStatus(workflowId,"2",new Date());
                    //当前流程步骤是否结束:0进行中(可继续走下个流程）;1已结束(包括审批未通过、办公室备案归档,即不可再往下走)
                    nowStep.setIsEnd("1");
                }

                // 如果审批流程为最后一步 且 审批通过 置审核状态为 3.审批通过   并设置主体流程结束时间
                String maxStep = workflowStepDao.selectMaxStepByWfwId(workflowId);
                if ("2".equals(spAdvice) && step.equals(maxStep)){
                    updateWorkFlowStatus(workflowId,"3",new Date());
                    nowStep.setIsEnd("1");
                    // 流程结束，设置归档处流程未读
                    setUpWorkflowRead(workflowId);
                }
                // 更新nowstep
                workflowStepService.updateById(nowStep);

                //如果审批同意,并且不是最后一步 2、生成下一步流程的待审批roleId 与 deptId、审批状态apprAdvice置为0待审批
                if ("2".equals(spAdvice) &&  !step.equals(maxStep)){
                    parmsMap.put("step",Integer.parseInt(step)+1+"");
                    List<WorkflowStep> nextSteps = workflowStepDao.selectStepByWfwIdStep(parmsMap);
                    if (!CollectionUtils.isEmpty(nextSteps)){
                        WorkflowStep nextStep = nextSteps.get(0);
                        nextStep.setApprAdvice("0");

                        //生成nextStep的 roleId 与 deptId
                        if (!"4".equals(nextStep.getDuty())){// 下一步需要的审批人职位duty不是局长4
                            List<Role> roles = roleService.selectRoleByDidADuty(nowStep.getDeptId(), nextStep.getDuty());
                            if (!CollectionUtils.isEmpty(roles)){
                                Role role = roles.get(0);
                                nextStep.setRoleId(role.getId());
                                nextStep.setDeptId(nowStep.getDeptId());
                            }
                        }
                        if ("4".equals(nextStep.getDuty())){// 下一步需要的审批人职位duty不是局长4
                            List<Role> roleJZs = roleDao.getRoleByNTD("局长办公室", "1", "4");
                            if (!CollectionUtils.isEmpty(roleJZs)){
                                Role roleJZ = roleJZs.get(0);
                                nextStep.setRoleId(roleJZ.getId());
                                nextStep.setDeptId(roleJZ.getDeptid());
                            }
                        }
                        // 更新nextStep
                        workflowStepService.updateById(nextStep);
                    }
                }
            }
        } catch (Exception e) {
            reBool = false;
            RecordLogTools.error("apprNormalflow审批一般流程失败",e);
        }
        return reBool;
    }

    /**
     * 审批收文审批流程
     */
    @Override
    public Boolean apprShouWenflow(String userFPDTOsStr,Integer workflowId, String step,String step2Type) {
        // 处理userFPDTOsStr
        List<UserFPDTO> userFPDTOs = escapUserSWDTOS(userFPDTOsStr);
        // 审批意见:0.未审批;1.不同意;2.同意; 3.收文办结;
        Boolean reBool = true;
        step = Integer.parseInt(step) -1 +"";
        if (!"5".equals(step) && !CollectionUtils.isEmpty(userFPDTOs)){// 不是最后一步，即不是科员办结步骤
            // Workflow 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
            Map<String, Object> parmsMap = new HashMap<>();
            // 由于封装原因，流程step需减一

            // 获取当前步骤
            parmsMap.put("workflowId",workflowId);
            Integer userId = ShiroKit.getUser().getId();
            parmsMap.put("userId",userId);
            List<WorkflowStep> workflowNowStepsBySW = workflowStepDao.selectWfwStepByWfwIdAUid(parmsMap);
            // Workflow 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
            // 处理当前步骤
            try {
                if (!CollectionUtils.isEmpty(workflowNowStepsBySW)){
                    WorkflowStep nowStep = workflowNowStepsBySW.get(0);
                    // 需要做的1、生成apprTime、apprAdvice、fenpeiUsers
                    Date date = new Date();
                    nowStep.setApprAdvice("2");
                    nowStep.setApprTime(date);

                    // 不是第一步设置创建时间
                    if (!"1".equals(nowStep.getStep())){
                        nowStep.setCreateTime(date);
                    }
                    // 设置分配人,需去电最后一个逗号,
                    String fenpeiUsers = "";
                    for (UserFPDTO userFPDTO : userFPDTOs) {
                        fenpeiUsers += userFPDTO.getUserId()+",";
                    }
                    nowStep.setFenpeiUsers(fenpeiUsers.substring(0,fenpeiUsers.length()-1));
                    // 更新nowstep
                    workflowStepService.updateById(nowStep);

                    // 处理下一步骤
                    //不是最后一步 2、生成下一步流程的待审批userId 与 deptId、审批状态apprAdvice置为0待审批
                    parmsMap.clear();
                    parmsMap.put("workflowId",workflowId);
                    parmsMap.put("step",Integer.parseInt(step)+1+"");
                    if ("FJZ".equals(step2Type)){
                        parmsMap.put("step",Integer.parseInt(step)+2+"");
                    }
                    List<WorkflowStep> nextSteps = workflowStepDao.selectSWswGpSnNullOrFUYIStepByWfwIdStep(parmsMap);
                    if (!CollectionUtils.isEmpty(nextSteps) && !CollectionUtils.isEmpty(userFPDTOs)){
                        WorkflowStep nextStep = nextSteps.get(0);
                        if ("4".equals(nextStep.getDuty())){
                            nextStep.setApprAdvice("0");
                            for (UserFPDTO userFPDTO : userFPDTOs) {
                                nextStep.setUserId(userFPDTO.getUserId());
                                nextStep.setUserName(userFPDTO.getUserName());
                                workflowStepService.updateById(nextStep);
                            }
                        }
                        if ("3".equals(nextStep.getDuty())){
                            for (int i = 0; i < userFPDTOs.size(); i++) {
                                if (i == 0){
                                    nextStep.setApprAdvice("0");
                                    nextStep.setUserId(userFPDTOs.get(0).getUserId());
                                    nextStep.setUserName(userFPDTOs.get(0).getUserName());
                                    nextStep.setSwGpSn(nextStep.getId()+"");
                                    workflowStepService.updateById(nextStep);
                                }else{
                                    WorkflowStep FJZNextStep = new WorkflowStep();
                                    BeanUtils.copyProperties(nextStep,FJZNextStep);
                                    FJZNextStep.setUserId(userFPDTOs.get(i).getUserId());
                                    FJZNextStep.setApprAdvice("0");
                                    FJZNextStep.setUserName(userFPDTOs.get(i).getUserName());
                                    FJZNextStep.setId(null);// 重置id
                                    workflowStepService.insert(FJZNextStep);
                                    FJZNextStep.setSwGpSn(FJZNextStep.getId()+"");
                                    workflowStepService.updateById(FJZNextStep);
                                }
                            }

                        }
                        if ("2".equals(nextStep.getDuty()) || "1".equals(nextStep.getDuty())){
                            for (UserFPDTO userFPDTO : userFPDTOs) {
                                WorkflowStep KZKYNextStep = new WorkflowStep();
                                BeanUtils.copyProperties(nextStep,KZKYNextStep);
                                KZKYNextStep.setUserId(userFPDTO.getUserId());
                                KZKYNextStep.setApprAdvice("0");
                                KZKYNextStep.setDeptId(userFPDTO.getDeptId());
                                KZKYNextStep.setUserName(userFPDTO.getUserName());
                                KZKYNextStep.setId(null);// 重置id
                                workflowStepService.insert(KZKYNextStep);
                                if ("2".equals(nextStep.getDuty())){// 科长的SwGpSn封装
                                    KZKYNextStep.setSwGpSn(nowStep.getId()+","+KZKYNextStep.getId());
                                }
                                if ("1".equals(nextStep.getDuty())){// 科员的SwGpSn封装
                                    KZKYNextStep.setSwGpSn(nowStep.getSwGpSn());
                                }
                                workflowStepService.updateById(KZKYNextStep);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                reBool = false;
                RecordLogTools.error("apprNormalflow审批一般流程失败",e);
            }
        }

        return reBool;
    }

    /**
     * 是否有该流程的审批权限
     */
    @Override
    public Boolean isCanAppr(Integer workflowId) {
        Boolean reBool = false;
        // 如果主体流程已经结束，即不在审批状态直接没有审批权限
        Workflow workflow = workflowService.selectById(workflowId);
        // 审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(必须通过);6.已撤销;
        if (ToolUtil.isNotEmpty(workflow) && !"1".equals(workflow.getStatus()) && !"4".equals(workflow.getStatus())){
            return false;
        }else{
            List<WorkflowStep> workflowSteps = super.selectList(new EntityWrapper<WorkflowStep>()
                    .eq("workflowId", workflowId)
                    .and()
                    .eq("apprAdvice", "0"));
            if (!CollectionUtils.isEmpty(workflowSteps)){
                String type = workflow.getType();
                if (Constant.OA_SHOWWEN_TYPE.equals(type)){// 收文审批，并没有roleId，通过账户id来判断
                    for (WorkflowStep swAuthStep : workflowSteps) {
                        Integer userId = ShiroKit.getUser().getId();
                        if (null != swAuthStep.getUserId() && (int)userId == (int)swAuthStep.getUserId()){
                            reBool = true;
                            break;
                        }
                    }
                }else{// 一般审批，通过登录账号角色是否包含任一步骤 待审批角色判断
                    WorkflowStep authStep = workflowSteps.get(0);
                    List<Integer> roleList = ShiroKit.getUser().getRoleList();
                    if (roleList.contains(authStep.getRoleId())){
                        reBool = true;
                    }
                }

            }

        }
        return reBool;
    }

    @Override
    public SWFPUsersDTO getFenpeiUsers(Integer workflowId, String step) {
        SWFPUsersDTO swfpUsersDTO = new SWFPUsersDTO();
        swfpUsersDTO.setNowStep(step);
        // 第二步,bgszr分配人 --> 局长或副局长的集合,副局长，局长均不设置deptId
        if ("2".equals(step)){
            // 副局长
            List<UserFPDTO> fjzSWFPUserS = getSWFPUserByTipsAndDuty(workflowId,"1", "3");
            swfpUsersDTO.setFjzSWFPUsers(fjzSWFPUserS);
            // 局长
            List<UserFPDTO> jzSWFPUsers = getSWFPUserByTipsAndDuty(workflowId,"1", "4");
            swfpUsersDTO.setJzSWFPUsers(jzSWFPUsers);
        }
        // 第三步,局长分配人 --> 副局长的集合, 不设置deptId
        if ("3".equals(step)){
            // 副局长
            List<UserFPDTO> fjzSWFPUserS = getSWFPUserByTipsAndDuty(workflowId,"1", "3");
            swfpUsersDTO.setCommSWFPUsers(fjzSWFPUserS);
        }
        // 第四步,副局长分配人 --> 副局长所分管科室下的所有科长账号 可选
        if ("4".equals(step)){
            List<UserFPDTO> fpKZUsers = getFJZSWFPUsers(workflowId);
            swfpUsersDTO.setCommSWFPUsers(fpKZUsers);

        }
        // 第五步,科长分配人 --> 该科长所在该科室的所有科员可选
        if ("5".equals(step)){
            List<UserFPDTO> fpKYUsers = getKZSWFPUsers(workflowId);
            swfpUsersDTO.setCommSWFPUsers(fpKYUsers);
        }
        return swfpUsersDTO;
    }

    @Override
    public List<BJDetailDTO> getBanjieDetail(Integer workflowId) {
        List<BJDetailDTO> reBjDetailDTOs = new ArrayList<BJDetailDTO>();
        // 获取当前步骤
        if (ShiroKit.isSWGD()){
            Map<String, Object> bjParmsMap = new HashMap<>();
            bjParmsMap.put("workflowId",workflowId);
            bjParmsMap.put("swGpSn","-1");
            reBjDetailDTOs =  workflowStepDao.getBanjieDetail(bjParmsMap);
        }else{
            Map<String, Object> parmsMap = new HashMap<>();
            parmsMap.put("workflowId",workflowId);
            Integer userId = ShiroKit.getUser().getId();
            parmsMap.put("userId",userId);
            List<WorkflowStep> workflowNowStepsBySW = workflowStepDao.selectWfwStepByWfwIdAUid(parmsMap);
            if (!CollectionUtils.isEmpty(workflowNowStepsBySW)){

                Map<String, Object> bjParmsMap = new HashMap<>();
                bjParmsMap.put("workflowId",workflowId);
                bjParmsMap.put("swGpSn",workflowNowStepsBySW.get(0).getSwGpSn());
                reBjDetailDTOs =  workflowStepDao.getBanjieDetail(bjParmsMap);
            }
        }
        return reBjDetailDTOs;
    }

    private void escapAttms( List<ShenPiWorkflowDTO> shenPiFlows){
        for (ShenPiWorkflowDTO shenPiWfd : shenPiFlows){
            List<WorkFlowAttachmentDTO> workFlowAttachmentDTOS = workflowAttachmentDao.selectByWfwId(shenPiWfd.getWorkflowId());
            shenPiWfd.setAttachments(workFlowAttachmentDTOS);
        }
    }
    private void escapOrdnumSW( List<ShenPiWorkflowDTO> shenPiFlows){
        for (ShenPiWorkflowDTO shenPiWfd : shenPiFlows){
            if (Constant.OA_SHOWWEN_TYPE.equals(shenPiWfd.getType())){
                List<WorkflowProperty> swPros = workflowPropertyDao.selectProByWfwId(shenPiWfd.getWorkflowId());
                if (!CollectionUtils.isEmpty(swPros)){
                    shenPiWfd.setOrdnumSW(swPros.get(0).getValue());
                }
            }
        }
    }

   private Boolean isFlowEnd(Workflow workflow){
        Boolean reBool = false;
        // 审批状态:1.审批中;2.审批未通过;3.审批通过;
        if ("3".equals(workflow.getStatus())){
            reBool = true;
        }
        return reBool;
   }

   private void updateWorkFlowStatus(Integer workflowId,String status,Date endTime){
       Workflow workflow = workflowService.selectById(workflowId);
       workflow.setStatus(status);
       workflow.setEndTime(endTime);
       workflowService.updateById(workflow);
   }

   private NormalDetailFlowsDTO escapFlowsLine(List<NormalFlowDTO> normalFlowDTOS){
       NormalDetailFlowsDTO reNorFlowsDTO = new NormalDetailFlowsDTO();
       for (NormalFlowDTO flowDTO : normalFlowDTOS) {
           if (flowDTO.getSpAdvice() != null && flowDTO.getSpAdvice().indexOf("##不同意##") > 0){
               reNorFlowsDTO.setLineType("不同意");
               reNorFlowsDTO.setLineStep(flowDTO.getStep());
               return reNorFlowsDTO;
           }
           if (flowDTO.getSpAdvice() != null && flowDTO.getSpAdvice().indexOf("##同意##") > 0){
               reNorFlowsDTO.setLineType("同意");
               reNorFlowsDTO.setLineStep(flowDTO.getStep());
           }
           if (flowDTO.getSpAdvice() != null && flowDTO.getSpAdvice().indexOf("##0##") > 0){
               reNorFlowsDTO.setLineType("待审批");
               reNorFlowsDTO.setLineStep(flowDTO.getStep());
               return reNorFlowsDTO;
           }
       }
       
       return reNorFlowsDTO;
   }
    private SWDetailFlowsDTO escapShouWenFlowsLine( List<SWFlowDTO> swFlowDTOS){
        SWDetailFlowsDTO reSWFlowsDTO = new SWDetailFlowsDTO();
        for (SWFlowDTO flowDTO : swFlowDTOS) {
         /*   if (flowDTO.getSpAdvice() != null && flowDTO.getSpAdvice().indexOf("##不同意##") > 0){
                reSWFlowsDTO.setLineType("不同意");
                reSWFlowsDTO.setLineStep(flowDTO.getStep());
                return reSWFlowsDTO;
            }*/
            if (flowDTO.getSpAdvice() != null && flowDTO.getSpAdvice().indexOf("##同意##") > 0){
                reSWFlowsDTO.setLineType("同意");
                reSWFlowsDTO.setLineStep(flowDTO.getStep());
                if ("5".equals(flowDTO.getStep())){
                    reSWFlowsDTO.setLineStep("6");
                }
            }
            if (flowDTO.getSpAdvice() != null && flowDTO.getSpAdvice().indexOf("##0##") > 0){
                reSWFlowsDTO.setLineType("待审批");
                reSWFlowsDTO.setLineStep(flowDTO.getStep());
                return reSWFlowsDTO;
            }
        }

        return reSWFlowsDTO;
    }
    private void escapUserFlowJueSe(Integer workflowId,Map<String, Object> parmsMap){
        if (ShiroKit.isSWGD()){// 发起页  swgd查看收文审批详情
            parmsMap.put("ownFlowPos","");
            return;
        }
       //通过 workflowId 和 userId确定用户在某一收文审批中的流程唯一那一步步骤
        Map<String, Object> parmasMap = new HashMap<>();
        parmasMap.put("workflowId",workflowId);
        Integer userId = ShiroKit.getUser().getId();
        parmasMap.put("userId",userId);
        List<WorkflowStep> wfwUserStepList = workflowStepDao.selectWfwStepByWfwIdAUid(parmasMap);
        if (!CollectionUtils.isEmpty(wfwUserStepList)){
            WorkflowStep wfwUserStep = wfwUserStepList.get(0);
            String ownDuty = wfwUserStep.getDuty();
            String swGpSn = wfwUserStep.getSwGpSn();
            if ("2".equals(ownDuty) && !"-1".equals(wfwUserStep.getSwGpSn())){// 除特定的bgszr外的科长
                parmsMap.put("KZswGpSn",swGpSn);
                if (StringUtils.hasText(swGpSn)){
                    parmsMap.put("FJZswGpSn",swGpSn.split(",")[0]);
                }
                parmsMap.put("ownFlowPos","isKZ");
                return;
            }
            if ("3".equals(ownDuty)){// 副局长
                parmsMap.put("FJZswGpSn",swGpSn);
                parmsMap.put("ownFlowPos","isFJZ");
                FJZhasAnyKZflowsExsit(workflowId,parmsMap);
                return ;
            }
            if ("4".equals(ownDuty) || ("2".equals(ownDuty) && "-1".equals(wfwUserStep.getSwGpSn()))){
                parmsMap.put("ownFlowPos","");
                return;
            }
        }
    }
    private void hasAnyKZflowsExsit(Integer workflowId,Map<String, Object> parmsMap){
        if ("".equals(parmsMap.get("ownFlowPos"))){
            Map<String, Object> parmsMapCondition = new HashMap<>();
            parmsMapCondition.put("workflowId",workflowId);
            parmsMapCondition.put("step","4");
            List<WorkflowStep> workflowSteps = workflowStepDao.selectStepByWfwIdStep(parmsMapCondition);
            if (!CollectionUtils.isEmpty(workflowSteps)){
                for (WorkflowStep workflowStep : workflowSteps) {
                    if (workflowStep.getUserId()!= null){
                        parmsMap.put("hasAnyKZflowsExsit","true");
                        break;
                    }
                }
            }
        }
    }

    private void FJZhasAnyKZflowsExsit(Integer workflowId,Map<String, Object> parmsMap){
        Map<String, Object> parmsMapCondition = new HashMap<>();
        parmsMapCondition.put("workflowId",workflowId);
        parmsMapCondition.put("userId",ShiroKit.getUser().getId());
        List<WorkflowStep> thisFJZworkflowSteps = workflowStepDao.selectWfwStepByWfwIdAUid(parmsMapCondition);
        if (!CollectionUtils.isEmpty(thisFJZworkflowSteps)){
            WorkflowStep hisFJZWorkflowStep = thisFJZworkflowSteps.get(0);
            parmsMapCondition.put("workflowId",workflowId);
            parmsMapCondition.put("step","4");
            List<WorkflowStep> KZworkflowSteps = workflowStepDao.selectStepByWfwIdStep(parmsMapCondition);
            if (!CollectionUtils.isEmpty(KZworkflowSteps)){
                for (WorkflowStep KZworkflowStep : KZworkflowSteps) {
                    if (KZworkflowStep.getUserId()!= null && KZworkflowStep.getSwGpSn() != null
                            && (","+KZworkflowStep.getSwGpSn()+",").indexOf(hisFJZWorkflowStep.getSwGpSn()) != -1){
                        parmsMap.put("FJZhasAnyKZflowsExsit","true");
                        break;
                    }
                }
            }
        }
    }

    private  List<UserFPDTO> getSWFPUserByTipsAndDuty(Integer workflowId,String tips,String duty){
        List<Integer> TDRoleIds = roleDao.getRoleByTipsDuty(tips, duty);
        if (!CollectionUtils.isEmpty(TDRoleIds)){
            List<Integer> SWFPUserIds = new ArrayList<Integer>();
            for (Integer TDRoleId : TDRoleIds) {
                List<Integer> tempFpUserIds = userDao.selectContainsUsersByRoleId(TDRoleId);
                SWFPUserIds.addAll(tempFpUserIds);
            }
            // 去掉流程里面已存在的userId，因为一个收文流程里面一个账号只会出现一次
            List<Integer> oneSWExsitUserIds = workflowStepDao.selectOneSWExsitUserIds(workflowId);
            SWFPUserIds.removeAll(oneSWExsitUserIds);
            if (!CollectionUtils.isEmpty(SWFPUserIds)){
                List<UserFPDTO> userFPDTOS = userDao.selectBatchQcUsers(SWFPUserIds);
                return userFPDTOS;
            }
        }
        return null;
    }

    private List<UserFPDTO> getFJZSWFPUsers(Integer workflowId){
        // 先筛选出该账号的所有角色中是副局长的
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        List<Integer> ownKZRoleIds = new ArrayList<Integer>();
        for (Integer roleId : roleList) {
            Role role = roleService.selectById(roleId);
            if (ToolUtil.isNotEmpty(role) && "3".equals(role.getDuty())){
                List<Role> KZroles = roleDao.selectRoleByDidADuty(role.getDeptid(), "2");
                if (!CollectionUtils.isEmpty(KZroles)){
                    ownKZRoleIds.add(KZroles.get(0).getId());
                }
            }
        }
        if (!CollectionUtils.isEmpty(ownKZRoleIds)){
            List<Integer> SWFPtUserIds = new ArrayList<Integer>();
            for (Integer TDRoleId : ownKZRoleIds) {
                List<Integer> tempFpUserIds = userDao.selectContainsUsersByRoleId(TDRoleId);
                SWFPtUserIds.addAll(tempFpUserIds);
            }
            // 去掉流程里面已存在的userId，因为一个收文流程里面一个账号只会出现一次
            List<Integer> oneSWExsitsUserIds = workflowStepDao.selectOneSWExsitUserIds(workflowId);
            SWFPtUserIds.removeAll(oneSWExsitsUserIds);
            if (!CollectionUtils.isEmpty(SWFPtUserIds)){
                List<UserFPDTO> userFPDTOS = userDao.selectBatchQcUsers(SWFPtUserIds);
                // 封装deptid
                for (UserFPDTO userFPDTO : userFPDTOS) {
                    User user = userService.selectById(userFPDTO.getUserId());
                    Integer[] roleArray = Convert.toIntArray(user.getRoleid());// 角色集合
                    for (int roleId : roleArray) {
                        // update by lgg start
                        // 加入role的时候先查询该role是否还存在
                        Role role = roleService.selectById(roleId);
                        if (role != null){
                            if (ToolUtil.isNotEmpty(role) && "2".equals(role.getDuty())){
                                userFPDTO.setDeptId(role.getDeptid());
                                break;
                            }
                        }
                    }
                }
                return userFPDTOS;
            }
        }
        return null;
    }

    private List<UserFPDTO> getKZSWFPUsers(Integer workflowId){
        Map<String, Object> parmasMap = new HashMap<>();
        Integer userId = ShiroKit.getUser().getId();
        parmasMap.put("userId",userId);
        parmasMap.put("workflowId",workflowId);
        List<WorkflowStep> workflowSteps = workflowStepDao.selectWfwStepByWfwIdAUid(parmasMap);
        if (!CollectionUtils.isEmpty(workflowSteps)){
            WorkflowStep fiveWfwStep = workflowSteps.get(0);
            Integer KZdeptId = fiveWfwStep.getDeptId();
            List<Integer> SWFPtUserIds = null;
            // 找到该科长对应的科员的角色id
            List<Role> roles = roleDao.selectRoleByDidADuty(KZdeptId, "1");
            if (!CollectionUtils.isEmpty(roles)){
                SWFPtUserIds = userDao.selectContainsUsersByRoleId(roles.get(0).getId());
            }

            // 去掉流程里面已存在的userId，因为一个收文流程里面一个账号只会出现一次
            List<Integer> oneSWExsitsUserIds = workflowStepDao.selectOneSWExsitUserIds(workflowId);
            if (!CollectionUtils.isEmpty(SWFPtUserIds)){
                SWFPtUserIds.removeAll(oneSWExsitsUserIds);
                if (!CollectionUtils.isEmpty(SWFPtUserIds)){
                    List<UserFPDTO> userFPDTOS = userDao.selectBatchQcUsers(SWFPtUserIds);
                    // 封装deptid
                    for (UserFPDTO userFPDTO : userFPDTOS) {
                        userFPDTO.setDeptId(KZdeptId);//与科长相同即可
                    }
                    return userFPDTOS;
                }
            }
        }
        return null;
    }

    private List<UserFPDTO> escapUserSWDTOS(String userFPDTOsStr){
        if (StringUtils.hasText(userFPDTOsStr)){
            List<UserFPDTO> userFPDTOSList = new ArrayList<>();
            JSONArray userFPDTOsArry = (JSONArray)JSONArray.parse(userFPDTOsStr);
            for (int i = 0; i < userFPDTOsArry.size(); i++) {
                JSONObject jsonObj = (JSONObject) JSON.toJSON(userFPDTOsArry.get(i));
                UserFPDTO userFPDTO = JSON.toJavaObject(jsonObj, UserFPDTO.class);
                userFPDTOSList.add(userFPDTO);
            }
            return userFPDTOSList;
        }
        return null;
    }

    private void  setUpWorkflowRead(Integer workflowId){
        Workflow workflow = workflowService.selectById(workflowId);
        if (ToolUtil.isNotEmpty(workflow)){
            if ("7".equals(workflow.getType()) || "8".equals(workflow.getType())){// 发文
                WorkflowRead workflowRead = new WorkflowRead();
                Integer swgdUserId = userDao.selectUserByAccount(Constant.SWGD);
                workflowRead.setUserId(swgdUserId);
                workflowRead.setWorkflowId(workflowId);
                workflowRead.setReadStatus("0");
                workflowRead.setUserAccount(Constant.SWGD);
                workflowReadService.insert(workflowRead);
            }else{
                WorkflowRead workflowRead = new WorkflowRead();
                Integer bgszrUserId = userDao.selectUserByAccount(Constant.BGSZR);
                workflowRead.setUserId(bgszrUserId);
                workflowRead.setWorkflowId(workflowId);
                workflowRead.setReadStatus("0");
                workflowRead.setUserAccount(Constant.BGSZR);
                workflowReadService.insert(workflowRead);
            }
        }


    }
}
