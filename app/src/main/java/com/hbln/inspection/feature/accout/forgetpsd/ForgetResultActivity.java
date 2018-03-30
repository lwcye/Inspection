package com.hbln.inspection.feature.accout.forgetpsd;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hbln.inspection.R;
import com.hbln.inspection.base.MyActivity;
import com.hbln.inspection.feature.accout.login.LoginActivity;
import com.hbln.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ForgetResultActivity extends MyActivity implements View.OnClickListener {
    
    /** 返回登录界面 */
    private Button mTvRegisterConfirm;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, ForgetResultActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_result);
        initView();
        
        TitleUtil.attach(this)
            .setBack(true)
            .setTitle("忘记密码");
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
