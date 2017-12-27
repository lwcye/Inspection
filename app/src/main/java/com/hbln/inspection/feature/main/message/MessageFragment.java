package com.hbln.inspection.feature.main.message;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.lib_views.SimpleItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View {
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private RUAdapter<String> mAdapter;
    
    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }
    
    @Override
    public void initView(View view) {
        ((TextView) view.findViewById(R.id.center)).setText("消息");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_message);
    }
    
    @Override
    public void initData() {
        mList.add("贾汪区旅游宣传口号网络投票入口");
        mList.add("全区纪检干部组织收听收看中央宣讲团党的十九大精神报告会的通知");
        mList.add("贾汪区旅游宣传口号网络投票入口");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_message) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_message, data);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }
}
