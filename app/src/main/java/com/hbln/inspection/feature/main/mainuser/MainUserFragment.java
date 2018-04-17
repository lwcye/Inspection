package com.hbln.inspection.feature.main.mainuser;


import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cmcc.lib_utils.utils.AppUtils;
import com.hbln.inspection.R;
import com.hbln.inspection.feature.accout.accountlist.AccountListActivity;
import com.hbln.inspection.mvp.MVPBaseFragment;
import com.hbln.inspection.network.model.ObjectModel;
import com.hbln.inspection.network.model.UserInfoModel;
import com.hbln.inspection.ui.Activity.UserTestListActivity;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MainUserFragment extends MVPBaseFragment<MainUserContract.View, MainUserPresenter> implements MainUserContract.View, View.OnClickListener {

    private View view;
    private TextView mLeft;
    private TextView mCenter;
    private TextView mRight;
    private View mVTitleBarShadow;
    private LinearLayout mLlTitle;
    /**
     * 李勇
     */
    private TextView mTvUserName;
    /**
     * 00000000000000000
     */
    private TextView mTvUserSfid;
    /**
     * 00000000000
     */
    private TextView mTvUserMobile;
    /**
     * 巡查三组--职务
     */
    private TextView mTvUserDanwei;
    private TextView mTvUserTest;
    private LinearLayout mLlUserTest;
    private TextView mTvUserRead;
    private LinearLayout mLlUserRead;
    private TextView mTvUserMail;
    private LinearLayout mLlUserMail;
    private TextView mTvUserChange;
    private LinearLayout mLlUserChange;
    /**
     * 退出登录
     */
    private Button mTvUserLogout;

    @Override
    protected MainUserPresenter createPresenter() {
        return new MainUserPresenter();
    }

    @Override
    public void initData() {
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.loadUserInfo();
        mPresenter.loadRead();
    }

    @Override
    public void initView(View view) {
        ((TextView) view.findViewById(R.id.center)).setText("个人中心");
        mLeft = (TextView) view.findViewById(R.id.left);
        mCenter = (TextView) view.findViewById(R.id.center);
        mRight = (TextView) view.findViewById(R.id.right);
        mVTitleBarShadow = view.findViewById(R.id.v_title_bar_shadow);
        mLlTitle = (LinearLayout) view.findViewById(R.id.ll_title);
        mTvUserName = (TextView) view.findViewById(R.id.tv_user_name);
        mTvUserSfid = (TextView) view.findViewById(R.id.tv_user_sfid);
        mTvUserMobile = (TextView) view.findViewById(R.id.tv_user_mobile);
        mTvUserDanwei = (TextView) view.findViewById(R.id.tv_user_danwei);
        mTvUserTest = (TextView) view.findViewById(R.id.tv_user_test);
        mTvUserRead = (TextView) view.findViewById(R.id.tv_user_read);
        mLlUserTest = (LinearLayout) view.findViewById(R.id.ll_user_test);
        mLlUserRead = (LinearLayout) view.findViewById(R.id.ll_user_read);
        mLlUserTest.setOnClickListener(this);
        mLlUserTest.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("当前版本");
                builder.setMessage("版本名：" + AppUtils.getAppVersionName() + ",版本号：" + AppUtils.getAppVersionCode());
                builder.create().show();
                return false;
            }
        });
        mTvUserMail = (TextView) view.findViewById(R.id.tv_user_mail);
        mLlUserMail = (LinearLayout) view.findViewById(R.id.ll_user_mail);
        mLlUserMail.setOnClickListener(this);
        mTvUserChange = (TextView) view.findViewById(R.id.tv_user_change);
        mLlUserChange = (LinearLayout) view.findViewById(R.id.ll_user_change);
        mLlUserChange.setOnClickListener(this);
        mTvUserLogout = (Button) view.findViewById(R.id.tv_user_logout);
        mTvUserLogout.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_user_logout:
                mPresenter.logout();
                break;
            case R.id.ll_user_test:
                UserTestListActivity.start(getBaseActivity());
                break;
            case R.id.ll_user_mail:
                AccountListActivity.start(getBaseActivity());
                break;
            case R.id.ll_user_change:
                break;
            default:
                break;
        }
    }

    @Override
    public void resultUserInfo(UserInfoModel.UserInfo userInfoModel) {
        if (userInfoModel == null) {
            mTvUserName.setText("");
            mTvUserSfid.setText("");
            mTvUserMobile.setText("");
            mTvUserDanwei.setText("");
        } else {
            mTvUserName.setText(userInfoModel.nickname);
            mTvUserSfid.setText(userInfoModel.sfid);
            mTvUserMobile.setText(userInfoModel.mobile);
            mTvUserDanwei.setText(userInfoModel.danwei);
        }
    }

    @Override
    public void resultRead(ObjectModel userInfoModel) {
        try {
            mTvUserRead.setText(String.format("%.2f", Float.valueOf(userInfoModel.info.toString()) * 100) + "%");
        } catch (NumberFormatException ignored) {
        }
    }
}
