package com.stylefeng.guns.modular.project.dao;


import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.*;
import com.stylefeng.guns.core.datascope.DataScope;
import com.stylefeng.guns.modular.project.dto.ExportExcelDTO;
import com.stylefeng.guns.modular.project.dto.FollowInfoDTO;
import com.stylefeng.guns.modular.project.dto.NormalProjrctInfoDTO;
import com.stylefeng.guns.modular.project.dto.ProFollowsPersonInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 常规项目Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 14:07:08
 */
@Component
public interface NormalProjectDao {


    List<Map<String,Object>> list(@Param("dataScope") DataScope dataScope,@Param("page") Page<NormalProjrctInfoDTO> page, @Param("params") Map<String, Object> map);

    List<Integer> getIds();

    List<FollowInfoDTO> followsOffice();

    List<FollowInfoDTO> followsStreet();

    List<FollowInfoDTO> getFollowsPlat();

    List<Integer> getRoleIds(@Param("id") Integer id);

    int insertProject(Map<Object, Object> map);

    int updateProject(Map<Object, Object> map);

    List<ProAttachment> getAttachByProId(Integer proid);

    List<UnitLiable> getUnitLiableByProId(Integer proid);

    List<ProArea> getProAreasByProId(Integer proid);

    List<ProAdviseOpeType> getTypeByProId(Integer proid);

    List<Object> getFollowOffice(@Param("proid") Integer proid, @Param("folType") Integer folType);

//    List<Integer> getFollowFlat(Integer id);

    List<ExportExcelDTO> exportExcel(@Param("fields") List<String> fields, @Param("tables") Set<String> tables);

    List<ProContractType> getContractTypeByProId(Integer id);

    int updateFolType(Map<String, Object> map);

    String getDeptNameById( @Param("did") Integer did);

    List<ExportExcelDTO> getExcelList(@Param("deptIds") List<Integer> deptIds);

    Integer checkNormalProject(@Param("name") String name, @Param("proId") Integer proId);

    List<ExportExcelDTO> getExcelListAdmin();

    ExportExcelDTO getTalkListByTime(@Param("proid") Integer proid,@Param("talkStartTime") String talkStartTime,
                                     @Param("talkEndTime") String talkEndTime,@Param("dayTime") Integer dayTime);

    ExportExcelDTO getConListByTime(@Param("proid") Integer proid, @Param("conStart") String conStart,
                                    @Param("conEnd") String conEnd, @Param("conTime") Integer conTime);

    //分配跟踪人员，更新项目表
    Integer updateNomalProject(Map<String, Object> map);
}
