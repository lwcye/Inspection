package com.cmcc.inspection.ui.fragment;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.lib_common.base.BaseFragment;

import java.util.List;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/14
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ListFragment<T> extends BaseFragment {
    public RecyclerView mRecyclerView;
    public List<T> mList;
    public RUAdapter<T> mAdapter;
    public RUAdapter.OnItemClickListener mOnItemClickListener;

    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(mAdapter);
    }

    @Override
    public void initData() {

    }

    public void setAdapter(RUAdapter<T> adapter) {
        mAdapter = adapter;
        if (mRecyclerView != null && mAdapter != null) {
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setDataEmptyLayoutId(R.layout.layout_empty);
        }
    }

    public void setData(List<T> list) {
        mList = list;
        if (mList != null && mAdapter != null) {
            mAdapter.setData(mList);
        }
    }

    public void setOnItemClickListener(RUAdapter.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        if (mOnItemClickListener != null && mAdapter != null) {
            mAdapter.setOnItemClickListener(mOnItemClickListener);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_list;
    }

    public class MyAdapter<T> extends RUAdapter<T> {
        /**
         * 数据适配器构造函数
         *
         * @param context 上下文
         * @param data 数据列表
         * @param layoutId 资源id
         */
        public MyAdapter(Context context, List<T> data, int layoutId) {
            super(context, data, layoutId);
        }

        @Override
        protected void onInflateData(RUViewHolder holder, T data, int position) {

        }

        @Override
        protected void onInflateEmptyLayout(RUViewHolder holder) {
            super.onInflateEmptyLayout(holder);
            ImageView imageView = holder.getViewById(R.id.iv_empty);
            if (imageView != null) {
//                LoaderFactory.getLoader().loadAssetsGif(mIvDialogTrain, "loading_train.gif");
            }
        }
    }
}
