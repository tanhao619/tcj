package com.stylefeng.guns.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProHistory;

import java.util.Map;

/**
 * 项目操作Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IProHistoryService extends IService<ProHistory>{

    Page<ProHistory> searchList(Map<String, Object> parasMap);
}
