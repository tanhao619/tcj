package com.stylefeng.guns.modular.zate.service;
import com.baomidou.mybatisplus.service.IService;
import com.stylefeng.guns.common.persistence.model.ZateLand;
import com.stylefeng.guns.common.persistence.model.ZateProperty;

import java.util.List;
import java.util.Map;

/**
 * 载体性质Service
 *
 * @author lgg
 * @Date 2018-01-29 16:36:52
 */
public interface IZatePropertyService extends IService<ZateProperty>{
   Boolean addLandProperty(String landPropertys,Integer zateLandId);

   List<Map<String, Object>> landPropertyMap(Integer zateLandId);

   Boolean updateZatePropertys(String landPropertys, ZateLand zateLand);
}
