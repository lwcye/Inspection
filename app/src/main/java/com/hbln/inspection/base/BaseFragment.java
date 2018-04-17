package com.hbln.inspection.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hbln.inspection.mvp.BaseView;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import javax.annotation.Nonnull;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * fragment基类
 *
 * @author mos
 * @date 2017.01.23
 * @note 1. 项目中所有子类必须继承自此基类
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public abstract class BaseFragment extends Fragment implements BaseView, LifecycleProvider<FragmentEvent> {
    /** 生命周期管理 */
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    /** 生命周期管理 */
    public View mView;

    @Override
    public String TAG() {

        return getClass().getSimpleName();
    }

    @Nonnull
    @Override
    public Observable<FragmentEvent> lifecycle() {

        return mLifecycleSubject.asObservable();
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindUntilEvent(@Nonnull FragmentEvent event) {

        return RxLifecycle.bindUntilEvent(mLifecycleSubject, event);
    }

    @Nonnull
    @Override
    public <T> LifecycleTransformer<T> bindToLifecycle() {

        return RxLifecycleAndroid.bindFragment(mLifecycleSubject);
    }

    @CallSuper
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @CallSuper
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @CallSuper
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
        mView = View.inflate(getActivity(), getLayoutId(), null);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initData();
    }

    /**
     * 初始化数据
     */
    public abstract void initView(View view);

    /**
     * 初始化数据
     */
    public abstract void initData();


    /**
     * 初始化数据
     *
     * @return 布局id
     */
    public abstract int getLayoutId();

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        mLifecycleSubject.onNext(FragmentEvent.START);
    }

    @CallSuper
    @Override
    public void onResume() {
        super.onResume();
        mLifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @CallSuper
    @Override
    public void onPause() {
        mLifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @CallSuper
    @Override
    public void onStop() {
        mLifecycleSubject.onNext(FragmentEvent.STOP);
        super.onStop();
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @CallSuper
    @Override
    public void onDestroy() {
        mLifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
    }

    @CallSuper
    @Override
    public void onDetach() {
        mLifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return ((BaseActivity) getActivity());
    }

    /**
     * 使用调度器
     *
     * @param event 生命周期
     * @return 调度转换器
     * @note 1. 若event传入null，则不绑定到生命周期，但依然会subscribe -> io，
     * observe -> ui。
     */
    public <T> Observable.Transformer<T, T> applySchedulers(final FragmentEvent event) {

        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                // 若不绑定到View的生命周期，则直接子线程中处理 -> UI线程中回调
                if (event != null) {
                    return observable.compose(BaseFragment.this.<T>bindUntilEvent(event))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread());
                }

                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    @Override
    public void startActivityEx(Intent intent) {
        getActivity().startActivity(intent);
    }

    @Override
    public void startServiceEx(Intent intent) {
        getActivity().startService(intent);
    }

    @Override
    public void showLoading(String message) {
        getBaseActivity().showLoading(message);
    }

    @Override
    public void hideLoading() {
        if (getBaseActivity() != null) {
            getBaseActivity().hideLoading();
        }
    }
}
