package com.stylefeng.guns.modular.zate.dao;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 土地载体资源土地点位图Dao
 *
 * @author fengshuonan
 * @Date 2017-11-07 23:21:32
 */
@Component
public interface ZateLandimgDao {

    List<Integer> selectImgIdsByLid(Integer landId);
}
