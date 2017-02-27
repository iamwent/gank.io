package com.iamwent.gank.ui.category;

import com.iamwent.gank.data.bean.Gank;
import com.iamwent.gank.ui.base.BasePresenter;
import com.iamwent.gank.ui.base.BaseView;

import java.util.List;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

interface CategoryContract {

    interface View extends BaseView {
        void changeProgress(boolean isShow);

        void error();

        void showGankList(List<Gank> ganks);
    }

    interface Presenter extends BasePresenter {
        void getType();

        void refresh();

        void loadMore();
    }
}
