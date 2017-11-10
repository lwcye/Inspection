package com.cmcc.inspection.feature.model;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.hbln.lib_views.BottomPopupDialog;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ModelDetailActivity extends MVPBaseActivity<ModelContract.View, ModelPresenter> implements ModelContract.View, View.OnClickListener {

    private ImageView mIvImg;
    private ImageButton mIbImgFont;
    private ImageButton mIbImgFontShare;

    public static void start(Context context) {
        Intent starter = new Intent(context, ModelDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected ModelPresenter createPresenter() {
        return new ModelPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_font);
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
        mIvImg.setImageResource(R.drawable.temp_model_detail);
        mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mIbImgFont = (ImageButton) findViewById(R.id.ib_img_font);
        mIbImgFont.setOnClickListener(this);
        mIbImgFontShare = (ImageButton) findViewById(R.id.ib_img_font_share);
        mIbImgFontShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_img_font:
                final BottomPopupDialog fontDialog = new BottomPopupDialog(getContext(), R.layout.dialog_font, true);
                fontDialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fontDialog.cancel();
                    }
                });
                fontDialog.show();
                break;
            case R.id.ib_img_font_share:
                final BottomPopupDialog shareDialog = new BottomPopupDialog(getContext(), R.layout.dialog_share, true);
                shareDialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.cancel();
                    }
                });
                shareDialog.show();
                break;
            default:
                break;
        }
    }
}
