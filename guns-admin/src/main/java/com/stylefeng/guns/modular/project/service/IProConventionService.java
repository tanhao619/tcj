package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProConvention;

import java.util.Map;

/**
 * 履约信息Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IProConventionService extends IService<ProConvention>{
    Page<ProConvention> searchList(Map<String, Object> map);

    Integer selectproConventionMaxId(Integer proId);
}
