package com.stylefeng.guns.modular.project.dao;

import com.stylefeng.guns.common.persistence.model.ProArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目地址及用地Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
public interface ProAreaDao {

    Long updateProArea(ProArea proArea);

    Long batchInsert(List<ProArea> proAreas);

    Long deleteProAreaByProId(@Param("list") List<Integer> areaIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> areaIds, @Param("proId") int proId, @Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);

}
