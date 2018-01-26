package com.hbln.inspection.feature.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.CommentModel;
import com.cmcc.lib_network.model.ObjectModel;
import com.cmcc.lib_network.model.WebViewModel;
import com.cmcc.lib_network.model.ZanModel;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.inspection.widget.DialogUtils;
import com.hbln.inspection.widget.x5.utils.BridgeWebView;
import com.hbln.inspection.widget.x5.x5.WebViewManager;
import com.hbln.lib_views.BottomPopupDialog;
import com.hbln.lib_views.DrawableCenterTextView;
import com.hbln.lib_views.SimpleItemDecoration;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

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
    private RecyclerView mRvWebviewComment;
    /** 评论数据 */
    private List<CommentModel.InfoBean> mCommentList = new ArrayList<>();
    /** 评论适配器 */
    private RUAdapter<CommentModel.InfoBean> mCommentAdapter;
    
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
        mType = getIntent().getIntExtra(INTENT_TYPE, WebViewModel.TYPE_MODEL_PRIVE);
        
        initView();
        initView();
        loadData(mId, mType);
        getCommentList(false);
        getZanList(false);
    }
    
    private void loadData(String id, int type) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showShortToastSafe("数据读取错误");
            finish();
            return;
        }
        showLoading("");
        Observable<WebViewModel> observable = HttpRequest.getModelService().gerenview(id);
        if (type == WebViewModel.TYPE_MODEL_GROUP) {
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
        
        mRvWebviewComment = (RecyclerView) findViewById(R.id.rv_webview_comment);
        // 评论
        mCommentAdapter = WebViewContentActivity.initCommentAdapter(getBaseActivity(), mCommentList, new Action1<ObjectModel>() {
            @Override
            public void call(ObjectModel objectModel) {
                ToastUtils.showShortToastSafe(objectModel.info.toString());
                getCommentList(true);
            }
        });
        mCommentAdapter.setDataEmptyLayoutId(0);
        mRvWebviewComment.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvWebviewComment.setNestedScrollingEnabled(false);
        mRvWebviewComment.addItemDecoration(new SimpleItemDecoration(getContext(), SimpleItemDecoration.VERTICAL_LIST));
        mRvWebviewComment.setAdapter(mCommentAdapter);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_webview_zan:
                ZanModel.handleZan(getBaseActivity(), true, mType, mId, new Action1<ObjectModel>() {
                    @Override
                    public void call(ObjectModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getZanList(true);
                    }
                });
                break;
            case R.id.tv_webview_cai:
                ZanModel.handleZan(getBaseActivity(), false, mType, mId, new Action1<ObjectModel>() {
                    @Override
                    public void call(ObjectModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getZanList(true);
                    }
                });
                break;
            case R.id.btn_webview_submit:
                CommentModel.handleComment(getBaseActivity(), mEtWebviewComment.getText().toString(), mType, mId, new Action1<ObjectModel>() {
                    @Override
                    public void call(ObjectModel objectModel) {
                        mEtWebviewComment.setText("");
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getCommentList(true);
                    }
                });
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
        if (mType == WebViewModel.TYPE_MODEL_PRIVE) {
            mTvWebviewDate.setText(data.name);
        } else {
            mTvWebviewDate.setText(data.danweitwo);
        }
        mWvWebview.loadDataWithBaseURL(null, data.content, "text/html", "utf-8", null);
    }
    
    /**
     * 获取评论列表
     */
    private void getCommentList(boolean showProgress) {
        CommentModel.getCommentList(getBaseActivity(), mType, mId, showProgress, new Action1<CommentModel>() {
            @Override
            public void call(CommentModel commentModel) {
                mCommentList = commentModel.info;
                mCommentAdapter.setData(commentModel.info);
            }
        });
    }
    
    /**
     * 获取点赞列表
     */
    private void getZanList(boolean showProgress) {
        ZanModel.getZanList(getBaseActivity(), mType, mId, showProgress, new Action1<ZanModel>() {
            @Override
            public void call(ZanModel zanModel) {
                mTvWebviewZan.setText("当前数量：" + zanModel.info.yeszan);
                mTvWebviewCai.setText("当前数量：" + zanModel.info.nozan);
            }
        });
    }
}
