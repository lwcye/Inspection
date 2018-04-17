package com.hbln.inspection.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hbln.inspection.R;
import com.hbln.inspection.base.MyActivity;
import com.hbln.inspection.feature.school.answer.AnswerActivity;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.TestListModel;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.lib_views.SimpleItemDecoration;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

public class UserTestListActivity extends MyActivity implements RUAdapter.OnItemClickListener {
    /** 列表 */
    private RecyclerView mRvTestList;
    private RUAdapter<TestListModel.InfoBean> mAdapter;
    private List<TestListModel.InfoBean> mList = new ArrayList<>();

    public static void start(Context context) {
        Intent starter = new Intent(context, UserTestListActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_test_list);
        initView();
        TitleUtil.attach(this)
                .setTitle(getTitle().toString())
                .setBack(true);
        initRecyclerView();
        loadData();
    }

    private void initRecyclerView() {
        mAdapter = new RUAdapter<TestListModel.InfoBean>(getContext(), mList, R.layout.item_table_cell) {
            @Override
            protected void onInflateData(RUViewHolder holder, TestListModel.InfoBean data, int position) {
                holder.setText(R.id.tv_item_table_cell_left, data.sjtitle);
                holder.setText(R.id.tv_item_table_cell_right, "分数：" + data.fenshu);
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvTestList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvTestList.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mRvTestList.setHasFixedSize(true);
        mRvTestList.setAdapter(mAdapter);
    }

    /**
     * 导入数据
     */
    private void loadData() {
        showLoading("");
        HttpRequest.getKaoShiService().chengjiinfo()
                .compose(NetWorkInterceptor.<TestListModel>retrySessionCreator())
                .compose(getBaseActivity().<TestListModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<TestListModel>() {
                    @Override
                    public void result(TestListModel objectModel) {
                        responseList(objectModel.info);
                    }
                }, new HttpError(this), new HttpComplete(this));
    }

    private void responseList(List<TestListModel.InfoBean> info) {
        mList = info;
        mAdapter.setData(mList);
    }

    private void initView() {
        mRvTestList = (RecyclerView) findViewById(R.id.rv_test_list);
    }

    @Override
    public void onItemClick(View view, int itemType, int position) {
        AnswerActivity.start(getContext(), mList.get(position).id, AnswerActivity.TYPE_TEST);
    }
}
