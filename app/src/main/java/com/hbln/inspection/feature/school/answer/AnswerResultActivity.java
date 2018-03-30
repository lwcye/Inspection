package com.hbln.inspection.feature.school.answer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hbln.inspection.R;
import com.hbln.inspection.base.MyActivity;
import com.hbln.inspection.utils.TitleUtil;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2018/1/2
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class AnswerResultActivity extends MyActivity {
    public static final String INTENT_TITLE = "title";
    public static final String INTENT_NUMS = "nums";
    public static final String INTENT_CHENG_JI = "chengji";
    public static String TYPE = "在线测试";
    private String title;
    private String nums;
    private String chengji;
    private TextView mTvAnswerResult;
    private TextView mTvAnswerResultTitle;
    private TextView mTvAnswerResultChengji;
    private TextView mTvAnswerResultNums;
    /** 在线测试 */
    private TextView mTvAnswerResultType;
    
    public static void start(Context context, String title, String nums, String chengji) {
        Intent starter = new Intent(context, AnswerResultActivity.class);
        starter.putExtra(INTENT_TITLE, title);
        starter.putExtra(INTENT_NUMS, nums);
        starter.putExtra(INTENT_CHENG_JI, chengji);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_result);
        initView();
        
        title = getIntent().getStringExtra(INTENT_TITLE);
        nums = getIntent().getStringExtra(INTENT_NUMS);
        chengji = getIntent().getStringExtra(INTENT_CHENG_JI);
        
        mTvAnswerResult.setText(chengji);
        mTvAnswerResultTitle.setText(title);
        mTvAnswerResultChengji.setText(chengji + "分");
        mTvAnswerResultNums.setText(nums);
        mTvAnswerResultType.setText(TYPE);
    }
    
    private void initView() {
        TitleUtil.attach(this)
            .setBack(true);
        mTvAnswerResult = (TextView) findViewById(R.id.tv_answer_result);
        mTvAnswerResultTitle = (TextView) findViewById(R.id.tv_answer_result_title);
        mTvAnswerResultChengji = (TextView) findViewById(R.id.tv_answer_result_chengji);
        mTvAnswerResultNums = (TextView) findViewById(R.id.tv_answer_result_nums);
        mTvAnswerResultType = (TextView) findViewById(R.id.tv_answer_result_type);
    }
}
