package com.hbln.inspection.feature.school.answer;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.cmcc.lib_utils.utils.LogUtils;
import com.cmcc.lib_utils.utils.TimeUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.hbln.inspection.network.model.JfShiTiModel;
import com.hbln.inspection.utils.TitleUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */
public class AnswerActivity extends MVPBaseActivity<AnswerContract.View, AnswerPresenter> implements AnswerContract.View, View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public static final int TYPE_KAOSHI_XUEXI = 10;
    public static final int TYPE_KAOSHI_CESHI = 11;
    public static final int TYPE_VISIT = 1;
    public static final int TYPE_TEST = 2;
    private static final String INTENT_ID = "sjid";
    private static final String INTENT_NAME = "name";
    private static final String INTENT_GUANXI = "guanxi";
    private static final String INTENT_MOBILE = "mobile";
    private static final String INTENT_START_TIME = "startTime";
    private static final String INTENT_END_TIME = "endTime";
    private static final String INTENT_TYPE = "type";
    /**
     * 标题
     */
    private TextView mTvAnswerTitle;
    /**
     * 2017-01-01    答题量：1234人
     */
    private TextView mTvAnswerDate;
    /**
     * 单选题
     */
    private TextView mTvAnswerType;
    /**
     * 内容
     */
    private TextView mTvAnswerContent;
    /**
     * 践行政府宗旨，用手中权力造福人民
     */
    private RadioButton mRbAnswer0;
    /**
     * 加强廉政建设，以清廉党风带好民风
     */
    private RadioButton mRbAnswer1;
    /**
     * 广泛吸收民智，增强决策公众参与度
     */
    private RadioButton mRbAnswer2;
    /**
     * 提升德行操守，树立政府威信
     */
    private RadioButton mRbAnswer3;
    private RadioGroup mRgAnswer;
    /**
     * 下一题
     */
    private Button mBtnAnswerNext;
    private JfShiTiModel mJfShiTiModel;
    private int position = 0;
    private int mType = 0;
    private String mSjid = "";
    private String name = "";
    private String guanxi = "";
    private String moblie = "";
    private long startTime = 0L;
    private long endTime = 0L;
    private long delayTime = 0L;
    private EditText mEtAnswerWanda;
    /**
     * 提交
     */
    private Button mBtnAnswerSubmit;
    private RadioButton mRbAnswerPanduan0;
    private RadioButton mRbAnswerPanduan1;
    private RadioGroup mRgAnswerPanduan;
    private CheckBox mRbAnswerDuoxuan0;
    private CheckBox mRbAnswerDuoxuan1;
    private CheckBox mRbAnswerDuoxuan2;
    private CheckBox mRbAnswerDuoxuan3;
    private LinearLayout mRgAnswerDuoxuan;
    /**
     * 正确答案：B
     */
    private TextView mTvAnswerOkDaan;
    /**
     * 上一题
     */
    private Button mBtnAnswerPre;
    /**
     * 正确答案
     */
    private Button mBtnAnswerOkAnswer;
    /**
     * 测试剩余时间：
     */
    private TextView mTvAnswerCount;

    public static void start(Context context, String sjid, int type) {
        Intent starter = new Intent(context, AnswerActivity.class);
        starter.putExtra(INTENT_ID, sjid);
        starter.putExtra(INTENT_TYPE, type);
        context.startActivity(starter);
    }

    /**
     * 在线测试
     *
     * @param context 上下文
     * @param sjid 试题ID
     * @param startTime 测试开始时间
     * @param endTime 测试结束时间
     * @param type 类型
     */
    public static void start(Context context, String sjid, long startTime, long endTime, int type) {
        Intent starter = new Intent(context, AnswerActivity.class);
        starter.putExtra(INTENT_ID, sjid);
        starter.putExtra(INTENT_START_TIME, startTime);
        starter.putExtra(INTENT_END_TIME, endTime);
        starter.putExtra(INTENT_TYPE, type);
        context.startActivity(starter);
    }

    /**
     * 家访
     *
     * @param context 上下文
     * @param sjid 试题ID
     * @param name 名字
     * @param guanxi 关系
     * @param mobile 手机
     */
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
    protected AnswerPresenter createPresenter() {
        return new AnswerPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        handleIntent();
        initView();
        TitleUtil.attach(this)
                .setBack(true);
        //处理测试时间
        if (handleTestTime()) {
            finish();
            return;
        }

        //请求试题
        mPresenter.loadData(mSjid, mType);

    }

    /**
     * 处理Intent
     */
    private void handleIntent() {
        mSjid = getIntent().getStringExtra(INTENT_ID);
        name = getIntent().getStringExtra(INTENT_NAME);
        guanxi = getIntent().getStringExtra(INTENT_GUANXI);
        moblie = getIntent().getStringExtra(INTENT_MOBILE);
        startTime = getIntent().getLongExtra(INTENT_START_TIME, 0L);
        endTime = getIntent().getLongExtra(INTENT_END_TIME, 0L);
        mType = getIntent().getIntExtra(INTENT_TYPE, 0);
    }

    /**
     * 初始化布局
     */
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
        mBtnAnswerPre = (Button) findViewById(R.id.btn_answer_pre);
        mBtnAnswerPre.setOnClickListener(this);
        mBtnAnswerOkAnswer = (Button) findViewById(R.id.btn_answer_ok_answer);
        mBtnAnswerOkAnswer.setOnClickListener(this);
        mRbAnswerPanduan0 = (RadioButton) findViewById(R.id.rb_answer_panduan_0);
        mRbAnswerPanduan1 = (RadioButton) findViewById(R.id.rb_answer_panduan_1);
        mRgAnswerPanduan = (RadioGroup) findViewById(R.id.rg_answer_panduan);
        mRbAnswerDuoxuan0 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_0);
        mRbAnswerDuoxuan1 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_1);
        mRbAnswerDuoxuan2 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_2);
        mRbAnswerDuoxuan3 = (CheckBox) findViewById(R.id.rb_answer_duoxuan_3);
        mRgAnswerDuoxuan = (LinearLayout) findViewById(R.id.rg_answer_duoxuan);
        mTvAnswerOkDaan = (TextView) findViewById(R.id.tv_answer_ok_daan);
        mTvAnswerCount = (TextView) findViewById(R.id.tv_answer_count);

        if (mType == TYPE_KAOSHI_XUEXI || mType == TYPE_TEST) {
            mBtnAnswerOkAnswer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 处理开始时间
     *
     * @return 是否退出
     */
    private boolean handleTestTime() {
        //判断开始时间
        if (startTime > 0 && endTime > 0) {
            long timeInMillis = Calendar.getInstance().getTimeInMillis() / 1000;
            if (timeInMillis < startTime) {
                //未到开始时间，直接退出
                ToastUtils.showShortToastSafe("还未到开始时间，请在" + TimeUtils.millis2String(startTime * 1000L) + "以后进行测试");
                return true;
            } else {
                long delay = endTime - timeInMillis;
                if (delay > 0) {
                    if (delay > 60 * 60) {
                        delay = 60 * 60;
                    }
                    delayTime = delay;
                    mTvAnswerCount.setVisibility(View.VISIBLE);
                    mTvAnswerCount.setText("测试剩余时间：" + TimeUtils.getFriendlyTimeSpan(delayTime * 1000L));
                    Observable.interval(1, TimeUnit.SECONDS)
                            .compose(applySchedulers(ActivityEvent.DESTROY))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Action1<Object>() {
                                @Override
                                public void call(Object o) {
                                    if (delayTime == 0) {
                                        answerHandle(position);
                                        ProgressDialog progressDialog = new ProgressDialog(getContext());
                                        progressDialog.setMessage("正在提交答案");
                                        progressDialog.show();
                                        performSubmit();
                                    } else {
                                        delayTime = delayTime - 1;
                                        if (delayTime > 60 * 5) {
                                            mTvAnswerCount.setTextColor(getCompatColor(R.color.black));
                                        } else {
                                            mTvAnswerCount.setTextColor(Color.RED);
                                        }
                                        mTvAnswerCount.setText("测试剩余时间：" + TimeUtils.getFriendlyTimeSpan(delayTime * 1000L));
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    LogUtils.e(throwable);
                                }
                            });
                } else {
                    ToastUtils.showLongToastSafe("该测试已经结束");
                    return true;
                }
            }
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_answer_next:
                answerHandle(position);
                position++;
                showAnswer(position);
                break;
            case R.id.btn_answer_submit:
                answerHandle(position);
                performSubmit();
                break;
            case R.id.btn_answer_pre:
                answerHandle(position);
                position--;
                showAnswer(position);
                break;
            case R.id.btn_answer_ok_answer:
                showRealAnswer(position);
                break;
            default:
                break;
        }
    }

    /**
     * 执行提交任务
     */
    private void performSubmit() {
        StringBuilder ids = new StringBuilder();
        StringBuilder daans = new StringBuilder();
        for (JfShiTiModel.ShiTiInfoBean.QuestionBean questionBean : mJfShiTiModel.info.questionList) {
            ids.append(questionBean.id).append(",");
            daans.append(questionBean.answer).append("@");
        }
        CharSequence stids = ids.toString().subSequence(0, ids.toString().length() - 1);
        CharSequence daids = daans.toString().subSequence(0, daans.toString().length() - 1);

        if (mType == TYPE_VISIT) {
            mPresenter.submit(mSjid, name, guanxi, moblie, stids.toString(), daids.toString());
        } else {
            mPresenter.submit(mSjid, stids.toString(), daids.toString());
        }
    }

    /**
     * 显示正确答案，只需要学习资料的栏目显示
     *
     * @param position
     */
    private void showRealAnswer(int position) {
        JfShiTiModel.ShiTiInfoBean.QuestionBean questionBean = mJfShiTiModel.info.questionList.get(position);
        mTvAnswerOkDaan.setVisibility(View.VISIBLE);
        //类型 0--单选 1--多选 2--判断 3--问答
        if (JfShiTiModel.CAT_ID_DAN_XUAN == questionBean.catid) {
            //单选答案
            if (TextUtils.isEmpty(getRbIndex())) {
                ToastUtils.showShortToastSafe("请选择一个选项");
            }
            String okdaan = questionBean.okdaan.trim();
            mTvAnswerOkDaan.setText("正确答案：" + okdaan);
            RadioButton btn = null;
            switch (okdaan) {
                case "A":
                    btn = mRbAnswer0;
                    break;
                case "B":
                    btn = mRbAnswer1;
                    break;
                case "C":
                    btn = mRbAnswer2;
                    break;
                case "D":
                    btn = mRbAnswer3;
                    break;
                default:
                    break;
            }
            if (getRbIndex().equals(okdaan)) {
                mTvAnswerOkDaan.setTextColor(Color.GREEN);
                if (btn != null) {
                    btn.setTextColor(Color.GREEN);
                }
            } else {
                mTvAnswerOkDaan.setTextColor(Color.RED);
                if (btn != null) {
                    btn.setTextColor(Color.GREEN);
                }
                switch (getRbIndex(getRbIndex())) {
                    case 0:
                        mRbAnswer0.setTextColor(Color.RED);
                        break;
                    case 1:
                        mRbAnswer1.setTextColor(Color.RED);
                        break;
                    case 2:
                        mRbAnswer2.setTextColor(Color.RED);
                        break;
                    default:
                        mRbAnswer3.setTextColor(Color.RED);
                        break;
                }
            }
        } else if (JfShiTiModel.CAT_ID_DUO_XUAN == questionBean.catid) {
            //多选答案
            if (TextUtils.isEmpty(getRbDuoxuanIndex())) {
                ToastUtils.showShortToastSafe("请至少选择一个选项");
            }
            String okdaan = questionBean.okdaan.trim();
            mTvAnswerOkDaan.setText("正确答案：" + okdaan);
            if (getRbDuoxuanIndex().equals(okdaan)) {
                mTvAnswerOkDaan.setTextColor(Color.GREEN);
            } else {
                mTvAnswerOkDaan.setTextColor(Color.RED);
            }
        } else if (JfShiTiModel.CAT_ID_PAN_DUAN == questionBean.catid) {
            //判断答案
            if (TextUtils.isEmpty(getRbPanDanIndex(questionBean))) {
                ToastUtils.showShortToastSafe("请至少选择一个选项");
            }
            String okdaan = questionBean.okdaan.trim();
            mTvAnswerOkDaan.setText("正确答案：" + okdaan);
            RadioButton RadioButton;
            if (questionBean.danan.indexOf(getRbPanDanIndex(questionBean)) == 0) {
                RadioButton = mRbAnswerPanduan0;
            } else {
                RadioButton = mRbAnswerPanduan1;
            }
            RadioButton.setTextColor(Color.GREEN);

            if (getRbPanDanIndex(questionBean).equals(okdaan)) {
                mTvAnswerOkDaan.setTextColor(Color.GREEN);
            } else {
                mTvAnswerOkDaan.setTextColor(Color.RED);
                RadioButton.setTextColor(Color.RED);
                if (RadioButton.getId() == mRbAnswerPanduan0.getId()) {
                    mRbAnswerPanduan1.setTextColor(Color.GREEN);
                } else {
                    mRbAnswerPanduan0.setTextColor(Color.GREEN);
                }
            }
        }
    }

    /**
     * 显示题目
     *
     * @param position 序号
     */
    private void showAnswer(int position) {
        JfShiTiModel.ShiTiInfoBean.QuestionBean questionBean = mJfShiTiModel.info.questionList.get(position);
        //初始化
        int color = getCompatColor(R.color.gray22);
        mRgAnswer.clearCheck();
        mRbAnswer0.setChecked(false);
        mRbAnswer1.setChecked(false);
        mRbAnswer2.setChecked(false);
        mRbAnswer3.setChecked(false);
        mRbAnswer0.setTextColor(color);
        mRbAnswer1.setTextColor(color);
        mRbAnswer2.setTextColor(color);
        mRbAnswer3.setTextColor(color);

        mRbAnswerDuoxuan0.setChecked(false);
        mRbAnswerDuoxuan1.setChecked(false);
        mRbAnswerDuoxuan2.setChecked(false);
        mRbAnswerDuoxuan3.setChecked(false);
        mRbAnswerDuoxuan0.setTextColor(color);
        mRbAnswerDuoxuan1.setTextColor(color);
        mRbAnswerDuoxuan2.setTextColor(color);
        mRbAnswerDuoxuan3.setTextColor(color);

        mRgAnswerPanduan.clearCheck();
        mRbAnswerPanduan0.setChecked(false);
        mRbAnswerPanduan1.setChecked(false);
        mRbAnswerPanduan0.setTextColor(color);
        mRbAnswerPanduan1.setTextColor(color);

        mEtAnswerWanda.setText("");

        //显示
        if (questionBean.catid == JfShiTiModel.CAT_ID_DAN_XUAN) {
            resetDanXuanData(questionBean);
        } else if (questionBean.catid == JfShiTiModel.CAT_ID_DUO_XUAN) {
            resetDuoXuanData(questionBean);
        } else if (questionBean.catid == JfShiTiModel.CAT_ID_PAN_DUAN) {
            resetPanDuanData(questionBean);
        } else {
            resetWenDaData(questionBean);
        }


        /*
        * 上一题：第二题开始都有前一题
        * 下一题：最后一题没有，其他都有
        * 提交：最后一题有
        * 查看答案，学习资料有
        */
        if (position == 0) {
            mBtnAnswerPre.setVisibility(View.INVISIBLE);
        } else {
            mBtnAnswerPre.setVisibility(View.VISIBLE);
        }
        //正确答案
        mTvAnswerOkDaan.setVisibility(View.GONE);
        //判断是否是最后一题
        if (this.position == mJfShiTiModel.info.questionList.size() - 1) {

            if (mType == TYPE_TEST) {
                mBtnAnswerNext.setVisibility(View.INVISIBLE);
                mBtnAnswerSubmit.setVisibility(View.GONE);
            } else {
                mBtnAnswerNext.setVisibility(View.GONE);
                mBtnAnswerSubmit.setVisibility(View.VISIBLE);
            }
        } else {
            mBtnAnswerNext.setVisibility(View.VISIBLE);
            mBtnAnswerSubmit.setVisibility(View.GONE);
        }
        //查看试题均不能点击
        if (mType == TYPE_TEST) {
            mRbAnswer0.setEnabled(false);
            mRbAnswer1.setEnabled(false);
            mRbAnswer2.setEnabled(false);
            mRbAnswer3.setEnabled(false);

            mRbAnswerDuoxuan0.setEnabled(false);
            mRbAnswerDuoxuan1.setEnabled(false);
            mRbAnswerDuoxuan2.setEnabled(false);
            mRbAnswerDuoxuan3.setEnabled(false);

            mRbAnswerPanduan0.setEnabled(false);
            mRbAnswerPanduan1.setEnabled(false);

            mEtAnswerWanda.setEnabled(false);

            //            showRealAnswer(position);
        }
    }

    /**
     * 判断是否选择答案，如果是最后一题则不显示下一题
     *
     * @param position 当前题号
     */
    private void answerHandle(int position) {
        JfShiTiModel.ShiTiInfoBean.QuestionBean questionBean = mJfShiTiModel.info.questionList.get(position);
        //类型 0--单选 1--多选 2--判断 3--问答
        questionBean.answer = "";
        if (JfShiTiModel.CAT_ID_DAN_XUAN == questionBean.catid) {
            //多选答案
            questionBean.answer = getRbIndex();
        } else if (JfShiTiModel.CAT_ID_DUO_XUAN == questionBean.catid) {
            //多选答案
            questionBean.answer = getRbDuoxuanIndex();
        } else if (JfShiTiModel.CAT_ID_PAN_DUAN == questionBean.catid) {
            //判断答案
            questionBean.answer = getRbPanDanIndex(questionBean);
        } else {
            //问答
            questionBean.answer = mEtAnswerWanda.getText().toString().trim();
        }
    }

    @Override
    public void setData(JfShiTiModel jfShiTiModel) {
        mJfShiTiModel = jfShiTiModel;
        //问题
        mJfShiTiModel.info.initData();
        if (mType == AnswerActivity.TYPE_TEST) {
            jfShiTiModel.info.resetAnswerData();
        }
        //标题
        mTvAnswerTitle.setText(mJfShiTiModel.info.title);

        if (mJfShiTiModel.info.questionList.size() <= 0) {
            ToastUtils.showShortToastSafe("该测试没有题目，请稍后再试");
            finish();
            return;
        }
        //时间和题量
        if (mType == AnswerActivity.TYPE_TEST) {
            mTvAnswerDate.setText("答题量：共" + mJfShiTiModel.info.questionList.size() + "题" +
                    "\t\t" + "得分：" + mJfShiTiModel.info.fenshu + "分");
        } else {
            mTvAnswerDate.setText(mJfShiTiModel.info.times + "\t\t"
                    + "答题量：共" + mJfShiTiModel.info.questionList.size() + "题");
        }

        //显示题目
        position = 0;
        showAnswer(position);
    }

    @Override
    public void submitSuccess(String message) {
        if (!TextUtils.isEmpty(message)) {
            int num = 0;
            for (JfShiTiModel.ShiTiInfoBean.QuestionBean questionBean : mJfShiTiModel.info.questionList) {
                if (!TextUtils.isEmpty(questionBean.answer)) {
                    num++;
                }
            }
            AnswerResultActivity.start(getContext(), mJfShiTiModel.info.title,
                    num + "/" + mJfShiTiModel.info.questionList.size(),
                    message);
        }
        finish();
    }

    /**
     * 重置单选数据
     *
     * @param danxuanBean 单选实例
     */
    public void resetDanXuanData(JfShiTiModel.ShiTiInfoBean.QuestionBean danxuanBean) {
        mTvAnswerType.setText("单选题");
        mRgAnswer.setVisibility(View.VISIBLE);
        mRgAnswerDuoxuan.setVisibility(View.GONE);
        mRgAnswerPanduan.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.GONE);

        mTvAnswerContent.setText((position + 1) + "、" + danxuanBean.title);

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

        //已选择答案
        if (!TextUtils.isEmpty(danxuanBean.answer)) {
            int rbIndex = getRbIndex(danxuanBean.answer);
            RadioButton button;
            if (rbIndex == 0) {
                button = mRbAnswer0;
            } else if (rbIndex == 1) {
                button = mRbAnswer1;
            } else if (rbIndex == 2) {
                button = mRbAnswer2;
            } else {
                button = mRbAnswer3;
            }
            button.setChecked(true);
        }
    }

    /**
     * 重置多选的题目
     *
     * @param duoxuanBean 多选实例
     */
    public void resetDuoXuanData(JfShiTiModel.ShiTiInfoBean.QuestionBean duoxuanBean) {
        mTvAnswerType.setText("多选题");
        mRgAnswer.setVisibility(View.GONE);
        mRgAnswerDuoxuan.setVisibility(View.VISIBLE);
        mRgAnswerPanduan.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.GONE);

        mTvAnswerContent.setText((position + 1) + "、" + duoxuanBean.title);
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
        //已选择答案
        if (!TextUtils.isEmpty(duoxuanBean.answer)) {
            for (int i = 0; i < duoxuanBean.answer.length(); i++) {
                char s1 = duoxuanBean.answer.charAt(i);
                int rbIndex = getRbIndex(String.valueOf(s1));
                CheckBox button;
                if (rbIndex == 0) {
                    button = mRbAnswerDuoxuan0;
                } else if (rbIndex == 1) {
                    button = mRbAnswerDuoxuan1;
                } else if (rbIndex == 2) {
                    button = mRbAnswerDuoxuan2;
                } else {
                    button = mRbAnswerDuoxuan3;
                }
                button.setChecked(true);
            }
        }
    }

    private void resetPanDuanData(JfShiTiModel.ShiTiInfoBean.QuestionBean panduanBean) {
        mTvAnswerType.setText("判断题");
        mRgAnswer.setVisibility(View.GONE);
        mRgAnswerDuoxuan.setVisibility(View.GONE);
        mRgAnswerPanduan.setVisibility(View.VISIBLE);
        mEtAnswerWanda.setVisibility(View.GONE);

        mTvAnswerContent.setText((position + 1) + "、" + panduanBean.title);
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
        //已选择答案
        if (!TextUtils.isEmpty(panduanBean.answer)) {
            int rbIndex = panduanBean.danan.indexOf(panduanBean.answer);
            RadioButton button;
            if (rbIndex == 0) {
                button = mRbAnswerPanduan0;
            } else {
                button = mRbAnswerPanduan0;
            }
            button.setChecked(true);
        }
    }

    public void resetWenDaData(JfShiTiModel.ShiTiInfoBean.QuestionBean WenDaBean) {
        mTvAnswerType.setText("问答题");
        mRgAnswer.setVisibility(View.GONE);
        mRgAnswerDuoxuan.setVisibility(View.GONE);
        mRgAnswerPanduan.setVisibility(View.GONE);
        mEtAnswerWanda.setVisibility(View.VISIBLE);

        mTvAnswerContent.setText((position + 1) + "、" + WenDaBean.title);

        //已选择答案
        if (!TextUtils.isEmpty(WenDaBean.answer)) {
            mEtAnswerWanda.setText(WenDaBean.answer);
        }
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

    /**
     * 获取位置
     *
     * @return
     */
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

    /**
     * 获得选择的位置
     *
     * @param index
     * @return
     */
    public int getRbIndex(String index) {
        if (index.equals("A")) {
            return 0;
        }
        if (index.equals("B")) {
            return 1;
        }
        if (index.equals("C")) {
            return 2;
        }
        if (index.equals("D")) {
            return 3;
        }
        return 0;
    }

    /**
     * 获得判断的答案
     *
     * @param bean
     * @return
     */
    public String getRbPanDanIndex(JfShiTiModel.ShiTiInfoBean.QuestionBean bean) {
        if (mRbAnswerPanduan0.isChecked()) {
            return bean.danan.get(0);
        }
        if (mRbAnswerPanduan1.isChecked()) {
            return bean.danan.get(1);
        }
        return "";
    }

    /**
     * 获得多选的答案
     *
     * @return
     */
    public String getRbDuoxuanIndex() {
        StringBuilder builder = new StringBuilder();
        if (mRbAnswerDuoxuan0.isChecked()) {
            builder.append("A");
        }
        if (mRbAnswerDuoxuan1.isChecked()) {
            builder.append("B");
        }
        if (mRbAnswerDuoxuan2.isChecked()) {
            builder.append("C");
        }
        if (mRbAnswerDuoxuan3.isChecked()) {
            builder.append("D");
        }
        return builder.toString();
    }
}
