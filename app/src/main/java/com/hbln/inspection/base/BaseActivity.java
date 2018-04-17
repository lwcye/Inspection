package com.hbln.inspection.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.cmcc.lib_utils.utils.LogUtils;
import com.hbln.inspection.mvp.BaseView;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.umeng.analytics.MobclickAgent;

import javax.annotation.Nonnull;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Activity基类
 *
 * @author mos
 * @date 2017.01.23
 * @note 1. 项目中所有子类必须继承自此基类
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BaseActivity extends AppCompatActivity implements BaseView, LifecycleProvider<ActivityEvent> {
    /** RxJava生命周期管理 */
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    ProgressDialog dialog;

    @Override
    public String TAG() {

        return getClass().getSimpleName();
    }

    @Nonnull
    @Override
    public rx.Observable<ActivityEvent> lifecycle() {

        return mLifecycleSubject.asObservable();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull ActivityEvent event) {

        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {

        return RxLifecycleAndroid.bindActivity(mLifecycleSubject);
    }

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleSubject.onNext(ActivityEvent.CREATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
        LogUtils.e(getIntent());
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        mLifecycleSubject.onNext(ActivityEvent.START);
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        mLifecycleSubject.onNext(ActivityEvent.RESUME);
        MobclickAgent.onResume(this);
    }

    @CallSuper
    @Override
    protected void onPause() {
        mLifecycleSubject.onNext(ActivityEvent.PAUSE);
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @CallSuper
    @Override
    protected void onStop() {
        mLifecycleSubject.onNext(ActivityEvent.STOP);
        super.onStop();
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        mLifecycleSubject.onNext(ActivityEvent.DESTROY);
        getWindow().getDecorView().removeCallbacks(null);
        super.onDestroy();
    }

    /**
     * 设置返回键
     */
    public void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 标题返回键
     */
    public void onHomeBackClick() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onHomeBackClick();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onHomeBackClick();
    }

    /**
     * 使用调度器
     *
     * @param event 生命周期
     * @return 调度转换器
     * @note 1. 若event传入null，则不绑定到生命周期，但依然会subscribe -> io，
     * observe -> ui。
     */
    public <T> rx.Observable.Transformer<T, T> applySchedulers(final ActivityEvent event) {

        return new rx.Observable.Transformer<T, T>() {
            @Override
            public rx.Observable<T> call(rx.Observable<T> observable) {
                // 若不绑定到View的生命周期，则直接子线程中处理 -> UI线程中回调
                if (event != null) {
                    return observable.compose(BaseActivity.this.<T>bindUntilEvent(event))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }

                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 获取Activity
     *
     * @return BaseActivity
     */
    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    public void startActivityEx(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void startServiceEx(Intent intent) {
        startService(intent);
    }

    @Override
    public void showLoading(String message) {
        if (null == dialog) {
            dialog = new ProgressDialog(getContext());
        }
        dialog.setMessage(message);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog && dialog.isShowing()) {
            dialog.cancel();
        }
    }

    /**
     * 获取Context
     *
     * @return Context
     */
    @Override
    public Context getContext() {
        return this;
    }

    /**
     * 通过兼容取Color
     *
     * @param resId ColorRes
     * @return ColorInt
     */
    @ColorInt
    public int getCompatColor(@ColorRes int resId) {
        return ContextCompat.getColor(this, resId);
    }

    /**
     * 通过兼容器取Drawable
     *
     * @param resId DrawableRes
     * @return Drawable
     */
    public Drawable getCompatDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(this, resId);
    }

    /**
     * 通过根布局在主线程的Handle运行runnable
     *
     * @param runnable task
     */
    public void post(Runnable runnable) {
        getWindow().getDecorView().post(runnable);
    }

    /**
     * 通过根布局在主线程的Handle运行runnable,delay为延迟
     *
     * @param runnable task
     * @param delay 延迟,单位为ms
     */
    public void postDelayed(Runnable runnable, int delay) {
        getWindow().getDecorView().postDelayed(runnable, delay);
    }

    /**
     * 启动Activity(扩展)
     *
     * @param activityClass Activity的class
     */
    public void startActivityEx(Class<?> activityClass) {
        startActivityEx(activityClass, null);
    }

    /**
     * 启动Activity(扩展)
     *
     * @param activityClass Activity的class
     * @param data 数据
     */
    public void startActivityEx(Class<?> activityClass, Bundle data) {
        Intent intent = new Intent(this, activityClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    /**
     * 启动Activity并清空在其之上的Activity(扩展)
     *
     * @param activityClass Activity的class
     */
    public void startActivityClearTopEx(Class<?> activityClass) {
        startActivityClearTopEx(activityClass, null);
    }

    public void startActivityClearTaskEx(Class<?> activityClass) {
        startActivityClearTaskEx(activityClass, null);
    }

    /**
     * 启动Activity并清空在其之上的Activity(扩展)
     *
     * @param activityClass Activity的class
     * @param data 数据
     */
    public void startActivityClearTopEx(Class<?> activityClass, Bundle data) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    public void startActivityClearTaskEx(Class<?> activityClass, Bundle data) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivity(intent);
    }

    /**
     * 启动Activity(扩展)
     *
     * @param activityClass Activity的class
     * @param requestCode 请求码
     */
    public void startActivityForResultEx(Class<?> activityClass, int requestCode) {
        startActivityForResultEx(activityClass, requestCode, null);
    }

    /**
     * 启动Activity(扩展)
     *
     * @param activityClass Activity的class
     * @param requestCode 请求码
     * @param data 数据
     */
    public void startActivityForResultEx(Class<?> activityClass, int requestCode, Bundle data) {
        Intent intent = new Intent(this, activityClass);
        if (data != null) {
            intent.putExtras(data);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean ret = super.dispatchTouchEvent(ev);
        /*
         * 若点击Activity的任何区域(除了输入框之外，应隐藏键盘)
         */
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            View v = getCurrentFocus();

            if (v != null && shouldHideInput(v, ev)) {
                hideInput(v);
            }
        }

        return ret;
    }

    /**
     * 是否应该隐藏输入
     *
     * @param v 焦点控件
     * @param event 动作事件
     * @return true -- 是  false -- 否
     */
    private boolean shouldHideInput(View v, MotionEvent event) {
        boolean should = true;

        // 仅点击到输入框时，键盘不隐藏
        if (v != null && v instanceof EditText) {
            int[] loc = new int[2];
            v.getLocationOnScreen(loc);

            // 焦点控件位置
            int left = loc[0];
            int top = loc[1];
            int right = left + v.getWidth();
            int bottom = top + v.getHeight();

            int touchX = (int) event.getRawX();
            int touchY = (int) event.getRawY();

            // 是否点击到输入框
            if ((touchX >= left && touchX <= right) &&
                    (touchY >= top && touchY <= bottom)) {

                should = false;
            }
        }

        return should;
    }

    /**
     * 显示Fragment
     *
     * @param containerId 容器id
     * @param fragment fragment
     */
    protected void showFragment(int containerId, Fragment fragment) {
        if (fragment == null) {

            return;
        }

        String tag = fragment.getClass().getSimpleName();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment cache = fm.findFragmentByTag(fragment.getTag());
        if (cache != null) {
            ft.show(cache);
        } else {
            ft.add(containerId, fragment, tag);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 隐藏fragment
     *
     * @param fragment fragment
     */
    protected void hideFragment(Fragment fragment) {
        if (fragment == null) {

            return;
        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment cache = fm.findFragmentByTag(fragment.getTag());
        if (cache != null) {
            ft.hide(cache);
        }
        ft.commitAllowingStateLoss();
    }

    /**
     * 隐藏键盘
     *
     * @param v 控件
     */
    private void hideInput(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
