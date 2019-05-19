package com.jayson.progressive;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * 全局线程池
     */
    private static ExecutorService es = Executors.newFixedThreadPool(1);

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = this;
    }

    public static Context globalContext() {
        return ctx;
    }

    public static ExecutorService getEs() {
        return es;
    }
}
