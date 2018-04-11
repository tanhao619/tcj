package com.stylefeng.guns.modular.util;

import com.stylefeng.guns.common.persistence.model.ZateLand;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public static String getReflecMonthValue(Object obj) throws Exception{
        Class<ZateLand> etClass = ZateLand.class;
        Object fieldValue = null;
        Field[] fields  = etClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(obj);
            fieldValue = value;
            if (value instanceof Date){
                Date date = switchTime((Date) field.get(obj));
                field.set(obj,date);
            }
        }
        if (fieldValue == null){
            return "";
        }
        return fieldValue+"";
    }

    /**
     * 处理时间
     * @param date
     * @return
     */
    private static Date switchTime(Date date){
        Date reDate = null;
        try {
            String strDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
     /*       String datestr= "Mon Aug 15 11:24:39 CST 2016";//Date的默认格式显示
            new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK)).parse(strDate);//格式化
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String sDate=sdf.format(date);*/


            //reDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reDate;
    }
}
