package com.hbln.inspection.feature.accout.loginforget;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hbln.inspection.R;
import com.hbln.inspection.feature.accout.forgetpsd.ForgetPsdActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class LoginForgetActivity extends MVPBaseActivity<LoginForgetContract.View, LoginForgetPresenter> implements LoginForgetContract.View, View.OnClickListener {

    /** 下一步 */
    private Button mTvForgetNext;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginForgetActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected LoginForgetPresenter createPresenter() {
        return new LoginForgetPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget);
        initView();
    }

    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_back, 0, 0, 0)
                .setColor(Color.WHITE, 255)
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                })
                .setTitle("忘记密码");
        mTvForgetNext = (Button) findViewById(R.id.tv_forget_next);
        mTvForgetNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget_next:
                ForgetPsdActivity.start(getContext());
                finish();
                break;
        }
    }
}
