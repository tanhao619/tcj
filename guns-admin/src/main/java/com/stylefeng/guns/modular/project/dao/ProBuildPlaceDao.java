package com.stylefeng.guns.modular.project.dao;
import com.stylefeng.guns.common.persistence.model.ProBuildPlace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 拟建设地点Dao
 *
 * @author fengshuonan
 * @Date 2017-11-30 11:20:23
 */
@Component
public interface ProBuildPlaceDao {
    Long updateProBuildPlace(ProBuildPlace proBuildPlace);

    Long batchInsert(List<ProBuildPlace> proBuildPlaces);

    Long deleteProBuildPlaceByProId(@Param("list") List<Integer> pbpIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> pbpIds, @Param("proId") int proId ,@Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);

}
