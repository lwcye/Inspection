package com.cmcc.inspection.feature.inspect.visitanswer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;
import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_network.model.JiafangModel;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.lib_views.BottomPopupDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class VisitAnswerActivity extends MVPBaseActivity<VisitAnswerContract.View, VisitAnswerPresenter> implements VisitAnswerContract.View, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String INTENT_KEY = "jiafangInfoBean";
    private static final String INTENT_NAME = "name";
    private static final String INTENT_GUANXI = "guanxi";
    private static final String INTENT_MOBILE = "mobile";

    /** 纪检干部培训调查问卷 */
    private TextView mTvAnswerTitle;
    /** 2017-01-01    答题量：1234人 */
    private TextView mTvAnswerDate;
    /** 单选题 */
    private TextView mTvAnswerType;
    /** 中央改进工作作风、密切联系群众八项规定出台后，各地严格执行公务接待制度，严格落实各项节约措施，坚决杜  绝公款浪费现象。我国政府狠杀浪费之风有利于？ */
    private TextView mTvAnswerContent;
    /** 践行政府宗旨，用手中权力造福人民 */
    private RadioButton mRbAnswer0;
    /** 加强廉政建设，以清廉党风带好民风 */
    private RadioButton mRbAnswer1;
    /** 广泛吸收民智，增强决策公众参与度 */
    private RadioButton mRbAnswer2;
    /** 提升德行操守，树立政府威信 */
    private RadioButton mRbAnswer3;
    private RadioGroup mRgAnswer;
    /** 下一题 */
    private Button mBtnAnswerNext;

    private ImageButton mIbShaoolDetailFont;
    private ImageButton mIbShaoolDetailShare;
    private JiafangModel.JiafangInfoBean mBean;
    private JfShiTiModel mJfShiTiModel;
    private int position = 0;
    private int type = 0;
    private String name = "";
    private String guanxi = "";
    private String moblie = "";

    private List<String> stids = new ArrayList<>();
    private List<String> daids = new ArrayList<>();
    private EditText mEtAnswerWanda;
    /** 提交 */
    private Button mBtnAnswerSubmit;

    public static void start(Context context, JiafangModel.JiafangInfoBean jiafangInfoBean, String name, String guanxi, String mobile) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(guanxi) || TextUtils.isEmpty(mobile) || jiafangInfoBean == null) {
            return;
        }

        Intent starter = new Intent(context, VisitAnswerActivity.class);
        starter.putExtra(INTENT_KEY, jiafangInfoBean);
        starter.putExtra(INTENT_NAME, name);
        starter.putExtra(INTENT_GUANXI, guanxi);
        starter.putExtra(INTENT_MOBILE, mobile);
        context.startActivity(starter);
    }

    @Override
    protected VisitAnswerPresenter createPresenter() {
        return new VisitAnswerPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mBean = getIntent().getParcelableExtra(INTENT_KEY);
        name = getIntent().getStringExtra(INTENT_NAME);
        guanxi = getIntent().getStringExtra(INTENT_GUANXI);
        moblie = getIntent().getStringExtra(INTENT_MOBILE);
        mPresenter.loadData(mBean);
        initView();
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_back, 0, 0, 0)
                .setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                })
                .setTitle("线上家访")
                .setColor(Color.WHITE, 255);
    }

    private void initView() {
        mTvAnswerTitle = (TextView) findViewById(R.id.tv_answer_title);
        mTvAnswerDate = (TextView) findViewById(R.id.tv_answer_date);
        mTvAnswerType = (TextView) findViewById(R.id.tv_answer_type);
        mTvAnswerContent = (TextView) findViewById(R.id.tv_answer_content);
        mRbAnswer0 = (RadioButton) findViewById(R.id.rb_answer_0);
        mRbAnswer1 = (RadioButton) findViewById(R.id.rb_answer_1);
        mRbAnswer2 = (RadioButton) findViewById(R.id.rb_answer_2);
        mRbAnswer3 = (RadioButton) findViewById(R.id.rb_answer_3);
        mRgAnswer = (RadioGroup) findViewById(R.id.rg_answer);
        mRgAnswer.setOnCheckedChangeListener(this);
        mBtnAnswerNext = (Button) findViewById(R.id.btn_answer_next);
        mBtnAnswerNext.setOnClickListener(this);
        mEtAnswerWanda = (EditText) findViewById(R.id.et_answer_wanda);
        mBtnAnswerSubmit = (Button) findViewById(R.id.btn_answer_submit);
        mBtnAnswerSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_shaool_detail_font:
                final BottomPopupDialog fontDialog = new BottomPopupDialog(getContext(), R.layout.dialog_font, true);
                fontDialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fontDialog.cancel();
                    }
                });
                fontDialog.show();
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
            case R.id.btn_answer_next:
                if (position + 1 == (mJfShiTiModel.info.danxuan.size() + mJfShiTiModel.info.wenda.size())) {
                    mBtnAnswerNext.setVisibility(View.INVISIBLE);
                }
                if (type == 0) {
                    stids.add(mJfShiTiModel.info.danxuan.get(position - 1).id + ",");
                    if (TextUtils.isEmpty(getRbIndex())) {
                        ToastUtils.showShortToastSafe("请选择一个选项");
                        return;
                    }
                    daids.add(getRbIndex() + "@");
                } else if (type == 1) {
                    stids.add(mJfShiTiModel.info.danxuan.get(position - 1).id + ",");
                    if (TextUtils.isEmpty(getRbIndex())) {
                        ToastUtils.showShortToastSafe("请至少选择一个选项");
                        return;
                    }
                    daids.add(mEtAnswerWanda.getText().toString().trim() + "@");
                } else {
                    stids.add(mJfShiTiModel.info.wenda.get(position - mJfShiTiModel.info.danxuan.size() - 1).id + ",");
                    daids.add(mEtAnswerWanda.getText().toString().trim() + "@");
                }

                if (mJfShiTiModel.info.danxuan != null && position < mJfShiTiModel.info.danxuan.size()) {
                    resetDanXuanData(mJfShiTiModel.info.danxuan.get(position++));
                } else if (mJfShiTiModel.info.duoxuan != null && position < mJfShiTiModel.info.duoxuan.size()) {
                    type = 1;
                    resetDanXuanData(mJfShiTiModel.info.danxuan.get(position++));
                } else {
                    type = 2;
                    resetWenDaData(mJfShiTiModel.info.wenda.get((position - mJfShiTiModel.info.danxuan.size())));
                }
                mRbAnswer0.setChecked(false);
                mRbAnswer1.setChecked(false);
                mRbAnswer2.setChecked(false);
                mRbAnswer3.setChecked(false);
                mEtAnswerWanda.setText("");
                break;
            case R.id.btn_answer_submit:
                if (type == 0) {
                    stids.add(mJfShiTiModel.info.danxuan.get(position - 1).id + ",");
                    if (TextUtils.isEmpty(getRbIndex())) {
                        ToastUtils.showShortToastSafe("请选择一个选项");
                        return;
                    }
                    daids.add(getRbIndex() + "@");
                } else if (type == 1) {
                    stids.add(mJfShiTiModel.info.danxuan.get(position - 1).id + ",");
                    if (TextUtils.isEmpty(getRbIndex())) {
                        ToastUtils.showShortToastSafe("请至少选择一个选项");
                        return;
                    }
                    daids.add(mEtAnswerWanda.getText().toString().trim() + "@");
                } else {
                    stids.add(mJfShiTiModel.info.wenda.get(position - mJfShiTiModel.info.danxuan.size() - 1).id + ",");
                    daids.add(mEtAnswerWanda.getText().toString().trim() + "@");
                }

                StringBuilder ids = new StringBuilder();
                for (String stid : stids) {
                    ids.append(stid);
                }
                CharSequence stids = ids.toString().subSequence(0, ids.toString().length() - 1);

                StringBuilder daans = new StringBuilder();
                for (String stid : daids) {
                    daans.append(stid);
                }
                CharSequence daids = daans.toString().subSequence(0, daans.toString().length() - 1);
                mPresenter.submit(mBean.id, name, guanxi, moblie, stids.toString(), daids.toString());
                break;
            default:
                break;

        }
    }

    @Override
    public void setData(JfShiTiModel jfShiTiModel) {
        mJfShiTiModel = jfShiTiModel;
        mTvAnswerTitle.setText(mJfShiTiModel.info.title);
        mTvAnswerDate.setText(TimeUtils.millis2String(Long.valueOf(mJfShiTiModel.info.create_time) * 1000, "yyyy-MM-dd") + "\t\t" + "答题量" + mJfShiTiModel.info.nums);

        resetDanXuanData(mJfShiTiModel.info.danxuan.get(position++));
    }

    @Override
    public void submitSuccess(String message) {
        ToastUtils.showShortToastSafe(message);
        finish();
    }

    public void resetDanXuanData(JfShiTiModel.ShiTiInfoBean.DanxuanBean danxuanBean) {
        mTvAnswerType.setText("单选题");
        mTvAnswerContent.setText(position + "、" + danxuanBean.title);

        for (int i = 0; i < danxuanBean.danan.size(); i++) {
            RadioButton button;
            if (i == 0) {
                button = mRbAnswer0;
            } else if (i == 1) {
                button = mRbAnswer1;
            } else if (i == 2) {
                button = mRbAnswer2;
            } else {
                button = mRbAnswer3;
            }
            if (TextUtils.isEmpty(danxuanBean.danan.get(i))) {
                button.setVisibility(View.GONE);
            } else {
                button.setVisibility(View.VISIBLE);
                button.setText(danxuanBean.danan.get(i));
            }
        }
    }

    public void resetDuoXuanData(JfShiTiModel.ShiTiInfoBean.DanxuanBean danxuanBean) {
        mTvAnswerType.setText("多选题");
        mTvAnswerContent.setText(position + "、" + danxuanBean.title);
        mRbAnswer0.setText(danxuanBean.danan.get(0));
        mRbAnswer1.setText(danxuanBean.danan.get(1));
        mRbAnswer2.setText(danxuanBean.danan.get(2));
        mRbAnswer3.setText(danxuanBean.danan.get(3));
    }

    public void resetWenDaData(JfShiTiModel.ShiTiInfoBean.WendaBean danxuanBean) {
        mRgAnswer.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.VISIBLE);
        mTvAnswerType.setText("问答题");
        mTvAnswerContent.setText(++position + "、" + danxuanBean.title);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_answer_0:
                break;
            case R.id.rb_answer_1:
                break;
            case R.id.rb_answer_2:
                break;
            case R.id.rb_answer_3:
                break;
            default:
                break;
        }

    }

    public String getRbIndex() {
        if (mRbAnswer0.isChecked()) {
            return "A";
        }
        if (mRbAnswer1.isChecked()) {
            return "B";
        }
        if (mRbAnswer2.isChecked()) {
            return "C";
        }
        if (mRbAnswer3.isChecked()) {
            return "D";
        }
        return "";
    }

}
