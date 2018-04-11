package com.stylefeng.guns.modular.util;

import java.lang.reflect.Field;

public class ReflectUtils {

    public static Object dealModelNull(Object et) {
        Class<?> aClass = et.getClass();
        Field[] fields  = aClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getType() == String.class && field.get(et) == null){
                    field.set(et,"");
                }
            }
        } catch(Exception e) {
           return et;
        }
        return et;
    }
}
