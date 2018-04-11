package com.stylefeng.guns.modular.oa.dao;
import com.stylefeng.guns.common.persistence.model.WorkflowConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 项目管理Dao
 *
 * @author fengshuonan
 * @Date 2017-12-17 23:40:08
 */
@Component
public interface WorkflowConfigDao {

    List<WorkflowConfig> selectWconfigByTD(@Param("parms") Map<String, Object> map);
}
