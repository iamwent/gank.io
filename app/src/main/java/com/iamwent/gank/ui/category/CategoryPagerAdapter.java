package com.iamwent.gank.ui.category;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iamwent.gank.R;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TAB_TITLES;

    CategoryPagerAdapter(Context ctx, FragmentManager fm) {
        super(fm);

        this.TAB_TITLES = ctx.getResources().getStringArray(R.array.gank_types);
    }

    @Override
    public Fragment getItem(int position) {
        String category = TAB_TITLES[position];
        return CategoryFragment.newInstance(category);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }
}
