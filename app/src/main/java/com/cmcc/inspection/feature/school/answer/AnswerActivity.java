package com.cmcc.inspection.feature.school.answer;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.cmcc.inspection.R;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.inspection.utils.TitleUtil;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AnswerActivity extends MVPBaseActivity<AnswerContract.View, AnswerPresenter> implements AnswerContract.View {
    
    @Override
    protected AnswerPresenter createPresenter() {
        return new AnswerPresenter();
    }
    
    public static void start(Context context) {
        Intent starter = new Intent(context, AnswerActivity.class);
        context.startActivity(starter);
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        TitleUtil.attach(this).setLeftDrawable(R.drawable.icon_back, 0, 0, 0)
            .setLeftClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            })
            .setColor(Color.WHITE, 255);
    }
}
