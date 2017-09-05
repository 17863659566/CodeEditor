package com.zh.young.codeeditor.util;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by young on 17-8-23.
 */

public class EncryptUtil {
    public static String encrypt(String plainText) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest(plainText.getBytes());
        StringBuilder result = new StringBuilder();
        Log.i("encrypt1","调用");
        for (byte b : bytes){
            String temp = Integer.toHexString(b & 0xff);
            if(temp.length() == 1)
                result.append("0").append(temp);
            else
                result.append(temp);

        }

        return result.toString();
    }
}
