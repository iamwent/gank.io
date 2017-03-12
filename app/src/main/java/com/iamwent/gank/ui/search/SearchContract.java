package com.iamwent.gank.ui.search;

import android.support.annotation.NonNull;

import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.data.bean.Search;
import com.iamwent.gank.ui.base.BasePresenter;
import com.iamwent.gank.ui.base.BaseView;

import java.util.List;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

interface SearchContract {

    interface View extends BaseView {
        void changeProgress(boolean isShow);

        void error();

        void showSearchResults(List<Search> results);
    }

    interface Presenter extends BasePresenter {
        void search(@NonNull String key, @NonNull String type);

        void loadMore();
    }
}
