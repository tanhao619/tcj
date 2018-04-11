package com.stylefeng.guns.modular.project.dao;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.ProCategary;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.project.dto.BigExcelExportDTO;
import com.stylefeng.guns.modular.project.dto.BigProjrctInfoDTO;
import com.stylefeng.guns.modular.project.dto.ExportExcelDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


/**
 * 重大项目Dao
 *
 * @author Monkey
 * @Date 2017-11-30 11:20:23
 */
@Component
public interface BigProjectDao {

    int insertProject(Map<Object, Object> map);

    int updateProject(Map<Object, Object> map);
    List<Map<String,Object>> list(@Param("dataScope") DataScope dataScope, @Param("page") Page<BigProjrctInfoDTO> page, @Param("params") Map<String, Object> map);

    List<Map> getIndustryTypeByProId(@Param("proId")Integer proId);

    List<Map> getPackPositionByProId(@Param("proId")Integer proId);

    List<Map> getUniliableByProId(@Param("proId")Integer proId);

    List<Map> getAdviseOpeTypeByProId(@Param("proId")Integer proId);

    List<Map> getFollowPlatByProId(@Param("proId")Integer proId);

    List<Map> getBuildPlaceByProId(@Param("proId")Integer proId);

    List<BigExcelExportDTO> getBigProExcelList(@Param("deptIds") List<Integer> deptIds);

    List<BigExcelExportDTO> getBigProExcelListAdmin();

    String getWorkProcessListByTime(@Param("proid") Integer proid, @Param("startTime") String startTime,
                                            @Param("endTime") String endTime, @Param("workProcessTime") Integer workProcessTime);

    int updateFolType(Map<String, Object> map);
    //分配跟踪人员，更新项目表
    Integer updateBigProject(Map<String, Object> map);

    Integer checkBigProject(@Param("name") String name, @Param("proId") Integer proId);
}
