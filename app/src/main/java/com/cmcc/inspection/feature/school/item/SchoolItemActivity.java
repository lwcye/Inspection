package com.cmcc.inspection.feature.school.item;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.school.detail.SchoolDetailActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.utils.ClickUtils;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.model.SchoolModel;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolItemActivity extends MVPBaseActivity<SchoolItemContract.View, SchoolItemPresenter> implements SchoolItemContract.View, TextView.OnEditorActionListener, View.OnClickListener {
    public static final String INTENT_INDEX = "index";
    private int index = 0;
    private RecyclerView mRvSchoolItem;
    private RUAdapter<SchoolModel.SchoolInfo> mAdapter;
    private List<SchoolModel.SchoolInfo> mList = new ArrayList<>();
    /** 请输入内容... */
    private EditText mEtShcoolItemSeach;
    private ImageView mIvEtSearch;
    
    public static void start(Context context, int index) {
        Intent starter = new Intent(context, SchoolItemActivity.class);
        starter.putExtra(INTENT_INDEX, index);
        context.startActivity(starter);
    }
    
    @Override
    protected SchoolItemPresenter createPresenter() {
        return new SchoolItemPresenter();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_item);
        index = getIntent().getIntExtra(INTENT_INDEX, 0);
        initView();
        mPresenter.loadData(index);
    }
    
    private void initView() {
        String title = "清风纪语";
        if (index == 1) {
            title = "我的业务我来讲";
        }
        if (index == 2) {
            title = "毛遂论坛";
        }
        TitleUtil.attach(this)
            .setBack(true)
            .setTitle(title);
        mRvSchoolItem = (RecyclerView) findViewById(R.id.rv_school_item);
        mEtShcoolItemSeach = (EditText) findViewById(R.id.et_search);
        mEtShcoolItemSeach.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        mEtShcoolItemSeach.setOnEditorActionListener(this);
        initRecylerView();
        mIvEtSearch = (ImageView) findViewById(R.id.iv_search);
        mIvEtSearch.setOnClickListener(this);
    }
    
    private void initRecylerView() {
        mRvSchoolItem.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RUAdapter<SchoolModel.SchoolInfo>(getContext(), mList, R.layout.item_school_item) {
            @Override
            protected void onInflateData(RUViewHolder holder, SchoolModel.SchoolInfo data, int position) {
                holder.setText(R.id.tv_item_shcool_item_title, data.title);
                holder.setText(R.id.tv_item_shcool_item_data, data.times);
                holder.setText(R.id.tv_item_shcool_item_dw, data.author);
                if (!TextUtils.isEmpty(data.pic)) {
                    holder.setVisibility(R.id.iv_item_shcool_item, View.VISIBLE);
                    holder.setImageNet(R.id.iv_item_shcool_item, data.pic);
                } else {
                    holder.setVisibility(R.id.iv_item_shcool_item, View.GONE);
                }
            }
        };
        mAdapter.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                SchoolDetailActivity.start(getContext(), mList.get(position).id);
            }
        });
        mRvSchoolItem.setAdapter(mAdapter);
    }
    
    @Override
    public void setData(SchoolModel schoolModel) {
        mList = schoolModel.info;
        mAdapter.setData(mList);
    }
    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (ClickUtils.isFastClick()) {
            return true;
        }
        mPresenter.searchData(index, mEtShcoolItemSeach.getText().toString().trim());
        return true;
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                mPresenter.searchData(index, mEtShcoolItemSeach.getText().toString().trim());
                break;
        }
    }
}
