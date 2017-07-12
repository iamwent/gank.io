package com.iamwent.gank.ui.category;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.iamwent.gank.BuildConfig;
import com.iamwent.gank.R;
import com.iamwent.gank.ui.base.BaseActivity;
import com.iamwent.gank.ui.daily.DailyActivity;

import butterknife.BindView;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class CategoryActivity extends BaseActivity {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public static void start(Context context) {
        Intent starter = new Intent(context, CategoryActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_category;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(false);

        viewPager.setAdapter(new CategoryPagerAdapter(this, getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_category, menu);

        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_by_calendar:
                DailyActivity.start(this);
                finish();
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_setting:
                return true;
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
