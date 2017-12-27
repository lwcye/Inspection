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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workininspection.WorkIninspectionActivity;
import com.cmcc.inspection.feature.workarena.workinspect.WorkInspectActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.lib_network.model.WorkArenaModel;
import com.cmcc.lib_utils.utils.ViewUtils;


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
    /** 科    室 */
    private Button mBtnWorkResultKeshi;
    /** 乡    镇 */
    private Button mBtnWorkResultXiang;
    /** 第四纪检组 */
    private TextView mTvWorkArenaTitle;
    /** 纪律审查: */
    private TextView mTvWorkArena0;
    private ProgressBar mPbWorkArena0;
    /** 14 */
    private TextView mTvWorkArenaNums0;
    /** 信息数量: */
    private TextView mTvWorkArena1;
    private ProgressBar mPbWorkArena1;
    /** 36 */
    private TextView mTvWorkArenaNums1;
    /** 外宣数量: */
    private TextView mTvWorkArena2;
    private ProgressBar mPbWorkArena2;
    /** 28 */
    private TextView mTvWorkArenaNums2;
    
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
        mPresenter.loadData();
    }
    
    private void initView() {
        mIvWorkArenaResultTop = (ImageView) findViewById(R.id.iv_work_arena_result_top);
        mCvWorkArenaResult = (CardView) findViewById(R.id.cv_work_arena_result);
        mBtnWorkResultIn = (Button) findViewById(R.id.btn_work_result_in);
        mBtnWorkResultIn.setOnClickListener(this);
        mLlWorkArenaResultBtn0 = (LinearLayout) findViewById(R.id.ll_work_arena_result_btn_0);
        mBtnWorkResultInspection = (Button) findViewById(R.id.btn_work_result_inspection);
        mBtnWorkResultInspection.setOnClickListener(this);
        mBtnWorkResultKeshi = (Button) findViewById(R.id.btn_work_result_keshi);
        mBtnWorkResultKeshi.setOnClickListener(this);
        mBtnWorkResultXiang = (Button) findViewById(R.id.btn_work_result_xiang);
        mBtnWorkResultXiang.setOnClickListener(this);
        mTvWorkArenaTitle = (TextView) findViewById(R.id.tv_work_arena_title);
        mTvWorkArena0 = (TextView) findViewById(R.id.tv_work_arena_0);
        mPbWorkArena0 = (ProgressBar) findViewById(R.id.pb_work_arena_0);
        mTvWorkArenaNums0 = (TextView) findViewById(R.id.tv_work_arena_nums_0);
        mTvWorkArena1 = (TextView) findViewById(R.id.tv_work_arena_1);
        mPbWorkArena1 = (ProgressBar) findViewById(R.id.pb_work_arena_1);
        mTvWorkArenaNums1 = (TextView) findViewById(R.id.tv_work_arena_nums_1);
        mTvWorkArena2 = (TextView) findViewById(R.id.tv_work_arena_2);
        mPbWorkArena2 = (ProgressBar) findViewById(R.id.pb_work_arena_2);
        mTvWorkArenaNums2 = (TextView) findViewById(R.id.tv_work_arena_nums_2);
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
            case R.id.btn_work_result_keshi:
                WorkIninspectionActivity.start(getContext());
                break;
            case R.id.btn_work_result_xiang:
                WorkInspectActivity.start(getContext());
                break;
        }
    }
    
    @Override
    public void setData(WorkArenaModel arenaModel) {
        mTvWorkArenaTitle.setText(arenaModel.keshi);
        
        WorkArenaModel.InfoBean.Lanmu1Bean lanmu1 = arenaModel.info.lanmu1;
        mTvWorkArena0.setText(lanmu1.lanmu);
        mPbWorkArena0.setProgress(lanmu1.nums / lanmu1.zongshu);
        mTvWorkArenaNums0.setText(lanmu1.nums);
        if (lanmu1.paiming == 1) {
            ViewUtils.setTextDrawable(mTvWorkArenaNums0, 0, 0, R.drawable.img_work_arena_result_rank_flower, 0, getContext());
        }
        
        WorkArenaModel.InfoBean.Lanmu1Bean lanmu2 = arenaModel.info.lanmu2;
        mTvWorkArena1.setText(lanmu2.lanmu);
        mPbWorkArena1.setProgress(lanmu2.nums / lanmu2.zongshu);
        mTvWorkArenaNums1.setText(lanmu2.nums);
        if (lanmu2.paiming == 1) {
            ViewUtils.setTextDrawable(mTvWorkArenaNums1, 0, 0, R.drawable.img_work_arena_result_rank_flower, 0, getContext());
        }
        
        WorkArenaModel.InfoBean.Lanmu1Bean lanmu3 = arenaModel.info.lanmu3;
        mTvWorkArena2.setText(lanmu3.lanmu);
        mPbWorkArena2.setProgress(lanmu3.nums / lanmu3.zongshu);
        mTvWorkArenaNums2.setText(lanmu3.nums);
        if (lanmu3.paiming == 1) {
            ViewUtils.setTextDrawable(mTvWorkArenaNums2, 0, 0, R.drawable.img_work_arena_result_rank_flower, 0, getContext());
        }
    }
}
