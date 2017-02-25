package com.iamwent.gank.app;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public final class GankConfig {

    public static final String HOST = "http://gank.io/api/";

    public static final int MAX_COUNT = 10;

    /**
     * 干货类型：all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     */
    public enum GankType {
        ALL,
        ANDROID,
        IOS,
        VIDEO,
        GIRL,
        EXPANSION,
        WEB,
        FUNNY,
        APP
    }

    private GankConfig() {
        throw new AssertionError("NO INSTANCE!");
    }
}
