package com.hbln.inspection.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.cmcc.lib_network.model.LoginModel;
import com.cmcc.lib_network.model.UserInfoModel;
import com.cmcc.lib_utils.utils.ToastUtils;
import com.hbln.inspection.R;

import rx.functions.Action1;

/**
 * <p>describe</p><br>
 *
 * @author - lwc
 * @date - 2017/12/15
 * @note -
 * -------------------------------------------------------------------------------------------------
 * @modified -
 * @date -
 * @note -
 */
public class VisitDialog extends AlertDialog implements View.OnClickListener {
    private View mVDialogShadow;
    private EditText mEtVisitName;
    private EditText mEtVisitAnswerName;
    private EditText mEtVisitAnswerGuanxi;
    private EditText mEtVisitAnswerMobile;
    /** 开始 */
    private Button mBtnVisitOk;
    /** 取消 */
    private Button mBtnVisitCancel;
    private View.OnClickListener mOnClickListener;

    public VisitDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_visit);
        initView();
        //只用下面这一行弹出对话框时需要点击输入框才能弹出软键盘
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    private void initView() {
        mVDialogShadow = (View) findViewById(R.id.v_dialog_shadow);
        mEtVisitName = (EditText) findViewById(R.id.et_visit_name);
        mEtVisitAnswerName = (EditText) findViewById(R.id.et_visit_answer_name);
        mEtVisitAnswerGuanxi = (EditText) findViewById(R.id.et_visit_answer_guanxi);
        mEtVisitAnswerMobile = (EditText) findViewById(R.id.et_visit_answer_mobile);
        mBtnVisitOk = (Button) findViewById(R.id.btn_visit_ok);
        if (mBtnVisitOk != null) {
            mBtnVisitOk.setOnClickListener(this);
        }
        mBtnVisitCancel = (Button) findViewById(R.id.btn_visit_cancel);
        if (mBtnVisitCancel != null) {
            mBtnVisitCancel.setOnClickListener(this);
        }

        LoginModel.getUserInfo(new Action1<UserInfoModel.UserInfo>() {
            @Override
            public void call(UserInfoModel.UserInfo userInfo) {
                mEtVisitName.setText(userInfo.nickname);
            }
        });
    }

    /**
     * ok的监听
     *
     * @param onClickListener
     */
    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public String getAnswerName() {
        String name = mEtVisitAnswerName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToastSafe("请输入亲戚姓名");
            return "";
        }
        return name;
    }

    public String getAnswerGuanxi() {
        String name = mEtVisitAnswerGuanxi.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToastSafe("请输入亲戚关系");
            return "";
        }
        return name;
    }

    public String getAnswerMobile() {
        String name = mEtVisitAnswerMobile.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShortToastSafe("请输入亲戚联系电话");
            return "";
        }
        return name;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_visit_ok:
                cancel();
                if (mOnClickListener != null) {
                    mOnClickListener.onClick(v);
                }
                break;
            case R.id.btn_visit_cancel:
                cancel();
                break;
            default:
                break;
        }
    }
}
