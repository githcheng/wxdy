package com.cjam.springboot.util;

import java.util.HashMap;

/**
 * Created by jam on 2017/1/20.
 */
public class GlobalDictUtil {
    public static HashMap<String, String> dict = new HashMap<String, String>();

    public static void put(String key, String value){
        dict.put(key,value);
    }


    public static String get(String key){
        return dict.get(key);
    }
}
