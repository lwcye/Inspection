package com.hbln.inspection.feature.main.message;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.model.MessageModel;
import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.lib_views.SimpleItemDecoration;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MessageFragment extends MVPBaseFragment<MessageContract.View, MessagePresenter> implements MessageContract.View {
    private RecyclerView mRecyclerView;
    private List<MessageModel.InfoBean> mList = new ArrayList<>();
    private RUAdapter<MessageModel.InfoBean> mAdapter;
    
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
        loadData();
        mAdapter = new RUAdapter<MessageModel.InfoBean>(getContext(), mList, R.layout.item_message) {
            @Override
            protected void onInflateData(RUViewHolder holder, MessageModel.InfoBean data, int position) {
                holder.setText(R.id.tv_item_message_title, data.title);
                holder.setText(R.id.tv_item_message_date, data.create_time);
                holder.setText(R.id.tv_item_message_content, data.content);
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
    }
    
    /**
     * 读取数据
     */
    private void loadData() {
        showLoading("");
        HttpRequest.getUserService().tongzhilistinfo()
            .compose(getBaseActivity().<MessageModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<MessageModel>() {
                @Override
                public void result(MessageModel objectModel) {
                    setData(objectModel.info);
                }
            }, new HttpError(getBaseActivity()), new HttpComplete(getBaseActivity()));
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_message;
    }
    
    public void setData(List<MessageModel.InfoBean> data) {
        mList = data;
        mAdapter.setData(mList);
    }
}
