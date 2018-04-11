package com.stylefeng.guns.modular.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProTalk;

import java.util.List;
import java.util.Map;

/**
 * 洽谈信息Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IProTalkService extends IService<ProTalk>{
    Page<ProTalk> searchList(Map<String, Object> parasMap);

    Integer selectproTalkMaxId(Integer proId);
}
