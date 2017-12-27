package com.hbln.inspection.feature.workarena.workinspect;


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

import com.cmcc.lib_utils.utils.ViewUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workdynamic.WorkDynamicActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkInspectActivity extends MVPBaseActivity<WorkInspectContract.View, WorkInspectPresenter> implements WorkInspectContract.View, RadioGroup.OnCheckedChangeListener {
    
    /** 巡查报告 */
    private RadioButton mRbWorkInspection0;
    /** 移交线索 */
    private RadioButton mRbWorkInspection1;
    /** 信息数量 */
    private RadioButton mRbWorkInspection2;
    /** 外宣数量 */
    private RadioButton mRbWorkInspection3;
    private RadioGroup mRgWorkInspection;
    private RecyclerView mRvWorkInspection;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    
    @Override
    protected WorkInspectPresenter createPresenter() {
        return new WorkInspectPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, WorkInspectActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_inspection);
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
            .setTitle("巡察机构");
        mRbWorkInspection0 = (RadioButton) findViewById(R.id.rb_work_inspection_0);
        mRbWorkInspection1 = (RadioButton) findViewById(R.id.rb_work_inspection_1);
        mRbWorkInspection2 = (RadioButton) findViewById(R.id.rb_work_inspection_2);
        mRbWorkInspection3 = (RadioButton) findViewById(R.id.rb_work_inspection_3);
        mRgWorkInspection = (RadioGroup) findViewById(R.id.rg_work_inspection);
        mRvWorkInspection = (RecyclerView) findViewById(R.id.rv_work_inspection);
        
        mRgWorkInspection.setOnCheckedChangeListener(this);
        onCheckedChanged(mRgWorkInspection, R.id.rv_work_in_0);
        
        initRecylerView();
    }
    
    private void initRecylerView() {
        mRvWorkInspection.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.add("巡察办");
        mList.add("巡察一组");
        mList.add("巡察二组");
        mList.add("巡察三组");
        mList.add("巡察四组");
        mList.add("巡察五组");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_work_in) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_work_in_name, data);
                if (position == 0) {
                    holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_0);
                    holder.setText(R.id.tv_item_work_in_num, 6 + "");
                    return;
                }
                if (position == 1) {
                    holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_1);
                    holder.setText(R.id.tv_item_work_in_num, 7 + "");
                    TextView name = holder.getViewById(R.id.tv_item_work_in_name);
                    ViewUtils.setTextDrawable(name, 0, 0, 0, 0, getContext());
                    return;
                }
                if (position == 2) {
                    holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_2);
                    holder.setText(R.id.tv_item_work_in_num, 9 + "");
                    TextView name = holder.getViewById(R.id.tv_item_work_in_name);
                    ViewUtils.setTextDrawable(name, 0, 0, 0, 0, getContext());
                    return;
                }
                
                holder.setBackgroundResource(R.id.iv_work_in_rank, R.drawable.img_work_in_num_3);
                holder.setText(R.id.iv_work_in_rank, (position + 1) + "");
                holder.setText(R.id.tv_item_work_in_num, (position + 8) + "");
                TextView name = holder.getViewById(R.id.tv_item_work_in_name);
                ViewUtils.setTextDrawable(name, 0, 0, 0, 0, getContext());
            }
        };
        mRvWorkInspection.setAdapter(mAdapter);
    }
    
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        
    }
}
