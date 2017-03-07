package com.iamwent.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamwent.gank.app.GankApplication;

import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by iamwent on 07/10/2016.
 *
 * @author iamwent
 * @since 07/10/2016
 */

public abstract class BaseFragment extends Fragment {

    @LayoutRes
    protected abstract int provideViewLayoutResId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.tag(getClass().getSimpleName());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(provideViewLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        GankApplication.getRefWatcher(getContext()).watch(this);
    }
}
