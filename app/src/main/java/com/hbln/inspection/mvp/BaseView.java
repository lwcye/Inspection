package com.hbln.inspection.mvp;

import android.content.Context;
import android.content.Intent;

import com.hbln.inspection.base.BaseActivity;


public interface BaseView {
    Context getContext();

    BaseActivity getBaseActivity();

    void startActivityEx(Intent intent);

    void startServiceEx(Intent intent);

    void showLoading(String message);

    void hideLoading();

    /**
     * 获取标志
     *
     * @return 标志字符串
     * @note 标志可用于打印调试信息
     */
    public String TAG();
    
}
