package com.zh.young.codeeditor.util;


import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ConfigFileOperateUtil {
    public static List<String> getFileType(Context context) throws IOException, XmlPullParserException {

        InputStream stream = context.getAssets().open("fileType.xml");
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(stream,"utf-8");
        List<String> list = new ArrayList<>();

        int eventType = parser.getEventType();
        while(eventType != XmlPullParser.END_DOCUMENT){
            if(eventType == XmlPullParser.TEXT){
                String text = parser.getText().trim();
                if(!text.equals(""))
                    list.add(text);
                }
            eventType = parser.next();
        }

        return list;
    }

}
