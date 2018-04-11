package com.stylefeng.guns.modular.system.dao;

import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.core.node.ZTreeNode;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 部门dao
 *
 * @author fengshuonan
 * @date 2017年2月17日20:28:58
 */
@Component
public interface DeptDao {

    /**
     * 获取ztree的节点列表
     *
     * @return
     * @date 2017年2月17日 下午8:28:43
     */
    List<ZTreeNode> tree();

    /**
     * 获取部门添加、修改的部门tree
     * @param
     * @return
     */
    List<ZTreeNode> getDeptTree();
    List<Map<String, Object>> list(@Param("condition") String condition);

    String selectMaxTips();

    /**
     * 根据登录人id 获取登录人所属的所有科室
     */
    List<Dept> selectDeptsByUserId(Integer uid);

    /**
     *  根据登录人id获取登录人科室最大权限类型
     * @param uid
     * @return
     */
    Integer selectMaxPermissByUserId(Integer uid);

    /**
     *通过科室 simplename、tips 确定科室
     */
    List<Dept> getDeptByNT(@Param("simplename")String simplename, @Param("tips")String tips);
}
