package com.hbln.inspection.feature.main;

import android.app.Dialog;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_network.model.VersionModel;
import com.cmcc.lib_utils.utils.AppUtils;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.mvp.BasePresenterImpl;
import com.hbln.inspection.utils.CheckUpdateTask;
import com.hbln.inspection.widget.DialogUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {

    private Dialog dialog;

    @Override
    public void getVersion() {
        getView().showLoading("");
        HttpRequest.getUserService().getversion(AppUtils.getAppVersionName())
                .compose(NetWorkInterceptor.<VersionModel>retrySessionCreator())
                .compose(getView().getBaseActivity().<VersionModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<VersionModel>() {
                    @Override
                    public void result(VersionModel versionModel) {
                        getView().setVersion(versionModel);
                    }
                }, new HttpError(getView()) {
                    @Override
                    public void call(Throwable throwable) {
                    }
                }, new HttpComplete(getView()));
    }

    @Override
    public void upDataVersion(final String url) {
        dialog = DialogUtils.getInstance().showProgress(getView().getBaseActivity());
        dialog.show();
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.pb_dialog_progress);
        final TextView textView = (TextView) dialog.findViewById(R.id.tv_dialog_progress);
        CheckUpdateTask.getInstance().downloadApk(url, new CheckUpdateTask.IDownloadStatusListener() {
            @Override
            public void onProgress(int percent) {
                progressBar.setProgress(percent);
                textView.setText(new StringBuilder().append(percent).append("%"));
            }

            @Override
            public void onFinish(boolean error, String path) {
                if (error) {
                    ToastUtils.showShortToastSafe(String.format("下载错误，请到 %s 重新下载", url));
                    return;
                }
                dialog.cancel();
                AppUtils.installApp(getView().getBaseActivity(), path);
            }
        });
    }
}
