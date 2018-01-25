package com.cmcc.lib_network.model;

import android.text.TextUtils;

import com.cmcc.lib_common.base.BaseActivity;
import com.cmcc.lib_network.http.HttpComplete;
import com.cmcc.lib_network.http.HttpError;
import com.cmcc.lib_network.http.HttpRequest;
import com.cmcc.lib_network.http.HttpResult;
import com.cmcc.lib_network.http.NetWorkInterceptor;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.List;

import rx.functions.Action1;

/**
 * <p>DESCRIBE</p><br>
 *
 * @author lwc
 * @date 2018/1/25 23:15
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class CommentModel extends ResultModel {
    
    public List<InfoBean> info;
    
    public static class InfoBean {
        /**
         * id : 1
         * sfid : 320311198512046157
         * name : 钱程
         * pic : http://58.218.171.12:1003/ganbujiandu/uploads/image/20171220/199de4a9325420c54591173988bee2dc28b28b.jpg
         * content : 解决了
         * create_time : 1516893244
         * catid : 1
         * sxid : 1
         * times : 2018-01-25 23:14
         */
        
        public String id;
        public String sfid;
        public String name;
        public String pic;
        public String content;
        public String create_time;
        public String catid;
        public String sxid;
        public String times;
    }
    
    /**
     * 处理评价
     *
     * @param comment 评价
     */
    public static void handleComment(final BaseActivity baseActivity, final String comment, final int type, final String id, final Action1<ObjectModel> action1) {
        if (TextUtils.isEmpty(comment)) {
            ToastUtils.showShortToastSafe("请输入评论内容");
            return;
        }
        baseActivity.showLoading("提交评论");
        LoginModel.getUserInfo(new Action1<UserInfoModel.UserInfo>() {
            @Override
            public void call(UserInfoModel.UserInfo userInfo) {
                if (userInfo == null) {
                    return;
                }
                HttpRequest.getUserService().pinglunadd(userInfo.sfid, comment, getCatId(type), id)
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
     * 获得评价列表
     *
     * @param id 评价
     * @param showProgress 是否显示加载中
     */
    public static void getCommentList(final BaseActivity baseActivity, final int type, String id, boolean showProgress, final Action1<CommentModel> action1) {
        if (showProgress) {
            baseActivity.showLoading("");
        }
        HttpRequest.getUserService().pingluninfo(getCatId(type), id)
            .compose(NetWorkInterceptor.<CommentModel>retrySessionCreator())
            .compose(baseActivity.<CommentModel>applySchedulers(ActivityEvent.DESTROY))
            .subscribe(new HttpResult<CommentModel>() {
                @Override
                public void result(CommentModel commentModel) {
                    if (action1 != null) {
                        action1.call(commentModel);
                    }
                }
            }, new HttpError(baseActivity) {
                @Override
                public void error(int errorCode, String message) {
                }
            }, new HttpComplete(baseActivity));
    }
    
    /**
     * 删除评价
     *
     * @param id 评价
     */
    public static void removeComment(final BaseActivity baseActivity, String id, final Action1<ObjectModel> action1) {
        baseActivity.showLoading("");
        HttpRequest.getUserService().pinglunshanchu(id)
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
    
    /**
     * 根据 type 获得 catId
     *
     * @param type
     * @return catId
     */
    private static String getCatId(int type) {
        String catid = "";
        switch (type) {
            case WebViewModel.TYPE_SCHOOL:
                catid = "1";
                break;
            case WebViewModel.TYPE_BRAND_WAIXUAN:
                catid = "2";
                break;
            case WebViewModel.TYPE_WORK:
                catid = "7";
                break;
            case WebViewModel.TYPE_FORTRESS_HOME:
                catid = "3";
                break;
            case WebViewModel.TYPE_FORTRESS_JIANDU:
                catid = "3";
                break;
            case WebViewModel.TYPE_FORTRESS_QUN_ZHONG:
                catid = "3";
                break;
            case WebViewModel.TYPE_FORTRESS_SAN_HUI:
                catid = "4";
                break;
            case WebViewModel.TYPE_MODEL_PRIVE:
                catid = "5";
                break;
            case WebViewModel.TYPE_MODEL_GROUP:
                catid = "6";
                break;
            default:
                break;
        }
        return catid;
    }
}
