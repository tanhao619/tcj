package com.stylefeng.guns.modular.zate.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ExpZateland;
import com.stylefeng.guns.common.persistence.model.ZateLand;

import java.util.List;
import java.util.Map;

/**
 * 土地类载体资源Service
 *
 * @author lgg
 * @Date 2017-11-07 23:07:20
 */
public interface IZateLandService extends IService<ZateLand>{
    List<ExpZateland> selectByIds(Map<String, Object> parasMap, List<Integer> zids);
}
