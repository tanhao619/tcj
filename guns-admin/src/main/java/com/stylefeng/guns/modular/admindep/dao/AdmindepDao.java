package com.stylefeng.guns.modular.admindep.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.GovaffDatamanAdmindep;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 部门信息Dao
 *
 * @author fengshuonan
 * @Date 2017-10-26 15:23:14
 */
@Component
public interface AdmindepDao {

    List<Map<String, Object>> selectByPage(@Param("page") Page page);
    List<Map<String, Object>> searchList(@Param("page") Page page, @Param("params") Map<String, Map<String, Object>> parmsMap);
}
