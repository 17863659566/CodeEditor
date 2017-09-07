package com.zh.young.codeeditor.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据
 *
 */
public class SPUtil {
    /**
     * 保存的文件名
     */
    private static final String file_name = "Share_data";

    public static void putObject(Context context,String key,Object value){
        SharedPreferences.Editor editor = getEditor(context);
       switch (value.getClass().getName()){
           case "String" :
               editor.putString(key, (String) value);
               break;
           case "Integer" :
               editor.putInt(key, (Integer) value);
               break;
           case "Boolean" :
               editor.putBoolean(key, (Boolean) value);
               break;

           case "Float" :
               editor.putFloat(key, (Float) value);
               break;
           case "Long" :
               editor.putLong(key, (Long) value);
               break;
       }
        editor.commit();
    }

    public Object get(Context context,String key,Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        if("String".equals(type)){
            return sp.getString(key, (String)defaultObject);
        }
        else if("Integer".equals(type)){
            return sp.getInt(key, (Integer)defaultObject);
        }
        else if("Boolean".equals(type)){
            return sp.getBoolean(key, (Boolean)defaultObject);
        }
        else if("Float".equals(type)){
            return sp.getFloat(key, (Float)defaultObject);
        }
        else if("Long".equals(type)){
            return sp.getLong(key, (Long)defaultObject);
        }

        return null;
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sp = context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        return sp.edit();
    }

}
