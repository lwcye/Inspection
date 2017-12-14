package com.cmcc.inspection.feature.fortress.manager;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressManagerFragment extends MVPBaseFragment<FortressManagerContract.View, FortressManagerPresenter> implements FortressManagerContract.View {
    private RecyclerView mRecyclerView;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    
    @Override
    protected FortressManagerPresenter createPresenter() {
        return null;
    }
    
    @Override
    public void initData() {
    }
    
    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_fortress_manager);
        initRecyclerVIew();
    
        ((ImageView) view.findViewById(R.id.iv_temp)).setImageResource(R.drawable.temp_fortress_manager);
    }
    
    private void initRecyclerVIew() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_fortress_manager) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                ExpandableListView listView = holder.getViewById(R.id.elv_item_fortress_manager);
            }
        };
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fortress_manager;
    }
}
