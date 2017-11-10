package com.cmcc.inspection.feature.model;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ModelActivity extends MVPBaseActivity<ModelContract.View, ModelPresenter> implements ModelContract.View {

    private ImageView mIvImg;

    public static void start(Context context) {
        Intent starter = new Intent(context, ModelActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected ModelPresenter createPresenter() {
        return new ModelPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
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
                .setTitle("先锋模范");
        mIvImg = (ImageView) findViewById(R.id.iv_img);
        mIvImg.setImageResource(R.drawable.temp_model);
        mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModelDetailActivity.start(getContext());
            }
        });
    }
}
