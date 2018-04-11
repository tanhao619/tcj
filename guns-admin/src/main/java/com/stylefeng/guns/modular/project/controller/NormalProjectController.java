package com.stylefeng.guns.modular.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.stylefeng.guns.common.annotion.DataPermission;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.BigProject;
import com.stylefeng.guns.common.persistence.model.NormalProject;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.dao.NormalProjectDao;
import com.stylefeng.guns.modular.project.dto.FollowInfoDTO;
import com.stylefeng.guns.modular.project.dto.NormalProjrctInfoDTO;
import com.stylefeng.guns.modular.project.dto.NormalProjrctMiniDTO;
import com.stylefeng.guns.modular.project.dto.ProFollowsPersonInfoDto;
import com.stylefeng.guns.modular.project.service.*;
import com.stylefeng.guns.modular.util.ExcelManage;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.util.ExcelExport;
import com.stylefeng.guns.modular.util.StringUtil;
import com.stylefeng.guns.modular.util.file.FileUploadUtil;
import com.stylefeng.guns.modular.zate.dto.FileDto;
import com.stylefeng.guns.modular.project.dto.*;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.INormalProjectService;
import com.stylefeng.guns.modular.project.service.IProCompanyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 常规项目控制器
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Controller
@RequestMapping("/normalProject")
public class NormalProjectController extends BaseController {

    @Autowired
    private INormalProjectService normalProjectService;

    @Autowired
    private IBigProjectService bigProjectService;

    @Autowired
    private IProCompanyService proCompanyService;

    @Autowired
    private IFollowProjectService followProjectService;

    @Autowired
    private IProAttachmentService proAttachmentService;

    @Autowired
    private IUnitLiableService unitLiableService;

    @Autowired
    private IProAreaService proAreaService;

    @Autowired
    private IProAdviseOpeTypeService proAdviseOpeTypeService;

    @Autowired
    private IProContractTypeService proContractTypeService;

    @Autowired
    private ExcelManage excelManage;

    @Autowired
    private IProIndustryTypeService proIndustryTypeService;


    private String PREFIX = "/api/normalProject/";

    /**
     * 跳转到常规项目首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "normalProject.html";
    }

    /**
     * 跳转到添加常规项目
     */
    @RequestMapping("/normalProject_add")
    public String normalProjectAdd() {
        return PREFIX + "normalProject_add.html";
    }

    /**
     * 导出excel弹窗
     */
    @RequestMapping(value = "/excelExport")
    public String excport() {
        return "/api/normalExportConfig/normalExportConfig.html";
    }

    /**
     * 跳转到修改常规项目
     * 控制修改按钮
     */
    @RequestMapping("/normalProject_update/{normalProjectId}")
    public String normalProjectUpdate(@PathVariable Integer normalProjectId, Model model) {
        try {
            NormalProjrctMiniDTO detail = normalProjectService.getDetailById(normalProjectId);
            model.addAttribute("proDetail", detail);
            NormalProTalkInfoDTO protalk = normalProjectService.getProtalkByProId(normalProjectId);
            model.addAttribute("protalk", protalk);
            NormalProConventionInfoDTO proconvention = normalProjectService.getProConventionByProId(normalProjectId);
            model.addAttribute("proconvention", proconvention);

            // add by lgg start
            // 是否拥有 项目管理-修改按钮权限
            boolean accountUseProjUpdateBtn = normalProjectService.isAccountUseProjUpdateBtn(normalProjectId);
            model.addAttribute("opearUpdateBtn", accountUseProjUpdateBtn);
            // 是否拥有 项目管理-跟踪人员分配权限
            boolean accountProjFollowBtn = normalProjectService.isAccountProjFollowBtn(normalProjectId);
            model.addAttribute("opearFollowUserBtn", accountProjFollowBtn);

            //获取当前人的最高等级，1科室 2平台 3街道
            Integer deptLv = followProjectService.queryDeptLv(normalProjectId, 1);
            model.addAttribute("deptLevel", deptLv);
            // add by lgg end
        } catch (Exception e) {
            RecordLogTools.error("normalProjectUpdate", e);
            return "/404.html";
        }
        return PREFIX + "normalProject_edit.html";
    }

    /**
     * add by dengshaojun
     * 跳转到更改跟踪人员信息对话框
     */
    @RequestMapping("/followUser")
    public String followUser() {
        return PREFIX + "followUser.html";
    }

    /**
     * add by dengshaojun
     * 跳转到更改跟踪科室和平台对话框
     */
    @RequestMapping("/followOfficeAndPlat/{normalProjectId}")
    public String followOfficeAndPlat(@PathVariable Integer normalProjectId, Model model) {
        model.addAttribute(normalProjectId);
        return PREFIX + "followOfficeAndPlat.html";
    }

    /**
     * add by dengshaojun
     * 跳转到洽谈更新信息table对话框
     */
    @RequestMapping("/talkLog")
    public String talkLog() {
        return PREFIX + "talkLog.html";
    }

    /**
     * add by dengshaojun
     * 跳转到履约更新信息table对话框
     */
    @RequestMapping("/conventionLog")
    public String conventionLog() {
        return PREFIX + "conventionLog.html";
    }

    /**
     * add by dengshaojun
     * 跳转到操作日志详情对话框
     */
    @RequestMapping("/operationLog/{operationLogId}")
    public String operationLog(@PathVariable Integer operationLogId, Model model) {
        model.addAttribute(operationLogId);
        return PREFIX + "operationLog.html";
    }

    /**
     * 获取常规项目列表
     */
    @ApiOperation(value = "常规项目分页列表Swagger", notes = "获取分页列表", tags = {"normalProject",})
    @PostMapping(value = "/list/{type}")
    @ResponseBody
    public Object list(@RequestBody Map<String, Object> map, @PathVariable("type") String type) {
        Page<NormalProjrctInfoDTO> zatePage = normalProjectService.list(map);
        return super.packForBT(zatePage);
    }

    /**
     * 获取常规项目列表
     */
    @ApiOperation(value = "常规项目分页列表", notes = "获取分页列表", tags = {"normalProject",})
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<NormalProjrctInfoDTO> zatePage = normalProjectService.list(map);
        return super.packForBT(zatePage);
    }

    /**
     * 新增常规项目
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(@RequestParam Map<Object, Object> map) {
        try {
            if (!map.isEmpty()) {
                map.put("userId", ShiroKit.getUser().id);
                map.put("userName", ShiroKit.getUser().name);
                map.put("currentTime", StringUtil.set(System.currentTimeMillis()));
                map.put("isUpdate", false);
                map.put("from", 1);
                Object id = map.get("id");
                int proId = 0;
                Map<Object, Object> objectMap = ParamsUtils.encapPara2(map);
                if (id != null) {
                    map.put("isUpdate", true);
                    proId = Integer.parseInt(id.toString());
                    normalProjectService.updateProject(objectMap);
                    RecordLogTools.info("修改常规项目proId : " + proId);
                } else {
                    objectMap.put("folType", 1);
                    proId = normalProjectService.insertProject(objectMap);
                    RecordLogTools.info("新增常规项目proId : " + proId);
                }
                //更新公司信息
                proCompanyService.batchInsertCompanys(proId, objectMap);
                //更新平台信息
                followProjectService.batchInsertFollowProjects(proId, objectMap);
                //更新附件信息
                proAttachmentService.batchMerge(proId, objectMap);
                //更新责任单位及相关责任人信息
                unitLiableService.batchMerge(proId, objectMap);
                //更新地址及用地
                proAreaService.batchMerge(proId, objectMap);
                //更新建议实施方式
                proAdviseOpeTypeService.batchMerge(proId, objectMap);
                //更新合同类型
                proContractTypeService.batchMerge(proId, objectMap);
                //更新产业类型
                proIndustryTypeService.batchMerge(proId, objectMap);
            } else {
                return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ADD_ERROR);
            }
        } catch (Exception e) {
            RecordLogTools.error("新增项目失败！", e);
            return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ADD_ERROR);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 删除常规项目
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改常规项目
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 常规项目详情
     */
    @ApiOperation(value = "查看常规项目详情", notes = "查看常规项目详情", tags = {"normalProject",})
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    @DataPermission(value = "id")//用于数据权限拦截 add by lgg
    public Object detail(@ApiParam(value = "对象唯一id", required = true) @RequestParam("id") Integer id) {
        NormalProjrctMiniDTO detail = normalProjectService.getDetailById(id);
        if (detail == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return detail;
    }

    /**
     * 常规项目跟踪洽谈信息
     */
    @ApiOperation(value = "查看常规项目跟踪洽谈信息", notes = "查看常规项目跟踪洽谈信息", tags = {"normalProject",})
    @RequestMapping(value = "/protalk", method = RequestMethod.GET)
    @ResponseBody
    @DataPermission(value = "id")//用于数据权限拦截 add by lgg
    public Object protalk(@ApiParam(value = "对象唯一id", required = true) @RequestParam("id") Integer id) {
        NormalProTalkInfoDTO protalk = normalProjectService.getProtalkByProId(id);
        if (protalk == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return protalk;
    }

    /**
     * 常规项目履约信息
     */
    @ApiOperation(value = "查看常规项目履约信息", notes = "查看常规项目履约信息", tags = {"normalProject",})
    @RequestMapping(value = "/proconvention", method = RequestMethod.GET)
    @ResponseBody
    @DataPermission(value = "id")//用于数据权限拦截 add by lgg
    public Object proconvention(@ApiParam(value = "对象唯一id", required = true) @RequestParam("id") Integer id) {
        NormalProConventionInfoDTO proconvention = normalProjectService.getProConventionByProId(id);
        if (proconvention == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return proconvention;
    }

    /**
     * 跟踪人员信息
     */
    @ApiOperation(value = "查看跟踪人员信息", notes = "查看跟踪人员信息", tags = {"normalProject",})
    @RequestMapping(value = "/followsPro", method = RequestMethod.GET)
    @ResponseBody
    public Object followsPro(@ApiParam(value = "跟踪人员姓名") @RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "proId", required = false) Integer proId) {
        List<ProFollowsPersonInfoDto> page = normalProjectService.getFollowsPerson(name, proId);
        if (page == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return page;
    }

    /**
     * 项目分配跟踪人员（新增）
     */
    @ApiOperation(value = "项目分配跟踪人员", notes = "项目分配跟踪人员", tags = {"normalProject",})
    @RequestMapping(value = "/updateFollowUser", method = RequestMethod.POST)
    @ResponseBody
    public Object followsPro(@ApiParam(value = "更新信息") @RequestParam Map<String, Object> map) {

        if (!map.isEmpty()) {
            int id = Integer.parseInt(map.get("id").toString());//项目id
            int from = Integer.parseInt(map.get("from").toString());//项目类型，1常规，2重大
            map.put("updateUserId", ShiroKit.getUser().id);
            map.put("currentTime", StringUtil.set(System.currentTimeMillis()));
            if (from == 1) {
                NormalProject normalProject = normalProjectService.selectById(id);
                if (normalProject != null) {
                    Integer count = normalProjectService.updateNomalProject(map);
                    if (count > 0) {
                        return new SuccessTip(HttpReStatus.OK, Constant.OK_MSG);
                    }
                } else {
                    return new FailTip(HttpReStatus.NOT_FOUND, Constant.UPDATE_ERROR);
                }
            }
            if (from == 2) {
                BigProject bigProject = bigProjectService.selectById(id);
                if (bigProject != null) {
                    Integer count = bigProjectService.updateBigProject(map);
                    if (count > 0) {
                        return new SuccessTip(HttpReStatus.OK, Constant.OK_MSG);
                    }
                } else {
                    return new FailTip(HttpReStatus.NOT_FOUND, Constant.UPDATE_ERROR);
                }
            }
        }
        return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ADD_ERROR);
    }

    /**
     * 跟踪科室信息
     */
    @ApiOperation(value = "查看跟踪科室信息", notes = "查看跟踪科室信息", tags = {"normalProject",})
    @RequestMapping(value = "/followsOffice", method = RequestMethod.GET)
    @ResponseBody
    public Object followsOffice() {
        List<FollowInfoDTO> info = normalProjectService.followsOffice();
        if (info == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return info;
    }

    /**
     * 跟踪平台信息
     */
    @ApiOperation(value = "查看跟踪平台信息", notes = "查看跟踪平台信息", tags = {"normalProject",})
    @RequestMapping(value = "/followsPlat", method = RequestMethod.GET)
    @ResponseBody
    public Object followsPlat() {
        List<FollowInfoDTO> info = normalProjectService.followsPlat();
        if (info == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return info;
    }

    /**
     * 跟踪街道信息
     */
    @ApiOperation(value = "查看跟踪街道信息", notes = "查看跟踪街道信息", tags = {"normalProject",})
    @RequestMapping(value = "/followsStreet", method = RequestMethod.GET)
    @ResponseBody
    public Object followsStreet() {
        List<FollowInfoDTO> info = normalProjectService.followsStreet();
        if (info == null) {
            return new FailTip(HttpReStatus.NOT_FOUND, "数据未找到!");
        }
        return info;
    }

    /**
     * 新增常规项目
     */
    @RequestMapping(value = "/queryProjectsForPerson", method = RequestMethod.GET)
    @ResponseBody
    public Object queryProjectsForPerson(@RequestParam Map<String, Object> map) {
        try {
            //normalProjectService.add
            System.out.println("2121");

        } catch (Exception e) {
            return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ADD_ERROR);
        }
        return super.SUCCESS_TIP;
    }

    /**
     * 常规项目导出Excel表
     */
    @RequestMapping(value = "/exportExcel")
    @ResponseBody
    public Object exportExcel(@RequestParam(value = "name", required = false) String[] name, @RequestParam(value = "conStart", required = false) String conStart, @RequestParam(value = "conEnd", required = false) String conEnd,
                              @RequestParam(value = "talkStartTime", required = false) String talkStartTime, @RequestParam(value = "talkEndTime", required = false) String talkEndTime,
                              @RequestParam(value = "talkTime", required = false) String talkTime, @RequestParam(value = "conTime", required = false) String conTime, HttpServletResponse response) {
        List<String> str = new ArrayList<>(Arrays.asList(name));
        if (StringUtils.isEmpty(str) || str.size() < 1) {
            return new FailTip(HttpReStatus.BAD_REQUEST, "请勾选需要导出的内容!");
        }
        List<Integer> deptIds = ShiroKit.getUser().getDeptIds();
        List<ExportExcelDTO> list = normalProjectService.exportExcel(deptIds, conStart, conEnd, talkStartTime, talkEndTime, talkTime, conTime);
        List<ExportExcelDTO> res = excelManage.setInfo(list, name);
        List<ExportExcelDTO> excList = excelManage.resultManageList(res);
        Map<String, String> titleMap = excelManage.setHeader(name);
        String sheetName = "成都市郫都区项目信息表";
        ExcelExport.excelExport(excList, titleMap, sheetName, response);
        return super.SUCCESS_TIP;
    }

    /**
     * 项目附件上传
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadFile(@RequestPart("file") MultipartFile file) {
        FileDto fileDto = null;
        try {
            fileDto = FileUploadUtil.upload(file);
        } catch (Exception e) {
            RecordLogTools.error("上传失败uploadFile", e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.FAIL_UPLOAD);
        }
        if (fileDto == null) {
            return new FailTip(HttpReStatus.SERVER_ERROR, Constant.FAIL_UPLOAD);
        }
        return fileDto;
    }

    /**
     * 检查项目名是否存在
     *
     * @return
     */
    @RequestMapping(value = "/checkNormalProject", method = RequestMethod.POST)
    @ResponseBody
    public Object checkNormalProject(@RequestParam Map<Object, Object> map) {
        JSONObject json = new JSONObject();
        if (map != null) {
            if (normalProjectService.checkNormalProject(map)) {
//                 return new FailTip(HttpReStatus.SERVER_ERROR, Constant.CHECK_RESULT);
                json.put("valid", false);
            } else {
                json.put("valid", true);
            }
        }

        return json;
    }


}
