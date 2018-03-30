package com.hbln.inspection.feature.regular;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.cmcc.lib_common.base.BaseActivity;
import com.hbln.inspection.R;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.lib_views.BottomPopupDialog;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class RegularDetailActivity extends MyActivity implements View.OnClickListener {
    
    private ImageButton mIbShaoolDetailFont;
    private ImageButton mIbShaoolDetailShare;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, RegularDetailActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_detail);
        initView();
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true)
            .setTitle("制度规定");
        mIbShaoolDetailFont = (ImageButton) findViewById(R.id.ib_shaool_detail_font);
        mIbShaoolDetailFont.setOnClickListener(this);
        mIbShaoolDetailShare = (ImageButton) findViewById(R.id.ib_shaool_detail_share);
        mIbShaoolDetailShare.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
    }
}
