package com.stylefeng.guns.modular.project.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.datascope.DataScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 履约信息Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
public interface ProConventionDao {
    List<Map<String, Object>> searchList(@Param("page") Page page, @Param("params") Map<String, Object> map);

    Integer selectMaxId(Integer proId);
}
