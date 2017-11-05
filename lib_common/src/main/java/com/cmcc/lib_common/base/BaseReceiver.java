package com.cmcc.lib_common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class BaseReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        onReceive(context, intent, 0);
    }

    /**
     * onReceive回调
     *
     * @param context 参考回调文档说明
     * @param intent 参考回调文档说明
     * @param flag 标志(暂未使用)
     */
    public abstract void onReceive(Context context, Intent intent, int flag);
}
