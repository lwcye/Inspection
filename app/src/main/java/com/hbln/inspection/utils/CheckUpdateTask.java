package com.hbln.inspection.utils;


import com.cmcc.lib_utils.utils.LogUtils;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

/**
 * 检测更新任务
 *
 * @author mos
 * @date 2017.03.13
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class CheckUpdateTask {
    /** 实例 */
    private static CheckUpdateTask sCheckUpdateTask = new CheckUpdateTask();
    /** 是否正在检查更新 */
    private boolean mIsCheckUpdateRunning = false;

    /**
     * 私有化构造函数
     */
    private CheckUpdateTask() {
    }

    /**
     * 获取实例
     *
     * @return 实例对象
     */
    public static CheckUpdateTask getInstance() {
        return sCheckUpdateTask;
    }


    /**
     * 下载Apk
     *
     * @param url 下载地址
     * @param listener 下载状态监听
     */
    public void downloadApk(String url, final IDownloadStatusListener listener) {
        LogUtils.e("download from: " + url);
        FileDownloader.getImpl()
                .create(url)
                .setPath(FileDownloadUtils.getDefaultSaveRootPath() + "/" + "inspection.apk")
                .setForceReDownload(true)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        if (listener != null) {
                            int percent = soFarBytes * 100 / totalBytes;
                            listener.onProgress(percent);
                        }
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        if (listener != null) {
                            listener.onFinish(false, task.getPath());
                        }
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtils.e(e.toString());
                        if (listener != null) {
                            listener.onFinish(true, "");
                        }
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                    }
                }).start();
    }

    /**
     * 下载状态监听
     */
    public interface IDownloadStatusListener {
        /**
         * 进度监听
         *
         * @param percent 进度百分比
         */
        void onProgress(int percent);

        /**
         * 结束监听
         *
         * @param error 是否错误
         * @param path 文件路径
         */
        void onFinish(boolean error, String path);
    }
}
