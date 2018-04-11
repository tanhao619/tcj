package com.stylefeng.guns.modular.admindep.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.GovaffDatamanAdmindep;
/**
 * 部门信息Service
 *
 * @author lgg
 * @Date 2017-10-26 15:23:14
 */
public interface IAdmindepService extends IService<GovaffDatamanAdmindep> {
    Page<GovaffDatamanAdmindep> searchList(GovaffDatamanAdmindep govaffDatamanAdmindep, String searchTimes, String searchLikes);
}
