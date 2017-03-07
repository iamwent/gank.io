package com.iamwent.gank.app;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import com.iamwent.gank.BuildConfig;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by iamwent on 26/02/2017.
 *
 * @author iamwent
 * @since 26/02/2017
 */

public class GankApplication extends Application {

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        GankApplication application = (GankApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        toggleStrictMode(BuildConfig.DEBUG);

        // init bugly
        CrashReport.initCrashReport(getApplicationContext(), "094e5020cb", BuildConfig.DEBUG);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    private void toggleStrictMode(boolean isDebug) {
        if (!isDebug) {
            return;
        }

        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setThreadPolicy(threadPolicy);

        StrictMode.VmPolicy vmPolicy = new StrictMode.VmPolicy
                .Builder()
                .detectAll()
                .penaltyLog()
                .build();
        StrictMode.setVmPolicy(vmPolicy);
    }
}
