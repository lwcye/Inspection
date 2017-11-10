package com.cmcc.inspection.feature.workarena;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.cmcc.inspection.R;
import com.cmcc.inspection.feature.workarena.workarenaresult.WorkArenaResultActivity;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkArenaActivity extends MVPBaseActivity<WorkArenaContract.View, WorkArenaPresenter> implements WorkArenaContract.View {
    public static void start(Context context) {
        Intent starter = new Intent(context, WorkArenaActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected WorkArenaPresenter createPresenter() {
        return new WorkArenaPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_arena);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Observable.timer(3, TimeUnit.SECONDS)
                .compose(getBaseActivity().<Long>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        WorkArenaResultActivity.start(getBaseActivity());
                        finish();
                    }
                });
    }
}
