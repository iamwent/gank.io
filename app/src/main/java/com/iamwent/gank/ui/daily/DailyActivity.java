package com.iamwent.gank.ui.daily;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.iamwent.gank.BuildConfig;
import com.iamwent.gank.R;
import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.ui.base.BaseActivity;
import com.iamwent.gank.ui.base.WebActivity;
import com.iamwent.gank.ui.category.CategoryActivity;
import com.iamwent.gank.util.DateUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class DailyActivity extends BaseActivity
        implements DailyContract.View {

    @BindView(R.id.rv_daily)
    RecyclerView recyclerView;

    @BindView(R.id.iv_bg)
    ImageView ivBg;

    private DailyContract.Presenter presenter;
    private DailyGankAdapter adapter;

    private int year;
    private int month;
    private int day;

    public static void start(Context context) {
        Intent starter = new Intent(context, DailyActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_daily;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(false);

        List<Gank> ganks = Collections.emptyList();
        adapter = new DailyGankAdapter(this, ganks);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        ivBg.setVisibility(View.VISIBLE);

        presenter = new DailyPresenter(this, GankRepository.getInstance(this));

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DATE);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        presenter.subscribe();
        presenter.getDaily(year, month + 1, day);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.unsubscribe();
    }

    @Override
    public void changeProgress(boolean isShow) {

    }

    @Override
    public void error() {
        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGankList(List<Gank> ganks) {
        if (ganks == null || ganks.size() == 0) {
            ivBg.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            adapter.replace(ganks);
            ivBg.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_daily, menu);

        MenuItem searchMenu = menu.findItem(R.id.action_search);
        SearchManager manager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = (SearchView) searchMenu.getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        MenuItem menuItem = menu.findItem(R.id.action_by_category);
        menuItem.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_calendar:
                chooseOneDay();
                return true;
            case R.id.action_search:
                return true;
            case R.id.action_by_category:
                CategoryActivity.start(this);
                finish();
                return true;
            case R.id.action_setting:
                return true;
            case R.id.action_about:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void chooseOneDay() {

        new DatePickerDialog(this, (view, newYear, newMonth, newDay) -> {

            int ok = DateUtil.checkDateAvailability(newYear, newMonth, newDay);
            if (ok > -1) {
                Toast toast = Toast.makeText(this, ok, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                chooseOneDay();
            } else {
                year = newYear;
                month = newMonth;
                day = newDay;

                recyclerView.scrollToPosition(0);
                presenter.getDaily(year, month + 1, day);
            }

        }, year, month, day).show();
    }
}
