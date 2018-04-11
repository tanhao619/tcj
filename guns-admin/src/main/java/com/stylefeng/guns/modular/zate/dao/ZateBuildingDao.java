package com.stylefeng.guns.modular.zate.dao;

import com.stylefeng.guns.common.persistence.model.ExpZatebuilding;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 楼宇或闲置厂房载体资源Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 23:10:45
 */
@Component
public interface ZateBuildingDao {

    List<ExpZatebuilding> selectByIds(@Param("params") Map<String, Object> parmsMap, @Param("ids") List<Integer> ids);

}
