package com.cmcc.inspection.feature.accout.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.accout.loginforget.LoginForgetActivity;
import com.cmcc.inspection.feature.accout.register.RegisterActivity;
import com.cmcc.inspection.feature.main.MainActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.lib_network.model.LoginModel;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View, View.OnClickListener {
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
    }

    private void initView() {
        mTvLoginSubmit = (Button) findViewById(R.id.tv_login_submit);
        mTvLoginSubmit.setOnClickListener(this);
        mTvLoginRegister = (Button) findViewById(R.id.tv_login_register);
        mTvLoginRegister.setOnClickListener(this);
        mTvLoginForget = (TextView) findViewById(R.id.tv_login_forget);
        mTvLoginForget.setOnClickListener(this);
        mEtLoginUsername = (EditText) findViewById(R.id.et_login_username);
        mEtLoginPassword = (EditText) findViewById(R.id.et_login_password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_submit:
                mPresenter.requestLogin(mEtLoginUsername.getText().toString().trim(), mEtLoginPassword.getText().toString().trim());
                break;
            case R.id.tv_login_register:
                RegisterActivity.start(getContext());
                break;
            case R.id.tv_login_forget:
                LoginForgetActivity.start(getContext());
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
}
