package com.hbln.inspection.feature.workarena.workdynamic;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_network.model.WorkModel;
import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.ClickUtils;
import com.hbln.inspection.utils.TitleUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkDynamicActivity extends MVPBaseActivity<WorkDynamicContract.View, WorkDynamicPresenter> implements WorkDynamicContract.View, View.OnClickListener, TextView.OnEditorActionListener, RUAdapter.OnItemClickListener {
    
    private RecyclerView mRvWorkDynamic;
    private RUAdapter<WorkModel.WorkInfoBean> mAdapter;
    private List<WorkModel.WorkInfoBean> mList = new ArrayList<>();
    /** 请输入内容... */
    private EditText mEtWorkSeach;
    private ImageView mIvEtSearch;
    
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
        mPresenter.getData();
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true)
            .setTitle("工作动态");
        mRvWorkDynamic = (RecyclerView) findViewById(R.id.rv_work_dynamic);
        initRecylerView();
        
        mEtWorkSeach = (EditText) findViewById(R.id.et_search);
        mEtWorkSeach.setOnEditorActionListener(this);
        mIvEtSearch = (ImageView) findViewById(R.id.iv_search);
        mIvEtSearch.setOnClickListener(this);
    }
    
    private void initRecylerView() {
        mRvWorkDynamic.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RUAdapter<WorkModel.WorkInfoBean>(getContext(), mList, R.layout.item_school_item) {
            @Override
            protected void onInflateData(RUViewHolder holder, WorkModel.WorkInfoBean data, int position) {
                holder.setText(R.id.tv_item_shcool_item_title, data.title);
                holder.setText(R.id.tv_item_shcool_item_dw, data.danwei);
                holder.setText(R.id.tv_item_shcool_item_data, data.times);
                if (!TextUtils.isEmpty(data.picpath.toString())) {
                    holder.setVisibility(R.id.iv_item_shcool_item, View.VISIBLE);
                    holder.setImageNet(R.id.iv_item_shcool_item, data.picpath.toString());
                } else {
                    holder.setVisibility(R.id.iv_item_shcool_item, View.GONE);
                }
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvWorkDynamic.setAdapter(mAdapter);
    }
    
    @Override
    public void setData(List<WorkModel.WorkInfoBean> list) {
        mList = list;
        mAdapter.setData(mList);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                mPresenter.searchData(mEtWorkSeach.getText().toString().trim());
                break;
        }
    }
    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (ClickUtils.isFastClick()) {
            return true;
        }
        mPresenter.searchData(mEtWorkSeach.getText().toString().trim());
        return true;
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        WebViewContentActivity.start(getContext(), mList.get(position).id, WebViewModel.TYPE_WORK);
    }
}
