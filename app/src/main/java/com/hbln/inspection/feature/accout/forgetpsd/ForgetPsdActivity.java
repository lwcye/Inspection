package com.hbln.inspection.feature.accout.forgetpsd;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ForgetPsdActivity extends MVPBaseActivity<ForgetPsdContract.View, ForgetPsdPresenter> implements ForgetPsdContract.View, View.OnClickListener {

    /** 确认 */
    private Button mTvForgetConfirm;

    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetPsdActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected ForgetPsdPresenter createPresenter() {
        return new ForgetPsdPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_forget_psd);
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
        mTvForgetConfirm = (Button) findViewById(R.id.tv_forget_confirm);
        mTvForgetConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forget_confirm:
                ForgetResultActivity.start(getContext());
                finish();
                break;
            default:
                break;
        }
    }
}
