package com.stylefeng.guns.modular.myplatform.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.modular.myplatform.dto.PendingListDto;
import com.stylefeng.guns.modular.myplatform.dto.UpdateListDto;

/**
 * 我的工作台Service
 *
 * @author lgg
 * @Date 2017-11-08 09:58:39
 */
public interface IMyPlatformService{
    Page<PendingListDto> pendingList();
    //跟踪项目更新提醒列表
    Page<UpdateListDto> updateList();
    //项目一周未更新列表
    Page<UpdateListDto> noUpdateList();
}
