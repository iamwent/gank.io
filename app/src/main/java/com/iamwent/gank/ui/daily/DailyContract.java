package com.iamwent.gank.ui.daily;

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

interface DailyContract {

    interface View extends BaseView {
        void changeProgress(boolean isShow);

        void error();

        void showGankList(List<Gank> ganks);
    }

    interface Presenter extends BasePresenter {
        void getDaily(int year, int month, int day);
    }
}
