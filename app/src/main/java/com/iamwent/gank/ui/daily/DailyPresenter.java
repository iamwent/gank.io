package com.iamwent.gank.ui.daily;

import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.data.bean.DailyResult;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

class DailyPresenter implements DailyContract.Presenter {

    private DailyContract.View view;

    private GankRepository repository;

    private CompositeDisposable disposable = new CompositeDisposable();

    DailyPresenter(DailyContract.View view, GankRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void getDaily(int year, int month, int day) {
        repository.getDaily(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> view.changeProgress(true))
                .subscribe(new Observer<DailyResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onNext(DailyResult result) {
                        view.showGankList(result.combineGanks());
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.changeProgress(false);
                        view.error();
                        Timber.e(e, "add2gank error");
                    }

                    @Override
                    public void onComplete() {
                        view.changeProgress(false);
                    }
                });
    }

    @Override
    public void subscribe() {
        repository.history()
                .map(dates -> dates.get(0))
                .flatMap(new Function<String, ObservableSource<DailyResult>>() {
                    @Override
                    public ObservableSource<DailyResult> apply(String date) throws Exception {
                        String[] ss = date.split("-");
                        int year = Integer.parseInt(ss[0]);
                        int month = Integer.parseInt(ss[1]);
                        int day = Integer.parseInt(ss[2]);
                        return repository.getDaily(year, month, day);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DailyResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DailyResult dailyResult) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void unsubscribe() {
        disposable.clear();
        view = null;
    }
}
