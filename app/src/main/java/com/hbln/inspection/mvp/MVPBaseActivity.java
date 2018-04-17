package com.hbln.inspection.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hbln.inspection.base.MyActivity;


/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/10/17 10:04
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends MyActivity implements BaseView {
    public T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 创建Presenter
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView((V) this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 创建Presenter对象
     *
     * @return Presenter对象
     * @note 若需要使用MVP模式，则子类需实现此方法
     */
    protected abstract T createPresenter();
}
