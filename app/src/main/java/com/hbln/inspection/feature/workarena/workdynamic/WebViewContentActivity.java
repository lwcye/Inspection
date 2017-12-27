package com.hbln.inspection.feature.workarena.workdynamic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.inspection.widget.DialogUtils;
import com.hbln.inspection.widget.x5.utils.BridgeWebView;
import com.hbln.inspection.widget.x5.x5.WebViewManager;
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
public class WebViewContentActivity extends BaseActivity implements View.OnClickListener {
    public static final String INTENT_ID = "ID";
    public static final String INTENT_TYPE = "type";
    public static final int TYPE_SCHOOL = 0;
    public static final int TYPE_WORK = 1;
    public static final int TYPE_REGULAR = 2;
    public static final int TYPE_FORTRESS_JIANDU = 3;
    public static final int TYPE_FORTRESS_HOME = 4;
    public static final int TYPE_FORTRESS_QUN_ZHONG = 5;
    public static final int TYPE_FORTRESS_SAN_HUI = 6;
    public static final int TYPE_BRAND_WAIXUAN = 7;
    public static boolean hasComment = true;
    public static boolean hasZan = true;
    public static boolean hasFont = true;
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
    private LinearLayout mLlWebviewZan;
    private LinearLayout mLlWebviewComment;
    private LinearLayout mLlWebviewFont;
    
    public static void start(Context context, String id, int type) {
        Intent starter = new Intent(context, WebViewContentActivity.class);
        starter.putExtra(INTENT_ID, id);
        starter.putExtra(INTENT_TYPE, type);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_content);
        mId = getIntent().getStringExtra(INTENT_ID);
        mType = getIntent().getIntExtra(INTENT_TYPE, 0);
        
        initView();
        loadData(mId, mType);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        hasComment = true;
        hasZan = true;
        hasFont = true;
    }
    
    private void loadData(String id, int type) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showShortToastSafe("数据读取错误");
            finish();
            return;
        }
        showLoading("");
        Observable<WebViewModel> observable = HttpRequest.getWorkService().jobdongtaiview(id);
        LogUtils.e(type);
        if (type == TYPE_SCHOOL) {
            observable = HttpRequest.getWorkService().jobdongtaiview(id);
        } else if (type == TYPE_WORK) {
            observable = HttpRequest.getWorkService().jobdongtaiview(id);
        } else if (type == TYPE_REGULAR) {
            observable = HttpRequest.getRegularService().zdview(id);
        } else if (type == TYPE_FORTRESS_HOME) {
            observable = HttpRequest.getFortressService().zhuzhiview(id);
        } else if (type == TYPE_FORTRESS_QUN_ZHONG) {
            observable = HttpRequest.getFortressService().qzgzview(id);
        } else if (type == TYPE_FORTRESS_JIANDU) {
            observable = HttpRequest.getFortressService().dangyuanjianduview(id);
        } else if (type == TYPE_FORTRESS_SAN_HUI) {
            observable = HttpRequest.getFortressService().sanhuiyikeview(id);
        } else if (type == TYPE_BRAND_WAIXUAN) {
            observable = HttpRequest.getBrandService().waixuanview(id);
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
        
        mLlWebviewZan = (LinearLayout) findViewById(R.id.ll_webview_zan);
        mLlWebviewComment = (LinearLayout) findViewById(R.id.ll_webview_comment);
        mLlWebviewFont = (LinearLayout) findViewById(R.id.ll_webview_font);
        
        if (hasZan) {
            mLlWebviewZan.setVisibility(View.VISIBLE);
        } else {
            mLlWebviewZan.setVisibility(View.GONE);
        }
        if (hasComment) {
            mLlWebviewComment.setVisibility(View.VISIBLE);
        } else {
            mLlWebviewComment.setVisibility(View.GONE);
        }
        if (hasFont) {
            mLlWebviewFont.setVisibility(View.VISIBLE);
        } else {
            mLlWebviewFont.setVisibility(View.GONE);
        }
        
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
        String times;
        if (TextUtils.isEmpty(data.times)) {
            try {
                times = TimeUtils.millis2String(Long.valueOf(data.create_time) * 1000, "yyyy-MM-dd");
            } catch (NumberFormatException e) {
                times = data.create_time;
            }
        } else {
            times = data.times;
        }
        mTvWebviewDate.setText(times + "\t\t" + "阅读量：" + data.nums);
        mWvWebview.loadDataWithBaseURL(null, data.content, "text/html", "utf-8", null);
//        mWvWebview.loadData(data.content, "text/html;charset=UTF-8", null);
    }
}
