package com.hbln.inspection.feature.fortress.manager;


import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.network.model.ManagerModel;
import com.hbln.inspection.ui.adapter.PersonExpListAdapter;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.widget.ManagerDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FortressManagerFragment extends MVPBaseFragment<FortressManagerContract.View, FortressManagerPresenter> implements FortressManagerContract.View {
    private ExpandableListView mExpandableListView;
    private TextView mTvName;
    private RUAdapter<String> mAdapter;
    private List<String> mList = new ArrayList<>();
    
    @Override
    protected FortressManagerPresenter createPresenter() {
        return new FortressManagerPresenter();
    }
    
    @Override
    public void initData() {
        mPresenter.loadManagerData();
    }
    
    @Override
    public void initView(View view) {
        mTvName = (TextView) view.findViewById(R.id.tv_fortress_manager);
        mExpandableListView = (ExpandableListView) view.findViewById(R.id.elv_fortress_manager);
        
        initRecyclerVIew();
    }
    
    private void initRecyclerVIew() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_fortress_manager) {
//            @Override
//            protected void onInflateData(RUViewHolder holder, String data, int position) {
//                ExpandableListView listView = holder.getViewById(R.id.elv_item_fortress_manager);
//
//            }
//        };
        
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_fortress_manager;
    }
    
    @Override
    public void setManagerData(final ManagerModel managerData) {
        mExpandableListView.setAdapter(new PersonExpListAdapter(managerData.info));
//        mExpandableListView.setGroupIndicator(((BaseActivity) getActivity()).getCompatDrawable(R.drawable.selector_exp));
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ManagerDialog dialog = new ManagerDialog(getContext(), managerData.info.get(groupPosition), childPosition);
                dialog.show();
                return false;
            }
        });
    }
}
