package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.UnitLiable;

import java.util.Map;

/**
 * 责任单位Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IUnitLiableService extends IService<UnitLiable>{

    boolean batchMerge(int proId, Map<Object, Object> map);
}
