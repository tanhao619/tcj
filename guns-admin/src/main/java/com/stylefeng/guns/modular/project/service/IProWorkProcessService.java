package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProWorkProcess;
import com.baomidou.mybatisplus.plugins.Page;

import java.util.Map;

/**
 * 工作进度Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:24
 */
public interface IProWorkProcessService extends IService<ProWorkProcess>{
    Integer selectproWorkProcessMaxId(Integer proId);

    Page<ProWorkProcess> searchList(Map<String, Object> map);

    boolean batchMerge(int proId, Map<Object, Object> map);
}
