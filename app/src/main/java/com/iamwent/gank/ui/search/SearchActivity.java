package com.iamwent.gank.ui.search;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.iamwent.gank.R;
import com.iamwent.gank.provider.GankRecentSearchProvider;
import com.iamwent.gank.ui.base.BaseActivity;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class SearchActivity extends BaseActivity {

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
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }
}
