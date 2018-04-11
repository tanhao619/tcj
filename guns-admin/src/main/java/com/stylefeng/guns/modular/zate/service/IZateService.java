package com.stylefeng.guns.modular.zate.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Zate;
import com.stylefeng.guns.common.persistence.model.ZateHistory;

import java.util.List;
import java.util.Map;

/**
 * 载体资源管理Service
 *
 * @author lgg
 * @Date 2017-11-07 22:59:39
 */
public interface IZateService extends IService<Zate>{
    Page<Zate> searchList(Map<String, Object> parasMap);

    List<Zate> searchExportList(Map<String, Object> parasMap);

    List<ZateHistory> exportExcel();
}
