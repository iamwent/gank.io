package com.iamwent.gank.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by iamwent on 07/03/2017.
 *
 * @author iamwent
 * @since 07/03/2017
 */

public final class Toasts {

    @SuppressLint("StaticFieldLeak")
    private static Context ctx;


    public static void install(Context ctx) {
        Toasts.ctx = ctx.getApplicationContext();
    }

    public static void showShort(@NonNull String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    public static void showShort(@StringRes int msgId) {
        showShort(ctx.getString(msgId));
    }

    public static void showLong(@NonNull String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void showLong(@StringRes int msgId) {
        showLong(ctx.getString(msgId));
    }

    private static void show(@StringRes int msgId, int duration) {
        show(ctx.getString(msgId), duration);
    }

    private static void show(@NonNull String msg, int duration) {
        check();

        Toast.makeText(ctx, msg, duration).show();
    }

    private static void check() {
        if (ctx == null) {
            throw new IllegalArgumentException("You should install it before using");
        }
    }

    private Toasts() {
        throw new AssertionError("NO INSTANCE!");
    }
}
