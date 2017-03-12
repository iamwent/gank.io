package com.iamwent.gank.data.remote;

import com.iamwent.gank.data.bean.BaseResponse;
import com.iamwent.gank.data.bean.DailyResult;
import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.data.bean.Search;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by iamwent on 24/02/2017.
 *
 * base host: http://gank.io/api/
 * @author iamwent
 * @since 24/02/2017
 */

public interface GankApi {

    // 每日数据
    @GET("day/{year}/{month}/{day}")
    Observable<BaseResponse<DailyResult>> getDaily(@Path("year") int year,
                                                   @Path("month") int month,
                                                   @Path("day") int day);

    // 分类数据
    @GET("data/{type}/{count}/{page}")
    Observable<BaseResponse<List<Gank>>> getCategory(@Path("type") String type,
                                                     @Path("count") int count,
                                                     @Path("page") int page);

    // 搜索 API
    // hsearch/query/listview/category/Android/count/10/page/1
    @GET("search/query/{key}/category/{type}/count/{count}/page/{page}")
    Observable<BaseResponse<List<Search>>> search(@Path("key") String key,
                                                  @Path("type") String type,
                                                  @Path("count") int count,
                                                  @Path("page") int page);

    //
    @GET("day/history")
    Observable<BaseResponse<List<String>>> history();

    // 支持提交干货到审核区
    @FormUrlEncoded
    @POST("add2gank")
    Observable<BaseResponse> submit(@FieldMap Map<String, String> form);


}
