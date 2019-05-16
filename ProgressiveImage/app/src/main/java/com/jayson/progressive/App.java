package com.jayson.progressive;

import android.app.Application;
import android.content.Context;

/**
 * @author Ye Jiashen
 * @date 2019/5/16
 * @description:
 */
public class App extends Application {
    /**
     * 全局上下文
     */
    private static Context ctx;

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
    }

    public static Context globalContext() {
        return ctx;
    }
}
