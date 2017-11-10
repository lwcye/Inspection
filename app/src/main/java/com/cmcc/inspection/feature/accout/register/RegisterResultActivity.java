package com.cmcc.inspection.feature.accout.register;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.accout.login.LoginActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_common.base.BaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegisterResultActivity extends BaseActivity implements View.OnClickListener {
    
    /** 返回登录界面 */
    private Button mTvRegisterConfirm;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, RegisterResultActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_result);
        initView();
        
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_back, 0, 0, 0)
            .setColor(Color.WHITE, 255)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            })
            .setTitle("注册");
    }
    
    private void initView() {
        mTvRegisterConfirm = (Button) findViewById(R.id.tv_register_confirm);
        mTvRegisterConfirm.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_confirm:
                LoginActivity.start(getContext());
                finish();
                break;
        }
    }
}
