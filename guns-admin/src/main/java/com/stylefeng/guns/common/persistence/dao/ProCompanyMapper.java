package com.stylefeng.guns.common.persistence.dao;

import com.stylefeng.guns.common.persistence.model.ProCompany;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 投资方公司 Mapper 接口
 * </p>
 *
 * @author monkey
 * @since 2017-11-08
 */
public interface ProCompanyMapper extends BaseMapper<ProCompany> {

    List<ProCompany> getCompaniesByProId(Integer id);
}