package com.stylefeng.guns.modular.project.dao;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.ProWorkProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 工作进度Dao
 *
 * @author fengshuonan
 * @Date 2017-11-30 11:20:24
 */
@Component
public interface ProWorkProcessDao {


    Integer seleMaxId(Integer proId);

    List<Map<String,Object>> searchList(@Param("page") Page page, @Param("params") Map<String, Object> map);

    boolean insert(@Param("proId") Integer proId, @Param("params") Map<Object, Object> map);
}
