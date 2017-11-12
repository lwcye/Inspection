package com.cmcc.inspection.feature.school.item;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.school.detail.SchoolDetailActivity;
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

public class SchoolItemActivity extends MVPBaseActivity<SchoolItemContract.View, SchoolItemPresenter> implements SchoolItemContract.View {

    private RecyclerView mRvSchoolItem;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, SchoolItemActivity.class);
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
                .setTitle("清风纪语");
        mRvSchoolItem = (RecyclerView) findViewById(R.id.rv_school_item);

        initRecylerView();
    }

    private void initRecylerView() {
        mRvSchoolItem.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.add("强筋骨、明纪律 铸造执纪铁军");
        mList.add("宣传加惩处 确保“六条禁令”落地生威");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_school_item) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_shcool_item_title, data);
                if (position == 1) {
                    holder.setImageView(R.id.iv_item_shcool_item, R.drawable.img_shcool_item_video);
                }
            }
        };
        mAdapter.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                SchoolDetailActivity.start(getContext());
            }
        });
        mRvSchoolItem.setAdapter(mAdapter);
    }
}
