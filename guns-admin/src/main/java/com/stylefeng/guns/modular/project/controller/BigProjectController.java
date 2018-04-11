package com.stylefeng.guns.modular.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.project.dto.BigExcelExportDTO;
import com.stylefeng.guns.modular.project.dto.BigProDetailDTO;
import com.stylefeng.guns.modular.project.dto.BigProjrctInfoDTO;
import com.stylefeng.guns.modular.project.service.IBigProjectService;
import com.stylefeng.guns.modular.project.service.IFollowProjectService;
import com.stylefeng.guns.modular.project.service.IProAdviseOpeTypeService;
import com.stylefeng.guns.modular.project.service.IUnitLiableService;
import com.stylefeng.guns.modular.util.ExcelExport;
import com.stylefeng.guns.modular.project.service.*;
import com.stylefeng.guns.modular.util.ExcelManage;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 重大项目控制器
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
@Controller
@RequestMapping("/bigProject")
@Api(value = "big-Project", description = "重大包装项目")
public class BigProjectController extends BaseController {

    @Autowired
    private IBigProjectService bigProjectService;

    @Autowired
    private IFollowProjectService followProjectService;

    @Autowired
    private IUnitLiableService unitLiableService;

    @Autowired
    private IProAdviseOpeTypeService proAdviseOpeTypeService;

    @Autowired
    private IProCategaryService proCategaryService;

    @Autowired
    private IProPackPositionService proPackPositionService;

    @Autowired
    private IProBuildPlaceService proBuildPlaceService;

    @Autowired
    private ExcelManage excelManage;

    @Autowired
    private IProWorkProcessService proWorkProcessService;

    @Autowired
    private IProIndustryTypeService proIndustryTypeService;

    private String PREFIX = "/api/bigProject/";

    /**
     * 跳转到重大项目首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "bigProject.html";
    }

    /**
     * 跳转到重大项目导出页
     */
    @RequestMapping("/bigExportExcel")
    public String bigExportCon() {
        return  "/api/bigProjectExportConfig/exportConfig.html";
    }

    /**
     * 跳转到添加重大项目
     */
    @RequestMapping("/bigProject_add")
    public String bigProjectAdd() {
        return PREFIX + "bigProject_add.html";
    }

    /**
     * 跳转到目前工作进度的查看修改弹窗
     */
    @RequestMapping("/processLog")
    public String processLog() {
        return PREFIX + "processLog.html";
    }

    /**
     * 跳转到查看修改重大项目
     */
    @RequestMapping("/bigProject_update/{bigProjectId}")
    public String bigProjectUpdate(@PathVariable Integer bigProjectId, Model model) {
        try {
            BigProDetailDTO detailDTO = bigProjectService.getDetailById(bigProjectId);
            model.addAttribute("detail", detailDTO);
            // add by lgg start
            // 是否拥有 项目管理-修改按钮权限
            boolean accountUseProjUpdateBtn = bigProjectService.isAccountUseProjUpdateBtn(bigProjectId);
            model.addAttribute("opearUpdateBtn",accountUseProjUpdateBtn);
            // 是否拥有 项目管理-跟踪人员分配权限
            boolean accountProjFollowBtn = bigProjectService.isAccountProjFollowBtn(bigProjectId);
            model.addAttribute("opearFollowUserBtn",accountProjFollowBtn);
            //获取当前人的最高等级，1科室 2平台 3街道
            Integer deptLv = followProjectService.queryDeptLv(bigProjectId, 2);
            model.addAttribute("deptLevel", deptLv);
        } catch (Exception e) {
            RecordLogTools.error("bigProjectUpdate", e);
            return  "/404.html";
        }
        // add by lgg end
        return PREFIX + "bigProject_edit.html";
    }

    /**
     * 获取重大项目列表
     */
    @ApiOperation(value = "重大包装项目分页列表", notes = "获取分页列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(@ApiParam(value = "搜索参数", required = true) @RequestParam Map<String,Object> map) {
        Page<BigProjrctInfoDTO> Page = bigProjectService.list(map);
        return super.packForBT(Page);
    }

    /**
     * 新增重大项目
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@RequestParam Map<Object, Object> map) {
        try{
            if (!map.isEmpty()) {
                map.put("userId", ShiroKit.getUser().id);
                map.put("userName", ShiroKit.getUser().name);
                map.put("currentTime", StringUtil.set(System.currentTimeMillis()));
                map.put("isUpdate", false);
                map.put("from", 2);
                Object id = map.get("id");
                int proId = 0;
                Map<Object, Object> objectMap = ParamsUtils.encapPara2(map);
                if (id != null) {
                    map.put("isUpdate", true);
                    proId = Integer.parseInt(id.toString());
                    bigProjectService.updateProject(objectMap);
                    RecordLogTools.info("修改重大项目proId : " + proId);
                } else {
//                    Integer maxDeptLevel = ShiroKit.getUser().getMaxDeptLevel();
                    objectMap.put("folType", 1);
                    proId = bigProjectService.insertProject(objectMap);
                    RecordLogTools.info("新增重大项目proId : " + proId);
                }
                //更新平台信息
                followProjectService.batchInsertFollowProjects(proId, objectMap);
                //更新责任单位及相关责任人信息
                unitLiableService.batchMerge(proId, objectMap);
                //更新建议实施方式
                proAdviseOpeTypeService.batchMerge(proId, objectMap);
                //更新行业
                proCategaryService.batchMerge(proId, objectMap);
                //更新包装定位
                proPackPositionService.batchMerge(proId, objectMap);
                //更新拟建设地点
                proBuildPlaceService.batchMerge(proId, objectMap);
                //更新工作进度
                proWorkProcessService.batchMerge(proId, objectMap);
                //更新产业类型
                proIndustryTypeService.batchMerge(proId, objectMap);
            } else {
                return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ADD_ERROR);
            }
        } catch (Exception e) {
            RecordLogTools.error("新增重大项目异常！", e);
            return new FailTip(HttpReStatus.BAD_REQUEST, Constant.ADD_ERROR);
        }

        return super.SUCCESS_TIP;
    }

    /**
     * 删除重大项目
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改重大项目
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 重大项目详情
     */
    @ApiOperation(value = "重大项目详情", notes = "重大项目详情", tags={ "bigProject", })
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    @ResponseBody
    public Object detail(@RequestParam(value = "id",required = false) Integer id) {
        BigProDetailDTO detailDTO = bigProjectService.getDetailById(id);
        if (StringUtils.isEmpty(detailDTO)){
            return new FailTip(HttpReStatus.NOT_FOUND, Constant.ERROR_MGS);
        }
        return detailDTO;
    }

    /**
     * 重大项目导出Excel表
     */
    @RequestMapping(value = "/exportBigExcel")
    @ResponseBody
    public Object exportBigExcel(@RequestParam(value = "name",required = false) String[] name, @RequestParam(value = "StartTime",required = false)String StartTime,
                                 @RequestParam(value = "EndTime",required = false)String EndTime, @RequestParam(value = "workProcessTime",required = false)String workProcessTime, HttpServletResponse response){

        List<String> str = new ArrayList<>(Arrays.asList(name));
        if (StringUtils.isEmpty(str) || str.size() < 1){
            return new FailTip(HttpReStatus.BAD_REQUEST ,"请勾选需要导出的内容!");
        }
        List<Integer> deptIds = ShiroKit.getUser().getDeptIds();
        List<BigExcelExportDTO> list = bigProjectService.exportExcel(deptIds,StartTime,EndTime,workProcessTime);
        List<BigExcelExportDTO> res = excelManage.setBigInfo(list,name);
        List<BigExcelExportDTO> excList = excelManage.resultBigManageList(res);
        Map<String, String> titleMap = excelManage.setBigHeader(name);
        String sheetName = "成都市郫都区社会投资重大储备项目汇总表";
        ExcelExport.excelExport(excList, titleMap, sheetName, response);
        return super.SUCCESS_TIP;
    }

    /**
     * 检查项目名是否存在
     *
     * @return
     */
    @RequestMapping(value = "/checkBigProject", method = RequestMethod.POST)
    @ResponseBody
    public Object checkBigProject(@RequestParam Map<Object, Object> map) {
        JSONObject json = new JSONObject();
        if (map != null) {
            if (bigProjectService.checkBigProject(map)) {
//                 return new FailTip(HttpReStatus.SERVER_ERROR, Constant.CHECK_RESULT);
                json.put("valid", false);
            } else {
                json.put("valid", true);
            }
        }

        return json;
    }


}
