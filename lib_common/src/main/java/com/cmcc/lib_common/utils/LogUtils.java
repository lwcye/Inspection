package com.cmcc.lib_common.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * <p> 日志相关工具类 </p><br>
 *
 * @author lwc
 * @date 2017/3/10 15:57
 * @note -
 * init      : 初始化函数
 * getBuilder: 获取LogUtils建造者
 * v         : Verbose日志
 * d         : Debug日志
 * i         : Info日志
 * w         : Warn日志
 * e         : Error日志
 * -------------------------------------------------------------------------------------------------
 * @modified mos
 * @date 2017.03.23
 * @note 修正没有给予android.permission.WRITE_EXTERNAL_STORAGE时，初始化函数崩溃的bug
 * -------------------------------------------------------------------------------------------------
 * @modified - lwc
 * @date - 2017.04.24
 * @note - 增加gson，增加打印多行的方法，增加(msgTag,msg)方法，可以打印对象和intent，通过gson转换成json打印出来
 */
public class LogUtils {
    private static boolean logSwitch = true;
    private static boolean log2FileSwitch = false;
    private static char logFilter = 'v';
    private static String tag = "TAG";
    private static String dir = null;
    private static int stackIndex = 0;
    private static Gson mGson;
    
    /**
     * 构造类
     */
    private LogUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    
    /**
     * 初始化函数
     * <p>与{@link #getBuilder()}两者选其一</p>
     *
     * @param logSwitch 日志总开关
     * @param log2FileSwitch 日志写入文件开关，设为true需添加权限 {@code <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>}
     * @param logFilter 输入日志类型有{@code v, d, i, w, e}<br> v 代表输出所有信息，w 则只输出警告，e 只输出 error，i 只输出 info，d 只输出 debug...
     * @param tag 标签
     */
    public static void init(boolean logSwitch, boolean log2FileSwitch, char logFilter, String tag) {
        if (log2FileSwitch && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator;
        } else {
            dir = Utils.getContext().getCacheDir().getPath() + File.separator;
        }
        LogUtils.logSwitch = logSwitch;
        LogUtils.log2FileSwitch = log2FileSwitch;
        LogUtils.logFilter = logFilter;
        LogUtils.tag = tag;
        LogUtils.mGson = new Gson();
    }
    
    /**
     * 获取LogUtils建造者
     * <p>与{@link #init(boolean, boolean, char, String)}两者选其一</p>
     *
     * @return Builder对象
     */
    public static Builder getBuilder() {
        if (log2FileSwitch && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            dir = Utils.getContext().getExternalCacheDir().getPath() + File.separator + "log" + File.separator;
        } else {
            dir = Utils.getContext().getCacheDir().getPath() + File.separator + "log" + File.separator;
        }
        return new Builder();
    }
    
    /**
     * Verbose日志
     *
     * @param msg 消息
     */
    public static void v(Object msg) {
        log("", msg, null, 'v');
    }
    
    /**
     * Verbose日志
     */
    public static void v() {
        log("", null, null, 'v');
    }
    
    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void v(String tag, Object msg) {
        log(tag, msg, null, 'v');
    }
    
    /**
     * Verbose日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     */
    public static void v(String tag, Object msg, Throwable tr) {
        log(tag, msg, tr, 'v');
    }
    
    /**
     * Verbose日志
     *
     * @param tr Throwable
     */
    public static void v(Throwable tr) {
        log("", "Throwable", tr, 'v');
    }
    
    /**
     * Debug日志
     */
    public static void d() {
        log("", null, null, 'd');
    }
    
    /**
     * Debug日志
     *
     * @param msg 消息
     */
    public static void d(Object msg) {
        log("", msg, null, 'd');
    }
    
    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void d(String tag, Object msg) {
        log(tag, msg, null, 'd');
    }
    
    /**
     * Debug日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     */
    public static void d(String tag, Object msg, Throwable tr) {
        log(tag, msg, tr, 'd');
    }
    
    /**
     * Debug日志
     *
     * @param tr Throwable
     */
    public static void d(Throwable tr) {
        log("", "Throwable", tr, 'd');
    }
    
    /**
     * Info日志
     */
    public static void i() {
        log("", null, null, 'i');
    }
    
    /**
     * Info日志
     *
     * @param msg 消息
     */
    public static void i(Object msg) {
        log("", msg, null, 'i');
    }
    
    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void i(String tag, Object msg) {
        log(tag, msg, null, 'i');
    }
    
    /**
     * Info日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     */
    public static void i(String tag, Object msg, Throwable tr) {
        log(tag, msg, tr, 'i');
    }
    
    /**
     * Info日志
     *
     * @param tr Throwable
     */
    public static void i(Throwable tr) {
        log("", "Throwable", tr, 'i');
    }
    
    /**
     * Warn日志
     */
    public static void w() {
        log("", null, null, 'w');
    }
    
    /**
     * Warn日志
     *
     * @param msg 消息
     */
    public static void w(Object msg) {
        log("", msg, null, 'w');
    }
    
    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     */
    public static void w(String tag, Object msg) {
        log(tag, msg, null, 'w');
    }
    
    /**
     * Warn日志
     *
     * @param tr Throwable
     */
    public static void w(Throwable tr) {
        log("", "Throwable", tr, 'w');
    }
    
    /**
     * Warn日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     */
    public static void w(String tag, Object msg, Throwable tr) {
        log(tag, msg, tr, 'w');
    }
    
    /**
     * Error日志
     */
    public static void e() {
        log("", null, null, 'e');
    }
    
    /**
     * Error日志
     *
     * @param msg 消息
     */
    public static void e(Object msg) {
        log("", msg, null, 'e');
    }
    
    /**
     * 打印Throwable
     *
     * @param tr Throwable
     */
    public static void e(Throwable tr) {
        log("", "Throwable", tr, 'e');
    }
    
    /**
     * 打印String集合，按arg1=arg2,arg3=arg4,的格式
     *
     * @param args String集合
     */
    public static void e(Object... args) {
        StringBuilder sb = new StringBuilder();
        int size = args.length;
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                sb.append(args[i]).append("=");
            } else {
                sb.append(args[i]).append(",");
            }
        }
        log("", sb.toString(), null, 'e');
    }
    
    /**
     * Error日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     */
    public static void e(String tag, Object msg, Throwable tr) {
        log(tag, msg.toString(), tr, 'e');
    }
    
    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     * @param type 日志类型
     */
    private static void log(String tag, Object msg, Throwable tr, char type) {
        if (msg == null) {
            msg = "loading...";
        }
        if (logSwitch) {
            if ('e' == type && ('e' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), generateMethod(msg), tr, 'e');
            } else if ('w' == type && ('w' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), generateMethod(msg), tr, 'w');
            } else if ('d' == type && ('d' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), generateMethod(msg), tr, 'd');
            } else if ('i' == type && ('i' == logFilter || 'v' == logFilter)) {
                printLog(generateTag(tag), generateMethod(msg), tr, 'i');
            }
            if (log2FileSwitch) {
                log2File(type, generateTag(tag), generateMethod(msg) + '\n' + Log.getStackTraceString(tr));
            }
        }
    }
    
    /**
     * 根据tag, msg和等级，输出日志
     *
     * @param tag 标签
     * @param msg 消息
     * @param tr 异常
     * @param type 日志类型
     */
    private static void printLog(final String tag, final String msg, Throwable tr, char type) {
        final int maxLen = 4000;
        for (int i = 0, len = msg.length(); i * maxLen < len; ++i) {
            String subMsg = msg.substring(i * maxLen, (i + 1) * maxLen < len ? (i + 1) * maxLen : len);
            switch (type) {
                case 'e':
                    Log.e(tag, subMsg, tr);
                    break;
                case 'w':
                    Log.w(tag, subMsg, tr);
                    break;
                case 'd':
                    Log.d(tag, subMsg, tr);
                    break;
                case 'i':
                    Log.i(tag, subMsg, tr);
                    break;
            }
        }
    }
    
    /**
     * 打开日志文件并写入日志
     *
     * @param type 日志类型
     * @param tag 标签
     * @param msg 信息
     **/
    private synchronized static void log2File(final char type, final String tag, final String msg) {
        Date now = new Date();
        String date = new SimpleDateFormat("MM-dd", Locale.getDefault()).format(now);
        final String fullPath = dir + date + ".txt";
        if (!FileUtils.createOrExistsFile(fullPath)) {
            return;
        }
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(now);
        final String dateLogContent = time + ":" + type + ":" + tag + ":" + msg + '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(fullPath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtils.closeIO(bw);
                }
            }
        }).start();
    }
    
    /**
     * 产生tag
     *
     * @return tag
     */
    public static String generateTag(String tag) {
        // 2017.04.24 在 tag 中去掉方法名，单纯打tag
//        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
//        if (stackIndex == 0) {
//            while (!stacks[stackIndex].getMethodName().equals("generateTag")) {
//                ++stackIndex;
//            }
//            stackIndex += 3;
//        }
//        StackTraceElement caller = stacks[stackIndex];
//        String callerClazzName = caller.getClassName();
//        String format = "Tag[" + tag + "] %s";
//        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
//        return String.format(format, callerClazzName);
        return new StringBuilder().append(LogUtils.tag).append("[").append(tag).append("] ").toString();
    }
    
    /**
     * 将文件和线程的数据放到log里面去
     *
     * @param msg message
     * @return 包装的message
     */
    public static String generateMethod(Object msg) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        StackTraceElement caller = stacks[5];
        String className = caller.getFileName();
        String methodName = caller.getMethodName();
        int lineNumber = caller.getLineNumber();
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);
        String threadName = Thread.currentThread().getName();
        String format = "[(%s:%d)#%s?Thread:%s] %s";
        String strMsg;
        if (msg instanceof Intent) {
            HashMap<String, Object> map = new HashMap<>();
            Intent intent = (Intent) msg;
            // Extra_String
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                for (String key : bundle.keySet()) {
                    map.put(key, bundle.get(key));
                }
            }
            
            // Class
            ComponentName component = intent.getComponent();
            if (null != component && component.getClassName() != null) {
                map.put("className", component.getClassName());
            }
            strMsg = mGson.toJson(map);
        } else if (msg instanceof String) {
            strMsg = msg.toString();
        } else if (msg instanceof Character) {
            strMsg = msg.toString();
        } else if (msg instanceof Boolean) {
            strMsg = msg.toString();
        } else if (msg instanceof Integer) {
            strMsg = msg.toString();
        } else if (msg instanceof Short) {
            strMsg = msg.toString();
        } else if (msg instanceof Long) {
            strMsg = msg.toString();
        } else if (msg instanceof Float) {
            strMsg = msg.toString();
        } else if (msg instanceof Double) {
            strMsg = msg.toString();
        } else if (msg instanceof Byte) {
            strMsg = msg.toString();
        } else {
            strMsg = mGson.toJson(msg);
        }
        return String.format(format, className, lineNumber, methodName, threadName, strMsg);
    }
    
    /**
     * 处理 Throwable 生成常见的字符串
     *
     * @param tr Throwable
     * @return Throwable的字符串
     */
    public static String handleThrowableMessage(Throwable tr) {
        StringBuilder builder = new StringBuilder();
        builder.append("message:").append(tr.toString()).append("\n").append("err:");
        if (tr.getMessage() != null) {
            tr.printStackTrace();
            StackTraceElement[] stack = tr.getStackTrace();
            if (stack != null) {
                for (StackTraceElement aStack : stack) {
                    builder.append("\tat ");
                    builder.append(aStack.toString());
                    builder.append("\n");
                }
            }
            builder.append("Case:").append(tr.getCause().toString()).append("\n").append("err:");
            if (tr.getCause() != null) {
                StackTraceElement[] stackCase = tr.getCause().getStackTrace();
                if (stackCase != null) {
                    for (StackTraceElement aStackCase : stackCase) {
                        builder.append("\tat ");
                        builder.append(aStackCase.toString());
                        builder.append("\n");
                    }
                }
            }
        }
        return generateMethod(builder.toString());
    }
    
    /**
     * 建造者模式
     */
    public static class Builder {
        
        private boolean logSwitch = true;
        private boolean log2FileSwitch = false;
        private char logFilter = 'v';
        private String tag = "TAG";
        
        public Builder setLogSwitch(boolean logSwitch) {
            this.logSwitch = logSwitch;
            return this;
        }
        
        public Builder setLog2FileSwitch(boolean log2FileSwitch) {
            this.log2FileSwitch = log2FileSwitch;
            return this;
        }
        
        public Builder setLogFilter(char logFilter) {
            this.logFilter = logFilter;
            return this;
        }
        
        public Builder setTag(String tag) {
            this.tag = tag;
            return this;
        }
        
        public void create() {
            LogUtils.logSwitch = logSwitch;
            LogUtils.log2FileSwitch = log2FileSwitch;
            LogUtils.logFilter = logFilter;
            LogUtils.tag = tag;
        }
    }
}