package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.FollowProject;

import java.util.Map;

/**
 * 项目跟踪Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IFollowProjectService extends IService<FollowProject>{

    boolean batchInsertFollowProjects(int proId, Map map);

    Integer queryDeptLv(Integer proId, Integer folType);
}
