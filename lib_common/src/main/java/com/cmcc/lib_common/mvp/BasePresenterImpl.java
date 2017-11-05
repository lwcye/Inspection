package com.cmcc.lib_common.mvp;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * <p>基类</p><br>
 *
 * @author - lwc
 * @date - 2017/10/21 20:02
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class BasePresenterImpl<I extends BaseView> implements BasePresenter<I> {
    /** 持有View的引用 */
    protected Reference<I> mViewRef;

    /**
     * 关联视图
     *
     * @param view 视图的引用
     */
    @Override
    public void attachView(I view) {
        mViewRef = new WeakReference<I>(view);
    }

    /**
     * 获得视图
     *
     * @return 视图的引用
     */
    @Override
    public I getView() {

        return mViewRef != null ? mViewRef.get() : null;
    }

    public BasePresenter getPresenter() {
        return this;
    }

    /**
     * View是否已关联到此Presenter
     *
     * @return true -- 是  false -- 否
     */
    public boolean isViewAttached() {

        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 取消关联视图
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
