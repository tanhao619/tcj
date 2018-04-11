package com.stylefeng.guns.modular.zate.dao;
import com.stylefeng.guns.common.persistence.model.ZateProperty;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 载体性质Dao
 *
 * @author fengshuonan
 * @Date 2018-01-29 16:36:52
 */
@Component
public interface ZatePropertyDao {

    ZateProperty selectOneByTypeZateId(@Param("type") String type, @Param("zateId") Integer zateId);

    List<ZateProperty> selectDelZproperty(@Param("zateId") Integer zateId, @Param("ids") List<Integer> ids);
}
