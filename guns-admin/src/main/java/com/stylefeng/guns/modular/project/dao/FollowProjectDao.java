package com.stylefeng.guns.modular.project.dao;

import com.stylefeng.guns.common.persistence.model.FollowProject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 项目跟踪Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
public interface FollowProjectDao {

    Long batchInsert(List<FollowProject> followProjects);

    Long deleteFollowProject(int proId);

    Long deleteFollowProjectByProId(@Param("list") List<Integer> fpIds, @Param("proId") int proId, @Param("folType") Integer folType);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> fpIds, @Param("proId") int proId, @Param("currentTime") String currentTime,
                                  @Param("userId") Integer userId,@Param("userName") String userName);

    Integer queryICDeptId();

    /**
     * add by lgg
     *  通过proId查询到 该项目的所有跟踪科室中 投促局下科室 对应角色中的科长角色
     * @param proId
     * @return
     */
    List<Integer> selectFollowProjectKZRoleIds(Integer proId);

    /**
     * add by lgg
     * 获取登录账号所拥有的 投促局下的科室 对应角色中的科长角色
     * @param ownRoleIds
     * @return
     */
    List<Integer> selectOwnTcjKZRoleIds(@Param("roleIds")List<Integer> ownRoleIds);

    // 通过proId查询到 该项目的所有跟踪科室中 投促局下科室 对应角色中的科员角色id
//    List<Integer> selectFollowProjectKeYuanRoleIds(Integer proId);
    // 通过proId查询到 该项目的所有跟踪科室中 投促局下科室 所有角色id
    List<Integer> selectFollowProjectAllRoleIds(Integer proId);

    List<Integer> selectFollowProjectKeYuanRoleIds(Integer proId);

    Integer checkProAuthByUserId(Map map);

    Integer queryDeptLv(Map map);
}
