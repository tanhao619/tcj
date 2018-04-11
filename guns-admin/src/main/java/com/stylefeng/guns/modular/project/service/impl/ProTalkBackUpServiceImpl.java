package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ProTalkBackup;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.project.service.IProTalkBackUpService;

/**
 * 洽谈信息备份表Service
 *
 * @author monkey
 * @Date 2017-12-21 08:52:32
 */
@Service
public class ProTalkBackUpServiceImpl extends ServiceImpl<BaseMapper<ProTalkBackup>,ProTalkBackup> implements IProTalkBackUpService {
}
