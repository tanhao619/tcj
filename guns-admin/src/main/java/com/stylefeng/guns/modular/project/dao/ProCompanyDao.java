package com.stylefeng.guns.modular.project.dao;

import com.stylefeng.guns.common.persistence.model.ProCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 投资方公司Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
public interface ProCompanyDao {

    Long batchInsert(@Param("list") List<ProCompany> companies);

    List<ProCompany> getCompaniesByProId(Integer id);

    Long updateProCompany(ProCompany company);

    Long deleteProCompanyByProId(@Param("list") List<Integer> comIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> comIds, @Param("proId") int proId, @Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);
}
