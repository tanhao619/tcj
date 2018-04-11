package com.stylefeng.guns.modular.zate.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ExpZatebuilding;
import com.stylefeng.guns.common.persistence.model.ZateBuilding;

import java.util.List;
import java.util.Map;

/**
 * 楼宇或闲置厂房载体资源Service
 *
 * @author lgg
 * @Date 2017-11-07 23:10:45
 */
public interface IZateBuildingService extends IService<ZateBuilding>{
    List<ExpZatebuilding> selectByIds(Map<String, Object> parasMap, List<Integer> zids);
}
