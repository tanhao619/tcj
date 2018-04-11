package com.stylefeng.guns.modular.zate.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.persistence.model.ProIndustryType;
import com.stylefeng.guns.common.persistence.model.ZateBuilding;
import com.stylefeng.guns.common.persistence.model.ZateHistory;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.project.service.IProIndustryTypeService;
import com.stylefeng.guns.modular.zate.service.IZateBuildingService;
import com.stylefeng.guns.modular.zate.service.IZateHistoryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 楼宇或闲置厂房载体资源控制器
 *
 * @author lgg
 * @Date 2017-11-07 23:10:45
 */
@Controller
@RequestMapping("/zateBuilding")
public class ZateBuildingController extends BaseController {

    @Autowired
    private IZateBuildingService zateBuildingService;
    @Autowired
    private IZateHistoryService zateHistoryService;
    @Autowired
    private IProIndustryTypeService proIndustryTypeService;
    private String PREFIX = "/api/zateBuilding/";

    /**
     * 跳转到楼宇或闲置厂房载体资源首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "zateBuilding.html";
    }

    /**
     * 跳转到添加楼宇或闲置厂房载体资源
     */
    @RequestMapping("/zateBuilding_add")
    public String zateBuildingAdd() {
        return PREFIX + "zateBuilding_add.html";
    }

    /**
     * 跳转到修改楼宇或闲置厂房载体资源
     */
    @RequestMapping("/zateBuilding_update/{zateBuildingId}/{historyId}")
    public String zateBuildingUpdate(@PathVariable Integer zateBuildingId,@PathVariable Integer historyId, Model model) {
        ZateBuilding zateBuilding = zateBuildingService.selectById(zateBuildingId);
        //查询产业类型回显
        Map<Object,Object> map=new HashMap();
        map.put("proId",zateBuilding.getId());
        map.put("folType",3);
        List<Map> proIndustryTypesListMap=proIndustryTypeService.selectByProId(map);
        model.addAttribute("proIndustryTypesListMap",proIndustryTypesListMap);
        model.addAttribute(zateBuilding);
        return "/api/zate/" + "zate_building_edit.html";
    }

    /**
     * 获取楼宇或闲置厂房载体资源列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增楼宇或闲置厂房载体资源
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ZateBuilding zateBuilding) {
        // 载体资源名字唯一
        if (zateBuilding != null && StringUtils.hasText(zateBuilding.getName())) {
            List<ZateBuilding> ZateExsitsList = zateBuildingService.selectList(new EntityWrapper<ZateBuilding>().
                    eq("name", zateBuilding.getName()));
            if (!CollectionUtils.isEmpty(ZateExsitsList)) {
                return new FailTip(HttpReStatus.BAD_REQUEST, "载体资源已存在！");
            }
        }
        if (zateBuilding != null) {
            Date date = new Date();
            zateBuilding.setCreateTime(date);
            zateBuilding.setUpdateTime(date);// 创建时间即为初始化的修改时间
            zateBuilding.setCurrentTime(System.currentTimeMillis() + "");
            if (ShiroKit.getUser() != null) {
                zateBuilding.setUpdateUserId(ShiroKit.getUser().getId());// 创建人即为初始化的修改人
                zateBuilding.setCreateUserId(ShiroKit.getUser().getId());

            }

        }
        boolean insertBool = zateBuildingService.insert(zateBuilding);

        if (!insertBool) {
            return new FailTip(HttpReStatus.SERVER_ERROR, "添加失败！");
        }
        //改需求，产业类别多选，插入产业类型
        if (zateBuilding != null && !StringUtils.isEmpty(zateBuilding.getInduType())) {
            String[] induType = zateBuilding.getInduType().split(",");
            ProIndustryType proIndustryType = new ProIndustryType();
            proIndustryType.setCreateTime(new Date());//创建时间
//            proIndustryType.setCurrentTime(System.currentTimeMillis() + "");//时间戳
            proIndustryType.setCurrentTime(zateBuilding.getCurrentTime());
            proIndustryType.setFolType(3);//载体
            proIndustryType.setUserId(ShiroKit.getUser().getId());
            proIndustryType.setUserName(ShiroKit.getUser().getName());
            proIndustryType.setProId(zateBuilding.getId());//载体id
            for (int i = 0; i < induType.length; i++) {
                proIndustryType.setName(induType[i]);
                //插入产业表
                boolean insertBool2 = proIndustryTypeService.insert(proIndustryType);
                if (!insertBool2) {
                    return new FailTip(HttpReStatus.SERVER_ERROR, "添加产业失败！");
                }
            }
        }
        return new SuccessTip(HttpReStatus.OK, "添加成功！");
    }

    /**
     * 删除楼宇或闲置厂房载体资源
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改楼宇或闲置厂房载体资源
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ZateBuilding zateBuilding) {
        // 载体资源名字唯一
        //zateBuilding = (ZateBuilding) ReflectUtils.dealModelNull(zateBuilding);
        if (zateBuilding != null && StringUtils.hasText(zateBuilding.getName())) {
            ZateBuilding zateBuildingOrg = zateBuildingService.selectById(zateBuilding.getId());
            if (zateBuildingOrg != null && !zateBuilding.getName().equals(zateBuildingOrg.getName())) {
                List<ZateBuilding> ZateExsitsList = zateBuildingService.selectList(new EntityWrapper<ZateBuilding>().
                        eq("name", zateBuilding.getName()));
                if (!CollectionUtils.isEmpty(ZateExsitsList)) {
                    return new FailTip(HttpReStatus.BAD_REQUEST, "载体资源已存在！");
                }
            }
        }
        if (ShiroKit.getUser() != null) {
            zateBuilding.setUpdateUserId(ShiroKit.getUser().getId());
        }
        zateBuilding.setCurrentTime(System.currentTimeMillis() + "");//时间戳，用于一对多的触发器记录
        zateBuilding.setUpdateTime(new Date());
        boolean bool = zateBuildingService.updateById(zateBuilding);
        if (!bool) {
            return new ErrorTip(HttpReStatus.NOT_FOUND, "修改失败！");
        }
        //改需求，产业类别多选，更新产业类型
        ProIndustryType proIndustryType = new ProIndustryType();
        proIndustryType.setCreateTime(new Date());//创建时间
//        proIndustryType.setCurrentTime(System.currentTimeMillis() + "");//时间戳
        proIndustryType.setCurrentTime(zateBuilding.getCurrentTime());
        proIndustryType.setFolType(3);//载体
        proIndustryType.setUserId(ShiroKit.getUser().getId());
        proIndustryType.setUserName(ShiroKit.getUser().getName());
        proIndustryType.setProId(zateBuilding.getId());//载体id
        //先查询产业表里面有哪些产业
        Map<Object, Object> map = new HashMap();
        map.put("proId", zateBuilding.getId());
        map.put("folType", 3);
        List<Map> proIndustryTypesListMap = proIndustryTypeService.selectByProId(map);
        //原本没有产业类型
        if (proIndustryTypesListMap == null || proIndustryTypesListMap.size() <= 0) {
            if (zateBuilding != null && !StringUtils.isEmpty(zateBuilding.getInduType())) {
                String[] induType = zateBuilding.getInduType().split(",");
                for (int i = 0; i < induType.length; i++) {
                    proIndustryType.setName(induType[i]);
                    //插入产业
                    boolean insertBool2 = proIndustryTypeService.insert(proIndustryType);
                    if (!insertBool2) {
                        return new FailTip(HttpReStatus.SERVER_ERROR, "添加产业失败！");
                    }
                }
            }
        } else {
            List<String> induTypeNameListOld = new ArrayList<>();//原来产业
            List<String> induTypeNameListjiaoji = new ArrayList<String>();
              for (int i = 0; i < proIndustryTypesListMap.size(); i++) {
                    String name = proIndustryTypesListMap.get(i).get("name").toString();
                    induTypeNameListOld.add(name);
                    induTypeNameListjiaoji.add(name);
              }

            if (zateBuilding != null && !StringUtils.isEmpty(zateBuilding.getInduType())) {
                String[] induType = zateBuilding.getInduType().split(",");
                List<String> induTypeListTemp = Arrays.asList(induType);//现在产业
                List<String> induTypeNameListNew = new ArrayList(induTypeListTemp);
                //求交集
                induTypeNameListjiaoji.retainAll(induTypeNameListNew);
                //原来的减去交集，就是要删除的
                induTypeNameListOld.removeAll(induTypeNameListjiaoji);
                if (induTypeNameListOld != null && induTypeNameListOld.size() > 0) {
//                    proIndustryType.setCurrentTime(System.currentTimeMillis() + "");//时间戳
                    proIndustryType.setCurrentTime(zateBuilding.getCurrentTime());
                    for (int i = 0; i < induTypeNameListOld.size(); i++) {
                        String s = induTypeNameListOld.get(i);
                        for (int k = 0; k < proIndustryTypesListMap.size(); k++) {
                            String ss = proIndustryTypesListMap.get(k).get("name").toString();
                            if (s.equals(ss)) {
                                Integer deletId = Integer.valueOf(proIndustryTypesListMap.get(k).get("id").toString());
                                //先更新
                                proIndustryType.setId(deletId);
                                proIndustryTypeService.updateById(proIndustryType);
                                proIndustryTypeService.deleteById(deletId);
                            }
                        }
                    }
                }
                //现在的减去交集，就是要新增的
                induTypeNameListNew.removeAll(induTypeNameListjiaoji);
                if (induTypeNameListNew != null && induTypeNameListNew.size() > 0) {
                    for (int i = 0; i < induTypeNameListNew.size(); i++) {
                        proIndustryType.setName(induTypeNameListNew.get(i));
                        boolean insertBool2 = proIndustryTypeService.insert(proIndustryType);
                        if (!insertBool2) {
                            return new FailTip(HttpReStatus.SERVER_ERROR, "添加产业失败！");
                        }
                    }
                }
            } else {
                //现在传入是空，表示要删除以前的全部
//                proIndustryType.setCurrentTime(System.currentTimeMillis() + "");//时间戳
                proIndustryType.setCurrentTime(zateBuilding.getCurrentTime());
                for (int i = 0; i < proIndustryTypesListMap.size(); i++) {
                    //先更新
                    proIndustryType.setId(Integer.valueOf(proIndustryTypesListMap.get(i).get("id").toString()));
                    proIndustryTypeService.updateById(proIndustryType);
                    proIndustryTypeService.deleteById(Integer.valueOf(proIndustryTypesListMap.get(i).get("id").toString()));
                }
            }
        }
        return new SuccessTip(HttpReStatus.OK,"修改成功！");
    }

    /**
     * 楼宇或闲置厂房载体资源详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    @ApiOperation(value = "土地类载体资源跟新历史记录详情列表", notes = "载体资源跟新历史记录详情列表", tags={ "Zate", })
    @RequestMapping(value = "/hisSpeciList/{zateId}/{temp}",method = RequestMethod.GET)
    @ResponseBody
    public Object getHistoryDetailList(@ApiParam(value = "temp", required = false)@PathVariable("temp") String temp,
                                       @PathVariable Integer zateId) {
        Page<ZateHistory> zateLandHistoryPage = zateHistoryService.selectSpeciList(zateId, 2);
        return super.packForBT(zateLandHistoryPage);
    }
    /**
     * 获取载体资源更新
     */
    @RequestMapping(value = "/hisSpeciList/{zateId}",method = RequestMethod.GET)
    @ResponseBody
    public Object getHistoryDetailList( @PathVariable Integer zateId) {
        Page<ZateHistory> zateLandHistoryPage = zateHistoryService.selectSpeciList(zateId, 2);
        return super.packForBT(zateLandHistoryPage);
    }

}
