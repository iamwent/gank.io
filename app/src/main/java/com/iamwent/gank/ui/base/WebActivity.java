package com.iamwent.gank.ui.base;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.iamwent.gank.R;

import butterknife.BindView;

/**
 * Created by iamwent on 24/02/2017.
 *
 * @author iamwent
 * @since 24/02/2017
 */

public class WebActivity extends BaseActivity {

    @BindView(R.id.srl_root)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.web)
    WebView webView;

    @BindView(R.id.pb_web)
    ProgressBar progressBar;

    private static final String EXTRA_TITLE = "EXTRA_TITLE";
    private static final String EXTRA_URL = "EXTRA_URL";

    private String title;
    private String url;

    public static void start(Activity act, String title, String url) {
        Intent starter = new Intent(act, WebActivity.class);
        starter.putExtra(EXTRA_TITLE, title);
        starter.putExtra(EXTRA_URL, url);
        act.startActivity(starter);
    }

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getIntent().getStringExtra(EXTRA_TITLE);
        url = getIntent().getStringExtra(EXTRA_URL);

        setTitle(title);

        swipeRefreshLayout.setOnRefreshListener(() -> webView.reload());

        WebSettings settings = webView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                progressBar.setProgress(newProgress);
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);

                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                shareTheUrl();
                return true;
            case R.id.action_copy_url:
                copyTheUrl();
                return true;
            case R.id.action_open_url:
                openTheUrlInBrowser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareTheUrl() {
    }

    private void copyTheUrl() {
        ClipData clipData = ClipData.newPlainText("gank_url", url);
        ClipboardManager manager = (ClipboardManager) getSystemService(
                Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(clipData);
        Toast.makeText(this, "copied: " + url, Toast.LENGTH_SHORT).show();
    }

    private void openTheUrlInBrowser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
//            Toasts.showLong(R.string.tip_open_fail);
        }
    }
}
