package com.stylefeng.guns.modular.project.dao;

import com.stylefeng.guns.common.persistence.model.UnitLiable;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 责任单位Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
public interface UnitLiableDao {
    Long updateUnitLiable(UnitLiable unitLiable);

    Long batchInsert(List<UnitLiable> unitLiables);

    Long deleteUnitLiableByProId(@Param("list") List<Integer> unitLiableIds, @Param("proId") int proId, @Param("from") int from);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> unitLiableIds, @Param("proId") int proId, @Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);


}
