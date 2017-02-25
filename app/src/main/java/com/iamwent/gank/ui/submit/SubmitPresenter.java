package com.iamwent.gank.ui.submit;

import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.BaseResponse;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

class SubmitPresenter implements SubmitContract.Presenter {

    private SubmitContract.View view;
    private GankRepository repository;

    private CompositeDisposable disposable = new CompositeDisposable();

    SubmitPresenter(SubmitContract.View view, GankRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void submit(String url, String desc, String who, String type, boolean debug) {
        Map<String, String> form = new HashMap<>();
        form.put("url", url);
        form.put("desc", desc);
        form.put("who", who);
        form.put("type", type);
        form.put("debug", String.valueOf(debug));

        repository.submit(form)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        view.success();
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.error();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }
}
