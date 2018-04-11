package com.stylefeng.guns.modular.zate.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.ZateHistory;
import com.stylefeng.guns.modular.zate.dao.ZateHistoryDao;
import com.stylefeng.guns.modular.zate.service.IZateHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 载体资源信息变更Service
 *
 * @author lgg
 * @Date 2017-11-07 23:19:57
 */
@Service
public class ZateHistoryServiceImpl extends ServiceImpl<BaseMapper<ZateHistory>,ZateHistory> implements IZateHistoryService {
    @Autowired
    private ZateHistoryDao zateHistoryDao;
    @Override
    public Page<ZateHistory> selectSpeciList(Integer zateId, Integer type) {
        Page<ZateHistory> page = new PageFactory<ZateHistory>().defaultPage();
        Page<ZateHistory> pageZateHis = super.selectPage(page, new EntityWrapper<ZateHistory>().eq("type", type)
                .and().eq("zateId",zateId)
                .and().eq("pid",0)
                .orderBy("createTime",false));
        return pageZateHis;
    }

    @Override
    public List<ZateHistory> selectHisDetail(Integer historyId) {
        //List<ZateHistory> hisDetailList = super.selectList(new EntityWrapper<ZateHistory>().eq("pid", historyId));
        List<ZateHistory> hisDetailList = zateHistoryDao.selectHisDetail(historyId);
        return hisDetailList;
    }
}
