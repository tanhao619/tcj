package com.stylefeng.guns.modular.zate.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ExpZateland;
import com.stylefeng.guns.common.persistence.model.ZateLand;
import com.stylefeng.guns.modular.util.ParamsUtils;
import com.stylefeng.guns.modular.zate.dao.ZateLandDao;
import com.stylefeng.guns.modular.zate.service.IZateLandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 土地类载体资源Service
 *
 * @author lgg
 * @Date 2017-11-07 23:07:20
 */
@Service
public class ZateLandServiceImpl extends ServiceImpl<BaseMapper<ZateLand>,ZateLand> implements IZateLandService {
    @Autowired
    private ZateLandDao zateLandDao;
    @Override
    public List<ExpZateland> selectByIds(Map<String, Object> parasMap, List<Integer> zids) {
        Map<String, Object> parasMapE = ParamsUtils.encapPara(parasMap);
        List<ExpZateland> expZatelands = zateLandDao.selectByIds(parasMapE, zids);
        return expZatelands;
    }
}
