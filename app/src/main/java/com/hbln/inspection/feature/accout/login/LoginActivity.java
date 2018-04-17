package com.hbln.inspection.feature.accout.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hbln.inspection.R;
import com.hbln.inspection.feature.accout.ModifyPsdActivity;
import com.hbln.inspection.feature.main.MainActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.network.model.LoginModel;
import com.tencent.tinker.lib.tinker.TinkerInstaller;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View, View.OnClickListener, TextWatcher {
    /** 登录 */
    private Button mTvLoginSubmit;
    /** 注册 */
    private Button mTvLoginRegister;
    /** 忘记密码? */
    private TextView mTvLoginForget;
    /** 请输入用户名 */
    private EditText mEtLoginUsername;
    /** 请输入密码 */
    private EditText mEtLoginPassword;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        mPresenter.initUserInfo();
        mPresenter.loadPatch();
    }

    private void initView() {
        mTvLoginSubmit = (Button) findViewById(R.id.tv_login_submit);
        mTvLoginSubmit.setOnClickListener(this);
        mTvLoginRegister = (Button) findViewById(R.id.tv_login_register);
        mTvLoginRegister.setOnClickListener(this);
        mTvLoginForget = (TextView) findViewById(R.id.tv_login_forget);
        mTvLoginForget.setOnClickListener(this);
        mEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
        mEtLoginUsername.addTextChangedListener(this);
        mEtLoginPassword = (EditText) findViewById(R.id.et_login_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_submit:
                mPresenter.requestLogin(mEtLoginUsername.getText().toString().trim(), mEtLoginPassword.getText().toString().trim());
                break;
            case R.id.tv_login_register:
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(), Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed_7zip.apk");
//                RegisterActivity.start(getContext());
                break;
            case R.id.tv_login_forget:
                ModifyPsdActivity.start(getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void resultLogin(LoginModel loginModel) {
        loginModel.saveUserInfo();
        MainActivity.start(getContext());
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
