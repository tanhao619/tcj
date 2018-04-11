package com.stylefeng.guns.modular.util;

import com.stylefeng.guns.core.util.SpringContextHolder;
import com.stylefeng.guns.modular.project.dao.FollowProjectDao;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Monkey
 * @Date: Created in 10:33  2017/12/21.
 * @Description:
 */
@Component
public class DeptLvUtils {

    private FollowProjectDao followProjectDao = null;

    private static DeptLvUtils dl = null;

    public static DeptLvUtils getInstance() {
        if (dl == null) {
            dl = new DeptLvUtils();
        }
        return dl;
    }

    public FollowProjectDao getFollowProjectDao() {
        if (followProjectDao == null) {
            followProjectDao = SpringContextHolder.getBean(FollowProjectDao.class);
        }
        return followProjectDao;
    }


    public  void setIsBack (Map map) {
        Map folMap = new HashMap();
        folMap.put("proId", map.get("id"));
        folMap.put("userId", map.get("userId"));
        folMap.put("folType", map.get("from"));
        Integer deptLv = getFollowProjectDao().queryDeptLv(folMap);
        if (deptLv > 1) {
            map.put("isBack", 0);
        } else {
            map.put("isBack", 1);
        }
    }
}
