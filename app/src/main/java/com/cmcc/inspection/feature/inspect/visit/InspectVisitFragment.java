package com.cmcc.inspection.feature.inspect.visit;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.school.answer.AnswerActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectVisitFragment extends MVPBaseFragment<InspectVisitContract.View, InspectVisitPresenter> implements InspectVisitContract.View, RUAdapter.OnItemClickListener {
    private RecyclerView mRvInspectTrack;
    private List<String> mList = new ArrayList<>();
    private RUAdapter<String> mAdapter;
    
    @Override
    protected InspectVisitPresenter createPresenter() {
        return new InspectVisitPresenter();
    }
    
    @Override
    public void initData() {
    }
    
    @Override
    public void initView(View view) {
        mRvInspectTrack = (RecyclerView) view.findViewById(R.id.rv_inspect_visit);
        
        initRecyclerView();
    }
    
    private void initRecyclerView() {
        mRvInspectTrack.setLayoutManager(new LinearLayoutManager(getContext()));
        mList.add("0");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_school_answer_0) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                holder.setVisibility(R.id.iv_item_shcool_answer_1_status, View.GONE);
                holder.setText(R.id.tv_item_shcool_answer_1_title, "线上家访");
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvInspectTrack.setAdapter(mAdapter);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_inspect_visit;
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        AnswerActivity.start(getContext());
    }
}
