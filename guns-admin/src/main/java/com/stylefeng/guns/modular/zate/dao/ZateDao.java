package com.stylefeng.guns.modular.zate.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.Zate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 载体资源管理Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 22:59:39
 */
@Component
public interface ZateDao {
    List<Map<String, Object>> searchList(@Param("page") Page page, @Param("params") Map<String, Object> parmsMap,@Param("isDefultSort") Boolean isDefultSort);

    List<Zate> searchExportList(@Param("params") Map<String, Object> parmsMap);
}
