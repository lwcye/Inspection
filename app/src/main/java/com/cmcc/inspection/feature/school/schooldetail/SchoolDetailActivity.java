package com.cmcc.inspection.feature.school.schooldetail;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.hbln.lib_views.BottomPopupDialog;
import com.hbln.lib_views.DrawableCenterTextView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolDetailActivity extends MVPBaseActivity<SchoolDetailContract.View, SchoolDetailPresenter> implements SchoolDetailContract.View, View.OnClickListener {
    
    /** 当前数量：1234 */
    private DrawableCenterTextView mTvSchoolDetailZan;
    /** 当前数量：0 */
    private DrawableCenterTextView mTvSchoolDetailCai;
    private ImageButton mIbShaoolDetailFont;
    private ImageButton mIbShaoolDetailShare;
    
    @Override
    protected SchoolDetailPresenter createPresenter() {
        return new SchoolDetailPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, SchoolDetailActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        initView();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_back, 0, 0, 0)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            })
            .setColor(Color.WHITE, 255);
        mTvSchoolDetailZan = (DrawableCenterTextView) findViewById(R.id.tv_school_detail_zan);
        mTvSchoolDetailZan.setOnClickListener(this);
        mTvSchoolDetailCai = (DrawableCenterTextView) findViewById(R.id.tv_school_detail_cai);
        mTvSchoolDetailCai.setOnClickListener(this);
        mIbShaoolDetailFont = (ImageButton) findViewById(R.id.ib_shaool_detail_font);
        mIbShaoolDetailFont.setOnClickListener(this);
        mIbShaoolDetailShare = (ImageButton) findViewById(R.id.ib_shaool_detail_share);
        mIbShaoolDetailShare.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_school_detail_zan:
                break;
            case R.id.tv_school_detail_cai:
                break;
            case R.id.ib_shaool_detail_font:
                final BottomPopupDialog fontDialog = new BottomPopupDialog(getContext(), R.layout.dialog_font, true);
                fontDialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fontDialog.cancel();
                    }
                });
                fontDialog.show();
                break;
            case R.id.ib_shaool_detail_share:
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
