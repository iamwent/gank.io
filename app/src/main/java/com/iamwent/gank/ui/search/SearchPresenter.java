package com.iamwent.gank.ui.search;

import android.support.annotation.NonNull;

import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.Search;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by iamwent on 12/03/2017.
 *
 * @author iamwent
 * @since 12/03/2017
 */

class SearchPresenter implements SearchContract.Presenter {

    private CompositeDisposable disposable = new CompositeDisposable();

    private final SearchContract.View view;
    private final GankRepository repository;

    private List<Search> searches;
    private int page = 1;

    private String key;
    private String type;

    SearchPresenter(SearchContract.View view, GankRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        disposable.clear();
    }

    @Override
    public void search(@NonNull String newKey, @NonNull String newType) {
        key = newKey;
        type = newType;

        if (searches != null) {
            searches.clear();
        }
        page = 1;

        repository.search(key, type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.changeProgress(true))
                .subscribe(new Observer<List<Search>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<Search> newSearches) {
                        searches = newSearches;
                        view.showSearchResults(searches);
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
    public void loadMore() {
        page += 1;

        repository.search(key, type, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.changeProgress(true))
                .subscribe(new Observer<List<Search>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(List<Search> newSearches) {
                        searches.addAll(newSearches);
                        view.showSearchResults(searches);
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
}
