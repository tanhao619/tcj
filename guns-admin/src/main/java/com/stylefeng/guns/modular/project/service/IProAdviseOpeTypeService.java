package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProAdviseOpeType;

import java.util.Map;

/**
 * 项目实施类型管理Service
 *
 * @author monkey1
 * @Date 2017-11-16 21:06:50
 */
public interface IProAdviseOpeTypeService extends IService<ProAdviseOpeType>{
    boolean batchMerge(int proId, Map<Object, Object> map);
}
