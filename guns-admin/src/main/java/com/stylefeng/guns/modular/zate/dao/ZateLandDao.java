package com.stylefeng.guns.modular.zate.dao;


import com.stylefeng.guns.common.persistence.model.ExpZateland;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 土地类载体资源Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 23:07:20
 */
@Component
public interface ZateLandDao {

    List<ExpZateland> selectByIds(@Param("params") Map<String, Object> parmsMap, @Param("ids") List<Integer> ids);

}
