package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProArea;

import java.util.Map;

/**
 * 项目地址及用地Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IProAreaService extends IService<ProArea>{

    boolean batchMerge(int proId, Map<Object, Object> map);
}
