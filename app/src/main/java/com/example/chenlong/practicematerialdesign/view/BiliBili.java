package com.example.chenlong.practicematerialdesign.view;

import android.app.Application;

/**
 * Created by chenlong on 2016/12/11.
 */

public class BiliBili extends Application
{
    private static BiliBili mBili;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mBili = this;
    }

    public static BiliBili getInstance()
    {
        return mBili;
    }
}
