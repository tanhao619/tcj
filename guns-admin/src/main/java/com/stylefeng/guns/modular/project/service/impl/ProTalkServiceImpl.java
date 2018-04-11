package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.ProTalk;
import com.stylefeng.guns.common.persistence.model.Zate;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.project.dao.ProTalkDao;
import com.stylefeng.guns.modular.project.service.IProTalkService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 洽谈信息Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class ProTalkServiceImpl extends ServiceImpl<BaseMapper<ProTalk>,ProTalk> implements IProTalkService {
    @Autowired
    private ProTalkDao proTalkDao;

    @Override
    public Page<ProTalk> searchList(Map<String, Object> parasMap) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(parasMap);
        Page<ProTalk> page = new PageFactory<ProTalk>().defaultPage();
        List<Map<String, Object>> result = proTalkDao.searchList(page, parasMapE);
        page.setRecords((List<ProTalk>) new BaseWarpper(result).warp());
        return page;
    }

    @Override
    public Integer selectproTalkMaxId(Integer proId) {
        Integer proTalkMaxId=proTalkDao.selecteProTalkIds(proId);
        return proTalkMaxId;
    }
}
