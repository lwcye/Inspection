package com.hbln.inspection.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.lib_common.base.BaseFragment;
import com.hbln.inspection.R;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.lib_views.SimpleItemDecoration;

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
    public RecyclerView.LayoutManager mLayoutManager;
    public RecyclerView.ItemDecoration mItemDecoration;
    public RUAdapter.OnItemClickListener mOnItemClickListener;
    
    @Override
    public void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list);
        if (mLayoutManager == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        } else {
            mRecyclerView.setLayoutManager(mLayoutManager);
        }
        if (mItemDecoration == null) {
            mRecyclerView.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        } else {
            mRecyclerView.addItemDecoration(mItemDecoration);
        }
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
    
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }
    
    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mItemDecoration = itemDecoration;
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
    
}
