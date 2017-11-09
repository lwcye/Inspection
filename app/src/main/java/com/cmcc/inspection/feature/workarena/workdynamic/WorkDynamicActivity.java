package com.cmcc.inspection.feature.workarena.workdynamic;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkDynamicActivity extends MVPBaseActivity<WorkDynamicContract.View, WorkDynamicPresenter> implements WorkDynamicContract.View {
    
    private RecyclerView mRvWorkDynamic;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    
    @Override
    protected WorkDynamicPresenter createPresenter() {
        return new WorkDynamicPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, WorkDynamicActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_dynamic);
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
            .setColor(Color.WHITE, 255)
            .setTitle("工作动态");
        mRvWorkDynamic = (RecyclerView) findViewById(R.id.rv_work_dynamic);
        initRecylerView();
        
    }
    
    private void initRecylerView() {
        mRvWorkDynamic.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.add("强化措施，奋力冲刺，确保圆满完成全年目标任务");
        mList.add("旗帜鲜明讲政治 步调一致讲大局");
        mList.add("强化基层党组织功能 充分发挥战斗堡垒作用");
        mList.add("响应十九大从严治党要求，大吴街道开展“两化”先进基层党组织创建活动");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_work_dynamic) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_work_dynamic_title, data);
                if (position == 1) {
                    holder.setText(R.id.tv_item_work_dynamic_name, "巡查二组");
                    holder.setText(R.id.tv_item_work_dynamic_org, "巡查机构");
                }
                if (position == 2) {
                    holder.setText(R.id.tv_item_work_dynamic_name, "大吴办");
                    holder.setText(R.id.tv_item_work_dynamic_org, "乡镇");
                }
                if (position == 3) {
                    holder.setText(R.id.tv_item_work_dynamic_name, "大吴办");
                    holder.setText(R.id.tv_item_work_dynamic_org, "乡镇");
                }
            }
        };
        mRvWorkDynamic.setAdapter(mAdapter);
    }
}
