package com.stylefeng.guns.modular.zate.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ZateLand;
import com.stylefeng.guns.common.persistence.model.ZateLandimg;

/**
 * 土地载体资源土地点位图Service
 *
 * @author lgg
 * @Date 2017-11-07 23:21:32
 */
public interface IZateLandimgService extends IService<ZateLandimg>{
    Boolean addLandImgs(String zateImgs,Integer zateLandId);
    Boolean updateLandImgs(String zateImgs, Integer zateLandId,ZateLand updatedZateLang);
}
