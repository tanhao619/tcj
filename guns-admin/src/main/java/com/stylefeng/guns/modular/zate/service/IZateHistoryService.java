package com.stylefeng.guns.modular.zate.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ZateHistory;

import java.util.List;

/**
 * 载体资源信息变更Service
 *
 * @author lgg
 * @Date 2017-11-07 23:19:57
 */
public interface IZateHistoryService extends IService<ZateHistory>{
    // type 1土地资源类   2楼宇或闲置厂房
    Page<ZateHistory> selectSpeciList(Integer zateId, Integer type);

    List<ZateHistory> selectHisDetail(Integer historyId);
}
