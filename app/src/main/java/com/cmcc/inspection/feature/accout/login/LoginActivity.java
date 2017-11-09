package com.cmcc.inspection.feature.accout.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.accout.register.RegisterActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;


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
    
    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    
    private void initView() {
        mTvLoginSubmit = (Button) findViewById(R.id.tv_login_submit);
        mTvLoginSubmit.setOnClickListener(this);
        mTvLoginRegister = (Button) findViewById(R.id.tv_login_register);
        mTvLoginRegister.setOnClickListener(this);
        mTvLoginForget = (TextView) findViewById(R.id.tv_login_forget);
        mTvLoginForget.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login_submit:
                break;
            case R.id.tv_login_register:
                RegisterActivity.start(getContext());
                break;
            case R.id.tv_login_forget:
                break;
        }
    }
}
