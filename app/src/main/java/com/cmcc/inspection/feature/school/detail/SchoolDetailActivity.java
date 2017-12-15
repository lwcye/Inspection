package com.cmcc.inspection.feature.school.detail;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.inspection.widget.DialogUtils;
import com.cmcc.inspection.widget.x5.utils.BridgeWebView;
import com.cmcc.inspection.widget.x5.x5.WebViewManager;
import com.cmcc.lib_network.model.SchoolModel;
import com.hbln.lib_views.BottomPopupDialog;
import com.hbln.lib_views.DrawableCenterTextView;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SchoolDetailActivity extends MVPBaseActivity<SchoolDetailContract.View, SchoolDetailPresenter> implements SchoolDetailContract.View, View.OnClickListener {
    public static final String INTENT_ID = "ID";
    public static final String INTENT_TYPE = "type";
    /** 当前数量：1234 */
    private DrawableCenterTextView mTvSchoolDetailZan;
    /** 当前数量：0 */
    private DrawableCenterTextView mTvSchoolDetailCai;
    private ImageButton mIbShaoolDetailFont;
    private ImageButton mIbShaoolDetailShare;
    private String id = "";
    /** 强筋骨、明纪律 铸造执纪铁军 */
    private TextView mTvShcoolDetailTitle;
    /** 2017-01-01    阅读量：1234 */
    private TextView mTvShcoolDetailDate;
    private BridgeWebView mWvSchoolDetail;
    private EditText mEtSchoolComment;
    /** 提交 */
    private Button mBtnSchoolDetailSubmit;
    private BottomPopupDialog fontDialog;
    
    @Override
    protected SchoolDetailPresenter createPresenter() {
        return new SchoolDetailPresenter();
    }
    
    public static void start(Context context, String id) {
        Intent starter = new Intent(context, SchoolDetailActivity.class);
        starter.putExtra(INTENT_ID, id);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);
        id = getIntent().getStringExtra(INTENT_ID);
        initView();
        mPresenter.loadData(id);
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true);
        mTvSchoolDetailZan = (DrawableCenterTextView) findViewById(R.id.tv_school_detail_zan);
        mTvSchoolDetailZan.setOnClickListener(this);
        mTvSchoolDetailCai = (DrawableCenterTextView) findViewById(R.id.tv_school_detail_cai);
        mTvSchoolDetailCai.setOnClickListener(this);
        mIbShaoolDetailFont = (ImageButton) findViewById(R.id.ib_shaool_detail_font);
        mIbShaoolDetailFont.setOnClickListener(this);
        mIbShaoolDetailShare = (ImageButton) findViewById(R.id.ib_shaool_detail_share);
        mIbShaoolDetailShare.setOnClickListener(this);
        mTvShcoolDetailTitle = (TextView) findViewById(R.id.tv_shcool_detail_title);
        mTvShcoolDetailDate = (TextView) findViewById(R.id.tv_shcool_detail_date);
        mWvSchoolDetail = (BridgeWebView) findViewById(R.id.wv_school_detail);
        mEtSchoolComment = (EditText) findViewById(R.id.et_school_comment);
        mBtnSchoolDetailSubmit = (Button) findViewById(R.id.btn_school_detail_submit);
        mBtnSchoolDetailSubmit.setOnClickListener(this);
        
        WebViewManager.getInstance().initWebView(mWvSchoolDetail);
    }
    
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_school_detail_zan:
                break;
            case R.id.tv_school_detail_cai:
                break;
            case R.id.ib_shaool_detail_font:
                DialogUtils.getInstance().showFont(getContext(), mWvSchoolDetail);
                break;
            case R.id.ib_shaool_detail_share:
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
            case R.id.btn_school_detail_submit:
                break;
        }
    }
    
    @Override
    public void setData(SchoolModel.SchoolInfo data) {
        mTvShcoolDetailTitle.setText(data.title);
        mTvShcoolDetailDate.setText(data.times + "\t\t" + "阅读量：" + data.nums);
        mWvSchoolDetail.loadDataWithBaseURL(null, data.content, "text/html", "utf-8", null);
    }
}
