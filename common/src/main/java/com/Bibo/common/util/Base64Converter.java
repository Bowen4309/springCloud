package com.Bibo.common.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Converter {

    final  static Base64.Encoder encoder = Base64.getEncoder();
    final  static Base64.Decoder decoder = Base64.getDecoder();


    /**
     * 加密字符串
     * @param text
     * @return
     */
    public static String encode(String text){
        return encoder.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * 解密字符串
     * @param encodeText
     * @return
     */
    public static String decode(String encodeText){
        return new String(decoder.decode(encodeText),StandardCharsets.UTF_8);
    }
}
