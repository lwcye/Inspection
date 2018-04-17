package com.hbln.inspection.network.model;

import com.hbln.inspection.base.BaseActivity;
import com.hbln.inspection.network.http.HttpComplete;
import com.hbln.inspection.network.http.HttpError;
import com.hbln.inspection.network.http.HttpRequest;
import com.hbln.inspection.network.http.HttpResult;
import com.hbln.inspection.network.http.NetWorkInterceptor;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.functions.Action1;


/**
 * <p>点赞</p><br>
 *
 * @author lwc
 * @date 2018/1/26 21:35
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class ZanModel extends ResultModel {
    /**
     * info : {"yeszan":1,"nozan":0}
     */

    public InfoBean info;

    /**
     * 处理赞
     */
    public static void handleZan(final BaseActivity baseActivity, final boolean zan, final int type, final String id, final Action1<ObjectModel> action1) {
        baseActivity.showLoading("");
        LoginModel.getUserInfo(new Action1<UserInfoModel.UserInfo>() {
            @Override
            public void call(UserInfoModel.UserInfo userInfo) {
                if (userInfo == null) {
                    return;
                }

                HttpRequest.getUserService().dianzhanadd(userInfo.sfid, zan ? "1" : "0", CommentModel.getCatId(type), id, zan ? "0" : "1")
                        .compose(NetWorkInterceptor.<ObjectModel>retrySessionCreator())
                        .compose(baseActivity.<ObjectModel>applySchedulers(ActivityEvent.DESTROY))
                        .subscribe(new HttpResult<ObjectModel>() {
                            @Override
                            public void result(ObjectModel objectModel) {
                                if (action1 != null) {
                                    action1.call(objectModel);
                                }
                            }
                        }, new HttpError(baseActivity), new HttpComplete(baseActivity));
            }
        });
    }

    /**
     * 获得点赞列表
     *
     * @param id 点赞
     * @param showProgress 是否显示加载中
     */
    public static void getZanList(final BaseActivity baseActivity, final int type, String id, boolean showProgress, final Action1<ZanModel> action1) {
        if (showProgress) {
            baseActivity.showLoading("");
        }
        HttpRequest.getUserService().pinglunzanshuliang(CommentModel.getCatId(type), id)
                .compose(NetWorkInterceptor.<ZanModel>retrySessionCreator())
                .compose(baseActivity.<ZanModel>applySchedulers(ActivityEvent.DESTROY))
                .subscribe(new HttpResult<ZanModel>() {
                    @Override
                    public void result(ZanModel zanModel) {
                        if (action1 != null) {
                            action1.call(zanModel);
                        }
                    }
                }, new HttpError(baseActivity) {
                    @Override
                    public void error(int errorCode, String message) {
                    }
                }, new HttpComplete(baseActivity));
    }

    public static class InfoBean {
        /**
         * yeszan : 1
         * nozan : 0
         */

        public int yeszan;
        public int nozan;
    }
}
