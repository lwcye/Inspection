package com.cmcc.inspection.feature.brand.branddetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BrandDetailActivity extends MVPBaseActivity<BrandDetailContract.View, BrandDetailPresenter> implements BrandDetailContract.View {
    
    @Override
    protected BrandDetailPresenter createPresenter() {
        return new BrandDetailPresenter();
    }
    public static void start(Context context) {
        Intent starter = new Intent(context, BrandDetailActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
    }
}
