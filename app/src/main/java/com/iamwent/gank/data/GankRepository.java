package com.iamwent.gank.data;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.iamwent.gank.app.GankConfig;
import com.iamwent.gank.data.bean.BaseResponse;
import com.iamwent.gank.data.bean.DailyResult;
import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.data.bean.Record;
import com.iamwent.gank.data.bean.Search;
import com.iamwent.gank.data.local.GankLocalDataSource;
import com.iamwent.gank.data.remote.GankApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.iamwent.gank.app.GankConfig.MAX_COUNT;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */
public class GankRepository {

    private static volatile GankRepository INSTANCE = null;

    private GankApi remote;

    private GankLocalDataSource local;

    private Map<String, Record> cache;

    public static GankRepository getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (GankLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GankRepository(ctx.getApplicationContext());
                    return INSTANCE;
                }
            }
        }

        return INSTANCE;
    }

    private GankRepository(Context ctx) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankConfig.HOST)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        remote = retrofit.create(GankApi.class);

        local = GankLocalDataSource.getInstance(ctx);

        cache = new HashMap<>(20);
    }

    public Observable<DailyResult> getDaily(int year, int month, int day) {

        String key = formatKey(year, month, day);

        return remote.getDaily(year, month, day)
                .doOnNext(response -> {
                    if (response == null || response.error || response.results == null) {
                        throw new Exception("no data");
                    }
                })
                .map(response -> response.results);
    }

    public Observable<List<Gank>> getCategory(String type, int page) {
        return remote.getCategory(type, MAX_COUNT, page)
                .doOnNext(response -> {
                    if (response == null || response.error || response.results == null) {
                        throw new Exception("no data");
                    }
                })
                .map(response -> response.results);
    }

    public Observable<List<Search>> search(String key, String type, int page) {
        return remote.search(key, type, MAX_COUNT, page)
                .doOnNext(response -> {
                    if (response == null || response.error || response.results == null) {
                        throw new Exception("no data");
                    }
                })
                .map(response -> response.results);
    }

    public Observable<List<String>> history() {
        return remote.history()
                .doOnNext(response -> {
                    if (response == null || response.error || response.results == null) {
                        throw new Exception("no data");
                    }
                })
                .map(response -> response.results);
    }

    public Observable<BaseResponse> submit(Map<String, String> form) {
        return remote.submit(form);
    }

    @SuppressLint("DefaultLocale")
    private String formatKey(int year, int month, int day) {
        return String.format("%d-%d-%d", year, month, day);
    }
}