package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProBuildPlace;

import java.util.Map;

/**
 * 拟建设地点Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
public interface IProBuildPlaceService extends IService<ProBuildPlace>{

    boolean batchMerge(int proId, Map<Object, Object> map);
}
