package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.BigProject;
import com.stylefeng.guns.modular.project.dto.BigExcelExportDTO;
import com.stylefeng.guns.modular.project.dto.BigProDetailDTO;
import com.stylefeng.guns.modular.project.dto.BigProjrctInfoDTO;

import java.util.List;
import java.util.Map;


import java.util.Map;

/**
 * 重大项目Service
 *
 * @author monkey
 * @Date 2017-11-30 11:20:23
 */
public interface IBigProjectService extends IService<BigProject>{

    Page<BigProjrctInfoDTO> list(Map<String, Object> map);


    int insertProject(Map<Object, Object> map);

    int updateProject(Map<Object, Object> map);


    List<BigExcelExportDTO> exportExcel(List<Integer> deptIds, String startTime, String endTime, String workProcessTime);

    BigProDetailDTO getDetailById(Integer id);

    int updateFolType(Map<String, Object> map);

    // 登录账号是否能够操作大项目  项目管理-修改按钮
    boolean isAccountUseProjUpdateBtn(Integer bigProjectId);

    // 登录账号是否能够操作一般项目 项目管理-跟踪人员信息按钮 必须去除信息中心
    boolean isAccountProjFollowBtn(Integer bigProjectId);
    //分配跟踪人员，更新项目表
    Integer updateBigProject(Map<String, Object> map);

    boolean checkBigProject(Map<Object, Object> map);
}
