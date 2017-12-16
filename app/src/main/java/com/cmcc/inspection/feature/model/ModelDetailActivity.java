package com.cmcc.inspection.feature.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.inspection.widget.DialogUtils;
import com.cmcc.inspection.widget.x5.utils.BridgeWebView;
import com.cmcc.inspection.widget.x5.x5.WebViewManager;
import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.lib_views.BottomPopupDialog;
import com.hbln.lib_views.DrawableCenterTextView;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.Observable;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2017/12/16 0:06
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ModelDetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String INTENT_ID = "ID";
    public static final String INTENT_TYPE = "type";
    public int mType = 0;
    public String mId = "";
    
    private BottomPopupDialog fontDialog;
    /** 强筋骨、明纪律 铸造执纪铁军 */
    private TextView mTvWebviewTitle;
    /** 2017-01-01    阅读量：1234 */
    private TextView mTvWebviewDate;
    private BridgeWebView mWvWebview;
    /** 当前数量：1234 */
    private DrawableCenterTextView mTvWebviewZan;
    /** 当前数量：0 */
    private DrawableCenterTextView mTvWebviewCai;
    private EditText mEtWebviewComment;
    /** 提交 */
    private Button mBtnWebviewSubmit;
    private ImageButton mIbWebviewFont;
    private ImageButton mIbWebviewShare;
    
    public static void start(Context context, String id, int type) {
        Intent starter = new Intent(context, ModelDetailActivity.class);
        starter.putExtra(INTENT_ID, id);
        starter.putExtra(INTENT_TYPE, type);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_detail);
        mId = getIntent().getStringExtra(INTENT_ID);
        mType = getIntent().getIntExtra(INTENT_TYPE, 0);
        
        initView();
        initView();
        loadData(mId, mType);
    }
    
    private void loadData(String id, int type) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showShortToastSafe("数据读取错误");
            finish();
            return;
        }
        showLoading("");
        Observable<WebViewModel> observable = HttpRequest.getModelService().gerenview(id);
        if (type == 1) {
            observable = HttpRequest.getModelService().danweiview(id);
        }
        observable
            .compose(NetWorkInterceptor.<WebViewModel>retrySessionCreator())
            .compose(getBaseActivity().<WebViewModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<WebViewModel>() {
                @Override
                public void result(WebViewModel webViewModel) {
                    setData(webViewModel.info);
                }
            }, new HttpError(this), new HttpComplete(this));
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true);
        mTvWebviewTitle = (TextView) findViewById(R.id.tv_webview_title);
        mTvWebviewDate = (TextView) findViewById(R.id.tv_webview_date);
        mWvWebview = (BridgeWebView) findViewById(R.id.wv_webview);
        mTvWebviewZan = (DrawableCenterTextView) findViewById(R.id.tv_webview_zan);
        mTvWebviewZan.setOnClickListener(this);
        mTvWebviewCai = (DrawableCenterTextView) findViewById(R.id.tv_webview_cai);
        mTvWebviewCai.setOnClickListener(this);
        mEtWebviewComment = (EditText) findViewById(R.id.et_webview_comment);
        mBtnWebviewSubmit = (Button) findViewById(R.id.btn_webview_submit);
        mBtnWebviewSubmit.setOnClickListener(this);
        mIbWebviewFont = (ImageButton) findViewById(R.id.ib_webview_font);
        mIbWebviewFont.setOnClickListener(this);
        mIbWebviewShare = (ImageButton) findViewById(R.id.ib_webview_share);
        mIbWebviewShare.setOnClickListener(this);
        
        WebViewManager.getInstance().initWebView(mWvWebview);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_webview_zan:
                break;
            case R.id.tv_webview_cai:
                break;
            case R.id.btn_webview_submit:
                break;
            case R.id.ib_webview_font:
                DialogUtils.getInstance().showFont(getContext(), mWvWebview);
                break;
            case R.id.ib_webview_share:
                final BottomPopupDialog shareDialog = new BottomPopupDialog(getContext(), R.layout.dialog_share, true);
                shareDialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shareDialog.cancel();
                    }
                });
                shareDialog.show();
                break;
            default:
                break;
        }
    }
    
    public void setData(WebViewModel.WebViewInfo data) {
        mTvWebviewTitle.setText(data.title);
        if (mType == 0) {
            mTvWebviewDate.setText(data.name);
        } else {
            mTvWebviewDate.setText(data.danweitwo);
        }
        mWvWebview.loadDataWithBaseURL(null, data.content, "text/html", "utf-8", null);
    }
}
