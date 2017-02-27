package com.iamwent.gank.ui.submit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iamwent.gank.BuildConfig;
import com.iamwent.gank.R;
import com.iamwent.gank.data.GankRepository;
import com.iamwent.gank.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SubmitActivity extends BaseActivity implements SubmitContract.View {

    @BindView(R.id.iv_god)
    ImageView ivGod;

    @BindView(R.id.tv_type)
    TextView tvType;

    @BindView(R.id.et_url)
    EditText etUrl;

    @BindView(R.id.et_desc)
    EditText etDesc;

    @BindView(R.id.et_who)
    EditText etWho;

    private static final String KEY_DRIVER = "submit.KEY_DRIVER";

    private SubmitContract.Presenter presenter;

    private String url;
    private String desc;
    private String who;

    private ProgressDialog dialog;

    private Handler handler = new Handler();

    private String[] types;
    private int typeIndex = 0;

    private SharedPreferences preferences;

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_submit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(false);

        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        who = preferences.getString(KEY_DRIVER, "");
        etWho.setText(who);
        etWho.setSelection(who.length());

        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && "text/plain".equals(type)) {
            handleShare(intent);
        }

        types = getResources().getStringArray(R.array.gank_types);
        tvType.setText(types[typeIndex]);

        presenter = new SubmitPresenter(this, GankRepository.getInstance(this));
    }

    private void handleShare(Intent intent) {
        url = intent.getStringExtra(Intent.EXTRA_TEXT);
        desc = intent.getStringExtra(Intent.EXTRA_SUBJECT);

        if (!TextUtils.isEmpty(url)) {
            etUrl.setText(url);
            etUrl.setSelection(url.length());
        }

        if (!TextUtils.isEmpty(desc)) {
            etDesc.setText(desc);
            etDesc.setSelection(desc.length());
        }

        etDesc.requestFocus();
    }

    @Override
    public void error() {
        dialog.dismiss();

        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success() {
        dialog.dismiss();

        Toast.makeText(this, R.string.alert_submit_success, Toast.LENGTH_SHORT).show();

        handler.postDelayed(this::finish, 1000);
    }

    @OnClick(R.id.tv_type)
    public void chooseOneType() {
        new AlertDialog.Builder(this)
                .setSingleChoiceItems(types, typeIndex, (dialog, which) -> {
                    typeIndex = which;
                    tvType.setText(types[typeIndex]);
                    dialog.dismiss();
                }).create().show();
    }

    private void send() {
        if (!check()) {
            return;
        }

        String type = tvType.getText().toString();

        presenter.submit(url, desc, who, type, BuildConfig.DEBUG);
        showDialog();
    }

    private boolean check() {
        url = etUrl.getText().toString();
        if (TextUtils.isEmpty(url)) {
            etUrl.requestFocus();
            Toast.makeText(this, R.string.alert_submit_url_empty, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!URLUtil.isHttpsUrl(url) && !URLUtil.isHttpUrl(url)) {
            etUrl.requestFocus();
            Toast.makeText(this, R.string.alert_submit_url_error, Toast.LENGTH_SHORT).show();
            return false;
        }

        desc = etDesc.getText().toString();
        if (TextUtils.isEmpty(desc) || desc.length() < 5) {
            etDesc.requestFocus();
            Toast.makeText(this, R.string.alert_submit_desc_too_short, Toast.LENGTH_SHORT).show();
            return false;
        }

        who = etWho.getText().toString();
        if (TextUtils.isEmpty(who)) {
            etWho.requestFocus();
            Toast.makeText(this, R.string.hint_submit_who, Toast.LENGTH_SHORT).show();
            return false;
        }
        preferences.edit()
                .putString(KEY_DRIVER, who)
                .apply();

        return true;
    }

    private void showDialog() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.alert_submit));
        dialog.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_submit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_submit:
                send();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
