package com.cmcc.inspection.feature.inspect.trackdetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackDetailActivity extends MVPBaseActivity<InspectTrackDetailContract.View, InspectTrackDetailPresenter> implements InspectTrackDetailContract.View, View.OnClickListener {

    /** 轨迹追踪 */
    private TextView mBtbInspectTrackDetail;
    private ImageView mIvTrackDetail;

    @Override
    protected InspectTrackDetailPresenter createPresenter() {
        return new InspectTrackDetailPresenter();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, InspectTrackDetailActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inpect_track_detail);
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
            .setTitle("监督管理");
        mBtbInspectTrackDetail = (TextView) findViewById(R.id.btb_inspect_track_detail);
        mBtbInspectTrackDetail.setOnClickListener(this);
        mIvTrackDetail = (ImageView) findViewById(R.id.iv_track_detail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btb_inspect_track_detail:
                mIvTrackDetail.setImageResource(R.drawable.img_inspect_track_1);
                break;
        }
    }
}
