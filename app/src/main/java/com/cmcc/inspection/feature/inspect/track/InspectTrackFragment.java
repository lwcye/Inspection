package com.cmcc.inspection.feature.inspect.track;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.inspect.trackdetail.InspectTrackDetailActivity;
import com.cmcc.inspection.mvp.MVPBaseFragment;
import com.cmcc.inspection.ui.adapter.RUAdapter;
import com.cmcc.inspection.ui.adapter.RUViewHolder;
import com.cmcc.inspection.widget.BeiZhuDialog;

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
public class InspectTrackFragment extends MVPBaseFragment<InspectTrackContract.View, InspectTrackPresenter> implements InspectTrackContract.View, View.OnClickListener {
    private RecyclerView mRvInspectTrack;
    private List<String> mList = new ArrayList<>();
    private RUAdapter<String> mAdapter;
    private BeiZhuDialog mBeiZhuDialog;

    @Override
    protected InspectTrackPresenter createPresenter() {
        return new InspectTrackPresenter();
    }

    @Override
    public void initData() {
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

        mList.add("0");
        mList.add("1");
        mList.add("2");
        mAdapter = new RUAdapter<String>(getContext(), mList, R.layout.item_inspect_track) {
            @Override
            protected void onInflateData(RUViewHolder holder, String data, int position) {

            }
        };
        mAdapter.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                InspectTrackDetailActivity.start(getContext());
            }
        });
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
                showBeiZhuDialog();
                break;
            default:
                break;
        }
    }

    private void showBeiZhuDialog() {
        if (mBeiZhuDialog == null) {
            mBeiZhuDialog = new BeiZhuDialog(getContext());
            mBeiZhuDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.postTrackData(mBeiZhuDialog.getBeiZhu());
                }
            });
        }
        mBeiZhuDialog.show();
    }
}
