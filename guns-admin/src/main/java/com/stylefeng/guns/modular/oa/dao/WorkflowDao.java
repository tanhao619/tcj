package com.stylefeng.guns.modular.oa.dao;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.Role;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.oa.dto.BanjieWorkflowDTO;
import com.stylefeng.guns.modular.oa.dto.GuidangWorkflowDTO;
import com.stylefeng.guns.modular.oa.dto.WorkflowDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * OA管理Dao
 *
 * @author fengshuonan
 * @Date 2017-12-04 12:59:20
 */
@Component
public interface WorkflowDao {

    List<WorkflowDTO> list(@Param("dataScope") DataScope dataScope, @Param("page") Page<WorkflowDTO> page, @Param("params") Map<String, Object> map);

    List<WorkflowDTO> adminList(@Param("dataScope") DataScope dataScope, @Param("page") Page<WorkflowDTO> page, @Param("params") Map<String, Object> map);

    /**
     * 办结列表
     * @return
     */
    List<BanjieWorkflowDTO> banjieList(@Param("dataScope") DataScope dataScope, @Param("page") Page<BanjieWorkflowDTO> page, @Param("params") Map<String, Object> map);

    /*通过登录用户id 获取OA 【我发起的】-发起人角色 ，必须是投促局下的科室角色 */
    List<Role> getFaqiRole(Integer uid);

    //根据角色id获取办公室科长的所有账号
    List<User> selectOfficeKZs(Integer roleid);
    //根据角色id获取办公室科长的所有账号
    List<User> selectUsersByRoleId(Integer roleid);

    Integer selectExsitQJCount(@Param("params") Map<Object, Object> map);

    //根据workflowId与确定类型（销假审批）来确定销假按钮
    String selectXiaojBtnByWfwId(Integer workflowId);

    /**
     * 办公室主任的归档list
     * @param map
     * @return
     */
    List<GuidangWorkflowDTO> guidangList(@Param("dataScope") DataScope dataScope, @Param("page") Page<GuidangWorkflowDTO> page, @Param("params") Map<String, Object> map);
}
