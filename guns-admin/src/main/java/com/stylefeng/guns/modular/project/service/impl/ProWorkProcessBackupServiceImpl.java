package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProWorkProcessBackup;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProWorkProcessBackupService;

/**
 * 工作进度备份表Service
 *
 * @author monkey
 * @Date 2017-12-21 08:52:32
 */
@Service
public class ProWorkProcessBackupServiceImpl extends ServiceImpl<BaseMapper<ProWorkProcessBackup>,ProWorkProcessBackup> implements IProWorkProcessBackupService {
}
