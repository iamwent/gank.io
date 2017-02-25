package com.iamwent.gank.ui.submit;

import com.iamwent.gank.ui.base.BasePresenter;
import com.iamwent.gank.ui.base.BaseView;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

interface SubmitContract {

    interface View extends BaseView {

        void error();

        void success();
    }

    interface Presenter extends BasePresenter {
        void submit(String url,
                    String desc,
                    String who,
                    String type,
                    boolean debug);
    }
}
