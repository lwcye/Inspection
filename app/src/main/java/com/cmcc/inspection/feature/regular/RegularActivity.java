package com.cmcc.inspection.feature.regular;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegularActivity extends MVPBaseActivity<RegularContract.View, RegularPresenter> implements RegularContract.View, View.OnClickListener {
    
    private LinearLayout mLlRagular0;
    private LinearLayout mLlRagular1;
    private LinearLayout mLlRagular2;
    
    @Override
    protected RegularPresenter createPresenter() {
        return new RegularPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, RegularActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular);
        initView();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
            .setColor(Color.WHITE, 255)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkDynamicActivity.start(getContext());
                }
            })
            .setTitle("制度规定");
        mLlRagular0 = (LinearLayout) findViewById(R.id.ll_ragular_0);
        mLlRagular0.setOnClickListener(this);
        mLlRagular1 = (LinearLayout) findViewById(R.id.ll_ragular_1);
        mLlRagular1.setOnClickListener(this);
        mLlRagular2 = (LinearLayout) findViewById(R.id.ll_ragular_2);
        mLlRagular2.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_ragular_0:
            case R.id.ll_ragular_1:
            case R.id.ll_ragular_2:
                RegularDetailActivity.start(getContext());
        }
    }
}
