package com.iamwent.gank.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iamwent.gank.R;
import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.ui.base.BaseFragment;
import com.iamwent.gank.ui.base.WebActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class CategoryFragment extends BaseFragment
        implements CategoryContract.View, CategoryAdapter.OnItemClickListener {

    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    @BindView(R.id.rv_ganks)
    RecyclerView recyclerView;

    private CategoryAdapter adapter;
    private List<Gank> ganks;
    private String type;

    private CategoryContract.Presenter presenter;

    public static CategoryFragment newInstance(String type) {

        Bundle args = new Bundle();
        args.putString(EXTRA_TYPE, type);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int provideViewLayoutResId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = getArguments();
        if (arguments != null) {
            type = arguments.getString(EXTRA_TYPE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ganks = new ArrayList<>();
        boolean isBeauty = "福利".equals(type);
        adapter = new CategoryAdapter(getContext(), ganks, isBeauty);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        presenter = new CategoryPresenter(this, GankRepository.getInstance(getContext()));
        presenter.getType(type);
    }

    @Override
    public void changeProgress(boolean isShow) {

    }

    @Override
    public void error() {

    }

    @Override
    public void showGankList(List<Gank> ganks) {
        this.ganks.addAll(ganks);
        adapter.replace(this.ganks);
    }

    @Override
    public void onItemClick(String title, String url) {
        WebActivity.start(getActivity(), title, url);
    }
}
