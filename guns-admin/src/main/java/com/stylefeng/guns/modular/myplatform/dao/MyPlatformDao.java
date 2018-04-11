package com.stylefeng.guns.modular.myplatform.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.modular.myplatform.dto.PendingListDto;
import com.stylefeng.guns.modular.myplatform.dto.UpdateListDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 我的工作台Dao
 *
 * @author fengshuonan
 * @Date 2017-11-08 09:58:39
 */
public interface MyPlatformDao {

    List<Map<String,Object>> pendingList(@Param("params")Page<PendingListDto> page,@Param("deptId") Integer deptId);

    List<Map<String,Object>> updateList(@Param("params")Page<UpdateListDto> page, @Param("ids") List<Integer> deptIds);

    List<Map<String,Object>> noUpdateList(@Param("params")Page<UpdateListDto> page,@Param("ids") List<Integer> deptIds);
}
