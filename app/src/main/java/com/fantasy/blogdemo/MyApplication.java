package com.fantasy.blogdemo;

import android.app.Application;
import android.content.Context;

/**
 * MyApplication
 * <pre>
 *     author  : Fantasy
 *     version : 1.0, 2019-06-05
 *     since   : 1.0, 2019-06-05
 * </pre>
 */
public class MyApplication extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    /**
     * 获取上下文
     *
     * @return 上下文
     */
    public static Context getContext() {
        return sContext;
    }
}
