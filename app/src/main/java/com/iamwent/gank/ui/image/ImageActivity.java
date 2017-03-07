package com.iamwent.gank.ui.image;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iamwent.gank.R;
import com.iamwent.gank.ui.base.BaseActivity;
import com.iamwent.gank.util.BitmapUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class ImageActivity extends BaseActivity {

    @BindView(R.id.iv_img)
    ImageView imageView;

    private static final String EXTRA_IMG_URL = "EXTRA_IMG_URL";

    private String url;

    public static void start(Context context, String imgUrl) {
        Intent starter = new Intent(context, ImageActivity.class);
        starter.putExtra(EXTRA_IMG_URL, imgUrl);
        context.startActivity(starter);
    }

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_image;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        url = getIntent().getStringExtra(EXTRA_IMG_URL);
        Glide.with(this)
                .load(url)
                .into(imageView);

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);

        photoViewAttacher.setOnLongClickListener(v -> {
            showDialog();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_image:
                saveToLocal();
                return true;
            case R.id.action_share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.alert_save_image)
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    saveToLocal();
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    private void saveToLocal() {
        saveImageToGallery()
                .subscribe(uri -> Toast.makeText(ImageActivity.this, "saved " + uri.getPath(), Toast.LENGTH_SHORT).show());
    }

    private Observable<Uri> saveImageToGallery() {

        return new RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .filter(granted -> granted)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Boolean, ObservableSource<Uri>>() {
                    @Override
                    public ObservableSource<Uri> apply(Boolean granted) throws Exception {
                        Bitmap bitmap = imageView.getDrawingCache();
                        int start = url.lastIndexOf('/') + 1;
                        int end = url.length();
                        return BitmapUtil.saveImageToGallery(bitmap, url.substring(start, end));
                    }
                })
                .doOnNext(uri -> BitmapUtil.addImageToGallery(imageView.getContext(), uri));
    }

    private void share() {
        saveImageToGallery()
                .subscribe(uri -> {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                    shareIntent.setType("image/*");
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.app_name)));
                });
    }
}
