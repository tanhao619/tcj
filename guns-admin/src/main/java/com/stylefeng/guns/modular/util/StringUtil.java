package com.stylefeng.guns.modular.util;

import java.util.Random;

/**
 * @Author: Monkey
 * @Date: Created in 13:29  2017/11/30.
 * @Description:
 */
public class StringUtil {

    public static String set(Long s) {
        if (s == 0 || s == null || s.toString().length() < 2) {
            return null;
        } else {
            s = s * new Random().nextLong();
            String str = s.toString().substring(0, s.toString().length()/2) + "-" + s.toString().substring(s.toString().length()/2) ;
            return str;
        }
    }
}
