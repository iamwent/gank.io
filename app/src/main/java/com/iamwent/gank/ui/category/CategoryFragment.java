package com.iamwent.gank.ui.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
        implements CategoryContract.View {

    private static final String EXTRA_TYPE = "EXTRA_TYPE";

    @BindView(R.id.root)
    SwipeRefreshLayout refreshLayout;

    @BindView(R.id.rv_ganks)
    RecyclerView recyclerView;

    private CategoryAdapter adapter;
    private String type;

    private CategoryContract.Presenter presenter;
    private LinearLayoutManager layoutManager;

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

        boolean isBeauty = "福利".equals(type);
        adapter = new CategoryAdapter(getContext(), null, isBeauty);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int last = layoutManager.findLastVisibleItemPosition();
                if (last + 3 > layoutManager.getItemCount()) {
                    presenter.loadMore();
                }
            }
        });

        refreshLayout.setOnRefreshListener(() -> presenter.refresh());

        presenter = new CategoryPresenter(this, GankRepository.getInstance(getContext()), type);
        presenter.getType();
    }

    @Override
    public void changeProgress(boolean isShow) {

    }

    @Override
    public void error() {

    }

    @Override
    public void showGankList(List<Gank> ganks) {
        adapter.replace(ganks);

        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
    }
}
