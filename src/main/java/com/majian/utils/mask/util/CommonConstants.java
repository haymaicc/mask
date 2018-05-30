package com.majian.utils.mask.util;


public class CommonConstants {

    /**
     * 扫描包名
     */
    public static final String nameRegular;
    /**
     * 掩码符号
     */
    public static final String maskSign;

    /**
     * 最大递归深度
     */
    public static final int maxLevel;

    static {
        nameRegular = System.getProperty("mask.regular", "^(com\\.xxx1|com\\.xxx2).*");
        maskSign = System.getProperty("mask.sign", "*");
        int level = Integer.parseInt(System.getProperty("mask.maxLevel", "10"));
        if (level < 0) {
            maxLevel = 10;
            throw new MaskException("mask.maxLevel 不能为负数");
        } else {
            maxLevel = level;
        }
    }


}
