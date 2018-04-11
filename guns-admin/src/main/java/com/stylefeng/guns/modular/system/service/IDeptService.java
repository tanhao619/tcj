package com.stylefeng.guns.modular.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门服务
 *
 * @author fengshuonan
 * @date 2017-04-27 17:00
 */
public interface IDeptService extends IService<Dept>{

    /**
     * 删除部门
     *
     * @author stylefeng
     * @Date 2017/7/11 22:30
     */
   void deleteDept(Integer deptId);

    /**
     *通过科室 simplename、tips 确定科室
     */
    List<Dept> getDeptByNT(String simplename, String tips);

    /**
     * 获取信息中心科室
     */
    Dept getXXZXDept();

}
