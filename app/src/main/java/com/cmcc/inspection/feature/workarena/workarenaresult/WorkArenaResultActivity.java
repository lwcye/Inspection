package com.cmcc.inspection.feature.workarena.workarenaresult;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workininspection.WorkIninspectionActivity;
import com.cmcc.inspection.feature.workarena.workinspect.WorkInspectActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkArenaResultActivity extends MVPBaseActivity<WorkArenaResultContract.View, WorkArenaResultPresenter> implements WorkArenaResultContract.View, View.OnClickListener {
    private ImageView mIvWorkArenaResultTop;
    private CardView mCvWorkArenaResult;
    /** 派驻纪检组 */
    private Button mBtnWorkResultIn;
    private LinearLayout mLlWorkArenaResultBtn0;
    /** 巡查机构 */
    private Button mBtnWorkResultInspection;
    
    public static void start(Context context) {
        Intent starter = new Intent(context, WorkArenaResultActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected WorkArenaResultPresenter createPresenter() {
        return new WorkArenaResultPresenter();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_arena_result);
        initView();
    }
    
    private void initView() {
        mIvWorkArenaResultTop = (ImageView) findViewById(R.id.iv_work_arena_result_top);
        mCvWorkArenaResult = (CardView) findViewById(R.id.cv_work_arena_result);
        mBtnWorkResultIn = (Button) findViewById(R.id.btn_work_result_in);
        mBtnWorkResultIn.setOnClickListener(this);
        mLlWorkArenaResultBtn0 = (LinearLayout) findViewById(R.id.ll_work_arena_result_btn_0);
        mBtnWorkResultInspection = (Button) findViewById(R.id.btn_work_result_inspection);
        mBtnWorkResultInspection.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_work_result_in:
                WorkIninspectionActivity.start(getContext());
                break;
            case R.id.btn_work_result_inspection:
                WorkInspectActivity.start(getContext());
                break;
        }
    }
}
