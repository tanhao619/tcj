package com.stylefeng.guns.modular.util;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class IdsUtils {

    public static List<Integer> encapIds(String ids){
        ArrayList<Integer> reList =  new ArrayList<>();
        try {
            if (StringUtils.hasText(ids)){
                String[] splitT = ids.split(",");
                if (splitT.length >= 0){
                    for (int i = 0; i < splitT.length; i++) {
                        reList.add(Integer.parseInt(splitT[i]));
                    }
                }

            }
        } catch (Exception e) {}//do nothing
        return reList;
    }
}
