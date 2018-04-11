package com.stylefeng.guns.modular.project.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ProCompany;

import java.util.Map;

/**
 * 投资方公司Service
 *
 * @author monkey
 * @Date 2017-11-07 14:07:08
 */
public interface IProCompanyService extends IService<ProCompany>{

    boolean batchInsertCompanys(int proId, Map map);
}
