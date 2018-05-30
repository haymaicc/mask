package com.majian.utils.mask.util;

/**
 * Created by majian on 2017/12/13.
 */
public class MaskHelper {

    private MaskHelper() {
    }

    private static final String MASK_TOKEN = CommonConstants.maskSign;

    private static void validateIdx(String content, int begin, int end) {
        int size = content.length();
        if (begin <= 0 || begin > size) {
            throw new MaskException(String.format("content:[%s]:begin[%s]不在有效范围内[0,%s].", content, begin, size));
        }
        if (end <= 0 || end > size) {
            throw new MaskException(String.format("content:[%s]:end[%s]不在有效范围内[0,%s]", content, end, size));
        }
        if (begin > end) {
            throw new MaskException(String.format("content:[%s]:begin[%s]不能大于end[%s]", content, begin, end));
        }
    }

    public static String maskMiddle(String content, int begin, int end) {
        validateIdx(content, begin, end);
        int leftBegin = begin - 1;
        int rightEnd = end - 1;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (i >= leftBegin && i <= rightEnd) {
                result.append(MASK_TOKEN);
            } else {
                result.append(content.charAt(i));
            }
        }
        return result.toString();
    }

    public static String maskPad(String content, int leftLength, int rightLength) {
        int rightBegin = content.length() - rightLength;
        int leftEnd = leftLength;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (i >= rightBegin || i < leftEnd) {
                result.append(MASK_TOKEN);
            } else {
                result.append(content.charAt(i));
            }
        }
        return result.toString();
    }

    public static String maskLeft(String content, int length) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (i < length) {
                result.append(MASK_TOKEN);
            } else {
                result.append(content.charAt(i));
            }
        }
        return result.toString();
    }

    public static String maskRight(String content, int length) {
        int begin = content.length() - length;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < content.length(); i++) {
            if (i >= begin) {
                result.append(MASK_TOKEN);
            } else {
                result.append(content.charAt(i));
            }
        }
        return result.toString();
    }

    public static String maskAll(String content) {
        return maskLeft(content, content.length());
    }
}
