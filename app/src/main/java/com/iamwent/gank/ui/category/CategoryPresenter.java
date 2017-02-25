package com.iamwent.gank.ui.category;

import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.Gank;

import java.util.List;

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

class CategoryPresenter implements CategoryContract.Presenter {

    private CategoryContract.View view;
    private GankRepository repository;
    private int page = 0;

    private CompositeDisposable disposable = new CompositeDisposable();

    CategoryPresenter(CategoryContract.View view, GankRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getType(String type) {
        repository.getCategory(type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Gank>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<Gank> ganks) {
                        view.showGankList(ganks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.changeProgress(false);
                        view.error();
                    }

                    @Override
                    public void onComplete() {
                        view.changeProgress(false);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        disposable.clear();
        this.view = null;
    }
}
