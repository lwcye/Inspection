package com.cmcc.inspection.feature.inspect.track;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.inspect.trackdetail.InspectTrackDetailActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class InspectTrackFragment extends MVPBaseFragment<InspectTrackContract.View, InspectTrackPresenter> implements InspectTrackContract.View {
    private RecyclerView mRvInspectTrack;
    private List<String> mList = new ArrayList<>();
    private RUAdapter<String> mAdapter;
    
    @Override
    protected InspectTrackPresenter createPresenter() {
        return new InspectTrackPresenter();
    }
    
    @Override
    public void initData() {
        initView(mView);
    }
    
    private void initView(View view) {
        mRvInspectTrack = (RecyclerView) view.findViewById(R.id.rv_inspect_track);
        
        initRecyclerView();
    }
    
    private void initRecyclerView() {
        mRvInspectTrack.setLayoutManager(new LinearLayoutManager(getContext()));
        
        mList.add("0");
        mList.add("1");
        mList.add("2");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_inspect_track) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                
            }
        };
        mAdapter.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                InspectTrackDetailActivity.start(getContext());
            }
        });
        mRvInspectTrack.setAdapter(mAdapter);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_inspect_track;
    }
}
