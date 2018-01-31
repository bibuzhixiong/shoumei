package com.wanhan.shouyu.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/26.
 */

public class MapUtil {
    public static Map getMap(Map map) {

        List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            //升序排序
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }

        });
      StringBuilder sign=new StringBuilder("");
        for (Map.Entry<String, String> mapping : list) {
            if(mapping.getKey().equals("timeStamp")){
            }
            sign.append(mapping.getKey()+":"+mapping.getValue()+",");
//            Log.e("TTT", mapping.getKey() + ":" + mapping.getValue());

        }
//        Log.e("TTT", sign.toString());
//        sign.deleteCharAt(sign.length()-1);
        String time=System.currentTimeMillis()+"";
        map.put("timeStamp",time);
        map.put("sign",CheckUtils.md5(sign.append(time+"SSH3").toString()));
        return map;
    }
}
