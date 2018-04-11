package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProCategary;

import java.util.Map;

/**
 * 行业分类Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
public interface IProCategaryService extends IService<ProCategary>{
    boolean batchMerge(int proId, Map<Object, Object> map);
}
