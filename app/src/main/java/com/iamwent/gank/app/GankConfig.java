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

    private GankConfig() {
        throw new AssertionError("NO INSTANCE!");
    }
}
