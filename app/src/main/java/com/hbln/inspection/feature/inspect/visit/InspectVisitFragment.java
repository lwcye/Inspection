package com.hbln.inspection.feature.inspect.visit;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.hbln.inspection.R;
import com.hbln.inspection.feature.school.answer.AnswerActivity;
import com.hbln.inspection.feature.school.answer.AnswerResultActivity;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.network.model.JiafangModel;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.loader.LoaderFactory;
import com.hbln.inspection.widget.VisitDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * <p> 线上家访 </p><br>
 *
 * @author lwc
 * @date 2017/12/15 0:59
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class InspectVisitFragment extends MVPBaseFragment<InspectVisitContract.View, InspectVisitPresenter> implements InspectVisitContract.View, RUAdapter.OnItemClickListener {
    private RecyclerView mRvInspectTrack;
    private List<JiafangModel.JiafangInfoBean> mList = new ArrayList<>();
    private RUAdapter<JiafangModel.JiafangInfoBean> mAdapter;
    private VisitDialog mDialog;

    @Override
    protected InspectVisitPresenter createPresenter() {
        return new InspectVisitPresenter();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadData();
    }

    @Override
    public void initView(View view) {
        mRvInspectTrack = (RecyclerView) view.findViewById(R.id.rv_inspect_visit);

        initRecyclerView();
    }

    private void initRecyclerView() {
        mRvInspectTrack.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RUAdapter<JiafangModel.JiafangInfoBean>(getContext(), mList, R.layout.item_school_answer_0) {
            @Override
            protected void onInflateData(RUViewHolder holder, JiafangModel.JiafangInfoBean data, int position) {
                // holder.setVisibility(R.id.iv_item_shcool_answer_1_status, View.GONE);
                holder.setText(R.id.tv_item_shcool_answer_1_title, data.title);
                holder.setText(R.id.tv_item_shcool_answer_1_content, data.nums + "人参与答题");
                if (!TextUtils.isEmpty(data.pic)) {
                    ImageView viewById = holder.getViewById(R.id.iv_item_shcool_answer_1);
                    LoaderFactory.getLoader().loadNet(viewById, data.pic);
                }
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvInspectTrack.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_inspect_visit;
    }

    @Override
    public void onItemClick(View view, int itemType, final int position) {
        if (mDialog == null) {
            mDialog = new VisitDialog(getContext());
        }
        mDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerResultActivity.TYPE = "在线家访";
                AnswerActivity.start(getContext(), mList.get(position).id, mDialog.getAnswerName(), mDialog.getAnswerGuanxi(), mDialog.getAnswerMobile());
            }
        });
        mDialog.show();
    }

    @Override
    public void setData(JiafangModel data) {
        mList = data.info;
        mAdapter.setData(mList);
    }
}
