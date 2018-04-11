package com.stylefeng.guns.modular.project.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.core.datascope.DataScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 洽谈信息Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
public interface ProTalkDao {
    List<Map<String, Object>> searchList(@Param("page") Page page, @Param("params") Map<String, Object> parmsMap);

    Integer selecteProTalkIds(Integer proId);
}
