package com.stylefeng.guns.modular.oa.dao;
import com.stylefeng.guns.common.persistence.model.WorkflowProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * OA管理Dao
 *
 * @author fengshuonan
 * @Date 2017-12-04 20:07:17
 */
@Component
public interface WorkflowPropertyDao {

    List<WorkflowProperty> selectProByWfwId(Integer workflowId);

    List<WorkflowProperty> selectProByNameAndWfwId(@Param("params") Map<Object, Object> map);
}
