package com.cmcc.inspection.feature.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cmcc.inspection.R;
import com.cmcc.inspection.constans.ENVs;
import com.cmcc.inspection.feature.main.find.FindFragment;
import com.cmcc.inspection.feature.main.mainhome.MainHomeFragment;
import com.cmcc.inspection.feature.main.mainuser.MainUserFragment;
import com.cmcc.inspection.feature.main.message.MessageFragment;
import com.cmcc.inspection.mvp.MVPBaseActivity;
import com.cmcc.lib_utils.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View, RadioGroup.OnCheckedChangeListener {
    /** 第一个Tab */
    private RadioButton mRbMain0;
    /** 第二个Tab */
    private RadioButton mRbMain1;
    /** 第三个Tab */
    private RadioButton mRbMain2;
    /** 第四个Tab */
    private RadioButton mRbMain3;
    /** Tab组 */
    private RadioGroup mRgMain;

    /** Home页 */
    private MainHomeFragment mMainHomeFragment = new MainHomeFragment();
    private FindFragment mMainFindFragment = new FindFragment();
    private MessageFragment mMainMessageFragment = new MessageFragment();
    private MainUserFragment mMainUserFragment = new MainUserFragment();
    /** 是否需要退出 */
    private boolean mIsExit = false;
    /** 是否被遮挡 - */
    private boolean mIsKeepOut = false;

    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 处理插件退出后.返回选择首页按钮
        if (mIsKeepOut) {
            onCheckedChanged(mRgMain, R.id.rb_main_0);
            mRbMain0.setChecked(true);
            mIsKeepOut = false;
        }
    }

    private void initView() {
        mRbMain0 = (RadioButton) findViewById(R.id.rb_main_0);
        mRbMain1 = (RadioButton) findViewById(R.id.rb_main_1);
        mRbMain2 = (RadioButton) findViewById(R.id.rb_main_2);
        mRbMain3 = (RadioButton) findViewById(R.id.rb_main_3);
        mRgMain = (RadioGroup) findViewById(R.id.rg_main);
        mRgMain.setOnCheckedChangeListener(this);

        // 初始化导航
        onCheckedChanged(mRgMain, R.id.rb_main_0);
    }

    @Override
    public void onBackPressed() {
        if (mIsExit) {
            finish();
        } else {
            mIsExit = true;
            ToastUtils.showShortToastSafe("再按一次退出");
            Observable.timer(ENVs.BACK_TO_EXIT_INTERVAL, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            mIsExit = false;
                        }
                    });
        }
    }

    /**
     * 显示指定位置的fragment
     *
     * @param pos fragment
     */
    public void showFragmentAtPos(int pos) {
        switch (pos) {
            case 0:
                // 首页
                showFragment(R.id.fl_main_fragment_container, mMainHomeFragment);
                hideFragment(mMainFindFragment);
                hideFragment(mMainMessageFragment);
                hideFragment(mMainUserFragment);
                break;

            case 1:
                // 活动
                showFragment(R.id.fl_main_fragment_container, mMainFindFragment);
                hideFragment(mMainHomeFragment);
                hideFragment(mMainMessageFragment);
                hideFragment(mMainUserFragment);
                break;
            case 2:
                // 视频-临时注释-取消一个界面
                showFragment(R.id.fl_main_fragment_container, mMainMessageFragment);
                hideFragment(mMainHomeFragment);
                hideFragment(mMainFindFragment);
                hideFragment(mMainUserFragment);
                break;

            case 3:
                // 我的
                showFragment(R.id.fl_main_fragment_container, mMainUserFragment);
                hideFragment(mMainHomeFragment);
                hideFragment(mMainFindFragment);
                hideFragment(mMainMessageFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_0:
                showFragmentAtPos(0);
                break;
            case R.id.rb_main_1:
                showFragmentAtPos(1);
                break;
            case R.id.rb_main_2:
                showFragmentAtPos(2);
                break;
            case R.id.rb_main_3:
                showFragmentAtPos(3);
                break;
            default:
                showFragmentAtPos(0);
                break;
        }
    }
}
