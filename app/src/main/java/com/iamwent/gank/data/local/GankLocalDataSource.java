package com.iamwent.gank.data.local;

import android.content.Context;

import com.iamwent.gank.BuildConfig;
import com.iamwent.gank.data.bean.Record;
import com.litesuits.orm.LiteOrm;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by iamwent on 25/02/2017.
 *
 * @author iamwent
 * @since 25/02/2017
 */
public class GankLocalDataSource {

    private static volatile GankLocalDataSource INSTANCE = null;

    private LiteOrm orm;

    public static GankLocalDataSource getInstance(Context ctx) {
        if (INSTANCE == null) {
            synchronized (GankLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new GankLocalDataSource(ctx.getApplicationContext());
                    return INSTANCE;
                }
            }
        }

        return INSTANCE;
    }

    public Observable saveRecords(List<Record> records) {
        orm.save(records);

        return Observable.empty();
    }

    public Observable<Record> getGankByDate(int year, int month, int day) {
        return Observable.create(new ObservableOnSubscribe<Record>() {
            @Override
            public void subscribe(ObservableEmitter<Record> emitter) throws Exception {
                String date = String.format(Locale.getDefault(), "%d-%d-%d", year, month, day);
                Record record = orm.queryById(date, Record.class);
                if (record != null && record.ganks != null) {
                    emitter.onNext(record);
                }
            }
        });
    }

    private GankLocalDataSource(Context ctx) {
        orm = LiteOrm.newSingleInstance(ctx, "gank.db");
        orm.setDebugged(BuildConfig.DEBUG);
    }
}
