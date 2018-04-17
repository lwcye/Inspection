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

import com.cmcc.lib_utils.utils.ConstUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.base.MyActivity;
import com.hbln.inspection.feature.school.answer.AnswerActivity;
import com.hbln.inspection.feature.school.answer.AnswerResultActivity;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.hbln.inspection.network.model.KaoShiModel;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.Calendar;
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
public class BusnissListActivity extends MyActivity implements RUAdapter.OnItemClickListener {
    public static final String INTENT_TYPE = "type";
    public static final int TYPE_XUEXI = 0;
    public static final int TYPE_CESHI = 1;
    public static final int TYPE_TIHUI = 2;
    public static boolean hasSearch = true;
    public int mType = 0;

    private LinearLayout mLlListSeach;
    private EditText mEtListSeach;
    private ImageView mIvlistSearch;
    private RecyclerView mRvList;
    private RUAdapter<KaoShiModel.InfoBean> mAdapter;
    private List<KaoShiModel.InfoBean> mList = new ArrayList<>();

    public static void start(Context context, int type) {
        Intent starter = new Intent(context, BusnissListActivity.class);
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
    protected void onRestart() {
        super.onRestart();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hasSearch = true;
    }

    private void initView() {
        String title;
        if (mType == TYPE_XUEXI) {
            title = "学习资料";
        } else if (mType == TYPE_CESHI) {
            title = "在线测试";
        } else {
            title = "体会交流";
        }
        TitleUtil.attach(this)
                .setTitle(title)
                .setBack(true);
        mLlListSeach = (LinearLayout) findViewById(R.id.ll_search);
        mEtListSeach = (EditText) findViewById(R.id.et_search);
        mIvlistSearch = (ImageView) findViewById(R.id.iv_search);
        mRvList = (RecyclerView) findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RUAdapter<KaoShiModel.InfoBean>(getContext(), mList, R.layout.item_school_item) {
            @Override
            protected void onInflateData(RUViewHolder holder, KaoShiModel.InfoBean data, int position) {
                holder.setText(R.id.tv_item_shcool_item_title, data.title);
                if (TextUtils.isEmpty(data.pic)) {
                    holder.setVisibility(R.id.iv_item_shcool_item, View.GONE);
                } else {
                    holder.setVisibility(R.id.iv_item_shcool_item, View.VISIBLE);
                    holder.setImageNet(R.id.tv_item_fortress_name, data.pic);
                }
                if (mType == TYPE_CESHI && !TextUtils.isEmpty(data.kaishitime) && !TextUtils.isEmpty(data.endtimes)) {
                    holder.setText(R.id.tv_item_shcool_item_data, "开始时间："
                            + data.kaishitime + "\n" + "结束时间：" + data.endtimes);
                } else {
                    holder.setText(R.id.tv_item_shcool_item_data, data.times);
                }
            }
        };
        mAdapter.setOnItemClickListener(this);
        mRvList.setAdapter(mAdapter);
    }

    public void loadData() {
        Observable<KaoShiModel> request;
        if (mType == TYPE_XUEXI) {
            request = HttpRequest.getKaoShiService().index("学习资料");
        } else if (mType == TYPE_CESHI) {
            request = HttpRequest.getKaoShiService().index("在线测试");
        } else {
            request = HttpRequest.getKaoShiService().tihuijiaoliu();
        }

        showLoading("");
        request.compose(NetWorkInterceptor.<KaoShiModel>retrySessionCreator())
                .compose(getBaseActivity().<KaoShiModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<KaoShiModel>() {
                    @Override
                    public void result(KaoShiModel objectModel) {
                        setKaoShiData(objectModel);
                    }
                }, new HttpError(this), new HttpComplete(this));
    }

    public void setKaoShiData(KaoShiModel kaoShiData) {
        mList = kaoShiData.info;
        mAdapter.setData(mList);
    }

    @Override
    public void onItemClick(View view, int itemType, int position) {
        KaoShiModel.InfoBean infoBean = mList.get(position);
        if (mType == TYPE_XUEXI || mType == TYPE_CESHI) {
            if (mType == TYPE_XUEXI) {
                AnswerResultActivity.TYPE = "学习资料";
                AnswerActivity.start(getContext(), infoBean.id, AnswerActivity.TYPE_KAOSHI_XUEXI);
            } else {
                AnswerResultActivity.TYPE = "在线测试";
                if (infoBean.startime > 0 && infoBean.endtime > 0) {
                    if (Calendar.getInstance().getTimeInMillis() < (infoBean.startime * ConstUtils.SEC)) {
                        ToastUtils.showLongToastSafe("还未到开始时间，请在" + infoBean.kaishitime + "以后进行测试");
                    } else if (Calendar.getInstance().getTimeInMillis() > (infoBean.endtime * ConstUtils.SEC)) {
                        ToastUtils.showLongToastSafe("该测试已经结束");
                    } else {
                        AnswerActivity.start(getContext(), mList.get(position).id, infoBean.startime, infoBean.endtime, AnswerActivity.TYPE_KAOSHI_CESHI);
                    }
                } else {
                    ToastUtils.showLongToastSafe("考试时间设置错误");
                }
            }
        } else {
            AnswerResultActivity.TYPE = "体会交流";
            TiHuiActivity.start(getContext(), mList.get(position).id);
        }
    }
}
