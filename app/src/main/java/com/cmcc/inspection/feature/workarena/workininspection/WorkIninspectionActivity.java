package com.cmcc.inspection.feature.workarena.workininspection;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_utils.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkIninspectionActivity extends MVPBaseActivity<WorkIninspectionContract.View, WorkIninspectionPresenter> implements WorkIninspectionContract.View, RadioGroup.OnCheckedChangeListener {
    
    /** 纪律审查榜 */
    private RadioButton mRvWorkIn0;
    /** 信息数量榜 */
    private RadioButton mRvWorkIn1;
    /** 外宣数量榜 */
    private RadioButton mRvWorkIn2;
    private RecyclerView mRvWorkIn;
    private RadioGroup mRgWorkIn;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    
    @Override
    protected WorkIninspectionPresenter createPresenter() {
        return new WorkIninspectionPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, WorkIninspectionActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_in_inspection);
        initView();
    }
    
    private void initView() {
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_home, 0, 0, 0)
            .setColor(Color.WHITE, 255)
            .setRightDrawable(R.drawable.icon_work_dynamic, 0, 0, 0)
            .setRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WorkDynamicActivity.start(getContext());
                }
            })
            .setTitle("派驻纪检组");
        mRvWorkIn0 = (RadioButton) findViewById(R.id.rv_work_in_0);
        mRvWorkIn1 = (RadioButton) findViewById(R.id.rv_work_in_1);
        mRvWorkIn2 = (RadioButton) findViewById(R.id.rv_work_in_2);
        mRvWorkIn = (RecyclerView) findViewById(R.id.rv_work_in);
        mRgWorkIn = (RadioGroup) findViewById(R.id.rg_work_in);
        mRgWorkIn.setOnCheckedChangeListener(this);
        onCheckedChanged(mRgWorkIn, R.id.rv_work_in_0);
        
        initRecylerView();
    }
    
    private void initRecylerView() {
        mRvWorkIn.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.add("第四纪检组");
        mList.add("第一纪检组");
        mList.add("第二纪检组");
        mList.add("第三纪检组");
        mList.add("第五纪检组");
        mList.add("第六纪检组");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_work_in) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_work_in_name, data);
                if (position == 0) {
                    holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_0);
                    holder.setText(R.id.tv_item_work_in_num, 6 + "");
                    holder.setImageView(R.id.iv_work_in_status, R.drawable.ic_work_in_up);
                    return;
                }
                if (position == 1) {
                    holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_1);
                    holder.setText(R.id.tv_item_work_in_num, 7 + "");
                    holder.setImageView(R.id.iv_work_in_status, R.drawable.ic_work_in_down);
                    TextView name = holder.getViewById(R.id.tv_item_work_in_name);
                    ViewUtils.setTextDrawable(name, 0, 0, 0, 0, getContext());
                    return;
                }
                if (position == 2) {
                    holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_2);
                    holder.setText(R.id.tv_item_work_in_num, 9 + "");
                    holder.setImageView(R.id.iv_work_in_status, R.drawable.ic_work_in_up);
                    TextView name = holder.getViewById(R.id.tv_item_work_in_name);
                    ViewUtils.setTextDrawable(name, 0, 0, 0, 0, getContext());
                    return;
                }
                
                holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_3);
                holder.setText(R.id.iv_work_in_rank, (position + 1) + "");
                holder.setText(R.id.tv_item_work_in_num, (position + 8) + "");
                holder.setVisibility(R.id.iv_work_in_status, View.GONE);
                TextView name = holder.getViewById(R.id.tv_item_work_in_name);
                ViewUtils.setTextDrawable(name, 0, 0, 0, 0, getContext());
            }
        };
        mRvWorkIn.setAdapter(mAdapter);
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rv_work_in_0:
                break;
            case R.id.rv_work_in_1:
                break;
            case R.id.rv_work_in_2:
                break;
            default:
                break;
        }
    }
}
