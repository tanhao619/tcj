package com.stylefeng.guns.modular.zate.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.constant.HttpReStatus;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.ZateHislist;
import com.stylefeng.guns.common.persistence.model.ZateHistory;
import com.stylefeng.guns.common.persistence.model.ZateLand;
import com.stylefeng.guns.common.persistence.model.ZateLandimg;
import com.stylefeng.guns.core.base.controller.BaseController;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.base.tips.FailTip;
import com.stylefeng.guns.core.base.tips.SuccessTip;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.util.ReflectUtils;
import com.stylefeng.guns.modular.util.TimeUtils;
import com.stylefeng.guns.modular.util.file.FileUploadUtil;
import com.stylefeng.guns.modular.zate.dto.FileDto;
import com.stylefeng.guns.modular.zate.dto.ZateImgDto;
import com.stylefeng.guns.modular.zate.dto.ZateLandDto;
import com.stylefeng.guns.modular.zate.service.IZateHistoryService;
import com.stylefeng.guns.modular.zate.service.IZateLandService;
import com.stylefeng.guns.modular.zate.service.IZateLandimgService;
import com.stylefeng.guns.modular.zate.service.IZatePropertyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 土地类载体资源控制器
 *
 * @author lgg
 * @Date 2017-11-07 23:07:20
 */
@Controller
@RequestMapping("/zateLand")
public class ZateLandController extends BaseController {

    @Autowired
    private IZateLandService zateLandService;
    @Autowired
    private IZateLandimgService zateLandimgService;
    @Autowired
    private IZateHistoryService zateHistoryService;

    @Autowired
    private IZatePropertyService zatePropertyService;

    private String PREFIX = "/api/zateLand/";

    /**
     * 跳转到土地类载体资源首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "zateLand.html";
    }

    /**
     * 跳转到添加土地类载体资源
     */
    @RequestMapping("/zateLand_add")
    public String zateLandAdd() {
        return PREFIX + "zateLand_add.html";
    }

    /**
     * 跳转到修改土地类载体资源
     */
    @RequestMapping("/zateLand_update/{zateLandId}/{historyId}")
    public String zateLandUpdate(@PathVariable Integer zateLandId, @PathVariable Integer historyId, Model model) {
        ZateLand zateLand = zateLandService.selectById(zateLandId);
        // 处理附件图片
        ZateLandDto zateLandDto = new ZateLandDto();
        BeanUtils.copyProperties(zateLand, zateLandDto);
        List<ZateLandimg> zateLandimgList = zateLandimgService.selectList(new EntityWrapper<ZateLandimg>().eq("landId", zateLand.getId()));
        if (!CollectionUtils.isEmpty(zateLandimgList)) {
            List<ZateImgDto> zateImgDtoList = new ArrayList<>();
            for (int i = 0; i < zateLandimgList.size(); i++) {
                ZateImgDto zateImgDto = new ZateImgDto();
                BeanUtils.copyProperties(zateLandimgList.get(i), zateImgDto);
                zateImgDtoList.add(zateImgDto);
            }
            zateLandDto.setZateImgs(zateImgDtoList);
        }
        // 处理 用地性质
        List<Map<String, Object>> landPropertyMaps = zatePropertyService.landPropertyMap(zateLandId);
        model.addAttribute("landPropertyMaps", landPropertyMaps);
        model.addAttribute("zateLand", zateLandDto);
        return "/api/zate/" + "zate_land_edit.html";
    }

    /**
     * 获取土地类载体资源列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return null;
    }

    /**
     * 新增土地类载体资源
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(ZateLand zateLand, @RequestParam(value = "zateImgs", required = false) String zateImgs) {
        // 载体资源名字唯一
        if (zateLand != null && StringUtils.hasText(zateLand.getName())) {
            List<ZateLand> ZateExsitsList = zateLandService.selectList(new EntityWrapper<ZateLand>().
                    eq("name", zateLand.getName()));
            if (!CollectionUtils.isEmpty(ZateExsitsList)) {
                return new FailTip(HttpReStatus.BAD_REQUEST, "载体资源已存在！");
            }
        }
        if (zateLand != null) {
            Date date = new Date();
            zateLand.setCreateTime(date);
            zateLand.setUpdateTime(date);// 创建时间即为初始化的修改时间
            zateLand.setCurrentTime(System.currentTimeMillis() + "");
            if (ShiroKit.getUser() != null) {
                zateLand.setCreateUserId(ShiroKit.getUser().getId());
                zateLand.setUpdateUserId(ShiroKit.getUser().getId());// 创建人即为初始化的修改人
            }
        }
        boolean insertBool = zateLandService.insert(zateLand);
        if (!insertBool) {
            return new FailTip(HttpReStatus.SERVER_ERROR, "添加失败！");
        }
        // 处理载体资源图片
        Boolean imgBool = true;
        if (StringUtils.hasText(zateImgs)) {
            imgBool = zateLandimgService.addLandImgs(zateImgs, zateLand.getId());
        }
        // 需求变更 土地性质多选
        String landPropertys = super.getPara("landPropertys").trim();
        Boolean protyBool = zatePropertyService.addLandProperty(landPropertys, zateLand.getId());

        if (imgBool && protyBool) {
            return new SuccessTip(HttpReStatus.OK, "添加成功！");
        } else {
            // 添加失败删除该载体
            zateLandService.deleteById(zateLand.getId());
            return new FailTip(HttpReStatus.BAD_REQUEST, "添加失败！");
        }
    }

    /**
     * 删除土地类载体资源
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(value = "ids") String ids) {
        return SUCCESS_TIP;
    }


    /**
     * 修改土地类载体资源
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(ZateLand zateLand, @RequestParam(value = "zateImgs", required = false) String zateImgs) {
        // 载体资源名字唯一
        //zateLand = (ZateLand)ReflectUtils.dealModelNull(zateLand);
        if (zateLand != null && StringUtils.hasText(zateLand.getName())) {
            ZateLand zateLandOrg = zateLandService.selectById(zateLand.getId());
            if (zateLandOrg != null && !zateLand.getName().equals(zateLandOrg.getName())) {
                List<ZateLand> ZateExsitsList = zateLandService.selectList(new EntityWrapper<ZateLand>().
                        eq("name", zateLand.getName()));
                if (!CollectionUtils.isEmpty(ZateExsitsList)) {
                    return new FailTip(HttpReStatus.BAD_REQUEST, "载体资源已存在！");
                }
            }
        }
        if (ShiroKit.getUser() != null) {
            zateLand.setUpdateUserId(ShiroKit.getUser().getId());
        }
        zateLand.setUpdateTime(new Date());
        zateLand.setCurrentTime(System.currentTimeMillis() + "");//时间戳，用于一对多的触发器记录
        boolean bool = zateLandService.updateById(zateLand);
        if (!bool) {
            return new ErrorTip(HttpReStatus.NOT_FOUND, "修改失败！");
        }
        // 处理载体资源图片
        Boolean imgBool = zateLandimgService.updateLandImgs(zateImgs, zateLand.getId(), zateLand);

        // 需求变更 土地性质多选
        String landPropertys = super.getPara("landPropertys").trim();
        Boolean protyUpdateBool = zatePropertyService.updateZatePropertys(landPropertys, zateLand);
        return new SuccessTip(HttpReStatus.OK, "修改成功！");
    }

    /**
     * 土地类载体资源详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }

    /**
     * 土地类载体资源图片上传
     */
    @RequestMapping(method = RequestMethod.POST, path = "/upload")
    @ResponseBody
    public Object upload(@RequestPart("file") MultipartFile file) {
        FileDto fileDto = null;
        try {
            fileDto = FileUploadUtil.upload(file);
        } catch (Exception e) {
            RecordLogTools.error("上传附件失败！", e);
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.FAIL_UPLOAD);
        }
        if (fileDto == null) {
            return new FailTip(HttpReStatus.SERVER_ERROR, Constant.FAIL_UPLOAD);
        }
        return fileDto;
    }

    /**
     * 土地类载体资源图片删除
     */
    @RequestMapping(method = RequestMethod.POST, path = "/delFile")
    @ResponseBody
    public Object delFile(String filePath) {
        Boolean delBool = false;
        try {
            delBool = FileUploadUtil.removeFile(filePath);
        } catch (Exception e) {
            return new ErrorTip(HttpReStatus.SERVER_ERROR, Constant.DEL_ERROR);
        }
        if (delBool) {
            return new SuccessTip(HttpReStatus.OK, Constant.DEL_OK);
        } else {
            return new FailTip(HttpReStatus.SERVER_ERROR, Constant.DEL_ERROR);
        }
    }

    @ApiOperation(value = "土地类载体资源跟新历史记录详情列表", notes = "载体资源跟新历史记录详情列表", tags = {"Zate",})
    @RequestMapping(value = "/hisSpeciList/{zateId}/{temp}", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistoryDetailList(@ApiParam(value = "temp", required = false) @PathVariable("temp") String temp,
                                       @PathVariable Integer zateId) {
        Page<ZateHistory> zateLandHistoryPage = zateHistoryService.selectSpeciList(zateId, 1);
        return super.packForBT(zateLandHistoryPage);
    }

    /**
     * 获取载体资源更新
     */
    @RequestMapping(value = "/hisSpeciList/{zateId}", method = RequestMethod.GET)
    @ResponseBody
    public Object getHistoryDetailList(@PathVariable Integer zateId) {
        Page<ZateHistory> zateLandHistoryPage = zateHistoryService.selectSpeciList(zateId, 1);
        return super.packForBT(zateLandHistoryPage);
    }

}
