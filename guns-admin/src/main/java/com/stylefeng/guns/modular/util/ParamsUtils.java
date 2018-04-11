package com.stylefeng.guns.modular.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.stylefeng.guns.modular.oa.dto.UserFPDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParamsUtils {
    public static Map<String,Object> encapPara(Map<String, Object> map){
        if (map != null){
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("".equals(entry.getValue())){
                    entry.setValue(null);
                }
            }
        }
        return map;
    }
    public static Map<Object, Object> encapPara2(Map<Object, Object> map){
        if (map != null){
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if ("".equals(entry.getValue()) || "null".equals(entry.getValue())){
                    entry.setValue(null);
                }
            }
        }
        return map;
    }

    /**
     * @Author: Monkey
     * @Param:
     * @param key
     * @param map
     * @Date: Created in 16:51  2017/11/20.
     * @Description:
     */
    public static String getMap(String key, Map map) {
        Object obj = map.get(key);

        if (obj != null && obj != "null") {
            return obj.toString();
        }
        return null;
    }

    public static List<UserFPDTO> escapUserSWDTOS(String userFPDTOsStr){
        if (StringUtils.hasText(userFPDTOsStr)){
            String t = "";
            List<UserFPDTO> userFPDTOSList = new ArrayList<>();
            JSONArray userFPDTOsArry = (JSONArray)JSONArray.parse(userFPDTOsStr);
            for (int i = 0; i < userFPDTOsArry.size(); i++) {
                JSONObject jsonObj = (JSONObject) JSON.toJSON(userFPDTOsArry.get(i));
                UserFPDTO userFPDTO = JSON.toJavaObject(jsonObj, UserFPDTO.class);
                userFPDTOSList.add(userFPDTO);
            }
            return userFPDTOSList;
        }
        return null;
    }

}
