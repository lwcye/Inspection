package com.cmcc.lib_common.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * <p> Handler相关工具类 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 15:56
 * @note -
 * HandlerHolder: 使用必读
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class HandlerUtils {
    /**
     * 构造类
     */
    private HandlerUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 收到消息回调接口
     */
    public interface OnReceiveMessageListener {
        void handlerMessage(Message msg);
    }

    /**
     * 静态内部类，防止内存泄漏
     */
    public static class HandlerHolder extends Handler {
        /** 弱应用实现回调监听 */
        WeakReference<OnReceiveMessageListener> mListenerWeakReference;

        /**
         * 使用必读：推荐在Activity或者Activity内部持有类中实现该接口，不要使用匿名类，可能会被GC
         *
         * @param listener 收到消息回调接口
         */
        public HandlerHolder(OnReceiveMessageListener listener) {
            mListenerWeakReference = new WeakReference<>(listener);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mListenerWeakReference != null && mListenerWeakReference.get() != null) {
                mListenerWeakReference.get().handlerMessage(msg);
            }
        }
    }
}
