package com.zh.young.codeeditor.util;
import android.content.Context;


public class DisplayTranslateUtil {
    /**
     * px 2 dp 进行单位换算
     * @param context
     * @param px  需要换算的px
     * @return 转换后的dip
     */
    public static float PX2DP(Context context,int px){
        float density = context.getResources().getDisplayMetrics().density;
        return px * density + 0.5f;
    }

    /**
     * dp 2 px 进行单位换算
     * @param context
     * @param dip 需要换算的dip
     * @return  实际的像素
     */
    public static float DP2PX(Context context,float dip){
        float density = context.getResources().getDisplayMetrics().density;
        return (dip - 0.5f) / density;
    }
}
