package com.stylefeng.guns.modular.project.dao;
import com.stylefeng.guns.common.persistence.model.ProAttachment;
import com.stylefeng.guns.common.persistence.model.ProContractType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 项目合同类型管理Dao
 *
 * @author fengshuonan
 * @Date 2017-11-16 21:07:25
 */
@Component
public interface ProContractTypeDao {

    Long updateProContractType(ProContractType proContractType);

    Long batchInsert(List<ProContractType> proContractTypes);

    Long deleteProContractTypeByProId(@Param("list") List<Integer> proContractTypeIds, @Param("proId") int proId);

    Long updateCurrentTimeByProId(@Param("list") List<Integer> proContractTypeIds, @Param("proId") int proId, @Param("currentTime") String currentTime,@Param("userId") Integer userId,@Param("userName") String userName);

}
