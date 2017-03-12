package com.iamwent.gank.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.iamwent.gank.R;
import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.Search;
import com.iamwent.gank.provider.GankRecentSearchProvider;
import com.iamwent.gank.ui.base.BaseActivity;

import java.lang.annotation.Retention;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {

    @Retention(SOURCE)
    @StringDef({
            ALL,
            ANDROID,
            IOS,
            VIDEO,
            GIRL,
            EXPANSION,
            FRONT_END,
            FUNNY,
            APP
    })
    public @interface Type {
    }

    public static final String ALL = "all";
    public static final String ANDROID = "Android";
    public static final String IOS = "iOS";
    public static final String VIDEO = "休息视频";
    public static final String GIRL = "福利";
    public static final String EXPANSION = "拓展资源";
    public static final String FRONT_END = "前端";
    public static final String FUNNY = "瞎推荐";
    public static final String APP = "App";


    @BindView(R.id.rv_search_result)
    RecyclerView recyclerView;

    private SearchResultAdapter adapter;
    private SearchContract.Presenter presenter;

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
            String query = getIntent().getStringExtra(SearchManager.QUERY);

            Toast.makeText(this, "query", Toast.LENGTH_SHORT).show();

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    GankRecentSearchProvider.AUTHORITY, GankRecentSearchProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }

        initView();

        presenter = new SearchPresenter(this, GankRepository.getInstance(this));
        presenter.subscribe();
    }

    private void initView() {
        adapter = new SearchResultAdapter(this, Collections.emptyList());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();

        super.onDestroy();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    public void changeProgress(boolean isShow) {

    }

    @Override
    public void error() {

    }

    @Override
    public void showSearchResults(List<Search> results) {

    }
}
