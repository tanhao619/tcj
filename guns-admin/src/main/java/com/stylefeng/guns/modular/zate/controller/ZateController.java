package com.stylefeng.guns.modular.zate.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.util.ExcelUtils;
import com.stylefeng.guns.modular.zate.service.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 载体资源管理控制器
 *
 * @author lgg
 * @Date 2017-11-07 22:59:39
 */
@Controller
@RequestMapping("/zate")
public class ZateController extends BaseController {

    @Autowired
    private IZateService zateService;
    @Autowired
    private IZateHislistService zateHislistService;
    @Autowired
    private IZateHistoryService zateHistoryService;
    @Autowired
    private IZateBuildingService buildingService;
    @Autowired
    private IZateLandService zateLandService;
    private String PREFIX = "/api/zate/";

    /**
     * 跳转到载体资源管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "zate.html";
    }

    /**
     * 跳转到添加载体资源管理
     */
    @RequestMapping("/zate_add")
    public String zateAdd() {
        return PREFIX + "zate_add.html";
    }

    /**
     * 跳转到修改载体资源管理
     */
    @RequestMapping("/zate_update/{zateId}")
    public String zateUpdate(@PathVariable Integer zateId, Model model) {
        return PREFIX + "zate_land_edit.html";
    }

    /**
     * 获取载体资源管理列表swagger
     */
    @ApiOperation(value = "载体资源列表", notes = "载体资源列表", tags = {"Zate",})
    @PostMapping(value = "/list/{temp}")
    @ResponseBody
    public Object list(@ApiParam(value = "查询条件,例如：{name:\"楼层\",address:\"成都优易街\",status:1}", required = false) @RequestBody Map<String, Object> map,
                       @ApiParam(value = "temp", required = false) @PathVariable("temp") String temp) {
        Page<Zate> zatePage = zateService.searchList(map);
        return super.packForBT(zatePage);
    }

    /**
     * 获取载体资源管理列表userfull
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(@RequestParam Map<String, Object> map) {
        Page<Zate> zatePage = zateService.searchList(map);
        return super.packForBT(zatePage);
    }

    /**
     * 新增载体资源管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Zate zate) {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除载体资源管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改载体资源管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * 载体资源管理详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }


    @ApiOperation(value = "载体资源跟新历史记录列表", notes = "载体资源跟新历史记录列表", tags = {"Zate",})
    @RequestMapping(value = "/historys/list/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistory(@ApiParam(value = "temp", required = false) @PathVariable("temp") String temp) {
        Page<ZateHislist> page = new PageFactory<ZateHislist>().defaultPage();
        Page<ZateHislist> zateHislistPage = zateHislistService.selectPage(page);
        return super.packForBT(zateHislistPage);
    }

    /**
     * 获取载体资源更新
     */
    @RequestMapping(value = "/historys/list", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistory() {
        Page<ZateHislist> page = new PageFactory<ZateHislist>().defaultPage();
        Page<ZateHislist> zateHislistPage = zateHislistService.selectPage(page);
        return super.packForBT(zateHislistPage);
    }


    @ApiOperation(value = "载体资源跟新历史记录详情", notes = "载体资源跟新历史记录详情", tags = {"Zate",})
    @RequestMapping(value = "/history/detail/{historyId}/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistoryDetail(@ApiParam(value = "temp", required = false) @PathVariable("temp") String temp,
                                   @PathVariable Integer historyId) {
        List<ZateHistory> historyDetailList = zateHistoryService.selectHisDetail(historyId);
        return historyDetailList;
    }

    /**
     * 获取载体资源更新
     */
    @RequestMapping(value = "/history/detail/{historyId}", method = RequestMethod.GET)
    public String getHistoryDetail(@PathVariable Integer historyId, Model model) {
        List<ZateHistory> historyDetailList = zateHistoryService.selectHisDetail(historyId);
        model.addAttribute("details", historyDetailList);
        return PREFIX + "zate_log_detail.html";
    }

    /*
      * 获取导出载体资源条数
      */
    @RequestMapping(value = "/export/zate_excel/{num}", method = RequestMethod.GET)
//    @ResponseBody
    public String zateExcel(@PathVariable Integer num, @RequestParam Map<String, Object> map, Model model) {
        model.addAttribute("exportNum", num);
        model.addAttribute("params", map);
        return PREFIX + "zate_excel.html";
    }

    @RequestMapping(value = "/exportNum", method = RequestMethod.GET)
    @ResponseBody
    public Object exportNum(@RequestParam Map<String, Object> map, Model model) {
        List<Zate> zates = zateService.searchExportList(map);
        Long zateType = Long.valueOf(map.get("zateType") + "");
        long exportNum = zates.stream().filter(p -> p.getType() != null
                && p.getType() == zateType).count();
        return exportNum;
    }

    /*
     * 导出载体资源excel
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public Object exportExcel(@RequestParam Map<String, Object> map) {
        // 查询需要导出的zate视图
        List<Zate> zates = new ArrayList<Zate>();
        long startTime = System.currentTimeMillis();
        //  载体类型 1 土地资源类 2 楼宇或闲置厂房类
        Integer zateType = Integer.valueOf(map.get(Constant.ZATE_TYPE) + "");
        if ("true".equals(map.get(Constant.ZATE_ALL))) {
            map.clear();
            map.put(Constant.Z_TYPE, zateType);
            zates = zateService.searchExportList(map);
        }
        if ("false".equals(map.get(Constant.ZATE_ALL))) {
            zates = zateService.searchExportList(map);
        }
        // 得到载体表实际ids
        List<Integer> zIds = zates.stream().map(p -> p.getZid()).collect(Collectors.toList());
        //  载体类型 1 土地资源类 2 楼宇或闲置厂房类
        HttpServletResponse response = HttpKit.getResponse();
        response.reset();
        String excelName = scapeEXcelName(zateType);
        try {
            response.setHeader("Content-Disposition", "attachment; filename="
                    + URLEncoder.encode(excelName, "UTF-8"));

            // 确保发送的当前文本格式
            response.setContentType("applicationnd/ms-excel");// 03版
            //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); // 07版

            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            ServletOutputStream out = response.getOutputStream();
            Workbook workbook = null;

            if (zateType == 1) {// 土地资源类
                List<ExpZateland> expZateLands = zateLandService.selectByIds(map, zIds);
                workbook = ExcelUtils.exportZLandExcel(expZateLands);
            }
            if (zateType == 2) {// 楼宇或标准厂房类
                List<ExpZatebuilding> expZateBuildings = buildingService.selectByIds(map, zIds);
                workbook = ExcelUtils.exportZBuildingExcel(expZateBuildings);
            }

            if (workbook != null) {
                workbook.write(out);
                workbook.close();
                out.flush();
                out.close();
                long endTime = System.currentTimeMillis();
                RecordLogTools.info("导出时间：" + (endTime - startTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessTip(HttpReStatus.OK, "导出失败！");
            //resultMap.put("msg","导出出错");
        }
        return new SuccessTip(HttpReStatus.OK, "导出成功！");
    }

    private String scapeEXcelName(Integer type) {
        String excelName = "";
        if (type == 1) {
            excelName = "土地载体资源" + System.currentTimeMillis() + ".xls";
        }
        if (type == 2) {
            excelName = "楼宇或标准厂房载体资源" + System.currentTimeMillis() + ".xls";
        }
        return excelName;
    }
}
