package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProContractType;

import java.util.Map;

/**
 * 项目合同类型管理Service
 *
 * @author monkey1
 * @Date 2017-11-16 21:07:25
 */
public interface IProContractTypeService extends IService<ProContractType>{
    boolean batchMerge(int proId, Map<Object, Object> map);
}
