package com.iamwent.gank.ui.submit;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

    private SubmitContract.Presenter presenter;

    private String url;
    private String desc;
    private String type;

    private ProgressDialog dialog;

    private Handler handler = new Handler();

    private String[] types;
    private int typeIndex = 0;

    @Override
    protected int provideContentViewLayoutResId() {
        return R.layout.activity_submit;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(false);

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

        etUrl.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        etDesc.setImeOptions(EditorInfo.IME_ACTION_DONE);
    }

    @Override
    public void error() {
        dialog.dismiss();

        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void success() {
        dialog.dismiss();

        Toast.makeText(this, R.string.alert_submit, Toast.LENGTH_SHORT).show();

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

        String who = "None";

        type = tvType.getText().toString();

        presenter.submit(url, desc, who, type, BuildConfig.DEBUG);
        showDialog();
    }

    private boolean check() {
        url = etUrl.getText().toString();
        if (TextUtils.isEmpty(url)) {
            etUrl.requestFocus();
            return false;
        } else if (!URLUtil.isValidUrl(url)) {
            etUrl.requestFocus();
            return false;
        }

        desc = etDesc.getText().toString();
        if (TextUtils.isEmpty(desc) || desc.length() < 5) {
            etDesc.requestFocus();
            return false;
        }

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
