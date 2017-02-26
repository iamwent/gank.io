package com.iamwent.gank.app;

import android.app.Application;

import com.iamwent.gank.BuildConfig;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by iamwent on 26/02/2017.
 *
 * @author iamwent
 * @since 26/02/2017
 */

public class GankApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // init bugly
        CrashReport.initCrashReport(getApplicationContext(), "094e5020cb", BuildConfig.DEBUG);
    }
}
