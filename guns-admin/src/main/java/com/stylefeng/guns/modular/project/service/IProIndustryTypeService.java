package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProIndustryType;

import java.util.List;
import java.util.Map;

/**
 * 产业分类表Service
 *
 * @author monkey
 * @Date 2018-01-04 10:41:23
 */
public interface IProIndustryTypeService extends IService<ProIndustryType>{
    boolean batchMerge(int proId, Map<Object, Object> map);
//查询载体产业类型
    List<Map> selectByProId(Map<Object, Object> map);

//     myInsert(ProIndustryType proIndustryType);

//    void deleteByProId(Integer proId);

    void batchInsert(List<ProIndustryType> proIndustryTypes);
}
