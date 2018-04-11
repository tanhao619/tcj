package com.stylefeng.guns.modular.project.dao;
import com.stylefeng.guns.common.persistence.model.ProAdviseOpeType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目实施类型管理Dao
 *
 * @author fengshuonan
 * @Date 2017-11-16 21:06:50
 */
@Component
public interface ProAdviseOpeTypeDao {

    Long updateProAdviseOpeType(ProAdviseOpeType proAdviseOpeType);

    Long batchInsert(List<ProAdviseOpeType> proAdviseOpeTypes);

    Long deleteProAdviseOpeTypeByProId(@Param("list") List<Integer> adviseIds, @Param("proId") int proId, @Param("from") int from);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> adviseIds, @Param("proId") int proId ,@Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);

}
