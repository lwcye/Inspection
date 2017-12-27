package com.hbln.inspection.feature.school.answer;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cmcc.lib_network.model.JfShiTiModel;
import com.cmcc.lib_utils.utils.EmptyUtils;
import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.utils.TitleUtil;
import com.hbln.lib_views.BottomPopupDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AnswerActivity extends MVPBaseActivity<AnswerContract.View, AnswerPresenter> implements AnswerContract.View, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private static final String INTENT_ID = "sjid";
    private static final String INTENT_NAME = "name";
    private static final String INTENT_GUANXI = "guanxi";
    private static final String INTENT_MOBILE = "mobile";
    private static final String INTENT_TYPE = "type";
    
    public static final int TYPE_KAOSHI = 0;
    public static final int TYPE_VISIT = 1;
    
    
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
    
    private JfShiTiModel mJfShiTiModel;
    private int position = 1;
    private int nums = 0;
    private int indexDanxuan = 0;
    private int indexDuoxuan = 0;
    private int indexPanDuan = 0;
    private int indexWenda = 0;
    private int mQuestionType = 0;
    private int mType = 0;
    private String mSjid = "";
    private String name = "";
    private String guanxi = "";
    private String moblie = "";
    
    private List<String> stids = new ArrayList<>();
    private List<String> daids = new ArrayList<>();
    private EditText mEtAnswerWanda;
    /** 提交 */
    private Button mBtnAnswerSubmit;
    private RadioButton mRbAnswerPanduan0;
    private RadioButton mRbAnswerPanduan1;
    private RadioGroup mRgAnswerPanduan;
    private CheckBox mRbAnswerDuoxuan0;
    private CheckBox mRbAnswerDuoxuan1;
    private CheckBox mRbAnswerDuoxuan2;
    private CheckBox mRbAnswerDuoxuan3;
    private LinearLayout mRgAnswerDuoxuan;
    
    
    @Override
    protected AnswerPresenter createPresenter() {
        return new AnswerPresenter();
    }
    
    public static void start(Context context, String sjid) {
        Intent starter = new Intent(context, AnswerActivity.class);
        starter.putExtra(INTENT_ID, sjid);
        starter.putExtra(INTENT_TYPE, TYPE_KAOSHI);
        context.startActivity(starter);
    }
    
    public static void start(Context context, String sjid, String name, String guanxi, String mobile) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(guanxi) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(sjid)) {
            return;
        }
        Intent starter = new Intent(context, AnswerActivity.class);
        starter.putExtra(INTENT_ID, sjid);
        starter.putExtra(INTENT_TYPE, TYPE_VISIT);
        starter.putExtra(INTENT_NAME, name);
        starter.putExtra(INTENT_GUANXI, guanxi);
        starter.putExtra(INTENT_MOBILE, mobile);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mSjid = getIntent().getStringExtra(INTENT_ID);
        name = getIntent().getStringExtra(INTENT_NAME);
        guanxi = getIntent().getStringExtra(INTENT_GUANXI);
        moblie = getIntent().getStringExtra(INTENT_MOBILE);
        mType = getIntent().getIntExtra(INTENT_TYPE, 0);
        initView();
        TitleUtil.attach(this)
            .setBack(true);
        mPresenter.loadData(mSjid, mType);
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
        mRbAnswerPanduan0 = (RadioButton) findViewById(R.id.rb_answer_panduan_0);
        mRbAnswerPanduan1 = (RadioButton) findViewById(R.id.rb_answer_panduan_1);
        mRgAnswerPanduan = (RadioGroup) findViewById(R.id.rg_answer_panduan);
        mRbAnswerDuoxuan0 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_0);
        mRbAnswerDuoxuan1 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_1);
        mRbAnswerDuoxuan2 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_2);
        mRbAnswerDuoxuan3 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_3);
        mRgAnswerDuoxuan = (LinearLayout) findViewById(R.id.rg_answer_duoxuan);
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
                if (answerHandle()) return;
                LogUtils.e(position, nums);
                showAnswer();
                break;
            case R.id.btn_answer_submit:
                if (answerHandle()) return;
                
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
                if (mType == TYPE_VISIT) {
                    mPresenter.submit(mSjid, name, guanxi, moblie, stids.toString(), daids.toString());
                } else {
                    mPresenter.submit(mSjid, stids.toString(), daids.toString());
                }
                break;
            default:
                break;
            
        }
    }
    
    private void showAnswer() {
        if (mJfShiTiModel.info.danxuan != null && indexDanxuan < mJfShiTiModel.info.danxuan.size()) {
            mQuestionType = 0;
            resetDanXuanData(mJfShiTiModel.info.danxuan.get(resetIndex(mQuestionType)));
        } else if (mJfShiTiModel.info.duoxuan != null && indexDuoxuan < mJfShiTiModel.info.duoxuan.size()) {
            mQuestionType = 1;
            resetDuoXuanData(mJfShiTiModel.info.duoxuan.get(resetIndex(mQuestionType)));
        } else if (mJfShiTiModel.info.panduan != null && indexPanDuan < mJfShiTiModel.info.panduan.size()) {
            mQuestionType = 2;
            resetPanDuanData(mJfShiTiModel.info.panduan.get(resetIndex(mQuestionType)));
        } else {
            mQuestionType = 3;
            resetWenDaData(mJfShiTiModel.info.wenda.get(resetIndex(mQuestionType)));
        }
        mRbAnswer0.setChecked(false);
        mRbAnswer1.setChecked(false);
        mRbAnswer2.setChecked(false);
        mRbAnswer3.setChecked(false);
        
        mRbAnswerDuoxuan0.setChecked(false);
        mRbAnswerDuoxuan1.setChecked(false);
        mRbAnswerDuoxuan2.setChecked(false);
        mRbAnswerDuoxuan3.setChecked(false);
        
        mRbAnswerPanduan0.setChecked(false);
        mRbAnswerPanduan1.setChecked(false);
        
        mEtAnswerWanda.setText("");
    }
    
    
    private int resetIndex(int type) {
        position = indexDanxuan + indexDuoxuan + indexPanDuan + indexWenda + 1;
        if (type == 0) {
            return indexDanxuan;
        }
        if (type == 1) {
            return indexDuoxuan;
        }
        if (type == 2) {
            return indexPanDuan;
        }
        if (type == 3) {
            return indexWenda;
        }
        return position;
    }
    
    private boolean answerHandle() {
        //类型 0--单选 1--多选 2--判断 3--问答
        if (mQuestionType == 0) {
            //单选答案
            stids.add(mJfShiTiModel.info.danxuan.get(indexDanxuan).id + ",");
            if (TextUtils.isEmpty(getRbIndex())) {
                ToastUtils.showShortToastSafe("请选择一个选项");
                return true;
            }
            daids.add(getRbIndex() + "@");
            indexDanxuan++;
        } else if (mQuestionType == 1) {
            //多选答案
            stids.add(mJfShiTiModel.info.duoxuan.get(indexDuoxuan).id + ",");
            if (TextUtils.isEmpty(getRbDuoxuanIndex())) {
                ToastUtils.showShortToastSafe("请至少选择一个选项");
                return true;
            }
            daids.add(getRbDuoxuanIndex() + "@");
            indexDuoxuan++;
        } else if (mQuestionType == 2) {
            //判断答案
            LogUtils.e(indexPanDuan);
            JfShiTiModel.ShiTiInfoBean.PanduanBean panduanBean = mJfShiTiModel.info.panduan.get(indexPanDuan);
            stids.add(panduanBean.id + ",");
            if (TextUtils.isEmpty(getRbPanDanIndex(panduanBean))) {
                ToastUtils.showShortToastSafe("请至少选择一个选项");
                return true;
            }
            daids.add(getRbPanDanIndex(panduanBean) + "@");
            indexPanDuan++;
        } else {
            //问答
            stids.add(mJfShiTiModel.info.wenda.get(indexWenda).id + ",");
            daids.add(mEtAnswerWanda.getText().toString().trim() + "@");
            indexWenda++;
        }
        resetIndex(mQuestionType);
        LogUtils.e(position, nums);
        //判断是否是最后一题
        if (position >= nums) {
            mBtnAnswerNext.setVisibility(View.INVISIBLE);
        }
        return false;
    }
    
    @Override
    public void setData(JfShiTiModel jfShiTiModel) {
        mJfShiTiModel = jfShiTiModel;
        mTvAnswerTitle.setText(mJfShiTiModel.info.title);
        mTvAnswerDate.setText(TimeUtils.millis2String(Long.valueOf(mJfShiTiModel.info.create_time) * 1000, "yyyy-MM-dd") + "\t\t" + "答题量：" + mJfShiTiModel.info.nums);
        if (!EmptyUtils.isEmpty(mJfShiTiModel.info.danxuan)) {
            nums += mJfShiTiModel.info.danxuan.size();
        }
        if (!EmptyUtils.isEmpty(mJfShiTiModel.info.duoxuan)) {
            nums += mJfShiTiModel.info.duoxuan.size();
        }
        if (!EmptyUtils.isEmpty(mJfShiTiModel.info.panduan)) {
            nums += mJfShiTiModel.info.panduan.size();
        }
        if (!EmptyUtils.isEmpty(mJfShiTiModel.info.wenda)) {
            nums += mJfShiTiModel.info.wenda.size();
        }
        
        showAnswer();
    }
    
    @Override
    public void submitSuccess(String message) {
        ToastUtils.showShortToastSafe(message);
        finish();
    }
    
    public void resetDanXuanData(JfShiTiModel.ShiTiInfoBean.DanxuanBean danxuanBean) {
        mTvAnswerType.setText("单选题");
        mRgAnswer.setVisibility(View.VISIBLE);
        mRgAnswerDuoxuan.setVisibility(View.GONE);
        mRgAnswerPanduan.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.GONE);
        
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
    
    public void resetDuoXuanData(JfShiTiModel.ShiTiInfoBean.DuoxuanBean duoxuanBean) {
        mTvAnswerType.setText("多选题");
        mRgAnswer.setVisibility(View.GONE);
        mRgAnswerDuoxuan.setVisibility(View.VISIBLE);
        mRgAnswerPanduan.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.GONE);
        
        mTvAnswerContent.setText(position + "、" + duoxuanBean.title);
        for (int i = 0; i < duoxuanBean.danan.size(); i++) {
            CheckBox button;
            if (i == 0) {
                button = mRbAnswerDuoxuan0;
            } else if (i == 1) {
                button = mRbAnswerDuoxuan1;
            } else if (i == 2) {
                button = mRbAnswerDuoxuan2;
            } else {
                button = mRbAnswerDuoxuan3;
            }
            if (TextUtils.isEmpty(duoxuanBean.danan.get(i))) {
                button.setVisibility(View.GONE);
            } else {
                button.setVisibility(View.VISIBLE);
                button.setText(duoxuanBean.danan.get(i));
            }
        }
    }
    
    private void resetPanDuanData(JfShiTiModel.ShiTiInfoBean.PanduanBean panduanBean) {
        mTvAnswerType.setText("判断题");
        mRgAnswer.setVisibility(View.GONE);
        mRgAnswerDuoxuan.setVisibility(View.GONE);
        mRgAnswerPanduan.setVisibility(View.VISIBLE);
        mEtAnswerWanda.setVisibility(View.GONE);
        
        mTvAnswerContent.setText(position + "、" + panduanBean.title);
        for (int i = 0; i < panduanBean.danan.size(); i++) {
            RadioButton button;
            if (i == 0) {
                button = mRbAnswerPanduan0;
            } else {
                button = mRbAnswerPanduan1;
            }
            if (TextUtils.isEmpty(panduanBean.danan.get(i))) {
                button.setVisibility(View.GONE);
            } else {
                button.setVisibility(View.VISIBLE);
                button.setText(panduanBean.danan.get(i));
            }
        }
    }
    
    public void resetWenDaData(JfShiTiModel.ShiTiInfoBean.WendaBean danxuanBean) {
        mTvAnswerType.setText("问答题");
        mRgAnswer.setVisibility(View.GONE);
        mRgAnswerDuoxuan.setVisibility(View.GONE);
        mRgAnswerPanduan.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.VISIBLE);
        
        mTvAnswerContent.setText(position + "、" + danxuanBean.title);
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
    
    public String getRbPanDanIndex(JfShiTiModel.ShiTiInfoBean.PanduanBean bean) {
        if (mRbAnswerPanduan0.isChecked()) {
            return bean.danan.get(0);
        }
        if (mRbAnswerPanduan1.isChecked()) {
            return bean.danan.get(1);
        }
        return "";
    }
    
    public String getRbDuoxuanIndex() {
        StringBuilder builder = new StringBuilder();
        if (mRbAnswerDuoxuan0.isChecked()) {
            builder.append("A");
        }
        if (mRbAnswerDuoxuan0.isChecked()) {
            builder.append("B");
        }
        if (mRbAnswerDuoxuan0.isChecked()) {
            builder.append("C");
        }
        if (mRbAnswerDuoxuan0.isChecked()) {
            builder.append("D");
        }
        return builder.toString();
    }
}
