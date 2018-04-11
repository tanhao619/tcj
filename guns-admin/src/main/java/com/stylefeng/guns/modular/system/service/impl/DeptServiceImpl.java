package com.stylefeng.guns.modular.system.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.dao.DeptMapper;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.modular.system.dao.DeptDao;
import com.stylefeng.guns.modular.system.service.IDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<BaseMapper<Dept>,Dept> implements IDeptService {

    @Resource
    DeptMapper deptMapper;

    @Autowired
    private DeptDao deptDao;
    @Override
    public void deleteDept(Integer deptId) {

        Dept dept = deptMapper.selectById(deptId);

        Wrapper<Dept> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pids", "%[" + dept.getId() + "]%");
        List<Dept> subDepts = deptMapper.selectList(wrapper);
        for (Dept temp : subDepts) {
            temp.deleteById();
        }

        dept.deleteById();
    }

    @Override
    public List<Dept> getDeptByNT(String simplename, String tips) {
        return deptDao.getDeptByNT(simplename, tips);
    }

    @Override
    public Dept getXXZXDept() {
        List<Dept> XXZXDept = deptDao.getDeptByNT("信息服务中心", "1");
        if (!CollectionUtils.isEmpty(XXZXDept)){
            return XXZXDept.get(0);
        }
        return null;
    }
}
