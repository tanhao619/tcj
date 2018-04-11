package com.stylefeng.guns.modular.project.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.persistence.model.ExportConfig;
import com.stylefeng.guns.modular.project.service.IExportConfigService;
import org.springframework.stereotype.Service;

/**
 * 导出配置Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
@Service
public class ExportConfigServiceImpl extends ServiceImpl<BaseMapper<ExportConfig>,ExportConfig> implements IExportConfigService {
}
