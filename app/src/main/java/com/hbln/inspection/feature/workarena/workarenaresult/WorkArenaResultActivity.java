package com.hbln.inspection.feature.workarena.workarenaresult;


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

import com.cmcc.lib_network.model.WorkArenaModel;
import com.cmcc.lib_utils.utils.ViewUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workininspection.WorkIninspectionActivity;
import com.hbln.inspection.feature.workarena.workinspect.WorkInspectActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;


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
    private TextView mTvWorkArenaRank0;
    private TextView mTvWorkArenaRank1;
    private TextView mTvWorkArenaRank2;
    
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
        mTvWorkArenaRank0 = (TextView) findViewById(R.id.tv_work_arena_rank_0);
        mTvWorkArenaRank1 = (TextView) findViewById(R.id.tv_work_arena_rank_1);
        mTvWorkArenaRank2 = (TextView) findViewById(R.id.tv_work_arena_rank_2);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_work_result_in:
                WorkIninspectionActivity.TITLE = "派驻纪检组";
                WorkIninspectionActivity.start(getContext());
                break;
            case R.id.btn_work_result_inspection:
                WorkInspectActivity.start(getContext());
                break;
            case R.id.btn_work_result_keshi:
                WorkIninspectionActivity.TITLE = "科室";
                WorkIninspectionActivity.start(getContext());
                break;
            case R.id.btn_work_result_xiang:
                WorkIninspectionActivity.TITLE = "乡镇";
                WorkIninspectionActivity.start(getContext());
                break;
            default:
                break;
        }
    }
    
    @Override
    public void setData(WorkArenaModel arenaModel) {
        mTvWorkArenaTitle.setText(arenaModel.keshi);
        
        WorkArenaModel.InfoBean.Lanmu1Bean lanmu1 = arenaModel.info.lanmu1;
        mTvWorkArena0.setText(lanmu1.lanmu);
        if (lanmu1.nums > 0 && lanmu1.zongshu > 0) {
            mPbWorkArena0.setProgress(lanmu1.nums * 100 / lanmu1.zongshu);
        } else {
            mPbWorkArena0.setProgress(0);
        }
        mTvWorkArenaNums0.setText(String.valueOf(lanmu1.nums));
        
        if (lanmu1.paiming == 1) {
            ViewUtils.setTextDrawable(mTvWorkArenaNums0, 0, 0, R.drawable.img_work_arena_result_rank_flower, 0, getContext());
            mTvWorkArenaRank0.setBackgroundResource(R.drawable.img_work_arena_result_rank_0);
        } else if (lanmu1.paiming == 2) {
            mTvWorkArenaRank0.setBackgroundResource(R.drawable.img_work_arena_result_rank_1);
        } else if (lanmu1.paiming == 3) {
            mTvWorkArenaRank0.setBackgroundResource(R.drawable.img_work_arena_result_rank_2);
        } else {
            mTvWorkArenaRank0.setBackgroundResource(R.drawable.img_work_arena_result_rank_3);
            mTvWorkArenaRank0.setText(lanmu1.paiming + "");
        }
        
        WorkArenaModel.InfoBean.Lanmu1Bean lanmu2 = arenaModel.info.lanmu2;
        mTvWorkArena1.setText(lanmu2.lanmu);
        if (lanmu2.nums > 0 && lanmu2.zongshu > 0) {
            mPbWorkArena1.setProgress(lanmu2.nums * 100 / lanmu2.zongshu);
        } else {
            mPbWorkArena1.setProgress(0);
        }
        mTvWorkArenaNums1.setText(String.valueOf(lanmu2.nums));
        if (lanmu2.paiming == 1) {
            ViewUtils.setTextDrawable(mTvWorkArenaNums1, 0, 0, R.drawable.img_work_arena_result_rank_flower, 0, getContext());
            mTvWorkArenaRank1.setBackgroundResource(R.drawable.img_work_arena_result_rank_0);
        } else if (lanmu2.paiming == 2) {
            mTvWorkArenaRank1.setBackgroundResource(R.drawable.img_work_arena_result_rank_1);
        } else if (lanmu2.paiming == 3) {
            mTvWorkArenaRank1.setBackgroundResource(R.drawable.img_work_arena_result_rank_2);
        } else {
            mTvWorkArenaRank1.setBackgroundResource(R.drawable.img_work_arena_result_rank_3);
            mTvWorkArenaRank1.setText(lanmu2.paiming + "");
        }
        
        WorkArenaModel.InfoBean.Lanmu1Bean lanmu3 = arenaModel.info.lanmu3;
        mTvWorkArena2.setText(lanmu3.lanmu);
        if (lanmu3.nums > 0 && lanmu3.zongshu > 0) {
            mPbWorkArena2.setProgress(lanmu3.nums * 100 / lanmu3.zongshu);
        } else {
            mPbWorkArena2.setProgress(0);
        }
        mTvWorkArenaNums2.setText(String.valueOf(lanmu3.nums));
        if (lanmu3.paiming == 1) {
            ViewUtils.setTextDrawable(mTvWorkArenaNums2, 0, 0, R.drawable.img_work_arena_result_rank_flower, 0, getContext());
            mTvWorkArenaRank2.setBackgroundResource(R.drawable.img_work_arena_result_rank_0);
        } else if (lanmu3.paiming == 2) {
            mTvWorkArenaRank2.setBackgroundResource(R.drawable.img_work_arena_result_rank_1);
        } else if (lanmu3.paiming == 3) {
            mTvWorkArenaRank2.setBackgroundResource(R.drawable.img_work_arena_result_rank_2);
        } else {
            mTvWorkArenaRank2.setBackgroundResource(R.drawable.img_work_arena_result_rank_3);
            mTvWorkArenaRank2.setText(lanmu3.paiming + "");
        }
    }
}
