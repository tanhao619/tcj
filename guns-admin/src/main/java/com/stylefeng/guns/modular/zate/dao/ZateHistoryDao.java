package com.stylefeng.guns.modular.zate.dao;

import com.stylefeng.guns.common.persistence.model.ZateHistory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 载体资源信息变更Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 23:19:57
 */
@Component
public interface ZateHistoryDao {

    List<ZateHistory> selectHisDetail(Integer hisId);
}
