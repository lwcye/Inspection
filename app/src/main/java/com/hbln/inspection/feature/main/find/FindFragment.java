package com.hbln.inspection.feature.main.find;


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

public class FindFragment extends MVPBaseFragment<FindContract.View, FindPresenter> implements FindContract.View, RUAdapter.OnItemClickListener {
    private RecyclerView mRecyclerView;
    private List<String> mList = new ArrayList<>();
    private RUAdapter<String> mAdapter;
    
    @Override
    protected FindPresenter createPresenter() {
        return new FindPresenter();
    }
    
    @Override
    public void initView(View view) {
        ((TextView) view.findViewById(R.id.center)).setText("发现");
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_find);
    }
    
    @Override
    public void initData() {
        mList.add("贾汪区旅游宣传口号网络投票入口");
        mList.add("全区纪检干部组织收听收看中央宣讲团党的十九大精神报告会的通知");
        mList.add("贾汪区旅游宣传口号网络投票入口");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_school_item) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {
                if (position == 0) {
                    holder.setImageView(R.id.iv_item_shcool_item, R.drawable.img_fortress_banner);
                    holder.setText(R.id.tv_item_shcool_item_title, "关于讲好中国故事，习近平总书记这样说");
                }
                if (position == 1) {
                    holder.setImageView(R.id.iv_item_shcool_item, R.drawable.img_shcool_item_img);
                    holder.setText(R.id.tv_item_shcool_item_title, "抓住党的建设着力点，焕发全党蓬勃活力");
                }
                if (position == 2) {
                    holder.setImageView(R.id.iv_item_shcool_item, R.drawable.img_fortress_news_0);
                    holder.setText(R.id.tv_item_shcool_item_title, "江苏省委宣讲团成员赴苏州等地宣讲十九大精神");
                }
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
    }
}
