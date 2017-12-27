package com.hbln.inspection.feature.workarena;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.workarena.workarenaresult.WorkArenaResultActivity;
import com.hbln.inspection.mvp.MVPBaseActivity;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WorkArenaActivity extends MVPBaseActivity<WorkArenaContract.View, WorkArenaPresenter> implements WorkArenaContract.View {
    private ImageView mIvWorkArena;
    
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
        initView();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    private void initView() {
        mIvWorkArena = (ImageView) findViewById(R.id.iv_work_arena);
        Glide.with(getContext())
            .asGif()
            .load("file:///android_asset/work_yewu.gif")
            .listener(new RequestListener<GifDrawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<GifDrawable> target, boolean b) {
                    return false;
                }
                
                @Override
                public boolean onResourceReady(GifDrawable gifDrawable, Object o, Target<GifDrawable> target, DataSource dataSource, boolean b) {
                    gifDrawable.setLoopCount(5);
                    mIvWorkArena.setImageDrawable(gifDrawable);
                    gifDrawable.start();
                    gifDrawable.setLoopCount(1);
                    
                    Observable.timer(gifDrawable.getFrameCount() * 126, TimeUnit.MILLISECONDS)
                        .compose(getBaseActivity().<Long>applySchedulers(ActivityEvent.DESTROY))
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                WorkArenaResultActivity.start(getBaseActivity());
                                finish();
                            }
                        });
                    return false;
                }
            }).submit();
    }
}
