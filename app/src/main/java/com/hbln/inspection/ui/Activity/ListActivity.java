package com.hbln.inspection.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.constans.URLs;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.FortressHomeModel;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/19
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ListActivity extends BaseActivity implements RUAdapter.OnItemClickListener {
    public static final String INTENT_TYPE = "type";
    public static final int TYPE_WENTI = 0;
    public static final int TYPE_DANG_FEI = 1;
    public static final int TYPE_QUN_ZHONG = 2;
    public static final int TYPE_SAN_HUI = 3;
    public static boolean hasSearch = true;
    public int mType = 0;

    private LinearLayout mLlListSeach;
    private EditText mEtListSeach;
    private ImageView mIvlistSearch;
    private RecyclerView mRvList;
    private RUAdapter<FortressHomeModel.InfoBean> mAdapter;
    private List<FortressHomeModel.InfoBean> mList = new ArrayList<>();
    private FortressHomeModel mWenTiData;

    public static void start(Context context, int type) {
        Intent starter = new Intent(context, ListActivity.class);
        starter.putExtra(INTENT_TYPE, type);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mType = getIntent().getIntExtra(INTENT_TYPE, 0);
        initView();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hasSearch = true;
    }

    private void initView() {
        String title;
        if (mType == TYPE_WENTI) {
            title = "文体活动";
        } else if (mType == TYPE_DANG_FEI) {
            title = "缴纳党费";
        } else {
            title = "群众工作";
        }
        TitleUtil.attach(this)
                .setTitle(title)
                .setBack(true);
        mLlListSeach = (LinearLayout) findViewById(R.id.ll_search);
        mEtListSeach = (EditText) findViewById(R.id.et_search);
        mIvlistSearch = (ImageView) findViewById(R.id.iv_search);
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RUAdapter<FortressHomeModel.InfoBean>(getContext(), mList, R.layout.item_fortress) {
            @Override
            protected void onInflateData(RUViewHolder holder, FortressHomeModel.InfoBean data, int position) {
                holder.setText(R.id.tv_item_fortress, data.title);
                if (TextUtils.isEmpty(data.zhibuname)) {
                    holder.setVisibility(R.id.tv_item_fortress_name, View.INVISIBLE);
                } else {
                    holder.setVisibility(R.id.tv_item_fortress_name, View.VISIBLE);
                    holder.setText(R.id.tv_item_fortress_name, data.zhibuname);
                }
                holder.setText(R.id.tv_item_fortress_date, data.times);
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvList.setAdapter(mAdapter);
    }

    public void loadData() {
        Observable<FortressHomeModel> request;
        if (mType == TYPE_WENTI) {
            request = HttpRequest.getFortressService().zhuzhishlist("文体活动");
        } else if (mType == TYPE_DANG_FEI) {
            request = HttpRequest.getFortressService().zhuzhishlist("缴纳党费");
        } else {
            request = HttpRequest.getFortressService().qzgongzuolist("1", URLs.PAGE_SIZE);
        }

        showLoading("");
        request.compose(NetWorkInterceptor.<FortressHomeModel>retrySessionCreator())
                .compose(getBaseActivity().<FortressHomeModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<FortressHomeModel>() {
                    @Override
                    public void result(FortressHomeModel objectModel) {
                        setWenTiData(objectModel);
                    }
                }, new HttpError(this), new HttpComplete(this));
    }

    public void setWenTiData(FortressHomeModel wenTiData) {
        mWenTiData = wenTiData;
        mList = mWenTiData.info;
        mAdapter.setData(mList);
    }

    @Override
    public void onItemClick(View view, int itemType, int position) {
        if (mType == TYPE_WENTI || mType == TYPE_DANG_FEI) {
            WebViewContentActivity.start(getContext(), mList.get(position).id, WebViewContentActivity.TYPE_FORTRESS_HOME);
        } else {
            WebViewContentActivity.start(getContext(), mList.get(position).id, WebViewContentActivity.TYPE_FORTRESS_QUN_ZHONG);
        }
    }
}
