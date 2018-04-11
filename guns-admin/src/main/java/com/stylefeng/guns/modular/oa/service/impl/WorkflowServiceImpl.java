package com.stylefeng.guns.modular.oa.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.dao.UserMapper;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.oa.dao.WorkflowAttachmentDao;
import com.stylefeng.guns.modular.oa.dao.WorkflowDao;
import com.stylefeng.guns.modular.oa.dao.WorkflowStepDao;
import com.stylefeng.guns.modular.oa.dto.*;
import com.stylefeng.guns.modular.oa.service.*;
import com.stylefeng.guns.modular.system.dao.DeptDao;
import com.stylefeng.guns.modular.system.dao.RoleDao;
import com.stylefeng.guns.modular.system.service.IDeptService;
import com.stylefeng.guns.modular.system.service.IRoleService;
import com.stylefeng.guns.modular.system.service.IUserService;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * OA管理Service
 *
 * @author lgg
 * @Date 2017-12-04 12:59:20
 */
@Service
public class WorkflowServiceImpl extends ServiceImpl<BaseMapper<Workflow>,Workflow> implements IWorkflowService {
    @Autowired
    private WorkflowDao workflowDao;
    @Autowired
    private WorkflowAttachmentDao workflowAttachmentDao;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IWorkflowAttachmentService attachmentService;
    @Autowired
    private IWorkflowPropertyService workflowPropertyService;
    @Autowired
    private IWorkflowStepService workflowStepService;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private IWorkflowConfigService workflowConfigService;
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private WorkflowStepDao workflowStepDao;
    @Override
    public Page<WorkflowDTO> list(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<WorkflowDTO> page = new PageFactory<WorkflowDTO>().defaultPage();
        parasMapE.put("userId", ShiroKit.getUser().getId());// 根据发起人id查询其所发起的oa

        List<WorkflowDTO> workFlows =null;
        if (ShiroKit.isAdmin()){// 超级管理员
            workFlows = workflowDao.adminList(null,page,parasMapE);
        }else{// 一般账号
            workFlows = workflowDao.list(null,page,parasMapE);
        }
        // 封装附件
        escapAttms(workFlows);
        // 请假审批 封装销假按钮
        escapXiaojiaBtn(workFlows);
        page.setRecords(workFlows);
        return page;
    }

    @Override
    public Page<BanjieWorkflowDTO> banjieList(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        parasMapE.put("userId", ShiroKit.getUser().getId());
        Page<BanjieWorkflowDTO> page = new PageFactory<BanjieWorkflowDTO>().defaultPage();
        List<BanjieWorkflowDTO> banjieFlows =  workflowDao.banjieList(null,page,parasMapE);

        // 封装附件
        escapBanjieAttms(banjieFlows);

        page.setRecords(banjieFlows);
        return page;
    }

    @Override
    public List<FaqiRoleDTO> selectApprRole(String type) {

        Integer uid = ShiroKit.getUser().getId();
        List<Role> ownRoles = workflowDao.getFaqiRole(uid);
        List<Role> apprRoles = new ArrayList<>();
        if (!CollectionUtils.isEmpty(ownRoles) && !ShiroKit.isAdmin()){
            Boolean flag = false;
            for (Role ro : ownRoles){
                if (!"3".equals(ro.getDuty()) &&!"".equals(ro.getDuty())){// 获取科员、科长的上一级角色
                    Role role = roleService.selectOne(new EntityWrapper<Role>().
                            eq("deptid", ro.getDeptid()).and()
                            .eq("duty", Integer.parseInt(ro.getDuty()) + 1 + ""));
                    if (ToolUtil.isNotEmpty(role)){
                        apprRoles.add(role);
                    }
                }
                if ("3".equals(ro.getDuty())){// 获取副局长的上一级角色，即局长
                    List<Dept> depts = deptService.getDeptByNT("局长办公室","1");
                    if (!CollectionUtils.isEmpty(depts) && !flag){
                        /*List<Role> roles = roleService.selectList(new EntityWrapper<Role>().eq("deptid", depts.get(0).getId())
                                .and().eq("duty", "4"));*/
                        List<Role> roles = roleService.selectRoleByDidADuty(depts.get(0).getId(),"4");
                        if (!CollectionUtils.isEmpty(roles)){
                            flag = true;
                            apprRoles.add(roles.get(0));
                        }
                    }

                }
            }

        }
        // 收文审批需要再过滤，选择收文审批，审批人只能为 郫都区投促局-办公室-科长 对应的账号 确定唯一
        if (Constant.OA_SHOWWEN_TYPE.equals(type)){
            // 郫都区投促局-办公室-科长

            List<User> bgsKZs = userService.getUserByNTD("办公室", "1", "2");
            apprRoles.clear();
            if (!CollectionUtils.isEmpty(bgsKZs)){
                Role bgsKZRole = new Role();
                bgsKZRole.setId(bgsKZs.get(0).getId());
                bgsKZRole.setName(bgsKZs.get(0).getName());
                apprRoles.add(bgsKZRole);
            }
/*            apprRoles = apprRoles.stream().filter(p -> Constant.ROLE_KEZHANG.equals(p.getDuty()) &&
                    (deptService.selectCount(new EntityWrapper<Dept>().eq("simplename", "办公室")
                            .and().eq("id", p.getDeptid()))) > 0
            ).collect(Collectors.toList());
            // 对应的账号
                if (!CollectionUtils.isEmpty(apprRoles)){
                    //根据角色id获取办公室科长的所用账号
                    List<User> users = workflowDao.selectOfficeKZs(apprRoles.get(0).getId());
                    List<User> jxyKZs = users.stream().filter(p -> "jiangxiaoying".equals(p.getAccount())).collect(Collectors.toList());
                    apprRoles.clear();

                    if (!CollectionUtils.isEmpty(jxyKZs)){
                        Role jxy = new Role();
                        jxy.setId(jxyKZs.get(0).getId());
                        jxy.setName("江小映");
                        apprRoles.add(jxy);
                    }
                }*/


        }

        // 选择发文审批，审批人必须为科长
        if (Constant.OA_FAWEN_TYPE.equals(type)){
            apprRoles = apprRoles.stream().filter(p -> Constant.ROLE_KEZHANG.equals(p.getDuty())).collect(Collectors.toList());
        }

        List<FaqiRoleDTO> faqiRoleDTOs = apprRoles.stream().map(p -> {
            FaqiRoleDTO faqiRoleDTO = new FaqiRoleDTO();
            BeanUtils.copyProperties(p, faqiRoleDTO);
            return faqiRoleDTO;
        }).collect(Collectors.toList());
        return faqiRoleDTOs;
    }

    @Override
    public Boolean insertWorkflow(Map<Object, Object> map) {
        Boolean rebool = false;
        try {
            String type = (String)map.get("typeFQ");//  审批类型
            // 一般审批：roleIdFQ 为角色id  收文审批：roleIdFQ为 郫都区投促局-办公室-科长 对应的账号的id，通用一个
            Integer roleId = Integer.parseInt(map.get("roleIdFQ")+"") ;// 审批人

            Workflow workflow = new Workflow();
            Date date = new Date();
            workflow.setName(switchWorkflowName(type));
            workflow.setType(type);
            workflow.setApprTime(date);
            workflow.setCreateTime(date);
            //审批状态:1.审批中;2.审批未通过;3.审批通过;4办结中;5已办结(办结成功);6办结失败7.已撤销;
            workflow.setStatus("1");// 初始化为审批中
            workflow.setUserId(ShiroKit.getUser().getId());
            workflow.setUserName(ShiroKit.getUser().getName());

            super.insert(workflow);// 添加流程
            Integer workflowId = workflow.getId();

            if (Constant.OA_QINGJIA_TYPE.equals(map.get("typeFQ"))){//添加请假审批,使用WorkflowProperty存储额外属性
             /* map.get("qjTimeB"); map.get("qjAMPMB");map.get("qjTimeE");
                map.get("qjAMPME");map.get("qjTotalDays");*/
                insertWorkflowProperty("qjTimeB", (String)map.get("qjTimeB"),workflowId,type);
                insertWorkflowProperty("qjAMPMB", (String)map.get("qjAMPMB"),workflowId,type);
                insertWorkflowProperty("qjTimeE", (String)map.get("qjTimeE"),workflowId,type);
                insertWorkflowProperty("qjAMPME", (String)map.get("qjAMPME"),workflowId,type);
                insertWorkflowProperty("qjTotalDays", (String)map.get("qjTotalDays"),workflowId,type);
            }
            if (Constant.OA_SHOWWEN_TYPE.equals(map.get("typeFQ"))){// 添加发文审批,使用WorkflowProperty存储这个属性
                String ordnumSW = (String)map.get("ordnumSW");
                insertWorkflowProperty("ordnumSW",ordnumSW,workflowId,type);
            }

            // 生成审批编号
            workflow.setOrdnum("1"+workflowId);

            // 通过审批人确定 发起人的角色
            Integer faqiRoleId = escapFaqiRole(roleId,type);
            workflow.setRoleId(faqiRoleId);

            // 通过审批人确定 发起人的科室
            Role role = roleService.selectById(faqiRoleId);
            if (ToolUtil.isNotEmpty(role)){
                workflow.setDeptId(role.getDeptid());
            }


            super.updateById(workflow);// 更新生成发起人roleId与审批编号
            // 生成下一步流程的第一步
            Boolean stepBool = escapWorkflowStepFirst(workflowId, roleId, date, type);
            
            // 初始化第二到end流程
            escapWorkflowStepSecondToEnd(workflowId,faqiRoleId,type);
            // 处理附件
            String attachments = (String)map.get("attachments");
            Boolean attmBool = attachmentService.addAttachments(attachments, workflowId);


            if (stepBool && attmBool){
                rebool = true;
            }
        } catch (Exception e) {
            RecordLogTools.error("发起审批失败",e);
        }
        return rebool;
    }

    /**
     * 审批记录
     * @param workflowId
     * @return
     */
    @Override
    public List<WorkflowStepDTO> getApprRecord(Integer workflowId) {
        // 一般审批记录

        // 收文审批记录
        return null;
    }

    @Override
    public Page<GuidangWorkflowDTO> bgszrGuidangList(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<GuidangWorkflowDTO> page = new PageFactory<GuidangWorkflowDTO>().defaultPage();
        parasMapE.put("isBGSZR","isBGSZR");
        List<GuidangWorkflowDTO> guidangWorkFlows = workflowDao.guidangList(null,page,parasMapE);

        // 封装附件
        escapGuidangAttms(guidangWorkFlows);
        // 请假审批 封装销假按钮
        escapGuidangXiaojiaBtn(guidangWorkFlows);
        page.setRecords(guidangWorkFlows);
        return page;
    }

    @Override
    public Page<GuidangWorkflowDTO> swgdGuidangList(Map<String, Object> map) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(map);
        Page<GuidangWorkflowDTO> page = new PageFactory<GuidangWorkflowDTO>().defaultPage();
        parasMapE.put("isSWGD","isSWGD");
        List<GuidangWorkflowDTO> guidangWorkFlows = workflowDao.guidangList(null,page,parasMapE);

        // 封装附件
        escapGuidangAttms(guidangWorkFlows);
        page.setRecords(guidangWorkFlows);
        return page;
    }
    private void escapAttms( List<WorkflowDTO> workFlows){
        for (WorkflowDTO wfd : workFlows){
            List<WorkFlowAttachmentDTO> workFlowAttachmentDTOS = workflowAttachmentDao.selectByWfwId(wfd.getId());
            wfd.setAttachments(workFlowAttachmentDTOS);
        }
    }
    private void escapBanjieAttms( List<BanjieWorkflowDTO> banjieFlows){
        for (BanjieWorkflowDTO banjieFlow : banjieFlows){
            List<WorkFlowAttachmentDTO> workFlowAttachmentDTOS = workflowAttachmentDao.selectByWfwId(banjieFlow.getWorkflowId());
            banjieFlow.setAttachments(workFlowAttachmentDTOS);
        }
    }
    private void escapXiaojiaBtn(List<WorkflowDTO> workFlows){
        for (WorkflowDTO workFlow : workFlows) {
            if (Constant.OA_QINGJIA_TYPE.equals(workFlow.getType())){
                String xiaojiaBtn = workflowDao.selectXiaojBtnByWfwId(workFlow.getId());
                workFlow.setXiaojBtn(xiaojiaBtn);
            }
        }
    }

    private void escapGuidangAttms( List<GuidangWorkflowDTO> guidangWorkFlows){
        for (GuidangWorkflowDTO guidangWfd : guidangWorkFlows){
            List<WorkFlowAttachmentDTO> workFlowAttachmentDTOS = workflowAttachmentDao.selectByWfwId(guidangWfd.getId());
            guidangWfd.setAttachments(workFlowAttachmentDTOS);
        }
    }
    private void escapGuidangXiaojiaBtn(List<GuidangWorkflowDTO> guidangWorkFlows){
        for (GuidangWorkflowDTO guidangWorkFlow : guidangWorkFlows) {
            if (Constant.OA_QINGJIA_TYPE.equals(guidangWorkFlow.getType())){
                String xiaojiaBtn = workflowDao.selectXiaojBtnByWfwId(guidangWorkFlow.getId());
                guidangWorkFlow.setXiaojBtn(xiaojiaBtn);
            }
        }
    }

    private void insertWorkflowProperty(String key,String value,Integer workflowId,String type){
        WorkflowProperty property = new WorkflowProperty();
        property.setName(key);
        property.setValue(value);
        property.setCreateTime(new Date());
        property.setWorkflowId(workflowId);
        property.setType(type);
        workflowPropertyService.insert(property);
    }
    // 生成下一步流程
    private Boolean escapWorkflowStepFirst(Integer workflowId, Integer roleId, Date newDate, String type){// 生成下一步
        WorkflowStep workflowStep = new WorkflowStep();
        workflowStep.setWorkflowId(workflowId);
        // 每一个下一步流程 初始化的时候都没有 审批人 和 审批人账号名 和 审批时间，是在审批的时候生成 审批人 和 审批人账号名 和审批时间
        workflowStep.setApprAdvice("0");// 初始化为未审批
        workflowStep.setStep("1");
        workflowStep.setCreateTime(newDate);
        workflowStep.setIsEnd("0");
        // 生成第一步的审批职位
        String duty = escapFirstDuty(roleId, type);
        workflowStep.setDuty(duty);
        if (Constant.OA_SHOWWEN_TYPE.equals(type)){
            // 收文审批：每一步流程 直接由具体人来接收审批
            // 指定审批人 如果是收文审批，roleId为userId
            Integer userId = roleId;
            List<Dept> deptBGS = deptDao.getDeptByNT(Constant.DEPT_OFFICE, "1");
            if (!CollectionUtils.isEmpty(deptBGS)){
                workflowStep.setDeptId(deptBGS.get(0).getId());
            }
            workflowStep.setUserId(userId);
            // 第1、2步需要设置swGpSn为-1，用于后续流程详情的查询
            workflowStep.setSwGpSn("-1");
            User user = userService.selectById(userId);
            if (ToolUtil.isNotEmpty(user)){
                workflowStep.setUserName(user.getName());
            }
        }else{
            // 一般审批：每一步流程 先走到角色，再由具体人来审批
            // 审批科室与角色
            workflowStep.setRoleId(roleId);
            Role role = roleService.selectById(roleId);
            if (ToolUtil.isNotEmpty(role)){
                workflowStep.setDeptId(role.getDeptid());
            }
        }



        return workflowStepService.insert(workflowStep);
    }

    private String switchWorkflowName(String type){
        String reName = "";
        // 审批类型:1.请假审批;2.销假审批;3.出差审批;4.接待审批;5.公务用车审批;6.驻点人员请假审批;7.发文审批;8.收文审批
        switch(type){
            case "1":
                reName = "请假审批";
                break;
            case "3":
                reName = "出差审批";
                break;
            case "4":
                reName = "接待审批";
                break;
            case "5":
                reName = "公务用车审批";
                break;
            case "6":
                reName = "驻点人员请假审批";
                break;
            case "7":
                reName = "发文审批";
                break;
            case "8":
                reName = "收文审批";
                break;
            default:
                break;
        }
        return reName;
    }

    /**
     * 生成 发起人角色
     */
    private Integer escapFaqiRole(Integer shenpiRoleId, String type){
        Integer reFaqiRoleId = null;
        if (Constant.OA_SHOWWEN_TYPE.equals(type)){
            // 收文审批 流程针对账号做审批
            // 发起角色 此时从前台传来roleIdFQ为 郫都区投促局-办公室-科长 对应的账号的id
            // 所以发起角色一定为办公室的科员，因为只有办公室科员有发起收文流程的权限
            List<Role> bgskyRoles = roleDao.getRoleByNTD("办公室", "1", "1");
            if (!CollectionUtils.isEmpty(bgskyRoles)){
                reFaqiRoleId = bgskyRoles.get(0).getId();
            }
        }else{// 一般审批 流程针对角色做审批
            Role shenpiRole = roleService.selectById(shenpiRoleId);
            List<Integer> ownRoleIds = ShiroKit.getUser().getRoleList();
            List<Role> ownRoles = roleService.selectBatchIds(ownRoleIds);

            // 如果审批人是局长，且发起审批账号有多个分管副局长，即以第一个 副局长角色 为 发起人角色
            try {
                if (ToolUtil.isNotEmpty(shenpiRole)){
                    if (Constant.ROLE_JUZHANG.equals(shenpiRole.getDuty())){
                        List<Role> fujuzhangRoles = ownRoles.stream().filter(p -> Constant.ROLE_FUJUZHANG.equals(p.getDuty())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(fujuzhangRoles)){
                            reFaqiRoleId = fujuzhangRoles.get(0).getId();
                        }

                    }else{// 审批人不是局长
                        String faqiDuty = Integer.parseInt(shenpiRole.getDuty())-1 +"";
                        List<Role> faqiRoles = roleService.selectList(new EntityWrapper<Role>().eq("duty", faqiDuty)
                                .and().eq("deptid", shenpiRole.getDeptid()));
                        if (!CollectionUtils.isEmpty(faqiRoles)){
                            reFaqiRoleId = faqiRoles.get(0).getId();
                        }
                    }
                }
            } catch (Exception e) {
            }
        }



        return reFaqiRoleId;
    }


    // 初始化第二到end流程
    private Boolean escapWorkflowStepSecondToEnd(Integer workflowId, Integer faqiRoleId, String type){

        // 根据流程type 与 发起人角色中的职位duty 确定流程步骤数
        Integer stepNums = enSureStepNum( workflowId,faqiRoleId, type);
        String spDuty = getStepSpDuty(workflowId, faqiRoleId, type);
        String[] spDutyArr = spDuty.split("-");
        if (Constant.OA_SHOWWEN_TYPE.equals(type)){
            // 收文审批：每一步流程 直接由具体人来接收审批
            // 指定审批人 如果是收文审批，roleId为userId
            for (int i = 0; i < stepNums - 1; i++) {
                WorkflowStep workflowStep = new WorkflowStep();
                workflowStep.setWorkflowId(workflowId);
                workflowStep.setStep(i + 2 +"");
                // 第1、2步需要设置swGpSn为-1，用于后续流程详情的查询
                if ("2".equals(workflowStep.getStep())){
                    workflowStep.setSwGpSn("-1");
                }
                workflowStep.setIsEnd("0");
                workflowStep.setDuty(spDutyArr[i + 1]);
                workflowStepService.insert(workflowStep);
            }
        }else if (Constant.OA_QINGJIA_TYPE.equals(type)){
            // 请假审批
            for (int i = 0; i < stepNums - 1; i++) {
                WorkflowStep workflowStep = new WorkflowStep();
                workflowStep.setWorkflowId(workflowId);
                workflowStep.setStep(i + 2 +"");
                workflowStep.setDuty(spDutyArr[i + 1]);
                workflowStep.setIsEnd("0");

                workflowStepService.insert(workflowStep);
            }
        }else {
            // 一般审批：每一步流程 先走到角色，再由具体人来审批
            // 审批科室与角色
            for (int i = 0; i < stepNums - 1; i++) {
                WorkflowStep workflowStep = new WorkflowStep();
                workflowStep.setWorkflowId(workflowId);
                workflowStep.setStep(i + 2 +"");
                workflowStep.setDuty(spDutyArr[i + 1]);
                workflowStep.setIsEnd("0");
                workflowStepService.insert(workflowStep);
            }
        }



        //return workflowStepService.insert(workflowStep);
        return true;
    }

    private Integer enSureStepNum(Integer workflowId,Integer faqiRoleId, String type){
        int stepNums = 0;
        // 发起人职位
        String duty = "";
        Role role = roleService.selectById(faqiRoleId);
        if (ToolUtil.isNotEmpty(role)){
            duty = role.getDuty();
        }
        if (Constant.OA_QINGJIA_TYPE.equals(type)){ // 请假类型
            type  = getGingJiaType(workflowId);
        }
        List<WorkflowConfig> workflowConfigs = workflowConfigService.selectWconfigByTD(type, duty);
        if (!CollectionUtils.isEmpty(workflowConfigs)){
            stepNums = workflowConfigs.get(0).getNum();
        }
        return stepNums;
    }

    private String getStepSpDuty(Integer workflowId,Integer faqiRoleId, String type){
        String  spDuty = "";
        // 发起人职位
        String duty = "";
        Role role = roleService.selectById(faqiRoleId);
        if (ToolUtil.isNotEmpty(role)){
            duty = role.getDuty();
        }
        if (Constant.OA_QINGJIA_TYPE.equals(type)){ // 请假类型
            type  = getGingJiaType(workflowId);
        }
        List<WorkflowConfig> workflowConfigs = workflowConfigService.selectWconfigByTD(type, duty);
        if (!CollectionUtils.isEmpty(workflowConfigs)){
            spDuty = workflowConfigs.get(0).getSpDuty();
        }
        return spDuty;
    }

    // 根据workflowId获取请假天数
    private String  getGingJiaType(Integer workflowId){
        Double qjTotalDays = 0D;
        String qjType = "";
        //根据name与value获取 WorkflowProperty
        List<WorkflowProperty> qjTotalDaysPros = workflowPropertyService.selectProByNameAndWfwId("qjTotalDays", workflowId);
        if (!CollectionUtils.isEmpty(qjTotalDaysPros)){
            String qjTotalDaysStr = qjTotalDaysPros.get(0).getValue();
            if (StringUtils.hasText(qjTotalDaysStr)){
                try {
                    qjTotalDays = Double.parseDouble(qjTotalDaysStr+"");
                    if (qjTotalDays > 0.5){// 走局长
                        qjType = "1-1";
                    }
                    if (qjTotalDays <= 0.5){// 不走局长
                        qjType = "1-2";
                    }
                } catch (NumberFormatException e) {
                    RecordLogTools.error("生成请假流程，获取请假总天数格式错误！",e);
                }
            }

        }
        return qjType;
    }

    private String escapFirstDuty(Integer faqiRoleId,String type){
        String reDuty = "";
        if (Constant.OA_SHOWWEN_TYPE.equals(type)){// 如果是收文审批，第一步的责任人肯定是办公的科长，即2
            reDuty = "2";
        }else{
            Role role = roleService.selectById(faqiRoleId);
            if (ToolUtil.isNotEmpty(role)){
                reDuty = role.getDuty();
            }
        }
        return reDuty;
    }
}
