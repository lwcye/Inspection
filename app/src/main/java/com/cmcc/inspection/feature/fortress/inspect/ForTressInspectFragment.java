package com.cmcc.inspection.feature.fortress.inspect;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.school.detail.SchoolDetailActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class ForTressInspectFragment extends MVPBaseFragment<ForTressInspectContract.View, ForTressInspectPresenter> implements ForTressInspectContract.View {
    private RecyclerView mRvFortressInspect;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    
    @Override
    protected ForTressInspectPresenter createPresenter() {
        return new ForTressInspectPresenter();
    }
    
    @Override
    public void initData() {
        initView(mView);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fortress_inspect;
    }
    
    public void initView(View view) {
        mRvFortressInspect = (RecyclerView) view.findViewById(R.id.rv_fortress_inspect);
        initRecyclerView();
    }
    
    private void initRecyclerView() {
        mRvFortressInspect.setLayoutManager(new LinearLayoutManager(getContext()));
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
                SchoolDetailActivity.start(getContext(),position+"");
            }
        });
        mRvFortressInspect.setAdapter(mAdapter);
    }
}
