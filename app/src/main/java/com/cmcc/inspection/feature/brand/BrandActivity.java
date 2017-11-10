package com.cmcc.inspection.feature.brand;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.brand.branddetail.BrandDetailActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.hbln.lib_views.DrawableCenterTextView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BrandActivity extends MVPBaseActivity<BrandContract.View, BrandPresenter> implements BrandContract.View, View.OnClickListener {
    
    /** 监督执纪\n问责平台 */
    private TextView mTvBrandTab0;
    /** 监督责任\n考核 */
    private TextView mTvBrandTab1;
    /** 三清贾汪大\n数据信息公开\n平台 */
    private TextView mTvBrandTab2;
    /** “守住底线，\n不忘初心”主\n题教育会 */
    private TextView mTvBrandTab3;
    /** 优化营商\n环境 */
    private TextView mTvBrandTab4;
    /** “月月\n谈”制度 */
    private TextView mTvBrandTab5;
    
    @Override
    protected BrandPresenter createPresenter() {
        return new BrandPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, BrandActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        initView();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                })
                .setColor(Color.WHITE, 255);
        mTvBrandTab0 = (DrawableCenterTextView) findViewById(R.id.tv_brand_tab_0);
        mTvBrandTab0.setOnClickListener(this);
        mTvBrandTab1 = (DrawableCenterTextView) findViewById(R.id.tv_brand_tab_1);
        mTvBrandTab2 = (TextView) findViewById(R.id.tv_brand_tab_2);
        mTvBrandTab3 = (TextView) findViewById(R.id.tv_brand_tab_3);
        mTvBrandTab5 = (TextView) findViewById(R.id.tv_brand_tab_5);
        mTvBrandTab1.setOnClickListener(this);
        mTvBrandTab2.setOnClickListener(this);
        mTvBrandTab3.setOnClickListener(this);
        mTvBrandTab4 = (DrawableCenterTextView) findViewById(R.id.tv_brand_tab_4);
        mTvBrandTab4.setOnClickListener(this);
        mTvBrandTab5.setOnClickListener(this);

    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_brand_tab_0:
                break;
            case R.id.tv_brand_tab_1:
                break;
            case R.id.tv_brand_tab_2:
                BrandDetailActivity.start(getContext());
                break;
            case R.id.tv_brand_tab_3:
                break;
            case R.id.tv_brand_tab_4:
                break;
            case R.id.tv_brand_tab_5:
                break;
        }
    }
}

