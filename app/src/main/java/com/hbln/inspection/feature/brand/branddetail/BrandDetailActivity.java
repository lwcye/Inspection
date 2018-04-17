package com.hbln.inspection.feature.brand.branddetail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workdynamic.WebViewContentActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.network.model.BrandDetailModel;
import com.hbln.inspection.network.model.CommentModel;
import com.hbln.inspection.network.model.ObjectModel;
import com.hbln.inspection.network.model.WebViewModel;
import com.hbln.inspection.network.model.ZanModel;
import com.hbln.inspection.ui.adapter.RUAdapter;
import com.hbln.inspection.ui.adapter.RUViewHolder;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.inspection.utils.loader.LoaderFactory;
import com.hbln.inspection.widget.DialogUtils;
import com.hbln.inspection.widget.x5.utils.BridgeWebView;
import com.hbln.inspection.widget.x5.x5.WebViewManager;
import com.hbln.lib_views.BottomPopupDialog;
import com.hbln.lib_views.DrawableCenterTextView;
import com.hbln.lib_views.SimpleItemDecoration;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class BrandDetailActivity extends MVPBaseActivity<BrandDetailContract.View, BrandDetailPresenter> implements BrandDetailContract.View, View.OnClickListener {
    public static final String INTENT_ID = "id";
    private String mId;
    private TextView mTvBrandDetail;
    private ImageView mIvBrandDetail;
    private BridgeWebView mWvBrandDetail;
    /** 外宣稿件 */
    private TextView mLlBrandDetailWaixuan;
    private RecyclerView mRvBrandDetailWaixuan;
    /** 分管领导 */
    private TextView mLlBrandDetailFenguan;
    /** 承办科室 */
    private TextView mLlBrandDetailChengban;
    private RecyclerView mRvBrandDetail;
    private RecyclerView mRvBrandDetail0;
    private RecyclerView mRvBrandDetail1;
    /** 当前数量：1234 */
    private DrawableCenterTextView mTvWebviewZan;
    /** 当前数量：0 */
    private DrawableCenterTextView mTvWebviewCai;
    private LinearLayout mLlWebviewZan;
    private EditText mEtWebviewComment;
    /** 提交 */
    private Button mBtnWebviewSubmit;
    private LinearLayout mLlWebviewComment;
    private ImageButton mIbWebviewFont;
    private ImageButton mIbWebviewShare;
    private LinearLayout mLlWebviewFont;
    private ImageView mImageView;
    private ImageView mIvBrandDetailFenguan;
    private TextView mTvBrandDetailFenguan;
    
    private List<BrandDetailModel.InfoBean.ChengbankeshiBean> mList = new ArrayList<>();
    private List<BrandDetailModel.InfoBean.ChengbankeshiBean.ChengBan> mList0 = new ArrayList<>();
    private List<BrandDetailModel.InfoBean.ChengbankeshiBean.ChengBan> mList1 = new ArrayList<>();
    private RUAdapter<BrandDetailModel.InfoBean.ChengbankeshiBean> mAdapter;
    private RUAdapter<BrandDetailModel.InfoBean.ChengbankeshiBean.ChengBan> mAdapter0;
    private RUAdapter<BrandDetailModel.InfoBean.ChengbankeshiBean.ChengBan> mAdapter1;
    private RelativeLayout mRlBrandDetail0;
    private RelativeLayout mRlBrandDetail1;
    private RelativeLayout mRlBrandDetailWaixuan;
    private List<BrandDetailModel.InfoBean.WaiXuanGaoJian> mWaiXuanGaoJianList = new ArrayList<>();
    private RUAdapter<BrandDetailModel.InfoBean.WaiXuanGaoJian> mWaiXuanGaoJianRUAdapter;
    
    /** 评论数据 */
    private List<CommentModel.InfoBean> mCommentList = new ArrayList<>();
    /** 评论适配器 */
    private RUAdapter<CommentModel.InfoBean> mCommentAdapter;
    private RecyclerView mRvWebviewComment;
    
    public static void start(Context context, String id) {
        Intent starter = new Intent(context, BrandDetailActivity.class);
        starter.putExtra(INTENT_ID, id);
        context.startActivity(starter);
    }
    
    @Override
    protected BrandDetailPresenter createPresenter() {
        return new BrandDetailPresenter();
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);
        mId = getIntent().getStringExtra(INTENT_ID);
        
        initView();
        
        mPresenter.loadDetail(mId);
        getCommentList(false);
        getZanList(false);
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true);
        mTvBrandDetail = (TextView) findViewById(R.id.tv_brand_detail);
        mIvBrandDetail = (ImageView) findViewById(R.id.iv_brand_detail);
        mWvBrandDetail = (BridgeWebView) findViewById(R.id.wv_brand_detail);
        mLlBrandDetailWaixuan = (TextView) findViewById(R.id.ll_brand_detail_waixuan);
        mRlBrandDetailWaixuan = (RelativeLayout) findViewById(R.id.rl_brand_detail_waixuan);
        mRvBrandDetailWaixuan = (RecyclerView) findViewById(R.id.rv_brand_detail_waixuan);
        mLlBrandDetailFenguan = (TextView) findViewById(R.id.ll_brand_detail_fenguan);
        mIvBrandDetailFenguan = (ImageView) findViewById(R.id.iv_brand_detail_fenguan);
        mTvBrandDetailFenguan = (TextView) findViewById(R.id.tv_brand_detail_fenguan);
        mLlBrandDetailChengban = (TextView) findViewById(R.id.ll_brand_detail_chengban);
        mRvBrandDetail = (RecyclerView) findViewById(R.id.rv_brand_detail_chengban);
        mTvWebviewZan = (DrawableCenterTextView) findViewById(R.id.tv_webview_zan);
        mTvWebviewZan.setOnClickListener(this);
        mTvWebviewCai = (DrawableCenterTextView) findViewById(R.id.tv_webview_cai);
        mTvWebviewCai.setOnClickListener(this);
        mLlWebviewZan = (LinearLayout) findViewById(R.id.ll_webview_zan);
        mEtWebviewComment = (EditText) findViewById(R.id.et_webview_comment);
        mBtnWebviewSubmit = (Button) findViewById(R.id.btn_webview_submit);
        mBtnWebviewSubmit.setOnClickListener(this);
        mLlWebviewComment = (LinearLayout) findViewById(R.id.ll_webview_comment);
        mIbWebviewFont = (ImageButton) findViewById(R.id.ib_webview_font);
        mIbWebviewFont.setOnClickListener(this);
        mIbWebviewShare = (ImageButton) findViewById(R.id.ib_webview_share);
        mIbWebviewShare.setOnClickListener(this);
        mLlWebviewFont = (LinearLayout) findViewById(R.id.ll_webview_font);
        
        WebViewManager.getInstance().initWebView(mWvBrandDetail);
        
        initRecylerView();
    }
    
    private void initRecylerView() {
        mAdapter = new RUAdapter<BrandDetailModel.InfoBean.ChengbankeshiBean>(getContext(), mList, R.layout.item_brand_detail_chengban_group) {
            @Override
            protected void onInflateData(RUViewHolder holder, BrandDetailModel.InfoBean.ChengbankeshiBean data, int position) {
                holder.setText(R.id.tv_item_brand_detail_danwei, data.danwei);
                holder.setText(R.id.tv_item_brand_detail_position, position + 1 + "");
                RecyclerView recyclerView = holder.getViewById(R.id.rv_brand_detail);
                mList0 = data.initList();
                mAdapter0 = new RUAdapter<BrandDetailModel.InfoBean.ChengbankeshiBean.ChengBan>(getContext(), mList0, R.layout.item_brand_detail_chengban) {
                    @Override
                    protected void onInflateData(RUViewHolder holder, BrandDetailModel.InfoBean.ChengbankeshiBean.ChengBan data, int position) {
                        holder.setImageNet(R.id.iv_item_brand_detail_chengban, data.pic);
                        holder.setText(R.id.tv_item_brand_detail_chengban_title, data.title);
                        holder.setText(R.id.tv_item_brand_detail_chengban_name, data.name);
                    }
                };
                GridLayoutManager gridLayoutManager0 = new GridLayoutManager(getContext(), 2);
                gridLayoutManager0.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position == 0) {
                            return 2;
                        }
                        return 1;
                    }
                });
                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(getContext(), 2);
                gridLayoutManager1.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        if (position == 0) {
                            return 2;
                        }
                        return 1;
                    }
                });
                recyclerView.setLayoutManager(gridLayoutManager0);
                recyclerView.setAdapter(mAdapter0);
            }
        };
        
        mRvBrandDetail.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvBrandDetail.setNestedScrollingEnabled(false);
        mRvBrandDetail.setAdapter(mAdapter);
        
        mWaiXuanGaoJianRUAdapter = new RUAdapter<BrandDetailModel.InfoBean.WaiXuanGaoJian>(getContext(), mWaiXuanGaoJianList, R.layout.item_brand_waixuan) {
            @Override
            protected void onInflateData(RUViewHolder holder, BrandDetailModel.InfoBean.WaiXuanGaoJian data, int position) {
                holder.setImageNet(R.id.iv_item_brand_waixuan, data.pic);
                holder.setText(R.id.tv_item_brand_waixuan, data.title);
            }
        };
        mWaiXuanGaoJianRUAdapter.setOnItemClickListener(new RUAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int itemType, int position) {
                WebViewContentActivity.start(getContext(), mWaiXuanGaoJianList.get(position).id, WebViewModel.TYPE_BRAND_WAIXUAN);
            }
        });
        mRvBrandDetailWaixuan.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRvBrandDetailWaixuan.setNestedScrollingEnabled(false);
        mRvBrandDetailWaixuan.setAdapter(mWaiXuanGaoJianRUAdapter);
        
        // 评论
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
                ZanModel.handleZan(getBaseActivity(), true, WebViewModel.TYPE_BRAND_WAIXUAN, mId, new Action1<ObjectModel>() {
                    @Override
                    public void call(ObjectModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getZanList(true);
                    }
                });
                break;
            case R.id.tv_webview_cai:
                ZanModel.handleZan(getBaseActivity(), false, WebViewModel.TYPE_BRAND_WAIXUAN, mId, new Action1<ObjectModel>() {
                    @Override
                    public void call(ObjectModel objectModel) {
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getZanList(true);
                    }
                });
                break;
            case R.id.btn_webview_submit:
                CommentModel.handleComment(getBaseActivity(), mEtWebviewComment.getText().toString(), WebViewModel.TYPE_BRAND_WAIXUAN, mId, new Action1<ObjectModel>() {
                    @Override
                    public void call(ObjectModel objectModel) {
                        mEtWebviewComment.setText("");
                        ToastUtils.showShortToastSafe(objectModel.info.toString());
                        getCommentList(true);
                    }
                });
                break;
            case R.id.ib_webview_font:
                DialogUtils.getInstance().showFont(getContext(), mWvBrandDetail);
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
        }
    }
    
    @Override
    public void setDetail(BrandDetailModel detailModel) {
        mTvBrandDetail.setText(detailModel.info.title);
        if (TextUtils.isEmpty(detailModel.info.pic)) {
            mIvBrandDetail.setVisibility(View.GONE);
        } else {
            mIvBrandDetail.setVisibility(View.VISIBLE);
            LoaderFactory.getLoader().loadNet(mIvBrandDetail, detailModel.info.pic);
        }
        mWvBrandDetail.loadDataWithBaseURL(null, detailModel.info.jianjie.content, "text/html", "utf-8", null);
        
        if (detailModel.info.waixuangaojian != null && detailModel.info.waixuangaojian.size() > 0) {
            mLlBrandDetailWaixuan.setVisibility(View.VISIBLE);
            mRvBrandDetailWaixuan.setVisibility(View.VISIBLE);
            mRlBrandDetailWaixuan.setVisibility(View.VISIBLE);
            mWaiXuanGaoJianList = detailModel.info.waixuangaojian;
            mWaiXuanGaoJianRUAdapter.setData(mWaiXuanGaoJianList);
        } else {
            mLlBrandDetailWaixuan.setVisibility(View.GONE);
            mRvBrandDetailWaixuan.setVisibility(View.GONE);
            mRlBrandDetailWaixuan.setVisibility(View.GONE);
        }
        
        LoaderFactory.getLoader().loadNet(mIvBrandDetailFenguan, detailModel.info.fenguanlingdao.pic);
        mTvBrandDetailFenguan.setText(detailModel.info.fenguanlingdao.name);
        
        
        if (detailModel.info.chengbankeshi == null || detailModel.info.chengbankeshi.size() == 0) {
            mRlBrandDetail0.setVisibility(View.GONE);
            mRlBrandDetail1.setVisibility(View.GONE);
        } else {
            mList = detailModel.info.chengbankeshi;
            mAdapter.setData(mList);
        }
    }
    
    /**
     * 获取评论列表
     */
    private void getCommentList(boolean showProgress) {
        CommentModel.getCommentList(getBaseActivity(), WebViewModel.TYPE_BRAND_WAIXUAN, mId, showProgress, new Action1<CommentModel>() {
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
        ZanModel.getZanList(getBaseActivity(), WebViewModel.TYPE_BRAND_WAIXUAN, mId, showProgress, new Action1<ZanModel>() {
            @Override
            public void call(ZanModel zanModel) {
                mTvWebviewZan.setText("当前数量：" + zanModel.info.yeszan);
                mTvWebviewCai.setText("当前数量：" + zanModel.info.nozan);
            }
        });
    }
}
