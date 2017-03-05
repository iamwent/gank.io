package com.iamwent.gank.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class DailyResult {
    @SerializedName("Android")
    public List<Gank> android;

    @SerializedName("iOS")
    public List<Gank> iOS;

    @SerializedName("休息视频")
    public List<Gank> video;

    @SerializedName("福利")
    public List<Gank> girl;

    @SerializedName("拓展资源")
    public List<Gank> expansion;

    @SerializedName("前端")
    public List<Gank> web;

    @SerializedName("瞎推荐")
    public List<Gank> funny;

    @SerializedName("App")
    public List<Gank> app;

    public List<Gank> combineGanks() {
        List<Gank> ganks = new ArrayList<>(20);

        if (girl != null) {
            ganks.addAll(girl);
        }

        if (iOS != null) {
            ganks.addAll(iOS);
        }

        if (android != null) {
            ganks.addAll(android);
        }

        if (funny != null) {
            ganks.addAll(funny);
        }

        if (expansion != null) {
            ganks.addAll(expansion);
        }

        if (web != null) {
            ganks.addAll(web);
        }

        if (app != null) {
            ganks.addAll(app);
        }

        if (video != null) {
            ganks.addAll(video);
        }

        return ganks;
    }
}
