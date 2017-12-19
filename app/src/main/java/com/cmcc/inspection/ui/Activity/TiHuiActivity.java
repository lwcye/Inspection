package com.cmcc.inspection.ui.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.KaoShiModel;
import com.trello.rxlifecycle.android.ActivityEvent;

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
public class TiHuiActivity extends BaseActivity implements View.OnClickListener {
    public static final String INTENT_ID = "id";
    /** 强筋骨、明纪律 铸造执纪铁军 */
    private TextView mTvTihuiTitle;
    /** 2017-01-01    阅读量：1234 */
    private TextView mTvTihuiDate;
    /** 问：对党员入党前的违纪问题，应如何处理？ */
    private TextView mTvTihuiTitleQuestion;
    /** 盛艳玲 */
    private TextView mTvTihuiName;
    /** （第一纪检组） */
    private TextView mTvTihuiDanwei;
    /** 13 */
    private TextView mTvTihuiZan;
    private TextView mTvTihuiContent;
    /** 2017-11-22 */
    private TextView mTvTihuiAnswerDate;
    /** 请输入解答 */
    private EditText mEtTihuiComment;
    /** 评论 */
    private Button mBtnTihuiComment;
    
    private String mId;
    
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
    
    private void loadData() {
        showLoading("");
        HttpRequest.getKaoShiService().thjlloglist(mId)
            .compose(NetWorkInterceptor.<KaoShiModel>retrySessionCreator())
            .compose(getBaseActivity().<KaoShiModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<KaoShiModel>() {
                @Override
                public void result(KaoShiModel objectModel) {
                    
                }
            }, new HttpError(this), new HttpComplete(this));
    }
    
    private void initView() {
        mTvTihuiTitle = (TextView) findViewById(R.id.tv_tihui_title);
        mTvTihuiDate = (TextView) findViewById(R.id.tv_tihui_date);
        mTvTihuiTitleQuestion = (TextView) findViewById(R.id.tv_tihui_title_question);
        mTvTihuiName = (TextView) findViewById(R.id.tv_tihui_name);
        mTvTihuiDanwei = (TextView) findViewById(R.id.tv_tihui_danwei);
        mTvTihuiZan = (TextView) findViewById(R.id.tv_tihui_zan);
        mTvTihuiContent = (TextView) findViewById(R.id.tv_tihui_content);
        mTvTihuiAnswerDate = (TextView) findViewById(R.id.tv_tihui_answer_date);
        mEtTihuiComment = (EditText) findViewById(R.id.et_tihui_comment);
        mBtnTihuiComment = (Button) findViewById(R.id.btn_tihui_comment);
        mBtnTihuiComment.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_tihui_comment:
                break;
        }
    }
}
