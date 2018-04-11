package com.stylefeng.guns.modular.project.dao;
import com.stylefeng.guns.common.persistence.model.ProAdviseOpeType;
import com.stylefeng.guns.common.persistence.model.ProCategary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 行业分类Dao
 *
 * @author fengshuonan
 * @Date 2017-11-30 11:20:23
 */
@Component
public interface ProCategaryDao {

    Long updateProCategary(ProCategary proCategary);

    Long batchInsert(List<ProCategary> proCategaries);

    Long deleteProCategaryByProId(@Param("list") List<Integer> cateIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> cateIds, @Param("proId") int proId ,@Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);


}
