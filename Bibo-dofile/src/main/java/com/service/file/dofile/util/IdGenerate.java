package com.service.file.dofile.util;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * UUID生成
 */
public class IdGenerate {

    private static SecureRandom random = new SecureRandom();

    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.  32位长度。
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long , 19位长度.
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    public static void main(String[] args) {
        System.out.println(IdGenerate.uuid());
        System.out.println(randomLong());
    }

}