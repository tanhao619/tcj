package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProPackPosition;

import java.util.Map;

/**
 * 策划包装定位Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
public interface IProPackPositionService extends IService<ProPackPosition>{
    boolean batchMerge(int proId, Map<Object, Object> map);
}
