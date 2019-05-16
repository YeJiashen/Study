package com.jayson.progressive.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * @author Ye Jiashen
 * @date 2019/5/16
 * @description: 渐进式图片加载工具类
 */
public class ProgressiveUtils {
    public static Bitmap getTempBitmap(byte[] byteArr, int offset) {
        byte[] temp = new byte[offset];
        System.arraycopy(byteArr, 0, temp, 0, offset);
        temp[offset - 2] = -1;
        temp[offset - 1] = -39;
        return BitmapFactory.decodeByteArray(temp, 0, offset);
    }
}
