package com.stylefeng.guns.modular.project.dao;
import com.stylefeng.guns.common.persistence.model.ProCategary;
import com.stylefeng.guns.common.persistence.model.ProPackPosition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 策划包装定位Dao
 *
 * @author fengshuonan
 * @Date 2017-11-30 11:20:23
 */
@Component
public interface ProPackPositionDao {

    //Long updateProPackPosition(ProPackPosition proPackPosition);

    Long batchInsert(List<ProPackPosition> proPackPositions);

    Long deleteProPackPositionByProId(@Param("list") List<Integer> pppIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> pppIds, @Param("proId") int proId ,@Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);
}
