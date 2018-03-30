package com.hbln.inspection.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.TiHuiModel;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.base.MyActivity;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.inspection.widget.x5.utils.BridgeWebView;
import com.hbln.inspection.widget.x5.x5.WebViewManager;
import com.hbln.lib_views.SimpleItemDecoration;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/20 7:29
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class TiHuiActivity extends MyActivity implements View.OnClickListener {
    public static final String INTENT_ID = "id";
    /** 强筋骨、明纪律 铸造执纪铁军 */
    private TextView mTvTihuiTitle;
    /** 2017-01-01    阅读量：1234 */
    private TextView mTvTihuiDate;


    private String mId;
    private TextView mLeft;
    private TextView mCenter;
    private TextView mRight;
    private View mVTitleBarShadow;
    private LinearLayout mLlTitle;
    private BridgeWebView mWvWebview;
    private RecyclerView mRvTihui;
    /** 请输入解答 */
    private EditText mEtTihuiComment;
    /** 评论 */
    private Button mBtnTihuiComment;

    private List<TiHuiModel.InfoBean.InfosBean> mList = new ArrayList<>();
    private RUAdapter<TiHuiModel.InfoBean.InfosBean> mAdapter;
    private TiHuiModel mData;


    public static void start(Context context, String id) {
        Intent starter = new Intent(context, TiHuiActivity.class);
        starter.putExtra(INTENT_ID, id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tihui);
        mId = getIntent().getStringExtra(INTENT_ID);
        initView();
        loadData();
    }

    private void initView() {
        TitleUtil.attach(this)
                .setTitle("体会交流")
                .setBack(true);

        mTvTihuiTitle = (TextView) findViewById(R.id.tv_tihui_title);
        mTvTihuiDate = (TextView) findViewById(R.id.tv_tihui_date);
        mEtTihuiComment = (EditText) findViewById(R.id.et_tihui_comment);
        mBtnTihuiComment = (Button) findViewById(R.id.btn_tihui_comment);
        mWvWebview = (BridgeWebView) findViewById(R.id.wv_webview);
        mRvTihui = (RecyclerView) findViewById(R.id.rv_tihui);
        mBtnTihuiComment.setOnClickListener(this);

        WebViewManager.getInstance().initWebView(mWvWebview);

        mAdapter = new RUAdapter<TiHuiModel.InfoBean.InfosBean>(getContext(), mList, R.layout.item_comment) {
            @Override
            protected void onInflateData(RUViewHolder holder, TiHuiModel.InfoBean.InfosBean data, int position) {
                holder.setImageNetCircle(R.id.iv_comment_item, data.pic);
                holder.setText(R.id.tv_comment_name, data.name);
                holder.setText(R.id.tv_comment_content, data.content);
                holder.setText(R.id.tv_comment_answer_date, TimeUtils.millis2String(Long.valueOf(data.create_time) * 1000, "yyyy-MM-dd"));
            }
        };
        mRvTihui.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvTihui.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mRvTihui.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tihui_comment:
                submit(mEtTihuiComment.getText().toString().trim());
                break;
            default:
                break;
        }
    }

    public void setData(TiHuiModel data) {
        mData = data;
        mTvTihuiTitle.setText(mData.info.title);
        mTvTihuiDate.setText(mData.info.times + "\t\t阅读量：" + mData.info.nums);
        mWvWebview.loadDataWithBaseURL(null, mData.info.content, "text/html", "utf-8", null);
        mList = data.info.infos;
        mAdapter.setData(mList);
    }

    private void loadData() {
        showLoading("");
        HttpRequest.getKaoShiService().thjlloglist(mId)
                .compose(NetWorkInterceptor.<TiHuiModel>retrySessionCreator())
                .compose(getBaseActivity().<TiHuiModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<TiHuiModel>() {
                    @Override
                    public void result(TiHuiModel objectModel) {
                        setData(objectModel);
                    }
                }, new HttpError(this), new HttpComplete(this));
    }

    private void submit(String content) {
        if (TextUtils.isEmpty(content)) {
            ToastUtils.showShortToastSafe("请输入内容");
            return;
        }
        showLoading("");
        HttpRequest.getKaoShiService().thjllogadd(mId, content)
                .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
                .compose(getBaseActivity().<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<ObjectModel>() {
                    @Override
                    public void result(ObjectModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        mEtTihuiComment.setText("");
                        loadData();
                    }
                }, new HttpError(this), new HttpComplete(this));
    }
}
