package com.iamwent.gank.ui.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.iamwent.gank.R;
import com.iamwent.gank.ui.base.BaseActivity;

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
}
