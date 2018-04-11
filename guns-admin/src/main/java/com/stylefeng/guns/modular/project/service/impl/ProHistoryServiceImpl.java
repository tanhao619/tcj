package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.factory.PageFactory;
import com.stylefeng.guns.common.persistence.model.ProHistory;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.modular.project.dao.ProHistoryDao;
import com.stylefeng.guns.modular.project.service.IProHistoryService;
import com.stylefeng.guns.modular.system.warpper.BaseWarpper;
import com.stylefeng.guns.modular.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 项目操作Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class ProHistoryServiceImpl extends ServiceImpl<BaseMapper<ProHistory>,ProHistory> implements IProHistoryService {
    @Autowired
    private ProHistoryDao proHistoryDao;

    @Override
    public Page<ProHistory> searchList(Map<String, Object> parasMap) {
        //去除map查询参数的为""的
        Map<String, Object> parasMapE = ParamsUtils.encapPara(parasMap);
        Page<ProHistory> page;
        if (parasMapE.get("id") != null ) {
            page = new PageFactory<ProHistory>().bigPage();
        } else {
            page = new PageFactory<ProHistory>().defaultPage();
        }
        List<Map<String, Object>> result = proHistoryDao.searchList(page, parasMapE);
        page.setRecords((List<ProHistory>) new BaseWarpper(result).warp());
        return page;
    }
}
