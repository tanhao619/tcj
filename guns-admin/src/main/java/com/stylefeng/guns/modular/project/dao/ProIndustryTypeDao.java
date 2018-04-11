package com.stylefeng.guns.modular.project.dao;
import com.stylefeng.guns.common.persistence.model.ProIndustryType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 产业分类表Dao
 *
 * @author fengshuonan
 * @Date 2018-01-04 10:41:23
 */
@Component
public interface ProIndustryTypeDao {
    Long updateProIndustryType(ProIndustryType proIndustryType);

    Long batchInsert(@Param("list") List<ProIndustryType> proIndustryTypes);

    Long deleteProIndustryTypeByProId(@Param("list") List<Integer> industryIds, @Param("proId") int proId, @Param("folType") int folType);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> attathIds, @Param("proId") int proId, @Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName,@Param("folType") int folType);

    List<Map> queryIndustryTypeByProId(Map map);

//    void insert(ProIndustryType proIndustryType);
}
