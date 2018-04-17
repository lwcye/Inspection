package com.hbln.inspection.feature.inspect.track;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hbln.inspection.R;
import com.hbln.inspection.feature.inspect.trackdetail.InspectTrackDetailActivity;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.network.model.TrackModel;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.widget.BeiZhuDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>轨迹追踪</p><br>
 *
 * @author - lwc
 * @date - 2017/12/18 16:28
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class InspectTrackFragment extends MVPBaseFragment<InspectTrackContract.View, InspectTrackPresenter> implements InspectTrackContract.View, View.OnClickListener, RUAdapter.OnItemClickListener {
    private RecyclerView mRvInspectTrack;
    private List<TrackModel.InfoBean> mList = new ArrayList<>();
    private RUAdapter<TrackModel.InfoBean> mAdapter;
    private BeiZhuDialog mBeiZhuDialog;
    
    @Override
    protected InspectTrackPresenter createPresenter() {
        return new InspectTrackPresenter();
    }
    
    @Override
    public void initData() {
    }
    
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadTrackData();
    }
    
    @Override
    public void initView(View view) {
        mRvInspectTrack = (RecyclerView) view.findViewById(R.id.rv_inspect_track);
        view.findViewById(R.id.iv_track_sign).setOnClickListener(this);
        initRecyclerView();
    }
    
    private void initRecyclerView() {
        mRvInspectTrack.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RUAdapter<TrackModel.InfoBean>(getContext(), mList, R.layout.item_inspect_track) {
            @Override
            protected void onInflateData(RUViewHolder holder, TrackModel.InfoBean data, final int position) {
                if (position % 3 == 0) {
                    holder.setImageView(R.id.iv_item_inspect, R.drawable.icon_inspect_item_0);
                } else if (position % 3 == 1) {
                    holder.setImageView(R.id.iv_item_inspect, R.drawable.icon_inspect_item_1);
                } else {
                    holder.setImageView(R.id.iv_item_inspect, R.drawable.icon_inspect_item_2);
                }
                holder.setText(R.id.tv_item_inspect_name, data.name);
                holder.setText(R.id.tv_item_inspect_loc, "位置：" + data.address);
                holder.setText(R.id.tv_item_inspect_date, data.times);
                holder.setOnClickListener(R.id.tv_item_inspect_tag, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showBeiZhuDialog(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mPresenter.postTrackTag(mList.get(position).id, mBeiZhuDialog.getBeiZhu());
                            }
                        });
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvInspectTrack.setAdapter(mAdapter);
    }
    
    @Override
    public int getLayoutId() {
        return R.layout.fragment_inspect_track;
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_track_sign:
                showBeiZhuDialog(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.postTrackData(mBeiZhuDialog.getBeiZhu());
                    }
                });
                break;
            default:
                break;
        }
    }
    
    private void showBeiZhuDialog(View.OnClickListener onClickListener) {
        if (mBeiZhuDialog == null) {
            mBeiZhuDialog = new BeiZhuDialog(getContext());
        }
        mBeiZhuDialog.setOnClickListener(onClickListener);
        mBeiZhuDialog.show();
    }
    
    @Override
    public void setTrackData(TrackModel trackData) {
        mList = trackData.info;
        mAdapter.setData(mList);
    }
    
    @Override
    public void onItemClick(View view, int itemType, int position) {
        InspectTrackDetailActivity.start(getContext(), mList.get(position));
    }
}
