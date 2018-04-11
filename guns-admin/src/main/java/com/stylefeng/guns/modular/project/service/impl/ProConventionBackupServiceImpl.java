package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProConventionBackup;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProConventionBackupService;

/**
 * 履约信息备份表Service
 *
 * @author monkey
 * @Date 2017-12-21 08:52:32
 */
@Service
public class ProConventionBackupServiceImpl extends ServiceImpl<BaseMapper<ProConventionBackup>,ProConventionBackup> implements IProConventionBackupService {
}
