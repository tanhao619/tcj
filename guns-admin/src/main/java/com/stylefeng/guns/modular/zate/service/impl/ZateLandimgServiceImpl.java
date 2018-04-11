package com.stylefeng.guns.modular.zate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ZateHistory;
import com.stylefeng.guns.common.persistence.model.ZateLand;
import com.stylefeng.guns.common.persistence.model.ZateLandimg;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.util.RecordLogTools;
import com.stylefeng.guns.modular.zate.dao.ZateLandimgDao;
import com.stylefeng.guns.modular.zate.dto.ZateImgDto;
import com.stylefeng.guns.modular.zate.service.IZateHistoryService;
import com.stylefeng.guns.modular.zate.service.IZateLandService;
import com.stylefeng.guns.modular.zate.service.IZateLandimgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 土地载体资源土地点位图Service
 *
 * @author lgg
 * @Date 2017-11-07 23:21:32
 */
@Service
public class ZateLandimgServiceImpl extends ServiceImpl<BaseMapper<ZateLandimg>,ZateLandimg> implements IZateLandimgService {

    @Autowired
    private IZateLandService zateLandService;
    @Autowired
    private IZateLandimgService zateLandimgService;
    @Autowired
    private ZateLandimgDao zateLandimgDao;
    @Autowired
    private IZateHistoryService zateHistoryService;
    @Override
    public Boolean addLandImgs(String zateImgs, Integer zateLandId) {
        boolean reBool = false;
        List<ZateLandimg> landimgList = new ArrayList<>();
        if (StringUtils.hasText(zateImgs)){
            try {
                JSONArray zateImgsArr = (JSONArray)JSONArray.parse(zateImgs);
                for (int i = 0; i < zateImgsArr.size(); i++) {
                    ZateLandimg zateLandimg = new ZateLandimg();
                    JSONObject jsonObj = (JSONObject)JSON.toJSON(zateImgsArr.get(i));
                    ZateImgDto zateImgDto = JSON.toJavaObject(jsonObj, ZateImgDto.class);
                    BeanUtils.copyProperties(zateImgDto,zateLandimg);
                    // 添加土地类载体资源成功，载体图片插入载体资源外键
                    zateLandimg.setLandId(zateLandId);
                    zateLandimg.setCreateTime(new Date());
                    ShiroUser user = ShiroKit.getUser();
                    if (user != null){
                        zateLandimg.setUserId(user.getId());
                        zateLandimg.setUserName(user.getName());
                    }
                    landimgList.add(zateLandimg);
                }
            } catch (Exception e) {
                zateLandService.deleteById(zateLandId);
                RecordLogTools.error("addLandImgs",e);
            }
            reBool = super.insertBatch(landimgList);
        }
        return reBool;
    }

    @Override
    public Boolean updateLandImgs(String zateImgs, Integer zateLandId,ZateLand updatedZateLang) {

        String zatelandCurrentTime = updatedZateLang.getCurrentTime()+"";
        Date zatelandUpdateTime = updatedZateLang.getUpdateTime();

        List<Integer> oldImgIds = zateLandimgDao.selectImgIdsByLid(zateLandId);
        List<Integer> newImgIds = new ArrayList<Integer>();
        boolean rBool = false;

        if (StringUtils.isEmpty(zateImgs) && !CollectionUtils.isEmpty(oldImgIds)
                && oldImgIds.size() == 1){// 处理删除最后一个图
            rBool =  deleteLastOne( zatelandCurrentTime, zatelandUpdateTime, oldImgIds);
        }

        //zateImgs = "[{\"id\":\"161\",\"name\":\"svn.txt\",\"url\":\"tcj/file/2017/11/18/yiX0YZd227911511010611749/svn.txt\"},{\"id\":\"162\",\"name\":\"把jar程序泡成后台程序，关于jar的各种操作.txt\",\"url\":\"tcj/file/2017/11/18/vjyqgyIk80381511011813620/把jar程序泡成后台程序，关于jar的各种操作.txt\"},{\"id\":\"\",\"name\":\"liunx实时监控启动脚本.txt\",\"url\":\"tcj/file/2017/11/18/i1QOGATQ6401511011342752/liunx实时监控启动脚本.txt\"}]";
        List<ZateLandimg> newImgList = new ArrayList<>();
        if (StringUtils.hasText(zateImgs)){
            try {
                JSONArray zateImgsArr = (JSONArray)JSONArray.parse(zateImgs);
                for (int i = 0; i < zateImgsArr.size(); i++) {
                    ZateLandimg zateLandimg = new ZateLandimg();
                    JSONObject jsonObj = (JSONObject)JSON.toJSON(zateImgsArr.get(i));
                    ZateImgDto zateImgDto = JSON.toJavaObject(jsonObj, ZateImgDto.class);

                    BeanUtils.copyProperties(zateImgDto,zateLandimg);
                    if (zateImgDto.getId()!=null && !"".equals(zateImgDto.getId())){
                        newImgIds.add(zateImgDto.getId());
                    }

                    zateLandimg.setCreateTime(new Date());
                    // 更新土地类载体资源成功，载体图片插入载体资源外键
                    zateLandimg.setLandId(zateLandId);
                    //时间戳，用于一对多的新增触发器记录
                    zateLandimg.setCurrentTime(zatelandCurrentTime);
                    ShiroUser user = ShiroKit.getUser();
                    if (user != null){
                        zateLandimg.setUserId(user.getId());
                        zateLandimg.setUserName(user.getName());
                    }

                    newImgList.add(zateLandimg);
                }
            } catch (Exception e) {
                RecordLogTools.error("updateLandImgs",e);
            }

            // 更新 及 添加记录
            rBool = super.insertOrUpdateBatch(newImgList);

            // 删除 及 删除记录- - 触发器可行：先更新被删除行的触发器的时间戳 --》 再触发触发器
            List<Integer> delImgIds = new ArrayList<>();
            oldImgIds.removeAll(newImgIds);//剔除得到被删除的imgids
            delImgIds.addAll(oldImgIds);
            for(Integer imgId : delImgIds){
                //先更新被删除img的时间戳 和createTime、操作人Id userId、操作人 userName 因为createTime会作为zate_history里面的createTime，即记录修改/更新时间
                ZateLandimg zateLandimg = zateLandimgService.selectById(imgId);
                zateLandimg.setCurrentTime(zatelandCurrentTime);
                zateLandimg.setCreateTime(zatelandUpdateTime);
                zateLandimg.setUserId(ShiroKit.getUser().getId());
                zateLandimg.setUserName(ShiroKit.getUser().getName());
                zateLandimgService.updateById(zateLandimg);
                //再触发删除操作
                zateLandimgService.deleteById(imgId);
            }
        }
        return rBool;
    }

    public Boolean deleteLastOne(String zatelandCurrentTime, Date zatelandUpdateTime, List<Integer> oldImgIds) {
        Boolean reBool = false;
        for(Integer imgId : oldImgIds){
            ZateLandimg zateLandimg = zateLandimgService.selectById(imgId);
            zateLandimg.setCurrentTime(zatelandCurrentTime);
            zateLandimg.setCreateTime(zatelandUpdateTime);
            zateLandimg.setUserId(ShiroKit.getUser().getId());
            zateLandimg.setUserName(ShiroKit.getUser().getName());
            zateLandimgService.updateById(zateLandimg);
            //再触发删除操作
            zateLandimgService.deleteById(imgId);
            reBool = true;
        }
        return reBool;
    }
}

