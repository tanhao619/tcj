package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.NormalProject;
import com.stylefeng.guns.modular.project.dto.*;


import java.util.List;
import java.util.Map;

/**
 * 常规项目Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface INormalProjectService extends IService<NormalProject>{
    NormalProjrctMiniDTO getDetailById(Integer id);

    Page<NormalProjrctInfoDTO> list(Map<String, Object> map);

    List<ProFollowsPersonInfoDto> getFollowsPerson(String name,Integer proId);

    List<FollowInfoDTO> followsPlat();

    List<FollowInfoDTO> followsOffice();

    List<FollowInfoDTO> followsStreet();

    int insertProject(Map<Object, Object> map);

    int updateProject(Map<Object, Object> map);

    NormalProTalkInfoDTO getProtalkByProId(Integer id);

    NormalProConventionInfoDTO getProConventionByProId(Integer id);

    List<ExportExcelDTO> exportExcel(List<Integer> deptIds,String conStart, String conEnd,
                                     String talkStartTime, String talkEndTime,String time,String conTime);

    int updateFolType(Map<String, Object> map);

    String getDeptNameById(Integer did);

    boolean checkNormalProject(Map<Object, Object> map);

    // 登录账号是否能够操作一般项目 项目管理-修改按钮
    boolean isAccountUseProjUpdateBtn(Integer normalProjectId);

    // 登录账号是否能够操作一般项目 项目管理-跟踪人员信息按钮 必须去除信息中心
    boolean isAccountProjFollowBtn(Integer normalProjectId);
    //分配跟踪人员，更新项目表
    Integer updateNomalProject(Map<String, Object> map);
}
