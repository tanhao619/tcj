package com.stylefeng.guns.modular.util;

import com.stylefeng.guns.common.constant.Constant;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.model.TimeObject;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sijianmeng
 * 字符串工具
 */
public class SearchUtil {


    /**
     * 封装查询条件
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Map<String, Object>> encapSearchConditions(Object obj,String searchTimes,String searchLikes){
        Map<String, Map<String, Object>> parmsMap = new HashMap<>();
        Map<String, Object> entityMap = new HashMap<String, Object>();
        Map<String, Object> timesMap = encapSearchTimes(searchTimes);
        Map<String, Object> likesMap = encapSearchLikes(searchLikes);
        parmsMap.put("entityMap", entityMap);
        parmsMap.put("timesMap", timesMap);
        parmsMap.put("likesMap", likesMap);
       

        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field:fields) {
                field.setAccessible(true);
                if (field.get(obj) != null && !"".equals(field.get(obj)) && !"serialVersionUID".equals(field.getName())){
                    entityMap.put(dealBigLetter(field.getName()),field.get(obj)+"");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parmsMap;
    }

    public static void main(String[] args) {
        String searchTimes = "createdTime,2013-10-30,2017-10-28|update_time,2013-10-30,2017-10-28";
        searchTimes = "createdTime,,|update_time,,";
        String[] split = searchTimes.split("\\|");
        String[] split1 = split[0].split(",");
        Map<String, Object> stringTimeObjectMap = encapSearchTimes(searchTimes);
    }
    private static Map<String, Object> encapSearchTimes(String searchTimes){
        Map<String, Object> timeMap = new HashMap<>();
        if (org.springframework.util.StringUtils.hasText(searchTimes) &&searchTimes.contains(",")){

            String[] splitT = searchTimes.split("\\|");
            for (int i = 0; i < splitT.length; i++) {
                TimeObject timeObject = new TimeObject();
                String[] splitRea = splitT[i].trim().split(",");
                if (splitRea.length >= 2){
                    for (int j = 0; j < splitRea.length; j++) {
                        timeObject.setBeginTime(dealBeginTime(splitRea[1].trim()));
                        timeObject.setEndTime(dealEndTime(splitRea[2].trim()));
                        timeMap.put(dealBigLetter(splitRea[0]),timeObject);
                    }
                }

            }
        }

        
        return timeMap;
    }


    private static Map<String, Object> encapSearchLikes(String searchLikes){
        Map<String, Object> likesMap = new HashMap<>();
        if (org.springframework.util.StringUtils.hasText(searchLikes)){

            String[] splitT = searchLikes.split(",");
            for (int i = 0; i < splitT.length; i++) {
                String[] splitRea = splitT[i].split(":",2);
                for (int j = 0; j < splitRea.length; j++) {
                    if (org.springframework.util.StringUtils.hasText(splitRea[1])){
                        likesMap.put(dealBigLetter(splitRea[0]).trim(),splitRea[1]);
                    }
                }
            }
        }
        return likesMap;
    }
    private static String dealBeginTime(String timeStr){
        return  timeStr + " 00:00:00";
    }
    private static String dealEndTime(String timeStr){
        return timeStr + " 23:59:59";
    }
    private static String dealBigLetter(String str){
        String reStr = "";
        if (org.springframework.util.StringUtils.hasText(str) && !isLetterNumZero(str)){
            while (true){
                List<Integer> intList = intList(str);
                char c = str.charAt(intList.get(0));
                String[] splitA = str.split(str.charAt(intList.get(0)) + "",2);
                char letterOne = str.charAt(intList.get(0));
                if ("".equals(splitA[0])){
                    reStr = String.valueOf(letterOne).toLowerCase() + splitA[1];
                }else{
                    reStr = splitA[0] +"_"+String.valueOf(letterOne).toLowerCase() + splitA[1];
                }
                str = reStr;
                if (isLetterNumZero(reStr) == true){
                    break;
                }
            }
        }else {
            return str;
        }
        System.out.println(reStr);
        return reStr;
    }

    private static Boolean isLetterNumZero(String str){
        Integer count = 0;
        for(int i=0;i<str.length();i++){
            if((byte)str.charAt(i)>64 &&(byte)str.charAt(i)<91) {//大写字母的Excel列号
                count++;
            }

        }
        if (count == 0){
            return true;
        }
        return false;
    }

    private  static  List<Integer> intList(String str){
        List<Integer> intList =new ArrayList<Integer>();
        for(int i=0;i<str.length();i++){
            if((byte)str.charAt(i)>64 &&(byte)str.charAt(i)<91) {//大写字母的Excel列号
                intList.add(i);
            }

        }
        return intList;
    }
}
