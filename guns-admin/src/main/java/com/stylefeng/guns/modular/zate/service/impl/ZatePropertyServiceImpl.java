package com.stylefeng.guns.modular.zate.service.impl;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.persistence.model.ZateLand;
import com.stylefeng.guns.common.persistence.model.ZateProperty;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.Convert;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.zate.dao.ZatePropertyDao;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stylefeng.guns.modular.zate.service.IZatePropertyService;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 载体性质Service
 *
 * @author lgg
 * @Date 2018-01-29 16:36:52
 */
@Service
public class ZatePropertyServiceImpl extends ServiceImpl<BaseMapper<ZateProperty>, ZateProperty> implements IZatePropertyService {
    @Autowired
    ZatePropertyDao zatePropertyDao;

    @Override
    public Boolean addLandProperty(String landPropertys, Integer zateLandId) {
        Integer[] propertys = Convert.toIntArray(landPropertys);
        List<ZateProperty> zateProperties = new ArrayList<>();
        if (propertys != null && propertys.length > 0) {
            for (Integer property : propertys) {
                ZateProperty zateProperty = new ZateProperty();
                zateProperty.setZateId(zateLandId);
                zateProperty.setCreateTime(new Date());
                zateProperty.setType(property + "");
                zateProperty.setUserId(ShiroKit.getUser().getId());
                zateProperty.setUserName(ShiroKit.getUser().getName());
                zateProperties.add(zateProperty);

            }
            return super.insertBatch(zateProperties);
        } else {
            return true;
        }

    }

    @Override
    public List<Map<String, Object>> landPropertyMap(Integer zateLandId) {
        List<ZateProperty> zatePropertList = super.selectList(new EntityWrapper<ZateProperty>().eq("zateId", zateLandId));
        List<Map<String, Object>> propertyMaps = null;
        try {
            propertyMaps = listToListMap(zatePropertList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return propertyMaps;
    }

    @Override
    public Boolean updateZatePropertys(String landPropertys, ZateLand zateLand) {
        Integer[] propertys = Convert.toIntArray(landPropertys);
        Integer zateId = zateLand.getId();
        String currentTime = zateLand.getCurrentTime();

        // 删除的用地性质
        List<Integer> zpropers = Arrays.asList(propertys);
        List<ZateProperty> delZpropers = zatePropertyDao.selectDelZproperty(zateId, zpropers);
        if (!CollectionUtils.isEmpty(delZpropers)){
            for (ZateProperty delZproper : delZpropers) {
                updateZateProperty(delZproper, currentTime);
                super.deleteById(delZproper.getId());
            }
        }
        // 新增的用地性质
        if (propertys != null && propertys.length > 0) {
            for (Integer property : propertys) {

                ZateProperty zateProperty = zatePropertyDao.selectOneByTypeZateId(property + "", zateId);
                if (ToolUtil.isEmpty(zateProperty)){
                    insertZateProperty(property, zateId, currentTime);
                }
            }
        }
        return null;
    }


    private static List<Map<String, Object>> listToListMap(List objList) throws IllegalAccessException {

        List<Map<String, Object>> reList = new ArrayList<Map<String, Object>>();
        for (Object obj : objList) {
            Map<String, Object> map = new HashMap<>();
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (Constant.ZATE_LAND_PROPERTY_TYEP.equals(fieldName) || Constant.ZATE_LAND_PROPERTY_ZATEID.equals(fieldName)) {
                    Object value = field.get(obj);
                    map.put(fieldName, value);
                }
            }
            reList.add(map);
        }
        return reList;
    }

    private void insertZateProperty(Integer property, Integer zateId,String currentTime){
        ZateProperty zateProperty = newZateProperty(property, zateId, currentTime);
        super.insert(zateProperty);
    }
    private void updateZateProperty(ZateProperty zateProperty,String currentTime){
        zateProperty.setCurrentTime(currentTime);
        zateProperty.setUserName(ShiroKit.getUser().getName());
        zateProperty.setUserId(ShiroKit.getUser().getId());
        zateProperty.setCreateTime(new Date());
        super.updateById(zateProperty);
    }
    private ZateProperty newZateProperty(Integer property, Integer zateId,String currentTime){
        ZateProperty zateProperty = new ZateProperty();
        zateProperty.setZateId(zateId);
        zateProperty.setCreateTime(new Date());
        zateProperty.setType(property + "");
        zateProperty.setUserId(ShiroKit.getUser().getId());
        zateProperty.setUserName(ShiroKit.getUser().getName());
        zateProperty.setCurrentTime(currentTime);
        return zateProperty;
    }
}
